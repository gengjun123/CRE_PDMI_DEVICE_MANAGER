package com.dayang.deviceManager.controller;

import com.dayang.deviceManager.entities.Device;
import com.dayang.deviceManager.service.DeviceService;
import com.dayang.deviceManager.util.JSONCheck;
import net.sf.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class DeviceController extends BaseController {

    @Resource
    private DeviceService deviceService;

    @PostMapping("/devices")
    public ResponseEntity<Object> saveDevice(@RequestBody String deviceStr) {

        JSONObject deviceObj = JSONObject.fromObject(deviceStr);
        String invalidKey = JSONCheck.ifAllExist(deviceObj,"name", "code");
        if (invalidKey != null) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("info", "参数" + invalidKey + "为必填项"));
        }

        String deviceId = deviceService.saveDevice(deviceObj);
        return ResponseEntity
                .ok()
                .body(Collections.singletonMap("deviceId", deviceId));
    }

    @GetMapping("/devices/{deviceId}")
    public ResponseEntity<Object> getDevice(@PathVariable String deviceId) {
        Device device = deviceService.getDevice(deviceId);
        if (device != null) {
            return ResponseEntity
                    .ok(device);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("deviceId:" + deviceId + "对应的设备不存在");
        }
    }

    @GetMapping("/devices")
    public ResponseEntity<Object> getDevices(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) String status,
                                             @RequestParam(required = false) String borrowerName,
                                             @RequestParam(name = "borrower", required = false) List<String> borrowerIdList,
                                             @RequestParam(required = false, defaultValue = "0") int start,
                                             @RequestParam(required = false, defaultValue = "10") int limit) {
        Map<String, Object> result = deviceService.getDevices(name, status, borrowerName, borrowerIdList, start, limit);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/devices/{deviceId}")
    public ResponseEntity<Object> updateDevice(@RequestBody String deviceStr, @PathVariable String deviceId) {
        JSONObject deviceObj = JSONObject.fromObject(deviceStr);
        String errorInfo = deviceService.updateDevice(deviceId, deviceObj);
        if (errorInfo == null) {
            return ResponseEntity
                    .ok()
                    .build();
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("info", errorInfo));
        }
    }

    @DeleteMapping("/devices/{deviceId}")
    public ResponseEntity<Object> deleteDevice(@PathVariable String deviceId) {
        deviceService.deleteDevice(deviceId);
        return ResponseEntity
                .ok()
                .build();
    }
}
