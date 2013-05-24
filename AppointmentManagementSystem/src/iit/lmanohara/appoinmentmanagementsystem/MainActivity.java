package iit.lmanohara.appoinmentmanagementsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

public class MainActivity extends Activity implements OnClickListener,
		OnDateChangeListener {

	private Button buttonCreate;
	private Button buttonEdit;
	private Button buttonDelete;
	private Button buttonMove;
	private Button buttonSearch;
	private Button buttonTranslate;
	private Intent intent;
	private CalendarView calendarViewAppointment;
	private String date = null;
	private boolean checkSelect = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonCreate = (Button) findViewById(R.id.button_create);
		buttonDelete = (Button) findViewById(R.id.button_delete);
		buttonEdit = (Button) findViewById(R.id.button_Edit);
		buttonMove = (Button) findViewById(R.id.button_move);
		buttonSearch = (Button) findViewById(R.id.button_search_app);
		buttonTranslate = (Button) findViewById(R.id.button_translate);

		buttonCreate.setOnClickListener(this);
		buttonDelete.setOnClickListener(this);
		buttonEdit.setOnClickListener(this);
		buttonMove.setOnClickListener(this);
		buttonSearch.setOnClickListener(this);
		buttonTranslate.setOnClickListener(this);

		calendarViewAppointment = (CalendarView) findViewById(R.id.calendarViewAppoinment);
		calendarViewAppointment.setOnDateChangeListener(this);

	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	
	

	@Override
	public void onClick(View view) {

		switch (view.getId()) {

		case R.id.button_create:
			intent = new Intent(this, CreateActivity.class);
			Log.d("Click", "create");
			startAppointmentActivity();
			break;
		case R.id.button_delete:
			intent = new Intent(this, DeleteActivity.class);
			startAppointmentActivity();
			break;
		case R.id.button_Edit:
			intent = new Intent(this, EditActivity.class);
			startAppointmentActivity();
			break;
		case R.id.button_move:
			intent = new Intent(this, MoveActivity.class);
			startAppointmentActivity();
			break;
		case R.id.button_search_app:
			intent = new Intent(this, SearchActivity.class);
			startAppointmentActivity();
			break;
		case R.id.button_translate:
			intent = new Intent(this, TranslatorActivity.class);
			startAppointmentActivity();
			break;

		}

	}

	private void startAppointmentActivity() {

		intent.putExtra("DATE", date);
		startActivity(intent);

	}

	@Override
	public void onSelectedDayChange(CalendarView v, int year, int month, int day) {
		// TODO Auto-generated method stub
		String yearString = String.valueOf(year);
		String monthString = String.valueOf(month);
		String dayString = String.valueOf(day);
		date = yearString + monthString + dayString;

		Log.d("date", date);

	}

}
