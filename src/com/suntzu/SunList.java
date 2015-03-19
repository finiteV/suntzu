package com.suntzu;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.util.EncodingUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;;

public class SunList extends Activity {
	final int KEYCODE_BACK=4;   //返回按钮的值
	final int ACTION_DOWN = 0; //按下时的值 
	int QUIT=0, EXIT=1;                                               //推出状态
    ListView listView;
	String[] mulu;
	View showContent;
	View showChapter;
	View current;
	TextView NameTxt, ContentTxt;
	
	Handler myHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showChapter =  getLayoutInflater().inflate(R.layout.activity_sun_list,null);
		current = showChapter;
		setContentView(current);
		//
		mulu = getResources().getStringArray(R.array.chapters);
		listView = (ListView) showChapter.findViewById(R.id.show_list);
		//
		showContent = getLayoutInflater().inflate(R.layout.activity_show, null);
		NameTxt = (TextView) showContent.findViewById(R.id.name);
		ContentTxt = (TextView) showContent.findViewById(R.id.content);
		/********************/
		myHandler= new Handler(){
			public void handleMessage(Message msg){
				if(msg.what == EXIT){
					 System.exit(0);
				}	 
			}
		};
		/*******************/
		AdapterView.OnItemClickListener  listListener = new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				InputStream in = null;
				switch(position){
				    case 0:
					    in = getResources().openRawResource(R.raw.c1);
					    break;
			    	case 1:
					    in = getResources().openRawResource(R.raw.c2);
					    break;	
				    case 2:
					    in = getResources().openRawResource(R.raw.c3);
					    break;	
				    case 3:
						in = getResources().openRawResource(R.raw.c4);
						break;		
					case 4:
						in = getResources().openRawResource(R.raw.c5);
						break;	
					case 5:
						in = getResources().openRawResource(R.raw.c6);
						break;		
					case 6:
						in = getResources().openRawResource(R.raw.c7);
						break;		
					case 7:
						in = getResources().openRawResource(R.raw.c8);
						break;		
					case 8:
						in = getResources().openRawResource(R.raw.c9);
						break;		
					case 9:
						in = getResources().openRawResource(R.raw.c10);
						break;		
					case 10:
						in = getResources().openRawResource(R.raw.c11);
						break;		
					case 11:
						in = getResources().openRawResource(R.raw.c12);
						break;		
					case 12:
						in = getResources().openRawResource(R.raw.c13);
						break;	
					default:
						break;
				}
				NameTxt.setText( mulu[position]);
				String txt = fetchChapter(in);

				ContentTxt.setText(txt);
				current = showContent;
				setContentView(current);
			};
		};
		/***********************/
		listView.setOnItemClickListener(listListener);
	}

	String fetchChapter(InputStream in){
		String result = "";
		try {
			byte[] buff = new byte[in.available()];
			in.read(buff);
			in.close();
			result = EncodingUtils.getString(buff, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ContentTxt.setText("Error to load file");
		}
		return result;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {//处理键盘按下事件的回调方法
		if(event.getAction() == ACTION_DOWN && event.getKeyCode() == KEYCODE_BACK){
			if(current == showContent){
           	   current = showChapter;
           	   setContentView(current);
           	   QUIT = 0;
            }	 
            else{
           	   Toast tst = Toast.makeText(SunList.this, "再按一次返回键退出~", Toast.LENGTH_SHORT);
           	   tst.show();
           	   if(QUIT >= 1){
           		  Message m = myHandler.obtainMessage();
           		  m.what = EXIT;
           		  myHandler.sendMessage(m);
           	   }
           	 QUIT++;
            }
		}
		return true;	
	}

}
