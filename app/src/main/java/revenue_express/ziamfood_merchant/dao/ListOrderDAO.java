package revenue_express.ziamfood_merchant.dao;

/**
 * Created by NEO on 16/2/2561.
 */

public class ListOrderDAO {
    String id ,shop_id, code, cust_id, cuser, cmail, ctel, caddr1,caddr2,caddr3,caddr4,sub_total,tax_rate,tax_amount,discount,grand_total,payment,authen_code,pay_total,costatus,odate,pdate,redate,codate,fidate;

    public ListOrderDAO(String id ,String shop_id, String code, String cust_id, String cuser, String cmail, String ctel, String caddr1,String caddr2,String caddr3,String caddr4,String sub_total,String tax_rate,String tax_amount,String discount,String grand_total,String payment,String authen_code,String pay_total,String costatus,String odate,String pdate,String redate,String codate,String fidate) {
        this.id = id;
        this.shop_id = shop_id;
        this.code = code;
        this.cust_id = cust_id;
        this.cuser = cuser;
        this.cmail = cmail;
        this.ctel = ctel;
        this.caddr1 = caddr1;
        this.caddr2 = caddr2;
        this.caddr3 = caddr3;
        this.caddr4 = caddr4;
        this.sub_total = sub_total;
        this.tax_rate = tax_rate;
        this.tax_amount = tax_amount;
        this.discount = discount;
        this.grand_total = grand_total;
        this.payment = payment;
        this.authen_code = authen_code;
        this.pay_total = pay_total;
        this.costatus = costatus;
        this.odate = odate;
        this.pdate = pdate;
        this.redate = redate;
        this.codate = codate;
        this.fidate = fidate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        this.cuser = cuser;
    }

    public String getCmail() {
        return cmail;
    }

    public void setCmail(String cmail) {
        this.cmail = cmail;
    }

    public String getCtel() {
        return ctel;
    }

    public void setCtel(String ctel) {
        this.ctel = ctel;
    }

    public String getCaddr1() {
        return caddr1;
    }

    public void setCaddr1(String caddr1) {
        this.caddr1 = caddr1;
    }

    public String getCaddr2() {
        return caddr2;
    }

    public void setCaddr2(String caddr2) {
        this.caddr2 = caddr2;
    }

    public String getCaddr3() {
        return caddr3;
    }

    public void setCaddr3(String caddr3) {
        this.caddr3 = caddr3;
    }

    public String getCaddr4() {
        return caddr4;
    }

    public void setCaddr4(String caddr4) {
        this.caddr4 = caddr4;
    }

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    public String getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(String tax_rate) {
        this.tax_rate = tax_rate;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(String tax_amount) {
        this.tax_amount = tax_amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getAuthen_code() {
        return authen_code;
    }

    public void setAuthen_code(String authen_code) {
        this.authen_code = authen_code;
    }

    public String getPay_total() {
        return pay_total;
    }

    public void setPay_total(String pay_total) {
        this.pay_total = pay_total;
    }

    public String getCostatus() {
        return costatus;
    }

    public void setCostatus(String costatus) {
        this.costatus = costatus;
    }

    public String getOdate() {
        return odate;
    }

    public void setOdate(String odate) {
        this.odate = odate;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getRedate() {
        return redate;
    }

    public void setRedate(String redate) {
        this.redate = redate;
    }

    public String getCodate() {
        return codate;
    }

    public void setCodate(String codate) {
        this.codate = codate;
    }

    public String getFidate() {
        return fidate;
    }

    public void setFidate(String fidate) {
        this.fidate = fidate;
    }
}
