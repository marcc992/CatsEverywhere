package es.marcmauri.catseverywhere.cats;

import android.os.Parcel;
import android.os.Parcelable;

public class CatBreedViewModel implements Parcelable {
    private String breedId;
    private String breedName;
    private String breedDescription;
    private String breedCountryCode;
    private String breedTemperament;
    private String breedWikipediaUrl;
    private String breedImageUrl;

    public CatBreedViewModel(String breedId, String breedName, String breedDescription,
                             String breedCountryCode, String breedTemperament,
                             String breedWikipediaUrl, String breedImageUrl) {
        this.breedId = breedId;
        this.breedName = breedName;
        this.breedDescription = breedDescription;
        this.breedCountryCode = breedCountryCode;
        this.breedTemperament = breedTemperament;
        this.breedWikipediaUrl = breedWikipediaUrl;
        this.breedImageUrl = breedImageUrl;
    }

    protected CatBreedViewModel(Parcel in) {
        breedId = in.readString();
        breedName = in.readString();
        breedDescription = in.readString();
        breedCountryCode = in.readString();
        breedTemperament = in.readString();
        breedWikipediaUrl = in.readString();
        breedImageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(breedId);
        dest.writeString(breedName);
        dest.writeString(breedDescription);
        dest.writeString(breedCountryCode);
        dest.writeString(breedTemperament);
        dest.writeString(breedWikipediaUrl);
        dest.writeString(breedImageUrl);
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

    public String getBreedCountryCode() {
        return breedCountryCode;
    }

    public void setBreedCountryCode(String breedCountryCode) {
        this.breedCountryCode = breedCountryCode;
    }

    public String getBreedTemperament() {
        return breedTemperament;
    }

    public void setBreedTemperament(String breedTemperament) {
        this.breedTemperament = breedTemperament;
    }

    public String getBreedWikipediaUrl() {
        return breedWikipediaUrl;
    }

    public void setBreedWikipediaUrl(String breedWikipediaUrl) {
        this.breedWikipediaUrl = breedWikipediaUrl;
    }

    public String getBreedImageUrl() {
        return breedImageUrl;
    }

    public void setBreedImageUrl(String breedImageUrl) {
        this.breedImageUrl = breedImageUrl;
    }
}
