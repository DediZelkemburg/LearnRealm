package com.example.dedi.learnrealm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dedi.learnrealm.Models.Adapter;
import com.example.dedi.learnrealm.Models.PojoDB.Users;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Ada 2 cara dalam menampilkan data pada realm
 * 1. RealmQuery  query ( query = realms.where(Users.class); RealmResults = query ..... )
 * 2. Langsung Realm realm ( RealmResults = realm.where(Users.class).findAll(); )
 **/

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Declare Realm
     **/
    RealmResults<Users> results_;
    List<Users> user = new ArrayList<>();
    Realm realm;
    RealmQuery<Users> query;
    /**
     * END Declare Realm
     **/

    ListView listView;
    Adapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //((ViewStub) findViewById(R.id.vs_add_data)).setVisibility(View.VISIBLE);
        ((ViewStub) findViewById(R.id.list_data)).setVisibility(View.VISIBLE);
        listView = (ListView) findViewById(R.id.list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /** Realm insert and get data With callback **/
        realm = Realm.getInstance(getApplicationContext()); // declare realm first time

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("waiting");
        progressDialog.setMax(10);
        progressDialog.show();
        realm.executeTransaction(new Realm.Transaction() {
            public void execute(Realm realms) { //not UI

                /** Realm for insert **/
                /*for (int i=0; i<500; i++) {
                    Users us = realms.createObject(Users.class);
                    us.setId(""+i);
                    us.setPass("taratas "+i);
                    us.setName("turutut "+i);
                }*/
                /** END Realm for insert **/

                /** Realm for get all data/delete use query **/
                query = realms.where(Users.class);
                //RealmResults<Users> results = query.findAll();
                results_ = query.findAll();
                for (int i = 0; i < results_.size(); i++) {
                    Users us = new Users();
                    us.setName(results_.get(i).getId());
                    us.setPass(results_.get(i).getName());
                    user.add(us);
                }
                adapter = new Adapter(MainActivity.this, user);
                /** END Realm for get all data/delete use query **/

            }
        }, new Realm.Transaction.Callback() {
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Successfully ", Toast.LENGTH_SHORT).show();
                listView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        /** END Realm insert and get data With callback **/

        /**
         * NOTES
         * REALM use executeTransaction once because automation realm.close()
         * **/


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Toast.makeText(getApplicationContext(), user.get(position).getName() + "", Toast.LENGTH_SHORT).show();

                /** without executeTransaction **/
                realm.beginTransaction();
                results_ = realm.where(Users.class).findAll();
                results_.remove(position);
                realm.commitTransaction();
                /** END without executeTransaction **/

                /*realm.executeTransaction(new Realm.Transaction() {
                    public void execute(Realm rea) {

                        *//** Realm for get all/delete data direction **//*
                        results_ = rea.where(Users.class).findAll();
                        results_.remove(position);
                        *//** END Realm for get all/delete data direction **//*

                    }
                }, new Realm.Transaction.Callback() {
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "YUhuu ", Toast.LENGTH_SHORT).show();
                    }

                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/

                /*results_.remove(position);
                results_.removeLast();*/


            }
        });

        /** Declare objects  **/
        /*Users user = new Users();
        user.setName("Dedi19d");
        user.setPass("1234");*/
        /** END declare objects  **/

        /** Declare first REALM for insert **/
        /*realm = Realm.getInstance(getApplicationContext());
        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();*/
        /** END declare first REALM for insert **/

        /** execute last time  **/
        Thread thread = new Thread(new Runnable() {
            public void run() {
                /*Realm realm1 = Realm.getInstance(getApplicationContext());
                Users users = realm1.where(Users.class).equalTo("name", "Dedi19").findFirst();
                Log.e("data ", "" + users.getName());
                realm1.close();*/
            }
        });
        thread.start();
        /** END execute last time  **/

        try {

            /** Get all data users without AsyncTask **/
            /*RealmQuery<Users> query = realm.where(Users.class);
            RealmResults<Users> results = query.findAll();
            for (int i = 0; i < results.size(); i++) {
                Log.e("data " + i, "" + results.get(i).getName());
            }*/
            /** END get all data users without AsyncTask **/

        } catch (Exception e) {
            Log.e("wtf " + e.getMessage(), "" + e.toString());
        }


        /**  REALM for UPDATE **/

        Users user = new Users();
        user.setId("3");
        user.setName("Dedi Dorez");

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();

        /** END REALM for UPDATE **/


    }

    /**
     * Realm get data With AsyncTask declare 1
     **/
    private RealmChangeListener callback = new RealmChangeListener() {
        public void onChange() {
            for (int i = 0; i < results_.size(); i++) {
                Log.e("data " + i, "" + results_.get(i).getName());
            }
        }
    };

    /**
     * Realm get data With AsyncTask declare 1
     **/

    protected void onStart() {
        super.onStart();

        /** Realm get data With AsyncTask declare 2**/
        /*results = realm.where(Users.class).findAllAsync();
        results.addChangeListener(callback);*/
        /** END Realm get data With AsyncTask declare 2**/
    }

    public void onClickSave(View v) {

    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camara) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
