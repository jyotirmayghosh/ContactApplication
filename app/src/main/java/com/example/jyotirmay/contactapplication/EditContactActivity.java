package com.example.jyotirmay.contactapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jyotirmay.contactapplication.com.example.jyotirmay.contactapplication.datasource.ContactAdapter;
import com.example.jyotirmay.contactapplication.com.example.jyotirmay.contactapplication.datasource.DbHandler;

public class EditContactActivity extends AppCompatActivity {


    ImageView closeView;
    TextView saveView;
    EditText nameText, phoneText, emailText;
    public static String RESULT="RESULT";
    DbHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        closeView=findViewById(R.id.imgClose);
        saveView=findViewById(R.id.tvSave);

        nameText=findViewById(R.id.etName);
        emailText=findViewById(R.id.etEmail);
        phoneText=findViewById(R.id.etPhone);

        handler=new DbHandler(EditContactActivity.this);

        Bundle getIntent=getIntent().getExtras();
        final String id=getIntent.getString(ContactDetailsActivity.ID);

        Cursor cursor=handler.readDetail(id);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(DbHandler.COL_NAME);
            int phoneIndex = cursor.getColumnIndex(DbHandler.COL_PHONE);
            int emailIndex = cursor.getColumnIndex(DbHandler.COL_EMAIL);
            while (cursor.moveToNext()) {
                String nameFromDb = cursor.getString(nameIndex);
                String phoneFromDb = cursor.getString(phoneIndex);
                String emailFromDb = cursor.getString(emailIndex);

                nameText.setText(nameFromDb);
                phoneText.setText(phoneFromDb);
                emailText.setText(emailFromDb);
            }
            cursor.close();
        }

        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        saveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nameText.getText().toString();
                String email=emailText.getText().toString();
                String phone=phoneText.getText().toString();
                String result = null;
                if (name.isEmpty())
                {
                    result="No data saved...";
                }
                else {
                    boolean row = handler.updateDetail(id, name, phone, email);
                    if (row== true)
                        result="Successfully saved...";
                }

                Toast.makeText(EditContactActivity.this, result, Toast.LENGTH_SHORT).show();
                Intent returnIntent=new Intent();
                returnIntent.putExtra(ContactDetailsActivity.ID, id);
                setResult(Activity.RESULT_OK, returnIntent);
                notifyAll();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
