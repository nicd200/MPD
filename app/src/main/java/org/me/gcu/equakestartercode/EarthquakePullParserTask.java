package org.me.gcu.equakestartercode;

import android.media.audiofx.Equalizer;
import android.util.Log;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;

public class EarthquakePullParserTask implements Runnable {
    private MainActivity mainActivity;
    private String xmlString = "";

    private String url;

    public EarthquakePullParserTask(MainActivity mainActivity, String aurl) {
        this.mainActivity = mainActivity;
        url = aurl;
    }

    private void loadStringFromUrl() {
        URL aurl;
        URLConnection yc;
        BufferedReader in = null;
        String inputLine = "";

        Log.e("MyTag", "in run");

        try {
            Log.e("MyTag", "in try");
            aurl = new URL(url);
            yc = aurl.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            Log.e("MyTag", "after ready");
            //
            // Now read the data. Make sure that there are no specific hedrs
            // in the data file that you need to ignore.
            // The useful data that you need is in each of the item entries
            //
            while ((inputLine = in.readLine()) != null) {
                xmlString = xmlString + inputLine;
                Log.e("MyTag", inputLine);
            }
            in.close();
        } catch (IOException ae) {
            Log.e("MyTag", "IOException in run");
        }
    }

    private ArrayList<EarthquakeItem> parseXML() throws XmlPullParserException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        EarthquakeItem eQuake = new EarthquakeItem();
        ArrayList<EarthquakeItem> itemList = null;

        xpp.setInput(new StringReader(xmlString));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document");
            } else if (eventType == XmlPullParser.START_TAG) {
                try {
                    String prefix = xpp.getPrefix() == null ? "" : xpp.getPrefix();
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        eQuake = new EarthquakeItem();
                    }
                    // Check which Tag we have
                    else if (xpp.getName().equalsIgnoreCase("channel")) {
                        itemList = new ArrayList<>();
                    } else if (xpp.getName().equalsIgnoreCase("item")) {
                        eQuake = new EarthquakeItem();
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        // Now just get the associated text
                        String temp = null;
                        temp = xpp.nextText();

                        // Do something with text
                        eQuake.setTitle(temp);
                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        // Now just get the associated text
                        String temp = xpp.nextText();

                        // Do something with text
                        eQuake.setDescription(temp);
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        // Now just get the associated text
                        String temp = xpp.nextText();

                        // Do something with text
                        eQuake.setLink(temp);
                    } else if (xpp.getName().equalsIgnoreCase("category")) {
                        // Now just get the associated text
                        String temp = xpp.nextText();

                        // Do something with text
                        eQuake.setCategory(temp);
                    } else if (prefix.equalsIgnoreCase("geo")
                            && xpp.getName().equalsIgnoreCase("lat")) {
                        // Now just get the associated text
                        String temp = xpp.nextText();

                        // Do something with text
                        float lat = Float.parseFloat(temp);

                        // Attempt to convert temp into a float
                        eQuake.setLat(lat);
                    } else if (prefix.equalsIgnoreCase("geo")
                            && xpp.getName().equalsIgnoreCase("long")) {
                        // Now just get the associated text
                        String temp = xpp.nextText();
                        // Do something with text

                        // Attempt to convert temp into a float
                        float lng = Float.parseFloat(temp);

                        eQuake.setLong(lng);
                    }
                } catch (IOException ex1) {
                    ex1.printStackTrace();
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                if (xpp.getName().equalsIgnoreCase("item")) {
                    Log.e("MyTag", "earthquake is " + eQuake.toString());
                    itemList.add(eQuake);

                    eQuake = new EarthquakeItem();
                } else if (xpp.getName().equalsIgnoreCase("channel")) {
                    int size;
                    size = itemList.size();
                    Log.e("MyTag", "itemList size is " + size);
                }
            }

            // Get the next event
            try {
                eventType = xpp.next();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } // End of while

        return itemList;
    }

    @Override
    public void run() {
        loadStringFromUrl();
        ArrayList<EarthquakeItem> earthquakeItems = new ArrayList<>();

        try {
            earthquakeItems = parseXML();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        //
        // Now that you have the xml data you can parse it
        //

        // Now update the TextView to display raw XML data
        // Probably not the best way to update TextView
        // but we are just getting started !
        ArrayList<EarthquakeItem> finalEarthquakeItems = earthquakeItems;
        this.mainActivity.runOnUiThread(() -> {
            Log.d("UI thread", "I am the UI thread");
            mainActivity.loadEarthquakeList(finalEarthquakeItems);
        });
    }
}
