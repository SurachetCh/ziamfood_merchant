package revenue_express.ziamfood_merchant.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import revenue_express.ziamfood_merchant.activity.MenuActivity;
import revenue_express.ziamfood_merchant.R;
import revenue_express.ziamfood_merchant.dao.ListOrderDAO;
import revenue_express.ziamfood_merchant.model.APIService;

/**
 * Created by NEO on 26/1/2561.
 */

public class OrderListAdapter extends RecyclerView.Adapter{
    private List<ListOrderDAO> mDataset;
    private Context context;
    //Ok http3
    private final OkHttpClient client = new OkHttpClient();
    String jsonData;
    public OrderListAdapter(FragmentActivity activity, List<ListOrderDAO> list_order) {
        context = activity;
        mDataset = list_order;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout card_layout;
        TextView tv_status,tv_order,tv_time,tv_date;
        public ListViewHolder(View itemView) {
            super(itemView);
        }
        public void bindView(final int position){
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_order = (TextView)itemView.findViewById(R.id.tv_order);
            tv_time = (TextView)itemView.findViewById(R.id.tv_time);
            tv_date = (TextView)itemView.findViewById(R.id.tv_date);
            card_layout = (LinearLayout)itemView.findViewById(R.id.card_layout);

            if(mDataset.get(position).getCostatus().equals("0")){
                tv_status.setText("NEW");
                tv_status.setTextColor(Color.parseColor("#D60707"));
                card_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        callSyncConfirmReading("https://dev.ziamthai.com/api.php/v2/"+"merchant/cashier_confirm_reading",mDataset.get(position).getCode(),mDataset.get(position).getId(),mDataset.get(position).getCostatus());
                        callConfirmReading(mDataset.get(position).getCode(),mDataset.get(position).getId(),mDataset.get(position).getCostatus());
                    }
                });
            }else if (mDataset.get(position).getCostatus().equals("1")){
                tv_status.setText("waiting");
                tv_status.setTextColor(Color.parseColor("#FFAA00"));
                card_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MenuActivity.class);
                        intent.putExtra("id", mDataset.get(position).getId());
                        intent.putExtra("temp", mDataset.get(position).getCostatus());
                        intent.putExtra("code", mDataset.get(position).getCode());
                        itemView.getContext().startActivity(intent);
                    }
                });
            }else {
                tv_status.setText("cooking");
                tv_status.setTextColor(Color.parseColor("#66CD00"));
                card_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MenuActivity.class);
                        intent.putExtra("id", mDataset.get(position).getId());
                        intent.putExtra("temp", mDataset.get(position).getCostatus());
                        intent.putExtra("code", mDataset.get(position).getCode());
                        itemView.getContext().startActivity(intent);
                    }
                });
            }
            tv_order.setText("OrderID : "+mDataset.get(position).getCode());
            String CurrentString = mDataset.get(position).getOdate();
            String[] separated = CurrentString.split(" ");

            separated[0] = separated[0].trim();
            separated[1] = separated[1].trim();

            String time = separated[0];
            String date = separated[1];
            tv_time.setText(time);
            tv_date.setText(date);
        }

        @Override
        public void onClick(View view) {

        }
    }

//    private void callSyncConfirmReading(final String url,final String code,final String id,final String temp1) {
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
//                System.out.println("Url:"+url.toString());
//
//                FormBody.Builder formBuilder = new FormBody.Builder();
//                formBuilder.add("access_token", "sd23g125sdf1gc10b3yhik58l4o4");
//                formBuilder.add("code", code);
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
////                                getActivity().runOnUiThread(new Runnable() {
////                                    @Override
////                                    public void run() {
////                                    }
////                                });
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
//                                    Log.i("Result: ",jsonData.toString());
//
//                                    Intent intent = new Intent(context, MenuActivity.class);
//                                        intent.putExtra("id", id);
//                                        intent.putExtra("code", code);
//                                        intent.putExtra("temp", temp1);
//                                    context.startActivity(intent);
//
//
//
//                                }
//
////                                Contextor.getInstance().getContext().runOnUiThread(new Runnable() {
////                                    @Override
////                                    public void run() {
////
////                                        try {
////
////                                            jsonObject = new JSONObject(jsonData);
////
////                                            jsonArray = jsonObject.getJSONArray("data");
////
////                                        } catch (JSONException e) {
////                                            e.getMessage();
////                                        }
//////                                        imgLoad.setVisibility(View.INVISIBLE);
////
////                                    }
////                                });
//                            }
//                        });
//                return null;
//            }
//
//        }.execute();
//    }

    private void callConfirmReading(final String code,final String id,final String temp1){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dev.ziamthai.com/api.php/v2/")
                .build();

        APIService api = retrofit.create(APIService.class);
        api.getConfirmReading("sd23g125sdf1gc10b3yhik58l4o4",code).enqueue(new retrofit2.Callback<ResponseBody>() {
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
                        Intent intent = new Intent(context, MenuActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("code", code);
                        intent.putExtra("temp", temp1);
                        context.startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
