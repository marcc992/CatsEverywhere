package es.marcmauri.catseverywhere.cats.catbreeds;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import es.marcmauri.catseverywhere.http.TheCatApiService;
import es.marcmauri.catseverywhere.http.apimodel.thecat.CatBreedApi;
import es.marcmauri.catseverywhere.http.apimodel.thecat.CatImageApi;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class TheCatApiCatBreedsRepository implements CatBreedsRepository {

    private static final String TAG = TheCatApiCatBreedsRepository.class.getName();
    private static final long CACHE_LIFETIME = 20 * 1000; // 20 seconds for cache

    private TheCatApiService theCatApiService;

    // Each map contains data from a specified page number
    private Map<Integer, List<CatBreedApi>> catBreedListMap;
    private Map<Integer, List<String>> imageUrlListMap;

    private long lastTimestamp;


    public TheCatApiCatBreedsRepository(TheCatApiService tcaService) {
        this.theCatApiService = tcaService;

        this.lastTimestamp = System.currentTimeMillis();

        this.catBreedListMap = new HashMap<>();
        this.imageUrlListMap = new HashMap<>();
    }

    private boolean isUpdated() {
        return (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME;
    }

    @Override
    public Observable<CatBreedApi> getCatBreedFromNetwork(int pageNumber) {

        Observable<List<CatBreedApi>> allCatBreedsObservable = theCatApiService.getAllCatBreeds(pageNumber, 20);

        return allCatBreedsObservable
                .concatMap(new Function<List<CatBreedApi>, Observable<CatBreedApi>>() {
                    @Override
                    public Observable<CatBreedApi> apply(List<CatBreedApi> catBreedApis) {
                        return Observable.fromIterable(catBreedApis);
                    }
                })
                .doOnNext(new Consumer<CatBreedApi>() {
                    @Override
                    public void accept(CatBreedApi catBreedApi) {
                        List<CatBreedApi> currentList = catBreedListMap.get(pageNumber);
                        if (currentList == null) {
                            currentList = new ArrayList<>();
                        }
                        currentList.add(catBreedApi);
                        catBreedListMap.put(pageNumber, currentList);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() {
                        Log.i("TAG", "CatBreeds obtained from Network");
                    }
                });
    }

    @Override
    public Observable<CatBreedApi> getCatBreedFromCache(int pageNumber) {
        if (isUpdated() && catBreedListMap.containsKey(pageNumber)) {
            Log.i(TAG, "CatBreeds obtained from Cache");

            try {
                return Observable.fromIterable(Objects.requireNonNull(catBreedListMap.get(pageNumber)));
            } catch (NullPointerException npe) {
                Log.w(TAG, "getCatBreedFromCache(..) -> catBreedListMap.get(" +
                        pageNumber + ") returns a NullPointerException. Message: " + npe.toString());
                return Observable.empty();
            }
        } else {
            lastTimestamp = System.currentTimeMillis();
            catBreedListMap.remove(pageNumber);
            return Observable.empty();
        }
    }

    @Override
    public Observable<CatBreedApi> getCatBreedData(int pageNumber) {
        return getCatBreedFromCache(pageNumber).switchIfEmpty(getCatBreedFromNetwork(pageNumber));
    }

    @Override
    public Observable<String> getCatBreedImageUrlFromNetwork(int pageNumber) {
        return getCatBreedFromNetwork(pageNumber)
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
                        List<String> currentList = imageUrlListMap.get(pageNumber);
                        if (currentList == null) {
                            currentList = new ArrayList<>();
                        }
                        currentList.add(url);
                        imageUrlListMap.put(pageNumber, currentList);
                    }
                });
    }

    @Override
    public Observable<String> getCatBreedImageUrlFromCache(int pageNumber) {


        if (isUpdated() && imageUrlListMap.containsKey(pageNumber)) {
            Log.i(TAG, "CatBreeds obtained from Cache");
            try {
                return Observable.fromIterable(Objects.requireNonNull(imageUrlListMap.get(pageNumber)));
            } catch (NullPointerException npe) {
                Log.w(TAG, "getCatBreedImageUrlFromCache(..) -> imageUrlListMap.get(" +
                        pageNumber + ") returns a NullPointerException. Message: " + npe.toString());
                return Observable.empty();
            }
        } else {
            lastTimestamp = System.currentTimeMillis();
            imageUrlListMap.remove(pageNumber);
            return Observable.empty();
        }
    }

    @Override
    public Observable<String> getCatBreedImageUrl(int pageNumber) {
        return getCatBreedImageUrlFromCache(pageNumber).switchIfEmpty(getCatBreedImageUrlFromNetwork(pageNumber));
    }

}
