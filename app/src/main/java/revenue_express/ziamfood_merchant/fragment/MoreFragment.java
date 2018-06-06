package revenue_express.ziamfood_merchant.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import revenue_express.ziamfood_merchant.activity.LoginActivity;
import revenue_express.ziamfood_merchant.R;
import revenue_express.ziamfood_merchant.manager.Contextor;
import revenue_express.ziamfood_merchant.model.User;

/**
 * Created by NEO on 24/1/2561.
 */

public class MoreFragment extends Fragment {
    private View myFragmentView;
    TextView tv_logout,tv_user;
    Realm realm;
    ImageView profile_image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_more, container, false);
        Realm.init(Contextor.getInstance().getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
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
        tv_logout = (TextView)myFragmentView.findViewById(R.id.tv_logout);
        tv_user = (TextView)myFragmentView.findViewById(R.id.tv_user);
        profile_image = (ImageView) myFragmentView.findViewById(R.id.profile_image);
    }
    private void setView(){
        realm.beginTransaction();
        RealmResults<User> result = realm.where(User.class).findAll();
        realm.commitTransaction();

        Glide.with(this)
                .load(result.get(0).getShop_logo())
                .into(profile_image);
        tv_user.setText(result.get(0).getUser_name());

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                realm.beginTransaction();
                RealmResults<User> result = realm.where(User.class).findAll();
                realm.commitTransaction();
                if (result.size()==0){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
//                intent.putExtra("tamp", "1");
                    getActivity().startActivity(intent);
                }else {
                    showMessageStatus("Logout" , "Are you sure?");

                }

            }
        });
    }

    private  void DeleteUserRealm(){
        realm.beginTransaction();
        RealmResults<User> result1 = realm.where(User.class).findAll();
        result1.deleteAllFromRealm();
        realm.commitTransaction();
    }

    //-----Show Message Status-----//
    private void showMessageStatus(String title , String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(msg);
        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteUserRealm();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
//                intent.putExtra("tamp", "1");
                        getActivity().startActivity(intent);
                        return;
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
