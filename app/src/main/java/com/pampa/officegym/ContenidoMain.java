package com.pampa.officegym;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pampa.officegym.Adapter.PagerAdapter;
import com.pampa.officegym.Fragments.TextFragment;
import com.pampa.officegym.Fragments.VideoFragment;

public class ContenidoMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido_main);

        TextView textoCategoria = findViewById(R.id.textoCategoria);
        ImageView imagenIcono = findViewById(R.id.iconoCategoria);
        Bundle intentData = getIntent().getExtras();
        String intentImg = intentData.getString("icono");
        String intentTxt = intentData.getString("categoria");
        textoCategoria.setText(intentTxt);
        Resources res = getResources();
        int resID = res.getIdentifier(intentImg , "drawable", getPackageName());
        imagenIcono.setImageResource(resID);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Videos"));
        tabLayout.addTab(tabLayout.newTab().setText("Imagenes"));
        tabLayout.addTab(tabLayout.newTab().setText("Textos"));

        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_ondemand_video_white_24dp);


        Bundle bundle = new Bundle();
        bundle.putString("categoria", intentTxt);
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setArguments(bundle);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adaptador = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adaptador);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}
