import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Project {

    private final static int MARGIN = 3;
    private final static String LS = System.lineSeparator();

    private String name;
    private Team team;
    private RiskMatrix riskMatrix;
    private ProjectSchedule schedule;

    public Project(String name, Team team, RiskMatrix riskMatrix, ProjectSchedule schedule) {
        this.setName(name);
        this.setTeam(team);
        this.setRiskMatrix(riskMatrix);
        this.setSchedule(schedule);
    }

    public Budget getBudget() {
        List<Budget> budgetList = new ArrayList<Budget>();
        for (Activity task : schedule.getActivities()) {
            budgetList.add(task.getBudget());
        }

        return new Budget(budgetList);
    }

    //TODO: implement (in budget)
    public double getEarnedValue() {

        return getBudget().getEarnedValue();
    }

    //TODO: implement (in Schedule)
    public double getTimeSpent(Member member) {

        return 0.0;
    }

    //TODO: implement
    public double getCostVariance() {


        return 0.0;
    }

    //TODO: implement
    public double getScheduleVariance() {

        return 0.0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.equals("")) {
            if (this.name == null) {
                this.name = "Unnamed Project " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        } else {
            this.name = name;
        }
    }

    public void addMember(Member member) throws MemberAlreadyRegisteredException, MemberIsNullException {
        this.team.addMember(member);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.join("", Collections.nCopies(getName().length() + 2 * MARGIN, "=")) + LS);
        sb.append(String.join("", Collections.nCopies(MARGIN, " ")) + getName() + LS);
        sb.append(String.join("", Collections.nCopies(getName().length() + 2 * MARGIN, "=")) + LS + LS);

        sb.append(team);
        sb.append(LS + LS);

        sb.append(getBudget());
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
}
