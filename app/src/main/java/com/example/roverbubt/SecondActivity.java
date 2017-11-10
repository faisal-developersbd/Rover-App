package com.example.roverbubt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



import java.net.UnknownHostException;








import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends Activity implements View.OnTouchListener{
	Button up,down,left,right,up_right,up_left,down_left,down_right,warn,mode,stop;
	Button corrent_btn = null;
	boolean error=false;
	String rep="";
	Socket socket=null;
	String _ip;
	String _port;
	int flag=0;
	boolean isButtonPressed=false;
	public void error_print()
	{
		if(rep.equals("ERROR"))
		{
			warn.setBackgroundResource(R.drawable.warn1);
			warn.getBackground().setAlpha(255);
			Toast.makeText(getBaseContext(), "NETWORK ERROR!", Toast.LENGTH_LONG).show();
		}
		else if(rep.equals("ok"))
		{
			warn.setBackgroundResource(R.drawable.warning_blue);
			warn.getBackground().setAlpha(255);
		}
	}
	String msg="Error";
//	public void data_recieve()
//	{
//		
//try{
//		ServerSocket socket2 = new ServerSocket(80);
//		Socket clientSocket = socket2.accept();       //This is blocking. It will wait.
//		DataInputStream DIS = new DataInputStream(clientSocket.getInputStream());
//		msg = DIS.readUTF();
//		clientSocket.close();
//		Toast.makeText(getBaseContext(), "Server Says: "+msg, Toast.LENGTH_LONG);
//}
//catch(Exception e)
//{
//	Toast.makeText(getBaseContext(), "Server Error: "+e, Toast.LENGTH_LONG);
//}

//	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//requestWindowFeature(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_second);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//requestWindowFeature(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		_ip=getIntent().getStringExtra("IP");
		_port=getIntent().getStringExtra("PORT");
		up=(Button) findViewById(R.id.btn_up);
		down=(Button) findViewById(R.id.btn_down);
		left=(Button) findViewById(R.id.btn_left);
		right=(Button) findViewById(R.id.btn_right);
		up_left=(Button) findViewById(R.id.btn_upleft);
		down_left=(Button) findViewById(R.id.btn_downleft);
		up_right=(Button) findViewById(R.id.btn_upright);
		down_right=(Button) findViewById(R.id.btn_downright);
		warn=(Button) findViewById(R.id.btn_warn);
		mode=(Button) findViewById(R.id.btn_mode);
		stop=(Button) findViewById(R.id.btn_stop);
		
		up.setOnTouchListener(this);
		down.setOnTouchListener(this);
		left.setOnTouchListener(this);
		right.setOnTouchListener(this);
		up_left.setOnTouchListener(this);
		up_right.setOnTouchListener(this);
		down_left.setOnTouchListener(this);
		down_right.setOnTouchListener(this);
		mode.setOnTouchListener(this);
		//warn.setOnTouchListener(this);
		stop.setOnTouchListener(this);
		
		Toast.makeText(getBaseContext(),"Corrent Ip:"+_ip+" & Port"+_port, Toast.LENGTH_LONG).show();;
		warn.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View view) {
				// TODO Auto-generated method stub
				for(int i=0;i<=2;i++)
				{
				flag=0;
				new HttpRequestAsynTask_connect(
						view.getContext(),null,_ip,_port
					).execute();
				}
				Intent intent=new Intent(SecondActivity.this,First_Activity.class);
				startActivity(intent);
				return false;
			}
		});
		warn.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				flag=1;
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					warn.getBackground().setAlpha(100);
					
					break;
				case MotionEvent.ACTION_UP:
					if(isButtonPressed==false)
					{
						
					new HttpRequestAsynTask_connect(
							v.getContext(),null,_ip,_port
						).execute();
					error_print();
						
						isButtonPressed=true;
						//error_print();
					}
					error_print();
