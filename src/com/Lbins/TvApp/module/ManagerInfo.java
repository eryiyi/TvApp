package com.Lbins.TvApp.module;

import com.Lbins.TvApp.dao.DaoSession;
import com.Lbins.TvApp.dao.ManagerInfoDao;
import de.greenrobot.dao.DaoException;


// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table MANAGER_INFO.
 */
public class ManagerInfo {

    /** Not-null value. */
    private String id;
    private String managerId;
    private String realName;
    private String idcard;
    private String idcardUrl;
    private String payNumber;
    private String checkName;
    private String bankCard;
    private String bankType;
    private String bankAddress;
    private String bankName;
    private String mobile;
    private String lat_company;
    private String lng_company;
    private String company_address;
    private String company_person;
    private String company_detail;
    private String company_tel;
    private String company_name;
    private String yingye_time_start;
    private String yingye_time_end;
    private String shouhui;
    private String company_pic;
    private String emp_id;
    private String emp_cover;
    private String gd_type_id;
    private String gd_type_name;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ManagerInfoDao myDao;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public ManagerInfo() {
    }

    public ManagerInfo(String id) {
        this.id = id;
    }

    public ManagerInfo(String id, String managerId, String realName, String idcard, String idcardUrl, String payNumber, String checkName, String bankCard, String bankType, String bankAddress, String bankName, String mobile, String lat_company, String lng_company, String company_address, String company_person, String company_detail, String company_tel, String company_name, String yingye_time_start, String yingye_time_end, String shouhui, String company_pic, String emp_id, String emp_cover, String gd_type_id, String gd_type_name) {
        this.id = id;
        this.managerId = managerId;
        this.realName = realName;
        this.idcard = idcard;
        this.idcardUrl = idcardUrl;
        this.payNumber = payNumber;
        this.checkName = checkName;
        this.bankCard = bankCard;
        this.bankType = bankType;
        this.bankAddress = bankAddress;
        this.bankName = bankName;
        this.mobile = mobile;
        this.lat_company = lat_company;
        this.lng_company = lng_company;
        this.company_address = company_address;
        this.company_person = company_person;
        this.company_detail = company_detail;
        this.company_tel = company_tel;
        this.company_name = company_name;
        this.yingye_time_start = yingye_time_start;
        this.yingye_time_end = yingye_time_end;
        this.shouhui = shouhui;
        this.company_pic = company_pic;
        this.emp_id = emp_id;
        this.emp_cover = emp_cover;
        this.gd_type_id = gd_type_id;
        this.gd_type_name = gd_type_name;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getManagerInfoDao() : null;
    }

    /** Not-null value. */
    public String getId() {
        return id;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setId(String id) {
        this.id = id;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIdcardUrl() {
        return idcardUrl;
    }

    public void setIdcardUrl(String idcardUrl) {
        this.idcardUrl = idcardUrl;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLat_company() {
        return lat_company;
    }

    public void setLat_company(String lat_company) {
        this.lat_company = lat_company;
    }

    public String getLng_company() {
        return lng_company;
    }

    public void setLng_company(String lng_company) {
        this.lng_company = lng_company;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getCompany_person() {
        return company_person;
    }

    public void setCompany_person(String company_person) {
        this.company_person = company_person;
    }

    public String getCompany_detail() {
        return company_detail;
    }

    public void setCompany_detail(String company_detail) {
        this.company_detail = company_detail;
    }

    public String getCompany_tel() {
        return company_tel;
    }

    public void setCompany_tel(String company_tel) {
        this.company_tel = company_tel;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getYingye_time_start() {
        return yingye_time_start;
    }

    public void setYingye_time_start(String yingye_time_start) {
        this.yingye_time_start = yingye_time_start;
    }

    public String getYingye_time_end() {
        return yingye_time_end;
    }

    public void setYingye_time_end(String yingye_time_end) {
        this.yingye_time_end = yingye_time_end;
    }

    public String getShouhui() {
        return shouhui;
    }

    public void setShouhui(String shouhui) {
        this.shouhui = shouhui;
    }

    public String getCompany_pic() {
        return company_pic;
    }

    public void setCompany_pic(String company_pic) {
        this.company_pic = company_pic;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_cover() {
        return emp_cover;
    }

    public void setEmp_cover(String emp_cover) {
        this.emp_cover = emp_cover;
    }

    public String getGd_type_id() {
        return gd_type_id;
    }

    public void setGd_type_id(String gd_type_id) {
        this.gd_type_id = gd_type_id;
    }

    public String getGd_type_name() {
        return gd_type_name;
    }

    public void setGd_type_name(String gd_type_name) {
        this.gd_type_name = gd_type_name;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}