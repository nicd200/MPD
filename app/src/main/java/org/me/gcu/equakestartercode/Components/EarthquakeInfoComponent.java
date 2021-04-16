package org.me.gcu.equakestartercode.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import org.me.gcu.equakestartercode.EarthquakeItem;
import org.me.gcu.equakestartercode.R;

// Nicola Dochnenko S1915348
public class EarthquakeInfoComponent extends Fragment {
    private final EarthquakeItem earthquake;
    private View viewInstance;

    public EarthquakeInfoComponent(EarthquakeItem earthquakeItem) {
        this.earthquake = earthquakeItem;
    }

    private TextView txtTitle = null, txtDate = null, txtLocation = null, txtDepth = null, txtMagnitude = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_earthquake_info, container, false);
        this.viewInstance = view;

        txtTitle     = view.findViewById(R.id.eqCompTitle);
        txtDate      = view.findViewById(R.id.eqCompDate);
        txtLocation  = view.findViewById(R.id.eqCompLocation);
        txtDepth     = view.findViewById(R.id.eqCompDepth);
        txtMagnitude = view.findViewById(R.id.eqCompMagnitude);

        reloadViewInformation();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void reloadViewInformation() {

        txtTitle.setText(earthquake.getData().Magnitude + " | " + earthquake.getData().Location);
        txtDate.setText("Date:" + earthquake.getData().OriginDateTime);
        txtLocation.setText("Loc:" + earthquake.getData().Location);
        txtDepth.setText("Depth: " + earthquake.getData().Depth);
        txtMagnitude.setText("Mag: " + earthquake.getData().Magnitude);
    }
}
