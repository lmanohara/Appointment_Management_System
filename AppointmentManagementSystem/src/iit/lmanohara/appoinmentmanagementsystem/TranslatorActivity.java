package iit.lmanohara.appoinmentmanagementsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TranslatorActivity extends Activity implements TextWatcher,
		OnClickListener {

	private Appointment _appointment;
	private TextView textViewAppList;
	private String date;
	private EditText editTextAppNo;
	private Button buttonTranUpdate;

	// ------------------------------------

	private static final String TAG = "TranslateTask";
	private Spinner fromSpinner;
	private Spinner toSpinner;
	private TextView origText;
	private TextView transText;
	private String fromLang;
	private String toLang;
	private TextWatcher textWatcher;
	private OnItemSelectedListener itemListener;
	private String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_translator);
		date = getIntent().getStringExtra("DATE");
		_appointment = new Appointment();
		_appointment.setDate(date);

		textViewAppList = (TextView) findViewById(R.id.textView_appList_translate);
		_appointment.addAppList();
		displayAppList();

		editTextAppNo = (EditText) findViewById(R.id.editText_appNo_Translate);
		editTextAppNo.addTextChangedListener(this);

		buttonTranUpdate = (Button) findViewById(R.id.button_update_translate);
		buttonTranUpdate.setOnClickListener(this);

		findViews();
		setAdapters();

	}

	private void findViews() {
		fromSpinner = (Spinner) findViewById(R.id.from_language);
		toSpinner = (Spinner) findViewById(R.id.to_language);
		origText = (TextView) findViewById(R.id.textView_fromlan_text);
		transText = (TextView) findViewById(R.id.translated_text);
	}

	/** Define data source for the spinners */
	private void setAdapters() {
		CharSequence[] itemArray = getResources().getTextArray(
				R.array.languages);
		List<CharSequence> itemList = new ArrayList<CharSequence>(
				Arrays.asList(itemArray));

		// Spinner list comes from a resource,
		/*
		 * ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
		 * this, R.array.languages, android.R.layout.simple_spinner_item);
		 */

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, itemList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		fromSpinner.setAdapter(adapter);
		toSpinner.setAdapter(adapter);
		// Automatically select two spinner items
		fromSpinner.setSelection(1); // English (en)
		toSpinner.setSelection(2); // Spanish (es)
	}

	private void setListeners() {
		textWatcher = new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// String translation =
				// doTranslate(origText.getText().toString()
				// .trim(), fromLang, toLang);
				// transText.setText(translation);

			}

			public void afterTextChanged(Editable s) {

			}
		};
		origText.addTextChangedListener(textWatcher);
		itemListener = new OnItemSelectedListener() {
			public void onItemSelected(AdapterView parent, View v,
					int position, long id) {
				fromLang = getLang(fromSpinner);
				toLang = getLang(toSpinner);
				String translation = doTranslate(origText.getText().toString()
						.trim(), fromLang, toLang);
				transText.setText(translation);
			}

			public void onNothingSelected(AdapterView parent) {
				/* Do nothing */
			}
		}; // end of OnItemSelectedListener()
		fromSpinner.setOnItemSelectedListener(itemListener);
		toSpinner.setOnItemSelectedListener(itemListener);
	}

	/*
	 * Extract the language code from the current spinner item
	 */
	private String getLang(Spinner spinner) {
		String result = spinner.getSelectedItem().toString();
		int lparen = result.indexOf("(");
		int rparen = result.indexOf(")");
		result = result.substring(lparen + 1, rparen);
		return result;
	}

	private String doTranslate(final String original, final String from,
			final String to) {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					result = getResources().getString(
							R.string.translation_error);
					HttpURLConnection con = null;
					try {
						// Build RESTful query for Google API
						String q = URLEncoder.encode(original, "UTF-8");

						URL url = new URL(
								"http://api.apertium.org/json/translate"
										+ "?q=" + q + "&langpair=" + from
										+ "%7C" + to);
						// ----------------------------
						Log.d("url", url.toString());
						// -----------------------------
						con = (HttpURLConnection) url.openConnection();
						con.setReadTimeout(10000 /* milliseconds */);
						con.setConnectTimeout(15000 /* milliseconds */);
						con.setRequestMethod("GET");
						con.addRequestProperty("Referer",
								"http://www.pragprog.com/hello-android");
						con.setDoInput(true);
						// Start the query
						con.connect();

						// Read results from the query
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(con.getInputStream(),
										"UTF-8"));
						String payload = reader.readLine();
						reader.close();
						// Parse to get translated text
						JSONObject jsonObject = new JSONObject(payload);
						result = jsonObject.getJSONObject("responseData")
								.getString("translatedText")
								.replace("&#39;", "’").replace("&amp;", "&");
					}

					catch (IOException e) {
						Log.e(TAG, "IOException", e);
					} catch (JSONException e) {
						Log.e(TAG, "JSONException", e);
					} finally {
						if (con != null) {
							con.disconnect();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();

		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pref, menu);
		return true;
	}

	public void displayAppList() {

		for (Appointment appointment : _appointment.getAppointment()) {
			textViewAppList.append("\n" + appointment.toString());
			// Log.d("this", "working");

		}

	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// -----------------------------------------------------------
		if (!editTextAppNo.getText().toString().equals("")) {
			_appointment.setSelectedApp(Integer.parseInt(editTextAppNo
					.getText().toString()));

		}

		origText.setText(_appointment.getSelectedApp().getDetail());
		setListeners();

		// ------------------------------------------------------------

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button_update_translate:
			updateValues();
			break;

		default:
			break;
		}

	}

	public void updateValues() {

		if (!transText.getText().toString().equals("null")) {
			_appointment.setDetail(transText.getText().toString());
			_appointment.updateValuesTrans();
		}
	}

	 @Override
	   public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	      case R.id.action_settings:
	         startActivity(new Intent(this, PrefActivity.class));
	         return true;
	      // More items go here (if any) ...
	      }
	      return false;
	   }

}
