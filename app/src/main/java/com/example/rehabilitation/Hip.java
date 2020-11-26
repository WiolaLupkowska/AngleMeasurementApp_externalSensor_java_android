package com.example.rehabilitation;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbientlab.metawear.Data;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.Route;
import com.mbientlab.metawear.Subscriber;
import com.mbientlab.metawear.android.BtleService;
import com.mbientlab.metawear.builder.RouteBuilder;
import com.mbientlab.metawear.builder.RouteComponent;
import com.mbientlab.metawear.data.Acceleration;
import com.mbientlab.metawear.module.Accelerometer;
import com.mbientlab.metawear.module.Led;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

public class Hip extends AppCompatActivity implements ServiceConnection {

    private BtleService.LocalBinder serviceBinder;
    private MetaWearBoard board;
    private Accelerometer accelerometer;
    private final String MAC_adress = "C5:6F:20:0C:40:27";
    private Led led;
    static List<Double> valuesX ;
    static List<Double> valuesY ;
    static List<Double> valuesZ ;
    public Angles angles;

    double hipFlex=0;
    double hipExtend=0;
    double hipAdduction=0;
    double hipAbduction=0;
    double hipExternalRotation=0;
    double hipInternalRotation=0;
    double angleMin;
    double angleMax;
    String movement;
    String joint = "staw biodrowy";

    ArrayList katy = new ArrayList();

    public Angles getAngles() {
        return angles;
    }

