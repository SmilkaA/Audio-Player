package com.example.audioplayer.adapters;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audioplayer.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BluetoothConnectionAdapter extends RecyclerView.Adapter<BluetoothConnectionAdapter.ViewHolder> {

    private final Context context;
    private final List<BluetoothDevice> devices;
    private final boolean saveState;

    public BluetoothConnectionAdapter(Context context, List<BluetoothDevice> devices, boolean saveState) {
        this.context = context;
        if (devices != null) {
            this.devices = devices;
        } else {
            this.devices = new ArrayList<>();
        }
        this.saveState = saveState;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bluetooth, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BluetoothDevice device = devices.get(position);
        holder.deviceName.setText(device.getName());
        holder.deviceMAC.setText(device.getAddress());
        if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
            holder.pairButton.setText(R.string.bluetooth_unpair_text);
        }
        holder.pairButton.setOnClickListener(v -> {
            if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                unpairDevice(device);
                holder.pairButton.setText(R.string.bluetooth_pair_text);
            } else {
                holder.pairButton.setText(R.string.bluetooth_pairing_text);
                pairDevice(device);
            }
            if (!saveState) {
                devices.remove(device);
                notifyItemRemoved(position);
                notifyItemChanged(position);
                notifyItemRangeChanged(position, devices.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    private void pairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("createBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception ignored) {
        }
    }

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception ignored) {
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView deviceName;
        TextView deviceMAC;
        Button pairButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            deviceName = itemView.findViewById(R.id.item_bluetooth_name);
            deviceMAC = itemView.findViewById(R.id.item_bluetooth_mac);
            pairButton = itemView.findViewById(R.id.connection_button);
            pairButton.setText(R.string.bluetooth_pair_text);
        }
    }

}
