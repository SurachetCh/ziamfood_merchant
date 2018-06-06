package revenue_express.ziamfood_merchant.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import revenue_express.ziamfood_merchant.R;

public class SummaryActivity extends AppCompatActivity {

    LinearLayout btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        bindView();
        setView();
    }
    private void bindView(){
        btnBack = (LinearLayout) findViewById(R.id.btnBack);
    }
    private void setView(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
