package com.dayang.deviceManager.service;

import com.dayang.deviceManager.entities.BorrowRecord;
import com.dayang.deviceManager.entities.Device;
import com.dayang.deviceManager.repositories.BorrowRecordRepository;
import com.dayang.deviceManager.repositories.DeviceRepository;
import com.dayang.deviceManager.repositories.DynamicQuery;
import com.dayang.deviceManager.util.UUID;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.TypedQuery;
import java.util.*;

@Service
public class DeviceService {

    @Resource
    private DeviceRepository deviceRepository;

    @Resource
    private BorrowRecordRepository borrowRecordRepository;

    @Resource
    private DynamicQuery dynamicQuery;

    public String saveDevice(JSONObject deviceObj) {
        Device device = new Device();
        device.setId(UUID.generate());
        device.setCreatedTime(new Date());
        device.setName(deviceObj.getString("name"));
        device.setCode(deviceObj.getString("code"));
        device.setStatus(Device.STATUS_IN_STOCK);
        if (deviceObj.containsKey("description")) {
            device.setDescription(deviceObj.getString("description"));
        }
        if (deviceObj.containsKey("importTime")) {
            device.setImportTime(new Date(deviceObj.getLong("importTime")));
        }
        deviceRepository.save(device);
        return device.getId();
    }

    public String updateDevice(String deviceId, JSONObject deviceObj) {
        // TODO: 2017/9/5 效率低
        Device device = deviceRepository.findOne(deviceId);
        if (device == null) {
            return "该id:" + deviceId + "对应的设备不存在";
        }

        if (deviceObj.containsKey("status")) {
            device.setStatus(deviceObj.getString("status"));
        }
        if (deviceObj.containsKey("name")) {
            device.setName(deviceObj.getString("name"));
        }
        if (deviceObj.containsKey("code")) {
            device.setCode(deviceObj.getString("code"));
        }
        if (deviceObj.containsKey("description")) {
            device.setDescription(deviceObj.getString("description"));
        }
        deviceRepository.save(device);
        return null;
    }

    public Device getDevice(String deviceId) {
        Device device = deviceRepository.findOne(deviceId);
        if (device != null) {
            List<BorrowRecord> borrowRecordList = borrowRecordRepository.findByDeviceIdOrderByCreatedTimeDesc(deviceId);
            device.setBorrowRecordList(borrowRecordList);
        }
        return device;
    }

    public Map<String, Object> getDevices(String name, String status, String borrowerName, String borrowId, int start, int limit) {
        Map<String, Object> params = new HashMap<>();
        String hql = " from Device device";
        if (StringUtils.isNotBlank(borrowerName) || StringUtils.isNotBlank(borrowId)) {
            hql += ", BorrowRecord record";
        }

        hql += " where 1 = 1";

        if (StringUtils.isNotBlank(name)) {
            hql += " and device.name like :name";
            params.put("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(status)) {
            hql += " and device.status = :status";
            params.put("status", status);
        }
        if (StringUtils.isNotBlank(borrowerName)) {
            hql += " and record.borrowerName like :borrowName";
            params.put("borrowName", "%" + borrowerName + "%");
        }
        if (StringUtils.isNotBlank(borrowId)) {
            hql += " and record.borrowId = :borrowId";
            params.put("borrowId", borrowId);
        }

        //查出设备记录
        TypedQuery<Device> recordQuery = dynamicQuery.executeHql("select distinct device" +
                hql + " order by device.createdTime desc", Device.class);
        recordQuery.setFirstResult(start);
        recordQuery.setMaxResults(limit);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            recordQuery.setParameter(entry.getKey(), entry.getValue());
        }
        List<Device> deviceList = recordQuery.getResultList();

        //查询出总数
        TypedQuery<Long> countQuery = dynamicQuery.executeHql("select count(device.id)" + hql, Long.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
        Long total = countQuery.getSingleResult();
        Map<String, Object> result = new HashMap<>();

        //组装数据
        for (Device device: deviceList) {
            //对于已经借出的设备，添加最新借出记录
            if (Device.STATUS_BORROWED.equals(device.getStatus())) {
                BorrowRecord borrowRecord = borrowRecordRepository.findTopByDeviceIdOrderByCreatedTimeDesc(device.getId());
                device.setLatestBorrowRecord(borrowRecord);
            }
        }
        result.put("count", deviceList.size());
        result.put("total", total);
        result.put("deviceList", deviceList);
        return result;
    }

    public void deleteDevice(String deviceId) {
        //先删除与设备关联的借出记录
        borrowRecordRepository.deleteByDeviceId(deviceId);
        //删除设备
        deviceRepository.delete(deviceId);
    }
}