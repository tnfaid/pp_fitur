package titik.com.pantaupadi.Activity;


import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import titik.com.pantaupadi.Fragment.Beranda;
import titik.com.pantaupadi.Fragment.Profil;
import titik.com.pantaupadi.Fragment.Scan;
import titik.com.pantaupadi.Fragment.Tambah;
import titik.com.pantaupadi.Fragment.Tentang;
import titik.com.pantaupadi.Activity.LoginActivity;
import titik.com.pantaupadi.R;

import static titik.com.pantaupadi.Activity.LoginActivity.session_status;

public class MainPeneliti extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    Fragment fragment = null;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferencesAdmin;

    Boolean session = false;
    Boolean sessionAdmin = false;
    int id;
    String first_name, first_nameIntent;
    String last_name, last_nameIntent;
    String email, emailIntent;
    String mobile, mobileIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Dialog myDialog = new Dialog(this);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        // tampilan default awal ketika aplikasii dijalankan
        if (savedInstanceState == null) {
            fragment = new Tambah();
            callFragment(fragment);
        }


    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Toast.makeText(getApplicationContext(), "Action Settings", Toast.LENGTH_SHORT).show();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        // Untuk memanggil layout dari menu yang dipilih
        if (id == R.id.nav_scan) {
            fragment = new Scan();
            callFragment(fragment);
        } else if (id == R.id.nav_beranda) {
            fragment = new Beranda();
            callFragment(fragment);
        } else if (id == R.id.nav_tambah) {
            fragment = new Tambah();
            callFragment(fragment);
        } else if (id == R.id.nav_profile) {
            fragment = new Profil();
            callFragment(fragment);
        } else if (id == R.id.nav_tentang) {
            fragment = new Tentang();
            callFragment(fragment);
        }



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // untuk mengganti isi kontainer menu yang dipiih
    private void callFragment(Fragment fragment) {
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }
}
