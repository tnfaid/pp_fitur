package titik.com.pantaupadi.Activity;


import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import titik.com.pantaupadi.R;

import static titik.com.pantaupadi.Activity.LoginActivity.session_status;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Dialog myDialog;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    Fragment fragment;

    SharedPreferences sharedPreferences;
    public static final String TAG_FIRST_NAME = "first_name";
    public static final String TAG_LAST_NAME = "last_name";
    public final static String TAG_EMAIL = "email";
    public static final String TAG_ID = "id";
    public static final String TAG_MOBILE = "mobile";
    public static final String TAG_COUNTRY = "country";

    Boolean session = false;
    String id;
    String first_name, first_nameIntent;
    String last_name, last_nameIntent;
    String mobile, mobileIntent;
    String country, countryIntent;
    String email, emailIntent;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDialog = new Dialog(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        sharedPreferences = getSharedPreferences(LoginActivity.my_shared_preferences, MODE_PRIVATE);
        session = sharedPreferences.getBoolean(session_status, false);
        id = sharedPreferences.getString(TAG_ID, null);
        first_name = sharedPreferences.getString(TAG_FIRST_NAME, null);
        last_name = sharedPreferences.getString(TAG_LAST_NAME, null);
        email = sharedPreferences.getString(TAG_EMAIL, null);
        country = sharedPreferences.getString(TAG_COUNTRY, null);
        mobile = sharedPreferences.getString(TAG_MOBILE, null);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         * if(isLogin)
         *         {
         *             navigationView.getMenu().clear();
         *             navigationView.inflateMenu(R.menu.activity_main_drawer);
         *         } else {
         *             navigationView.getMenu().clear();
         *             navigationView.inflateMenu(R.menu.activity_main_drawer_petani);
         *         }
         *
         *
         *         // tampilan default awal ketika aplikasii dijalankan
         *         if (savedInstanceState == null) {
         *             fragment = new Scan();
         *             callFragment(fragment);
         *         }
         */


        if(!session){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer_petani);
        }else{
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }
        if (savedInstanceState == null) {
            fragment = new Scan();
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

//    @SuppressWarnings("StatementWithEmptyBody")
//

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(!session){
            if (id == R.id.nav_scan) {
                fragment = new Scan();
                callFragment(fragment);
            } else if (id == R.id.nav_beranda) {
                fragment = new Beranda();
                callFragment(fragment);
            } else if (id == R.id.nav_login) {
                intent = new Intent (MainActivity.this, LoginActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_tentang) {
                fragment = new Tentang();
                callFragment(fragment);
            }
        } else {
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
        }
        // Untuk memanggil layout dari menu yang dipilih


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

