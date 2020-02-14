package com.dat153.oblig1.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
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
    private List<String> names;
    private List<Bitmap> images;
    private List<String> fileNames;
    private Context context;

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

    // Remove from storage
    public void removeFromStorage(String filename, Context context) {
        String path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + filename;
        File file = new File(path);
        file.delete();
    }

    // Removes from view
    public void remove(int position) {
        names.remove(position);
        images.remove(position);
        fileNames.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor
    public AdapterDB(List<String> names, List<Bitmap> images, List<String> fileNames, Context context) {
        this.names = names;
        this.images = images;
        this.fileNames = fileNames;
        this.context = context;
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
        if (!(names.size() <= 0)) {
            final String name = names.get(position);
            final Bitmap pic = images.get(position);
            final String filename = fileNames.get(position);
            holder.txtName.setText(name);
            holder.icon.setImageBitmap(pic);
            holder.rmButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(position);
                    removeFromStorage(filename, context);
                    notifyDataSetChanged();
                }
            });


        }
    }

    // Return the size of values (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return names.size();
    }



}
