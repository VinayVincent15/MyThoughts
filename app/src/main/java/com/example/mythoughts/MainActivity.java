package com.example.mythoughts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText ETtitle,ETthought;
    private Button save, show, update, delete;
    private TextView TVrectitle,TVrecthought;
    private static final String keytitle="Title", keythought="Thought";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference journalref = db.document("Journal/First Thoughts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ETtitle = findViewById(R.id.ETtitle);
        ETthought = findViewById(R.id.ETthought);
        save = findViewById(R.id.Btnadd);
        TVrectitle = findViewById(R.id.TVrectitle);
        TVrecthought = findViewById(R.id.TVrecthought);
        show = findViewById(R.id.Btnshow);
        update = findViewById(R.id.Btnupdate);
        delete = findViewById(R.id.BtnDelete);

        save.setOnClickListener(v -> {
            String title = ETtitle.getText().toString().trim();
            String thought = ETthought.getText().toString().trim();
            Map<String,Object> data = new HashMap<>();
            data.put(keytitle, title);
            data.put(keythought, thought);
            journalref.set(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        show.setOnClickListener(v -> {
            db.collection("Journal").document("First Thoughts")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                TVrectitle.setText(documentSnapshot.getString(keytitle));
                                TVrecthought.setText(documentSnapshot.getString(keythought));
                            }
                            else{
                                Toast.makeText(MainActivity.this,"No data",Toast.LENGTH_SHORT).show();
                                TVrectitle.setText("");
                                TVrecthought.setText("");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,"Failed to get data",Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        update.setOnClickListener(v -> {
            Map<String,Object> data =new HashMap<>();
            data.put(keytitle,ETtitle.getText().toString().trim());
            data.put(keythought,ETthought.getText().toString().trim());
            journalref.update(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainActivity.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,"Update Failed",Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        delete.setOnClickListener(v ->{
            journalref.delete();
        });

    }
}