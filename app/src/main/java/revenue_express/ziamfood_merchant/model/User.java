package revenue_express.ziamfood_merchant.model;

import io.realm.RealmObject;

/**
 * Created by NEO on 16/2/2561.
 */

public class User extends RealmObject {
    private String device_id,user_id,user_name,shop_id,shop_name,shop_address1,shop_address2,shop_logo;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_address1() {
        return shop_address1;
    }

    public void setShop_address1(String shop_address1) {
        this.shop_address1 = shop_address1;
    }

    public String getShop_address2() {
        return shop_address2;
    }

    public void setShop_address2(String shop_address2) {
        this.shop_address2 = shop_address2;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }
}
