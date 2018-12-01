import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.corp.evil.RiskAlreadyRegisteredException;

public class RiskMatrix {
    private ArrayList<Risk> risks;
    private String lineSeparator = System.lineSeparator();

    public RiskMatrix() {
        risks = new ArrayList<>();
    }

    public RiskMatrix(List<Risk> risks) throws RiskAlreadyRegisteredException, RiskIsNullException {
        this();
        addRisks(risks);
    }

    public RiskMatrix(String jsonText) {
        //TODO use gson
    }

    public RiskMatrix(File file) {
        this(RiskMatrix.fromJsonFile(file));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (risks.isEmpty()) {
            sb.append("There are no risks registered in this Risk Matrix yet." + lineSeparator);
        } else {
            sb.append("\tRisk Matrix" + lineSeparator + lineSeparator);

            sb.append("Risk name\tprobability\timpact\trisk" + lineSeparator);
            for (Risk risk : risks) {
                sb.append(risk.getRiskName() + "\t" + risk.getProbability() + "\t" + risk.getImpact() +
                        "\t" + risk.getRisk() + lineSeparator);
            }
        }
        return sb.toString();
    }

    // TODO: Constructor from JSON-file

    public void addRisk(Risk risk) throws RiskIsNullException, RiskAlreadyRegisteredException {
        if (risk == null) {
            throw new RiskIsNullException("RiskMatrix can not add NULL value to risks");
        } else if (risks.contains(risk)) {
            throw new RiskAlreadyRegisteredException("A risk with this name has already been registered. " +
                    "Change the name or delete the current risk of name <" + risk.getRiskName() + ">");
        } else {
        	this.risks.add(risk);
        }
    }

    public void removeRisk(Risk risk) throws RiskIsNullException {
        if (risk == null) {
            throw new RiskIsNullException("RiskMatrix can not remove NULL value from risks");
        } else {
            risks.remove(risk);
        }
    }

    public void addRisks(List<Risk> risks) throws RiskAlreadyRegisteredException, RiskIsNullException {
        for(Risk risk:risks) {
            addRisk(risk);
        }
    }

    public static String fromJsonFile(File file) {

        // TODO: read json file to String
        String jsonText = "TODO!!!";
        return jsonText;
    }
}
