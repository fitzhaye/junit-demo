package org.lokvin.junit;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.matchers.JUnitMatchers.hasItem;

public class HamcrestTest {
    private List<String> values;
    
    @Before
    public void setUpList() {
        values = new ArrayList<String>();
        values.add("x");
        values.add("y");
        values.add("z");
    }
    
    @Test
    public void testWithoutHamcrest() {
        assertTrue("it should be true", values.contains("x") 
                || values.contains("two") 
                || values.contains("three"));
    }
    
    @Test
    public void testWithHamcrest() {
        assertThat("should has item", values, 
                hasItem(anyOf(
                        equalTo("x"), 
                        equalTo("two"), 
                        equalTo("three"))));
    }
}
