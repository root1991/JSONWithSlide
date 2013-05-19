package com.root.json_test_pr.jsonparsing;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONStringsGetter {
	private JSONArray array;
	ArrayList<Model> data;

	public ArrayList<Model> getData(String jsonString) throws JSONException {
		data = new ArrayList<Model>();
		JSONObject object = new JSONObject(jsonString);
		array = object.getJSONArray(Constants.ITEMS);
		for(int i=0; i<array.length(); i++) {
			JSONObject currentObject = array.getJSONObject(i);
			long id = currentObject.getLong(Constants.ID);
			String title = currentObject.getString(Constants.TITLE);
			String body = currentObject.getString(Constants.BODY);
			String photoURL = currentObject.getString(Constants.PICTURE);
			data.add(new Model(id, title, body, photoURL));
		}
		
		return data;
	}	
}