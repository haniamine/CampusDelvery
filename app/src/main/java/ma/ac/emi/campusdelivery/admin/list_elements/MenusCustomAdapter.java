package ma.ac.emi.campusdelivery.admin.list_elements;

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
import ma.ac.emi.campusdelivery.admin.AdminMenuActivity;
import ma.ac.emi.campusdelivery.models.Menu;
import ma.ac.emi.campusdelivery.models.Store;

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
                Toast.makeText(view.getContext(),"Commande effecttué",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(final View view, final int position) {

                Toast.makeText(view.getContext(),"Commande effecttué",Toast.LENGTH_SHORT).show();


            }
        });

        return viewHolder;
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
