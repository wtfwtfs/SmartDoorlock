package com.example.inu_smartdoor;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class webcam extends Activity {
	
String url;
Button btn;
EditText edit1;
WebView ipcamView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webcam);

		edit1 = findViewById(R.id.editText1);
		btn = findViewById(R.id.buttoncam);
		

		ipcamView = findViewById(R.id.webView1);
		ipcamView.getSettings().setJavaScriptEnabled(true);
		ipcamView.setWebViewClient(new WebViewClient());
		ipcamView.loadUrl("http://INUdoor5555.iptime.org:5555");
	
		
	}

	public void setListener() {

		btn.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					url = edit1.getText().toString();
					ipcamView.loadUrl(url);

				}
				return false;
			}
		});
	
	}

	public class IpcamViewClient extends WebViewClient{
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			
			return false;
		}
	}
	
}