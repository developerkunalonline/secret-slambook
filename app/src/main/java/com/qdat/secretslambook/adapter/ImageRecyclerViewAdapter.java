package com.qdat.secretslambook.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qdat.secretslambook.R;

import java.util.List;

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.ViewHolder> {



    Context context;
    List<Bitmap> imageList;
    private OnItemClickListner listner = null;

    @NonNull
    @Override
    public ImageRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mInflater = LayoutInflater.from(context).inflate(R.layout.image_recyclerview_layout,parent,false);

        return new ViewHolder(mInflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.imageView.setImageBitmap(imageList.get(position));
        if (listner!=null){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onItemClicked(position , imageList.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();

    }

    public ImageRecyclerViewAdapter(Context context, List<Bitmap> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    public ImageRecyclerViewAdapter(Context context) {
        this.context = context;

    }
    public void setList(List<Bitmap> imageList){
        this.imageList = imageList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewer);
        }
    }
    public void setOnItemsClickListner(OnItemClickListner listner){
        this.listner = listner;
    }
    public interface OnItemClickListner{
        void onItemClicked(int position , Bitmap map);
    }
}
