package iit.lmanohara.appoinmentmanagementsystem;

import android.app.Application;
import android.content.Context;

public class ApplicationContextProvider extends Application {

	private static Context sContext;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		sContext = getApplicationContext();
	}

	public static Context getContext() {

		return sContext;
	}

}
