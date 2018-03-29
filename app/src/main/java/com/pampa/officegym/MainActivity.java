package com.pampa.officegym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.pampa.officegym.Model.Utils;

public class MainActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
    public static final String YouTubeKey  = "AIzaSyArH0FkdjVqvNSb307-Dj9JtO9Hv7QoqH4";
    private YouTubePlayerFragment playerFragment;
    private YouTubePlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_player_fragment);
        playerFragment.initialize(YouTubeKey, this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ImageView imagenIcono1 = (ImageView) findViewById(R.id.imageView1);
        ImageView imagenIcono2 = (ImageView) findViewById(R.id.imageView2);
        ImageView imagenIcono3 = (ImageView) findViewById(R.id.imageView3);
        ImageView imagenIcono4 = (ImageView) findViewById(R.id.imageView4);
        ImageView imagenIcono5 = (ImageView) findViewById(R.id.imageView5);
        ImageView imagenIcono6 = (ImageView) findViewById(R.id.imageView6);
        ImageView imagenIcono7 = (ImageView) findViewById(R.id.imageView7);

        imagenIcono1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ContenidoMain.class);
                intent.putExtra("categoria","Yoga");
                intent.putExtra("icono", "icono1");
                startActivity(intent);
            }
        });
        imagenIcono2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ContenidoMain.class);
                intent.putExtra("categoria","Visio");
                intent.putExtra("icono", "icono2");
                startActivity(intent);
            }
        });
        imagenIcono3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ContenidoMain.class);
                intent.putExtra("categoria","Espalda");
                intent.putExtra("icono", "icono3");
                startActivity(intent);
            }
        });
        imagenIcono4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ContenidoMain.class);
                intent.putExtra("categoria","Stretching");
                intent.putExtra("icono", "icono4");
                startActivity(intent);
            }
        });
        imagenIcono5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ContenidoMain.class);
                intent.putExtra("categoria","Funcional");
                intent.putExtra("icono", "icono5");
                startActivity(intent);
            }
        });
        imagenIcono6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ContenidoMain.class);
                intent.putExtra("categoria","PNL");
                intent.putExtra("icono", "icono6");
                startActivity(intent);
            }
        });
        imagenIcono7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ContenidoMain.class);
                intent.putExtra("categoria","Auto");
                intent.putExtra("icono", "icono7");
                startActivity(intent);
            }
        });





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_settings:
                Log.i("ActionBar", "Nuevo!");
                return true;
            case R.id.action_help:
                Log.i("ActionBar", "Guardar!");;
                return true;
            case R.id.action_about:
                Log.i("ActionBar", "Settings!");;
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        mPlayer = player;


        //Enables automatic control of orientation
        mPlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);

        //Show full screen in landscape mode always
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);

        //System controls will appear automatically
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);

        if (!wasRestored) {
            //player.cueVideo("9rLZYyMbJic");
            //mPlayer.loadVideo("P-qknEkZUjo");
            mPlayer.loadVideo(Utils.getVideoDelDia());
        }
        else
        {
            mPlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        mPlayer = null;
    }



}
