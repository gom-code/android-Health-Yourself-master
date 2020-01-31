

package com.example.sangil.pedometer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.widget.Toast;





public class StepService extends Service {

    private SharedPreferences mSettings;
    private PedometerSettings mPedometerSettings;
    private SharedPreferences mState;
    private SharedPreferences.Editor mStateEditor;
    private SensorManager mSensorManager;
    private StepDetector mStepDetector;
    private StepDisplayer mStepDisplayer;
    private DistanceNotifier mDistanceNotifier;
    private CaloriesNotifier mCaloriesNotifier;

    
    private PowerManager.WakeLock wakeLock;
    private NotificationManager mNM;

    private int mSteps;

    private float mDistance;

    private float mCalories;
    

    public class StepBinder extends Binder {
        StepService getService() {
            return StepService.this;
        }
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);


        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        mPedometerSettings = new PedometerSettings(mSettings);
        mState = getSharedPreferences("state", 0);

        mStepDetector = new StepDetector();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(mStepDetector, 
                SensorManager.SENSOR_ACCELEROMETER | 
                SensorManager.SENSOR_MAGNETIC_FIELD | 
                SensorManager.SENSOR_ORIENTATION,
                SensorManager.SENSOR_DELAY_FASTEST);

        mStepDisplayer = new StepDisplayer(mPedometerSettings);
        mStepDisplayer.setSteps(mSteps = mState.getInt("steps", 0));
        mStepDisplayer.addListener(mStepListener);
        mStepDetector.addStepListener(mStepDisplayer);


        mDistanceNotifier = new DistanceNotifier(mDistanceListener, mPedometerSettings);
        mDistanceNotifier.setDistance(mDistance = mState.getFloat("distance", 0));
        mStepDetector.addStepListener(mDistanceNotifier);
        
     
        mCaloriesNotifier = new CaloriesNotifier(mCaloriesListener, mPedometerSettings);
        mCaloriesNotifier.setCalories(mCalories = mState.getFloat("calories", 0));
        mStepDetector.addStepListener(mCaloriesNotifier);
        


        reloadSettings();
        
    }
    
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);


        Toast.makeText(this, getText(R.string.started), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        
        mStateEditor = mState.edit();
        mStateEditor.putInt("steps", mSteps);

        mStateEditor.putFloat("distance", mDistance);

        mStateEditor.putFloat("calories", mCalories);
        mStateEditor.commit();
        
        mNM.cancel(R.string.app_name);

        wakeLock.release();
        
        super.onDestroy();
        

        mSensorManager.unregisterListener(mStepDetector);
        

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Receives messages from activity.
     */
    private final IBinder mBinder = new StepBinder();

    public interface ICallback {
        public void stepsChanged(int value);
        public void distanceChanged(float value);
        public void caloriesChanged(float value);
    }
    
    private ICallback mCallback;

    public void registerCallback(ICallback cb) {
        mCallback = cb;

    }
    



    public void reloadSettings() {
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        
        if (mStepDetector != null) { 
            mStepDetector.setSensitivity(
                    Integer.valueOf(mSettings.getString("sensitivity", "30"))
            );
        }
        

        
     
        
        if (mStepDisplayer    != null) mStepDisplayer.reloadSettings();
        if (mDistanceNotifier != null) mDistanceNotifier.reloadSettings();
        if (mCaloriesNotifier != null) mCaloriesNotifier.reloadSettings();
     
    }
    
    public void resetValues() {
        mStepDisplayer.setSteps(0);
        mDistanceNotifier.setDistance(0);
        mCaloriesNotifier.setCalories(0);
    }
    

    private StepDisplayer.Listener mStepListener = new StepDisplayer.Listener() {
        public void stepsChanged(int value) {
            mSteps = value;
            passValue();
        }
        public void passValue() {
            if (mCallback != null) {
                mCallback.stepsChanged(mSteps);
            }
        }
    };


    private DistanceNotifier.Listener mDistanceListener = new DistanceNotifier.Listener() {
        public void valueChanged(float value) {
            mDistance = value;
            passValue();
        }
        public void passValue() {
            if (mCallback != null) {
                mCallback.distanceChanged(mDistance);
            }
        }
    };
 
    private CaloriesNotifier.Listener mCaloriesListener = new CaloriesNotifier.Listener() {
        public void valueChanged(float value) {
            mCalories = value;
            passValue();
        }
        public void passValue() {
            if (mCallback != null) {
                mCallback.caloriesChanged(mCalories);
            }
        }
    };


}

