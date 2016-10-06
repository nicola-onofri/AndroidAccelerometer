package com.example.nicola.androidaccelerometer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private final double speed_factor = 3.0;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastUpdate;
    private TextView monitor;


    public AnimatedView animatedView = null;
    public ShapeDrawable mDrawable = new ShapeDrawable();

    public static int x = 0;
    public static int y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        //monitor = (TextView) findViewById(R.id.txtView_log);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //lastUpdate = System.currentTimeMillis();

        animatedView = new AnimatedView(this);
        setContentView(animatedView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //String log = "";
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x -= (int) (event.values[0] * speed_factor);
            y += (int) (event.values[1] * speed_factor);
            //log += "accelerometer_x: " + x + "\naccelerometer_y: " + y;
        }
        //monitor.append(log);
    }

    public class AnimatedView extends ImageView {
        static final int width = 50;
        static final int height = 50;

        public AnimatedView(Context context) {
            super(context);
            mDrawable = new ShapeDrawable(new OvalShape());
            mDrawable.setBounds(x, y, x + width, y + height);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            mDrawable.setBounds(x, y, x + width, y + height);
            mDrawable.draw(canvas);
            invalidate();
        }
    }
}
