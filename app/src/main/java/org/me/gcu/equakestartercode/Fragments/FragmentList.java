package org.me.gcu.equakestartercode.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.me.gcu.equakestartercode.Components.EarthquakeInfoComponent;
import org.me.gcu.equakestartercode.EarthquakeData;
import org.me.gcu.equakestartercode.EarthquakeItem;
import org.me.gcu.equakestartercode.MainActivity;
import org.me.gcu.equakestartercode.R;

import java.util.ArrayList;

// Nicola Dochnenko S1915348
public class FragmentList extends Fragment implements IEarthquakeFragment {

    private MainActivity mainActivity = null;

    private ArrayList<EarthquakeItem> earthquakes;

    private TableLayout quakeTable;
    private SearchView searchBar;
    private Button searchButton;
    private Button reloadButton;

    private View viewInstance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navfrag_list, container, false);
        this.viewInstance = view;

        // Set up the raw links to the graphical components
        quakeTable = view.findViewById(R.id.eqTable);
        searchBar = view.findViewById(R.id.searchBox);

        reloadButton = view.findViewById(R.id.reloadButton);
        reloadButton.setOnClickListener(this::btnReload_OnClick);

        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this::btnSearch_OnClick);

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

    private void btnReload_OnClick(View v) {
        //
        mainActivity.reloadAllEarthquakes();
    }

    private void btnSearch_OnClick(View v) {
        // Filter the items
        mainActivity.reloadAllEarthquakes();
    }

    @Override
    public void setMainActivityInstance(MainActivity activity) {
        this.mainActivity = activity;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onEarthquakeListReady(ArrayList<EarthquakeItem> earthquakes) {
        this.earthquakes = earthquakes;

        // Finding table
        TableLayout tbl = viewInstance.findViewById(R.id.eqTable);
        tbl.removeAllViews(); // Clears rows
        tbl.removeAllViewsInLayout();

        //
        int id = 91234;
        for(EarthquakeItem ei : earthquakes) {
            Log.e("Earthquakes", String.format("%f,%f - %s", ei.getLat(), ei.getLat(), ei.getTitle()));

            TableRow row = new TableRow(tbl.getContext());
            row.setId(id++);
            row.setLayoutParams(
                new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            FragmentManager fragMan = getFragmentManager();
            FragmentTransaction fragTransaction = fragMan.beginTransaction();

            EarthquakeInfoComponent eic = new EarthquakeInfoComponent(ei);
            fragTransaction.add(row.getId(), eic, "frg");
            fragTransaction.commit();

            tbl.addView(row,
                new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}
