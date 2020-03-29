package com.example.goggolman;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.example.goggolman.Interfaces.FragmentListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;

    MenuItem menuSetting;
    Toolbar toolbar;

    DBHelper dbHelper;

    //initialize fragments
    BookmarkFragment bookmarkFragment;
    DictionaryFragment dictionaryFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dbHelper = new DBHelper(this);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_bookmark, R.id.nav_rate, R.id.nav_about,
//                R.id.nav_help, R.id.nav_share)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

  
        dictionaryFragment = new DictionaryFragment();
        bookmarkFragment = BookmarkFragment.getNewInstance(dbHelper);
        goToFraments(dictionaryFragment, true);

        dictionaryFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                String id = Global.getState(MainActivity.this, "dic_type");
                int dicType = id == null? R.id.action_bwall_english : Integer.valueOf(id);
                goToFraments(DetailFragment.getNewInstance(value, dbHelper, dicType), false);
            }
        });

        bookmarkFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                String id = Global.getState(MainActivity.this, "dic_type");
                int dicType = id == null? R.id.action_bwall_english : Integer.valueOf(id);
                goToFraments(DetailFragment.getNewInstance(value, dbHelper, dicType), false);

            }
        });

        EditText editText_search = findViewById(R.id.edit_search);
        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dictionaryFragment.filterValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuSetting = menu.findItem(R.id.action_settings);

        String id = Global.getState(this, "dic_type");
        if (id != null){
            onOptionsItemSelected(menu.findItem(Integer.valueOf(id)));
        }else{
            ArrayList<String> source = dbHelper.getWord(R.id.action_bwall_english);
            dictionaryFragment.resetDataSource(source);
        }
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (R.id.action_settings == id)return true;

        Global.saveState(this, "dic_type", String.valueOf(id));


        ArrayList<String> source = dbHelper.getWord(id);

        if (id == R.id.action_eng_bwall){
            dictionaryFragment.resetDataSource(source);
            menuSetting.setIcon(getDrawable(R.drawable.e));
        }else if (id == R.id.action_bwall_english){
            dictionaryFragment.resetDataSource(source);
            menuSetting.setIcon(getDrawable(R.drawable.b));
        }else if (id == R.id.action_eng_bwalll){
            dictionaryFragment.resetDataSource(source);
            menuSetting.setIcon(getDrawable(R.drawable.b));
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_bookmark){
            String activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
            if (!activeFragment.equals(BookmarkFragment.class.getSimpleName())){
                goToFraments(bookmarkFragment, false);
            }

            //Toast.makeText(this, "Let us bookmark it!", Toast.LENGTH_SHORT).show();

        }else if (id == R.id.nav_home){
            Toast.makeText(this, "We are home!", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_rate){
            Toast.makeText(this, "Thumbs Up", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_help){
            Toast.makeText(this, "How may we help you?", Toast.LENGTH_SHORT).show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Method to navigate to fragments
    void goToFraments(Fragment fragment, boolean isTop){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);


        if (!isTop){
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
        if (activeFragment.equals(BookmarkFragment.class.getSimpleName())){
            menuSetting.setVisible(false);
            toolbar.findViewById(R.id.edit_search).setVisibility(View.GONE);
            toolbar.setTitle("Bookmark");
        }else {
            menuSetting.setVisible(true);
            toolbar.findViewById(R.id.edit_search).setVisibility(View.VISIBLE);
            toolbar.setTitle("");
        }
        return true;
    }
}
