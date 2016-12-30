package gr.teilar.scrooge;

import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gr.teilar.scrooge.Core.Category;
import gr.teilar.scrooge.Core.Expense;
import gr.teilar.scrooge.Core.ExpenseLocation;
import gr.teilar.scrooge.Database.ExpenseDb;
import gr.teilar.scrooge.Database.LocationDb;
import gr.teilar.scrooge.Fragments.AddExpenseFragment;
import gr.teilar.scrooge.Fragments.MapFragment;

public class AddExpenseActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener, AddExpenseFragment.OnAddExpenseListener  {

    private GoogleApiClient googleApiClient;

    private LocationRequest mLocationRequest;

    private boolean mLocationPermissionGranted;

    private Location mCurrentLocation;

    private Geocoder geocoder;

    private List<Address> address;

    private FragmentTransaction ft;

    private MapFragment mapFragment = new MapFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        AddExpenseFragment expenseFragment = new AddExpenseFragment();
        //MapFragment mapFragment = new MapFragment();


        //ena = (TextView) findViewById(R.id.textView2);
        //dio = (TextView) findViewById(R.id.textView3);
        geocoder = new Geocoder(getBaseContext(), Locale.getDefault());


        // FragmentTransaction

        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, expenseFragment);
        ft.addToBackStack(null);
        //ft.replace(R.id.map, mapFragment);
        ft.commit();


        buildGoogleApiClient();
        // Σύνδεση με τον GoogleApiClient μετά την δημιουργία του.
        googleApiClient.connect();



    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private synchronized void buildGoogleApiClient() {

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        createLocationRequest();



    }

    private void createLocationRequest() {

        mLocationRequest = new LocationRequest();

        /*
         * Θέτει το επιθυμητό διάστημα για ενημέρωσεις τοποθεσίας.
         * Αυτό το διάστημα είναι ανακριβές. Μπορεί να μην λάβουμε και
         * καθόλου ενημερώσεις αν δεν υπάρχουν διαθέσιμες πηγές, ή μπορεί
         * να λάβουμε ενημερώσεις αργότερα ή γρηγορότερα.
         */
        mLocationRequest.setInterval(1000);

        /*
         * Αυτό θέτει το ταχύτερο διάστημα ενημέρωσης. Ενημερώσεις δεν
         * θα συμβούν ποτέ ταχύτερα από αυτό το διάστημα.
         */
        mLocationRequest.setFastestInterval(500);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    private void getDeviceLocation() {
        /*
         * Ζήτα την άδεια για ανάκτηση τοποθεσίας, έτσι ώστε να μπορέσουμε
         * να ανακτήσουμε την τοποθεσία της συσκευής. Το αποτέλεσμα της αίτησης
         * θα το χειριστεί σε μία κλήση προς τα πίσω (callback) η μέθοδος
         * onRequestPermissionsResult την οποία θα πρέπει να υλοποιήσουμε
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        /*
         *
         * Ανάκτησε την καλύτερη και πιο πρόσφατη
         * τοποθεσία της συσκευής, που μπορεί να είναι
         * null αν η τοποθεσία δεν είναι διαθέσιμη.
         * Επίσης ζήτησε τακτικές ενημερώσεις για την
         * τοποθεσία της συσκευής.
         */
        if (mLocationPermissionGranted) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            /*
            try {
                address = geocoder.getFromLocation(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(), 1);
                if (address.size()>0) {
                    //ena.setText(address.get(0).getAddressLine(0));
                    //dio.setText(address.get(0).toString());
                }
                else {
                    //ena.setText(Double.toString(mCurrentLocation.getLatitude()));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                    mLocationRequest, this);

            startMapFragment();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case 1: {
                // Αν το αίτημα ακυρώθηκε, το array grantResults
                // είναι κενό.
                if (grantResults.length > 0
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getDeviceLocation();



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;

    }

    private void startMapFragment () {
        Bundle b = new Bundle();
        b.putDouble("lat", mCurrentLocation.getLatitude());
        b.putDouble("long", mCurrentLocation.getLongitude());
        mapFragment.setArguments(b);

        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.map, mapFragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public void onAddExpense(Category selectedCategory, String expenseDate,
                             String expenseAmount, String expenseDescription, String expenseLocation) {

        float amount = Float.parseFloat(expenseAmount);
        ExpenseLocation expenseLocation1 = new ExpenseLocation(expenseLocation, mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        long resultLocation = 0;
        long resultExpense = 0;
        try {
            resultLocation = LocationDb.insertLocation(AddExpenseActivity.this, expenseLocation1);
        } catch (SQLiteException e) {
            Log.v("Add Location", e.toString());

        }

        if (resultLocation == -1 ) {
            Log.v("Add Location", "Sfalma");

        } else {
            expenseLocation1.setLocationId(resultLocation);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            Date date = null;
            try {
                date = sdf.parse(expenseDate);
                Log.v("date", date.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Expense expense = new Expense(date, expenseDescription, amount, expenseLocation1, selectedCategory);

            try {
                resultExpense = ExpenseDb.insertExpense(AddExpenseActivity.this, expense);
            }catch (SQLiteException e){
                Log.v("Add Expense", e.toString());
            }

            if(resultExpense == -1) {
                Log.v("Add expense", "Sfalma");
            }
            else {
                Toast.makeText(this,"Expsense Succefully Added", Toast.LENGTH_LONG).show();
            }
        }




    }
}
