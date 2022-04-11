package com.example.drawactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.drawactivity.PaintView.colorLits;
import  static com.example.drawactivity.PaintView.current_brush;
import static com.example.drawactivity.PaintView.pathList;

public class MainActivity extends AppCompatActivity {
public static Path path = new Path();
public static Paint paint_brush = new Paint();
private Button pencil,eraser,redColor,yellowColor,greenColor,blueColor;
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
    }
    public void currentColor(int color){
        current_brush = color;
        path = new Path();
    }
}