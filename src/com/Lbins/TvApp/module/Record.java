package com.Lbins.TvApp.module;

import com.Lbins.TvApp.dao.DaoSession;
import com.Lbins.TvApp.dao.RecordDao;
import de.greenrobot.dao.DaoException;

import java.io.Serializable;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table RECORD.
 */
public class Record implements Serializable{

    /** Not-null value. */
    private String mm_msg_id;
    /** Not-null value. */
    private String mm_emp_id;
    private String mm_msg_type;
    private String mm_msg_content;
    private String mm_msg_picurl;
    private String dateline;
    private String is_del;
    private String is_top;
    private String top_num;
    private String mm_emp_cover;
    private String mm_emp_mobile;
    private String mm_emp_nickname;
    private String deviceType;
    private String mm_emp_sex;
    private String mm_emp_birthday;
    private String mm_emp_up_emp;
    private String mm_hangye_id;
    private String levelName;
    private String zanNum;
    private String plNum;
    private String hangye;
    private String mm_emp_motto;
    private String mm_emp_company;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient RecordDao myDao;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Record() {
    }

    public Record(String mm_msg_id) {
        this.mm_msg_id = mm_msg_id;
    }

    public Record(String mm_msg_id, String mm_emp_id, String mm_msg_type, String mm_msg_content, String mm_msg_picurl, String dateline, String is_del, String is_top, String top_num, String mm_emp_cover, String mm_emp_mobile, String mm_emp_nickname, String deviceType, String mm_emp_sex, String mm_emp_birthday, String mm_emp_up_emp, String mm_hangye_id, String levelName, String zanNum, String plNum, String hangye, String mm_emp_motto, String mm_emp_company) {
        this.mm_msg_id = mm_msg_id;
        this.mm_emp_id = mm_emp_id;
        this.mm_msg_type = mm_msg_type;
        this.mm_msg_content = mm_msg_content;
        this.mm_msg_picurl = mm_msg_picurl;
        this.dateline = dateline;
        this.is_del = is_del;
        this.is_top = is_top;
        this.top_num = top_num;
        this.mm_emp_cover = mm_emp_cover;
        this.mm_emp_mobile = mm_emp_mobile;
        this.mm_emp_nickname = mm_emp_nickname;
        this.deviceType = deviceType;
        this.mm_emp_sex = mm_emp_sex;
        this.mm_emp_birthday = mm_emp_birthday;
        this.mm_emp_up_emp = mm_emp_up_emp;
        this.mm_hangye_id = mm_hangye_id;
        this.levelName = levelName;
        this.zanNum = zanNum;
        this.plNum = plNum;
        this.hangye = hangye;
        this.mm_emp_motto = mm_emp_motto;
        this.mm_emp_company = mm_emp_company;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRecordDao() : null;
    }

    /** Not-null value. */
    public String getMm_msg_id() {
        return mm_msg_id;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMm_msg_id(String mm_msg_id) {
        this.mm_msg_id = mm_msg_id;
    }

    /** Not-null value. */
    public String getMm_emp_id() {
        return mm_emp_id;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMm_emp_id(String mm_emp_id) {
        this.mm_emp_id = mm_emp_id;
    }

    public String getMm_msg_type() {
        return mm_msg_type;
    }

    public void setMm_msg_type(String mm_msg_type) {
        this.mm_msg_type = mm_msg_type;
    }

    public String getMm_msg_content() {
        return mm_msg_content;
    }

    public void setMm_msg_content(String mm_msg_content) {
        this.mm_msg_content = mm_msg_content;
    }

    public String getMm_msg_picurl() {
        return mm_msg_picurl;
    }

    public void setMm_msg_picurl(String mm_msg_picurl) {
        this.mm_msg_picurl = mm_msg_picurl;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getIs_top() {
        return is_top;
    }

    public void setIs_top(String is_top) {
        this.is_top = is_top;
    }

    public String getTop_num() {
        return top_num;
    }

    public void setTop_num(String top_num) {
        this.top_num = top_num;
    }

    public String getMm_emp_cover() {
        return mm_emp_cover;
    }

    public void setMm_emp_cover(String mm_emp_cover) {
        this.mm_emp_cover = mm_emp_cover;
    }

    public String getMm_emp_mobile() {
        return mm_emp_mobile;
    }

    public void setMm_emp_mobile(String mm_emp_mobile) {
        this.mm_emp_mobile = mm_emp_mobile;
    }

    public String getMm_emp_nickname() {
        return mm_emp_nickname;
    }

    public void setMm_emp_nickname(String mm_emp_nickname) {
        this.mm_emp_nickname = mm_emp_nickname;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getMm_emp_sex() {
        return mm_emp_sex;
    }

    public void setMm_emp_sex(String mm_emp_sex) {
        this.mm_emp_sex = mm_emp_sex;
    }

    public String getMm_emp_birthday() {
        return mm_emp_birthday;
    }

    public void setMm_emp_birthday(String mm_emp_birthday) {
        this.mm_emp_birthday = mm_emp_birthday;
    }

    public String getMm_emp_up_emp() {
        return mm_emp_up_emp;
    }

    public void setMm_emp_up_emp(String mm_emp_up_emp) {
        this.mm_emp_up_emp = mm_emp_up_emp;
    }

    public String getMm_hangye_id() {
        return mm_hangye_id;
    }

    public void setMm_hangye_id(String mm_hangye_id) {
        this.mm_hangye_id = mm_hangye_id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getZanNum() {
        return zanNum;
    }

    public void setZanNum(String zanNum) {
        this.zanNum = zanNum;
    }

    public String getPlNum() {
        return plNum;
    }

    public void setPlNum(String plNum) {
        this.plNum = plNum;
    }

    public String getHangye() {
        return hangye;
    }

    public void setHangye(String hangye) {
        this.hangye = hangye;
    }

    public String getMm_emp_motto() {
        return mm_emp_motto;
    }

    public void setMm_emp_motto(String mm_emp_motto) {
        this.mm_emp_motto = mm_emp_motto;
    }

    public String getMm_emp_company() {
        return mm_emp_company;
    }

    public void setMm_emp_company(String mm_emp_company) {
        this.mm_emp_company = mm_emp_company;
    }

    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
