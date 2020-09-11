package com.java.feedmillo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class second_click_activity extends AppCompatActivity {
    private static final int GALLERY_PICK = 1;
    private static final int CAMERA_REQUEST = 1888;
    public ImageView mealImg;
    private int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_click_activity);

        ImageView cameraBtn = findViewById(R.id.camera_btn);
        mealImg = findViewById(R.id.mealpic);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                { requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE); }
                else { Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST && resultCode==RESULT_OK){
            Bitmap imageBmp = (Bitmap) data.getExtras().get("data");
            mealImg.setImageBitmap(imageBmp);

            Intent intent = new Intent(second_click_activity.this , share_meal_activity.class);
            intent.putExtra("imageBmp",imageBmp);
            second_click_activity.this.startActivity(intent);
            finish();
        }
    }

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
        { super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        { if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
        { Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } else
            { Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        }
}