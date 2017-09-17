package com.company;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {


    @Test
    public void emptyJson() throws Exception {
        String expected = Constants.ежедневно;
        Assert.assertEquals(expected, Main.getScheduleString(JsonStrings.JSON2));
    }


    //ошибка если не все дни! если дня нет то два интервала должно быть!!!
    @Test
    public void allDaysIsButAllTimeIsEmpty() throws Exception {
        String expected = Constants.ежедневно;
        Assert.assertEquals(expected, Main.getScheduleString(JsonStrings.JSON4));
    }


    //ошибка если не все дни! если дня нет то два интервала должно быть!!!
    @Test
    public void notAllDaysIsButAllTimeIsEmpty() throws Exception {
        String notExpected = Constants.ежедневно;
        Assert.assertNotEquals(notExpected, Main.getScheduleString(JsonStrings.JSON5));
    }


    @Test
    public void oneTimeForAllDays() throws Exception {
        String expected = Constants.ежедневно + " " + "09:00-23:00";
        Assert.assertEquals(expected, Main.getScheduleString(JsonStrings.JSON1));
    }

}