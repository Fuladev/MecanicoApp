package com.mecanico.mecanicoapp.recursos;

public class SpinnerItem {
    private String mMarcaName;
    private int mIconImage;

    public SpinnerItem(String marcaName, int iconImage) {
        this.mMarcaName = marcaName;
        this.mIconImage = iconImage;
    }


    public String getMarcaName() {
        return mMarcaName;
    }

    public int getIconImage() {
        return mIconImage;
    }
}
