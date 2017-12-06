package com.sjl.platform.base.image;

/**
 * ImageManager
 *
 * @author SJL
 * @date 2017/12/6
 */

public class ImageManager {
    private static final String TAG = "ImageManager";
    private static ImageManager imageManager;

    public static ImageManager getInstance() {
        if (imageManager == null) {
            synchronized (TAG) {
                if (imageManager == null) {
                    imageManager = new ImageManager();
                }
            }
        }
        return imageManager;
    }


}
