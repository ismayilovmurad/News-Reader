package com.martiandeveloper.newsreader.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

// If we're using the another library (e.g FirebaseCrash),
// we have to use PowerMock and Mockito

// DateUtils is a static function, therefore we're
// using PowerMock instead of Mockito
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({FirebaseCrash.class})
public class DateUtilsTest {

    @Test
    public void formatNewsApiDate_correctDate_outputCorrectDate(){
        String correctInputDate1 = "2016-07-25T09:56:27Z";
        String outputDate = DateUtils.formatNewsApiDate(correctInputDate1);
        String correctOutputDate1 = "2016-07-25";
        assertEquals(outputDate, correctOutputDate1);
    }

    @SuppressWarnings({"SimplifiableJUnitAssertion", "ConstantConditions"})
    @Test
    public void formatNewsApiDate_nullInput_outputNull(){
        String outputDate = DateUtils.formatNewsApiDate(null);
        assertEquals(outputDate, null);
    }

    @Test
    public void formatNewsApiDate_incorrectInput_returnsSame(){
        String incorrectInputDate1 = "2016-07-25";
        String outputDate = DateUtils.formatNewsApiDate(incorrectInputDate1);
        assertEquals(outputDate, incorrectInputDate1);
    }

    /*@Test
    public void formatNewsApiDate_incorrectInput_returnsSame(){
        PowerMockito.mockStatic(FirebaseCrash.class);

        String outputDate = DateUtils.formatNewsApiDate(incorrectInputDate1);
        assertEquals(outputDate, incorrectInputDate1);
        PowerMockito.verifyStatic();
        FirebaseCrash.report(Matchers.isA(ParseException.class));
    }*/
}
