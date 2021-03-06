package ma.ac.emi.campusdelivery.admin;
import ma.ac.emi.campusdelivery.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddMenuActivity extends AppCompatActivity {

    EditText menuName,menuDesc,menuPrice;
    Button btnAdd,btnCancel;
    FirebaseFirestore db;
    Bundle bundle;
    String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);
        db  = FirebaseFirestore.getInstance();
        menuName = findViewById(R.id.menuName);
        menuDesc = findViewById(R.id.menuDesc);
        menuPrice = findViewById(R.id.menuPrice);



        bundle = getIntent().getExtras();
        storeId = bundle.getString("storeId");

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = menuName.getText().toString().trim();
                String desc = menuDesc.getText().toString().trim();
                String price = menuPrice.getText().toString().trim();
                uploadData(name,desc,storeId,price);
                finish();
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

    private void uploadData(String name,String desc,String storeId,String price) {
        String id = UUID.randomUUID().toString();
        Map<String,Object> doc = new HashMap<>();
        doc.put("id",id);
        doc.put("menuTitle",name);
        doc.put("menuDesc",desc);
        doc.put("menuPrice",price);
        doc.put("storeId",storeId);
        db.collection("Menus").document(id).set((doc)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //pd.dismiss()
                Toast.makeText(AddMenuActivity.this, "Menu Ajouté", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //pd.dismiss()
                        Toast.makeText(AddMenuActivity.this, "Erreur:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
