package es.marcmauri.catseverywhere.catbreeds;

public class CatBreedViewModel {
    private String breedId;
    private String breedName;
    private String breedDescription;
    private String imgUrl;

    public CatBreedViewModel(String breedId, String breedName, String breedDescription, String imgUrl) {
        this.breedId = breedId;
        this.breedName = breedName;
        this.breedDescription = breedDescription;
        this.imgUrl = imgUrl;
    }

    public String getBreedId() {
        return breedId;
    }

    public void setBreedId(String breedId) {
        this.breedId = breedId;
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
