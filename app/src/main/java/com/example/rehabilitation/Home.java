package com.example.rehabilitation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mbientlab.metawear.DeviceInformation;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.android.BtleService;
import com.mbientlab.metawear.module.Led;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import bolts.Continuation;
import bolts.Task;

public class Home extends AppCompatActivity  {
//    private BtleService.LocalBinder serviceBinder;
//    private final String MAC_adress = "C5:6F:20:0C:40:27";
//    private MetaWearBoard board;
//    private  Led led;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Bind the service when the activity is created
//        getApplicationContext().bindService(new Intent(this, BtleService.class),
//                this, Context.BIND_AUTO_CREATE);
    }



//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // Unbind the service when the activity is destroyed
//        getApplicationContext().unbindService(this);
//        board.tearDown();//zamyka wszystko
//    }
//
//    @Override
//    public void onServiceConnected(ComponentName name, IBinder service) {
//        // Typecast the binder to the service's LocalBinder class
//        serviceBinder = (BtleService.LocalBinder) service;
//        Log.i("MetaWearBoard", "polaczony");
//        retrieveBoard(MAC_adress);
//    }
//
//    @Override
//    public void onServiceDisconnected(ComponentName name) {
//        //zamyka połaczenie, mozliwe ze niepotrzebne
//        board.disconnectAsync().continueWith(new Continuation<Void, Void>() {
//            @Override
//            public Void then(Task<Void> task) throws Exception {
//                Log.i("MainActivity", "Disconnected");
//                return null;
//            }
//        });
//    }
//
//    private void retrieveBoard(String MAC_adress) {
//        final BluetoothManager btManager =
//                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//        final BluetoothDevice remoteDevice =
//                btManager.getAdapter().getRemoteDevice(MAC_adress);
//
//        // Create a MetaWear board object for the Bluetooth Device
//        this.board = serviceBinder.getMetaWearBoard(remoteDevice);
//        board.connectAsync().continueWith(new Continuation<Void, Void>() {
//
//            @Override
//            public Void then(Task<Void> task) throws Exception {
//                    Log.i("MetaWearBoard", "board connection- success");
//
//                //zapalenie zielonego ledu sygnalizuje polączenie z urządzeniem
//                led = board.getModule(Led.class);
//                led.editPattern(Led.Color.GREEN)
//                        .riseTime((short) 0)
//                        .pulseDuration((short) 1000)
//                        .repeatCount((byte) 5)
//                        .highTime((short) 200)
//                        .highIntensity((byte) 16)
//                        .lowIntensity((byte) 16)
//                        .commit();
//                led.play();
//                return null;
//                }
//        });
//    }

    public void onClickHip(View view) {
        Intent intent = new Intent(this,Hip.class);
        startActivity(intent);
    }

    public void onClickKnee(View view) {
        Intent intent = new Intent(this,Knee.class);
        startActivity(intent);
    }


}
