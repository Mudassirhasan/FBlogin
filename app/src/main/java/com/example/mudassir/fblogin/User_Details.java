package com.example.mudassir.fblogin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

public class User_Details extends AppCompatActivity {

    CallbackManager callback;
    LoginButton login;
    ImageView im2;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__details);

        im2=findViewById(R.id.imageView2);



        tv=findViewById(R.id.textView2);
        Intent intent= getIntent();
        String fn=intent.getStringExtra("First_Name");
        String ln=intent.getStringExtra("Last_Name");
        String e=intent.getStringExtra("Email");
        String id=intent.getStringExtra("Id");
        String bi=intent.getStringExtra("Birth");
        String p=intent.getStringExtra("im");
        String lati=intent.getStringExtra("lati");
        String longi=intent.getStringExtra("longi");

        tv.setText(fn+" "+ln+"\n Email:"+e+"\n Id:"+id+"\n Birth:"+bi+"\nYour location:"+"\nLatitude:"+lati+"\n Longitude:"+longi);
        Picasso.with(this).load(p).into(im2);



    }
}
