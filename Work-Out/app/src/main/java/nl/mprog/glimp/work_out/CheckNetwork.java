package nl.mprog.glimp.work_out;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

/**
 * Created by Gido Limperg on 27-6-2017.
 * Checks internet connection and displays a dialog when there is no connection. Source used:
 * https://stackoverflow.com/questions/15714122/checking-internet-connection-in-every-activity
 */

public class CheckNetwork {

    /**
     * Checks whether the user is currently connected to the internet.
     * @param context the Context in which the connection is being checked.
     * @return true if connected to internet, else false.
     */
    public static boolean isInternetAvailable(Context context)
    {
        ConnectivityManager service = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = service.getActiveNetworkInfo();

        return !(info == null);
    }

    /**
     * Display AlertDialog, allowing the user to retry internet connection.
     * @param context the Context in which the AlertDialog is being displayed.
     */
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
