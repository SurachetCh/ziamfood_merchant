package revenue_express.ziamfood_merchant.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import revenue_express.ziamfood_merchant.R;
import revenue_express.ziamfood_merchant.model.APIService;
import revenue_express.ziamfood_merchant.model.User;

public class LoginActivity extends AppCompatActivity {
    //Ok http3
    private static final OkHttpClient client = new OkHttpClient();
    Button btnLogin;
    TextInputEditText edt_user,edt_password,edt_shop_id;
    String jsonData;
    JSONObject jsonObject;
    String url_login;
    Realm realm;
    String device_id,user_id,user_name,shop_name,shop_address1,shop_address2,shop_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
        bindView();
        setView();
    }

    private void bindView(){
        btnLogin = (Button)findViewById(R.id.btnLogin);
        edt_user = (TextInputEditText) findViewById(R.id.edt_user);
        edt_password = (TextInputEditText)findViewById(R.id.edt_password);
        edt_shop_id = (TextInputEditText)findViewById(R.id.edt_shop_id);
    }
    private void setView(){
        url_login = getResources().getString(R.string.Base_URL)+getResources().getString(R.string.URL_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.putExtra("tamp", "1");
//                getApplicationContext().startActivity(intent);
//                callSyncLogin(url_login);
                callLogin();
            }
        });

//        View.OnFocusChangeListener ofcListener = new MyFocusChangeListener();
//        edt_user.setOnFocusChangeListener(ofcListener);
//        edt_password.setOnFocusChangeListener(ofcListener);
//        edt_shop_id.setOnFocusChangeListener(ofcListener);
        edt_shop_id.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
//                            callSyncLogin(url_login);
                            callLogin();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

//    private void callSyncLogin(final String url) {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
////                imgLoad.setVisibility(View.VISIBLE);
//            }
//            @Override
//            protected Void doInBackground(Void... params) {
//                System.out.println("Url Value:"+url.toString());
//                FormBody.Builder formBuilder = new FormBody.Builder();
//                formBuilder.add("access_token", getResources().getString(R.string.access_token));
//                formBuilder.add("shop",getResources().getString(R.string.id_shop));
//                formBuilder.add("login", String.valueOf(edt_user.getText()));
//                formBuilder.add("pass", String.valueOf(edt_password.getText()));
//                formBuilder.add("skey", SplashScreenActivity.onesignal_userId);
//                MediaType.parse("application/json; charset=utf-8");
//                RequestBody formBody = formBuilder.build();
//                Request request = new Request.Builder()
//                        .url(url)
//                        .post(formBody)
//                        .build();
//                client.newCall(request)
//                        .enqueue(new Callback() {
//                            @Override
//                            public void onFailure(final Call call, IOException e) {
//                                // Error
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                    }
//                                });
//                            }
//                            @Override
//                            public void onResponse(Call call, Response response) throws IOException {
//                                jsonData = response.body().string();
//                                Log.i("Result login user", jsonData);
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        try {
//                                            jsonObject = new JSONObject(jsonData);
//                                            Log.i("Data login user: ",jsonObject.toString());
//                                            String msg = jsonObject.getString("mesg");
//                                            Log.i("Message: ",msg.toString());
//
//                                            if (jsonData.toLowerCase().contains("true")) {
//                                                JSONObject jsonObject1 = (JSONObject)jsonObject.get("data");
//                                                device_id = jsonObject1.getString("device_id");
//                                                user_id = jsonObject1.getString("user_id");
//                                                user_name = jsonObject1.getString("user_name");
//
//                                                DeleteUserRealm();
//                                                executeRealmUser(device_id,user_id,user_name);
//
//                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        startActivity(intent);
////                                                onBackPressed();
//                                            } else {
//                                                showMessageStatus("Login ", msg);
//                                            }
//                                        } catch (JSONException e) {
//                                            e.getMessage();
//                                        }
//                                    }
//                                });
//                            }
//                        });
//                return null;
//            }
//        }.execute();
//    }

    private void callLogin(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.Base_URL))
                .build();

        APIService api = retrofit.create(APIService.class);
        api.getLogin(getResources().getString(R.string.access_token),String.valueOf(edt_shop_id.getText()),String.valueOf(edt_user.getText()),String.valueOf(edt_password.getText()),SplashScreenActivity.onesignal_userId).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (!response.isSuccessful()) {
                    try {
                        throw new IOException("Unexpected code " + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("Response:",response.toString());
                    try {
                        jsonData = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                jsonObject = new JSONObject(jsonData);
                                Log.i("Data login user: ",jsonObject.toString());
                                String msg = jsonObject.getString("mesg");
                                Log.i("Message: ",msg.toString());

                                if (jsonData.toLowerCase().contains("true")) {
                                    JSONObject jsonObject1 = (JSONObject)jsonObject.get("data");
                                    device_id = jsonObject1.getString("device_id");
                                    user_id = jsonObject1.getString("user_id");
                                    user_name = jsonObject1.getString("user_name");
                                    JSONObject jsonObject2 = (JSONObject)jsonObject.get("shop");
                                    shop_name = jsonObject2.getString("title");
                                    shop_address1 = jsonObject2.getString("shop_addr1");
                                    shop_address2 = jsonObject2.getString("shop_addr2");
                                    shop_logo = jsonObject2.getString("img_logo_thumb");

                                    DeleteUserRealm();
                                    executeRealmUser(device_id,user_id,user_name, String.valueOf(edt_shop_id.getText()),shop_name,shop_address1,shop_address2,shop_logo);

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
//                                                onBackPressed();
                                } else {
                                    showMessageStatus("Login ", msg);
                                }
                            } catch (JSONException e) {
                                e.getMessage();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void executeRealmUser(final String device_id,final String user_id,final String user_name,final String shop_id,final String shop_name,final String shop_address1,final String shop_address2,final String shop_logo) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                User user = realm.createObject(User.class);
                user.setDevice_id(device_id);
                user.setUser_id(user_id);
                user.setUser_name(user_name);
                user.setShop_id(shop_id);
                user.setShop_name(shop_name);
                user.setShop_address1(shop_address1);
                user.setShop_address2(shop_address2);
                user.setShop_logo(shop_logo);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(LoginActivity.this,"Create user error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void DeleteUserRealm(){
        realm.beginTransaction();
        RealmResults<User> result = realm.where(User.class).findAll();
        result.deleteAllFromRealm();
        realm.commitTransaction();
    }

    //-----Show Message Status-----//
    private void showMessageStatus(String title , String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(title);
        builder.setMessage(msg);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private class MyFocusChangeListener implements View.OnFocusChangeListener {

        public void onFocusChange(View v, boolean hasFocus){

//            if(hasFocus = false) {

            InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

//            }
        }
    }
}
