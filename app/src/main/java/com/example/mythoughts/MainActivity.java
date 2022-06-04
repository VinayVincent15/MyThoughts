package com.example.mythoughts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText ETtitle,ETthought;
    private Button save;
    private TextView show;
    private static final String keytitle="Title", keythought="Thought";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ETtitle = findViewById(R.id.ETtitle);
        ETthought = findViewById(R.id.ETthought);
        save = findViewById(R.id.Btnadd);
        show = findViewById(R.id.TVshow);

        save.setOnClickListener(v -> {
            String title = ETtitle.getText().toString().trim();
            String thought = ETthought.getText().toString().trim();
            Map<String,Object> user = new HashMap<>();
            user.put(keytitle, title);
            user.put(keythought, thought);
            db.collection("Journal")
                    .document("First Thoughts")
                    .set(user)
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
    }
}