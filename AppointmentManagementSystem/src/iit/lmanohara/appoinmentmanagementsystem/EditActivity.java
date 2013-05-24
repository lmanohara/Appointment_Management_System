package iit.lmanohara.appoinmentmanagementsystem;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class EditActivity extends Activity implements OnTimeChangedListener,
		TextWatcher, OnClickListener {

	private String date;
	private Appointment _appointment;
	private TextView textViewAppList;
	private EditText editTextAppNo;
	private EditText editTextTitle;
	private TimePicker timePickerEdit;
	private EditText editTextDetail;
	private Button buttonUpdate;
	private String time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		_appointment = new Appointment();
		date = getIntent().getStringExtra("DATE");
		_appointment.setDate(date);
		textViewAppList = (TextView) findViewById(R.id.textView_edit);
		_appointment.addAppList();
		displayAppList();

		editTextAppNo = (EditText) findViewById(R.id.editText_search);
		editTextTitle = (EditText) findViewById(R.id.editText_deleteAppNo);
		editTextDetail = (EditText) findViewById(R.id.editText_detail);
		editTextAppNo.addTextChangedListener(this);

		timePickerEdit = (TimePicker) findViewById(R.id.timePicker_edit);
		timePickerEdit.setOnTimeChangedListener(this);
		timePickerEdit.setIs24HourView(true);

		buttonUpdate = (Button) findViewById(R.id.button_update);
		buttonUpdate.setOnClickListener(this);

	}



	public void displayAppList() {

		for (Appointment appointment : _appointment.getAppointment()) {
			textViewAppList.append("\n" + appointment.toString());
			// Log.d("this", "working");

		}

	}

	public void viewSelectedApp() {

		_appointment.setSelectedApp(Integer.parseInt(editTextAppNo.getText()
				.toString()));

		Appointment selectedApp = _appointment.getSelectedApp();
		String[] splits = selectedApp.getTime().split(":");
		editTextTitle.setText(selectedApp.getTitle());
		editTextDetail.setText(selectedApp.getDetail());
		timePickerEdit.setCurrentHour(Integer.parseInt(splits[0]));
		timePickerEdit.setCurrentMinute(Integer.parseInt(splits[1]));

	}

	public void updateValues() {

		_appointment.setTitle(editTextTitle.getText().toString());
		_appointment.setTime(time);
		_appointment.setDetail(editTextDetail.getText().toString());

		_appointment.updateValues();
	}

	@Override
	public void onTimeChanged(TimePicker arg0, int hours, int minute) {
		time = hours + ":" + minute;
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		viewSelectedApp();

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
		case R.id.button_update:
			updateValues();
			break;

		default:
			break;
		}

	}

}
