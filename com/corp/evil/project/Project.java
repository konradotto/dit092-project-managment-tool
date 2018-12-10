import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class Project {

    private final static int MARGIN = 3;
    private final static String LS = System.lineSeparator();

    private String name;
    private Team team;
    private RiskMatrix riskMatrix;
    private ProjectSchedule schedule;
    private Budget budget;

    public Project(String name, Team team, RiskMatrix riskMatrix, ProjectSchedule schedule, Budget budget) {
        this.setName(name);
        this.setTeam(team);
        this.setRiskMatrix(riskMatrix);
        this.setSchedule(schedule);
        this.setBudget(budget);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.equals("")) {
            if (this.name == null) {
                this.name = "Unnamed Project " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_"));
            }
        } else {
            this.name = name;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.join("", Collections.nCopies(getName().length() + 2 * MARGIN, "=")) + LS);
        sb.append(String.join("", Collections.nCopies(MARGIN, " ")) + getName() + LS);
        sb.append(String.join("", Collections.nCopies(getName().length() + 2 * MARGIN, "=")) + LS + LS);

        sb.append(team);
        sb.append(LS + LS);

        sb.append(budget);
        sb.append(LS + LS);

        sb.append(schedule);
        sb.append(LS + LS);

        sb.append(riskMatrix);

        return sb.toString();
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public RiskMatrix getRiskMatrix() {
        return riskMatrix;
    }

    public void setRiskMatrix(RiskMatrix riskMatrix) {
        this.riskMatrix = riskMatrix;
    }

    public ProjectSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(ProjectSchedule schedule) {
        this.schedule = schedule;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