    public void setAngles(Angles angles) {
        this.angles = angles;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.hip);
        getApplicationContext().bindService(new Intent(this, BtleService.class),
                this, Context.BIND_AUTO_CREATE);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                valuesX = new ArrayList<>();
                valuesY = new ArrayList<>();
                valuesZ = new ArrayList<>();
                accelerometer.acceleration().start();
                accelerometer.start();
            }
        });
        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accelerometer.stop();
                accelerometer.acceleration().stop();

                Angles angles= new Angles(valuesX,valuesY,valuesZ);
                setAngles(angles);

                double tab[]={0,0,0};

                switch(movement) {
                    case "zgięcie":
                        tab = angles.calculatePitchHip();
                        hipFlex = tab[2];
                        break;
                    case "wyprost":
                        tab = angles.calculatePitchHip();
                        hipExtend = tab[2];
                        break;
                    case "przywodzenie":
                        tab =angles.calculateYawHip();
                        hipAdduction = tab[2];
                        break;
                    case "odwodzenie":
                        tab =angles.calculateYawHip();
                        hipAbduction = tab[2];
                        break;
                    case "rotacja zewnętrzna":
                        tab =angles.calculateYawHip();
                        hipExternalRotation = tab[2];
                        break;
                    case "rotacja wewnętrzna":
                        tab =angles.calculateYawHip();
                        hipInternalRotation = tab[2];
                        break;
                }
                angleMin = tab[0];
                angleMax = tab[1];
                double range = tab[2];
                TextView textViewSumUp = (TextView) findViewById(R.id.textViewSumUp);
                textViewSumUp.setText("min: "+String.valueOf(angleMin)+"\n"+"max: "+ String.valueOf(angleMax)+"\n"+"ruch: "+movement+"\n"+"staw: "+joint+"\n"+"zakres ruchomości: "+String.valueOf(range));



            }
        });

        TextView textViewAngles = (TextView) findViewById(R.id.textViewCurrentAngle);
        textViewAngles.setText("kąty");
        TextView textViewSumUp = (TextView) findViewById(R.id.textViewSumUp);
        textViewSumUp.setText("wyniki");


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getApplicationContext().unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        serviceBinder = (BtleService.LocalBinder) service;
        Log.i("MetaWearBoard", "polaczony");
        retrieveBoard(MAC_adress);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        board.disconnectAsync().continueWith(new Continuation<Void, Void>() {
            @Override
            public Void then(Task<Void> task) throws Exception {
                Log.i("MainActivity", "Disconnected");
                return null;
            }
        });
    }

    public void retrieveBoard(String MAC_adress) {
        valuesX = new ArrayList<>();
        valuesY = new ArrayList<>();
        valuesZ = new ArrayList<>();
        final BluetoothManager btManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        final BluetoothDevice remoteDevice =
                btManager.getAdapter().getRemoteDevice(MAC_adress);
        this.board = serviceBinder.getMetaWearBoard(remoteDevice);


        board.connectAsync().continueWith(new Continuation<Void, Task<Route>>() {
            @Override
            public Task<Route> then(Task<Void> task) throws Exception {
                if (task.isFaulted()){
                    Log.i("MetaWearBoard", "board connection- failure");
                }else{
                    Log.i("MetaWearBoard", "board connection- success");
                }

                accelerometer= board.getModule(Accelerometer.class);
                accelerometer.configure()
                        .odr(25f)
                        .commit();

                led = board.getModule(Led.class);
                led.editPattern(Led.Color.BLUE)
                        .riseTime((short) 0)
                        .pulseDuration((short) 1000)
                        .repeatCount((byte) 5)
                        .highTime((short) 200)
                        .highIntensity((byte) 16)
                        .lowIntensity((byte) 16)
                        .commit();
                led.play();
                return accelerometer.acceleration().addRouteAsync(new RouteBuilder() {

                    @Override
                    public void configure(RouteComponent source) {
                        source.stream(new Subscriber() {

                            @Override
                            public void apply(Data data, Object... env) {
                                Log.i("MetaWearBoard", data.value(Acceleration.class).toString());
                                //double current=0;

                                //double current = Math.toDegrees(Math.atan2(data.value(Acceleration.class).y(), data.value(Acceleration.class).z()));
                                double current = Math.toDegrees(Math.atan(data.value(Acceleration.class).x()/(Math.sqrt(Math.pow(data.value(Acceleration.class).x(),2)+Math.pow(data.value(Acceleration.class).y(),2)+Math.pow(data.value(Acceleration.class).z(),2)))));
                                 //double current = Math.toDegrees(Math.atan(data.value(Acceleration.class).z()/(Math.sqrt(Math.pow(data.value(Acceleration.class).y(),2)+Math.pow(data.value(Acceleration.class).z(),2)+Math.pow(data.value(Acceleration.class).x(),2)))));



//                                if(movement=="zgięcie"){
//                                    if(current<0){
//                                        current=current+360;
//                                    }else{
//                                        current = Math.toDegrees(Math.atan2(data.value(Acceleration.class).x(), data.value(Acceleration.class).y()));
//                                    }
//                                }
                                valuesX.add((double) data.value(Acceleration.class).x());
                                valuesY.add((double) data.value(Acceleration.class).y());
                                valuesZ.add((double) data.value(Acceleration.class).z());
                                katy.add(current);
                                String currentStr = String.valueOf(current);
                                TextView textViewAngles = (TextView) findViewById(R.id.textViewCurrentAngle);
                                textViewAngles.setText(currentStr);
                                System.out.println(currentStr);

                            }
                        });
                    }

                });

            }
        });


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.RadioButtonFlexHip:
                if (checked)
                    movement="zgięcie";
                break;
            case R.id.RadioButtonExtendHip:
                if (checked)
                    movement="wyprost";
                break;
            case R.id.RadioButtonAdduction:
                if (checked)
                    movement="przywodzenie";
                break;
            case R.id.RadioButtonAbduction:
                if (checked)
                    movement="odwodzenie";
                break;
            case R.id.RadioButtonExternalRotation:
                if (checked)
                    movement="rotacja zewnętrzna";
                break;
            case R.id.RadioButtonInternalRotation:
                if (checked)
                    movement="rotacja wewnętrzna";
                break;
        }
    }
//    public void onClickStart(){
//
//    }
//    public void onClickStop(){
//
//    }
    public void onClickBtnWynik(View view) {

    }
}
