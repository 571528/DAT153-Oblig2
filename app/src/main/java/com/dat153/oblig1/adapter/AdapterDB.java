package com.dat153.oblig1.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dat153.oblig1.R;

public class AdapterDB extends RecyclerView.Adapter<AdapterDB.ViewHolder> {
    private List<String> values;
    private List<Bitmap> images;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtName;
        public ImageView icon;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtName = v.findViewById(R.id.dbName);
            icon = v.findViewById(R.id.icon);
        }
    }

    public void add(int position, String item, Bitmap bm) {
        values.add(position, item);
        images.add(position, bm);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        images.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterDB(List<String> nameList, List<Bitmap> imageList) {
        values = nameList;
        images = imageList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterDB.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = values.get(position);
        final Bitmap pic = images.get(position);
        holder.txtName.setText(name);
        holder.icon.setImageBitmap(pic);

        holder.txtName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        holder.txtName.setText(name);
        holder.icon.setImageBitmap(pic);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }


}
