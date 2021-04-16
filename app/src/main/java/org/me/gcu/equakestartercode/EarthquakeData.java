package org.me.gcu.equakestartercode;

import android.util.Log;

import java.util.HashMap;
import java.util.Objects;

// Nicola Dochnenko S1915348
public class EarthquakeData {
    // Origin date/time: Mon, 05 Apr 2021 03:04:09 ;
    // Location: BLACKFORD,PERTH/KINROSS ;
    // Lat/long: 56.261,-3.766 ;
    // Depth: 2 km ;
    // Magnitude: 0.6

    public String OriginDateTime;
    public String Location;
    public String LatLng;
    public String Depth;
    public float Magnitude;

    public EarthquakeData(String[] description) {
        HashMap<String, String> hm = new HashMap<>();
        for(String s : description) {
            String[] split = s.split(":");
            if (split.length <= 1) {
                Log.e("EarthquakeData.Error", s);
                continue;
            }
            String key = split[0].trim();
            String value = split[1].trim();
            hm.put(key, value);
        }

        this.OriginDateTime = getHashMapValue(hm, "Origin date/time", "");
        this.Location = getHashMapValue(hm, "Location", "");
        this.LatLng = getHashMapValue(hm, "Lat/long", "");
        this.Depth = getHashMapValue(hm, "Depth", "");
        this.Magnitude = getFloatHashMapValue(hm, "Magnitude", -1);
    }


    public String getHashMapValue(HashMap<String,String> data, String property, String def) {
        if (data.containsKey(property))
            return data.get(property);
        return def;
    }

    public float getFloatHashMapValue(HashMap<String,String> data, String property, float def) {
        if (data.containsKey(property)) {
            //return data.get(property);
            return Float.parseFloat(Objects.requireNonNull(data.get(property)));
        }
        return def;
    }

}
