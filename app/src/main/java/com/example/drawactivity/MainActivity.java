package com.example.drawactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.drawactivity.PaintView.colorLits;
import  static com.example.drawactivity.PaintView.current_brush;
import static com.example.drawactivity.PaintView.pathList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
public static Path path = new Path();
public static Paint paint_brush = new Paint();
private Button pencil,eraser,redColor,yellowColor,greenColor,blueColor,btnSave;
private int STORAGE_PERMISSION_CODE = 1;
private ConstraintLayout paintView;
Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        pencil = findViewById(R.id.pencil);
        eraser = findViewById(R.id.eraser);
        redColor = findViewById(R.id.redColor);
        yellowColor = findViewById(R.id.yellowColor);
        greenColor = findViewById(R.id.greenColor);
        blueColor = findViewById(R.id.blueColor);
        btnSave = findViewById(R.id.btnSave);
        paintView = findViewById(R.id.constraintLayout1);
        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                paint_brush.setColor(Color.BLACK);
                currentColor(paint_brush.getColor());
            }
        });
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               pathList.clear();
                colorLits.clear();
                path.reset();
               // Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        redColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint_brush.setColor(Color.RED);
                currentColor(paint_brush.getColor());
               // Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        yellowColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint_brush.setColor(Color.YELLOW);
                currentColor(paint_brush.getColor());
                //Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        greenColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint_brush.setColor(Color.GREEN);
                currentColor(paint_brush.getColor());
            }
        });

        blueColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint_brush.setColor(Color.BLUE);
                currentColor(paint_brush.getColor());
            }
        });
        btnSave.setOnClickListener(view -> {
            saveImage();
        });
    }

    private void saveImage() {
        btnSave.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                Bitmap bitmap = Bitmap.createBitmap(paintView.getWidth(),paintView.getHeight(),Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                paintView.draw(canvas);
                OutputStream ops;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
                    ContentResolver resolver = getContentResolver();
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.MediaColumns.DISPLAY_NAME,System.currentTimeMillis() + ".png");
                    values.put(MediaStore.MediaColumns.MIME_TYPE,"image/png");
                    values.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES);
                    Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                    Toast.makeText(this, "Image saved!", Toast.LENGTH_SHORT).show();
                    try {
                        ops = resolver.openOutputStream(Objects.requireNonNull(imageUri));
                        {
                            bitmap.compress(Bitmap.CompressFormat.PNG,100,ops);
                            ops.flush();
                            ops.close();
                        }
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                else {
                    FileOutputStream outputStream = null;
                    File sdCard = Environment.getExternalStorageDirectory();
                    File directory = new File(sdCard.getAbsoluteFile() + "/paint");
                    directory.mkdir();
                    String filename = String.format("%d.jpg",System.currentTimeMillis());
                    File outFile = new File(directory,filename);
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                    try {
                        outputStream = new FileOutputStream(outFile);
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                        outputStream.flush();
                        outputStream.close();
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(Uri.fromFile(outFile));
                        sendBroadcast(intent);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }else {
                requestStoragePermission();
            }
        });
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("this permission is needed")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           ActivityCompat.requestPermissions((MainActivity.this), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }
        else {
            ActivityCompat.requestPermissions((MainActivity.this), new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }

    public void currentColor(int color){
        current_brush = color;
        path = new Path();
    }
}