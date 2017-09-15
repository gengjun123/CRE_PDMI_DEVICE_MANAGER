package com.dayang.deviceManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceManagerApplicationTests {

	@Test
	public void contextLoads() {
        Assert.assertEquals("hello", join(" or ", Arrays.asList("hello")));
    }

    public String join(String delimiter, Collection collection) {
        StringBuilder stringBuilder = new StringBuilder();

        int cnt = 0;
        for (Object item : collection) {
            stringBuilder.append(item);
            if(++cnt < collection.size()) {
                stringBuilder.append(delimiter);
            }
        }

        return stringBuilder.toString();
    }
}
