package jcchen.goodsmanager.entity;

import org.junit.Test;

import static org.junit.Assert.*;


public class EntityUnitTest {

    @Test
    public void DateInfo_decode() throws Exception {
        assertEquals(DateInfo.decode("BJ"), 811);
        assertEquals(DateInfo.decode("KT"), 910);
        assertEquals(DateInfo.decode("RTE"), 21001);

        assertEquals(DateInfo.decode(""), Integer.MAX_VALUE);
        assertEquals(DateInfo.decode("RTEE"), Integer.MAX_VALUE);
        assertEquals(DateInfo.decode("RTZ"), Integer.MAX_VALUE);
        assertEquals(DateInfo.decode("ZZZ"), Integer.MAX_VALUE);
        assertEquals(DateInfo.decode("123456"), Integer.MAX_VALUE);
    }
}
