package com.zalizniak.sonarcodequality;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SonarCodeQualityApplicationTests {

    @Autowired
    private String someBean;

    @Test
    public void contextLoads() {
        Assert.assertNull(someBean);
    }

}
