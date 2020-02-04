package com.dat153.oblig1.adapter;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dat153.oblig1.R;

public class AdapterDB extends RecyclerView.Adapter<AdapterDB.ViewHolder> {
    private List<String> values;
    private List<Bitmap> images;
    private List<String> imgFilename;
    SharedPreferences sp;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public ImageView icon;
        public ImageButton rmButton;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtName = v.findViewById(R.id.dbName);
            icon = v.findViewById(R.id.icon);
            rmButton = v.findViewById(R.id.rmButton);
        }
    }

    // Adds to values and images
    public void add(int position, String item, Bitmap bm, String imgFile) {
        values.add(position, item);
        images.add(position, bm);
        notifyItemInserted(position);
    }

    // Removes from shared preferences
    public void removeFromSP(int position) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(imgFilename.get(position));
        editor.commit();
    }

    // Removes from view
    public void remove(int position) {
        values.remove(position);
        images.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor
    public AdapterDB(Context appContext, List<String> nameList, List<Bitmap> imageList, List<String> fileList) {
        values = nameList;
        images = imageList;
        imgFilename = fileList;
        sp = appContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
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
        // - get a name and image from values and images at this position
        // - replace the contents of the view with that element
        final String name = values.get(position);
        final Bitmap pic = images.get(position);
        holder.txtName.setText(name);
        holder.icon.setImageBitmap(pic);

        if (!(name.equals("No Classmates!"))) {
            holder.rmButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeFromSP(position);
                    remove(position);
                }
            });
            holder.txtName.setText(name);
            holder.icon.setImageBitmap(pic);
        }


    }

    // Return the size of values (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }


}
