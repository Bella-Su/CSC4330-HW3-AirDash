//====================================================================
//
// Application: Air Dash simulation application
// Activity:    ActMain
// Description:
//   This app simulates meal deliveries by a fleet of drones
//
//====================================================================

package com.example.airdash;

//Import packages
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import java.util.Timer;

//--------------------------------------------------------------------
// Class ActMain
//--------------------------------------------------------------------
public class ActMain extends AppCompatActivity {

    //Constants
    public static final String APP_NAME = "Air Dash";
    public static final String APP_VERSION = "1.0";
    public static final int ORDER_RATE_START = 0;
    public static final int DRONES_FLYING_START = 0;
    public static final int DELIVERY_TIME_START = 0;

    //Variables
    private Toolbar tbrMain;
    private static EditText txtClock;
    private static EditText txtOrdersQueued;
    private static EditText txtOrdersDelivered;
    private static EditText txtDronesAvailable;
    private static EditText txtDronesCrashes;
    private EditText txtOrderRate;
    private EditText txtDronesFlying;
    private EditText txtDeliveryTime;
    private SeekBar sbOrderRate;
    private static SeekBar sbDronesFlying;
    private SeekBar sbDeliveryTime;
    private Button btnStartTimer;
    private Button btnStopTimer;
    private Timer timer;
    private AlertDialog.Builder builder;
    private int lastClock;
    private int lastOrderQueued;
    private int lastOrderDelivered;
    private int lastDronesAvailable;
    private int lastDronesCrashes;
    private int lastOrderRate;
    private int lastDronesFlying;
    private int lastDeliveryTime;

    //----------------------------------------------------------------
    // onCreate
    //----------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laymain);


        //Define and connect to toolbar
        tbrMain = findViewById(R.id.tbrMain);
        setSupportActionBar(tbrMain);
        tbrMain.setNavigationIcon(R.mipmap.ic_launcher_new);

        //Define controls
        txtClock = findViewById(R.id.txtClock);
        txtOrdersQueued = findViewById(R.id.txtOrdersQueued);
        txtOrdersDelivered = findViewById(R.id.txtOrdersDelivered);
        txtDronesAvailable = findViewById((R.id.txtDronesAvailable));
        txtDronesCrashes = findViewById(R.id.txtDronesCrashes);
        txtOrderRate = findViewById(R.id.txtOrderRate);
        txtDronesFlying = findViewById(R.id.txtDronesFlying);
        txtDeliveryTime = findViewById(R.id.txtDeliveryTime);
        sbOrderRate = findViewById(R.id.sbOrderRate);
        sbDronesFlying = findViewById(R.id.sbDronesFlying);
        sbDeliveryTime = findViewById(R.id.sbDeliveryTime);
        btnStartTimer = findViewById(R.id.btnStartTimer);
        btnStopTimer = findViewById(R.id.btnStopTimer);

        //Set buttons
        btnStartTimer.setEnabled(true);
        btnStopTimer.setEnabled(false);

        //Set order rate seek bar
        sbOrderRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtOrderRate.setText(String.valueOf(progress));
                Shared.Data.orderRateValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Set Drones flying seek bar
        sbDronesFlying.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtDronesFlying.setText(String.valueOf(progress));
                Shared.Data.dronesFlyingValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Set delivery time seek bar
        sbDeliveryTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtDeliveryTime.setText(String.valueOf(progress));
                Shared.Data.deliveryTimeValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    //----------------------------------------------------------------
    // startSimulation
    //----------------------------------------------------------------
    public void startSimulation(View view)
    {
        //Cancel timer if exists
        if(timer != null)
            timer.cancel();

        //Create and start timer
        timer = new Timer();
        timer.schedule(new Task(), 0, 1000);

        //Set buttons
        btnStartTimer.setEnabled(false);
        btnStopTimer.setEnabled(true);

        //Show toast message
        Toast.makeText(getApplicationContext(),
                "Simulation started.", Toast.LENGTH_LONG).show();
    }

    //----------------------------------------------------------------
    // stopSimulation
    //----------------------------------------------------------------
    public void stopSimulation(View view)
    {
        //Cancel timer if exists
        if(timer != null)
            timer.cancel();
        timer = null;

        //Set button
        btnStartTimer.setEnabled(true);
        btnStopTimer.setEnabled(false);

        //Show toast message
        Toast.makeText(getApplicationContext(),
                "Simulation stopped.", Toast.LENGTH_LONG).show();
    }

