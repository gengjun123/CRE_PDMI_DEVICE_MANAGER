package com.dayang.deviceManager.controller;


import com.dayang.deviceManager.entities.Device;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BorrowRecordControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void crud() throws Exception{

        //添加设备
        JSONObject device = new JSONObject();
        device.put("name", "Sony/索尼 FDR-X3000R 潜水运动摄像机");
        device.put("code", "KNHPIJ3488");
        device.put("description", "摄像机...");
        device.put("importTime", 1504512427148L);
        MvcResult result = this.mockMvc.perform(post("/devices").content(device.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //设备借出
        String content = result.getResponse().getContentAsString();
        JSONObject contentObj = JSONObject.fromObject(content);
        String deviceId = contentObj.getString("deviceId");
        JSONObject borrow = new JSONObject();
        borrow.put("borrowerId", "239hihw348fhgw938h9gwhtrp9");
        borrow.put("borrowerName", "张女士");
        borrow.put("borrowerPhone", "1234");
        borrow.put("borrowTime", 1504515765940L);
        borrow.put("timeToReturn", 1504515767940L);
        System.out.println("设备借出");
        this.mockMvc.perform(post("/devices/" + deviceId + "/borrowRecords").content(borrow.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/devices/" + deviceId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Device.STATUS_BORROWED));

        //设备归还
        borrow = new JSONObject();
        borrow.put("returnerName", "张女士");
        borrow.put("timeReturned", 1504515765940L);
        this.mockMvc.perform(put("/devices/" + deviceId + "/borrowRecords").content(borrow.toString()))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/devices/" + deviceId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Device.STATUS_IN_STOCK));

        //删除设备
        this.mockMvc.perform(delete("/devices/" + deviceId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
