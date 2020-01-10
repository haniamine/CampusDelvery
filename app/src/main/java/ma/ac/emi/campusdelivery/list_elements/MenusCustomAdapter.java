package ma.ac.emi.campusdelivery.list_elements;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ma.ac.emi.campusdelivery.MenuActivity;
import ma.ac.emi.campusdelivery.R;
import ma.ac.emi.campusdelivery.admin.AddMenuActivity;
import ma.ac.emi.campusdelivery.models.Menu;

public class MenusCustomAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    ListActivity listActivity;
    List<Menu> menuList;
    Context context;

    public MenusCustomAdapter(ListActivity listActivity, List<Menu> menuList, Context context) {
        // this.listActivity = listActivity;
        this.menuList = menuList;
        this.context = context;
    }
    public MenusCustomAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_menu_layout,parent,false);

        MenuViewHolder viewHolder = new MenuViewHolder(itemView);
        // item click
        viewHolder.setOnClickListener(new MenuViewHolder.ClickListener() {
            @Override
            public void onItemClick(final View view, final int position) {
                String menuId = menuList.get(position).getId();
                decideCommand(view);
                makeCommand(menuId);
            }

            @Override
            public void onItemLongClick(final View view, final int position) {
            }
        });

        return viewHolder;
    }

    private void  decideCommand(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void makeCommand(String menuId) {
        String id = UUID.randomUUID().toString();
        Map<String,Object> doc = new HashMap<>();

        FirebaseFirestore db;
        db  = FirebaseFirestore.getInstance();
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        String email = fAuth.getCurrentUser().getEmail();
        doc.put("id",id);
        doc.put("menuId",menuId);
        doc.put("deliverMail",email);
        db.collection("Commands").document(id).set((doc)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //pd.dismiss()
               //Toast.makeText(AddMenuActivity.this, "Menu Ajouté", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //pd.dismiss()
                        //Toast.makeText(AddMenuActivity.this, "Erreur:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.title.setText(menuList.get(position).getTitle());
        holder.desc.setText(menuList.get(position).getDescription());
        holder.price.setText(menuList.get(position).getPrix());
    }


    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
