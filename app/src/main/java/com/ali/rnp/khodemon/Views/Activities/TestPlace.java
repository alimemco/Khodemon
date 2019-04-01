package com.ali.rnp.khodemon.Views.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.rnp.khodemon.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class TestPlace extends AppCompatActivity  {

    String API_KEY_PLACES = "AIzaSyCL2OQzXCytliGrm1OC_HI0VT0rarHn2h8";
    String PLACE_ID_MHR = "ChIJrTLr-GyuEmsRBfy61i59si0";
    private static final String TAG = "TestPlaceNewActivity";
    private TextView textView;

    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_place);

        textView = findViewById(R.id.test_place_text);

        button = findViewById(R.id.test_place_button);


        button.setOnClickListener(v -> fetchPlaceById());




    }

    private void fetchPlaceById () {
        //Initialize Places.
        Places.initialize(getApplicationContext(), API_KEY_PLACES);

// Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);


        // Define a Place ID.
       // String placeId = "ChIJrTLr-GyuEmsRBfy61i59si0";

// Specify the fields to return (in this example all fields are returned).
        List<Place.Field> placeFields = Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG,
                Place.Field.PRICE_LEVEL,
                Place.Field.PHONE_NUMBER,
                Place.Field.OPENING_HOURS,
                Place.Field.USER_RATINGS_TOTAL,
                Place.Field.WEBSITE_URI,
                Place.Field.VIEWPORT
        );

// Construct a request object, passing the place ID and fields array.
        FetchPlaceRequest request = FetchPlaceRequest.builder(PLACE_ID_MHR, placeFields).build();


        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            Log.i(TAG, "Place found: " + place.getName());

            String data =
                    "Name : " + place.getName() + " \n \n" +
                            "Address : " + place.getAddress() + " \n \n " +
                            "LatLng : " + place.getLatLng() + " \n \n" +
                            "PriceLevel : " + place.getPriceLevel() + " \n \n" +
                            "PhoneNumber : " + place.getPhoneNumber() + " \n \n" +
                            "OpeningHours : " + place.getOpeningHours() + " \n \n" +
                            "UserRatingsTotal : " + place.getUserRatingsTotal() + " \n \n" +
                            "WebsiteUri : " + place.getWebsiteUri() + " \n \n" +
                            "Viewport : " + place.getViewport();


            textView.setText(data);


        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {

                textView.setText(exception.getMessage());

            }else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
