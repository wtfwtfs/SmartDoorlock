package com.example.inu_smartdoor;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends Activity {

	private static final int REQUEST_ENABLE_BT = 3;

	private ArrayAdapter<String> mArrayAdapter;

	private ListView mConversationView;

	private static final String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {

	    FirebaseInstanceId.getInstance().getInstanceId()
				.addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
					@Override
					public void onComplete(@NonNull Task<InstanceIdResult> task) {
						if (!task.isSuccessful()) {
							Log.w(TAG, "getInstanceId failed", task.getException());
							return;
						}

						// Get new Instance ID token
						String token = task.getResult().getToken();

						// Log and toast
						String msg = getString(R.string.msg_token_fmt, token);
						Log.d(TAG, msg);
						Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
					}
				});

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		BluetoothAdapter mBTAdapter = BluetoothAdapter.getDefaultAdapter();

		mArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
		// ������� Ȱ��ȭ
		if (!mBTAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}

		Set<BluetoothDevice> pairedDevices = mBTAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				mArrayAdapter
						.add(device.getName() + "\n" + device.getAddress());
			}
		}

		mConversationView = findViewById(R.id.in);
		mConversationView.setAdapter(mArrayAdapter);

		mConversationView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				Intent btIntent = new Intent(MainActivity.this,
						BTControll.class);
				btIntent.putExtra("mac", arg0.getAdapter().getItem(arg2)
						.toString().split("\n")[1]);
				startActivity(btIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

}