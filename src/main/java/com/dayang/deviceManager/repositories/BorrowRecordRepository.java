package com.dayang.deviceManager.repositories;

import com.dayang.deviceManager.entities.BorrowRecord;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface BorrowRecordRepository extends CrudRepository<BorrowRecord, String> {

    BorrowRecord findTopByDeviceIdOrderByCreatedTimeDesc(String deviceId);

    List<BorrowRecord> findByDeviceIdOrderByCreatedTimeDesc(String deviceId);
//
//    //更改借用记录的归还人和归还时间
//    @Query("update BorrowRecord r" +
//            " set r.returnerName = :returnerName, r.timeReturned = :timeReturned" +
//            " where id = :id")
//    void updateReturnerNameAndTimeReturned(String id, String returnerName, String timeReturned);
    @Modifying
    @Transactional
    int deleteByDeviceId(String deviceId);
}
