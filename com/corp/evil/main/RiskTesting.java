import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class RiskTesting {


    public static void main(String[] args) throws RiskProbabilityNotDefinedException, RiskImpactNotDefinedException, RiskAlreadyRegisteredException, RiskIsNullException {

        Risk r = new Risk("Test1", 2, 4);
        Risk r2 = new Risk("Test2", 4, 5);

        RiskMatrix rm = new RiskMatrix();
        rm.addRisk(r);
        rm.addRisk(r2);

        System.out.println(rm.toString());
        System.out.println(rm.toStringText());

        String filename = "matrix.json";

        try (Writer writer = new FileWriter(filename)){
            Gson gson = new GsonBuilder().create();
            gson.toJson(rm, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RiskMatrix rm2 = null;
        try (Reader reader = new FileReader(filename)) {
            Gson gson = new GsonBuilder().create();
            rm2 = gson.fromJson(reader, RiskMatrix.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(rm2.toString());
        System.out.println(rm2.toStringText());

        System.out.println(rm.equals(rm2));

        System.out.println(rm.getRisks().get(0).equals(rm2.getRisks().get(0)));
    }
}
