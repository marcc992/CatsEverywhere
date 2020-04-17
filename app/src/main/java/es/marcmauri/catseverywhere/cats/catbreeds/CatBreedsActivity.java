package es.marcmauri.catseverywhere.cats.catbreeds;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catseverywhere.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.marcmauri.catseverywhere.cats.CatBreedViewModel;
import es.marcmauri.catseverywhere.cats.CountryViewModel;
import es.marcmauri.catseverywhere.cats.catbreeddetails.CatBreedDetailsActivity;
import es.marcmauri.catseverywhere.common.ExtraTags;
import es.marcmauri.catseverywhere.root.App;

public class CatBreedsActivity extends AppCompatActivity implements CatBreedsMVP.View {

    private final String TAG = CatBreedsActivity.class.getName();

    @BindView(R.id.activity_cat_breeds_root_view)
    ViewGroup rootView;

    @BindView(R.id.spinner_countries)
    Spinner spinner;

    @BindView(R.id.recyclerView_cat_breeds)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar_cat_breed_list)
    ProgressBar progressBar;

    @Inject
    CatBreedsMVP.Presenter presenter;

    private boolean existMoreCats;
    private int currentPage;

    private CatBreedListAdapter catBreedListAdapter;
    private ArrayList<CatBreedViewModel> catBreedList = new ArrayList<>();
    // TODO: Make CustomSpinnerAdapter<>
    //private ArrayAdapter<String> spinnerAdapter;
    //private ArrayList<CountryViewModel> catBreedCountries = new ArrayList<>();
    //private ArrayList<String> catBreedCountriesSpinner = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_breedss);

        Log.i(TAG, "onCreate()");

        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

        if (savedInstanceState != null) {
            existMoreCats = savedInstanceState.getBoolean("myExistMoreCats");
            currentPage = savedInstanceState.getInt("myCurrentPage");
            catBreedList = savedInstanceState.getParcelableArrayList("myCatBreeds");
        } else {
            existMoreCats = true;
            currentPage = 0;
        }
/*
        spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                catBreedCountriesSpinner);

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onCatBreedCountryClicked(catBreedCountries.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
*/
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if (dy > 0) {
                    if ((progressBar.getVisibility() != View.VISIBLE)
                            && ((totalItemCount - visibleItemCount) <= pastVisibleItems)) {
                        if (existMoreCats) {
                            presenter.loadCatBreedsFromPage(++currentPage);
                        }
                        // else means No more cats to show
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");

        presenter.setView(this);

        if (catBreedList.isEmpty()) {
            Log.i(TAG, "catBreedList is empty !!");
            currentPage = 0;
            presenter.loadCatBreedsFromPage(currentPage);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");

        // If the progresBarr is Visible in this point, it means that we are changing the activity
        // then, we need to stop receiving data and delete the more fresh elements from the catBreeds List
        // Remember the follow:
        // 1 => onSaveInstanceState() // Only when screen parameters changed: rotate, textSize, ...
        // 2 => onStop() // Always
        if (progressBar.getVisibility() == View.VISIBLE) {
            // TODO: Remove the elements which are fetching from the presenter
        } else {
            hiddenProgressBar();
        }

        presenter.rxJavaUnsubscribe();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState()");

        outState.putBoolean("myExistMoreCats", existMoreCats);
        Log.i(TAG, "Total Cat breed pages saved to Bundle! Value = " + existMoreCats);

        outState.putInt("myCurrentPage", currentPage);
        Log.i(TAG, "Current page saved to Bundle! Value = " + currentPage);

        // If the progresBarr is Visible in this point, it means that we are changing the appearance
        // of the current activity / screen.
        // Then, we need to stop receiving data and delete the more fresh elements from the catBreeds
        // lest before save them in the Bundle
        // Remember the follow:
        // 1 => onSaveInstanceState()
        // 2 => onStop()
        if (progressBar.getVisibility() == View.VISIBLE) {
            // TODO: Remove the elements which are fetching from the presenter
        } else {
            hiddenProgressBar();
        }

        outState.putParcelableArrayList("myCatBreeds", catBreedList);
        Log.i(TAG, "Cat breed List saved to Bundle! size = " + catBreedList.size());
    }

    @Override
    public void updateIfExistMoreCats(boolean moreCats) {
        this.existMoreCats = moreCats;
    }

    @Override
    public void updateData(CatBreedViewModel viewModel) {
        catBreedList.add(viewModel);
        catBreedListAdapter.notifyItemInserted(catBreedList.size() - 1);
        Log.d(TAG, "New item inserted: " + viewModel.getBreedName());
    }

    @Override
    public void updateSpinner(CountryViewModel country) {
        //catBreedCountries.add(country);
        //catBreedCountriesSpinner.add(country.getName());
        //spinnerAdapter.notifyDataSetChanged();
        Log.d(TAG, "New country inserted: " + country);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hiddenProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToCatBreedDetailsScreen(CatBreedViewModel catBreed) {
        Intent intent = new Intent(this, CatBreedDetailsActivity.class)
                .putExtra(ExtraTags.EXTRA_CAT_BREED_OBJECT, catBreed);
        startActivity(intent);
    }
}
