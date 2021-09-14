package utest;


import org.junit.Test;
import utils.DistanceUtil;


import static org.junit.Assert.assertEquals;


public class TestDistance {

    @Test
    public void verifyDistance() {

        int expected = 2238;//El Merah - Alborg2238

        double la1=7.8103;
        double lo1=36.8236;
        double la2=9.85606;
        double lo2=57.0952  ;

        DistanceUtil obj = new DistanceUtil();
        int distanceToTest = obj.distanceBetweenAirports(la1,lo1,la2,lo2);
        System.out.println("JUnit to test die AbstandBerechung");
        assertEquals(expected,distanceToTest);
    }

}
