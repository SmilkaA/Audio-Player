package com.example.audioplayer.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audioplayer.adapters.BluetoothConnectionAdapter;
import com.example.audioplayer.databinding.ActivityBluetoothBinding;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("MissingPermission")
public class BluetoothActivity extends AppCompatActivity {

    private static final int RELOAD_DELAY_MILLIS = 3000;
    private static final int ANIMATION_DURATION = 1000;
    private static final int ANIMATION_ANGLE = 180;

    private ActivityBluetoothBinding binding;
    private RecyclerView savedDevices;
    private TextView noSavedView;
    private RecyclerView availableDevices;
    private TextView noAvailableView;
    private ImageView reloadAvailable;

    private List<BluetoothDevice> devicesPaired = new ArrayList<>();
    private List<BluetoothDevice> devicesAvailable = new ArrayList<>();

    private BluetoothAdapter bluetoothAdapter;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            devicesAvailable.clear();
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devicesAvailable.add(device);
            } else {
                noAvailableView.setVisibility(View.VISIBLE);
            }
            initRecyclerViewForAvailableDevices();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBluetoothBinding.inflate(getLayoutInflater());
        handlePermissions();
        initComponents();

        setContentView(binding.getRoot());
        updatePeriodically();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void handlePermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 111);

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
    }

    private void initComponents() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        savedDevices = binding.rvBluetoothSaved;
        noSavedView = binding.bluetoothNoSavedText;

        availableDevices = binding.rvBluetoothAvailable;
        noAvailableView = binding.bluetoothNoAvailableText;

        reloadAvailable = binding.reload;
        reloadAvailable.setOnClickListener(v -> {
            onReloadClicked();
        });
    }

    private void onReloadClicked() {
        addAnimationToReloadButton();
        getBluetoothDevices();
    }

    private void initRecyclerViewForSavedDevices() {
        devicesPaired.clear();
        devicesPaired.addAll(bluetoothAdapter.getBondedDevices());
        if (devicesPaired.isEmpty()) {
            noSavedView.setVisibility(View.VISIBLE);
        } else {
            noSavedView.setVisibility(View.GONE);
            BluetoothConnectionAdapter adapterForPaired = new BluetoothConnectionAdapter(getApplicationContext(), devicesPaired, true);
            savedDevices.setLayoutManager(new LinearLayoutManager(this));
            savedDevices.setAdapter(adapterForPaired);
        }
    }

    private void initRecyclerViewForAvailableDevices() {
        if (devicesAvailable.isEmpty()) {
            noAvailableView.setVisibility(View.VISIBLE);
        } else {
            noAvailableView.setVisibility(View.GONE);
            BluetoothConnectionAdapter adapterForAvailable = new BluetoothConnectionAdapter(getApplicationContext(), devicesAvailable, false);
            availableDevices.setLayoutManager(new LinearLayoutManager(this));
            availableDevices.setAdapter(adapterForAvailable);
        }
        initRecyclerViewForSavedDevices();
    }

    public void getBluetoothDevices() {
        registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        bluetoothAdapter.startDiscovery();
        initRecyclerViewForAvailableDevices();
    }

    private void addAnimationToReloadButton() {
        float mCurrRotation = reloadAvailable.getRotation();
        float toRotation = mCurrRotation + ANIMATION_ANGLE;

        RotateAnimation rotateAnim = new RotateAnimation(
                mCurrRotation, toRotation, reloadAvailable.getWidth() / 2, reloadAvailable.getHeight() / 2);

        rotateAnim.setDuration(ANIMATION_DURATION);
        rotateAnim.setFillAfter(true);

        reloadAvailable.startAnimation(rotateAnim);
    }

    private void updatePeriodically() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(this, RELOAD_DELAY_MILLIS);
                onReloadClicked();
            }
        }, RELOAD_DELAY_MILLIS);
    }
}