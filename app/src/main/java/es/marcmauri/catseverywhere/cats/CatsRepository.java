package es.marcmauri.catseverywhere.cats;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class CatsRepository implements Repository {

    private static final String TAG = CatsRepository.class.getName();
    private static final long CACHE_LIFETIME = 20 * 1000; // 20 seconds for cache

    // todo: el objeto sera el propietario del servidor
    private List<CatViewModel> results;

    private long lastTimestamp;


    public CatsRepository() {
        // todo: this.apiService = param[0];

        this.lastTimestamp = System.currentTimeMillis();

        this.results = new ArrayList<>();
    }

    private boolean isUpdated() {
        return (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME;
    }

    @Override
    public Observable<CatViewModel> getCatBreedsFromNetwork() {
        // todo:logica con la api de los gatos
        return null;
    }

    @Override
    public Observable<CatViewModel> getCatBreedsFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(results);
        } else {
            lastTimestamp = System.currentTimeMillis();
            results.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<CatViewModel> getCatBreedsData() {
        return getCatBreedsFromCache().switchIfEmpty(getCatBreedsFromNetwork());
    }
}
