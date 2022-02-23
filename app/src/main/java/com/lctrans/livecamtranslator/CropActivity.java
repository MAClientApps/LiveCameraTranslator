package com.lctrans.livecamtranslator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropActivity extends AppCompatActivity implements View.OnClickListener {
    public static CropImageView cropImageView;
    public static Bitmap croppedImage;
    LinearLayout iv_done;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_crop);

        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        this.iv_done = (LinearLayout) findViewById(R.id.iv_done);

        Glide.with((FragmentActivity) this).asBitmap().load("").into(new SimpleTarget<Bitmap>() {
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {

                CropActivity.cropImageView.setImageBitmap(bitmap);
            }

            public void onLoadFailed(Drawable drawable) {

                super.onLoadFailed(drawable);
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                return;
            case R.id.iv_done:
                Bitmap croppedImage2 = cropImageView.getCroppedImage();
                croppedImage = croppedImage2;
                runTextRec(croppedImage2);
                return;
            case R.id.lytLeftRotate:
                cropImageView.rotateImage(-90);
                return;
            default:
                return;
        }
    }

    private void runTextRec(Bitmap bitmap) {
        FirebaseVision.getInstance().getOnDeviceTextRecognizer().processImage(FirebaseVisionImage.fromBitmap(bitmap)).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                if (firebaseVisionText.getText().length() <= 0) {
                    Toast.makeText(CropActivity.this.getApplicationContext(), "No text Found", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(CropActivity.this, TextActivity.class);
                intent.putExtra("text", firebaseVisionText.getText());
                CropActivity.this.startActivity(intent);
                CropActivity.this.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception exc) {
                Toast.makeText(CropActivity.this.getApplicationContext(), "Unable to fetch text from image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

}
