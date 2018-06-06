package revenue_express.ziamfood_merchant.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import revenue_express.ziamfood_merchant.activity.MenuActivity;
import revenue_express.ziamfood_merchant.R;
import revenue_express.ziamfood_merchant.dao.ListOrderDAO;

/**
 * Created by NEO on 26/1/2561.
 */

public class SummaryAdapter extends RecyclerView.Adapter{
    private List<ListOrderDAO> mDataset;
    private Context context;
    //Ok http3
    private final OkHttpClient client = new OkHttpClient();
    JSONObject jsonObject = null;
    JSONArray jsonArray = null;
    String jsonData;
    public SummaryAdapter(FragmentActivity activity, List<ListOrderDAO> list_order) {
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

//            tv_status.setVisibility(View.INVISIBLE);
            tv_order.setText("OrderID : "+mDataset.get(position).getCode());
            String CurrentString = mDataset.get(position).getOdate();
            String[] separated = CurrentString.split(" ");

            separated[0] = separated[0].trim();
            separated[1] = separated[1].trim();

            String time = separated[0];
            String date = separated[1];
            tv_time.setText(time);
            tv_date.setText(date);
            tv_status.setText("Done");
            tv_status.setTextColor(Color.parseColor("#0066ab"));

            card_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MenuActivity.class);
                    intent.putExtra("id", mDataset.get(position).getId());
                    intent.putExtra("code", mDataset.get(position).getCode());
                    intent.putExtra("temp", "3");
                    itemView.getContext().startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View view) {
        }
    }
}
