package nl.mprog.glimp.work_out;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Gido Limperg on 12-6-2017.
 * AsyncTask that loads an image, scales it and sets it to an ImageView.
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
        return image;

    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        if (result != null) {
            // scale image
            int newWidth = Math.min((int) (result.getWidth() * 0.5), 400);
            int newHeight = Math.min((int) (result.getHeight() * 0.6), 400);
            Bitmap scaledImage = Bitmap.createScaledBitmap(result, newWidth, newHeight, true);

            imageView.setImageBitmap(scaledImage);
        }
    }
}

