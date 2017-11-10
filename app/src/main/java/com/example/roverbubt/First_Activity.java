package com.example.roverbubt;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;




import java.nio.channels.GatheringByteChannel;

import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class First_Activity extends ActionBarActivity implements View.OnClickListener{
	int flag=0;
	String rep;
	Button connect_btn;
	EditText ip_txt;
	EditText port_txt;
	Socket socket=null;
	boolean error=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_first_);
		connect_btn=(Button) findViewById(R.id.btn_connect);
		ip_txt=(EditText) findViewById(R.id.txt_ip);
		port_txt=(EditText) findViewById(R.id.txt_port);
		connect_btn.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				String ip_address="";
				String port="";
				switch (event.getAction()) {
				
				case MotionEvent.ACTION_DOWN:
					connect_btn.getBackground().setAlpha(100);	
					
// 					if(ip_address.length()>0 && port.length()>0)
// 					{
// 						new HttpRequestAsynTask_connect(
// 							v.getContext(),null,ip_address,port
// 						).execute();
// 					}
					break;
				case MotionEvent.ACTION_UP:
					flag=1;
					 ip_address=ip_txt.getText().toString().trim();
					 port=port_txt.getText().toString().trim();
					if(ip_address.length()>0 && port.length()>0)
					{
 					Intent intent=new Intent(First_Activity.this,SecondActivity.class);
 					ip_address=ip_txt.getText().toString().trim();
 					port=port_txt.getText().toString().trim();
 					intent.putExtra("IP", ip_address);
 					intent.putExtra("PORT", port);
 					
 					
 					startActivity(intent);
					}
					else
					{
						Toast.makeText(getBaseContext(), "TYPE IP AND PORT NUMBER", Toast.LENGTH_LONG).show();
					}
 					connect_btn.getBackground().setAlpha(255);
					break;

				case MotionEvent.ACTION_MOVE:
					break;
				}
				return false;
			}
		});
		
	}
	boolean presstwich=false;
	@Override
	public void onBackPressed() {
		if(presstwich==true)
		{
			Intent intent=new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			System.exit(0);
		}
		presstwich=true;
		Toast.makeText(getBaseContext(), "press BACK button again to exit", Toast.LENGTH_LONG).show();;
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				presstwich=false;
				
			}
		}, 2000);
	}
	private class HttpRequestAsynTask_connect extends AsyncTask<Void, Void, Void>
	{
		private String ip_address,requestReplay,port_name;
		private Context context;
		private AlertDialog alartdialog;
		private String parameter;
		private String parametervalue;
		
		public HttpRequestAsynTask_connect(Context context, String parametervalue,
				String ip_address, String port_name) {
			// TODO Auto-generated constructor stub
			this.context=context;
			this.ip_address=ip_address;
			this.parametervalue=parametervalue;
			this.port_name=port_name;
			
		}
		public String sendRequest_openport(String parameterValue,String ip_address,String port_number,String parameter_name) throws UnknownHostException, IOException
		{
			String serverResponse="ERROR";
			try{

			 socket = new Socket(ip_address,Integer.parseInt(port_number));
			 flag=1;
			// Toast.makeText(getBaseContext(), "CONNECTED", Toast.LENGTH_LONG).show();
			 error=false;
			}catch(Exception e)
			{
			//	Toast.makeText(getBaseContext(), "NETWORK ERROR !", Toast.LENGTH_LONG).show();
				error=true;
			}

			 
			 
			return serverResponse;
			
		}
		public String sendRequest_close(String parameterValue,String ip_address,String port_number,String parameter_name) throws UnknownHostException, IOException
		{
			
			String serverResponse="ERROR";
			
			
			try{
			 socket.close();
			 flag=0;
			 error=false;
			 Toast.makeText(getBaseContext(), "CONNECTED", Toast.LENGTH_LONG).show();
			}catch(Exception e)
			{
				Toast.makeText(getBaseContext(), "NETWORK ERROR !", Toast.LENGTH_LONG).show();
			error=true;
			}

			 
			 
			return serverResponse;
			
		}
		public String sendRequest_wrData(String parameterValue,String ip_address,String port_number,String parameter_name) throws UnknownHostException, IOException
		{
			String serverResponse="ERROR";
			try{
			DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
			DOS.writeUTF(parameterValue);
			flag=2;
			error=false;
			}catch(Exception e)
			{
				Toast.makeText(getBaseContext(), "NETWORK ERROR !", Toast.LENGTH_LONG).show();
			error=true;
			}
			 
			 
			return serverResponse;
			
		}

		@Override
		protected Void doInBackground(Void... voids) {
			// TODO Auto-generated method stub
			try {
				if(flag==0)
				requestReplay=sendRequest_close(parametervalue,ip_address,port_name,parameter);
				else if(flag==1){
					requestReplay=sendRequest_openport(parametervalue,ip_address,port_name,parameter);
					rep=requestReplay;
				}
				else if(flag==3)
					requestReplay=sendRequest_wrData(parametervalue,ip_address,port_name,parameter);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		protected void onPostExecution(Void avoid)
		{
			//alartdialog.setMessage(requestReplay);
		}
		protected void onPreExecution() {
			//alartdialog.setMessage("Sending data to server... Pleasewait");
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first_, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
