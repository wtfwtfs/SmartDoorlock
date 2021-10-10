package com.example.inu_smartdoor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.os.CountDownTimer;

public class BTControll<Study_3> extends com.example.inu_smartdoor.MainActivity {

	private String connectedDeviceName;
	private BluetoothAdapter btAdapter = null;
	private BluetoothDevice btDevice = null;
	private BluetoothSocket btSocket = null;
	private OutputStream btos;
	private InputStream btis;

	String pass;
	String ans = "";
	Button btn1;
	Button btn2;
	Button btn3;
	Button btn4;
	Button btn5;
	Button btn6;
	Button btn7;
	Button btn8;
	Button btn9;
	Button btn0;
	Button reset;
	Button enter;
	Button cam;
	int re = 0;
	int openAndclose = 0;
	int wrongNum = 0;
	CountDownTimer mCountDown = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn1 = findViewById(R.id.button1);
		btn2 = findViewById(R.id.button2);
		btn3 = findViewById(R.id.button3);
		btn4 = findViewById(R.id.button4);
		btn5 = findViewById(R.id.button5);
		btn6 = findViewById(R.id.button6);
		btn7 = findViewById(R.id.button7);
		btn8 = findViewById(R.id.button8);
		btn9 = findViewById(R.id.button9);
		btn0 = findViewById(R.id.button0);
		cam = findViewById(R.id.buttoncam);
		reset = findViewById(R.id.buttonr);
		enter = findViewById(R.id.buttonE);

        getPreferences();
		setListener();
		
	}

	private void getPreferences() {
		SharedPreferences mPref = getSharedPreferences("pref", MODE_PRIVATE);
		pass = mPref.getString("pass", "0000");
	}

	public void setListener() {
		try {
			openBT();
			Toast.makeText(getApplicationContext(), "open Success",
					Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "open Failed",
					Toast.LENGTH_LONG).show();
			finish();

		}

		btn1.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "1";

				}
				return false;
			}
		});
		btn2.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "2";

				}
				return false;
			}
		});
		btn3.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "3";

				}
				return false;
			}
		});
		btn4.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "4";

				}
				return false;
			}
		});
		btn5.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "5";

				}
				return false;
			}
		});
		btn6.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "6";

				}
				return false;
			}
		});
		btn7.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "7";

				}
				return false;
			}
		});
		btn8.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "8";

				}
				return false;
			}
		});
		btn9.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "9";

				}
				return false;
			}
		});
		btn0.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "0";

				}
				return false;
			}
		});
		enter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (re == 1) {
					Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다.",
							Toast.LENGTH_SHORT).show();
					SharedPreferences mPref = getSharedPreferences("pref",
							MODE_PRIVATE);
					SharedPreferences.Editor editor = mPref.edit();
					editor.remove("pass");
					editor.putString("pass", ans);
					editor.commit();
					pass = ans;
					re = 2;

				} else {
					if (pass.equals(ans)) {
						if(openAndclose == 0){
                           	write("a");
							reset.setEnabled(true);
							Toast.makeText(getApplicationContext(), "열렸습니다.",
									Toast.LENGTH_SHORT).show();
							openAndclose = 1;
							wrongNum = 0;
						}
						else{
							write("a");
							//sign = 'a';
							reset.setEnabled(true);
							Toast.makeText(getApplicationContext(), "닫혔습니다.",
									Toast.LENGTH_SHORT).show();
							openAndclose = 0;
							wrongNum = 0;
						}
					} else {
						Toast.makeText(getApplicationContext(), "틀렸습니다.",
								Toast.LENGTH_SHORT).show();
						reset.setEnabled(false);
						wrongNum++;
						if(wrongNum == 5){
							btn1.setEnabled(false);
							btn2.setEnabled(false);
							btn3.setEnabled(false);
							btn4.setEnabled(false);
							btn5.setEnabled(false);
							btn6.setEnabled(false);
							btn7.setEnabled(false);
							btn8.setEnabled(false);
							btn9.setEnabled(false);
							btn0.setEnabled(false);
							enter.setEnabled(false);
							shutdown();
						}
					}
				}
				ans = "";
			}
		});
		reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				re = 1;
				reset.setEnabled(false);
			}
		});
		
		cam.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), webcam.class);
				startActivity(intent);
			}
		});
		
	}
	
	private void shutdown() {
		final Toast toast1 = Toast.makeText(getApplicationContext(), "잠시 후 다시 시도해주세요.",
				Toast.LENGTH_SHORT);
		mCountDown = new CountDownTimer(10000, 1000) {
			public void onTick(long millisUntilFinished) {
				toast1.show();
			}

			public void onFinish() {
				toast1.cancel();
				btn1.setEnabled(true);
				btn2.setEnabled(true);
				btn3.setEnabled(true);
				btn4.setEnabled(true);
				btn5.setEnabled(true);
				btn6.setEnabled(true);
				btn7.setEnabled(true);
				btn8.setEnabled(true);
				btn9.setEnabled(true);
				btn0.setEnabled(true);
				enter.setEnabled(true);
				wrongNum = 0;
			}
		}.start();
	}
	public void openBT() throws IOException {
		Intent intent = getIntent();

		connectedDeviceName = intent.getExtras().getString("mac");
		btAdapter = BluetoothAdapter.getDefaultAdapter();

		Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {

				if (device.getAddress().equals(connectedDeviceName)) {
					btDevice = device;
					break;
				}
			}
		}

		UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
		btSocket = btDevice.createRfcommSocketToServiceRecord(uuid);
		btSocket.connect();

		Toast.makeText(getApplicationContext(),
				String.valueOf(btSocket.isConnected()), Toast.LENGTH_LONG)
				.show();

		btos = btSocket.getOutputStream();
		btis = btSocket.getInputStream();

	}

	public void closeBt() throws IOException {
		btos.close();
		btis.close();
		btSocket.close();

	}

	public void write(String str) {
		try {
			btos.write(str.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
