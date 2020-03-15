package com.example.mmgps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CameraActivity extends AppCompatActivity {


    ImageView chooseImage;
    Button chooseImageButton;
    EditText message;
    EditText subject;
    Button nextButton;
   // String imageName=  UUID.randomUUID().toString() + ".jpg";
    String str_subject , str_message;
    String url = "http://mmgps.000webhostapp.com/imageinfo.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        chooseImageButton= (Button)findViewById(R.id.selectButton);
        subject =  findViewById(R.id.subject);
        message =findViewById(R.id.messagEditText);
        nextButton = (Button) findViewById(R.id.nextButton);


    }

    public void getPhoto() {
        Intent intent =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);

    }
    public void chooseImage(View view)
    {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getPhoto();
        }
    }



    @Override
    protected void  onActivityResult(int requestCode, int resultCode, Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage = data.getData();


        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                // ImageView imageView= findViewById(R.id.imageView);
                chooseImage= (ImageView) findViewById(R.id.galleryImageView);

                chooseImage.setImageBitmap(bitmap);
                //chooseImage.setVisibility(View.VISIBLE);

            } catch ( Exception  e) {
                e.printStackTrace();
            }

        }

    }
    @Override
    public  void  onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto();
            }
        }
    }
    public void nextClicked(View view ) {
        // Get the data from an ImageView as bytes
        chooseImage.setDrawingCacheEnabled(true);
        chooseImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) chooseImage.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat  = stream.toByteArray();
       final String str_image = Base64.encodeToString(byteFormat,Base64.NO_WRAP);

        str_message =message.getText().toString();
        str_subject=subject.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(CameraActivity.this, response, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(CameraActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("subject",str_subject);
                params.put("message",str_message);
                params.put("image",str_image);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CameraActivity.this);
        requestQueue.add(request);










    }


}

