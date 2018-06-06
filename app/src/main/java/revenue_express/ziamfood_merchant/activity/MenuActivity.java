package revenue_express.ziamfood_merchant.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import revenue_express.ziamfood_merchant.R;
import revenue_express.ziamfood_merchant.model.APIService;

public class MenuActivity extends AppCompatActivity {
    WebView web_view;
    ImageView imgLoad;
    String temp,code,id;
    Button btnBottom;
    LinearLayout btnBack;
    String URL_order_detail,URL_confirm_finish,URL_confirm_cooking;
    //Ok http3
    private final OkHttpClient client = new OkHttpClient();
    JSONObject jsonObject = null;
    JSONArray jsonArray = null;
    String jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        URL_order_detail = getResources().getString(R.string.Base_URL)+getResources().getString(R.string.URL_order_detail);
        URL_confirm_cooking = getResources().getString(R.string.Base_URL)+getResources().getString(R.string.URL_confirm_cooking);
        URL_confirm_finish = getResources().getString(R.string.Base_URL)+getResources().getString(R.string.URL_confirm_finish);
        bindView();
        setView();
    }
    private void bindView(){
        web_view = (WebView) findViewById(R.id.web_view);
        btnBottom = (Button)findViewById(R.id.btnBottom);
        btnBack = (LinearLayout) findViewById(R.id.btnBack);
        imgLoad = (ImageView)findViewById(R.id.imgLoad);
    }
    private void setView(){
        Glide.with(this)
                .load(R.drawable.downloads_gif)
                .asGif()
                .into(imgLoad);
        imgLoad.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        temp = intent.getStringExtra("temp");
        id = intent.getStringExtra("id");
        code = intent.getStringExtra("code");
        if (temp.equals("0")){
            btnBottom.setText(getResources().getString(R.string.confirm));
        }else if (temp.equals("1")){
            btnBottom.setText(getResources().getString(R.string.confirm));
        }else if (temp.equals("2")){
            btnBottom.setText(getResources().getString(R.string.complete));
        }else {
                btnBottom.setVisibility(View.INVISIBLE);
        }
//        callSyncOrderDetail(URL_order_detail);
        callOrderDetail();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (temp.equals("0")){
//                    callSyncCooking(URL_confirm_cooking);
                    callCooking();
                }else if (temp.equals("1")){
//                    callSyncCooking(URL_confirm_cooking);
                    callCooking();
                }else if (temp.equals("2")){
//                    callSyncFinish(URL_confirm_finish);
                    callFinish();
                }else {

                }
            }
        });

    }

//    private void callSyncOrderDetail(final String url) {
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
//                formBuilder.add("code",code);
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
//                                if (!response.isSuccessful()) {
//                                    throw new IOException("Unexpected code " + response);
//                                } else {
//                                    Log.i("Response:",response.toString());
//                                    jsonData = response.body().string();
////                                    try {
////                                        jsonObject = new JSONObject(jsonData);
////                                    } catch (JSONException e) {
////                                        e.printStackTrace();
////                                    }
////                                    OrderID = jsonObject.getString("")
//                                }
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        web_view.loadData(jsonData, "text/html", "UTF-8");
//                                        web_view.setInitialScale(1);
//
//                                        web_view.getSettings().setUseWideViewPort(true);
//                                        web_view.getSettings().setLoadWithOverviewMode(true);
//                                        web_view.getSettings().setBuiltInZoomControls(true);
//                                        web_view.getSettings().setSupportZoom(true);
//                                    }
//                                });
//                            }
//                        });
//                return null;
//            }
//        }.execute();
//    }
    private void callOrderDetail(){
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.Base_URL))
                .build();

        APIService api = retrofit1.create(APIService.class);
        api.getOrderDetail(getResources().getString(R.string.access_token),code).enqueue(new retrofit2.Callback<ResponseBody>() {
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
                            web_view.loadData(jsonData, "text/html", "UTF-8");
                            web_view.setInitialScale(1);

                            web_view.getSettings().setUseWideViewPort(true);
                            web_view.getSettings().setLoadWithOverviewMode(true);
                            web_view.getSettings().setBuiltInZoomControls(true);
                            web_view.getSettings().setSupportZoom(true);
                        }
                    });
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }

