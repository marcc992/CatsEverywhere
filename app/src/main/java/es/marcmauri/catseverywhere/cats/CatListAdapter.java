package es.marcmauri.catseverywhere.cats;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catseverywhere.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatListAdapter extends RecyclerView.Adapter<CatListAdapter.ListItemViewHolder> {

    private final String TAG = CatListAdapter.class.getName();

    List<CatViewModel> list;


    public CatListAdapter(List<CatViewModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_breed_list_item, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        Log.d(TAG, "Image fetched from breed " + list.get(position).getBreedName() + " => " + list.get(position).getImgUrl());

        if (list.get(position).getImgUrl().isEmpty()) {
            Log.w(TAG, "The cat breed " + list.get(position).getBreedName() + " has no image");
        } else {
            Picasso.get().load(list.get(position).getImgUrl())
                    .fit().centerCrop()
                    .into(holder.catBreedImage);
        }

        holder.catBreedName.setText(list.get(position).getBreedName());
        holder.catBreedDescription.setText(list.get(position).getBreedDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ListItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView_cat_breed)
        ImageView catBreedImage;

        @BindView(R.id.textView_breed_name)
        TextView catBreedName;

        @BindView(R.id.textView_bread_description)
        TextView catBreedDescription;

        ListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
