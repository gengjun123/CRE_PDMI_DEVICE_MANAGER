package com.dayang.deviceManager.service;

import com.dayang.deviceManager.entities.BorrowRecord;
import com.dayang.deviceManager.entities.Device;
import com.dayang.deviceManager.repositories.BorrowRecordRepository;
import com.dayang.deviceManager.repositories.DeviceRepository;
import com.dayang.deviceManager.util.UUID;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class BorrowRecordService {

    @Resource
    private BorrowRecordRepository borrowRecordRepository;

    @Resource
    private DeviceRepository deviceRepository;

    public String saveRecord(String deviceId, JSONObject recordObj) {
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setId(UUID.generate());
        borrowRecord.setDeviceId(deviceId);
        borrowRecord.setBorrowerName(recordObj.getString("borrowerName"));
        borrowRecord.setBorrowTime(new Date(recordObj.getLong("borrowTime")));
        borrowRecord.setCreatedTime(new Date());
        if (recordObj.containsKey("borrowerPhone")) {
            borrowRecord.setBorrowerPhone(recordObj.getString("borrowerPhone"));
        }
        if (recordObj.containsKey("timeToReturn")) {
            borrowRecord.setTimeToReturn(new Date(recordObj.getLong("timeToReturn")));
        }
        borrowRecordRepository.save(borrowRecord);

        //更新设备状态为借出
        deviceRepository.updateStatus(deviceId, Device.STATUS_BORROWED);
        return borrowRecord.getId();
    }

    public void returnDevice(String deviceId, JSONObject recordObj) {
        String returnerName = recordObj.getString("returnerName");
        Date timeReturned = new Date(recordObj.getLong("timeReturned"));
        //更新资源的状态为在库
        deviceRepository.updateStatus(deviceId, Device.STATUS_IN_STOCK);

        //更新借出记录
        BorrowRecord borrowRecord = borrowRecordRepository.findTopByDeviceIdOrderByCreatedTimeDesc(deviceId);
        if (borrowRecord != null) {
            borrowRecord.setReturnerName(returnerName);
            borrowRecord.setTimeReturned(timeReturned);
            borrowRecordRepository.save(borrowRecord);
        }
    }
}
