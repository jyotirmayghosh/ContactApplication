package com.example.jyotirmay.contactapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jyotirmay.contactapplication.com.example.jyotirmay.contactapplication.datasource.ContactAdapter;
import com.example.jyotirmay.contactapplication.com.example.jyotirmay.contactapplication.datasource.DbHandler;

public class ContactDetailsActivity extends AppCompatActivity {

    ImageView backImageView, messageImageView, emailImageView, deleteImageView;
    TextView nameTextView, phoneTextView, emailTextView;
    ConstraintLayout callLayout, messageLayout, emailLayout;
    FloatingActionButton editActionButton;
    DbHandler handler;
    String id;
    public final static String ID="ID";
    public final static int REQUEST_CODE=100;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        backImageView=findViewById(R.id.imgBack);
        messageImageView=findViewById(R.id.imgMessage);
        emailImageView=findViewById(R.id.imgEmail);
        nameTextView=findViewById(R.id.tvName);
        phoneTextView=findViewById(R.id.tvPhone);
        emailTextView=findViewById(R.id.tvEmail);
        callLayout=findViewById(R.id.callLayout);
        messageLayout=findViewById(R.id.messageLayout);
        emailLayout=findViewById(R.id.emailLayout);
        editActionButton=findViewById(R.id.floatEdit);
        deleteImageView=findViewById(R.id.imgDelete);

        builder = new AlertDialog.Builder(ContactDetailsActivity.this);

        handler=new DbHandler(ContactDetailsActivity.this);

        Bundle getIntent=getIntent().getExtras();
        id=getIntent.getString(ContactAdapter.ID);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ContactDetailsActivity.this, EditContactActivity.class);
                intent.putExtra(ID, id);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(ContactDetailsActivity.this, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.pop_up, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.popDelete)
                        {
                            builder.setTitle("Delete Contact")
                                    .setMessage("Are you sure you want to delete this contact?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            handler.deleteContact(id);
                                            notifyAll();
                                            finish();
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                        return true;
                    }
                });
                popup.show();

            }
        });
        Cursor cursor=handler.readDetail(id);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(DbHandler.COL_NAME);
            int phoneIndex = cursor.getColumnIndex(DbHandler.COL_PHONE);
            int emailIndex = cursor.getColumnIndex(DbHandler.COL_EMAIL);
            while (cursor.moveToNext()) {
                String nameFromDb = cursor.getString(nameIndex);
                String phoneFromDb = cursor.getString(phoneIndex);
                String emailFromDb = cursor.getString(emailIndex);

                nameTextView.setText(nameFromDb);
                phoneTextView.setText(phoneFromDb);
                emailTextView.setText(emailFromDb);
            }
            cursor.close();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQUEST_CODE)
        {
            if (resultCode== Activity.RESULT_OK)
            {
                String id=data.getStringExtra(ID);
                Cursor cursor=handler.readDetail(id);
                if (cursor != null) {
                    int nameIndex = cursor.getColumnIndex(DbHandler.COL_NAME);
                    int phoneIndex = cursor.getColumnIndex(DbHandler.COL_PHONE);
                    int emailIndex = cursor.getColumnIndex(DbHandler.COL_EMAIL);
                    while (cursor.moveToNext()) {
                        String nameFromDb = cursor.getString(nameIndex);
                        String phoneFromDb = cursor.getString(phoneIndex);
                        String emailFromDb = cursor.getString(emailIndex);

                        nameTextView.setText(nameFromDb);
                        phoneTextView.setText(phoneFromDb);
                        emailTextView.setText(emailFromDb);
                    }
                    cursor.close();
                }
            }
            else if (requestCode==Activity.RESULT_CANCELED)
            {

            }
        }
    }
}
