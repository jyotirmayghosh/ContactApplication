package com.example.jyotirmay.contactapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jyotirmay.contactapplication.com.example.jyotirmay.contactapplication.datasource.DbHandler;

public class AddContactActivity extends AppCompatActivity {

    ImageView closeView;
    TextView saveView;
    EditText nameText, phoneText, emailText;

    public static String RESULT="RESULT";

    DbHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        closeView=findViewById(R.id.imgClose);
        saveView=findViewById(R.id.tvSave);

        nameText=findViewById(R.id.etName);
        emailText=findViewById(R.id.etEmail);
        phoneText=findViewById(R.id.etPhone);

        handler=new DbHandler(AddContactActivity.this);

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
                    boolean row = handler.insert(name, phone, email);
                    if (row)
                        result="Successfully saved...";
                }

                Toast.makeText(AddContactActivity.this, result, Toast.LENGTH_SHORT).show();
                Intent returnIntent=new Intent();
                returnIntent.putExtra(RESULT, result);
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
