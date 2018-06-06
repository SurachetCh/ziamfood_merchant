package revenue_express.ziamfood_merchant.model;

import android.nfc.Tag;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import revenue_express.ziamfood_merchant.R;

/**
 * Created by NEO on 26/3/2561.
 */

public interface APIService {
    @FormUrlEncoded
    @POST("merchant/list_order")
    Call<ResponseBody>getOrderList(@Field("access_token") String access_token, @Field("shop") String shop, @Field("tab") String tab);

    @FormUrlEncoded
    @POST("merchant/login")
    Call<ResponseBody>getLogin(@Field("access_token") String access_token, @Field("shop") String shop, @Field("login") String login, @Field("pass") String pass, @Field("skey") String skey);

    @FormUrlEncoded
    @POST("merchant/cashier_confirm_reading")
    Call<ResponseBody>getConfirmReading(@Field("access_token") String access_token, @Field("code") String code);

    @FormUrlEncoded
    @POST("shop_order/info_html")
    Call<ResponseBody>getOrderDetail(@Field("access_token") String access_token, @Field("code") String code);

    @FormUrlEncoded
    @POST("merchant/cashier_confirm_cooking")
    Call<ResponseBody>postCooking(@Field("access_token") String access_token, @Field("code") String code);

    @FormUrlEncoded
    @POST("merchant/kitchen_confirm_finish")
    Call<ResponseBody>postFinish(@Field("access_token") String access_token, @Field("code") String code);

}
