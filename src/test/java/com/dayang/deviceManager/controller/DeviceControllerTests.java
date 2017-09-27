package com.dayang.deviceManager.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeviceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void CrudTest() throws Exception{

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

        //查找设备
        String content = result.getResponse().getContentAsString();
        JSONObject contentObj = JSONObject.fromObject(content);
        String deviceId = contentObj.getString("deviceId");
        this.mockMvc.perform(get("/devices/" + deviceId ))
                .andDo(print())
                .andExpect(status().isOk());

        //批量查找
        this.mockMvc.perform(get("/devices")
                .requestAttr("name", "Sony/索尼 FDR-X3000R 潜水运动摄像机")
                .requestAttr("borrowerName", "张三")
                .requestAttr("borrowerId", "ajsdkfjalfdjsalfqof")
                .requestAttr("borrowerId", "sdfdkfurif")
                .requestAttr("status", "IN_STOCK")
            )
                .andDo(print())
                .andExpect(status().isOk());

        //更改
        device = new JSONObject();
        device.put("name", "Sony/索尼 FDR-X3000R 潜水运动摄像机123");
        device.put("code", "KNHPIJ3488123");
        device.put("description", "摄像机...132");
        this.mockMvc.perform(put("/devices/" + deviceId).content(device.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        //设备删除
        this.mockMvc.perform(delete("/devices/" + deviceId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
