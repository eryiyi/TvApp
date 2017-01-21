package com.Lbins.TvApp.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.Lbins.TvApp.dao.DaoSession;
import com.Lbins.TvApp.module.Record;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table RECORD.
*/
public class RecordDao extends AbstractDao<Record, String> {

    public static final String TABLENAME = "RECORD";

    /**
     * Properties of entity Record.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Mm_msg_id = new Property(0, String.class, "mm_msg_id", true, "MM_MSG_ID");
        public final static Property Mm_emp_id = new Property(1, String.class, "mm_emp_id", false, "MM_EMP_ID");
        public final static Property Mm_msg_type = new Property(2, String.class, "mm_msg_type", false, "MM_MSG_TYPE");
        public final static Property Mm_msg_content = new Property(3, String.class, "mm_msg_content", false, "MM_MSG_CONTENT");
        public final static Property Mm_msg_picurl = new Property(4, String.class, "mm_msg_picurl", false, "MM_MSG_PICURL");
        public final static Property Dateline = new Property(5, String.class, "dateline", false, "DATELINE");
        public final static Property Is_del = new Property(6, String.class, "is_del", false, "IS_DEL");
        public final static Property Is_top = new Property(7, String.class, "is_top", false, "IS_TOP");
        public final static Property Top_num = new Property(8, String.class, "top_num", false, "TOP_NUM");
        public final static Property Mm_emp_cover = new Property(9, String.class, "mm_emp_cover", false, "MM_EMP_COVER");
        public final static Property Mm_emp_mobile = new Property(10, String.class, "mm_emp_mobile", false, "MM_EMP_MOBILE");
        public final static Property Mm_emp_nickname = new Property(11, String.class, "mm_emp_nickname", false, "MM_EMP_NICKNAME");
        public final static Property DeviceType = new Property(12, String.class, "deviceType", false, "DEVICE_TYPE");
        public final static Property Mm_emp_sex = new Property(13, String.class, "mm_emp_sex", false, "MM_EMP_SEX");
        public final static Property Mm_emp_birthday = new Property(14, String.class, "mm_emp_birthday", false, "MM_EMP_BIRTHDAY");
        public final static Property Mm_emp_up_emp = new Property(15, String.class, "mm_emp_up_emp", false, "MM_EMP_UP_EMP");
        public final static Property Mm_hangye_id = new Property(16, String.class, "mm_hangye_id", false, "MM_HANGYE_ID");
        public final static Property LevelName = new Property(17, String.class, "levelName", false, "LEVEL_NAME");
        public final static Property ZanNum = new Property(18, String.class, "zanNum", false, "ZAN_NUM");
        public final static Property PlNum = new Property(19, String.class, "plNum", false, "PL_NUM");
        public final static Property Hangye = new Property(20, String.class, "hangye", false, "HANGYE");
        public final static Property Mm_emp_motto = new Property(21, String.class, "mm_emp_motto", false, "MM_EMP_MOTTO");
        public final static Property Mm_emp_company = new Property(22, String.class, "mm_emp_company", false, "MM_EMP_COMPANY");
    };

    private DaoSession daoSession;


    public RecordDao(DaoConfig config) {
        super(config);
    }
    
    public RecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'RECORD' (" + //
                "'MM_MSG_ID' TEXT PRIMARY KEY NOT NULL ," + // 0: mm_msg_id
                "'MM_EMP_ID' TEXT NOT NULL ," + // 1: mm_emp_id
                "'MM_MSG_TYPE' TEXT," + // 2: mm_msg_type
                "'MM_MSG_CONTENT' TEXT," + // 3: mm_msg_content
                "'MM_MSG_PICURL' TEXT," + // 4: mm_msg_picurl
                "'DATELINE' TEXT," + // 5: dateline
                "'IS_DEL' TEXT," + // 6: is_del
                "'IS_TOP' TEXT," + // 7: is_top
                "'TOP_NUM' TEXT," + // 8: top_num
                "'MM_EMP_COVER' TEXT," + // 9: mm_emp_cover
                "'MM_EMP_MOBILE' TEXT," + // 10: mm_emp_mobile
                "'MM_EMP_NICKNAME' TEXT," + // 11: mm_emp_nickname
                "'DEVICE_TYPE' TEXT," + // 12: deviceType
                "'MM_EMP_SEX' TEXT," + // 13: mm_emp_sex
                "'MM_EMP_BIRTHDAY' TEXT," + // 14: mm_emp_birthday
                "'MM_EMP_UP_EMP' TEXT," + // 15: mm_emp_up_emp
                "'MM_HANGYE_ID' TEXT," + // 16: mm_hangye_id
                "'LEVEL_NAME' TEXT," + // 17: levelName
                "'ZAN_NUM' TEXT," + // 18: zanNum
                "'PL_NUM' TEXT," + // 19: plNum
                "'HANGYE' TEXT," + // 20: hangye
                "'MM_EMP_MOTTO' TEXT," + // 21: mm_emp_motto
                "'MM_EMP_COMPANY' TEXT);"); // 22: mm_emp_company
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'RECORD'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Record entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getMm_msg_id());
        stmt.bindString(2, entity.getMm_emp_id());
 
        String mm_msg_type = entity.getMm_msg_type();
        if (mm_msg_type != null) {
            stmt.bindString(3, mm_msg_type);
        }
 
        String mm_msg_content = entity.getMm_msg_content();
        if (mm_msg_content != null) {
            stmt.bindString(4, mm_msg_content);
        }
 
        String mm_msg_picurl = entity.getMm_msg_picurl();
        if (mm_msg_picurl != null) {
            stmt.bindString(5, mm_msg_picurl);
        }
 
        String dateline = entity.getDateline();
        if (dateline != null) {
            stmt.bindString(6, dateline);
        }
 
        String is_del = entity.getIs_del();
        if (is_del != null) {
            stmt.bindString(7, is_del);
        }
 
        String is_top = entity.getIs_top();
        if (is_top != null) {
            stmt.bindString(8, is_top);
        }
 
        String top_num = entity.getTop_num();
        if (top_num != null) {
            stmt.bindString(9, top_num);
        }
 
        String mm_emp_cover = entity.getMm_emp_cover();
        if (mm_emp_cover != null) {
            stmt.bindString(10, mm_emp_cover);
        }
 
        String mm_emp_mobile = entity.getMm_emp_mobile();
        if (mm_emp_mobile != null) {
            stmt.bindString(11, mm_emp_mobile);
        }
 
        String mm_emp_nickname = entity.getMm_emp_nickname();
        if (mm_emp_nickname != null) {
            stmt.bindString(12, mm_emp_nickname);
        }
 
        String deviceType = entity.getDeviceType();
        if (deviceType != null) {
            stmt.bindString(13, deviceType);
        }
 
        String mm_emp_sex = entity.getMm_emp_sex();
        if (mm_emp_sex != null) {
            stmt.bindString(14, mm_emp_sex);
        }
 
        String mm_emp_birthday = entity.getMm_emp_birthday();
        if (mm_emp_birthday != null) {
            stmt.bindString(15, mm_emp_birthday);
        }
 
        String mm_emp_up_emp = entity.getMm_emp_up_emp();
        if (mm_emp_up_emp != null) {
            stmt.bindString(16, mm_emp_up_emp);
        }
 
        String mm_hangye_id = entity.getMm_hangye_id();
        if (mm_hangye_id != null) {
            stmt.bindString(17, mm_hangye_id);
        }
 
        String levelName = entity.getLevelName();
        if (levelName != null) {
            stmt.bindString(18, levelName);
        }
 
        String zanNum = entity.getZanNum();
        if (zanNum != null) {
            stmt.bindString(19, zanNum);
        }
 
        String plNum = entity.getPlNum();
        if (plNum != null) {
            stmt.bindString(20, plNum);
        }
 
        String hangye = entity.getHangye();
        if (hangye != null) {
            stmt.bindString(21, hangye);
        }
 
        String mm_emp_motto = entity.getMm_emp_motto();
        if (mm_emp_motto != null) {
            stmt.bindString(22, mm_emp_motto);
        }
 
        String mm_emp_company = entity.getMm_emp_company();
        if (mm_emp_company != null) {
            stmt.bindString(23, mm_emp_company);
        }
    }

    @Override
    protected void attachEntity(Record entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Record readEntity(Cursor cursor, int offset) {
        Record entity = new Record( //
            cursor.getString(offset + 0), // mm_msg_id
            cursor.getString(offset + 1), // mm_emp_id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // mm_msg_type
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mm_msg_content
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // mm_msg_picurl
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // dateline
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // is_del
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // is_top
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // top_num
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // mm_emp_cover
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // mm_emp_mobile
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // mm_emp_nickname
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // deviceType
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // mm_emp_sex
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // mm_emp_birthday
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // mm_emp_up_emp
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // mm_hangye_id
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // levelName
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // zanNum
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // plNum
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // hangye
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // mm_emp_motto
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22) // mm_emp_company
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Record entity, int offset) {
        entity.setMm_msg_id(cursor.getString(offset + 0));
        entity.setMm_emp_id(cursor.getString(offset + 1));
        entity.setMm_msg_type(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMm_msg_content(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMm_msg_picurl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDateline(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIs_del(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setIs_top(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTop_num(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setMm_emp_cover(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setMm_emp_mobile(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setMm_emp_nickname(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setDeviceType(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setMm_emp_sex(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setMm_emp_birthday(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setMm_emp_up_emp(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setMm_hangye_id(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setLevelName(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setZanNum(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setPlNum(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setHangye(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setMm_emp_motto(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setMm_emp_company(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(Record entity, long rowId) {
        return entity.getMm_msg_id();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(Record entity) {
        if(entity != null) {
            return entity.getMm_msg_id();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
