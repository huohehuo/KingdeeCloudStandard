package com.fangzuo.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.fangzuo.assist.cloud.Beans.WortPrintData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WORT_PRINT_DATA".
*/
public class WortPrintDataDao extends AbstractDao<WortPrintData, Long> {

    public static final String TABLENAME = "WORT_PRINT_DATA";

    /**
     * Properties of entity WortPrintData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property FIndex = new Property(0, Long.class, "FIndex", true, "_id");
        public final static Property FID = new Property(1, String.class, "FID", false, "FID");
        public final static Property FEntryID = new Property(2, String.class, "FEntryID", false, "FENTRY_ID");
        public final static Property FItemID = new Property(3, String.class, "FItemID", false, "FITEM_ID");
        public final static Property FUnitID = new Property(4, String.class, "FUnitID", false, "FUNIT_ID");
        public final static Property FDCStockID = new Property(5, String.class, "FDCStockID", false, "FDCSTOCK_ID");
        public final static Property FDCSPID = new Property(6, String.class, "FDCSPID", false, "FDCSPID");
        public final static Property FSTOCKORGID = new Property(7, String.class, "FSTOCKORGID", false, "FSTOCKORGID");
        public final static Property FOWNERID = new Property(8, String.class, "FOWNERID", false, "FOWNERID");
        public final static Property FBoxCode = new Property(9, String.class, "FBoxCode", false, "FBOX_CODE");
        public final static Property FSplitBoxCode = new Property(10, String.class, "FSplitBoxCode", false, "FSPLIT_BOX_CODE");
        public final static Property FDate = new Property(11, String.class, "FDate", false, "FDATE");
        public final static Property FUser = new Property(12, String.class, "FUser", false, "FUSER");
        public final static Property FName = new Property(13, String.class, "FName", false, "FNAME");
        public final static Property FUnit = new Property(14, String.class, "FUnit", false, "FUNIT");
        public final static Property FModel = new Property(15, String.class, "FModel", false, "FMODEL");
        public final static Property FBatch = new Property(16, String.class, "FBatch", false, "FBATCH");
        public final static Property FLenght = new Property(17, String.class, "FLenght", false, "FLENGHT");
        public final static Property FChang = new Property(18, String.class, "FChang", false, "FCHANG");
        public final static Property FKuan = new Property(19, String.class, "FKuan", false, "FKUAN");
        public final static Property FHou = new Property(20, String.class, "FHou", false, "FHOU");
        public final static Property FQty = new Property(21, String.class, "FQty", false, "FQTY");
        public final static Property FQtySplit = new Property(22, String.class, "FQtySplit", false, "FQTY_SPLIT");
        public final static Property FM2Split = new Property(23, String.class, "FM2Split", false, "FM2_SPLIT");
        public final static Property FQtySum = new Property(24, String.class, "FQtySum", false, "FQTY_SUM");
        public final static Property FM2 = new Property(25, String.class, "FM2", false, "FM2");
        public final static Property FM2Sum = new Property(26, String.class, "FM2Sum", false, "FM2_SUM");
        public final static Property FVolSum = new Property(27, String.class, "FVolSum", false, "FVOL_SUM");
        public final static Property FTip = new Property(28, String.class, "FTip", false, "FTIP");
        public final static Property FWide = new Property(29, String.class, "FWide", false, "FWIDE");
        public final static Property FMaker = new Property(30, String.class, "FMaker", false, "FMAKER");
        public final static Property FPrintNum = new Property(31, String.class, "FPrintNum", false, "FPRINT_NUM");
        public final static Property FFBaoNum = new Property(32, String.class, "FFBaoNum", false, "FFBAO_NUM");
        public final static Property FLev = new Property(33, String.class, "FLev", false, "FLEV");
        public final static Property FCarNo = new Property(34, String.class, "FCarNo", false, "FCAR_NO");
        public final static Property FFBaoNo = new Property(35, String.class, "FFBaoNo", false, "FFBAO_NO");
        public final static Property FType = new Property(36, String.class, "FType", false, "FTYPE");
        public final static Property FHuozhuNumber = new Property(37, String.class, "FHuozhuNumber", false, "FHUOZHU_NUMBER");
        public final static Property FStorageNumber = new Property(38, String.class, "FStorageNumber", false, "FSTORAGE_NUMBER");
        public final static Property FOrgNumber = new Property(39, String.class, "FOrgNumber", false, "FORG_NUMBER");
        public final static Property FAveLenght = new Property(40, String.class, "FAveLenght", false, "FAVE_LENGHT");
    }


    public WortPrintDataDao(DaoConfig config) {
        super(config);
    }
    
    public WortPrintDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WORT_PRINT_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: FIndex
                "\"FID\" TEXT," + // 1: FID
                "\"FENTRY_ID\" TEXT," + // 2: FEntryID
                "\"FITEM_ID\" TEXT," + // 3: FItemID
                "\"FUNIT_ID\" TEXT," + // 4: FUnitID
                "\"FDCSTOCK_ID\" TEXT," + // 5: FDCStockID
                "\"FDCSPID\" TEXT," + // 6: FDCSPID
                "\"FSTOCKORGID\" TEXT," + // 7: FSTOCKORGID
                "\"FOWNERID\" TEXT," + // 8: FOWNERID
                "\"FBOX_CODE\" TEXT," + // 9: FBoxCode
                "\"FSPLIT_BOX_CODE\" TEXT," + // 10: FSplitBoxCode
                "\"FDATE\" TEXT," + // 11: FDate
                "\"FUSER\" TEXT," + // 12: FUser
                "\"FNAME\" TEXT," + // 13: FName
                "\"FUNIT\" TEXT," + // 14: FUnit
                "\"FMODEL\" TEXT," + // 15: FModel
                "\"FBATCH\" TEXT," + // 16: FBatch
                "\"FLENGHT\" TEXT," + // 17: FLenght
                "\"FCHANG\" TEXT," + // 18: FChang
                "\"FKUAN\" TEXT," + // 19: FKuan
                "\"FHOU\" TEXT," + // 20: FHou
                "\"FQTY\" TEXT," + // 21: FQty
                "\"FQTY_SPLIT\" TEXT," + // 22: FQtySplit
                "\"FM2_SPLIT\" TEXT," + // 23: FM2Split
                "\"FQTY_SUM\" TEXT," + // 24: FQtySum
                "\"FM2\" TEXT," + // 25: FM2
                "\"FM2_SUM\" TEXT," + // 26: FM2Sum
                "\"FVOL_SUM\" TEXT," + // 27: FVolSum
                "\"FTIP\" TEXT," + // 28: FTip
                "\"FWIDE\" TEXT," + // 29: FWide
                "\"FMAKER\" TEXT," + // 30: FMaker
                "\"FPRINT_NUM\" TEXT," + // 31: FPrintNum
                "\"FFBAO_NUM\" TEXT," + // 32: FFBaoNum
                "\"FLEV\" TEXT," + // 33: FLev
                "\"FCAR_NO\" TEXT," + // 34: FCarNo
                "\"FFBAO_NO\" TEXT," + // 35: FFBaoNo
                "\"FTYPE\" TEXT," + // 36: FType
                "\"FHUOZHU_NUMBER\" TEXT," + // 37: FHuozhuNumber
                "\"FSTORAGE_NUMBER\" TEXT," + // 38: FStorageNumber
                "\"FORG_NUMBER\" TEXT," + // 39: FOrgNumber
                "\"FAVE_LENGHT\" TEXT);"); // 40: FAveLenght
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WORT_PRINT_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, WortPrintData entity) {
        stmt.clearBindings();
 
        Long FIndex = entity.getFIndex();
        if (FIndex != null) {
            stmt.bindLong(1, FIndex);
        }
 
        String FID = entity.getFID();
        if (FID != null) {
            stmt.bindString(2, FID);
        }
 
        String FEntryID = entity.getFEntryID();
        if (FEntryID != null) {
            stmt.bindString(3, FEntryID);
        }
 
        String FItemID = entity.getFItemID();
        if (FItemID != null) {
            stmt.bindString(4, FItemID);
        }
 
        String FUnitID = entity.getFUnitID();
        if (FUnitID != null) {
            stmt.bindString(5, FUnitID);
        }
 
        String FDCStockID = entity.getFDCStockID();
        if (FDCStockID != null) {
            stmt.bindString(6, FDCStockID);
        }
 
        String FDCSPID = entity.getFDCSPID();
        if (FDCSPID != null) {
            stmt.bindString(7, FDCSPID);
        }
 
        String FSTOCKORGID = entity.getFSTOCKORGID();
        if (FSTOCKORGID != null) {
            stmt.bindString(8, FSTOCKORGID);
        }
 
        String FOWNERID = entity.getFOWNERID();
        if (FOWNERID != null) {
            stmt.bindString(9, FOWNERID);
        }
 
        String FBoxCode = entity.getFBoxCode();
        if (FBoxCode != null) {
            stmt.bindString(10, FBoxCode);
        }
 
        String FSplitBoxCode = entity.getFSplitBoxCode();
        if (FSplitBoxCode != null) {
            stmt.bindString(11, FSplitBoxCode);
        }
 
        String FDate = entity.getFDate();
        if (FDate != null) {
            stmt.bindString(12, FDate);
        }
 
        String FUser = entity.getFUser();
        if (FUser != null) {
            stmt.bindString(13, FUser);
        }
 
        String FName = entity.getFName();
        if (FName != null) {
            stmt.bindString(14, FName);
        }
 
        String FUnit = entity.getFUnit();
        if (FUnit != null) {
            stmt.bindString(15, FUnit);
        }
 
        String FModel = entity.getFModel();
        if (FModel != null) {
            stmt.bindString(16, FModel);
        }
 
        String FBatch = entity.getFBatch();
        if (FBatch != null) {
            stmt.bindString(17, FBatch);
        }
 
        String FLenght = entity.getFLenght();
        if (FLenght != null) {
            stmt.bindString(18, FLenght);
        }
 
        String FChang = entity.getFChang();
        if (FChang != null) {
            stmt.bindString(19, FChang);
        }
 
        String FKuan = entity.getFKuan();
        if (FKuan != null) {
            stmt.bindString(20, FKuan);
        }
 
        String FHou = entity.getFHou();
        if (FHou != null) {
            stmt.bindString(21, FHou);
        }
 
        String FQty = entity.getFQty();
        if (FQty != null) {
            stmt.bindString(22, FQty);
        }
 
        String FQtySplit = entity.getFQtySplit();
        if (FQtySplit != null) {
            stmt.bindString(23, FQtySplit);
        }
 
        String FM2Split = entity.getFM2Split();
        if (FM2Split != null) {
            stmt.bindString(24, FM2Split);
        }
 
        String FQtySum = entity.getFQtySum();
        if (FQtySum != null) {
            stmt.bindString(25, FQtySum);
        }
 
        String FM2 = entity.getFM2();
        if (FM2 != null) {
            stmt.bindString(26, FM2);
        }
 
        String FM2Sum = entity.getFM2Sum();
        if (FM2Sum != null) {
            stmt.bindString(27, FM2Sum);
        }
 
        String FVolSum = entity.getFVolSum();
        if (FVolSum != null) {
            stmt.bindString(28, FVolSum);
        }
 
        String FTip = entity.getFTip();
        if (FTip != null) {
            stmt.bindString(29, FTip);
        }
 
        String FWide = entity.getFWide();
        if (FWide != null) {
            stmt.bindString(30, FWide);
        }
 
        String FMaker = entity.getFMaker();
        if (FMaker != null) {
            stmt.bindString(31, FMaker);
        }
 
        String FPrintNum = entity.getFPrintNum();
        if (FPrintNum != null) {
            stmt.bindString(32, FPrintNum);
        }
 
        String FFBaoNum = entity.getFFBaoNum();
        if (FFBaoNum != null) {
            stmt.bindString(33, FFBaoNum);
        }
 
        String FLev = entity.getFLev();
        if (FLev != null) {
            stmt.bindString(34, FLev);
        }
 
        String FCarNo = entity.getFCarNo();
        if (FCarNo != null) {
            stmt.bindString(35, FCarNo);
        }
 
        String FFBaoNo = entity.getFFBaoNo();
        if (FFBaoNo != null) {
            stmt.bindString(36, FFBaoNo);
        }
 
        String FType = entity.getFType();
        if (FType != null) {
            stmt.bindString(37, FType);
        }
 
        String FHuozhuNumber = entity.getFHuozhuNumber();
        if (FHuozhuNumber != null) {
            stmt.bindString(38, FHuozhuNumber);
        }
 
        String FStorageNumber = entity.getFStorageNumber();
        if (FStorageNumber != null) {
            stmt.bindString(39, FStorageNumber);
        }
 
        String FOrgNumber = entity.getFOrgNumber();
        if (FOrgNumber != null) {
            stmt.bindString(40, FOrgNumber);
        }
 
        String FAveLenght = entity.getFAveLenght();
        if (FAveLenght != null) {
            stmt.bindString(41, FAveLenght);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, WortPrintData entity) {
        stmt.clearBindings();
 
        Long FIndex = entity.getFIndex();
        if (FIndex != null) {
            stmt.bindLong(1, FIndex);
        }
 
        String FID = entity.getFID();
        if (FID != null) {
            stmt.bindString(2, FID);
        }
 
        String FEntryID = entity.getFEntryID();
        if (FEntryID != null) {
            stmt.bindString(3, FEntryID);
        }
 
        String FItemID = entity.getFItemID();
        if (FItemID != null) {
            stmt.bindString(4, FItemID);
        }
 
        String FUnitID = entity.getFUnitID();
        if (FUnitID != null) {
            stmt.bindString(5, FUnitID);
        }
 
        String FDCStockID = entity.getFDCStockID();
        if (FDCStockID != null) {
            stmt.bindString(6, FDCStockID);
        }
 
        String FDCSPID = entity.getFDCSPID();
        if (FDCSPID != null) {
            stmt.bindString(7, FDCSPID);
        }
 
        String FSTOCKORGID = entity.getFSTOCKORGID();
        if (FSTOCKORGID != null) {
            stmt.bindString(8, FSTOCKORGID);
        }
 
        String FOWNERID = entity.getFOWNERID();
        if (FOWNERID != null) {
            stmt.bindString(9, FOWNERID);
        }
 
        String FBoxCode = entity.getFBoxCode();
        if (FBoxCode != null) {
            stmt.bindString(10, FBoxCode);
        }
 
        String FSplitBoxCode = entity.getFSplitBoxCode();
        if (FSplitBoxCode != null) {
            stmt.bindString(11, FSplitBoxCode);
        }
 
        String FDate = entity.getFDate();
        if (FDate != null) {
            stmt.bindString(12, FDate);
        }
 
        String FUser = entity.getFUser();
        if (FUser != null) {
            stmt.bindString(13, FUser);
        }
 
        String FName = entity.getFName();
        if (FName != null) {
            stmt.bindString(14, FName);
        }
 
        String FUnit = entity.getFUnit();
        if (FUnit != null) {
            stmt.bindString(15, FUnit);
        }
 
        String FModel = entity.getFModel();
        if (FModel != null) {
            stmt.bindString(16, FModel);
        }
 
        String FBatch = entity.getFBatch();
        if (FBatch != null) {
            stmt.bindString(17, FBatch);
        }
 
        String FLenght = entity.getFLenght();
        if (FLenght != null) {
            stmt.bindString(18, FLenght);
        }
 
        String FChang = entity.getFChang();
        if (FChang != null) {
            stmt.bindString(19, FChang);
        }
 
        String FKuan = entity.getFKuan();
        if (FKuan != null) {
            stmt.bindString(20, FKuan);
        }
 
        String FHou = entity.getFHou();
        if (FHou != null) {
            stmt.bindString(21, FHou);
        }
 
        String FQty = entity.getFQty();
        if (FQty != null) {
            stmt.bindString(22, FQty);
        }
 
        String FQtySplit = entity.getFQtySplit();
        if (FQtySplit != null) {
            stmt.bindString(23, FQtySplit);
        }
 
        String FM2Split = entity.getFM2Split();
        if (FM2Split != null) {
            stmt.bindString(24, FM2Split);
        }
 
        String FQtySum = entity.getFQtySum();
        if (FQtySum != null) {
            stmt.bindString(25, FQtySum);
        }
 
        String FM2 = entity.getFM2();
        if (FM2 != null) {
            stmt.bindString(26, FM2);
        }
 
        String FM2Sum = entity.getFM2Sum();
        if (FM2Sum != null) {
            stmt.bindString(27, FM2Sum);
        }
 
        String FVolSum = entity.getFVolSum();
        if (FVolSum != null) {
            stmt.bindString(28, FVolSum);
        }
 
        String FTip = entity.getFTip();
        if (FTip != null) {
            stmt.bindString(29, FTip);
        }
 
        String FWide = entity.getFWide();
        if (FWide != null) {
            stmt.bindString(30, FWide);
        }
 
        String FMaker = entity.getFMaker();
        if (FMaker != null) {
            stmt.bindString(31, FMaker);
        }
 
        String FPrintNum = entity.getFPrintNum();
        if (FPrintNum != null) {
            stmt.bindString(32, FPrintNum);
        }
 
        String FFBaoNum = entity.getFFBaoNum();
        if (FFBaoNum != null) {
            stmt.bindString(33, FFBaoNum);
        }
 
        String FLev = entity.getFLev();
        if (FLev != null) {
            stmt.bindString(34, FLev);
        }
 
        String FCarNo = entity.getFCarNo();
        if (FCarNo != null) {
            stmt.bindString(35, FCarNo);
        }
 
        String FFBaoNo = entity.getFFBaoNo();
        if (FFBaoNo != null) {
            stmt.bindString(36, FFBaoNo);
        }
 
        String FType = entity.getFType();
        if (FType != null) {
            stmt.bindString(37, FType);
        }
 
        String FHuozhuNumber = entity.getFHuozhuNumber();
        if (FHuozhuNumber != null) {
            stmt.bindString(38, FHuozhuNumber);
        }
 
        String FStorageNumber = entity.getFStorageNumber();
        if (FStorageNumber != null) {
            stmt.bindString(39, FStorageNumber);
        }
 
        String FOrgNumber = entity.getFOrgNumber();
        if (FOrgNumber != null) {
            stmt.bindString(40, FOrgNumber);
        }
 
        String FAveLenght = entity.getFAveLenght();
        if (FAveLenght != null) {
            stmt.bindString(41, FAveLenght);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public WortPrintData readEntity(Cursor cursor, int offset) {
        WortPrintData entity = new WortPrintData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // FIndex
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // FID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // FEntryID
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // FItemID
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // FUnitID
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // FDCStockID
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // FDCSPID
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // FSTOCKORGID
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // FOWNERID
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // FBoxCode
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // FSplitBoxCode
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // FDate
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // FUser
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // FName
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // FUnit
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // FModel
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // FBatch
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // FLenght
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // FChang
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // FKuan
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // FHou
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // FQty
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // FQtySplit
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // FM2Split
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // FQtySum
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // FM2
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // FM2Sum
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // FVolSum
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // FTip
            cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29), // FWide
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // FMaker
            cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31), // FPrintNum
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // FFBaoNum
            cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33), // FLev
            cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34), // FCarNo
            cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35), // FFBaoNo
            cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36), // FType
            cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37), // FHuozhuNumber
            cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38), // FStorageNumber
            cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39), // FOrgNumber
            cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40) // FAveLenght
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, WortPrintData entity, int offset) {
        entity.setFIndex(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFEntryID(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFItemID(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFUnitID(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFDCStockID(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setFDCSPID(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFSTOCKORGID(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setFOWNERID(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setFBoxCode(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setFSplitBoxCode(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setFDate(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setFUser(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setFName(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setFUnit(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setFModel(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setFBatch(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setFLenght(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setFChang(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setFKuan(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setFHou(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setFQty(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setFQtySplit(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setFM2Split(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setFQtySum(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setFM2(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setFM2Sum(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setFVolSum(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setFTip(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setFWide(cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29));
        entity.setFMaker(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setFPrintNum(cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31));
        entity.setFFBaoNum(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setFLev(cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33));
        entity.setFCarNo(cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34));
        entity.setFFBaoNo(cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35));
        entity.setFType(cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36));
        entity.setFHuozhuNumber(cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37));
        entity.setFStorageNumber(cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38));
        entity.setFOrgNumber(cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39));
        entity.setFAveLenght(cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(WortPrintData entity, long rowId) {
        entity.setFIndex(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(WortPrintData entity) {
        if(entity != null) {
            return entity.getFIndex();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(WortPrintData entity) {
        return entity.getFIndex() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}