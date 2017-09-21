package com.dayang.deviceManager.controller;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeviceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void CrudTest() throws Exception{
        JSONObject device = new JSONObject();
        device.put("name", "Sony/索尼 FDR-X3000R 潜水运动摄像机");
        device.put("code", "KNHPIJ3488");
        device.put("description", "摄像机...");
        device.put("importTime", 1504512427148L);
        this.mockMvc.perform(post("/devices").content(device.toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
