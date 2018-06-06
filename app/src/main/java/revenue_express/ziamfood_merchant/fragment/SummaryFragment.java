package revenue_express.ziamfood_merchant.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
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
import revenue_express.ziamfood_merchant.adapter.SummaryAdapter;
import revenue_express.ziamfood_merchant.dao.ListOrderDAO;
import revenue_express.ziamfood_merchant.model.APIService;
import revenue_express.ziamfood_merchant.model.User;

/**
 * Created by NEO on 24/1/2561.
 */

public class SummaryFragment extends Fragment {
    private View myFragmentView;
    RecyclerView recycler_view;
    private SwipeRefreshLayout swipeRefreshLayout;
    Realm realm;
    //Ok http3
    private final OkHttpClient client = new OkHttpClient();
    JSONObject jsonObject = null;
    JSONArray jsonArray = null;
    String jsonData;
    String URL_summary;
    public static List<ListOrderDAO> list_order;
    String id ,shop_id, code, cust_id, cuser, cmail, ctel, caddr1,caddr2,caddr3,caddr4,sub_total,tax_rate,tax_amount,discount,grand_total,payment,authen_code,pay_total,costatus,odate,pdate,redate,codate,fidate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_summary, container, false);
//        URL_summary = getResources().getString(R.string.Base_URL)+getResources().getString(R.string.URL_list_order);
        Realm.init(getActivity());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);

        realm.beginTransaction();
        RealmResults<User> result = realm.where(User.class).findAll();
        shop_id = result.get(0).getShop_id();
        realm.commitTransaction();
        bindView();
        setView();
        return myFragmentView;
    }
    @Override
    public void onResume() {
        super.onResume();
        setView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    private void bindView(){
        swipeRefreshLayout = (SwipeRefreshLayout) myFragmentView.findViewById(R.id.swipe_refresh_layout);
        recycler_view = (RecyclerView)myFragmentView.findViewById(R.id.recycler_view);
    }
    private void setView(){
        list_order = new ArrayList<ListOrderDAO>();
//        callSyncSummary(URL_summary);
        callSummary();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Get shop detail
                try {
                    callSummary();
                }catch (Exception e){
                    e.getMessage();
                }
            }
        });
    }
    private void setrecycler(){
        SummaryAdapter adapter = new SummaryAdapter(getActivity(),list_order);
        recycler_view.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(layoutManager);

    }

