package es.marcmauri.catseverywhere.http;

import java.util.List;

import es.marcmauri.catseverywhere.http.apimodel.thecat.CatBreedApi;
import es.marcmauri.catseverywhere.http.apimodel.thecat.CatImageApi;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheCatApiService {

    @GET("breeds")
    Observable<List<CatBreedApi>> getAllCatBreeds(
            @Query("page") int page,
            @Query("limit") int resultsPerPage);

    @GET("images/search")
    Observable<List<CatImageApi>> getBreadImageById(
            @Query("breed_ids") String breedId,
            //@Query("size") String size,           //TODO: {full, med, small, thumb} This doesn't work
            //@Query("mime_types") String mimeType, //TODO: {jpg,gif,png} Not all breeds have all formats
            @Query("include_breeds") boolean includeBreed);
}
