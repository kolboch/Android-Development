package bochynski.karol.bmi;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Karol on 2017-04-01.
 */

public class ImageShareUtils {

    final static String EXTERNAL_ABS_DIR_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String PNG_EXTENSION = ".png";

    public static Bitmap getScreenshot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap screenBitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return screenBitmap;
    }

    public static File storeBitmapToFile(Bitmap toStore, String dirFolderName, String fileName) {
        String folderDir = EXTERNAL_ABS_DIR_PATH + "/" + dirFolderName;
        File folder = new File(folderDir);
        if(!folder.exists()){
            folder.mkdir();
        }
        File file = new File(folder, fileName + PNG_EXTENSION);
        int i = 1;
        while(file.exists()){
            file = null;
            file = new File(folder, fileName + "(" + i + ")" + PNG_EXTENSION);
            i++;
        }
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            boolean compressResult = toStore.compress(CompressFormat.PNG, 100, fileOutputStream); // when png quality is ignored
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String generateFileName(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(d);
    }

}