//    private void callSyncCooking(final String url) {
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
//                formBuilder.add("code",code);
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
//                                if (!response.isSuccessful()) {
//                                    throw new IOException("Unexpected code " + response);
//                                } else {
//                                    Log.i("Response:",response.toString());
//                                    jsonData = response.body().string();
////                                    try {
////                                        jsonObject = new JSONObject(jsonData);
////                                    } catch (JSONException e) {
////                                        e.printStackTrace();
////                                    }
////                                    OrderID = jsonObject.getString("")
//                                }
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Log.i("Result shop name", jsonData);
//                                        if (jsonData.toLowerCase().contains("true")){
////                                            imgLoad.setVisibility(View.INVISIBLE);
////                                            showStatus("Cooking","success full","success");
//                                            onBackPressed();
//                                        }else {
////
//                                                showStatus("Cooking","false","false");
//                                        }
//                                    }
//                                });
//                            }
//                        });
//                return null;
//            }
//        }.execute();
//    }

    private void callCooking(){
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.Base_URL))
                .build();
        APIService api = retrofit2.create(APIService.class);
        imgLoad.setVisibility(View.VISIBLE);
        api.postCooking(getResources().getString(R.string.access_token),code).enqueue(new retrofit2.Callback<ResponseBody>() {
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
                            Log.i("Result shop name", jsonData);
                            if (jsonData.toLowerCase().contains("true")){
//                                            imgLoad.setVisibility(View.INVISIBLE);
//                                            showStatus("Cooking","success full","success");
                                imgLoad.setVisibility(View.INVISIBLE);
                                onBackPressed();
                            }else {
//
                                imgLoad.setVisibility(View.INVISIBLE);
                                showStatus("Cooking","false","false");
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                imgLoad.setVisibility(View.INVISIBLE);
            }
        });
    }
//    private void callSyncFinish(final String url) {
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
//                formBuilder.add("code",code);
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
//                                if (!response.isSuccessful()) {
//                                    throw new IOException("Unexpected code " + response);
//                                } else {
//                                    Log.i("Response:",response.toString());
//                                    jsonData = response.body().string();
////                                    try {
////                                        jsonObject = new JSONObject(jsonData);
////                                    } catch (JSONException e) {
////                                        e.printStackTrace();
////                                    }
////                                    OrderID = jsonObject.getString("")
//                                }
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Log.i("Result shop name", jsonData);
//                                        if (jsonData.toLowerCase().contains("true")){
////                                            imgLoad.setVisibility(View.INVISIBLE);
////                                            showStatus("Cooking","success full","success");
//                                            onBackPressed();
//                                        }else {
////
//                                            showStatus("Cooking","false","false");
//                                        }
//                                    }
//                                });
//                            }
//                        });
//                return null;
//            }
//        }.execute();
//    }

    private void callFinish(){
        Retrofit retrofit3 = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.Base_URL))
                .build();
        APIService api = retrofit3.create(APIService.class);
        imgLoad.setVisibility(View.INVISIBLE);
        api.postFinish(getResources().getString(R.string.access_token),code).enqueue(new retrofit2.Callback<ResponseBody>() {
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
                            Log.i("Result shop name", jsonData);
                            if (jsonData.toLowerCase().contains("true")){
//                                            imgLoad.setVisibility(View.INVISIBLE);
//                                            showStatus("Cooking","success full","success");
                                imgLoad.setVisibility(View.INVISIBLE);
                                onBackPressed();
                            }else {
//
                                imgLoad.setVisibility(View.INVISIBLE);
                                showStatus("Cooking","false","false");
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                imgLoad.setVisibility(View.INVISIBLE);
            }
        });
    }

    //-----Show Status Dialog-----//
    private void showStatus(String title, String msg, final String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (status.equals("success")){
//                            DeleteOrderListRealm();
//                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
