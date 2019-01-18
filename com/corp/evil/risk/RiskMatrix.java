import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RiskMatrix {
    private final static int COLUMN_WIDTH = 15;
    private final static int COLUMNS = 5;
    private final static int SPACES = 2;
    private int longestRisk = 0;

    private ArrayList<Risk> risks;
    private String lineSeparator = System.lineSeparator();

    public RiskMatrix() {
        risks = new ArrayList<>();
    }

    public RiskMatrix(List<Risk> risks) throws RiskAlreadyRegisteredException, RiskIsNullException {
        this();
        addRisks(risks);
    }

    @Override
    public String toString() {
        if (ConsoleProgram.useAscii()) {
            return toStringAscii();
        }
        return toStringText();
    }

    public String toStringText() {
        return formatTable(false);
    }

    public String toStringAscii() {
        StringBuilder sb = new StringBuilder();

        String headline = "Risk Matrix";
        String columnHeader = "Description";
        String numbers = " 1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25";
        int maxNameLength = columnHeader.length();


        // check for maximum length of
        for (Risk risk : risks) {
            if (risk.getRiskName().length() > maxNameLength) {
                maxNameLength = risk.getRiskName().length();
            }
        }

        // full table header
        sb.append(Print.stretchString("", (maxNameLength + SPACES + numbers.length() - headline.length()) / 2, ' ')
                + headline + Print.LS);
        sb.append(Print.stretchString("", (maxNameLength + 2 * SPACES + numbers.length()), '.') + Print.LS);
        sb.append(Print.stretchString("", maxNameLength + SPACES, ' ') + numbers + Print.LS);
        sb.append(columnHeader + Print.LS);

        for (Risk risk : risks) {
            sb.append(Print.stretchString(risk.getRiskName(), maxNameLength + SPACES, ' '));
            for (int i = 0; i < risk.getRisk(); i++) {
                sb.append("+++");
            }
            sb.append(Print.LS);
        }

        return sb.toString();
    }

    public String formatTable(boolean numeric) {
        StringBuilder sb = new StringBuilder();

        if (risks.isEmpty()) {
            sb.append("There are no risks registered in this Risk Matrix yet." + lineSeparator);
        } else {

            sb.append("\t\t\tRisk Matrix" + lineSeparator);


            longestRisk = "| Risk name: ".length();
            for (Risk risk : risks) {
                if (risk.getRiskName().length() > longestRisk) {
                    longestRisk = risk.getRiskName().length();
                }
            }

            sb.append(String.join("", Collections.nCopies((COLUMNS - 2) * COLUMN_WIDTH + longestRisk + 5, "-")));
            sb.append(lineSeparator);

            // format table content
            sb.append(formatTableRow(new String[]{"| Risk name:", "| Probability:", "| Impact:", "| Risk:", "|"}));

            // separator line

            sb.append(String.join("", Collections.nCopies((COLUMNS - 2) * COLUMN_WIDTH + longestRisk + 5, "-")));
            sb.append(lineSeparator);

            for (Risk risk : risks) {
                sb.append(formatTableRow(new String[]{"| " + risk.getRiskName(),
                        "| " + String.valueOf(numeric ? risk.getProbability().getProbability() : risk.getProbability().getText()),
                        "| " + String.valueOf(numeric ? risk.getImpact().getImpact() : risk.getImpact().getText()),
                        "| " + String.valueOf(numeric ? risk.getRisk() : risk.getRiskString()), "|"}));

                sb.append(String.join("", Collections.nCopies((COLUMNS - 2) * COLUMN_WIDTH + longestRisk + 5, "-")));
                sb.append(lineSeparator);
            }
        }
        //test
        return sb.toString();
    }


    private String formatTableRow(String[] columns) {
        String result = "";


        for (int i = 0; i < COLUMNS - 1; ++i) {
            if (i == 0) {
                result += String.format("%1$-" + (longestRisk + 4) + "s", columns[i]);
            } else {
                result += String.format("%1$-" + COLUMN_WIDTH + "s", columns[i]);
            }
        }
        result += String.format(columns[4] + "%n");

        return result;
    }

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

    public ArrayList<Risk> getRisks() {
        return risks;
    }
}