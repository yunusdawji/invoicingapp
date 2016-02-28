package shopbillsample.retail.kloudportal.com.shopbillsample.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;
import java.util.List;

/**
 * Created by yunusdawji on 16-01-10.
 */
@DatabaseTable(tableName = "Invoice")
public class Invoice {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String shopname;

    @DatabaseField
    private String website;

    @DatabaseField
    private String phonenumber;

    @DatabaseField
    private String address;

    @DatabaseField
    private String description;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @DatabaseField
    private String city;

    @DatabaseField
    private String state;

    @DatabaseField
    private String country;


    @DatabaseField
    private long dateCreated;

    @ForeignCollectionField
    private Collection<ProductBean> items;

    public Invoice(String shopname, String website, String phonenumber, String address, String description, String city, String state, String country, long dateCreated) {
        this.shopname = shopname;
        this.website = website;
        this.phonenumber = phonenumber;
        this.address = address;
        this.description = description;
        this.city = city;
        this.state = state;
        this.country = country;
        this.dateCreated = dateCreated;
    }

    public Invoice() {
    }


    public Long getId() {
        return id;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Collection<ProductBean> getItems() {
        return items;
    }

    public void setItems(Collection<ProductBean> items) {
        this.items = items;
    }

}
