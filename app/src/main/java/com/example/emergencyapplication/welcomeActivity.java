package com.example.emergencyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class welcomeActivity extends AppCompatActivity implements LocationListener{
    TextView contact1,contact2,contact3;
    Button edit,send;
    String phone1;
    String phone2;
    String phone3;
    String text;
    Boolean isPErmissionGranted = false;
    String adress;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        contact1=(TextView)findViewById(R.id.textView3);
        contact2=(TextView)findViewById(R.id.textView4);
        contact3=(TextView)findViewById(R.id.textView5);
        edit=(Button)findViewById(R.id.button2);
        send=(Button)findViewById(R.id.button3);
        SharedPreferences sharedPreferences1 = getSharedPreferences("my_contacts",MODE_PRIVATE);
        phone1 =sharedPreferences1.getString("number1","");
        phone2 =sharedPreferences1.getString("number2","");
        phone3 =sharedPreferences1.getString("number3","");
        contact1.setText(phone1);
        contact2.setText(phone2);
        contact3.setText(phone3);

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_PHONE_STATE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.areAllPermissionsGranted()){
                    //this code will exectue if all perm are granted
                    isPErmissionGranted = true;

                }

            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();




        edit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {

               Intent intent1 = new Intent(welcomeActivity.this,ContactActivity.class);
               startActivity(intent1);






            }
        });
        //creating map activity
        send.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
               // Intent intent2 = new Intent(welcomeActivity.this,MapsActivity.class);
              //  startActivity(intent2);
                if(!(phone1.toString().isEmpty()&& phone2.toString().isEmpty() && phone3.toString().isEmpty())==false)
                {
                    Toast.makeText(welcomeActivity.this,"please Edit contacts of your friends or family members",Toast.LENGTH_SHORT).show();

                }
                else {
                    fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(welcomeActivity.this);
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(welcomeActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            onLocationChanged(location);
//                            LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
//                            Log.d("WELCOME","fused"+location.getLatitude()+" and "+location.getLongitude());
//                            // getadress(location);
//                            Intent intent3= new Intent(welcomeActivity.this,MapsActivity.class);
//                            MapsActivity.latlngfetched =latLng;
//                            startActivity(intent3);
                           Toast.makeText(welcomeActivity.this,"adress is"+getadress(location),Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(welcomeActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(welcomeActivity.this,"failure is accured.",Toast.LENGTH_SHORT).show();
                            getLocation();

                        }
                    });

                }

//


            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu manu)
    {
        getMenuInflater().inflate(R.menu.menu,manu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id==R.id.about){
            Intent intent6= new Intent(welcomeActivity.this,about_me.class);
            startActivity(intent6);
            return true;
        }
        return true;
    }

    private void Mymessage(String adress) {


        if((phone1.toString().isEmpty()||phone2.toString().isEmpty() ||phone3.toString().isEmpty())==false )
        {
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(phone1,null,"Your friend is danger situation please help him.His/Her adress is"+adress.toString(),null,null);
            smsManager.sendTextMessage(phone2,null,"Your friend is danger situation please help him.His/Her adress is"+adress.toString(),null,null);
            smsManager.sendTextMessage(phone3,null,"Your friend is danger situation please help him.His/Her adress is"+adress.toString(),null,null);
            Toast.makeText(welcomeActivity.this,"message sent to three contacts sucessfully",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(welcomeActivity.this,"please make sure you filled your best  three known contact details..",Toast.LENGTH_SHORT).show();

        }

    }


    private void getLocation() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {

                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
       // getadress(location);
        Log.d("WELCOME",location.getLatitude()+" and "+location.getLongitude());
        Intent intent3= new Intent(welcomeActivity.this,MapsActivity.class);
        MapsActivity.latlngfetched =latLng;
        startActivity(intent3);

    }
    String getadress(Location location)
    {
        adress="";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresseslist = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
            adress=addresseslist.get(0)+"";

            //permission check
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.SEND_SMS)
                    .withListener(new PermissionListener() {
                        @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                            //write the code you want to execute when permission is given

                                Mymessage(adress);


                        }
                        @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                        @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                    }).check();



        } catch (IOException e) {
            e.printStackTrace();
        }

        return  adress;
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}
