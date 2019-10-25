package com.example.android.recyclerplayground;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

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
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.model.BleGattCharacter;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.model.BleGattService;
import com.inuker.bluetooth.library.receiver.listener.BluetoothBondListener;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;

import java.util.List;
import java.util.UUID;

import static com.inuker.bluetooth.library.Code.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;


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
    BluetoothClient mClient;
    private final BluetoothBondListener mBluetoothBondListener = new BluetoothBondListener() {
        @Override
        public void onBondStateChanged(String mac, int bondState) {
            // bondState = Constants.BOND_NONE, BOND_BONDING, BOND_BONDED
        }
    };


    private final BleConnectStatusListener mBleConnectStatusListener = new BleConnectStatusListener() {

        @Override
        public void onConnectStatusChanged(String mac, int status) {
            SimpleAdapter.GameItem it = null;
            if ( fragmentM.mAdapter != null)
            {
                int pos = fragmentM.mAdapter.findItem(mac);
                it = fragmentM.mAdapter.getItem(pos);

            }
            if (status == STATUS_CONNECTED) {
                if (it != null)
                {
                    it.connectState = STATUS_CONNECTED;
                }

            } else if (status == STATUS_DISCONNECTED) {
                if (it != null)
                {
                    it.connectState = STATUS_CONNECTED;
                }
            }
        }
    };

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

    public void connectMac(String mac)
    {
        final  String addr = mac;
        mClient.connect(mac, new BleConnectResponse() {
            //  mClient.registerConnectStatusListener(MAC, mBleConnectStatusListener);


            @Override
            public void onResponse(int code, BleGattProfile profile) {
                if (code == REQUEST_SUCCESS) {
                    List<BleGattService> services = profile.getServices();
                    for (int i = 0; i < services.size(); i++) {
                        BleGattService se = services.get(i);
                        List<BleGattCharacter> cs = se.getCharacters();
                        for (int j = 0; j < cs.size(); j++) {
                            BleGattCharacter c = cs.get(i);
                            mClient.notify(addr, se.getUUID(), c.getUuid(), new BleNotifyResponse() {
                                @Override
                                public void onNotify(UUID service, UUID character, byte[] value) {

                                }

                                @Override
                                public void onResponse(int code) {
                                    if (code == REQUEST_SUCCESS) {

                                    }
                                }
                            });

                        }
                    }


                    int pos = fragmentM.mAdapter.findItem(addr);
                    final SimpleAdapter.GameItem it =  fragmentM.mAdapter.getItem(pos);
                    if (it != null)
                    {
                        it.connectState = code;
                        fragmentM.mAdapter.notifyDataSetChanged();
                    }


                }
            }

            });
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
                fragmentM = VerticalFragment.newInstance();
                request = new SearchRequest.Builder()
                    // .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                    .searchBluetoothLeDevice(200000).build();      // 再扫BLE设备2s
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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title );
        if (mClient == null)
        {
            mClient = new BluetoothClient(this);
            mClient.registerBluetoothBondListener(mBluetoothBondListener);

        }



        mClient.search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {
               Button bt = findViewById(R.id.buttonScan);
               bt.setText("扫描中");
            }

            @Override
            public void onDeviceFounded(SearchResult device) {
                deviceT =device;

               // BluetoothLog.v(String.format("beacon for %s\n%s", device.getAddress(), beacon.toString()));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {



                        Beacon beacon = new Beacon(deviceT.scanRecord);
                       int n = fragmentM.mAdapter.findItem( deviceT.getAddress());
                        SimpleAdapter.GameItem it= new SimpleAdapter.GameItem( deviceT.getAddress(),
                                beacon.toString(),0,0);
                       if (n>=0)
                       {
                           fragmentM.mAdapter.updateItemCount(n,it);
                       }
                       else {
                           fragmentM.mAdapter.addItem(new SimpleAdapter.GameItem( deviceT.getAddress(),
                                   beacon.toString(),0,0));
                       }

                        if ( fragmentM.mAdapter != null)
                        {

                            fragmentM.mAdapter.setOnConnectClickListener( new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    if(event.getAction() == MotionEvent.ACTION_DOWN)
                                    {
                                        String posS = (String) v.getTag();
                                        int pos = Integer.parseInt(posS);
                                        SimpleAdapter.GameItem it = fragmentM.mAdapter.getItem(pos);
                                       // SimpleAdapter.VerticalItemHolder
                                        connectMac(it.addr);
                                        //ib_image.setImageResource(R.drawable.search_press);
                                    }
                                    else
                                    {
                                        // ib_image.setImageDrawable(null);
                                    }
                                    return false;
                                }
                            });
                           // fragmentM.mAdapter.setItemCount(0);
                            fragmentM.mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    final SimpleAdapter.GameItem it = fragmentM.mAdapter.getItem(position);
                                    if (it!= null)
                                    {
                                        connectMac(it.addr);


                                    }
                                }
                            });
                        }

                    }
                });


           }

            @Override
            public void onSearchStopped() {
                Button bt = findViewById(R.id.buttonScan);
                bt.setText("扫描");
            }

            @Override
            public void onSearchCanceled() {
                Button bt = findViewById(R.id.buttonScan);
                bt.setText("扫描");
            }
        });
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
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
