package com.photolocator.cassandra;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class CassandraFunction {
	
	private AsyncHttpClient client;
	private CassandraCallback ccb;
	
	public static String USERNAME="user_name";
	public static String PASSWORD="passwd";
	public static String EMAIL="email";
	public static String USERURL="http://142.157.168.132:8080/virgil/data/photolocator/user/";
	
	public CassandraFunction(CassandraCallback ccb){
		client = new AsyncHttpClient();
		this.ccb=ccb;
	}
	
	public void retrivePassword(Context context, String username){
					String url=USERURL + username;
					
					client.get(context, url, new AsyncHttpResponseHandler(){
						
						
						
						@Override
						public void onSuccess(int statusCode,org.apache.http.Header[] headers,byte[] responseBody) {
							// TODO Auto-generated method stub
							//Log.i("CassandraLogin", response);
							//Toast.makeText(null, response, Toast.LENGTH_LONG).show();
							try {
								JSONObject json=new JSONObject(new String(responseBody));
								if(responseBody!=null)
									System.out.println(json.toString());
								else
									System.out.println("null");
								ccb.passwdRetrived(json.getString("passwd"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
						/* (non-Javadoc)
						 * @see com.loopj.android.http.JsonHttpResponseHandler#onFailure(java.lang.Throwable, org.json.JSONArray)
						 */
						@Override
						public void onFailure(Throwable e, String errorResponse) {
							// TODO Auto-generated method stub
							Log.i("CassandraLogin", errorResponse);
							Toast.makeText(null, errorResponse, Toast.LENGTH_LONG).show();
							ccb.passwdRetrived(null);
						}
						
						
					});
	}
	
	public void register(Context context, String username, String email, String password){
		JSONObject json=new JSONObject();
		try {
				json.put(USERNAME, username);
				json.put(PASSWORD, password);
				json.put(EMAIL, email);
				String url=USERURL + username;
				StringEntity entity = new StringEntity(json.toString());
				Log.i("CassandraRegister", url);
				client.put(context, url, entity, null, new AsyncHttpResponseHandler(){
	
					/* (non-Javadoc)
					 * @see com.loopj.android.http.JsonHttpResponseHandler#onSuccess(int, org.json.JSONArray)
					 */
					@Override
					public void onSuccess(int i, String response) {
						// TODO Auto-generated method stub
						//Log.i("CassandraRegister", response);
						//Toast.makeText(null, "Success", Toast.LENGTH_LONG).show();
						ccb.registered(true);
					}
					
					
					/* (non-Javadoc)
					 * @see com.loopj.android.http.JsonHttpResponseHandler#onFailure(java.lang.Throwable, org.json.JSONArray)
					 */
					@Override
					public void onFailure(Throwable e, String errorResponse) {
						// TODO Auto-generated method stub
						//Log.i("CassandraRegister", errorResponse);
						//Toast.makeText(null, "Failure", Toast.LENGTH_LONG).show();
						ccb.registered(false);
					}
					
				});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertData(Context context, CassandraDataUnit cd){
		JSONObject json=new JSONObject();
		try {
			String username=cd.getUserName();
			String photoName=cd.getPhotoName();
			//Bit
				//json.put(USERNAME, username);
				//json.put(PASSWORD, password);
				//json.put(EMAIL, email);
				String url=USERURL + username;
				StringEntity entity = new StringEntity(json.toString());
				Log.i("CassandraRegister", url);
				client.put(context, url, entity, null, new AsyncHttpResponseHandler(){

					@Override
					public void onSuccess(int i, String response) {
						// TODO Auto-generated method stub
						//Log.i("CassandraRegister", response);
						//Toast.makeText(null, "Success", Toast.LENGTH_LONG).show();
						ccb.registered(true);
					}

					@Override
					public void onFailure(Throwable e, String errorResponse) {
						// TODO Auto-generated method stub
						//Log.i("CassandraRegister", errorResponse);
						//Toast.makeText(null, "Failure", Toast.LENGTH_LONG).show();
						ccb.registered(false);
					}
					
				});
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readDataByUserName(String username){

	}
	
	public void readDataByPhotoName(String photoName){

	}
	
	public void readDataByLocation(Location location, double range){

	}
	
}
