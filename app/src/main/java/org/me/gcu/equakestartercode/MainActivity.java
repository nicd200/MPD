package org.me.gcu.equakestartercode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.me.gcu.equakestartercode.Fragments.FragmentList;
import org.me.gcu.equakestartercode.Fragments.FragmentMap;
import org.me.gcu.equakestartercode.Fragments.FragmentStatistics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

// Nicola Dochnenko S1915348
public class MainActivity extends AppCompatActivity
{
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";

    FragmentList       fragList;
    FragmentMap        fragMap;
    FragmentStatistics fragStatistics;
    BottomNavigationView bottomNav;

    private ArrayList<EarthquakeItem> earthquakes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");

        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        fragList = new FragmentList();
        fragList.setMainActivityInstance(this);

        fragMap = new FragmentMap();
        fragMap.setMainActivityInstance(this);

        fragStatistics = new FragmentStatistics();
        fragStatistics.setMainActivityInstance(this);

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.navigationFragment, fragList)
            .commit();

        this.reloadAllEarthquakes();
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.NavBtn_List:
                selectedFragment = this.fragList;
                break;
            case R.id.NavBtn_Map:
                selectedFragment = this.fragMap;
                break;
            case R.id.NavBtn_Info:
                selectedFragment = this.fragStatistics;
                break;
        }

        // It will help to replace the one fragment to other.
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.navigationFragment, selectedFragment)
            .commit();
        return true;
    }

    public void reloadAllEarthquakes()
    {
        // Run network access on a separate thread;
        new Thread(new EarthquakePullParserTask(this, urlSource)).start();
    }

    // On UI Thread
    public void loadEarthquakeList(ArrayList<EarthquakeItem> earthquakes) {
        // Earthquakes have loaded

        this.fragMap.onEarthquakeListReady(earthquakes);
        this.fragList.onEarthquakeListReady(earthquakes);
        this.fragStatistics.onEarthquakeListReady(earthquakes);
    }
}