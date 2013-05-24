package iit.lmanohara.appoinmentmanagementsystem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/iit.lmanohara.appoinmentmanagementsystem/databases/";
	private static String DB_NAME = "AppointmentDatabase";
	private static SQLiteDatabase mDataBase;
	private static DataBase sInstance = null;
	private static final int DATABASE_VERSION = 1;

	public DataBase() {
		super(ApplicationContextProvider.getContext(), DB_NAME, null,
				DATABASE_VERSION);

		try {

			createDataBase();
			openDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------------
	/**
	 * Singleton for DataBase
	 * 
	 * @return singleton instance
	 */
	public static DataBase instance() {

		if (sInstance == null) {
			sInstance = new DataBase();
		}
		return sInstance;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * 
	 * @throws java.io.IOException
	 *             io exception
	 */

	private void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();

		if (dbExist) {

		} else {

			this.getReadableDatabase();

			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */

	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {

		}

		if (checkDB != null) {

			checkDB.close();
		}

		return checkDB != null;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * 
	 * @throws java.io.IOException
	 *             io exception
	 */
	public void copyDataBase() throws IOException {

		// open local db as the input stream
		InputStream myInput = ApplicationContextProvider.getContext()
				.getAssets().open(DB_NAME);

		// path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// close the stream
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	private void openDataBase() throws SQLException {

		// open the database
		String myPath = DB_PATH + DB_NAME;
		mDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	/**
	 * Select method
	 * 
	 * @param query
	 *            select query
	 * @return - Cursor with the results
	 * @throws android.database.SQLException
	 *             sql exception
	 */

	public Cursor select(String query) throws SQLException {
		return mDataBase.rawQuery(query, null);
	}

	public void insert(String table, ContentValues values) throws SQLException {
		mDataBase.insert(table, null, values);
		Log.d("inserted", "value");
	}

	public void update(String table, ContentValues values, String where) {
		mDataBase.update(table, values, where, null);

	}

	public void delete(String table, String where) throws SQLException {
		mDataBase.delete(table, where, null);
	}

	public void sqlCommand(String command) {
		mDataBase.execSQL(command);
	}

	@Override
	public synchronized void close() {
		if (mDataBase != null)
			mDataBase.close();

		super.close();
	}

	// -------------------------------------------------------------------------------------
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	// ---------------------------------------------------------------------------------
}
