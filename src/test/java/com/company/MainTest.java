package com.company;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class MainTest {

    Locale locale = Locale.getDefault();

    @Test
    public void emptyJson() throws Exception {
        String expected = Constants.ежедневно;
        Assert.assertEquals(expected, Main.getScheduleString(locale, JsonStrings.JSON2));
    }

    @Test
    public void allDaysIsButAllTimeIsEmpty() throws Exception {
        String expected = Constants.ежедневно;
        Assert.assertEquals(expected, Main.getScheduleString(locale, JsonStrings.JSON4));
    }

    //если дня нет то два интервал должен быть, а не "ежедневно"
    @Test
    public void notAllDaysIsButAllTimeIsEmpty_notPrintDaily() throws Exception {
        String notExpected = Constants.ежедневно;
        Assert.assertNotEquals(notExpected, Main.getScheduleString(locale, JsonStrings.JSON5));
    }




    @Test
    public void oneTimeForAllDays() throws Exception {
        String expected = Constants.ежедневно + " " + "09:00-23:00";
        Assert.assertEquals(expected, Main.getScheduleString(locale, JsonStrings.JSON1));
    }





    @Test
    public void notAllDaysIsButAllTimeIsEmpty_truePrint() throws Exception {
        String expected = "пн-сб";
        Assert.assertEquals(expected, Main.getScheduleString(locale, JsonStrings.JSON5));
    }

    @Test
    public void notAllDaysIsButAllTimeIsEmpty_truePrint2() throws Exception {
        String expected = "пн-чт, сб-вс";
        Assert.assertEquals(expected, Main.getScheduleString(locale, JsonStrings.JSON6));
    }

    @Test
    public void notAllDaysAndSpecificTime() throws Exception {
        String expected = "пн 09:00-23:00, 19:00-23:00, вт-ср, пт-сб 09:00-23:00, вс 05:00-23:00";
        Assert.assertEquals(expected, Main.getScheduleString(locale, JsonStrings.JSON3));
    }


    //US Locale

    @Test
    public void notAllDaysIsButAllTimeIsEmpty_truePrint_anotherLocale() throws Exception {
        String expected = "sun-thu, sat";
        Assert.assertEquals(expected, Main.getScheduleString(Locale.US, JsonStrings.JSON6));
    }

    @Test
    public void notAllDaysAndSpecificTime_anotherLocale() throws Exception {
        String expected = "sun 05:00-23:00, mon 09:00-23:00, 19:00-23:00, tue-wed, fri-sat 09:00-23:00";
        Assert.assertEquals(expected, Main.getScheduleString(Locale.US, JsonStrings.JSON3));
    }
}