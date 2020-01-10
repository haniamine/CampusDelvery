package ma.ac.emi.campusdelivery.deliver_list_element;

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

import ma.ac.emi.campusdelivery.MenuActivity;
import ma.ac.emi.campusdelivery.R;
import ma.ac.emi.campusdelivery.models.Command;
import ma.ac.emi.campusdelivery.models.Store;

public class DeliverCustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    ListActivity listActivity;
    List<Command> commandList;
    Context context;

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

        ViewHolder viewHolder = new ViewHolder(itemView);
        // item click
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
            }

        });

        return viewHolder;
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
