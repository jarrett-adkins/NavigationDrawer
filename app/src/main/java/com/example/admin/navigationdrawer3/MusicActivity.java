package com.example.admin.navigationdrawer3;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MusicActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    private Button button;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private int intentFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        button = (Button) findViewById( R.id.btnPlayMusic );

        //set up the navigation drawer
        drawerLayout = (DrawerLayout) findViewById( R.id.drDrawerLayout );
        actionBarDrawerToggle = new ActionBarDrawerToggle( this, drawerLayout, R.string.drawer_open, R.string.drawer_close );
        drawerLayout.addDrawerListener( actionBarDrawerToggle );
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

    public void playMusic(View view) {
        Intent playMusicIntent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
        if ( playMusicIntent.resolveActivity(getPackageManager()) != null ) {
            startActivityForResult( playMusicIntent, REQUEST_CODE );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //called when action bar button is clicked.

        switch ( item.getItemId() ) {
            case R.id.backArrow:
                onBackPressed();
                return true;
            case R.id.green:
                intentFlag = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
                Toast.makeText( this, "Intent flag set: REORDER_TO_FRONT", Toast.LENGTH_LONG ).show();
                return true;
            case R.id.red:
                intentFlag = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;
                Toast.makeText( this, "Intent flag set: EXCLUDE_FROM_RECENTS", Toast.LENGTH_LONG ).show();
                return true;
            case R.id.blue:
                intentFlag = Intent.FLAG_ACTIVITY_NEW_TASK;
                Toast.makeText( this, "Intent flag set: ACTIVITY_NEW_TASK", Toast.LENGTH_LONG ).show();
                return true;
            default:
                //this makes the hamburger work
                if( actionBarDrawerToggle.onOptionsItemSelected( item ) )
                    return true;
                return super.onOptionsItemSelected( item );
        }
    }

    @Override
    public void onBackPressed() {
        //called when the phone's back button is pressed.

        //check if the drawer is open; if so, close it.
        if (drawerLayout.isDrawerOpen( GravityCompat.START )) {
            drawerLayout.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    public void onNavigationItemSelected( MenuItem item ) {
        //called by the items of the navigation drawer.
        switch ( item.getItemId() ) {
            case R.id.nav_home:
                item.setChecked( false );
                Intent intent = new Intent( this, MainActivity.class );
                intent.setFlags( intentFlag );
                startActivity( intent );
                break;
            case R.id.nav_camera:
                //item.setChecked( false );
                Intent cameraIntent = new Intent( this, CameraActivity.class );
                cameraIntent.setFlags( intentFlag );
                startActivity( cameraIntent );
                break;
            case R.id.nav_gallery:
                item.setChecked( false );
                Intent galleryIntent = new Intent( this, GalleryActivity.class );
                galleryIntent.setFlags( intentFlag );
                startActivity( galleryIntent );
                break;
            case R.id.nav_music:
                item.setChecked( false );
                Intent musicIntent = new Intent( this, MusicActivity.class );
                musicIntent.setFlags( intentFlag );
                startActivity( musicIntent );
                break;
        }

        drawerLayout.closeDrawers();
    }
}
