package ca.csci4100.uoit.project;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.*;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class ChatRoomActivity extends AppCompatActivity {
    EditText messageInput;
    TextView chatView;
    TextView roomLabel;

    private String userEmail, roomName;

    DatabaseReference ref;
    String temp;

    MediaPlayer sentSound;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_room);

        messageInput = (EditText)findViewById(R.id.messageInput);

        chatView = (TextView)findViewById(R.id.chatView);
        roomLabel = (TextView)findViewById(R.id.roomLabel);

        chatView.setMovementMethod(new ScrollingMovementMethod());

        userEmail = getIntent().getExtras().get("userEmail").toString();
        roomName = getIntent().getExtras().get("roomName").toString();

        roomLabel.setText("Room Name: " + roomName);

        ref = FirebaseDatabase.getInstance().getReference("Chat").child(roomName);

        sentSound = MediaPlayer.create(context, R.raw.sent);

        final Button sendButton = (Button) findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (sentSound.isPlaying()) {
                        sentSound.stop();
                        sentSound.release();
                        sentSound = MediaPlayer.create(context, R.raw.sent);
                    }
                    sentSound.start();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                send(v);
            }
        });

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                updateChatView(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                updateChatView(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                updateChatView(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    public void send(View v)
    {
        String timeStamp = new SimpleDateFormat("EEE MMM dd yyyy h:mm:ss a").format(Calendar.getInstance().getTime());

        HashMap<String, Object> map = new HashMap<>();

        temp = ref.push().getKey();
        ref.updateChildren(map);

        DatabaseReference child = ref.child(temp);

        HashMap<String, Object> messageInfo = new HashMap<>();

        if (messageInput.getText().toString().isEmpty()) {
            Toast.makeText(ChatRoomActivity.this,"Please enter a message", Toast.LENGTH_LONG).show();
        }
        else {
            messageInfo.put("Email", userEmail);
            messageInfo.put("Message", messageInput.getText().toString());
            messageInfo.put("Time", timeStamp);

            child.updateChildren(messageInfo).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            messageInput.setText("");
        }
    }

    public void updateChatView(DataSnapshot ss)
    {
        Iterator i = ss.getChildren().iterator();

        String message, email, time;

        while(i.hasNext())
        {
            email = ((DataSnapshot)i.next()).getValue().toString();
            message = ((DataSnapshot)i.next()).getValue().toString();
            time = ((DataSnapshot)i.next()).getValue().toString();

            chatView.append("User" + ": " + email + "\nSent at: " + time + "\n" + message + "\n\n");
        }
    }
}