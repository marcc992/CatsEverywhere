package es.marcmauri.catseverywhere.cats.catbreeds;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

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

    private CatBreedListAdapter catBreedListAdapter;
    private List<CatBreedViewModel> catBreedList = new ArrayList<>();
    // TODO: Make CustomSpinnerAdapter<>
    private ArrayAdapter<String> spinnerAdapter;
    private List<CountryViewModel> catBreedCountries = new ArrayList<>();
    private List<String> catBreedCountriesSpinner = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_breedss);

        Log.i(TAG, "onCreate()");

        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadCountries();
        presenter.loadAllData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.rxJavaUnsubscribe();
        catBreedCountries.clear();
        catBreedCountriesSpinner.clear();
        spinnerAdapter.notifyDataSetChanged();
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
    public void updateSpinner(CountryViewModel country) {
        catBreedCountries.add(country);
        catBreedCountriesSpinner.add(country.getName());
        spinnerAdapter.notifyDataSetChanged();
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
