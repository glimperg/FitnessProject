package nl.mprog.glimp.work_out;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by Gido Limperg on 27-6-2017.
 * Checks internet connection and displays a dialog when there is no connection. Source used:
 * https://stackoverflow.com/questions/15714122/checking-internet-connection-in-every-activity
 */

public class CheckNetwork {

    private static final String TAG = "CheckNetwork";

    public static boolean isInternetAvailable(Context context)
    {
        ConnectivityManager service = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = service.getActiveNetworkInfo();

        if (info == null)
        {
            Log.d(TAG,"no internet connection");
            return false;
        }
        else
        {
            if(info.isConnected())
            {
                Log.d(TAG," internet connection available...");
                return true;
            }
            else
            {
                Log.d(TAG," internet connection");
                return true;
            }

        }
    }

    public static void displayAlertDialog(final Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("No internet connection available.");
        builder.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).recreate();
            }
        });
        builder.show();
    }
}
