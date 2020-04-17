package es.marcmauri.catseverywhere.cats.catbreeddetails;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catseverywhere.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.marcmauri.catseverywhere.root.App;

public class CatBreedDetailsActivity extends AppCompatActivity implements CatBreedDetailsMVP.View {

    private static final String TAG = CatBreedDetailsActivity.class.getName();

    @BindView(R.id.activity_cat_breed_details_root_view)
    ViewGroup rootView;

    @BindView(R.id.imageView_details_breed)
    ImageView imageView_breed;

    @BindView(R.id.textView_details_breed_name)
    TextView textView_breedName;

    @BindView(R.id.textView_details_breed_description_content)
    TextView textView_breedDescription;

    @BindView(R.id.textView_details_breed_country_code_content)
    TextView textView_breedCountryCode;

    @BindView(R.id.textView_details_breed_temperament_content)
    TextView textView_breedTemperament;

    @BindView(R.id.textView_details_breed_wikipedia_link_content)
    TextView textView_breedWikiLink;

    @BindView(R.id.progressBar_cat_breed_details)
    ProgressBar progressBar;

    @Inject
    CatBreedDetailsMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_breed_details);

        Log.i(TAG, "onCreate()");

        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadData();
    }

    @Override
    public void setImage(String url) {
        if (url.isEmpty()) {
            Log.w(TAG, "The cat breed url is empty");
        } else {
            Picasso.get().load(url)
                    .fit().centerInside()
                    .into(imageView_breed);
        }
    }

    @Override
    public void setName(String name) {
        textView_breedName.setText(name);
    }

    @Override
    public void setDescription(String description) {
        textView_breedDescription.setText(description);
    }

    @Override
    public void setCountryCode(String countryCode) {
        textView_breedCountryCode.setText(countryCode);
    }

    @Override
    public void setTemperament(String temperament) {
        textView_breedTemperament.setText(temperament);
    }

    @Override
    public void setLink(String link) {
        textView_breedWikiLink.setText(link);
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
    public Bundle getExtras() {
        if (getIntent() != null) {
            return getIntent().getExtras();
        } else {
            Log.w(TAG, "getIntent() returns null");
            return null;
        }
    }
}
