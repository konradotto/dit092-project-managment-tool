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

    public Project(String name, Team team, RiskMatrix riskMatrix, ProjectSchedule schedule) {
        this.setName(name);
        this.setTeam(team);
        this.setRiskMatrix(riskMatrix);
        this.setSchedule(schedule);
    }

    public String getBudget() {
        StringBuilder sb = new StringBuilder();

        sb.append("Earned Value: " + schedule.getEarnedValue() + LS);

        return sb.toString();
    }

    public double getTimeSpent(Member member) {
        return member.getTimeSpent();
    }

    public double getCostVariance() {
        return schedule.getCostVariance();
    }

    public double getScheduleVariance() {
        return schedule.getScheduleVariance();
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

    public boolean addActivity(String name, int startYear, int startWeek, int endWeek, int endYear, Team team) throws ActivityAlreadyRegisteredException, ActivityIsNullException {

        // make sure the members are in the team
        for (Member member : team.getMembers()) {
            if (!team.getMembers().contains(member)) {
                return false;
            }
        }

        schedule.addActivity(new Activity(name, startYear, startWeek, endWeek, endYear, team));
        return true;
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
