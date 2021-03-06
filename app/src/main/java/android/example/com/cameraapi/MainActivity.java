package android.example.com.cameraapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
private ImageView mImageView;
private static final int IMAGE_REQUEST = 1;
String currentImagePath = null;

private File getImageFile() throws IOException {
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
             Log.i("timeStamp", timeStamp);
    String imageName = "jpg_"+ timeStamp+"_"; //image name
             Log.i("imageName", imageName);
    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
             Log.i("storageDir", storageDir.toString());

    //create image file
    File imageFile = File.createTempFile(imageName,".jpg",storageDir);
    currentImagePath =  imageFile.getAbsolutePath();
            Log.i("currentImagePath", currentImagePath.toString());
             Log.i("Image File: ", imageFile.toString());

    return imageFile;





}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView)findViewById(R.id.imageView);

    }

    public void takePicture(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) !=null){
            File imageFile = null;
            Log.i("1", "imageFile null");
            try {
                imageFile = getImageFile();
                Log.i("2", "imageFile created");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(imageFile != null){
                Log.i("3", "imageFile is not null");
                Uri imageUri = FileProvider.getUriForFile(this,"com.example.android.fileprovider",imageFile);
                Log.i("4", "imageUri created");
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                Log.i("5", "cameraIntent put extra");
                startActivityForResult(cameraIntent,IMAGE_REQUEST);
                Log.i("6", "activity started");
            }


        }

    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK){

      // Bitmap imageBitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("image_path"));

         Bundle extra = data.getExtras();
        Bitmap imageBitmap =  (Bitmap) extra.get("data");
        mImageView.setImageBitmap(imageBitmap);


    }
}

 */

    public void showImage(View view) {
     String name = "sov";
    Intent intent = new Intent(this, displayImage.class);
    intent.putExtra("image_path", currentImagePath);
    startActivity(intent);


    }
}
