package es.marcmauri.catseverywhere.cats.catbreeds;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catseverywhere.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.marcmauri.catseverywhere.cats.CatBreedViewModel;

public class CatBreedListAdapter extends RecyclerView.Adapter<CatBreedListAdapter.ListItemViewHolder>
        implements Filterable {

    private final String TAG = CatBreedListAdapter.class.getName();

    private List<CatBreedViewModel> catBreedList;
    private List<CatBreedViewModel> catBreedListFiltered;
    private OnItemClickListener listener;

    public class ListItemViewHolder extends RecyclerView.ViewHolder {

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(catBreedList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }


    public CatBreedListAdapter(List<CatBreedViewModel> catBreedList, OnItemClickListener listener) {
        this.listener = listener;
        this.catBreedList = catBreedList;
        this.catBreedListFiltered = catBreedList;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cat_breed_list_item, parent, false);

        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        final CatBreedViewModel catBreed = catBreedListFiltered.get(position);
        Log.d(TAG, "Image fetched from breed " + catBreed.getName() + " => " + catBreed.getImageUrl());

        holder.catBreedName.setText(catBreed.getName());
        holder.catBreedDescription.setText(catBreed.getDescription());

        if (catBreed.getImageUrl().isEmpty()) {
            Log.w(TAG, "The cat breed " + catBreed.getName() + " has no image");
        } else {
            Picasso.get().load(catBreed.getImageUrl())
                    .fit().centerCrop()
                    .into(holder.catBreedImage);
        }
    }

    @Override
    public int getItemCount() {
        return catBreedListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String country = constraint.toString();
                if (country.isEmpty()) {
                    catBreedListFiltered = catBreedList;
                } else {
                    List<CatBreedViewModel> filteredList = new ArrayList<>();
                    for (CatBreedViewModel catBreed : catBreedList) {

                        // country match condition
                        if (catBreed.getCountryName().contains(country)) {
                            filteredList.add(catBreed);
                        }
                    }

                    catBreedListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = catBreedListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                catBreedListFiltered = (ArrayList<CatBreedViewModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }




    public interface OnItemClickListener {
        void onItemClick(CatBreedViewModel catBreed, int position);
    }
}
