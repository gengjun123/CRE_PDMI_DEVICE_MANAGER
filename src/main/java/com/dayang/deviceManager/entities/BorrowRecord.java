package com.dayang.deviceManager.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_borrow_records")
public class BorrowRecord {

    @Id
    private String id;

    private String deviceId;

    private String borrowerId;

    private String borrowerName;

    private String borrowerPhone;

    private Date borrowTime;

    private Date timeToReturn;

    private Date timeReturned;

    private String returnerName;

    private Date createdTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getBorrowerPhone() {
        return borrowerPhone;
    }

    public void setBorrowerPhone(String borrowerPhone) {
        this.borrowerPhone = borrowerPhone;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Date getTimeToReturn() {
        return timeToReturn;
    }

    public void setTimeToReturn(Date timeToReturn) {
        this.timeToReturn = timeToReturn;
    }

    public Date getTimeReturned() {
        return timeReturned;
    }

    public void setTimeReturned(Date timeReturned) {
        this.timeReturned = timeReturned;
    }

    public String getReturnerName() {
        return returnerName;
    }

    public void setReturnerName(String returnerName) {
        this.returnerName = returnerName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}