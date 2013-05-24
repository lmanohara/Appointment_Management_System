package iit.lmanohara.appoinmentmanagementsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

public class PrefActivity extends Activity implements OnItemClickListener {

	private ListView listViewLanguage;

	private CharSequence[] itemArray;
	private List<CharSequence> itemList;
	private ArrayAdapter<CharSequence> adapter;
	private List<String> selectedItem = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pref);

		itemArray = getResources().getTextArray(R.array.languages);
		itemList = new ArrayList<CharSequence>(Arrays.asList(itemArray));

		listViewLanguage = (ListView) findViewById(R.id.listView_language);
		listViewLanguage.setChoiceMode(listViewLanguage.CHOICE_MODE_MULTIPLE);
		adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_multiple_choice, itemList);
		listViewLanguage.setAdapter(adapter);
		listViewLanguage.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int value, long arg3) {
		// TODO Auto-generated method stub

		CheckedTextView check = (CheckedTextView) view;
		check.setChecked(!check.isChecked());
		boolean click = !check.isChecked();
		check.setChecked(click);
		switch (value) {
		case 0:
			if (click) {

			} else {
				selectedItem.add((String) adapter.getItem(0));
			}
			break;

		case 1:
			if (click) {

			} else {
				selectedItem.add((String) adapter.getItem(1));
			}
			break;
		case 3:
			if (click) {

			} else {
				selectedItem.add((String) adapter.getItem(3));
			}
			break;

		}
		
		setLIstToArray();

	}

	public void setLIstToArray() {
		String[] array = selectedItem.toArray(new String[selectedItem.size()]);
		saveArray(array, "selectedItem", this);

	}

	public boolean saveArray(String[] array, String arrayName, Context mContext) {
		SharedPreferences prefs = mContext.getSharedPreferences(
				"preferencename", 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(arrayName + "_size", array.length);
		for (int i = 0; i < array.length; i++)
			editor.putString(arrayName + "_" + i, array[i]);
		return editor.commit();
	}

}
