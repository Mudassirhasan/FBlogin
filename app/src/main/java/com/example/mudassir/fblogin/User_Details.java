package com.example.mudassir.fblogin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
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
        EditText et1,et2,et3,et4,et5;
        et1=findViewById(R.id.editText);
        et2=findViewById(R.id.editText2);
        et3=findViewById(R.id.et3);
        et4=findViewById(R.id.editText3);
        et5=findViewById(R.id.editText4);




        tv=findViewById(R.id.tvv);
        Intent intent= getIntent();
        String fn=intent.getStringExtra("First_Name");
        String ln=intent.getStringExtra("Last_Name");
        String e=intent.getStringExtra("Email");
        String id=intent.getStringExtra("Id");
        String bi=intent.getStringExtra("Birth");
        String p=intent.getStringExtra("im");

        String lati=intent.getStringExtra("lati");
        String longi=intent.getStringExtra("longi");

       tv.setText("Your location:"+"\nLatitude:"+lati+"\n Longitude:"+longi);
        et1.setText(fn);
        et2.setText(ln);
        et3.setText(e);
        et4.setText(bi);
        et5.setText(id);




        Picasso.with(this).load("https://graph.facebook.com/"+id+"/picture?type=large").into(im2);



    }
}
