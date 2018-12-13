package com.resonatestudios.quiz_1603631_muhammad_nabillah.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.resonatestudios.quiz_1603631_muhammad_nabillah.R;
import com.resonatestudios.quiz_1603631_muhammad_nabillah.adapter.AdapterMovementList;

import java.util.ArrayList;

/**
 * Muhammad Nabillah F.
 * 1603631
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String LAST_STATE = "last_state";
    RecyclerView recyclerViewMovement;
    ArrayList<String> savedList;
    AdapterMovementList adapterMovementList;
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewMovement = findViewById(R.id.recycler_view_movement);

        savedList = new ArrayList<>();
        if (savedInstanceState != null) {
            // mengambil savedList terakhir dari savedInstanceState
            savedList = savedInstanceState.getStringArrayList(LAST_STATE);
        }

        // mengosongkan judul actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        // sensor manager untuk accelerometer linear
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(getString(R.string.how_to_use));
        alertDialog.show();

        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerViewMovement.setLayoutManager(new LinearLayoutManager(this));
        adapterMovementList = new AdapterMovementList(this);
        recyclerViewMovement.setAdapter(adapterMovementList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // savedList disimpan ke outstate
        outState.putStringArrayList(LAST_STATE, savedList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // set menu di action bar
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                // klik Clear
                // list akan dikosongkan dan recyclerview akan di-notify
                adapterMovementList.getListDirection().clear();
                adapterMovementList.notifyDataSetChanged();
                Toast.makeText(this, "Cleared", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_save:
                // klik Save
                // list yang dipegang adapter akan disimpan ke savedList
                // savedList akan dimasukkan ke onSavedInstance dan akan diambil ketika aplikasi dijalankan lagi
                // BELUM BERHASIL
                savedList = adapterMovementList.getListDirection();
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_load:
                // klik Load
                // list adapter akan dikosongkan lalu diisi dengan savedList
                // BELUM BERHASIL
                adapterMovementList.getListDirection().clear();
                adapterMovementList.getListDirection().addAll(savedList);
                adapterMovementList.notifyDataSetChanged();
                adapterMovementList.notifyItemInserted(0);
                Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            if (event.values[0] > -0.4 && 0.4 < event.values[0]) {
                // gerak kiri-kanan
                adapterMovementList.addToList("kiri-kanan");
            }
            if (event.values[1] > -0.4 && 0.4 < event.values[1]) {
                // gerak atas-bawah
                adapterMovementList.addToList("atas-bawah");
            }
            if (event.values[2] > -0.4 && 0.4 < event.values[2]) {
                // getak angkat-turun
                adapterMovementList.addToList("angkat-turun");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //
    }
}
