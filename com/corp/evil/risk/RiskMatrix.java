import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.corp.evil.RiskAlreadyRegisteredException;

public class RiskMatrix {
    private ArrayList<Risk> risks;

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
