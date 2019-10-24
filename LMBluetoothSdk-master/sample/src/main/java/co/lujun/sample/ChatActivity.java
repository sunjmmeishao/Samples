package co.lujun.sample;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.lujun.lmbluetoothsdk.BluetoothController;
import co.lujun.lmbluetoothsdk.base.BluetoothListener;
import co.lujun.lmbluetoothsdk.base.State;

/**
 * Author: lujun(http://blog.lujun.co)
 * Date: 2016-1-21 16:10
 */
public class ChatActivity extends Activity {
    private BluetoothController mBluetoothController;

    private Button btnDisconnect, btnSend;
    private EditText etSend;
    private TextView tvConnectState, tvContent, tvDeviceName, tvDeviceMac;

    private int mConnectState;
    private String mMacAddress = "", mDeviceName = "";

    private static final String TAG = "LMBluetoothSdk";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main);

        init();
    }

    private void init(){
        mMacAddress = getIntent().getStringExtra("mac");
        mDeviceName = getIntent().getStringExtra("name");

        mBluetoothController = BluetoothController.getInstance();
        mBluetoothController.setBluetoothListener(new BluetoothListener() {
            @Override
            public void onActionStateChanged(int preState, int state) {
                Toast.makeText(ChatActivity.this, "BT state: " + state, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onActionDiscoveryStateChanged(String discoveryState) {}

            @Override
            public void onActionScanModeChanged(int preScanMode, int scanMode) {}

            @Override
            public void onBluetoothServiceStateChanged(final int state) {
                // If you want to update UI, please run this on UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mConnectState = state;
                        tvConnectState.setText("Connection state: " + Utils.transConnStateAsString(state));
                    }
                });
            }

            @Override
            public void onActionDeviceFound(BluetoothDevice device, short rssi) {}

            @Override
            public void onReadData(final BluetoothDevice device, final byte[] data) {
                // If you want to update UI, please run this on UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String deviceName = device == null ? "" : device.getName();
                        tvContent.append(deviceName + ": " + new String(data) + "\n");
                    }
                });
            }
        });

        btnSend = (Button) findViewById(R.id.btn_send);
        btnDisconnect = (Button) findViewById(R.id.btn_disconnect);
        tvConnectState = (TextView) findViewById(R.id.tv_connect_state);
        etSend = (EditText) findViewById(R.id.et_send_content);
        tvContent = (TextView) findViewById(R.id.tv_chat_content);
        tvDeviceName = (TextView) findViewById(R.id.tv_device_name);
        tvDeviceMac = (TextView) findViewById(R.id.tv_device_mac);

        tvDeviceName.setText("Device: " + mDeviceName);
        tvDeviceMac.setText("MAC address: " + mMacAddress);
        tvConnectState.setText("Connection state: "
                + Utils.transConnStateAsString(mBluetoothController.getConnectionState()));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = etSend.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    return;
                }
                mBluetoothController.write(msg.getBytes());
                tvContent.append("Me: " + msg + "\n");
                etSend.setText("");
            }
        });
        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConnectState == State.STATE_CONNECTED) {
                    mBluetoothController.disconnect();
                }
                finish();
            }
        });

        if (!TextUtils.isEmpty(mMacAddress)) {
            mBluetoothController.connect(mMacAddress);
        }else {
            if (mBluetoothController.getConnectedDevice() == null){
                return;
            }
            mDeviceName = mBluetoothController.getConnectedDevice().getName();
            mMacAddress = mBluetoothController.getConnectedDevice().getAddress();
            tvDeviceName.setText("Device: " + mDeviceName);
            tvDeviceMac.setText("MAC address: " + mMacAddress);
        }
    }
}
