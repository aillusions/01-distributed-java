package com.zalizniak.sonarcodequality;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class JUnitTest extends TestCase {

    @Test
    public void testDemo() {
        Assert.assertEquals(1, 1);
    }

    @Test
    public void testBean() {
        Assert.assertNull(new SonarCodeQualityApplication().someBean());
    }
}
