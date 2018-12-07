import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RiskMatrix {
    private final static int COLUMN_WIDTH = 15;
    private final static int COLUMNS = 5;

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
    	return formatTable(true);
    }
    
    public String toStringText() {
    	return formatTable(false);
    }
    
    public String formatTable(boolean numeric) {
        StringBuilder sb = new StringBuilder();

        if (risks.isEmpty()) {
            sb.append("There are no risks registered in this Risk Matrix yet." + lineSeparator);
        } else {
            
            sb.append("\t\t\tRisk Matrix" + lineSeparator);

            sb.append(String.join("", Collections.nCopies((COLUMNS-1) * COLUMN_WIDTH +1, "-")));
            sb.append(lineSeparator);
            
            // format table content
            sb.append(formatTableRow(new String[] {"| Risk name:", "| Probability:", "| Impact:", "| Risk:", "|"}));

            // separator line

            sb.append(String.join("", Collections.nCopies((COLUMNS-1) * COLUMN_WIDTH +1, "-")));
            sb.append(lineSeparator);

            for (Risk risk : risks) {
                sb.append(formatTableRow(new String[] {"| " + risk.getRiskName(),
                		"| " + String.valueOf(numeric ? risk.getProbability().getProbability() : risk.getProbability().getText()),
                		"| " + String.valueOf(numeric ? risk.getImpact().getImpact() : risk.getImpact().getText()),
                		"| " + String.valueOf(numeric ? risk.getRisk() : risk.getRiskString()), "|"}));

                sb.append(String.join("", Collections.nCopies((COLUMNS-1) * COLUMN_WIDTH +1, "-")));
                sb.append(lineSeparator);
            }
        }
        //test
        return sb.toString();
    }
    
    
    private String formatTableRow(String[] columns) {
        String result = "";

        for(int i = 0; i < COLUMNS - 1; ++i) {
            result += String.format("%1$-" + COLUMN_WIDTH + "s", columns[i]);
        }
        result += String.format(columns[4] + "%n");

        return result;
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
        for (Risk risk : risks) {
            addRisk(risk);
        }
    }

    public static String fromJsonFile(File file) {

        // TODO: read json file to String
        String jsonText = "TODO!!!";
        return jsonText;
    }
}
