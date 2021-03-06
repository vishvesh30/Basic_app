package com.example.vishvraj.basic_app;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private ImageView ivImage;
    private String userChoosenTask;
    private String spinnerdata[];
    private static final int SELECT_PICTURE = 0;
    private ImageView imageview;
    DatabaseHelper mydb;
    EditText editname,editsurname,editemail,editpassword,editphnno;
    Spinner citynames;
    RadioButton male,female;
    Button btnAddData;
    CheckBox checkBox2,checkBox3,checkBox4,checkBox5;
    File destination;
    ImageView imgview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(R.mipmap.icon);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        spinnerdata = new String[]{
                "Ahmedabad", "Surat", "Gandhinagar", "Vadodara", "Rajkot"
        };
        ivImage = (ImageView) findViewById(R.id.imageView);
        citynames = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adpter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerdata);
        citynames.setAdapter(adpter);
        editname=(EditText)findViewById(R.id.editName);
        editsurname=(EditText)findViewById(R.id.editSurname);
        editemail=(EditText)findViewById(R.id.editEmail);
        editpassword=(EditText)findViewById(R.id.editPassword);
        editphnno=(EditText)findViewById(R.id.editPhone);
        male=(RadioButton)findViewById(R.id.radioButtonMale);
        female=(RadioButton)findViewById(R.id.radioButtonFemale);
        checkBox2=(CheckBox)findViewById(R.id.checkBox2);
        checkBox3=(CheckBox)findViewById(R.id.checkBox3);
        checkBox4=(CheckBox)findViewById(R.id.checkBox4);
        checkBox5=(CheckBox)findViewById(R.id.checkBox5);
        imgview=(ImageView)findViewById(R.id.imageView);
        btnAddData=(Button)findViewById(R.id.buttonsbm);
        mydb = new DatabaseHelper(this);
        addData();
    }
    public void addData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,surname,email,password,phnno,gender,city,photo;
                StringBuffer hobby=new StringBuffer("");
                name=editname.getText().toString();
                surname=editsurname.getText().toString();
                email=editemail.getText().toString();
                password=editpassword.getText().toString();
                phnno=editphnno.getText().toString();

                if(male.isChecked()){
                    gender=male.getText().toString();
                }
                else {
                    gender = female.getText().toString();
                }
                city=citynames.getSelectedItem().toString();

                if(checkBox2.isChecked()){
                    hobby.append(checkBox2.getText().toString()+",");
                }

                if(checkBox3.isChecked()){
                    hobby.append(checkBox3.getText().toString()+",");
                }

                if(checkBox4.isChecked()){
                    hobby.append(checkBox4.getText().toString()+",");
                }

                if(checkBox5.isChecked()){
                    hobby.append(checkBox5.getText().toString()+",");
                }

                photo="";
                boolean isInserted=mydb.insertData(name,surname,email,password,phnno,gender,city,photo,hobby.toString());
                if(isInserted){

                    Toast.makeText(MainActivity.this,"Data inserted",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Data not inserted",Toast.LENGTH_LONG).show();
                }
                Intent intent=new Intent("com.example.vishvraj.basic_app.showdata");
                startActivity(intent);

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void changecolor() {

        setTheme(R.style.RedTheme);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                Toast.makeText(getApplicationContext(), "Info selected", Toast.LENGTH_SHORT).show();
                changecolor();
                break;
            case R.id.theme:
                Toast.makeText(getApplicationContext(), "Theme selected", Toast.LENGTH_SHORT).show();
                changecolor();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    public void selectImage(View v) {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(MainActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
        Uri selected=data.getData();

    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

         destination = new File(Environment.DIRECTORY_PICTURES,
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivImage.setImageBitmap(bm);
    }


    /*private Bitmap getPath(Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
         cursor.close();
        // Convert file path into bitmap image using below line.

      //  Bitmap bitmap = BitmapFactory.decodeFile(filePath);


       // return bitmap;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = getPath(data.getData());
            InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(bitmap);
        }
    }

    public void selectImage(View v) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }*/
}
