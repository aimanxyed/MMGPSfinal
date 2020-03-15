package com.example.mmgps;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class RegistrationActivity extends AppCompatActivity {



    EditText name ;
    EditText userLoginID;
    EditText password;
    ProgressBar progressBar;
    Button  imageUpload;


    String str_name,str_userLoginID,str_password;
    String url = "http://mmgps.000webhostapp.com/register.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.ed_username);
        userLoginID=findViewById(R.id.ed_email);
        password=findViewById(R.id.ed_password);
        progressBar = findViewById(R.id.progressBar);



    }

    public void getPhoto() {
        Intent intent =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);

    }
    public void UploadImage(View view)
    {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getPhoto();
        }
    }


    public void Register(View view)
    {
        final ProgressBar progressBar = new ProgressBar(RegistrationActivity.this, null, android.R.attr.progressBarStyleLarge);

        if(name.getText().toString().equals("")){
            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
        }
        else if(userLoginID.getText().toString().equals("")){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().toString().equals("")){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else {

            progressBar.setVisibility(View.VISIBLE);
            str_name = name.getText().toString().trim();
            str_userLoginID = userLoginID.getText().toString().trim();
            str_password = password.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    progressBar.setVisibility(View.GONE);
                    name.setText("");
                    userLoginID.setText("");
                    password.setText("");
                    Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                    startActivity(intent);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("name",str_name);
                    params.put("userLoginID",str_userLoginID);
                    params.put("password",str_password);
                    return params;

                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);
            requestQueue.add(request);




        }





    }




}
