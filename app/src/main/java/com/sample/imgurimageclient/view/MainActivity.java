package com.sample.imgurimageclient.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.imgurimageclient.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Added two fragment so if we want to display detail page in tablet
        //side by side to list view it will be easier
        if (findViewById(R.id.fragmentsContainer) != null) {

        // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            ImageListFragment imageListFragment = new ImageListFragment();

            // Add the fragment to the 'fragmentsContainer' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentsContainer, imageListFragment).commit();
        }
    }
}
