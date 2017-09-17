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

    @Test
    public void allDaysIsButAllTimeIsEmpty() throws Exception {
        String expected = Constants.ежедневно;
        Assert.assertEquals(expected, Main.getScheduleString(JsonStrings.JSON4));
    }

    //если дня нет то два интервал должен быть, а не "ежедневно"
    @Test
    public void notAllDaysIsButAllTimeIsEmpty_notPrintDaily() throws Exception {
        String notExpected = Constants.ежедневно;
        Assert.assertNotEquals(notExpected, Main.getScheduleString(JsonStrings.JSON5));
    }




    @Test
    public void oneTimeForAllDays() throws Exception {
        String expected = Constants.ежедневно + " " + "09:00-23:00";
        Assert.assertEquals(expected, Main.getScheduleString(JsonStrings.JSON1));
    }





    @Test
    public void notAllDaysIsButAllTimeIsEmpty_truePrint() throws Exception {
        String expected = "пн-сб";
        Assert.assertEquals(expected, Main.getScheduleString(JsonStrings.JSON5));
    }

    @Test
    public void notAllDaysIsButAllTimeIsEmpty_truePrint2() throws Exception {
        String expected = "пн-чт, сб-вс";
        Assert.assertEquals(expected, Main.getScheduleString(JsonStrings.JSON6));
    }




}