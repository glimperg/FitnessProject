package nl.mprog.glimp.work_out;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Gido Limperg on 12-6-2017.
 */

public class ImageAsyncTask extends AsyncTask<Object, Void, Bitmap> {

    private ImageView imageView;
    private String url;

    public ImageAsyncTask(ImageView imageView) {
        this.imageView = imageView;
        this.url = imageView.getTag().toString();
    }

    @Override
    protected Bitmap doInBackground(Object... params) {

        Bitmap image = null;
        try {
            InputStream inputStream = new java.net.URL(url).openStream();
            image = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert image != null;
        // return scaled image
        // TODO: hiermee experimenteren
        int newWidth = Math.min((int)(image.getWidth()*0.5),600);
        int newHeight = Math.min((int)(image.getHeight()*0.5),400);
        return Bitmap.createScaledBitmap(image,newWidth,newHeight,true);

    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        imageView.setImageBitmap(result);
    }
}

