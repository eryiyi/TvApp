package com.Lbins.TvApp.module;

import com.Lbins.TvApp.dao.DaoSession;
import com.Lbins.TvApp.dao.MinePicObjDao;
import de.greenrobot.dao.DaoException;


// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table MINE_PIC_OBJ.
 */
public class MinePicObj {

    /** Not-null value. */
    private String picStr;
    /** Not-null value. */
    private String mm_emp_id;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient MinePicObjDao myDao;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public MinePicObj() {
    }

    public MinePicObj(String picStr) {
        this.picStr = picStr;
    }

    public MinePicObj(String picStr, String mm_emp_id) {
        this.picStr = picStr;
        this.mm_emp_id = mm_emp_id;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMinePicObjDao() : null;
    }

    /** Not-null value. */
    public String getPicStr() {
        return picStr;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPicStr(String picStr) {
        this.picStr = picStr;
    }

    /** Not-null value. */
    public String getMm_emp_id() {
        return mm_emp_id;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMm_emp_id(String mm_emp_id) {
        this.mm_emp_id = mm_emp_id;
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
