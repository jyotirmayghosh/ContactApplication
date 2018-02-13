package com.example.jyotirmay.contactapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jyotirmay.contactapplication.com.example.jyotirmay.contactapplication.datasource.ContactAdapter;
import com.example.jyotirmay.contactapplication.com.example.jyotirmay.contactapplication.datasource.DataBean;
import com.example.jyotirmay.contactapplication.com.example.jyotirmay.contactapplication.datasource.DbHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int ADD_REQUEST_CODE=001;
    RecyclerView recyclerView;
    DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);

            }
        });

        dbHandler=new DbHandler(MainActivity.this);

        recyclerView=findViewById(R.id.recyclerView);

        Cursor contactCursor=dbHandler.read();
        List<DataBean> allDataBean=new ArrayList<>();
        if (contactCursor!=null)
        {
            int idIndex=contactCursor.getColumnIndex(DbHandler.COL_ID);
            int nameIndex=contactCursor.getColumnIndex(DbHandler.COL_NAME);
            int phoneIndex=contactCursor.getColumnIndex(DbHandler.COL_PHONE);
            int emailIndex=contactCursor.getColumnIndex(DbHandler.COL_EMAIL);
            while (contactCursor.moveToNext())
            {
                String idFromDb = contactCursor.getString(idIndex);
                String nameFromDb = contactCursor.getString(nameIndex);
                String phoneFromDb = contactCursor.getString(phoneIndex);
                String emailFromDb = contactCursor.getString(emailIndex);

                DataBean data = new DataBean();
                if (!nameFromDb.isEmpty()) {
                    data.setC_id(idFromDb);
                    data.setName(nameFromDb);
                    data.setEmail(emailFromDb);
                    data.setPhone(phoneFromDb);
                    allDataBean.add(data);
                }
            }
            contactCursor.close();
        }
        ContactAdapter adaptor=new ContactAdapter(allDataBean, getApplicationContext());
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==ADD_REQUEST_CODE)
        {
            if (resultCode== Activity.RESULT_OK)
            {
                String result=data.getStringExtra(AddContactActivity.RESULT);
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
            else if (requestCode==Activity.RESULT_CANCELED)
            {

            }
        }
    }
}
