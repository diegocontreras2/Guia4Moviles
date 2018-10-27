package com.example.desarrollo.guia4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

public class Principal extends AppCompatActivity {

    private int PICK_PHOTO1=3;
    private int PICK_PHOTO2=4;
    ImageView imageView1;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        imageView1 = findViewById(R.id.imgFondo1);
        imageView2 = findViewById(R.id.imgFondo2);

        imageView1.setImageDrawable(getResources().getDrawable(R.drawable.android_pic));
        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.android_pic));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.miAgregar1:
                agregarIMG(PICK_PHOTO1);
                break;
            case R.id.miAgregar2:
                agregarIMG(PICK_PHOTO1);
                break;
            case R.id.miEliminar:
                eliminarIMG();
                break;
                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO1 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
                imageView1.setImageBitmap(bmp);
            } catch (IOException e) {
                Toast.makeText(this,"Error Cargando imagen",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }else if(requestCode == PICK_PHOTO2 && resultCode == Activity.RESULT_OK){
            Uri selectedImage = data.getData();
            Bitmap bmp = null;
            try{
                bmp = getBitmapFromUri(selectedImage);
                imageView2.setImageBitmap(bmp);
            }catch (IOException e){
                Toast.makeText(this, "Error Cargando imagen", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private void eliminarIMG() {
        imageView1.setImageDrawable(null);
        imageView2.setImageDrawable(null);
    }


    private void agregarIMG(int pick) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,pick);
    }


}
