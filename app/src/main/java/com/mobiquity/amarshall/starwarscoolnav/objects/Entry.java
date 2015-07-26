package com.mobiquity.amarshall.starwarscoolnav.objects;

import java.io.Serializable;

/**
 * Created by Andrew on 7/25/15.
 */
public class Entry implements Serializable {

    private String mID;
    private String mName;
    private String mHeight;
    private String mMass;
    private String mHairColor;
    private String mSkinColor;
    private String mEyeColor;
    private String mBirthYear;
    private String mGender;
    private String mURL;

    private boolean cached;

    public Entry() {

    }

    public Entry(String _ID, String _name, String _height, String _mass,
                 String _hairColor, String _skinColor,
                 String _eyeColor, String _birthYear,
                 String _gender, String _URL) {

        mID = _ID;
        mName = _name;
        mHeight = _height;
        mMass = _mass;
        mHairColor = _hairColor;
        mSkinColor = _skinColor;
        mEyeColor = _eyeColor;
        mBirthYear = _birthYear;
        mGender = _gender;
        mURL = _URL;

    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmHeight() {
        return mHeight;
    }

    public void setmHeight(String mHeight) {
        this.mHeight = mHeight;
    }

    public String getmMass() {
        return mMass;
    }

    public void setmMass(String mMass) {
        this.mMass = mMass;
    }

    public String getmHairColor() {
        return mHairColor;
    }

    public void setmHairColor(String mHairColor) {
        this.mHairColor = mHairColor;
    }

    public String getmSkinColor() {
        return mSkinColor;
    }

    public void setmSkinColor(String mSkinColor) {
        this.mSkinColor = mSkinColor;
    }

    public String getmEyeColor() {
        return mEyeColor;
    }

    public void setmEyeColor(String mEyeColor) {
        this.mEyeColor = mEyeColor;
    }

    public String getmBirthYear() {
        return mBirthYear;
    }

    public void setmBirthYear(String mBirthYear) {
        this.mBirthYear = mBirthYear;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmURL() {
        return mURL;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

    public boolean isCached() {
        return cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }


}
