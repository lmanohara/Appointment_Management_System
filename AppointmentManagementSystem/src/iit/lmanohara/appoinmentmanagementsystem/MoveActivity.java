package iit.lmanohara.appoinmentmanagementsystem;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class MoveActivity extends Activity implements TextWatcher,
		OnDateChangeListener {

	private String date;
	private String moveDate;
	private Appointment _appointment;
	private TextView textViewMoveApp;
	private EditText editTextAppNo;
	private CalendarView calenderViewMoveApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_move);
		date = getIntent().getStringExtra("DATE");
		_appointment = new Appointment();
		Log.d("date", date);
		_appointment.setDate(date);
		_appointment.addAppList();
		textViewMoveApp = (TextView) findViewById(R.id.textView_move);
		displayAppList();
		editTextAppNo = (EditText) findViewById(R.id.editText_moveAppNo);
		editTextAppNo.addTextChangedListener(this);
		calenderViewMoveApp = (CalendarView) findViewById(R.id.calendarView_appMove);
		calenderViewMoveApp.setOnDateChangeListener(this);
	}

	

	public void displayAppList() {

		for (Appointment appointment : _appointment.getAppointment()) {
			textViewMoveApp.append("\n" + appointment.toString());
			// Log.d("this", "working");

		}

	}

	@Override
	public void afterTextChanged(Editable arg0) {
		if (!editTextAppNo.getText().toString().equals("")) {
			_appointment.setSelectedApp(Integer.parseInt(editTextAppNo
					.getText().toString()));

		}

	}

	public void updateValues() {
		_appointment.setDate(moveDate);
		Log.d("moveDate", moveDate);
		_appointment.updateValuesMove();
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
	public void onSelectedDayChange(CalendarView arg0, int year, int month,
			int day) {
		moveDate = String.valueOf(year) + String.valueOf(month)
				+ String.valueOf(day);
		updateValues();

	}

}
