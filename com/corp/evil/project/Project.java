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
    private ArrayList<Team> teams;

    public Project(String name, ProjectSchedule schedule) {
        this.setName(name);
        this.setSchedule(schedule);
        this.setTeam(new Team(name));
        this.setRiskMatrix(new RiskMatrix());
        this.teams = new ArrayList<>();
    }

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

    public boolean addActivity(String name, int startWeek, int startYear, int endWeek, int endYear, Team team) throws ActivityAlreadyRegisteredException, ActivityIsNullException {

        // make sure the members are in the team
        for (Member member : team.getMembers()) {
            if (!team.getMembers().contains(member)) {
                return false;
            }
        }

        schedule.addActivity(new Activity(name, startWeek, startYear, endWeek, endYear, team));
        return true;
    }

    public void addTeam(Team team) throws TeamAlreadyRegisteredException, TeamIsNullException {
        if (team == null) {
            throw new TeamIsNullException("This team does not exist!");
        } else if (teams.contains(team)) {
            throw new TeamAlreadyRegisteredException("A team with same name exists already!");
        } else {
            teams.add(team);
        }
    }

    public void addTeam(List<Team> teams) throws TeamAlreadyRegisteredException, TeamIsNullException {
        for (Team team : teams) {
            addTeam(team);
        }
    }

    public void removeTeam(Team team) throws TeamIsNullException {
        if (team == null) {
            throw new TeamIsNullException("This team does not exist!");
        }
        teams.remove(team);
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

    public void removeMember(Member member) {
        //TODO: remove the passed member
    }

    public ArrayList<Team> getTeams(){
        return this.teams;
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
