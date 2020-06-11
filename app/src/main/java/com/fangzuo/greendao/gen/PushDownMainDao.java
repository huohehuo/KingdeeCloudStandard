package com.fangzuo.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.fangzuo.assist.cloud.Dao.PushDownMain;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PUSH_DOWN_MAIN".
*/
public class PushDownMainDao extends AbstractDao<PushDownMain, Long> {

    public static final String TABLENAME = "PUSH_DOWN_MAIN";

    /**
     * Properties of entity PushDownMain.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property FID = new Property(1, String.class, "FID", false, "FID");
        public final static Property FBillNo = new Property(2, String.class, "FBillNo", false, "FBILL_NO");
        public final static Property FDate = new Property(3, String.class, "FDate", false, "FDATE");
        public final static Property FSupplyID = new Property(4, String.class, "FSupplyID", false, "FSUPPLY_ID");
        public final static Property FSupply = new Property(5, String.class, "FSupply", false, "FSUPPLY");
        public final static Property FSaleOrgID = new Property(6, String.class, "FSaleOrgID", false, "FSALE_ORG_ID");
        public final static Property FStoreOrgID = new Property(7, String.class, "FStoreOrgID", false, "FSTORE_ORG_ID");
        public final static Property FSettleOrgId = new Property(8, String.class, "FSettleOrgId", false, "FSETTLE_ORG_ID");
        public final static Property FSaleManID = new Property(9, String.class, "FSaleManID", false, "FSALE_MAN_ID");
        public final static Property FSaleDeptID = new Property(10, String.class, "FSaleDeptID", false, "FSALE_DEPT_ID");
        public final static Property FNot = new Property(11, String.class, "FNot", false, "FNOT");
        public final static Property Tag = new Property(12, int.class, "tag", false, "TAG");
        public final static Property FBillTypeName = new Property(13, String.class, "FBillTypeName", false, "FBILL_TYPE_NAME");
        public final static Property FAccountID = new Property(14, String.class, "FAccountID", false, "FACCOUNT_ID");
        public final static Property FDept = new Property(15, String.class, "FDept", false, "FDEPT");
        public final static Property FDeptID = new Property(16, String.class, "FDeptID", false, "FDEPT_ID");
        public final static Property FClient = new Property(17, String.class, "FClient", false, "FCLIENT");
        public final static Property FClientID = new Property(18, String.class, "FClientID", false, "FCLIENT_ID");
        public final static Property FAppOrgID = new Property(19, String.class, "FAppOrgID", false, "FAPP_ORG_ID");
        public final static Property FHuozhuInType = new Property(20, String.class, "FHuozhuInType", false, "FHUOZHU_IN_TYPE");
        public final static Property FHuozhuOutType = new Property(21, String.class, "FHuozhuOutType", false, "FHUOZHU_OUT_TYPE");
        public final static Property FDbType = new Property(22, String.class, "FDbType", false, "FDB_TYPE");
        public final static Property FOrderNo = new Property(23, String.class, "FOrderNo", false, "FORDER_NO");
        public final static Property FWordShopID = new Property(24, String.class, "FWordShopID", false, "FWORD_SHOP_ID");
        public final static Property FWordShop = new Property(25, String.class, "FWordShop", false, "FWORD_SHOP");
        public final static Property FStoreManID = new Property(26, String.class, "FStoreManID", false, "FSTORE_MAN_ID");
        public final static Property FStockerGroupID = new Property(27, String.class, "FStockerGroupID", false, "FSTOCKER_GROUP_ID");
        public final static Property FCreateOrgID = new Property(28, String.class, "FCreateOrgID", false, "FCREATE_ORG_ID");
        public final static Property FHuoZhuID = new Property(29, String.class, "FHuoZhuID", false, "FHUO_ZHU_ID");
        public final static Property FWlCompany = new Property(30, String.class, "FWlCompany", false, "FWL_COMPANY");
        public final static Property FCarBoxNo = new Property(31, String.class, "FCarBoxNo", false, "FCAR_BOX_NO");
        public final static Property FPassNo = new Property(32, String.class, "FPassNo", false, "FPASS_NO");
        public final static Property FFreight = new Property(33, String.class, "FFreight", false, "FFREIGHT");
        public final static Property FYaoNo = new Property(34, String.class, "FYaoNo", false, "FYAO_NO");
        public final static Property FFieldMan = new Property(35, String.class, "FFieldMan", false, "FFIELD_MAN");
        public final static Property FStr1 = new Property(36, String.class, "FStr1", false, "FSTR1");
        public final static Property FStr2 = new Property(37, String.class, "FStr2", false, "FSTR2");
        public final static Property FStr3 = new Property(38, String.class, "FStr3", false, "FSTR3");
        public final static Property FStr4 = new Property(39, String.class, "FStr4", false, "FSTR4");
        public final static Property FStr5 = new Property(40, String.class, "FStr5", false, "FSTR5");
        public final static Property FPayType = new Property(41, String.class, "FPayType", false, "FPAY_TYPE");
        public final static Property FAcPrd = new Property(42, String.class, "FAcPrd", false, "FAC_PRD");
    }


    public PushDownMainDao(DaoConfig config) {
        super(config);
    }
    
    public PushDownMainDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PUSH_DOWN_MAIN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"FID\" TEXT," + // 1: FID
                "\"FBILL_NO\" TEXT," + // 2: FBillNo
                "\"FDATE\" TEXT," + // 3: FDate
                "\"FSUPPLY_ID\" TEXT," + // 4: FSupplyID
                "\"FSUPPLY\" TEXT," + // 5: FSupply
                "\"FSALE_ORG_ID\" TEXT," + // 6: FSaleOrgID
                "\"FSTORE_ORG_ID\" TEXT," + // 7: FStoreOrgID
                "\"FSETTLE_ORG_ID\" TEXT," + // 8: FSettleOrgId
                "\"FSALE_MAN_ID\" TEXT," + // 9: FSaleManID
                "\"FSALE_DEPT_ID\" TEXT," + // 10: FSaleDeptID
                "\"FNOT\" TEXT," + // 11: FNot
                "\"TAG\" INTEGER NOT NULL ," + // 12: tag
                "\"FBILL_TYPE_NAME\" TEXT," + // 13: FBillTypeName
                "\"FACCOUNT_ID\" TEXT," + // 14: FAccountID
                "\"FDEPT\" TEXT," + // 15: FDept
                "\"FDEPT_ID\" TEXT," + // 16: FDeptID
                "\"FCLIENT\" TEXT," + // 17: FClient
                "\"FCLIENT_ID\" TEXT," + // 18: FClientID
                "\"FAPP_ORG_ID\" TEXT," + // 19: FAppOrgID
                "\"FHUOZHU_IN_TYPE\" TEXT," + // 20: FHuozhuInType
                "\"FHUOZHU_OUT_TYPE\" TEXT," + // 21: FHuozhuOutType
                "\"FDB_TYPE\" TEXT," + // 22: FDbType
                "\"FORDER_NO\" TEXT," + // 23: FOrderNo
                "\"FWORD_SHOP_ID\" TEXT," + // 24: FWordShopID
                "\"FWORD_SHOP\" TEXT," + // 25: FWordShop
                "\"FSTORE_MAN_ID\" TEXT," + // 26: FStoreManID
                "\"FSTOCKER_GROUP_ID\" TEXT," + // 27: FStockerGroupID
                "\"FCREATE_ORG_ID\" TEXT," + // 28: FCreateOrgID
                "\"FHUO_ZHU_ID\" TEXT," + // 29: FHuoZhuID
                "\"FWL_COMPANY\" TEXT," + // 30: FWlCompany
                "\"FCAR_BOX_NO\" TEXT," + // 31: FCarBoxNo
                "\"FPASS_NO\" TEXT," + // 32: FPassNo
                "\"FFREIGHT\" TEXT," + // 33: FFreight
                "\"FYAO_NO\" TEXT," + // 34: FYaoNo
                "\"FFIELD_MAN\" TEXT," + // 35: FFieldMan
                "\"FSTR1\" TEXT," + // 36: FStr1
                "\"FSTR2\" TEXT," + // 37: FStr2
                "\"FSTR3\" TEXT," + // 38: FStr3
                "\"FSTR4\" TEXT," + // 39: FStr4
                "\"FSTR5\" TEXT," + // 40: FStr5
                "\"FPAY_TYPE\" TEXT," + // 41: FPayType
                "\"FAC_PRD\" TEXT);"); // 42: FAcPrd
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PUSH_DOWN_MAIN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PushDownMain entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String FID = entity.getFID();
        if (FID != null) {
            stmt.bindString(2, FID);
        }
 
        String FBillNo = entity.getFBillNo();
        if (FBillNo != null) {
            stmt.bindString(3, FBillNo);
        }
 
        String FDate = entity.getFDate();
        if (FDate != null) {
            stmt.bindString(4, FDate);
        }
 
        String FSupplyID = entity.getFSupplyID();
        if (FSupplyID != null) {
            stmt.bindString(5, FSupplyID);
        }
 
        String FSupply = entity.getFSupply();
        if (FSupply != null) {
            stmt.bindString(6, FSupply);
        }
 
        String FSaleOrgID = entity.getFSaleOrgID();
        if (FSaleOrgID != null) {
            stmt.bindString(7, FSaleOrgID);
        }
 
        String FStoreOrgID = entity.getFStoreOrgID();
        if (FStoreOrgID != null) {
            stmt.bindString(8, FStoreOrgID);
        }
 
        String FSettleOrgId = entity.getFSettleOrgId();
        if (FSettleOrgId != null) {
            stmt.bindString(9, FSettleOrgId);
        }
 
        String FSaleManID = entity.getFSaleManID();
        if (FSaleManID != null) {
            stmt.bindString(10, FSaleManID);
        }
 
        String FSaleDeptID = entity.getFSaleDeptID();
        if (FSaleDeptID != null) {
            stmt.bindString(11, FSaleDeptID);
        }
 
        String FNot = entity.getFNot();
        if (FNot != null) {
            stmt.bindString(12, FNot);
        }
        stmt.bindLong(13, entity.getTag());
 
        String FBillTypeName = entity.getFBillTypeName();
        if (FBillTypeName != null) {
            stmt.bindString(14, FBillTypeName);
        }
 
        String FAccountID = entity.getFAccountID();
        if (FAccountID != null) {
            stmt.bindString(15, FAccountID);
        }
 
        String FDept = entity.getFDept();
        if (FDept != null) {
            stmt.bindString(16, FDept);
        }
 
        String FDeptID = entity.getFDeptID();
        if (FDeptID != null) {
            stmt.bindString(17, FDeptID);
        }
 
        String FClient = entity.getFClient();
        if (FClient != null) {
            stmt.bindString(18, FClient);
        }
 
        String FClientID = entity.getFClientID();
        if (FClientID != null) {
            stmt.bindString(19, FClientID);
        }
 
        String FAppOrgID = entity.getFAppOrgID();
        if (FAppOrgID != null) {
            stmt.bindString(20, FAppOrgID);
        }
 
        String FHuozhuInType = entity.getFHuozhuInType();
        if (FHuozhuInType != null) {
            stmt.bindString(21, FHuozhuInType);
        }
 
        String FHuozhuOutType = entity.getFHuozhuOutType();
        if (FHuozhuOutType != null) {
            stmt.bindString(22, FHuozhuOutType);
        }
 
        String FDbType = entity.getFDbType();
        if (FDbType != null) {
            stmt.bindString(23, FDbType);
        }
 
        String FOrderNo = entity.getFOrderNo();
        if (FOrderNo != null) {
            stmt.bindString(24, FOrderNo);
        }
 
        String FWordShopID = entity.getFWordShopID();
        if (FWordShopID != null) {
            stmt.bindString(25, FWordShopID);
        }
 
        String FWordShop = entity.getFWordShop();
        if (FWordShop != null) {
            stmt.bindString(26, FWordShop);
        }
 
        String FStoreManID = entity.getFStoreManID();
        if (FStoreManID != null) {
            stmt.bindString(27, FStoreManID);
        }
 
        String FStockerGroupID = entity.getFStockerGroupID();
        if (FStockerGroupID != null) {
            stmt.bindString(28, FStockerGroupID);
        }
 
        String FCreateOrgID = entity.getFCreateOrgID();
        if (FCreateOrgID != null) {
            stmt.bindString(29, FCreateOrgID);
        }
 
        String FHuoZhuID = entity.getFHuoZhuID();
        if (FHuoZhuID != null) {
            stmt.bindString(30, FHuoZhuID);
        }
 
        String FWlCompany = entity.getFWlCompany();
        if (FWlCompany != null) {
            stmt.bindString(31, FWlCompany);
        }
 
        String FCarBoxNo = entity.getFCarBoxNo();
        if (FCarBoxNo != null) {
            stmt.bindString(32, FCarBoxNo);
        }
 
        String FPassNo = entity.getFPassNo();
        if (FPassNo != null) {
            stmt.bindString(33, FPassNo);
        }
 
        String FFreight = entity.getFFreight();
        if (FFreight != null) {
            stmt.bindString(34, FFreight);
        }
 
        String FYaoNo = entity.getFYaoNo();
        if (FYaoNo != null) {
            stmt.bindString(35, FYaoNo);
        }
 
        String FFieldMan = entity.getFFieldMan();
        if (FFieldMan != null) {
            stmt.bindString(36, FFieldMan);
        }
 
        String FStr1 = entity.getFStr1();
        if (FStr1 != null) {
            stmt.bindString(37, FStr1);
        }
 
        String FStr2 = entity.getFStr2();
        if (FStr2 != null) {
            stmt.bindString(38, FStr2);
        }
 
        String FStr3 = entity.getFStr3();
        if (FStr3 != null) {
            stmt.bindString(39, FStr3);
        }
 
        String FStr4 = entity.getFStr4();
        if (FStr4 != null) {
            stmt.bindString(40, FStr4);
        }
 
        String FStr5 = entity.getFStr5();
        if (FStr5 != null) {
            stmt.bindString(41, FStr5);
        }
 
        String FPayType = entity.getFPayType();
        if (FPayType != null) {
            stmt.bindString(42, FPayType);
        }
 
        String FAcPrd = entity.getFAcPrd();
        if (FAcPrd != null) {
            stmt.bindString(43, FAcPrd);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PushDownMain entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String FID = entity.getFID();
        if (FID != null) {
            stmt.bindString(2, FID);
        }
 
        String FBillNo = entity.getFBillNo();
        if (FBillNo != null) {
            stmt.bindString(3, FBillNo);
        }
 
        String FDate = entity.getFDate();
        if (FDate != null) {
            stmt.bindString(4, FDate);
        }
 
        String FSupplyID = entity.getFSupplyID();
        if (FSupplyID != null) {
            stmt.bindString(5, FSupplyID);
        }
 
        String FSupply = entity.getFSupply();
        if (FSupply != null) {
            stmt.bindString(6, FSupply);
        }
 
        String FSaleOrgID = entity.getFSaleOrgID();
        if (FSaleOrgID != null) {
            stmt.bindString(7, FSaleOrgID);
        }
 
        String FStoreOrgID = entity.getFStoreOrgID();
        if (FStoreOrgID != null) {
            stmt.bindString(8, FStoreOrgID);
        }
 
        String FSettleOrgId = entity.getFSettleOrgId();
        if (FSettleOrgId != null) {
            stmt.bindString(9, FSettleOrgId);
        }
 
        String FSaleManID = entity.getFSaleManID();
        if (FSaleManID != null) {
            stmt.bindString(10, FSaleManID);
        }
 
        String FSaleDeptID = entity.getFSaleDeptID();
        if (FSaleDeptID != null) {
            stmt.bindString(11, FSaleDeptID);
        }
 
        String FNot = entity.getFNot();
        if (FNot != null) {
            stmt.bindString(12, FNot);
        }
        stmt.bindLong(13, entity.getTag());
 
        String FBillTypeName = entity.getFBillTypeName();
        if (FBillTypeName != null) {
            stmt.bindString(14, FBillTypeName);
        }
 
        String FAccountID = entity.getFAccountID();
        if (FAccountID != null) {
            stmt.bindString(15, FAccountID);
        }
 
        String FDept = entity.getFDept();
        if (FDept != null) {
            stmt.bindString(16, FDept);
        }
 
        String FDeptID = entity.getFDeptID();
        if (FDeptID != null) {
            stmt.bindString(17, FDeptID);
        }
 
        String FClient = entity.getFClient();
        if (FClient != null) {
            stmt.bindString(18, FClient);
        }
 
        String FClientID = entity.getFClientID();
        if (FClientID != null) {
            stmt.bindString(19, FClientID);
        }
 
        String FAppOrgID = entity.getFAppOrgID();
        if (FAppOrgID != null) {
            stmt.bindString(20, FAppOrgID);
        }
 
        String FHuozhuInType = entity.getFHuozhuInType();
        if (FHuozhuInType != null) {
            stmt.bindString(21, FHuozhuInType);
        }
 
        String FHuozhuOutType = entity.getFHuozhuOutType();
        if (FHuozhuOutType != null) {
            stmt.bindString(22, FHuozhuOutType);
        }
 
        String FDbType = entity.getFDbType();
        if (FDbType != null) {
            stmt.bindString(23, FDbType);
        }
 
        String FOrderNo = entity.getFOrderNo();
        if (FOrderNo != null) {
            stmt.bindString(24, FOrderNo);
        }
 
        String FWordShopID = entity.getFWordShopID();
        if (FWordShopID != null) {
            stmt.bindString(25, FWordShopID);
        }
 
        String FWordShop = entity.getFWordShop();
        if (FWordShop != null) {
            stmt.bindString(26, FWordShop);
        }
 
        String FStoreManID = entity.getFStoreManID();
        if (FStoreManID != null) {
            stmt.bindString(27, FStoreManID);
        }
 
        String FStockerGroupID = entity.getFStockerGroupID();
        if (FStockerGroupID != null) {
            stmt.bindString(28, FStockerGroupID);
        }
 
        String FCreateOrgID = entity.getFCreateOrgID();
        if (FCreateOrgID != null) {
            stmt.bindString(29, FCreateOrgID);
        }
 
        String FHuoZhuID = entity.getFHuoZhuID();
        if (FHuoZhuID != null) {
            stmt.bindString(30, FHuoZhuID);
        }
 
        String FWlCompany = entity.getFWlCompany();
        if (FWlCompany != null) {
            stmt.bindString(31, FWlCompany);
        }
 
        String FCarBoxNo = entity.getFCarBoxNo();
        if (FCarBoxNo != null) {
            stmt.bindString(32, FCarBoxNo);
        }
 
        String FPassNo = entity.getFPassNo();
        if (FPassNo != null) {
            stmt.bindString(33, FPassNo);
        }
 
        String FFreight = entity.getFFreight();
        if (FFreight != null) {
            stmt.bindString(34, FFreight);
        }
 
        String FYaoNo = entity.getFYaoNo();
        if (FYaoNo != null) {
            stmt.bindString(35, FYaoNo);
        }
 
        String FFieldMan = entity.getFFieldMan();
        if (FFieldMan != null) {
            stmt.bindString(36, FFieldMan);
        }
 
        String FStr1 = entity.getFStr1();
        if (FStr1 != null) {
            stmt.bindString(37, FStr1);
        }
 
        String FStr2 = entity.getFStr2();
        if (FStr2 != null) {
            stmt.bindString(38, FStr2);
        }
 
        String FStr3 = entity.getFStr3();
        if (FStr3 != null) {
            stmt.bindString(39, FStr3);
        }
 
        String FStr4 = entity.getFStr4();
        if (FStr4 != null) {
            stmt.bindString(40, FStr4);
        }
 
        String FStr5 = entity.getFStr5();
        if (FStr5 != null) {
            stmt.bindString(41, FStr5);
        }
 
        String FPayType = entity.getFPayType();
        if (FPayType != null) {
            stmt.bindString(42, FPayType);
        }
 
        String FAcPrd = entity.getFAcPrd();
        if (FAcPrd != null) {
            stmt.bindString(43, FAcPrd);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public PushDownMain readEntity(Cursor cursor, int offset) {
        PushDownMain entity = new PushDownMain( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // FID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // FBillNo
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // FDate
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // FSupplyID
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // FSupply
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // FSaleOrgID
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // FStoreOrgID
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // FSettleOrgId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // FSaleManID
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // FSaleDeptID
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // FNot
            cursor.getInt(offset + 12), // tag
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // FBillTypeName
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // FAccountID
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // FDept
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // FDeptID
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // FClient
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // FClientID
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // FAppOrgID
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // FHuozhuInType
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // FHuozhuOutType
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // FDbType
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // FOrderNo
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // FWordShopID
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // FWordShop
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // FStoreManID
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // FStockerGroupID
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // FCreateOrgID
            cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29), // FHuoZhuID
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // FWlCompany
            cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31), // FCarBoxNo
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // FPassNo
            cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33), // FFreight
            cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34), // FYaoNo
            cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35), // FFieldMan
            cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36), // FStr1
            cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37), // FStr2
            cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38), // FStr3
            cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39), // FStr4
            cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40), // FStr5
            cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41), // FPayType
            cursor.isNull(offset + 42) ? null : cursor.getString(offset + 42) // FAcPrd
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PushDownMain entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFBillNo(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFSupplyID(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFSupply(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setFSaleOrgID(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFStoreOrgID(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setFSettleOrgId(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setFSaleManID(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setFSaleDeptID(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setFNot(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setTag(cursor.getInt(offset + 12));
        entity.setFBillTypeName(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setFAccountID(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setFDept(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setFDeptID(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setFClient(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setFClientID(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setFAppOrgID(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setFHuozhuInType(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setFHuozhuOutType(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setFDbType(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setFOrderNo(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setFWordShopID(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setFWordShop(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setFStoreManID(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setFStockerGroupID(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setFCreateOrgID(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setFHuoZhuID(cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29));
        entity.setFWlCompany(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setFCarBoxNo(cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31));
        entity.setFPassNo(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setFFreight(cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33));
        entity.setFYaoNo(cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34));
        entity.setFFieldMan(cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35));
        entity.setFStr1(cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36));
        entity.setFStr2(cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37));
        entity.setFStr3(cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38));
        entity.setFStr4(cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39));
        entity.setFStr5(cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40));
        entity.setFPayType(cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41));
        entity.setFAcPrd(cursor.isNull(offset + 42) ? null : cursor.getString(offset + 42));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(PushDownMain entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(PushDownMain entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PushDownMain entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
