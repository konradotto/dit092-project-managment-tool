import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RiskTest {

    @Test
    public void testGetRisk() throws RiskProbabilityNotDefinedException, RiskImpactNotDefinedException, RiskAlreadyRegisteredException, RiskIsNullException {
        Risk r = new Risk("Test1", 2, 4);
        int expected = r.getProbability().getProbability() * r.getImpact().getImpact();
        int actual = r.getRisk();
        assertEquals("Risk should be the product of probability and impact.", expected, actual);

        RiskMatrix rm = new RiskMatrix();
        rm.addRisk(r);

        System.out.print(r.toString());
    }
}