//					else{
//						new HttpRequestAsynTask_connect(
//								v.getContext(),null,_ip,_port
//							).execute();
//						error_print();
//					}
					//error_print();
					warn.getBackground().setAlpha(255);
					break;
				case MotionEvent.ACTION_MOVE:
	
					break;
	
				
				}
				return false;
			}
		});
		
		
		
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
		public String sendRequest_openport(String ip_address,String port_number) 
		{
			String serverResponse="ERROR";
			
try{
			 socket = new Socket(ip_address,Integer.parseInt(port_number));
			 flag=1;
			// Toast.makeText(getBaseContext(), "CONNECTED", Toast.LENGTH_LONG).show();
			 error=false;
			 serverResponse="ok";
			
			 return serverResponse;
}catch(Exception e)
{
	error=true;
	
	return serverResponse;
}
			 
			 
			
			
		}
		public String sendRequest_close() throws UnknownHostException, IOException
		{
			
			String serverResponse="ERROR";
			
			
			try{
			 socket.close();
			 flag=0;
			 error=false;
			 Toast.makeText(getBaseContext(), "CONNECTED", Toast.LENGTH_LONG).show();
			 serverResponse="ok";
			}catch(Exception e)
			{
				//Toast.makeText(getBaseContext(), "NETWORK ERROR !", Toast.LENGTH_LONG).show();
			error=true;
			}

			 
			 
			return serverResponse;
			
		}
		public String sendRequest_wrData(String parameterValue,String ip_address) throws UnknownHostException, IOException
		{
			String serverResponse="ERROR";
			try{
			DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
			DOS.writeUTF(parameterValue);

//			DataInputStream DIS = new DataInputStream(socket.getInputStream());
//			msg = DIS.readUTF().toString();
			flag=2;
			error=false;
			serverResponse="ok";
			// data_recieve();
			}catch(Exception e)
			{
				//Toast.makeText(getBaseContext(), "NETWORK ERROR !", Toast.LENGTH_LONG).show();
			error=true;
			serverResponse="ERROR";
			msg="Error: "+e;
			}
			 
			 
			return serverResponse;
			
		}
		

		@Override
		protected Void doInBackground(Void... voids) {
			// TODO Auto-generated method stub
			try {
				if(flag==0)
				requestReplay=sendRequest_close();
				else if(flag==1){
					requestReplay=sendRequest_openport(ip_address,port_name);
					rep=requestReplay;
					
				}
				else if(flag==2)
					requestReplay=sendRequest_wrData(parametervalue,ip_address);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//Toast.makeText(getBaseContext(), "NETWORK ERROR", Toast.LENGTH_LONG).show();
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		
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
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		String parameterValue="";
		
		if(v.getId()==up.getId())
		{
			parameterValue="U";
			corrent_btn=up;
			
		}
		if(v.getId()==down.getId())
		{
			parameterValue="D";
			corrent_btn=down;
		}if(v.getId()==left.getId())
		{
			parameterValue="L";
			corrent_btn=left;
		}if(v.getId()==right.getId())
		{
			parameterValue="R";
			corrent_btn=right;
		}if(v.getId()==up_left.getId())
		{
			parameterValue="1";
			corrent_btn=up_left;
		}if(v.getId()==up_right.getId())
		{
			parameterValue="2";
			corrent_btn=up_right;
		}if(v.getId()==down_left.getId())
		{
			parameterValue="3";
			corrent_btn=down_left;
		}if(v.getId()==down_right.getId())
		{
			parameterValue="4";
			corrent_btn=down_right;
		}
//		if(v.getId()==warn.getId())
//		{
//			parameterValue="W";
//			corrent_btn=warn;
//		}
		if(v.getId()==mode.getId())
		{
			parameterValue="M";
			corrent_btn=mode;
		}if(v.getId()==stop.getId())
		{
			parameterValue="S";
			corrent_btn=stop;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			corrent_btn.getBackground().setAlpha(100);
			
			break;
		case MotionEvent.ACTION_UP:
			flag=2;
			new HttpRequestAsynTask_connect(
					v.getContext(),parameterValue,_ip,_port
				).execute();
			error_print();
		    if(parameterValue.equals("M"))
		    {
		    	Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		    	// Vibrate for 400 milliseconds
		    	vb.vibrate(500);
		    	Toast.makeText(getBaseContext(), "Vibrating", Toast.LENGTH_LONG).show();;
		    	
		    }
//		    if(msg.equals("HI"))
//		    {
//		    	Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//
//		    	// Vibrate for 400 milliseconds
//		    	vb.vibrate(500);
//		    }
		    
		    	try{
		    	
		    	Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
		    	}
		    	catch(Exception e)
		    	{
		    		Toast.makeText(getBaseContext(), "Error: "+e, Toast.LENGTH_LONG).show();
		    	}
		    	
		    
			corrent_btn.getBackground().setAlpha(255);
			
			break;

		case MotionEvent.ACTION_MOVE:
			break;
		}
		return false;
	}
	
}