//    private void callSyncSummary(final String url) {
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
////                if(status_reload == "1") {
////                    imgLoad.setVisibility(View.INVISIBLE);
////                }else{
////                    imgLoad.setVisibility(View.VISIBLE);
////                }
////                imgLoad.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            protected Void doInBackground(Void... params) {
//
//                System.out.println("Url Summary:"+url.toString());
//
//                FormBody.Builder formBuilder = new FormBody.Builder();
//                formBuilder.add("access_token", getResources().getString(R.string.access_token));
//                formBuilder.add("shop", getResources().getString(R.string.id_shop));
//                formBuilder.add("tab", "summary");
//                MediaType.parse("application/json; charset=utf-8");
//
//                RequestBody formBody = formBuilder.build();
//                Request request = new Request.Builder()
//                        .url(url)
//                        .post(formBody)
//                        .build();
//
//                client.newCall(request)
//                        .enqueue(new Callback() {
//
//                            @Override
//                            public void onFailure(final Call call, IOException e) {
//                                // Error
//
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onResponse(Call call, Response response) throws IOException {
//
//                                if (!response.isSuccessful()) {
//                                    throw new IOException("Unexpected code " + response);
//                                } else {
//                                    Log.i("Response:",response.toString());
//                                    jsonData = response.body().string();
//                                    Log.i("Summary Result: ",jsonData.toString());
//                                }
//
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        try {
//
//                                            jsonObject = new JSONObject(jsonData);
//
//                                            jsonArray = jsonObject.getJSONArray("data");
//                                            list_order.clear();
//
//                                            for (int i = 0; i < jsonArray.length(); i++) {
//                                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                                                //Get data review
//                                                id = jsonObject2.getString("id");
//                                                shop_id = jsonObject2.getString("shop_id");
//                                                code = jsonObject2.getString("code");
//                                                cust_id = jsonObject2.getString("cust_id");
//                                                cuser = jsonObject2.getString("cuser");
//                                                cmail = jsonObject2.getString("cmail");
//                                                ctel = jsonObject2.getString("ctel");
//                                                caddr1 = jsonObject2.getString("caddr1");
//                                                caddr2 = jsonObject2.getString("caddr2");
//                                                caddr3 = jsonObject2.getString("caddr3");
//                                                caddr4 = jsonObject2.getString("caddr4");
//                                                sub_total = jsonObject2.getString("sub_total");
//                                                tax_rate = jsonObject2.getString("tax_rate");
//                                                tax_amount = jsonObject2.getString("tax_amount");
//                                                discount = jsonObject2.getString("discount");
//                                                grand_total = jsonObject2.getString("grand_total");
//
//                                                payment = jsonObject2.getString("payment");
//                                                authen_code = jsonObject2.getString("authen_code");
//                                                pay_total = jsonObject2.getString("pay_total");
//                                                costatus = jsonObject2.getString("costatus");
//                                                odate = jsonObject2.getString("odate");
//                                                pdate = jsonObject2.getString("pdate");
//                                                redate = jsonObject2.getString("redate");
//                                                codate = jsonObject2.getString("codate");
//                                                fidate = jsonObject2.getString("fidate");
//
//                                                list_order.add(new ListOrderDAO(id ,shop_id, code, cust_id, cuser, cmail, ctel, caddr1,caddr2,caddr3,caddr4,sub_total,tax_rate,tax_amount,discount,grand_total,payment,authen_code,pay_total,costatus,odate,pdate,redate,codate,fidate));
//                                            }
//                                            setrecycler();
//                                        } catch (JSONException e) {
//                                            e.getMessage();
//                                        }
////                                        imgLoad.setVisibility(View.INVISIBLE);
//
//                                    }
//                                });
//                            }
//                        });
//                return null;
//            }
//
//        }.execute();
//    }

    private void callSummary() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.Base_URL))
                .build();

        APIService api = retrofit.create(APIService.class);
        api.getOrderList(getResources().getString(R.string.access_token),shop_id,"summary").enqueue(new retrofit2.Callback<ResponseBody>() {
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                jsonObject = new JSONObject(jsonData);

                                jsonArray = jsonObject.getJSONArray("data");
                                list_order.clear();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    //Get data review
                                    id = jsonObject2.getString("id");
                                    shop_id = jsonObject2.getString("shop_id");
                                    code = jsonObject2.getString("code");
                                    cust_id = jsonObject2.getString("cust_id");
                                    cuser = jsonObject2.getString("cuser");
                                    cmail = jsonObject2.getString("cmail");
                                    ctel = jsonObject2.getString("ctel");
                                    caddr1 = jsonObject2.getString("caddr1");
                                    caddr2 = jsonObject2.getString("caddr2");
                                    caddr3 = jsonObject2.getString("caddr3");
                                    caddr4 = jsonObject2.getString("caddr4");
                                    sub_total = jsonObject2.getString("sub_total");
                                    tax_rate = jsonObject2.getString("tax_rate");
                                    tax_amount = jsonObject2.getString("tax_amount");
                                    discount = jsonObject2.getString("discount");
                                    grand_total = jsonObject2.getString("grand_total");

                                    payment = jsonObject2.getString("payment");
                                    authen_code = jsonObject2.getString("authen_code");
                                    pay_total = jsonObject2.getString("pay_total");
                                    costatus = jsonObject2.getString("costatus");
                                    odate = jsonObject2.getString("odate");
                                    pdate = jsonObject2.getString("pdate");
                                    redate = jsonObject2.getString("redate");
                                    codate = jsonObject2.getString("codate");
                                    fidate = jsonObject2.getString("fidate");

                                    list_order.add(new ListOrderDAO(id ,shop_id, code, cust_id, cuser, cmail, ctel, caddr1,caddr2,caddr3,caddr4,sub_total,tax_rate,tax_amount,discount,grand_total,payment,authen_code,pay_total,costatus,odate,pdate,redate,codate,fidate));
                                }
                                setrecycler();
                            } catch (JSONException e) {
                                e.getMessage();
                            }
//                                        imgLoad.setVisibility(View.INVISIBLE);
                            swipeRefreshLayout.setRefreshing(false);

                        }
                    });
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
