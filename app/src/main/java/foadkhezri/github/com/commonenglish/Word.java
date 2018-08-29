package foadkhezri.github.com.commonenglish;

public class Word {

    private String miwokTranslation;
    private String defaultTranslation;
    private int mAudioResourceId;
    private int mImageResourceId = NO_IMAGE;
    private static final int NO_IMAGE = -1;
    private int backgroundColor;


    public Word(String a, String b, int imageResourceId, int mAudioResourceId, int backgroundColor) {
        this.miwokTranslation = a;
        this.defaultTranslation =b;
        this.mImageResourceId = imageResourceId;
        this.mAudioResourceId = mAudioResourceId;
        this.backgroundColor = backgroundColor;
    }

    public Word(String a, String b, int mAudioResourceId, int backgroundColor) {
        this.miwokTranslation = a;
        this.defaultTranslation= b;
        this.mAudioResourceId = mAudioResourceId;
        this.backgroundColor = backgroundColor;
    }

    public String getMiwokTranslation() {return miwokTranslation;}

    public String getDefaultTranslation() {return defaultTranslation;}

    public int getmAudioResourceId() {return mAudioResourceId;}

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE;
    }

    public int getBackgroundColor() {return backgroundColor;}

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    @Override
    public String toString() {
        return "Word{" +
                "miwokTranslation='" + miwokTranslation + '\'' +
                ", defaultTranslation='" + defaultTranslation + '\'' +
                ", mAudioResourceId=" + mAudioResourceId +
                ", mImageResourceId=" + mImageResourceId +
                ", backgroundColor=" + backgroundColor +
                '}';
    }
}