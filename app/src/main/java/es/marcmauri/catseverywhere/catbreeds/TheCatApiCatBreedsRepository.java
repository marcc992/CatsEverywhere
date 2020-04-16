package es.marcmauri.catseverywhere.catbreeds;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.marcmauri.catseverywhere.http.TheCatApiService;
import es.marcmauri.catseverywhere.http.apimodel.thecat.CatBreedApi;
import es.marcmauri.catseverywhere.http.apimodel.thecat.CatImageApi;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class TheCatApiCatBreedsRepository implements CatBreedsRepository {

    private static final String TAG = TheCatApiCatBreedsRepository.class.getName();
    private static final long CACHE_LIFETIME = 20 * 1000; // 20 seconds for cache

    private TheCatApiService theCatApiService;

    // todo: el objeto sera el propietario del servidor
    private List<CatBreedApi> catBreedList;
    private List<String> imageUrls;

    private long lastTimestamp;


    public TheCatApiCatBreedsRepository(TheCatApiService tcaService) {
        this.theCatApiService = tcaService;

        this.lastTimestamp = System.currentTimeMillis();

        this.catBreedList = new ArrayList<>();
        this.imageUrls = new ArrayList<>();
    }

    private boolean isUpdated() {
        return (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME;
    }

    @Override
    public Observable<CatBreedApi> getCatBreedFromNetwork() {

        Observable<List<CatBreedApi>> allCatBreedsObservable = theCatApiService.getAllCatBreeds(0, 15);

        return allCatBreedsObservable
                .concatMap(new Function<List<CatBreedApi>, Observable<CatBreedApi>>() {
                    @Override
                    public Observable<CatBreedApi> apply(List<CatBreedApi> catBreedApis) {
                        return Observable.fromIterable(catBreedApis);
                    }
                })
                .doOnNext(new Consumer<CatBreedApi>() {
                    @Override
                    public void accept(CatBreedApi catBreedApi) throws Exception {
                        catBreedList.add(catBreedApi);
                    }
                });
    }

    @Override
    public Observable<CatBreedApi> getCatBreedFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(catBreedList);
        } else {
            lastTimestamp = System.currentTimeMillis();
            catBreedList.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<CatBreedApi> getCatBreedData() {
        return getCatBreedFromCache().switchIfEmpty(getCatBreedFromNetwork());
    }

    @Override
    public Observable<String> getCatBreedImageUrlFromNetwork() {
        return getCatBreedFromNetwork()
                .concatMap(new Function<CatBreedApi, Observable<List<CatImageApi>>>() {
                    @Override
                    public Observable<List<CatImageApi>> apply(CatBreedApi catBreedApi) {
                        return theCatApiService.getBreedImageById(catBreedApi.getId(), "png,jpg", false);
                    }
                })
                .concatMap(new Function<List<CatImageApi>, Observable<CatImageApi>>() {
                    @Override
                    public Observable<CatImageApi> apply(List<CatImageApi> catImageApis) throws Exception {
                        if (catImageApis.isEmpty()) {
                            Log.w(TAG, "List of CatImageApi empty! Adding a dummy CatImageApi");
                            catImageApis.add(new CatImageApi());
                        }
                        return Observable.fromIterable(catImageApis);
                    }
                })
                .defaultIfEmpty(new CatImageApi())
                .concatMap(new Function<CatImageApi, Observable<String>>() {
                    @Override
                    public Observable<String> apply(CatImageApi catImageApi) {
                        if (catImageApi == null || catImageApi.getUrl() == null || catImageApi.getUrl().isEmpty()) {
                            return Observable.just("");
                        } else {
                            return Observable.just(catImageApi.getUrl());
                        }
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String url) throws Exception {
                        imageUrls.add(url);
                    }
                });
    }

    @Override
    public Observable<String> getCatBreedImageUrlFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(imageUrls);
        } else {
            lastTimestamp = System.currentTimeMillis();
            imageUrls.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<String> getCatBreedImageUrl() {
        return getCatBreedImageUrlFromCache().switchIfEmpty(getCatBreedImageUrlFromNetwork());
    }

}
