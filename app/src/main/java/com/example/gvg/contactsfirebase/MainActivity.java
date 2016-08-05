package com.example.gvg.contactsfirebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {


    private List<Contacts> friendsList;
    private ListView listView;
    private ArrayAdapter adapter;
    private FirebaseDatabase conDatabase;
    private DatabaseReference conDatabaseReference;
    private ChildEventListener childEventListener;

    private EditText mName;
    private EditText mPass;

    private Button mAdd;
    private Button mDelete;
    private Button mUpdate;
    private Button mCall;

    private ListView listcontacts;

    private ArrayList<Contacts> contactList;

    Contacts contact = new Contacts();
    String name,contactNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conDatabase = FirebaseDatabase.getInstance();
        conDatabaseReference = conDatabase.getReference("Contacts");

        mAdd = (Button) findViewById(R.id.buttonAddItem);
        mDelete = (Button) findViewById(R.id.buttonDeleteItem);
        mUpdate = (Button) findViewById(R.id.buttonUpdateItem);
        mCall = (Button) findViewById(R.id.buttonCallItem);

        mName = (EditText) findViewById(R.id.txtUsername);
        mPass = (EditText) findViewById(R.id.txtPass);
        listcontacts = (ListView) findViewById(R.id.listView);

        conDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactList = new ArrayList<>();


                Iterable<DataSnapshot> snapshotIterable = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterable.iterator();

                while (iterator.hasNext()) {
                    Contacts contacts = iterator.next().getValue(Contacts.class);
                    contactList.add(contacts);


                    ArrayAdapter<Contacts> adapter = new ArrayAdapter<Contacts>(getApplicationContext(), android.R.layout.simple_list_item_1, contactList);
                    listcontacts.setAdapter(adapter);
                }
//                .simple_list_item_1

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mName.getText().toString();
                contactNumber = mPass.getText().toString();
                contact.setContactNumber(contactNumber);
                contact.setFriendName(name);
                String key = conDatabaseReference.push().getKey();
                Contacts contact = new Contacts(name, contactNumber);
                conDatabaseReference.child(key).setValue(contact);

            }
        });




        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteContact(contact);
            }
        });


        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mName.getText().toString();
                contactNumber = mPass.getText().toString();
                contact.setContactNumber(contactNumber);
                contact.setFriendName(name);
                String key = conDatabaseReference.push().getKey();
                Contacts contact = new Contacts(name, contactNumber);
                conDatabaseReference.child(key).setValue(contact);

            }
        });

        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + mPass.getText().toString()));

                try{
                    startActivity(intent);
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Not enought load",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


        public void deleteContact(Contacts contacts){
            final Contacts verify = contacts;
            Query deleteQuery = conDatabaseReference.orderByChild("name").equalTo(contacts.getFriendName());

            deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                    }
                }
//            deleteQuery.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
//                    Iterator<DataSnapshot> iterator= snapshotIterator.iterator();
//
//                    while(iterator.hasNext()){
//                        DataSnapshot snapshot = iterator.next();
//                        Contacts contacts = snapshot.getValue(Contacts.class);
//
//                        if(contacts.getFriendName().equals(verify.getFriendName())){
//                            String key = snapshot.getKey();
//                            conDatabaseReference.child(key).removeValue();
//                        }
//                    }
//
//
//                }
//
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }





    }

