package ma.ac.emi.campusdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

import ma.ac.emi.campusdelivery.deliver_list_element.DeliverCustomAdapter;
import ma.ac.emi.campusdelivery.models.Command;
import ma.ac.emi.campusdelivery.models.Menu;
import ma.ac.emi.campusdelivery.store_list_elements.MenusCustomAdapter;

public class UserDeliveryActivity extends AppCompatActivity {

    List<Command> commandList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    DeliverCustomAdapter adapter;
    Bundle bundle;
    String storeId,storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delivery);

        Button btnBack = findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.deliv_recyler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(UserDeliveryActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        String email = fAuth.getCurrentUser().getEmail();
        showDataByMail(email) ;
    }

    private void showDataByMail(String email) {

        db.collection("Commands").whereEqualTo("deliverMail",email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot doc : task.getResult()){ //String id, String title, String price, String store
                            Command command= new Command(doc.getString("id"),doc.getString("menuTitle"),doc.getString("menuPrice"),doc.getString("storeName"));
                            commandList.add(command);
                        }

                        //adapter
                        adapter = new DeliverCustomAdapter(commandList);
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
