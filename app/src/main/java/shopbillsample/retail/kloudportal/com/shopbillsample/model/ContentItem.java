package shopbillsample.retail.kloudportal.com.shopbillsample.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by yunusdawji on 16-01-10.
 */
public class ContentItem {

    @DatabaseField(generatedId = true)
    protected long id;

    @DatabaseField(foreign = true)
    protected ProductBean item;

    public void setItem(ProductBean domainItem)
    {
        this.item = domainItem;
    }

}