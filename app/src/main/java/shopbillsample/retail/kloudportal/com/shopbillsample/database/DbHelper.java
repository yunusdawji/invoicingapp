package shopbillsample.retail.kloudportal.com.shopbillsample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kloudportal.shop.quickbill.R;

import java.sql.SQLException;

import shopbillsample.retail.kloudportal.com.shopbillsample.model.Invoice;
import shopbillsample.retail.kloudportal.com.shopbillsample.model.ProductBean;

/**
 * Created by yunusdawji on 16-01-10.
 */
public class DbHelper  extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "todo";
    private static final int DATABASE_VERSION = 1;


    /**
     * The data access object used to interact with the Sqlite database to do C.R.U.D operations.
     */
    private Dao<Invoice, Long> todoDao;
    private Dao<ProductBean, Long> productBeanDao;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            /**
             * creates the Todo database table
             */
            TableUtils.createTable(connectionSource, ProductBean.class);
            TableUtils.createTable(connectionSource, Invoice.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            /**
             * Recreates the database when onUpgrade is called by the framework
             */
            TableUtils.dropTable(connectionSource, Invoice.class, false);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an instance of the data access object
     * @return
     * @throws SQLException
     */
    public Dao<Invoice, Long> getDao() throws SQLException {
        if(todoDao == null) {
            todoDao = getDao(Invoice.class);
        }
        return todoDao;
    }


    public Dao<ProductBean, Long> getProductBeanDao() throws SQLException {
        if(productBeanDao == null) {
            productBeanDao = getDao(ProductBean.class);
        }
        return productBeanDao;
    }
}