package com.java.feedmillo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class share_meal_activity extends AppCompatActivity {

    private static final int GALLERY_PICK = 1;
    private static final int CAMERA_REQUEST = 1888;
    private int MY_CAMERA_PERMISSION_CODE = 100;
    public ImageView mealImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_meal_activity);

        Button share_btn = findViewById(R.id.sharemealbutton);

        Intent intent = getIntent();
        final Bitmap bmp = (Bitmap)intent.getParcelableExtra("imageBmp");

        TextView take_again = findViewById(R.id.takeagain);
        mealImage = findViewById(R.id.sharemealpic);

        take_again.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        mealImage.setImageBitmap(bmp);

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(share_meal_activity.this , AnimateActivity.class);
                intent.putExtra("imageBmp",bmp);
                share_meal_activity.this.startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST && resultCode==RESULT_OK){
            Bitmap Bmp = (Bitmap) data.getExtras().get("data");
            mealImage.setImageBitmap(Bmp);
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