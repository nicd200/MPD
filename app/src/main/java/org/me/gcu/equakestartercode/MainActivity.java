package org.me.gcu.equakestartercode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private TextView rawDataDisplay;
    private Button startButton;
    private String result = "";
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private TableLayout quakeTable;
    private SearchView searchBar;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");

        // Set up the raw links to the graphical components
        quakeTable = (TableLayout)findViewById(R.id.eqTable);
        searchBar = (SearchView)findViewById(R.id.searchBox);
        searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        Log.e("MyTag","after startButton");

        // More Code goes here
    }

    public void onClick(View aview)
    {
        Log.e("MyTag","in onClick");
        startProgress();
        Log.e("MyTag","after startProgress");
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new EarthquakePullParserTask(this, urlSource)).start();
    } //

    // On UI Thread
    public void loadEarthquakeList(ArrayList<EarthquakeItem> earthquakes) {
        // Earthquakes have loaded

        for(EarthquakeItem ei : earthquakes) {
            Log.e("Earthquakes", String.format("%f,%f - %s", ei.getLat(), ei.getLat(), ei.getTitle()));

            // Finding table
            TableLayout tbl = (TableLayout)findViewById(R.id.eqTable);
            tbl.removeAllViews(); // Clears rows

            // Creating table rows
            TableRow row = new TableRow(tbl.getContext());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Content for location table row
            TextView location = new TextView(this);
            location.setText(ei.getTitle());
            location.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Content for magnitude table row
            TextView magnitude = new TextView(this);
            magnitude.setText(ei.getTitle());
            magnitude.setGravity(Gravity.RIGHT);
            magnitude.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            row.addView(location);
            row.addView(magnitude);

            tbl.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    public TextView getRawDataDisplay() {
        return rawDataDisplay;
    }
}