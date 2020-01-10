package ma.ac.emi.campusdelivery.admin;
import ma.ac.emi.campusdelivery.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddStoreActivity extends AppCompatActivity {

    EditText storeName;
    TextView title;
    Button btnAdd,btnCancel;
    FirebaseFirestore db;
    String storeId,storeNam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);
        db  = FirebaseFirestore.getInstance();
        storeName = findViewById(R.id.storeName);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            // Update
            title = findViewById(R.id.storeAddTitle);
            title.setText("Mettre à jour un Magasin");
            btnAdd.setText("Update");
            storeId = bundle.getString("storeID");
            storeNam = bundle.getString("storeName");

            storeName.setText(storeNam);
        }


        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = storeName.getText().toString().trim();
                Bundle bundle = getIntent().getExtras();
                if(bundle !=null){
                    updateData(storeId,name);
                }
                else{
                    uploadData(name);
                    finish();
                }

            }
        });
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void updateData(String id,String name) {
        db.collection("Stores").document(id)
                .update("storeName",name)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //pd.dismiss()
                        Toast.makeText(AddStoreActivity.this, "Magasin à jour !", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //pd.dismiss()
                        Toast.makeText(AddStoreActivity.this, "Erreur:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadData(String name) {
        String id = UUID.randomUUID().toString();
        Map<String,Object> doc = new HashMap<>();
        doc.put("id",id);
        doc.put("storeName",name);
        db.collection("Stores").document(id).set((doc))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //pd.dismiss()
                        Toast.makeText(AddStoreActivity.this, "Magasin Ajouté", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //pd.dismiss()
                        Toast.makeText(AddStoreActivity.this, "Erreur:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
