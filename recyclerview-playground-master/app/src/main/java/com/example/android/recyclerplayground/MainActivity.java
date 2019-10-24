package com.example.android.recyclerplayground;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.recyclerplayground.adapters.SimpleAdapter;
import com.example.android.recyclerplayground.fragments.FixedTwoWayFragment;
import com.example.android.recyclerplayground.fragments.HorizontalFragment;
import com.example.android.recyclerplayground.fragments.NavigationDrawerFragment;
import com.example.android.recyclerplayground.fragments.RecyclerFragment;
import com.example.android.recyclerplayground.fragments.VerticalFragment;
import com.example.android.recyclerplayground.fragments.VerticalGridFragment;
import com.example.android.recyclerplayground.fragments.VerticalStaggeredGridFragment;
import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;


public class MainActivity extends AppCompatActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    RecyclerFragment fragmentM;
    SimpleAdapter adapter;
    SearchResult deviceT;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        SearchRequest request = null;

        switch (position) {
            case 0:
                fragmentM =  VerticalFragment.newInstance();
                request = new SearchRequest.Builder()
                        // .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                        .searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                        .build();
                title =   getString(R.string.title_section1);
                break;
            case 1:
                fragmentM = HorizontalFragment.newInstance();
                request = new SearchRequest.Builder()
                    // .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                    .searchBluetoothLeDevice(2000).build();      // 再扫BLE设备2s
                title =   getString(R.string.title_section2);
                break;
            case 2:
                fragmentM = VerticalGridFragment.newInstance();

                break;
            case 3:
                fragmentM =VerticalStaggeredGridFragment.newInstance();

                break;
            case 4:
                fragmentM = FixedTwoWayFragment.newInstance();

                break;
            default:
                fragmentM = null;
                //Do nothing
                break;
        }
        ft.replace(R.id.container, fragmentM);

        ft.commit();
       // fragmentM.mAdapter.addItem(new SimpleAdapter.GameItem( "device.getAddress()",
       //         "beacon.toString()",10,220));
        BluetoothClient mClient = new BluetoothClient(this);

        if ( fragmentM.mAdapter != null) fragmentM.mAdapter.setItemCount(0);
        mClient.search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {

            }

            @Override
            public void onDeviceFounded(SearchResult device) {
                deviceT =device;

               // BluetoothLog.v(String.format("beacon for %s\n%s", device.getAddress(), beacon.toString()));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ActionBar actionBar = getSupportActionBar();

                        actionBar.setTitle(title );
                        Beacon beacon = new Beacon(deviceT.scanRecord);

                         fragmentM.mAdapter.addItem(new SimpleAdapter.GameItem( deviceT.getAddress(),
                                beacon.toString(),0,0));

                    }
                });


           }

            @Override
            public void onSearchStopped() {

            }

            @Override
            public void onSearchCanceled() {

            }
        });
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

}
