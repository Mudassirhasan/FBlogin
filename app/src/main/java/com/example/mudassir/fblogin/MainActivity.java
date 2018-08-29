package com.example.mudassir.fblogin;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION=1;
    TextView tv;
    LocationManager locationManager;
    String lat,lag;
    String address;
    ImageView im;
    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);


        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile","user_birthday");
        im=findViewById(R.id.imageView);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                String userid = loginResult.getAccessToken().getUserId();
                String accesstoken=loginResult.getAccessToken().getToken();
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        displayUserInfo(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name,last_name,email,id,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
 private void displayUserInfo(JSONObject object) {
     String first_name, last_name, email, id, birth;

     locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
     if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
         builAlertMsgNoGps();
     } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
         getLocation();
     }


        try {


            first_name=object.getString("first_name");
            last_name=object.getString("last_name");
            email=object.getString("email");
            id=object.getString("id");
            birth=object.getString("birthday");

            // TextView tv1,tv2,tv3,tv4;

//            tv1=findViewById(R.id.textView);
  //          tv2=findViewById(R.id.emailtv);
    //        tv3=findViewById(R.id.id);
      //      tv4=findViewById(R.id.birth);
        //    tv1.setText(first_name+" "+last_name);
          //  tv2.setText("Email: "+email);
         //   tv3.setText("Id: "+id);
          //  tv4.setText("Birthday: "+birth);
            Intent intent=new Intent(MainActivity.this,User_Details.class);
            intent.putExtra("First_Name",first_name);
            intent.putExtra("Last_Name",last_name);
            intent.putExtra("Email",email);
            intent.putExtra("Id",id);
            intent.putExtra("Birth",birth);
            intent.putExtra("lati",lat);
            intent.putExtra("longi",lag);
            intent.putExtra("add",address);



            startActivity(intent);



        } catch (JSONException e1) {
            e1.printStackTrace();
        }


 }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double lati = location.getLatitude();
                double longi = location.getLongitude();
               lat = String.valueOf(lati);
                lag = String.valueOf(longi);
               getAddress(lati,longi);

            }

        }
    }

    public String getAddress(double lat, double lag) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null; // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        try {
            addresses = geocoder.getFromLocation(lat, lag, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        return address;
    }


    protected void builAlertMsgNoGps()
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Please Turn On Your Gps")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    }

