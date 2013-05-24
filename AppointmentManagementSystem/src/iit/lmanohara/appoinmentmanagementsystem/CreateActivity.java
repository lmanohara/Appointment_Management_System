package iit.lmanohara.appoinmentmanagementsystem;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class CreateActivity extends Activity implements OnClickListener,
		OnTimeChangedListener {

	private DataBase dataBase;
	private String date;
	private String time;

	// -----------------------------------
	private EditText title;
	private EditText detail;
	private Button buttonSave;
	private TimePicker pickTime;

	// -------------------------------------
	public static final String TABLE_NAME = "Appointment";
	public static final String COLUMN_NAME_TITLE = "title";
	public static final String COLUMN_NAME_DATE = "date";
	public static final String COLUMN_NAME_TIME = "time";
	public static final String COLUMN_NAME_DETAIL = "detail";
	public static final String COLUMN_NAME_APPNO = "appointmentNo";
	public static final String COLUMN_NAME_APPID = "appointmentId";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);

		date = getIntent().getStringExtra("DATE");
		Log.d("getDate", date);
		dataBase = DataBase.instance();

		title = (EditText) findViewById(R.id.editText_deleteAppNo);
		detail = (EditText) findViewById(R.id.editText_detail);
		detail.setMovementMethod(new ScrollingMovementMethod());

		buttonSave = (Button) findViewById(R.id.button_save);
		buttonSave.setOnClickListener(this);

		pickTime = (TimePicker) findViewById(R.id.timePicker_app);
		pickTime.setOnTimeChangedListener(this);
		pickTime.setIs24HourView(true);
		

	}

	
	public void updateValue() {

		if (isAppointmentTime()) {
			saveAppointment();
		} else {
			Log.d("Available", "title not available");
		}

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.button_save:
			updateValue();
			break;

		default:
			break;
		}

	}

	public boolean isAppointmentTime() {

		Cursor cursor = dataBase
				.select("SELECT * FROM " + TABLE_NAME + " WHERE "
						+ COLUMN_NAME_DATE + " = '" + date + "' AND "
						+ COLUMN_NAME_TITLE + " = '"
						+ title.getText().toString() + "'");

		while (cursor.moveToNext()) {

			String s = cursor.getString(cursor
					.getColumnIndex(COLUMN_NAME_TITLE));

			if (s.equals(title.getText().toString())) {
				return false;

			}

		}

		cursor.close();
		return true;

	}

	public void saveAppointment() {

		// Log.d("id", String.valueOf(appId));
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_TITLE, title.getText().toString());
		values.put(COLUMN_NAME_DATE, date);
		values.put(COLUMN_NAME_TIME, time);
		values.put(COLUMN_NAME_DETAIL, detail.getText().toString());
		dataBase.insert(TABLE_NAME, values);

	}

	@Override
	public void onTimeChanged(TimePicker arg0, int hours, int minute) {
		// TODO Auto-generated method stub
		time = hours + ":" + minute;

		Log.d("Time", time);

	}

}
