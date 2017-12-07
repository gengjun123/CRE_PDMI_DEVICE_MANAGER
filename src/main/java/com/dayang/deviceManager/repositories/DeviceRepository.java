package com.dayang.deviceManager.repositories;


import com.dayang.deviceManager.entities.Device;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface DeviceRepository extends CrudRepository<Device, String>{

//    //根据设备名称查询
//    Page<Device> findByNameContaining(String name, Pageable pageable);
//
//    //根据借用人名称查询
//    @Query("select distinct device from Device device, BorrowRecord record" +
//            " where device.id = record.deviceId" +
//            " and record.borrowerName like %:borrowerName%")
//    Page<Device> findByBorrowerName(@Param("borrowerName") String borrowerName, Pageable pageable);
//
//    //全部查询
//    Page<Device> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Device d" +
            " set d.status = :status" +
            " where d.id = :id")
    int updateStatus(@Param("id") String id, @Param("status") String status);

    @Modifying
    @Transactional
    @Query("update Device d" +
            " set d.latestBorrowerId = :latestBorrowerId" +
            " where d.id = :id")
    int updateLatestBorrowerId(@Param("id") String id, @Param("latestBorrowerId") String latestBorrowerId);
}