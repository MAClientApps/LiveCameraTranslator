package com.lctrans.livecamtranslator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.RewardedVideoCallbacks;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements RewardedVideoCallbacks {
    String imageFilePath;
    File photoFile;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.home);
        FirebaseApp.initializeApp(this);
        if(AppConstants.SHOW_ADS) {
            try {
                AdsUtil.initialiseAds(this);
                AdsUtil.showBannerAds(this, R.id.appodealBannerView);
                Appodeal.setRewardedVideoCallbacks(this);
            } catch (Exception e) {
            }
        }

        findViewById(R.id.button_history).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    HomeActivity.this.askPermission(ExifInterface.GPS_MEASUREMENT_2D);
                } else {
                    HomeActivity.this.goHistory();
                }
            }
        });

        findViewById(R.id.button_camera_translate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    HomeActivity.this.askPermission("5");
                } else {
                    HomeActivity.this.openCameraIntent();
                }
            }
        });
    }


    public void goHistory() {
        startActivity(new Intent(this, HistoryActivity.class));
    }

    public void askPermission(final String str) {
        Dexter.withActivity(this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO").withListener(new MultiplePermissionsListener() {
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (!multiplePermissionsReport.areAllPermissionsGranted()) {
                    return;
                }
                if (str.equals("1")) {
                    HomeActivity.this.goTranslate();
                } else if (str.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                    HomeActivity.this.goHistory();
                }else if (str.equals("5")) {
                    HomeActivity.this.openCameraIntent();
                }
            }

            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }


    public void openCameraIntent() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                this.photoFile = createImageFile();
            } catch (IOException unused) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            File file = this.photoFile;
            if (file != null) {

                intent.putExtra("output", FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file));
                startActivityForResult(intent, 100);
            }
        }
    }

    private File createImageFile() throws IOException {
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File createTempFile = File.createTempFile("IMG_" + format + "_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        this.imageFilePath = createTempFile.getAbsolutePath();
        return createTempFile;
    }
    public void goTranslate() {
        startActivity(new Intent(this, TextActivity.class));
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 203) {
            CropImage.ActivityResult activityResult = CropImage.getActivityResult(intent);
            if (i2 == -1) {
                try {
                    runTextRec(MediaStore.Images.Media.getBitmap(getContentResolver(), activityResult.getUri()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (i2 == 204) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
        if (i == 100) {
            Intent intent2 = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            File file = new File(this.imageFilePath);
            Uri fromFile = Uri.fromFile(file);
            intent2.setData(fromFile);
            sendBroadcast(intent2);
            if (file.length() > 0) {
                CropImage.activity(fromFile).start(this);
            }
        } else if (i == 101) {
            try {
                String[] strArr = {"_data"};
                Cursor query = getContentResolver().query(intent.getData(), strArr, (String) null, (String[]) null, (String) null);
                query.moveToFirst();
                String string = query.getString(query.getColumnIndex(strArr[0]));
                query.close();
                File file2 = new File(string);
                Uri fromFile2 = Uri.fromFile(file2);
                if (file2.length() > 0) {
                    CropImage.activity(fromFile2).start(this);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void runTextRec(Bitmap bitmap) {
        FirebaseVision.getInstance().getOnDeviceTextRecognizer().processImage(FirebaseVisionImage.fromBitmap(bitmap)).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @SuppressLint("WrongConstant")
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                if (firebaseVisionText.getText().length() <= 0) {
                    Toast.makeText(HomeActivity.this.getApplicationContext(), "No text Found", 0).show();
                    return;
                }
                Intent intent = new Intent(HomeActivity.this, TextActivity.class);
                intent.putExtra("text", firebaseVisionText.getText());
                HomeActivity.this.startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @SuppressLint("WrongConstant")
            public void onFailure(Exception exc) {
                Toast.makeText(HomeActivity.this.getApplicationContext(), "Unable to fetch text from image", 0).show();
            }
        });
    }


    @Override
    public void onRewardedVideoLoaded(boolean isPrecache) {
        AdsUtil.showVideoAds(this,false);
    }

    @Override
    public void onRewardedVideoFailedToLoad() {

    }

    @Override
    public void onRewardedVideoShown() {

    }

    @Override
    public void onRewardedVideoShowFailed() {

    }

    @Override
    public void onRewardedVideoFinished(double amount, String name) {

    }

    @Override
    public void onRewardedVideoClosed(boolean finished) {

    }

    @Override
    public void onRewardedVideoExpired() {

    }

    @Override
    public void onRewardedVideoClicked() {

    }
}
