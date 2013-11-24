package com.photolocator.cassandra;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class CassandraFunction {

	private AsyncHttpClient client;
	private CassandraCallback ccb;

	public static String USERNAME = "user_name";
	public static String PASSWORD = "passwd";
	public static String EMAIL = "email";
	public static String PHOTONAME = "photo_name";
	public static String PHOTO = "photo";
	public static String LOCATION = "location";
	public static String CELLPHONETYPE = "cellphoneType";
	public static String LATITUDE = "latitude";
	public static String LONGITUDE = "longitude";
	public static String ALTITUDE = "altitude";
	public static String TIME = "time";

	// public static String
	public static String USERURL = "http://142.157.168.132:8080/virgil/data/photolocator/user/";
	public static String DATAURL = "http://142.157.168.132:8080/virgil/data/photolocator/data/";

	public CassandraFunction(CassandraCallback ccb) {
		client = new AsyncHttpClient();
		this.ccb = ccb;
	}

	public void retrivePassword(Context context, String username) {
		String url = USERURL + username;

		client.get(context, url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode,
					org.apache.http.Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				// Log.i("CassandraLogin", response);
				// Toast.makeText(null, response, Toast.LENGTH_LONG).show();
				try {
					JSONObject json;
					if (responseBody != null) {
						json = new JSONObject(new String(responseBody));
						ccb.passwdRetrived(json.getString("passwd"));
					} else {
						ccb.passwdRetrived(null);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.loopj.android.http.JsonHttpResponseHandler#onFailure(java
			 * .lang.Throwable, org.json.JSONArray)
			 */
			@Override
			public void onFailure(Throwable e, String errorResponse) {
				// TODO Auto-generated method stub
				//Log.i("CassandraLogin", errorResponse);
				//Toast.makeText(null, errorResponse, Toast.LENGTH_LONG).show();
				ccb.passwdRetrived(null);
			}

		});
	}

	public void register(Context context, String username, String email,
			String password) {
		JSONObject json = new JSONObject();
		try {
			json.put(USERNAME, username);
			json.put(PASSWORD, password);
			json.put(EMAIL, email);
			String url = USERURL + username;
			StringEntity entity = new StringEntity(json.toString());
			Log.i("CassandraRegister", url);
			client.put(context, url, entity, null,
					new AsyncHttpResponseHandler() {

						/*
						 * (non-Javadoc)
						 * 
						 * @see
						 * com.loopj.android.http.JsonHttpResponseHandler#onSuccess
						 * (int, org.json.JSONArray)
						 */
						@Override
						public void onSuccess(int i, String response) {
							// TODO Auto-generated method stub
							ccb.registered(true);
						}

						/*
						 * (non-Javadoc)
						 * 
						 * @see
						 * com.loopj.android.http.JsonHttpResponseHandler#onFailure
						 * (java.lang.Throwable, org.json.JSONArray)
						 */
						@Override
						public void onFailure(Throwable e, String errorResponse) {
							// TODO Auto-generated method stub
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

	public void insertData(Context context, CassandraDataUnit cd) {
		JSONObject json = new JSONObject();
		try {
			String username = cd.getUserName();
			String photoName = cd.getPhotoName();
			Bitmap bitmap = cd.getBitmap();
			Location location = cd.getLocation();
			String locationName = cd.getLocationName();
			Date time = cd.getTime();
			String cellphoneType = cd.getCellphoneType();
			json.put(USERNAME, username);
			json.put(PHOTONAME, photoName);
			json.put(CELLPHONETYPE, cellphoneType);
			
			ByteArrayOutputStream baos=new  ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte [] b=baos.toByteArray();
            String temp=Base64.encodeToString(b, Base64.DEFAULT);
			json.put(PHOTO, temp);

			json.put(LOCATION, locationName);
			if(location!=null){
				json.put(ALTITUDE, location.getAltitude());
				json.put(LATITUDE, location.getLatitude());
				json.put(LONGITUDE, location.getLongitude());
			}
			json.put(TIME, String.valueOf((time.getTime())));

			String url = DATAURL + username;
			StringEntity entity = new StringEntity(json.toString());
			Log.i("CassandraInserData", url+":"+json.toString());
			client.put(context, url, entity, null,
					new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int i, String response) {
							// TODO Auto-generated method stub
							// Log.i("CassandraRegister", response);
							// Toast.makeText(null, "Success",
							// Toast.LENGTH_LONG).show();
							ccb.dataInserted(true);
						}

						@Override
						public void onFailure(Throwable e, String errorResponse) {
							// TODO Auto-generated method stub
							// Log.i("CassandraRegister", errorResponse);
							// Toast.makeText(null, "Failure",
							// Toast.LENGTH_LONG).show();
							ccb.dataInserted(false);
						}

					});
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void readDataByUserName(Context context, String username) {
		String url = DATAURL + username;
		client.get(context, url, new CDUHandler());
	}

	public void readDataByPhotoName(Context context, String photoName) {
		String url = DATAURL + photoName;
		client.get(context, url, new CDUHandler());
	}

	public void readDataByLocation(Location location, double range) {
		
	}
	
	private class CDUHandler extends AsyncHttpResponseHandler{

		@Override
		public void onSuccess(int statusCode,
				org.apache.http.Header[] headers, byte[] responseBody) {
			// TODO Auto-generated method stub
			// Log.i("CassandraLogin", response);
			// Toast.makeText(null, response, Toast.LENGTH_LONG).show();
			ArrayList<CassandraDataUnit> cdus = new ArrayList<CassandraDataUnit>();
			try {
				JSONArray jsonarray;
				JSONObject jsonobj;
				if (responseBody != null) {
					try{
						jsonarray = new JSONArray(new String(responseBody));
						for (int i = 0; i < jsonarray.length(); i++) {
							CassandraDataUnit cdu = new CassandraDataUnit();
							JSONObject json = jsonarray.getJSONObject(i);
							try {
								if (json.getString(USERNAME) != null) {
									cdu.setUserName(json.getString(USERNAME));
								}
							} catch (JSONException je) {
								je.printStackTrace();
							}
							try {
								if (json.getString(PHOTONAME) != null) {
									cdu.setPhotoName(json.getString(PHOTONAME));
								}
							} catch (JSONException je) {
								je.printStackTrace();
							}
							try {
								if (json.getString(PHOTO) != null) {
									String photoString = json.getString(PHOTO);
									byte [] encodeByte=Base64.decode(photoString,Base64.DEFAULT);
								    Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
									cdu.setBitmap(bitmap);
								}
							} catch (JSONException je) {
								je.printStackTrace();
							}
							try {
								if (json.getString(LOCATION) != null) {
									cdu.setLocationName(json
											.getString(LOCATION));
								}
							} catch (JSONException je) {
								je.printStackTrace();
							}
							try {
								if (json.getString(CELLPHONETYPE) != null) {
									cdu.setCellphoneType(json.getString(CELLPHONETYPE));
								}
							} catch (JSONException je) {
								je.printStackTrace();
							}
							try {
								Location location = new Location(
										"Photo Locator");
								location.setAltitude(json.getDouble(ALTITUDE));
								location.setLatitude(json.getDouble(LATITUDE));
								location.setLongitude(json.getDouble(LONGITUDE));
								cdu.setLocation(location);
							} catch (JSONException je) {
								je.printStackTrace();
							}
							try {
								cdu.setTime(new Date(json.getLong(TIME)));
							} catch (JSONException je) {
								je.printStackTrace();
							}
							cdus.add(cdu);
						}
					}
					catch(Exception e){
						jsonobj=new JSONObject(new String(responseBody));
						CassandraDataUnit cdu = new CassandraDataUnit();
						JSONObject json = jsonobj;
						try {
							if (json.getString(USERNAME) != null) {
								cdu.setUserName(json.getString(USERNAME));
							}
						} catch (JSONException je) {
							je.printStackTrace();
						}
						try {
							if (json.getString(PHOTONAME) != null) {
								cdu.setPhotoName(json.getString(PHOTONAME));
							}
						} catch (JSONException je) {
							je.printStackTrace();
						}
						try {
							if (json.getString(PHOTO) != null) {
								String photoString = json.getString(PHOTO);
								byte [] encodeByte=Base64.decode(photoString,Base64.DEFAULT);
							    Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
								cdu.setBitmap(bitmap);
							}
						} catch (JSONException je) {
							je.printStackTrace();
						}
						try {
							if (json.getString(LOCATION) != null) {
								cdu.setLocationName(json
										.getString(LOCATION));
							}
						} catch (JSONException je) {
							je.printStackTrace();
						}
						try {
							if (json.getString(CELLPHONETYPE) != null) {
								cdu.setCellphoneType(json.getString(CELLPHONETYPE));
							}
						} catch (JSONException je) {
							je.printStackTrace();
						}
						try {
							Location location = new Location(
									"Photo Locator");
							location.setAltitude(json.getDouble(ALTITUDE));
							location.setLatitude(json.getDouble(LATITUDE));
							location.setLongitude(json.getDouble(LONGITUDE));
							cdu.setLocation(location);
						} catch (JSONException je) {
							je.printStackTrace();
						}
						try {
							cdu.setTime(new Date(json.getLong(TIME)));
						} catch (JSONException je) {
							je.printStackTrace();
						}
						cdus.add(cdu);
					}
					
					ccb.dataReaded(cdus);
				} else
					ccb.dataReaded(null);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void onFailure(Throwable e, String errorResponse) {
			// TODO Auto-generated method stub
			Log.i("CassandraLogin", errorResponse);
			Toast.makeText(null, errorResponse, Toast.LENGTH_LONG).show();
			ccb.dataReaded(null);
		}
	}
}
