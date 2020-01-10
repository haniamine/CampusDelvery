package ma.ac.emi.campusdelivery.admin;
import ma.ac.emi.campusdelivery.*;
import ma.ac.emi.campusdelivery.admin.list_elements.MenusCustomAdapter;
import ma.ac.emi.campusdelivery.admin.list_elements.StoresCustomAdapter;
import ma.ac.emi.campusdelivery.models.Menu;
import ma.ac.emi.campusdelivery.models.Store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminMenuActivity extends AppCompatActivity {


    List<Menu> menuList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    MenusCustomAdapter adapter;
    Bundle bundle;
    String storeId,storeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        bundle = getIntent().getExtras();
        storeId = bundle.getString("storeID");
        storeName = bundle.getString("storeName");

        TextView store = findViewById(R.id.menu_Store_Name);
        store.setText(storeName);

        Button btnAdd = findViewById(R.id.btnAddMenu);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),AddMenuActivity.class);
                i.putExtra("storeId",storeId);
                startActivity(i);
            }
        });
        Button btnUpdate = findViewById(R.id.btnStoreUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(AdminMenuActivity.this,AddStoreActivity.class);
                    i.putExtra("storeId",storeId);
                    i.putExtra("storeName",storeName);
                    startActivity(i);

            }
        });
        Button btnSupp = findViewById(R.id.btnSuppress);
        btnSupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(storeId);
            }
        });
        Button btnBack = findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.menu_recyler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(AdminMenuActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        showDataById(storeId) ;

    }

    private void deleteData(String id) {
        db.collection("Stores").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //pd.dismiss()
                        Toast.makeText(AdminMenuActivity.this, "Magasin Suppimé !", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //pd.dismiss()
                        Toast.makeText(AdminMenuActivity.this, "Erreur:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showDataById(String id) {
        db.collection("Menus").whereEqualTo("storeId",id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot doc : task.getResult()){
                            Menu menu= new Menu(doc.getString("id"),doc.getString("menuTitle"),doc.getString("menuDesc"),doc.getString("menuPrice"),doc.getString("storeId"));
                            menuList.add(menu);
                        }
                        //adapter
                        adapter = new MenusCustomAdapter(menuList);
                        recyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
