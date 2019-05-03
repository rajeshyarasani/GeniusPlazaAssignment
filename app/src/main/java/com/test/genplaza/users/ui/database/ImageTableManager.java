package com.test.genplaza.users.ui.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ImageTableManager {
    private static ImageTableManager mInstance;
    private static ImagesDao imagesDao;

    public static ImageTableManager getInstance() {
        if (mInstance == null) {
            mInstance = new ImageTableManager();
        }
        return mInstance;
    }

    public static void initRoom(Context context) {
        imagesDao = ImagesDB.getInstance(context).getRepoDao();
    }

    public ImageEntity getImageTable(String imageId) {
        ImageEntity imageTable = imagesDao.getImageWithComments(imageId);
        return imageTable;
    }

    public ImageEntity insertImageTable(String imageId, String comment) {
        ImageEntity imageTable = imagesDao.getImageWithComments(imageId);
        if (imageTable != null) {
            List<String> oldComments = imageTable.getComments();
            oldComments.add(comment);
            imageTable.setComments(oldComments);
            imagesDao.updateImageTable(imageTable);
            return imageTable;
        } else {
            ImageEntity newImageTable = new ImageEntity();
            newImageTable.setImageId(imageId);
            List<String> comments = new ArrayList<>();
            comments.add(comment);
            newImageTable.setComments(comments);
            imagesDao.insertImageTable(newImageTable);
            return newImageTable;
        }
    }
}
