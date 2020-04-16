package es.marcmauri.catseverywhere.catbreeds;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catseverywhere.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.marcmauri.catseverywhere.root.App;

public class CatBreedsActivity extends AppCompatActivity implements CatBreedsMVP.View {

    private final String TAG = CatBreedsActivity.class.getName();

    @BindView(R.id.activity_cats_root_view)
    ViewGroup rootView;

    @BindView(R.id.recyclerView_cat_breeds)
    RecyclerView recyclerView;

    @Inject
    CatBreedsMVP.Presenter presenter;

    private CatBreedListAdapter catBreedListAdapter;
    private List<CatBreedViewModel> catBreedList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_breedss);

        Log.i(TAG, "onCreate()");

        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

        catBreedListAdapter = new CatBreedListAdapter(catBreedList, new CatBreedListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CatBreedViewModel catBreed, int position) {
                presenter.onCatBreedItemClicked(catBreed);
            }
        });

        recyclerView.setAdapter(catBreedListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.rxJavaUnsubscribe();
        catBreedList.clear();
        catBreedListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(CatBreedViewModel viewModel) {
        catBreedList.add(viewModel);
        catBreedListAdapter.notifyItemInserted(catBreedList.size() - 1);
        Log.d(TAG, "New item inserted: " + viewModel.getBreedName());
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }
}