package com.example.gvg.contactsfirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gvg on 8/5/2016.
 */
public class Adapter extends ArrayAdapter<Contacts> {

    private Context context;
    private int resource;
    private ArrayList<Contacts> objects;

    public Adapter(Context context, int resource, ArrayList<Contacts> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contacts contacts = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            TextView mName= (TextView) convertView.findViewById(R.id.txtname);
            TextView mContactnumber = (TextView) convertView.findViewById(R.id.txtcontactNumber);

            mName.setText(contacts.getFriendName());
            mContactnumber.setText(contacts.getContactNumber());
        }

        return convertView;
    }
}
