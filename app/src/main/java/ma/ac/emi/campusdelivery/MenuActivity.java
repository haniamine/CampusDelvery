package ma.ac.emi.campusdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ma.ac.emi.campusdelivery.list_elements.MenusCustomAdapter;
import ma.ac.emi.campusdelivery.models.Menu;


public class MenuActivity extends AppCompatActivity {


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
        setContentView(R.layout.activity_menu);

        bundle = getIntent().getExtras();
        storeId = bundle.getString("storeID");
        storeName = bundle.getString("storeName");

        TextView store = findViewById(R.id.menu_Store_Name);
        store.setText(storeName);

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
        layoutManager = new LinearLayoutManager(MenuActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        showDataById(storeId) ;

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