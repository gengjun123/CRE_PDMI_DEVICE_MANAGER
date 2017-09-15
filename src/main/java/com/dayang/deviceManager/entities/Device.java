package com.dayang.deviceManager.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_devices")
public class Device {
    public static final String STATUS_BORROWED = "BORROWED";
    public static final String STATUS_IN_STOCK = "IN_STOCK";
    public static final String STATUS_SCRAPPED = "SCRAPPED";

    @Id
    private String id;

    private String name;

    private String code;

    private String status;

    private String description;

    private Date createdTime;

    private Date importTime;

    @Transient
    private BorrowRecord latestBorrowRecord;

    @Transient
    private List<BorrowRecord> borrowRecordList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public BorrowRecord getLatestBorrowRecord() {
        return latestBorrowRecord;
    }

    public void setLatestBorrowRecord(BorrowRecord latestBorrowRecord) {
        this.latestBorrowRecord = latestBorrowRecord;
    }

    public List<BorrowRecord> getBorrowRecordList() {
        return borrowRecordList;
    }

    public void setBorrowRecordList(List<BorrowRecord> borrowRecordList) {
        this.borrowRecordList = borrowRecordList;
    }
}
