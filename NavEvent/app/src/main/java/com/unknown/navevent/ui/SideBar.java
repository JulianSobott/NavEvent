package com.unknown.navevent.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import com.unknown.navevent.R;

import java.util.List;


public class SideBar extends Fragment {

    public SideBarInterface activityCommander;

    public interface SideBarInterface{
        public void hideSideBar();
    }
    private View v;
    private Button closeButton;
    private MainActivity mainActivity = new MainActivity();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityCommander = (SideBarInterface) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_side_bar, container, false);
        closeButton =(Button) v.findViewById(R.id.buttonClose);
        createButtonListeners();
        return v;
    }

    private void createButtonListeners(){
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            activityCommander.hideSideBar();
            }
        });
    }
    private void createListView(List<String> elementList){
        
    }
}
