package com.photolocator.cassandra;

import java.util.Date;

import android.graphics.Bitmap;
import android.location.Location;

public class CassandraDataUnit {
	
	private String userName;
	private String photoName;
	private Bitmap bitmap;
	private Location location;
	private Date time;
	private String cellphoneType;
	
	public CassandraDataUnit(){
		userName=null;
		photoName=null;
		bitmap=null;
		location=null;
		time=null;
		cellphoneType=null;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the photoName
	 */
	public String getPhotoName() {
		return photoName;
	}

	/**
	 * @param photoName the photoName to set
	 */
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	/**
	 * @return the bitmap
	 */
	public Bitmap getBitmap() {
		return bitmap;
	}

	/**
	 * @param bitmap the bitmap to set
	 */
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the cellphoneType
	 */
	public String getCellphoneType() {
		return cellphoneType;
	}

	/**
	 * @param cellphoneType the cellphoneType to set
	 */
	public void setCellphoneType(String cellphoneType) {
		this.cellphoneType = cellphoneType;
	}
	
	
	
	
}
