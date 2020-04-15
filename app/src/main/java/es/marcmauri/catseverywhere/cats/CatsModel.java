package es.marcmauri.catseverywhere.cats;

import io.reactivex.Observable;

public class CatsModel implements CatsMVP.Model {

    private Repository repository;

    public CatsModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<CatViewModel> result() {
        // Todo: parsear el objeto de la API con el objeto CatsViewModel
        return repository.getCatBreedsData();
    }
}
