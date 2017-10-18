package com.softmilktea.camcha;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by SEJIN on 2017-10-17.
 */

public class SnapshotGalleryActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snapshot_gallery);

        String camchaRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Camcha";
        File camchaDir = new File(camchaRootPath);

        if (!camchaDir.exists()){
            if (! camchaDir.mkdirs()){
                Dlog.e("Failed to create directory");
            }
        }
        else {
            List<Bitmap> snapshotList = makeSnapshotList(camchaDir);
            addSnapshotsToGallaryGridLayout(snapshotList);
            changeImageOfLargeImageView(snapshotList, snapshotList.size() - 1);
        }

    }

    /**
     * Make a linked list of snapshots of which extension is png in the given path
     * @author Sejin Jeon
     * @param rootPath
     * @return
     */
    public List<Bitmap> makeSnapshotList(File rootPath) {
        List<Bitmap> snapshotList = new LinkedList();
        File[] snapshots = rootPath.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".png");
            }
        });
        for(File snapshotFile : snapshots) {
//            Dlog.e(snapshotFile.getPath());
            Bitmap snapshotBitmap = BitmapFactory.decodeFile(snapshotFile.getPath());
            snapshotList.add(snapshotBitmap);
        }

        return snapshotList;
    }

    /**
     * Convert all snapshot bitmap files into image views and add them to gallary_grid_layout
     * (Need to separate 'gallary' from gallary grid layout)
     * @author Sejin Jeon
     * @param snapshotList
     */
    public void addSnapshotsToGallaryGridLayout(final List<Bitmap> snapshotList) {
        int height = (int)(150 * BaseApplication.DP);   // I have no idea why this value isn't precise.
        int width = (int)(height * ((float)BaseApplication.SCREEN_WIDTH / (float)BaseApplication.SCREEN_HEIGHT));
        int padding = (int)(5 * BaseApplication.DP);
        GridLayout snapshotListGridLayout = (GridLayout) findViewById(R.id.snapshot_gallery_grid_layout);
        // Bring images in reverse order of file name (because files are named in date fomat).
        Dlog.e(Integer.toString(snapshotList.size()));
        for(int i = snapshotList.size() - 1; i >= 0; i--) {
            final int idx = i;
            Bitmap snapshotBitmap = snapshotList.get(i);
            ImageView snapshotImageView = new ImageView(this);
            snapshotImageView.setImageBitmap(snapshotBitmap);
            snapshotImageView.setLayoutParams(new ViewGroup.LayoutParams(height/*width*/, width/*height*/));    // Swap them when the screenshot rotation feature is created
            snapshotImageView.setPadding(padding, 0, padding, 0);
            snapshotImageView.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeImageOfLargeImageView(snapshotList, idx);
                }
            });
            snapshotListGridLayout.addView(snapshotImageView);
        }
    }

    /**
     * Change the image file of snapshot_gallery_large_image_view with the selected file.
     * @author Sejin Jeon
     * @param snapshotList
     * @param idx
     */
    public void changeImageOfLargeImageView(List<Bitmap> snapshotList, int idx) {
        ImageView largeImageView = (ImageView) findViewById(R.id.snapshot_gallery_large_image_view);
        if(idx >= 0) {
            Bitmap snapshotBitmap = snapshotList.get(idx);
            largeImageView.setImageBitmap(snapshotBitmap);
        }
        else {
            largeImageView.setImageResource(R.drawable.icon_no_image);
        }
    }
}
