package gr.teilar.scrooge.Fragments;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import gr.teilar.scrooge.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment  {

    private MapView mMapView;
    private GoogleMap googleMap;


    public MapFragment() {

        // Required empty public constructor
    }


    //Fragment που περιέχει ενα mapView για την εμφάνιση του χάρτη.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R.layout.fragment_map,container, false);

        //Λήψη των συντεταγμένων απ το bundle
        final Bundle locationBundle = this.getArguments();


        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);


        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                //googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng expensePosition = new LatLng(locationBundle.getDouble("lat"), locationBundle.getDouble("long"));
                if(locationBundle.getString("address")!=null) {
                    googleMap.addMarker(new MarkerOptions().position(expensePosition).title(getString(R.string.your_location)).snippet(locationBundle.getString("address")));
                }
                else {
                    googleMap.addMarker(new MarkerOptions().position(expensePosition).title(getString(R.string.your_location)).snippet("You are here"));

                }
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(expensePosition).zoom(13).build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });



        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}
