package iit.lmanohara.appoinmentmanagementsystem;

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

public class DeleteActivity extends Activity implements OnClickListener,
		TextWatcher {

	private String date;
	private Appointment _appointment;
	private Button buttonDeleteAll;
	private Button buttonSelectDelete;;
	private TextView textViewList;
	private EditText editTextAppNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete);

		date = getIntent().getStringExtra("DATE");
		_appointment = new Appointment();
		_appointment.setDate(date);
		_appointment.addAppList();

		buttonDeleteAll = (Button) findViewById(R.id.button_delete_all);
		buttonSelectDelete = (Button) findViewById(R.id.button_delete_one);
		buttonDeleteAll.setOnClickListener(this);
		buttonSelectDelete.setOnClickListener(this);

		textViewList = (TextView) findViewById(R.id.textView_delete);
		editTextAppNo = (EditText) findViewById(R.id.editText_deleteAppNo);
		editTextAppNo.addTextChangedListener(this);

	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button_delete_all:
			_appointment.deleteAllValues();
			break;

		case R.id.button_delete_one:
			displayAppList();
			break;
		}

	}

	public void displayAppList() {
		for (Appointment appointment : _appointment.getAppointment()) {
			textViewList.append("\n" + appointment.toString());
			// Log.d("this", "working");

		}

	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				_appointment.deleteValue();

				break;

			case DialogInterface.BUTTON_NEGATIVE:
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
					"Are you sure to delete "
							+ _appointment.getSelectedApp().getTitle() + " ? ")
					.setPositiveButton("Yes", dialogClickListener)
					.setNegativeButton("No", dialogClickListener).show();

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
