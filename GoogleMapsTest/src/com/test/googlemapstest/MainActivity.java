//IMPORTANT: The approach of trying to get the current position with LocationClient is DEPRECATED. 
//Instead, LocationServices should be used.

package com.test.googlemapstest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements
	GooglePlayServicesClient.ConnectionCallbacks,
	GooglePlayServicesClient.OnConnectionFailedListener,
	com.google.android.gms.location.LocationListener {
 
    // Google Map
    private GoogleMap googleMap;
    private LocationClient mLocationClient;
    
    private Location mLocation;
    int currentView;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //create LocationClient
        mLocationClient =  new LocationClient(this, this, this);
        
        try {
            // Loading map
            initializeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initializeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
            
            //set lat and long
            double latitude = 51.593786;
            double longitude = 10.927959;
            LatLng defLocation = new LatLng(latitude, longitude);
            
            //create a standard marker
            MarkerOptions marker = new MarkerOptions().position(defLocation);
            
            //add marker to map
            googleMap.addMarker(marker);
            
            //show user's current location
            googleMap.setMyLocationEnabled(true); 
            
            //move camera to defined location
            //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( defLocation, 10));
      
        }
    }
    //show a toast with user location
    public void showMyLocation() {
        if (mLocationClient != null && mLocationClient.isConnected()) {
            String msg = "Location = " + mLocationClient.getLastLocation();
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
    
    private void setUpLocationClientIfNeeded() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(
                    getApplicationContext(),
                    this,  // ConnectionCallbacks
                    this); // OnConnectionFailedListener
        }
    }
 
    @Override
    protected void onStart(){
    	super.onStart();
    	mLocationClient.connect(); //HAS to be in onStart() s
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (mLocationClient != null && mLocationClient.isConnected()) {
    		//get the last user location
        	 mLocation = mLocationClient.getLastLocation();
        	 //Location -> LatLng
        	 LatLng userLatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        	 		 
        	 googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( userLatLng, 10));
        	 googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom( userLatLng, 10));
        	
         }
    }
    
    @Override
    protected void onStop(){
    	mLocationClient.disconnect();
        super.onStop();

    }

	@Override
	public void onLocationChanged(Location location) {
		// get the last location
		//this could return null, if the last location is unknown!
		mLocation = mLocationClient.getLastLocation();
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
 
}