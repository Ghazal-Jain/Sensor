package com.example.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


/**
 * SensorListeners demonstrates how to gain access to sensors (here, the light
 * and proximity sensors), how to register sensor listeners, and how to
 * handle sensor events.
 */


public class MainActivity extends AppCompatActivity
        implements SensorEventListener {

    private static final String TAG = "Sensor";
    // System sensor manager instance.
    private SensorManager mSensorManager;

    // Proximity and light sensors, as retrieved from the sensor manager.

    private Sensor mSensorAccelerometer;

    private Sensor mSensorGravity;
    private Sensor mSensorGyroscope;
    private Sensor mSensorLinearAcceleration;


    private Sensor mSensorRotationVector;


    // TextViews to display current sensor values.


    private TextView mTextSensorAccelerometer;

    private TextView mTextSensorGravity;
    private TextView mTextSensorGyroscope;
    private TextView mTextSensorLinearAcceleration;


    private TextView mTextSensorRotationVector;



    private float maxAccelerometer ;
    private float minAccelerometer =1000.0f;
    private float maxTemperature ;
    private float minTemperature =1000.0f;
    private float maxGravity ;
    private float minGravity=1000.0f;
    private float maxGyroscope ;
    private float minGyroscope=1000.0f;
    private float maxLinearAcceleration ;
    private float minLinearAcceleration =1000.0f;
    private float maxRotation;
    private float minRotation =1000.0f;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize all view variables.

        mTextSensorAccelerometer = findViewById(R.id.label_accelerometer);
        mTextSensorGravity = findViewById(R.id.label_gravity);
        mTextSensorGyroscope = findViewById(R.id.label_gyroscope);
        mTextSensorLinearAcceleration =findViewById(R.id.label_linearAcceleration);
        mTextSensorRotationVector = findViewById(R.id.label_rotationVector);

        // Get an instance of the sensor manager.
        mSensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);

        // Get light and proximity sensors from the sensor manager.
        // The getDefaultSensor() method returns null if the sensor
        // is not available on the device.
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mSensorGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorLinearAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorRotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);


        // Get the error message from string resources.
        String sensor_error = getResources().getString(R.string.error_no_sensor);

        // If either mSensorLight or mSensorProximity are null, those sensors
        // are not available in the device.  Set the text to the error message

        if (mSensorAccelerometer == null) { mTextSensorAccelerometer.setText(sensor_error); }
        if (mTextSensorGravity == null) { mTextSensorGravity.setText(sensor_error); }
        if (mTextSensorGyroscope == null) { mTextSensorGyroscope.setText(sensor_error); }
        if (mTextSensorLinearAcceleration == null) { mTextSensorLinearAcceleration.setText(sensor_error); }
        if (mTextSensorRotationVector == null) { mTextSensorRotationVector.setText(sensor_error); }


    }

    @Override
    protected void onStart() {
        super.onStart();

        // Listeners for the sensors are registered in this callback and
        // can be unregistered in onPause().
        //
        // Check to ensure sensors are available before registering listeners.
        // Both listeners are registered with a "normal" amount of delay
        // (SENSOR_DELAY_NORMAL)

        if (mSensorAccelerometer != null) {
            mSensorManager.registerListener(this, mSensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorGravity != null) {
            mSensorManager.registerListener(this, mSensorGravity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorGyroscope != null) {
            mSensorManager.registerListener(this, mSensorGyroscope,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLinearAcceleration != null) {
            mSensorManager.registerListener(this, mSensorLinearAcceleration,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorRotationVector!= null) {
            mSensorManager.registerListener(this, mSensorRotationVector,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Unregister all sensor listeners in this callback so they don't
        // continue to use resources when the app is paused.
        mSensorManager.unregisterListener(this);
    }

    private static final float ERROR = (float) 25.0;
    private float x1, x2, x3;
    private int count= 0;
    private boolean init;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        // The sensor type (as defined in the Sensor class).
        int sensorType = sensorEvent.sensor.getType();

        // The new data value of the sensor.  Both the light and proximity
        // sensors report one value at a time, which is always the first
        // element in the values array.
        float currentValue = sensorEvent.values[0];
        float currentValue1 = sensorEvent.values[1];
        float currentValue2 = sensorEvent.values[2];

        switch (sensorType) {
            // Event came from the light sensor.



            case Sensor.TYPE_ACCELEROMETER:
                if(maxAccelerometer<=currentValue){
                    maxAccelerometer = currentValue;
                }
                if(minAccelerometer>=currentValue){
                    minAccelerometer = currentValue;
                }
                // Set the proximity sensor text view to the light sensor
                // string from the resources, with the placeholder filled in.
                mTextSensorAccelerometer.setText(getResources().getString(
                        R.string.label_accelerometer, currentValue ,currentValue1, currentValue2, maxAccelerometer, minAccelerometer));

                //Log.d(TAG, "Accelerometer!!");

                //Get x,y and z values
                float x,y,z;
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];


                if (!init) {
                    x1 = x;
                    x2 = y;
                    x3 = z;
                    init = true;
                } else {

                    float diffX = Math.abs(x1 - x);
                    float diffY = Math.abs(x2 - y);
                    float diffZ = Math.abs(x3 - z);

                    //Handling ACCELEROMETER Noise
                    if (diffX < ERROR) {

                        diffX = (float) 0.0;
                    }
                    if (diffY < ERROR) {
                        diffY = (float) 0.0;
                    }
                    if (diffZ < ERROR) {

                        diffZ = (float) 0.0;
                    }


                    x1 = x;
                    x2 = y;
                    x3 = z;


                    //Horizontal Shake Detected!
                    if (diffX > diffY) {
                        Log.d(TAG, "Shake Count " + count);
                        count = count+1;
                        Toast.makeText(MainActivity.this, "Shake Detected!", Toast.LENGTH_SHORT).show();
                    }
                }

                break;



            case Sensor.TYPE_GRAVITY:
                if(maxGravity<=currentValue){
                    maxGravity = currentValue;
                }
                if(minGravity>=currentValue){
                    minGravity = currentValue;
                }
                // Set the proximity sensor text view to the light sensor
                // string from the resources, with the placeholder filled in.
                mTextSensorGravity.setText(getResources().getString(
                        R.string.label_gravity, currentValue,  maxGravity, minGravity));

                //Log.d(TAG, "Gravity!!");
                break;

            case Sensor.TYPE_GYROSCOPE:
                if(maxGyroscope<=currentValue){
                    maxGyroscope = currentValue;
                }
                if(minGyroscope>=currentValue){
                    minGyroscope = currentValue;
                }
                // Set the proximity sensor text view to the light sensor
                // string from the resources, with the placeholder filled in.
                mTextSensorGyroscope.setText(getResources().getString(
                        R.string.label_gyroscope, currentValue, currentValue1, currentValue2,  maxGyroscope, minGyroscope));

                //Log.d(TAG, "Gyroscope!!");
                break;

            case Sensor.TYPE_LINEAR_ACCELERATION:
                if(maxLinearAcceleration<=currentValue){
                    maxLinearAcceleration = currentValue;
                }
                if(minLinearAcceleration>=currentValue){
                    minLinearAcceleration= currentValue;
                }
                // Set the proximity sensor text view to the light sensor
                // string from the resources, with the placeholder filled in.
                mTextSensorLinearAcceleration.setText(getResources().getString(
                        R.string.label_linearAcceleration, currentValue, maxLinearAcceleration, minLinearAcceleration));

                //Log.d(TAG, "Linear Acceleration!!");
                break;



            case Sensor.TYPE_ROTATION_VECTOR:
                if(maxRotation<=currentValue){
                    maxRotation = currentValue;
                }
                if(minRotation >=currentValue){
                    minRotation = currentValue;
                }

                if(currentValue1>0.30) {
                    Toast.makeText(MainActivity.this, "Tilt Right", Toast.LENGTH_SHORT).show();
                }
                if(currentValue1<-0.30) {
                    Toast.makeText(MainActivity.this, "Tilt Left", Toast.LENGTH_SHORT).show();
                }
                if(currentValue1 == 0.0){
                    Toast.makeText(MainActivity.this, "Center", Toast.LENGTH_SHORT).show();
                }
                // Set the proximity sensor text view to the light sensor
                // string from the resources, with the placeholder filled in.
                mTextSensorRotationVector.setText(getResources().getString(
                        R.string.label_rotationVector, currentValue, currentValue1, currentValue2, maxRotation, minRotation));

                //Log.d(TAG, "Rotation Vector!!");
                break;

            default:
                // do nothing
        }



    }

    /**
     * Abstract method in SensorEventListener.  It must be implemented, but is
     * unused in this app.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

}