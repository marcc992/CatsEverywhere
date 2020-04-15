package es.marcmauri.catseverywhere.cats;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catseverywhere.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatListAdapter extends RecyclerView.Adapter<CatListAdapter.ListItemViewHolder> {

    private final String TAG = CatListAdapter.class.getName();

    private View itemView;
    private List<CatViewModel> list;
    private OnItemClickListener listener;


    public CatListAdapter(List<CatViewModel> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_breed_list_item, parent, false);
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

        holder.cardViewCatBreed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(list.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ListItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardView_cat_breed)
        CardView cardViewCatBreed;

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

    public interface OnItemClickListener {
        void onItemClick(CatViewModel catBreed, int position);
    }
}
