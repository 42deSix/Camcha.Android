package com.softmilktea.camcha;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        System.out.println(((float)BaseApplication.SCREEN_WIDTH / (float)BaseApplication.SCREEN_HEIGHT));
        double height = 2560.0;
        double width = 1532.0;
        assertEquals(4, 2 + 2);
//        Dlog.d(((float)BaseApplication.SCREEN_WIDTH / (float)BaseApplication.SCREEN_HEIGHT)+"");
        assertNotEquals(0.0, ((float)width / (float)height));
    }
}