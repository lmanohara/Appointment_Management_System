package iit.lmanohara.appoinmentmanagementsystem;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class Appointment {
	private String _title;
	private String _time;
	private String _detail;
	private int _appNo;
	private String _date;
	private int _id;
	private Appointment _selectedApp;
	// ----------------------------
	private String TABLE_NAME = "Appointment";
	private String COLUMN_DATE = "date";
	private String COLUMN_TIME = "time";
	private String COLUMN_TITLE = "title";
	private String COLUMN_DETAIL = "detail";
	private String COLUMN_APPID = "appointmentId";

	// -----------------------------------------
	private DataBase dataBase;

	public Appointment() {
		dataBase = DataBase.instance();

	}

	public Appointment(int id, String title, String time, String detail,
			int appNo) {
		_id = id;
		_title = title;
		_time = time;
		_detail = detail;
		_appNo = appNo;

	}

	public void setId(int id) {
		this._id = id;

	}

	public int getId() {
		return _id;
	}

	public void setTitle(String title) {
		this._title = title;
	}

	public void setTime(String time) {
		this._time = time;

	}

	public void setDetail(String detail) {
		this._detail = detail;
	}

	public String getTitle() {
		return _title;
	}

	public String getTime() {

		return _time;
	}

	public String getDetail() {
		return _detail;
	}

	public void setAppNo(int appNo) {
		this._appNo = appNo;

	}

	public int getAppNo() {

		return _appNo;
	}

	public void setDate(String date) {
		this._date = date;
	}

	public String getDate() {
		return _date;
	}

	private List<Appointment> _appointment = new ArrayList<Appointment>();

	public void setAppoinment(Appointment appointment) {
		this._appointment.add(appointment);
	}

	public List<Appointment> getAppointment() {
		return _appointment;
	}

	public void setSelectedApp(int appNo) {
		appNo = appNo - 1;
		_selectedApp = getAppointment().get(appNo);
	}

	public Appointment getSelectedApp() {
		return _selectedApp;

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String space = " ";
		return String.valueOf(getAppNo()) + space + getTime() + space
				+ getTitle();
	}

	// ------------------------------------------------------------------------------------------

	public void addAppList() {
		int appCounter = 0;
		Cursor cursor = dataBase.select("SELECT * FROM " + TABLE_NAME
				+ " WHERE " + COLUMN_DATE + " = '" + getDate() + "'");

		while (cursor.moveToNext()) {
			appCounter++;
			String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
			String title = cursor
					.getString(cursor.getColumnIndex(COLUMN_TITLE));
			String detail = cursor.getString(cursor
					.getColumnIndex(COLUMN_DETAIL));
			int id = cursor.getInt(cursor.getColumnIndex(COLUMN_APPID));

			setAppoinment(new Appointment(id, title, time, detail, appCounter));

		}
	}

	public void updateValues() {
		ContentValues values = new ContentValues();
		values.put(COLUMN_TITLE, getTitle());
		values.put(COLUMN_TIME, getTime());
		values.put(COLUMN_DETAIL, getDetail());

		Log.d("title", getTitle());
		Log.d("id", String.valueOf(getSelectedApp()._id));
		dataBase.update(TABLE_NAME, values, COLUMN_APPID + " = "
				+ getSelectedApp()._id);

	}

	public void deleteAllValues() {
		dataBase.delete(TABLE_NAME, COLUMN_DATE + " = '" + getDate() + "'");
	}

	public void deleteValue() {
		dataBase.delete(TABLE_NAME, COLUMN_APPID + " = " + getSelectedApp()._id);
	}

	public void updateValuesMove() {
		ContentValues values = new ContentValues();
		values.put(COLUMN_DATE, getDate());

		dataBase.update(TABLE_NAME, values, COLUMN_APPID + " = "
				+ getSelectedApp()._id);

	}
	
	public void addAppSearchList() {
		int appCounter = 0;
		Cursor cursor = dataBase.select("SELECT * FROM " + TABLE_NAME);

		while (cursor.moveToNext()) {
			appCounter++;
			String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
			String title = cursor
					.getString(cursor.getColumnIndex(COLUMN_TITLE));
			String detail = cursor.getString(cursor
					.getColumnIndex(COLUMN_DETAIL));
			int id = cursor.getInt(cursor.getColumnIndex(COLUMN_APPID));

			setAppoinment(new Appointment(id, title, time, detail, appCounter));

		}
	}
	
	public void updateValuesTrans() {
		ContentValues values = new ContentValues();
		values.put(COLUMN_DETAIL, getDetail());

		dataBase.update(TABLE_NAME, values, COLUMN_APPID + " = "
				+ getSelectedApp()._id);

	}

}
