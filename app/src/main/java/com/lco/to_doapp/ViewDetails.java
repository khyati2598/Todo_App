package com.lco.to_doapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lco.to_doapp.Model.Listdata;

public class ViewDetails extends AppCompatActivity {
Button update,delete;
EditText title,description;
DatabaseReference mDatabase;
String titlesend,descsend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        title = findViewById(R.id.t1);
        description =findViewById(R.id.d2);
        update = findViewById(R.id.updatenotes);
        delete = findViewById(R.id.deletenotes);
        final Intent i = getIntent();
        String gettitle = i.getStringExtra("title");
        String getdes = i.getStringExtra("desc");
        final String getid = i.getStringExtra("id");
        title.setText(gettitle);
        description.setText(getdes);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateNotes(getid);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNotes(getid);
            }
        });
    }
    private  void updateNotes(final String getid)
    {
        titlesend = title.getText().toString();
        descsend = description.getText().toString();
        Listdata listdata = new Listdata(getid,titlesend,descsend);
        mDatabase.child("Task").child(getid).setValue(listdata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ViewDetails.this, "Notes Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });
    }

    private void deleteNotes(String getid)
    {
        mDatabase.child("Task").child(getid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ViewDetails.this, "Deleted!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });
    }
}
