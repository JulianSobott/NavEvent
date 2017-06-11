package com.unknown.navevent.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.unknown.navevent.R;
import com.unknown.navevent.bLogic.AdminAreaLogic;
import com.unknown.navevent.bLogic.NavigationDrawerLogic;
import com.unknown.navevent.interfaces.AdminAreaLogicInterface;
import com.unknown.navevent.interfaces.BeaconData;
import com.unknown.navevent.interfaces.NavigationDrawerLogicInterface;
import com.unknown.navevent.interfaces.NavigationDrawerUI;

import java.util.ArrayList;
import java.util.List;


public class SideBar extends Fragment implements NavigationDrawerUI {
	private NavigationDrawerLogicInterface mIfc = null;

	public SideBarInterface activityCommander;

	@Override
	public void searchResults(final List<BeaconData> results) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Searchresults");
        String[] beaconNames=new String[results.size()];
        for(int i=0;i<results.size();i++){
            beaconNames[i]=results.get(i).getName();
        }
        builder.setItems(beaconNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                List<Integer> b = new ArrayList<Integer>();
                b.add(results.get(i).getId());
                activityCommander.markBeacons(b);
            }
        });
	}


	public interface SideBarInterface {
        void hideSideBar();
        void markBeacons(List<Integer> beaconIDs);
	}

	private View v;
	private Button closeButton;
	private Button buttonMapFlur;
	private Button buttonMapKreuz;
	private Button optionsbutton;
	private SearchView searchView;
	private ListView importantPlacesList;
	private ListView neededPlacesList;
	private List<String> SearchResults;

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
		//buttonMapFlur =(Button)v.findViewById(R.id.buttonMapFlur); todo remove obsolete code
		//buttonMapKreuz=(Button)v.findViewById(R.id.buttonMapKreuz);
		optionsbutton = (Button) v.findViewById(R.id.OptionButton);
		importantPlacesList = (ListView) v.findViewById(R.id.ListViewImportantPlaces);
		neededPlacesList = (ListView) v.findViewById(R.id.ListViewNeededPlaces);
		searchView = (SearchView) v.findViewById(R.id.SeachViewBeacons);
		createButtonListeners();


		ArrayAdapter<String> adapter =new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,mIfc.getSpecialBeacons());
		importantPlacesList.setAdapter(adapter);
		adapter =new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,mIfc.getOrdinaryBeacons());
		neededPlacesList.setAdapter(adapter);
		return v;
	}

	public void loadBeacons() {

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


        /*buttonMapFlur.setOnClickListener(new View.OnClickListener() { todo del
            @Override
            public void onClick(View view) {
                activityCommander.showMapFlur();
            }
        });

        buttonMapKreuz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCommander.showMapKreuz();
            }
        });*/
	}

}
