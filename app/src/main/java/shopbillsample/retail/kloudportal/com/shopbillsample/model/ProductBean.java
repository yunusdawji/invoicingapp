package shopbillsample.retail.kloudportal.com.shopbillsample.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by cloudfuze on 14/07/15.
 */
@DatabaseTable(tableName = "productbean")
public class ProductBean {

    public static final String INVOICE_ID_FIELD_NAME = "invoice_id";

    public ProductBean() {
    }

    public ProductBean(String productname, double productquantitiy, double productPrice, double totalprice) {

        this.productname = productname;
        this.productquantitiy = productquantitiy;
        this.productPrice = productPrice;
        this.totalprice = totalprice;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String productname;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = INVOICE_ID_FIELD_NAME)
    private Invoice invoice;

    public double getProductquantitiy() {
        return productquantitiy;
    }

    public void setProductquantitiy(double productquantitiy) {
        this.productquantitiy = productquantitiy;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    @DatabaseField
    private double productquantitiy;

    @DatabaseField
    private double productPrice;

    @DatabaseField
    private double totalprice;


}
