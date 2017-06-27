package com.unknown.navevent.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.NavigationDrawerLogic;
import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.NavigationDrawerLogicInterface;
import com.unknown.navevent.interfaces.NavigationDrawerUI;

import java.util.ArrayList;
import java.util.List;


public class SideBar extends Fragment implements NavigationDrawerUI {
	private NavigationDrawerLogicInterface mIfc = null;

	public SideBarInterface activityCommander;												//Creats a Interface to talk to he Mainactivity

	@Override
	public void searchResults(final List<BeaconData> results) {								//Processes the response to mIfc.searchfor() and presents the results
		if(!results.isEmpty()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Searchresults");
			String[] beaconNames = new String[results.size()];
			for (int i = 0; i < results.size(); i++) {
				beaconNames[i] = results.get(i).getName();
			}
			builder.setItems(beaconNames, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					List<Integer> b = new ArrayList<>();
					b.add(results.get(i).getId());
					activityCommander.markBeacons(b);
					activityCommander.hideSideBar();
					searchView.setQuery("",false);
				}
			}).show();
		}
		else Toast.makeText(getActivity(), getString(R.string.found_no_search_results), Toast.LENGTH_SHORT).show();
	}


	public interface SideBarInterface {
        void hideSideBar();
        void markBeacons(List<Integer> beaconIDs);
	}

	private View v;
	private Button closeButton;
	private Button optionsbutton;
	private SearchView searchView;
	private ListView importantPlacesList;
	private ListView neededPlacesList;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		activityCommander = (SideBarInterface) getActivity();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		//Creating the NavigationDrawer
		mIfc = new NavigationDrawerLogic(this);
		mIfc.onCreate();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_side_bar, container, false);
		closeButton = (Button) v.findViewById(R.id.buttonClose);
		optionsbutton = (Button) v.findViewById(R.id.OptionButton);
		importantPlacesList = (ListView) v.findViewById(R.id.ListViewImportantPlaces);
		neededPlacesList = (ListView) v.findViewById(R.id.ListViewNeededPlaces);
		searchView = (SearchView) v.findViewById(R.id.SeachViewBeacons);
		createButtonListeners();
		loadBeacons();
		return v;
	}

	public void loadBeacons() {
		ArrayAdapter<String> adapter =new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_list_item_1,mIfc.getSpecialBeacons());				//Fills the Lists in the NavigationDrawer
		importantPlacesList.setAdapter(adapter);
		adapter =new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_list_item_1,mIfc.getOrdinaryBeacons());
		neededPlacesList.setAdapter(adapter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		mIfc.onDestroy();
	}

	private void createButtonListeners() {
		closeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				activityCommander.hideSideBar();
			}
		});
		optionsbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), QrActivity.class);
				startActivity(intent);
			}
		});
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				mIfc.searchFor(query);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});
		importantPlacesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				mIfc.findAllSpecialBeacons(mIfc.getSpecialBeacons().get(i));
			}
		});
		neededPlacesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				mIfc.findAllOrdinaryBeacons(mIfc.getOrdinaryBeacons().get(i));
			}
		});
	}

}
