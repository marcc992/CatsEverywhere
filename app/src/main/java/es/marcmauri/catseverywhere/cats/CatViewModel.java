package es.marcmauri.catseverywhere.cats;

public class CatViewModel {
    private String breedName;
    private String breedDescription;
    private String imgUrl;

    public CatViewModel(String breedName, String breedDescription, String imgUrl) {
        this.breedName = breedName;
        this.breedDescription = breedDescription;
        this.imgUrl = imgUrl;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public String getBreedDescription() {
        return breedDescription;
    }

    public void setBreedDescription(String breedDescription) {
        this.breedDescription = breedDescription;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
