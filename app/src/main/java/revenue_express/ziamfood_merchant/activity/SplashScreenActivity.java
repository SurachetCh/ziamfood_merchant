package revenue_express.ziamfood_merchant.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.onesignal.OneSignal;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import revenue_express.ziamfood_merchant.R;
import revenue_express.ziamfood_merchant.model.User;

public class SplashScreenActivity extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;
    Realm realm;
    Integer a = 0;
    static String onesignal_userId,onesignal_registrationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // onesignal
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);

                onesignal_userId = userId;
                onesignal_registrationId = registrationId;
            }
        });

        // no statudbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);

        realm.beginTransaction();
        RealmResults<User> result = realm.where(User.class).findAll();
        a = result.size();
        realm.commitTransaction();

        handler = new Handler();

        runnable = new Runnable() {
            public void run() {
                if (a != 0){
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                }else {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };
    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

    public void connectInternet() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            Log.d("สถานะการเชื่อมต่อ", "ต่อ internet");

        } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            Log.d("สถานะการเชื่อมต่อ", "ไม่ต่อ internet");
            checkInternet(getResources().getString(R.string.status_connect), getResources().getString(R.string.disconnection));
        }
    }

    //Check internet
    private void checkInternet(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);
        builder.setTitle(title);
        builder.setMessage(msg);

        String positiveText = getResources().getString(R.string.setting);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    }
                });

        String negativeText = getResources().getString(R.string.close);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
