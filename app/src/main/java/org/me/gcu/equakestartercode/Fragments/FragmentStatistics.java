package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.me.gcu.equakestartercode.EarthquakeItem;
import org.me.gcu.equakestartercode.MainActivity;
import org.me.gcu.equakestartercode.R;

import java.util.ArrayList;

// Nicola Dochnenko S1915348
public class FragmentStatistics extends Fragment implements IEarthquakeFragment {

    private MainActivity mainActivity = null;
    private ArrayList<EarthquakeItem> earthquakes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navfrag_statistics, container, false);

        //

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (this.earthquakes != null)
            onEarthquakeListReady(this.earthquakes);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.earthquakes != null)
            onEarthquakeListReady(this.earthquakes);
    }

    @Override
    public void setMainActivityInstance(MainActivity activity) {
        this.mainActivity = activity;
    }

    @Override
    public void onEarthquakeListReady(ArrayList<EarthquakeItem> earthquakes) {
        //
        this.earthquakes = earthquakes;

    }
}