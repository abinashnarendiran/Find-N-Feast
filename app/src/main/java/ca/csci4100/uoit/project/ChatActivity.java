package ca.csci4100.uoit.project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ChatActivity extends AppCompatActivity {

    DatabaseReference ref;

    ArrayList<String> arrayList;

    FirebaseUser user;

    EditText roomInput;
    ListView roomView;
    ArrayAdapter<String> adapter;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_main);

        roomInput = (EditText)findViewById(R.id.roomInput);
        roomView = (ListView)findViewById(R.id.roomView);

        arrayList = new ArrayList<>();

        this.user = (FirebaseUser) getIntent().getExtras().get("user");

        adapter = new ArrayAdapter<String>(ChatActivity.this, android.R.layout.simple_list_item_1, arrayList){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(Color.WHITE);
                textView.setTextSize(18);

                return view;
            }
        };

        roomView.setAdapter(adapter);

        ref = FirebaseDatabase.getInstance().getReference("Chat");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashSet<String> rooms = new HashSet<String>();

                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    rooms.add(((DataSnapshot) i.next()).getKey());
                }

                arrayList.clear();
                arrayList.addAll(rooms);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ChatActivity.this, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });


        roomView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent chatRoomIntent = new Intent(ChatActivity.this, ChatRoomActivity.class);
                chatRoomIntent.putExtra("userEmail", user.getEmail());
                chatRoomIntent.putExtra("roomName", ((TextView) view).getText().toString());
                startActivity(chatRoomIntent);

            }
        });
    }

    public void insert_data(View v)
    {
        HashMap<String,Object> rooms = new HashMap<>();
        rooms.put(roomInput.getText().toString(), "");
        ref.updateChildren(rooms);
    }
}