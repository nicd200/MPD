package org.me.gcu.equakestartercode.Fragments;

import org.me.gcu.equakestartercode.EarthquakeItem;
import org.me.gcu.equakestartercode.MainActivity;

import java.util.ArrayList;
import java.util.List;

// Nicola Dochnenko S1915348
public interface IEarthquakeFragment {
    void setMainActivityInstance(MainActivity activity);

    void onEarthquakeListReady(ArrayList<EarthquakeItem> earthquakes);
}
