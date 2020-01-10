package ma.ac.emi.campusdelivery.admin.list_elements;
import ma.ac.emi.campusdelivery.StoresFragment;
import ma.ac.emi.campusdelivery.admin.*;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ma.ac.emi.campusdelivery.R;
import ma.ac.emi.campusdelivery.models.Store;

public class StoresCustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    ListActivity listActivity;
    List<Store> storeList;
    Context context;

    public StoresCustomAdapter(ListActivity listActivity, List<Store> storeList, Context context) {
        // this.listActivity = listActivity;
        this.storeList = storeList;
        this.context = context;
    }
    public StoresCustomAdapter(List<Store> storeList) {
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_store_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        // item click
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String id = storeList.get(position).getId();
                String name = storeList.get(position).getName();
                Intent i = new Intent(view.getContext(),AdminMenuActivity.class);
                i.putExtra("storeID",id);
                i.putExtra("storeName",name);
                view.getContext().startActivity(i);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                String name =storeList.get(position).getName();
                Toast.makeText(view.getContext(),name,Toast.LENGTH_SHORT).show();
            }

        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // bind   view / set data
        holder.name.setText(storeList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }
}
