package es.marcmauri.catseverywhere.cats;

import android.os.Parcel;
import android.os.Parcelable;

public class CountryViewModel implements Parcelable {

    private String code;
    private String name;

    public CountryViewModel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    protected CountryViewModel(Parcel in) {
        code = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CountryViewModel> CREATOR = new Creator<CountryViewModel>() {
        @Override
        public CountryViewModel createFromParcel(Parcel in) {
            return new CountryViewModel(in);
        }

        @Override
        public CountryViewModel[] newArray(int size) {
            return new CountryViewModel[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
