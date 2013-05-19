package com.root.json_test_pr.jsonparsing;


public class Model {
	
	private long mId;
	public long getmId() {
		return mId;
	}

	public void setmId(long mId) {
		this.mId = mId;
	}

	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getmBody() {
		return mBody;
	}

	public void setmBody(String mBody) {
		this.mBody = mBody;
	}

	public String getmPicture() {
		return mPicture;
	}

	public void setmPicture(String mPicture) {
		this.mPicture = mPicture;
	}

	private String mTitle;
	private String mBody;
	private String mPicture;
	
	public Model(long id, String title, String body, String picture) {
		mId = id;
		mTitle = title;
		mBody = body;
		mPicture = picture;
	}
	

}
