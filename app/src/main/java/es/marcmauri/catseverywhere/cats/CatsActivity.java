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
        // todo: presenter.loadData();
        updateData(new CatViewModel("Yorkshire", "Perrete muy, pero que muy repelente", "no_image"));
        updateData(new CatViewModel("Yorkshire 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut ali", "no_image"));
        updateData(new CatViewModel("Yorkshire 3", "m veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. E", "no_image"));
        updateData(new CatViewModel("Yorkshire 4", "smod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in r", "no_image"));
        updateData(new CatViewModel("Yorkshire 5", "nulla pariatur. Excepteur sint o", "no_image"));
        updateData(new CatViewModel("Yorkshire 6", "m dolor sit amet,", "no_image"));
        updateData(new CatViewModel("Yorkshire 7", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", "no_image"));
        updateData(new CatViewModel("Yorkshire 8", "Molestie nunc non blandit massa. Etiam tempor orci eu lobortis elementum nibh tellus molestie ", "no_image"));
        updateData(new CatViewModel("Yorkshire 9", "nunc. Id nibh tortor id aliquet lectus proin nibh. Ac feugiat sed lectus vestib", "no_image"));
        updateData(new CatViewModel("Yorkshire 10", "Id nibh tortor id aliquet lectus proin nibh. Ac feugiat sed lectus vestibulum mattis ullamcorper velit sed. In nibh mauris cursus mattis molestie a. A", "no_image"));
        updateData(new CatViewModel("Yorkshire 11", "get. Arcu ac tortor dignissim convallis aenean et. Morbi enim nunc faucibus a pel", "no_image"));
        updateData(new CatViewModel("Yorkshire 12", "Congue mauris rhoncus aenean vel elit scelerisque mauris. Rhoncus urna neque viverra justo nec ultrices dui sapien eget. Pellentesque nec nam aliquam sem et.", "no_image"));
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
