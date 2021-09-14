package utest;

import models.Fluge;
import org.junit.Test;
import service.FluegeService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestCalculateDataBuchung {

    @Test
    public void buchungAbrufen() {
        int probeFlug = 4676;
        List<Integer> expResult = new ArrayList<Integer>();
        expResult.add(7);
        expResult.add(4);
        FluegeService service = new FluegeService();
        service.loadDataFluge();
        Fluge fluge = (Fluge)service.getElementFromList(1,probeFlug,"");
        List <Integer> result=service.getDataBuchungen(fluge);
        assertEquals(expResult,result);
    }

}
