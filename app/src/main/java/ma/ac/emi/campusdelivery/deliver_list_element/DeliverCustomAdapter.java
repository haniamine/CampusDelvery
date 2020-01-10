package ma.ac.emi.campusdelivery.deliver_list_element;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.List;

import ma.ac.emi.campusdelivery.MenuActivity;
import ma.ac.emi.campusdelivery.R;
import ma.ac.emi.campusdelivery.admin.AddStoreActivity;
import ma.ac.emi.campusdelivery.admin.AdminMenuActivity;
import ma.ac.emi.campusdelivery.models.Command;
import ma.ac.emi.campusdelivery.models.Store;

public class DeliverCustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    ListActivity listActivity;
    List<Command> commandList;
    Context context;

    FirebaseFirestore db;
    FirebaseAuth fAuth;
    String mail;
boolean decision;
    public DeliverCustomAdapter(ListActivity listActivity, List<Command> commandList, Context context) {
        // this.listActivity = listActivity;
        this.commandList = commandList;
        this.context = context;
    }
    public DeliverCustomAdapter(List<Command> commandList) {
        this.commandList = commandList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_command_layout,parent,false);

        db  = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        ViewHolder viewHolder = new ViewHolder(itemView);
        // item click
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = commandList.get(position).getId();
                mail = fAuth.getCurrentUser().getEmail();
               // decideCommand(view,"Prendre la Livraison","Est-ce que vous voulez livrer cette commande?");
                takeDelivery(id, mail);
                Toast.makeText(view.getContext(), "Vous êtes maintenant le livreur de cette commande", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {
                String id = commandList.get(position).getId();
              //  decideCommand(view,"Livraison Terminé","Est-ce que vous avez terminer la livraison?");
                confirmDelivery(id);
                Toast.makeText(view.getContext(),"Livraison terminé",Toast.LENGTH_SHORT).show();

            }

        });

        return viewHolder;
    }


    private void  decideCommand(View view,String titre,String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(titre);
        builder.setMessage(msg);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                decision = true;
                dialog.notifyAll();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                decision =false;
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    private void confirmDelivery(String id) {
        db.collection("Commands").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //pd.dismiss()
                       // Toast.makeText(AdminMenuActivity.this, "Magasin Suppimé !", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //pd.dismiss()
                        //Toast.makeText(AdminMenuActivity.this, "Erreur:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void takeDelivery(String id,String mail) {
        db.collection("Commands").document(id)
                .update("deliverMail",mail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //pd.dismiss()
                       // Toast.makeText(AddStoreActivity.this, "Magasin à jour !", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //pd.dismiss()
                       // Toast.makeText(AddStoreActivity.this, "Erreur:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // bind   view / set data
        holder.title.setText(commandList.get(position).getTitle());
        holder.price.setText(commandList.get(position).getPrice());
        holder.store.setText(commandList.get(position).getStore());
    }

    @Override
    public int getItemCount() {
        return commandList.size();
    }
}
