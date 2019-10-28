package com.niit.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class FileNotesActivity extends Activity {
	final String LOG_TAG = "myLogs";
	final String FILENAME = "file";
	final String DIR_SD = "MyFiles";
	final String FILENAME_SD = "fileSD";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void onclick(View v) {
		switch (v.getId()) {
			case R.id.btnWrite:
				writeFile();
				break;
			case R.id.btnRead:
				readFile();
				break;
			case R.id.btnWriteSD:
				writeFileSD();
				break;
			case R.id.btnReadSD:
				readFileSD();
				break;
		}
	}

	void writeFile() {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					openFileOutput(FILENAME, MODE_PRIVATE)));
			bw.write("File Contents");
			bw.close();
			Toast.makeText(getApplicationContext(), "Success!",Toast.LENGTH_LONG).show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void readFile() {
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(
					openFileInput(FILENAME)));
			String str = "";

			while ((str = br.readLine()) != null) {
				Toast.makeText(getApplicationContext(), str,Toast.LENGTH_LONG).show();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void writeFileSD() {
		String state = Environment.getExternalStorageState();
		if (!Environment.MEDIA_MOUNTED.equals(state)) {
			Toast.makeText(getApplicationContext(), "External Storage is not available!",Toast.LENGTH_LONG).show();
			return;
		}

		String root = Environment.getExternalStorageDirectory().toString();
		File dir = new File (root + "/download");
		dir.mkdirs();
		File file = new File(dir, FILENAME_SD);

		try {
			FileOutputStream f = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(f);
			pw.println("Hi , How are you");
			pw.println("Hello");
			pw.flush();
			pw.close();
			f.close();
			Toast.makeText(getApplicationContext(), "Success!",Toast.LENGTH_LONG).show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void readFileSD() {

		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Log.d(LOG_TAG, "External storage is not available " + Environment.getExternalStorageState());
			return;
		}

		String root = Environment.getExternalStorageDirectory().toString();
		File dir = new File (root + "/download");
		File sdFile = new File(dir, FILENAME_SD);
		try {

			BufferedReader br = new BufferedReader(new FileReader(sdFile));
			String str = "";

			while ((str = br.readLine()) != null) {
				Toast.makeText(getApplicationContext(), str,Toast.LENGTH_LONG).show();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}