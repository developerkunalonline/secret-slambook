package com.qdat.secretslambook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qdat.secretslambook.R;
import com.qdat.secretslambook.utility.Dairy;

import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    List<Dairy> dairyList;
    Context context;

    private OnItemClickListner listner = null;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_recycler_view_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(dairyList.get(position).getTitle());
        holder.date.setText(dairyList.get(position).getDate().getDate());

        if (listner!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onItemClicked(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dairyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title , date;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text_view);
            date = itemView.findViewById(R.id.date_viewer);
            view = itemView;
        }
    }

    public HomeRecyclerAdapter(Context context,List<Dairy> dairyList) {
        this.dairyList = dairyList;
        this.context = context;
    }

    public interface OnItemClickListner{
        void onItemClicked(int position);
    }

    public void setOnItemsClickListner(OnItemClickListner listner){
        this.listner = listner;
    }
    public HomeRecyclerAdapter(Context context) {
        this.context = context;
    }
    public void setDairyList(List<Dairy> dairyList){
        this.dairyList = dairyList;
    }
}
