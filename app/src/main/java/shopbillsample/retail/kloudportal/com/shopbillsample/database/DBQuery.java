package shopbillsample.retail.kloudportal.com.shopbillsample.database;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import shopbillsample.retail.kloudportal.com.shopbillsample.model.Invoice;
import shopbillsample.retail.kloudportal.com.shopbillsample.model.ProductBean;

/**
 * Created by yunusdawji on 16-01-10.
 */
public class DBQuery {

    private final static String TAG = DBQuery.class.getSimpleName();

    public static void createItem(Dao<Invoice, Long> dao, Dao<ProductBean, Long> daoProductBean, ProductBean item, Invoice invoice) throws SQLException {
        try {
            Collection<ProductBean> temp = new ArrayList<ProductBean>();
            item.setInvoice(invoice);
            daoProductBean.create(item);

            temp.add(new ProductBean());

            //Invoice tempinvoice = new Invoice("A", "A", "A", "A", "A", new Date().getTime());
            //dao.create(tempinvoice);

            Invoice todos = dao.queryForId(invoice.getId());
            Long test = todos.getId();
        }catch(SQLException e){
            throw e;
        }
    }

    public static void createInvoice(Dao<Invoice, Long> dao, Dao<ProductBean, Long> daoProductBean, ProductBean item, Invoice invoice) throws SQLException {
        try {
            dao.create(invoice);
        }catch(SQLException e){
            throw e;
        }
    }

}
