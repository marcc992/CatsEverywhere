package es.marcmauri.catseverywhere.cats;

import android.os.Parcel;
import android.os.Parcelable;

public class CatBreedViewModel implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String countryCode;
    private String countryName;
    private String temperament;
    private String wikipediaUrl;
    private String imageUrl;

    public CatBreedViewModel(String id, String name, String description,
                             String countryCode, String countryName,
                             String temperament, String wikipediaUrl,
                             String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.temperament = temperament;
        this.wikipediaUrl = wikipediaUrl;
        this.imageUrl = imageUrl;
    }

    protected CatBreedViewModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        countryCode = in.readString();
        countryName = in.readString();
        temperament = in.readString();
        wikipediaUrl = in.readString();
        imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(countryCode);
        dest.writeString(countryName);
        dest.writeString(temperament);
        dest.writeString(wikipediaUrl);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CatBreedViewModel> CREATOR = new Creator<CatBreedViewModel>() {
        @Override
        public CatBreedViewModel createFromParcel(Parcel in) {
            return new CatBreedViewModel(in);
        }

        @Override
        public CatBreedViewModel[] newArray(int size) {
            return new CatBreedViewModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
