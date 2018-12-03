public class RiskTesting {


    public static void main(String[] args) throws RiskProbabilityNotDefinedException, RiskImpactNotDefinedException, RiskAlreadyRegisteredException, RiskIsNullException {

        Risk r = new Risk("Test1", 2, 4);

        RiskMatrix rm = new RiskMatrix();
        rm.addRisk(r);

        System.out.println(rm.toString());
        System.out.println(rm.toStringText());
    }
}
