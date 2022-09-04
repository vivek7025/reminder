package com.va.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.va.reminder.adapter.ShowReminderAdapter;
import com.va.reminder.model.Reminders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reference;
    Button button;
    private RecyclerView rv_showReminders;
    private IntentFilter intentFilter;
    ImageView imgReload,imgSignOut;
    private ShowReminderAdapter adapter;
    private List<Reminders> mList = new ArrayList<>();
    private String userID = "";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String insertContent,insertTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        Intent intent = getIntent();
        String action = intent.getStringExtra("action");
        if(action != null){
            if(action.equalsIgnoreCase("insert")) {
                insertContent = intent.getStringExtra("content");
                insertTime = intent.getStringExtra("time");
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String id = currentUser.getUid();
                DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("reminderList").child(id);
                Map<String, Object> data = new HashMap<>();
                data.put("Content", insertContent);
                data.put("Time", insertTime);
                user.push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "new data is inserted", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }




        imgReload = findViewById(R.id.imgReload);
        imgSignOut = findViewById(R.id.imgSignOut);

        intentFilter = new IntentFilter("com.va.reminder.ADD_REMIND");

        rv_showReminders = findViewById(R.id.rv_showReminders);
        rv_showReminders.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setStackFromEnd(false);
        rv_showReminders.setLayoutManager(linearLayoutManager);

        imgReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllReminder(userID);
            }
        });
        imgSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), UserLogin.class);
                startActivity(intent);
            }
        });

        checkUser();
        userID = mUser.getUid();
        getAllReminder(userID);

    }


    private void checkUser() {
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            String id = currentUser.getUid();
        }else{
            Intent intent = new Intent(getApplicationContext(), UserLogin.class);
            startActivity(intent);
        }
    }
    private void getAllReminder(String userID) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.getUid() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("reminderList").child(userID);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Reminders reminders = dataSnapshot.getValue(Reminders.class);
                        mList.add(reminders);
                    }
                    adapter = new ShowReminderAdapter(MainActivity.this, mList, userID);
                    rv_showReminders.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }
}