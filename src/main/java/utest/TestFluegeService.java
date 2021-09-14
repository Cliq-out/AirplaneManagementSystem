package utest;

import models.*;
import org.hibernate.query.Query;
import org.junit.Test;
import service.FluegeService;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestFluegeService {

    @Test
    public void flugGewinn() {

        int probeFlug = 4676;
        Double expResult =324.75;
        FluegeService service = new FluegeService();
        service.loadDataFluge();
        Fluge fluge = (Fluge)service.getElementFromList(1,probeFlug,"");
        Double result=service.gewinnFluege(fluge);
        assertEquals(expResult,result);
    }

    @Test
    public void testloadDataFluge(){
        int expResult=1;
        FluegeService service = new FluegeService();
        int result=service.loadDataFluge();
        assertEquals(expResult,result);
    }

}
