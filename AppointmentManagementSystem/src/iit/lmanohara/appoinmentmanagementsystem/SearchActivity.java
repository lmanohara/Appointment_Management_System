package iit.lmanohara.appoinmentmanagementsystem;

import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends Activity implements OnClickListener,
		TextWatcher {

	private EditText editTextSearch;
	private TextView textViewSearchResult;
	private Button buttonSearch;
	private String keyWord;
	private EditText editTextAppNo;

	private Appointment _appointment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		_appointment = new Appointment();
		_appointment.addAppSearchList();
		editTextSearch = (EditText) findViewById(R.id.editText_search);
		textViewSearchResult = (TextView) findViewById(R.id.textView_search_result);
		buttonSearch = (Button) findViewById(R.id.button_search_app);
		buttonSearch.setOnClickListener(this);
		editTextAppNo = (EditText) findViewById(R.id.editText_searchAppNo);
		editTextAppNo.addTextChangedListener(this);

	}



	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button_search_app:
			search();
			break;

		default:
			break;
		}

	}

	public void search() {
		textViewSearchResult.setText("");
		keyWord = editTextSearch.getText().toString();

		for (Appointment appointment : _appointment.getAppointment()) {

			if (Pattern
					.compile(Pattern.quote(keyWord), Pattern.CASE_INSENSITIVE)
					.matcher(appointment.getTitle()).find()
					|| Pattern
							.compile(Pattern.quote(keyWord),
									Pattern.CASE_INSENSITIVE)
							.matcher(appointment.getDetail()).find()) {

				textViewSearchResult.append("\n" + appointment.toString());

			}
			// Log.d("this", "working");

		}

	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_NEUTRAL:
				
				break;
			}

		}
	};

	@Override
	public void afterTextChanged(Editable arg0) {
		if (!editTextAppNo.getText().toString().equals("")) {
			_appointment.setSelectedApp(Integer.parseInt(editTextAppNo
					.getText().toString()));

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"Date : " + _appointment.getSelectedApp().getDate() + "\n"
							+ "Time: "
							+ _appointment.getSelectedApp().getTime() + "\n"
							+ "Title: "
							+ _appointment.getSelectedApp().getTitle() + "\n"
							+ "Detail: "
							+ _appointment.getSelectedApp().getDetail())
					.setNeutralButton("Ok", dialogClickListener).show();

		}

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

}
