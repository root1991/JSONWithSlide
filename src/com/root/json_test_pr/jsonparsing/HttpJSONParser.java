package com.root.json_test_pr.jsonparsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpJSONParser {

	public HttpJSONParser() {

	}

	public String getJSON(String link) throws IOException {
		URL url = new URL(link);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.connect();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(urlConnection.getInputStream()));
		String jsonString = bufferedReader.readLine();
		return jsonString;
	}
}
