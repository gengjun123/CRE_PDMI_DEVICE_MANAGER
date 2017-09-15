package com.dayang.deviceManager.controller;


import com.dayang.deviceManager.service.BorrowRecordService;
import com.dayang.deviceManager.util.JSONCheck;
import net.sf.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;

@RestController
@CrossOrigin
public class BorrowRecordController extends BaseController {

    @Resource
    private BorrowRecordService borrowRecordService;

    @PostMapping("/devices/{deviceId}/borrowRecords")
    public ResponseEntity saveBorrowRecord(@RequestBody String recordStr,
                                           @PathVariable String deviceId) {
        JSONObject recordObj = JSONObject.fromObject(recordStr);
        String invalidParam = JSONCheck.ifAllExist(recordObj, "borrowerName", "borrowTime");
        if (invalidParam != null) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("info", "参数" + invalidParam + "为必填项"));
        } else {
            String recordId = borrowRecordService.saveRecord(deviceId, recordObj);
            return ResponseEntity
                    .ok()
                    .body(Collections.singletonMap("recordId", recordId));
        }
    }

    @PutMapping("/devices/{deviceId}/borrowRecords")
    public ResponseEntity returnDevice(@RequestBody String recordStr,
                                       @PathVariable String deviceId) {
        JSONObject recordObj = JSONObject.fromObject(recordStr);
        String invalidParam = JSONCheck.ifAllExist(recordObj, "returnerName", "timeReturned");
        if (invalidParam != null) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("info", "参数" + invalidParam + "为必填项"));
        } else {
            borrowRecordService.returnDevice(deviceId, recordObj);
            return ResponseEntity
                    .ok()
                    .build();
        }
    }
}