    //----------------------------------------------------------------
    // recallSimulation
    //----------------------------------------------------------------
    public void recallSimulation(View view)
    {
        //Create a dialog box
        builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(APP_NAME + " (" + APP_VERSION + ") " + "Message");
        builder.setMessage("Recall the last simulation?");

        //Define "Yes" response
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Retrieve value from last simulation
                txtClock.setText(String.valueOf(lastClock));
                txtOrdersQueued.setText(String.valueOf(lastOrderQueued));
                txtOrdersDelivered.setText(String.valueOf(lastOrderDelivered));
                txtDronesAvailable.setText(String.valueOf(lastDronesAvailable));
                txtDronesCrashes.setText(String.valueOf(lastDronesCrashes));
                Shared.Data.clockValue = lastClock;
                Shared.Data.orderQueuedValue = lastOrderQueued;
                Shared.Data.orderDeliveredValue = lastOrderDelivered;
                Shared.Data.dronesAvailableValue = lastDronesAvailable;
                Shared.Data.dronesCrashesValue = lastDronesCrashes;
                sbOrderRate.setProgress(lastOrderRate);
                sbDronesFlying.setProgress(lastDronesFlying);
                sbDeliveryTime.setProgress(lastDeliveryTime);
                sbDronesFlying.setMax(lastDronesAvailable);
            }
        });

        //Define "No" response
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),
                        "Last simulation NOT recalled.", Toast.LENGTH_LONG).show();
            }
        });

        //Show dialog box
        builder.show();
    }

    //----------------------------------------------------------------
    // resetSimulation
    //----------------------------------------------------------------
    public void resetSimulation(View view)
    {
        //Create a dialog box
        builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(APP_NAME + " (" + APP_VERSION + ") " + "Message");
        builder.setMessage("Reset the simulation?");

        //Define "Yes" response
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                txtClock.setText("0");
                txtOrdersQueued.setText("0");
                txtOrdersDelivered.setText("0");
                txtDronesAvailable.setText("100");
                txtDronesCrashes.setText("0");
                lastClock = Shared.Data.clockValue;
                lastOrderQueued = Shared.Data.orderQueuedValue;
                lastOrderDelivered = Shared.Data.orderDeliveredValue;
                lastDronesAvailable = Shared.Data.dronesAvailableValue;
                lastDronesCrashes = Shared.Data.dronesCrashesValue;
                lastOrderRate = Shared.Data.orderRateValue;
                lastDronesFlying = Shared.Data.dronesFlyingValue;
                lastDeliveryTime = Shared.Data.deliveryTimeValue;
                Shared.Data.clockValue = 0;
                Shared.Data.orderQueuedValue = 0;
                Shared.Data.orderDeliveredValue = 0;
                Shared.Data.dronesAvailableValue = 100;
                Shared.Data.dronesCrashesValue = 0;
                sbOrderRate.setProgress(ORDER_RATE_START);
                sbDronesFlying.setProgress(DRONES_FLYING_START);
                sbDronesFlying.setMax(100);
                sbDeliveryTime.setProgress(DELIVERY_TIME_START);

            }
        });

        //Define "No" response
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),
                        "Simulation NOT reset.", Toast.LENGTH_LONG).show();
            }
        });

        //Show dialog box
        builder.show();
    }

    //----------------------------------------------------------------
    // timerTaskHandler
    //----------------------------------------------------------------
    public static Handler timerTaskHandler =
            new Handler(Looper.getMainLooper())
            {

                //------------------------------------------------------------
                // handleMessage
                //------------------------------------------------------------
                @Override
                public void handleMessage(Message msg)
                {
                    txtClock.setText(String.valueOf(Shared.Data.clockValue));
                    txtOrdersQueued.setText(String.valueOf(Shared.Data.orderQueuedValue));
                    txtOrdersDelivered.setText(String.valueOf(Shared.Data.orderDeliveredValue));
                    txtDronesAvailable.setText(String.valueOf(Shared.Data.dronesAvailableValue));
                    txtDronesCrashes.setText(String.valueOf(Shared.Data.dronesCrashesValue));

                    //Check the drone left
                    sbDronesFlying.setMax(Shared.Data.dronesAvailableValue);
                }

            };

}