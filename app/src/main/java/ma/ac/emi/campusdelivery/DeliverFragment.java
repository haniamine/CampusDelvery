package ma.ac.emi.campusdelivery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ma.ac.emi.campusdelivery.admin.AddStoreActivity;
import ma.ac.emi.campusdelivery.deliver_list_element.DeliverCustomAdapter;
import ma.ac.emi.campusdelivery.models.Command;


public class DeliverFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DeliverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeliverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeliverFragment newInstance(String param1, String param2) {
        DeliverFragment fragment = new DeliverFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    List<Command> commandList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    DeliverCustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deliver, container, false);

        Button btn = view.findViewById(R.id.btnLivraison);

        FirebaseAuth mFirebaseAuth =FirebaseAuth.getInstance();
        FirebaseUser mFireUser = mFirebaseAuth.getCurrentUser();
        if(mFireUser == null){
            btn.setVisibility(View.INVISIBLE);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UserDeliveryActivity.class));
            }
        });

        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.deliver_recyler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        showData();
        return view;

    }

    public void showData() {
        db.collection("Commands").whereEqualTo("deliverMail",null)
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
