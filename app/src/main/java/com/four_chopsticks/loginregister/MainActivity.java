package com.four_chopsticks.loginregister;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.four_chopsticks.loginregister.Fragments.ExploreFragment;
import com.four_chopsticks.loginregister.Fragments.FeedFragment;
import com.four_chopsticks.loginregister.Fragments.MyNetworkFragment;
import com.four_chopsticks.loginregister.Fragments.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference mRef;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase instance
        auth=FirebaseAuth.getInstance();
        mRef= FirebaseDatabase.getInstance().getReference();

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //default fragment opens initialized when
        toolbar.setTitle("Feed");
        loadFragment(new FeedFragment());
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {

                case R.id.navigation_profile:
                    toolbar.setTitle("Profile");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_explore:
                    toolbar.setTitle("Explore");
                    fragment = new ExploreFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_feed:
                    toolbar.setTitle("Feed");
                    fragment = new FeedFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_my_network:
                    toolbar.setTitle("My Network");
                    fragment = new MyNetworkFragment();
                    loadFragment(fragment);
                    return true;

                default:
                    return false;
            }
        }
    };
    private void loadFragment(Fragment fragment){
        //load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
        //activity is stored at stack
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.menu_log_out:
                //USED TO SIGN OUT
                auth.signOut();
                startActivity(new Intent(MainActivity.this,SignInActivity.class));
                finish();
                return true;

            case R.id.menu_create_network:
                //network details
                startActivity(new Intent(MainActivity.this,Networks.class));
                return true;

                default:
                    return false;
                }
    }

}
