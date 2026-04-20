package com.example.reallogin;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import com.otaliastudios.zoom.ZoomLayout;
import pl.droidsonroids.gif.GifImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.ViewHolder> {

    private List<GifModel> gifList;
    private List<GifModel> fullList;
    private Context context;
    // 🔥 Click listener for game mode
    public interface OnItemClickListener {
        void onItemClick(GifModel model);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public GifAdapter(Context context, List<GifModel> gifList) {
        this.context = context;
        this.gifList = new ArrayList<>(gifList);
        this.fullList = new ArrayList<>(gifList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gifImage;
        TextView gifName;

        public ViewHolder(View itemView) {
            super(itemView);
            gifImage = itemView.findViewById(R.id.gifImage);
            gifName = itemView.findViewById(R.id.gifName);
        }
    }

    @Override
    public GifAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gif_item, parent, false);
        return new ViewHolder(view);
    }

    @Override

    public void onBindViewHolder(GifAdapter.ViewHolder holder, int position) {

        GifModel model = gifList.get(position);
        if (listener != null) {
            // 🎮 Game mode → hide text
            holder.gifName.setVisibility(View.GONE);
        } else {
            // 📚 Learning mode → show text
            holder.gifName.setVisibility(View.VISIBLE);
            holder.gifName.setText(model.getName().toUpperCase());
        }
        Log.d("GIF_TEST", "Name: " + model.getName() + ", ID: " + model.getResId());

        Glide.with(context).asGif().load(model.getResId()).into(holder.gifImage);

        holder.itemView.setOnClickListener(v -> {

            // 🔥 IF Game mode is using adapter
            if (listener != null) {
                listener.onItemClick(model);
                return;
            }

            // 🔥 OTHERWISE → Learning Mode Fullscreen Dialog

            Context dialogContext = context;

            pl.droidsonroids.gif.GifImageView gifView =
                    new pl.droidsonroids.gif.GifImageView(dialogContext);

            gifView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));

            gifView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            gifView.setImageResource(model.getResId());

            com.otaliastudios.zoom.ZoomLayout zoomLayout =
                    new com.otaliastudios.zoom.ZoomLayout(dialogContext);

            zoomLayout.addView(gifView);

            AlertDialog dialog = new AlertDialog.Builder(dialogContext).create();
            dialog.setView(zoomLayout);
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return gifList.size();
    }

    public void filter(String text) {
        gifList.clear();
        if (text.isEmpty()) {
            gifList.addAll(fullList);
        } else {
            text = text.toLowerCase();
            for (GifModel item : fullList) {
                if (item.getName().toLowerCase().contains(text)) {
                    gifList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}

