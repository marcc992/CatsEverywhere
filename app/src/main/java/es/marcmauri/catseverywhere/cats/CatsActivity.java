package es.marcmauri.catseverywhere.cats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.example.catseverywhere.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.marcmauri.catseverywhere.root.App;

public class CatsActivity extends AppCompatActivity implements CatsMVP.View {

    private final String TAG = CatsActivity.class.getName();

    @BindView(R.id.activity_cats_root_view)
    ViewGroup rootView;

    @BindView(R.id.recyclerView_cat_breeds)
    RecyclerView recyclerView;

    @Inject
    CatsMVP.Presenter presenter;

    private CatListAdapter catListAdapter;
    private List<CatViewModel> catBreedList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cats);

        Log.i(TAG, "onCreate()");

        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

        catListAdapter = new CatListAdapter(catBreedList);

        recyclerView.setAdapter(catListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        presenter.rxJavaUnsuscribe();
        catBreedList.clear();
        catListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(CatViewModel viewModel) {
        catBreedList.add(viewModel);
        catListAdapter.notifyItemInserted(catBreedList.size() - 1);
        Log.d(TAG, "New item inserted: " + viewModel.getBreedName());
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }
}
