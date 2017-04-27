package com.unknown.navevent.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import com.unknown.navevent.R;

public class BeaconInfo extends Fragment {
    private static TextView infoText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_beacon_info, container, false);
        infoText=(TextView) v.findViewById(R.id.textView);
        return v;
    }
    public void changeText(String text){
        infoText.setText(text);
    }
}