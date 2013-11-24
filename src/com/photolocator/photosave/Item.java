package com.photolocator.photosave;

import android.graphics.Bitmap;

public class Item {
	private String id;
	private String title;
	private String desc;
	private String pubdate;
	private String link;
	private Bitmap bitmap;
	
	public Item(){
		id=null;
		title=null;
		desc=null;
		pubdate=null;
		link=null;
		bitmap=null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

}
