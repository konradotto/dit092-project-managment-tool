import javax.swing.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Project {

    // constants
    private static final int MARGIN = 3;
    private static final String LS = System.lineSeparator();

    // member variables
    private String name;
    private Team team;
    private RiskMatrix riskMatrix;
    private ProjectSchedule schedule;
    private ArrayList<Team> teams;  //TODO: decide whether we really need this...

    private File file;

    /**
     * Almost empty constructor taking only a name and a schedule as parameters.
     * This allows to create projects which only have a name and a startWeek
     * (+potentially already a set of activities which are pending execution).
     * This way you can first do approximate planning of the demand within a project before
     * assigning a team that is suited to it. The risks and further activities can easily
     * be added after creation.
     *
     * @param name     String specifying the project name
     * @param schedule ProjectSchedule defining at least a preliminary start year and week as well as
     *                 end year and week
     */
    public Project(String name, ProjectSchedule schedule) {
        this.setName(name);
        this.setSchedule(schedule);
        this.setTeam(new Team(name));
        this.setRiskMatrix(new RiskMatrix());
        this.teams = new ArrayList<>();
        onChange();         // save all changes
    }


    /**
     * Constructor for a project where the team, risk matrix and project schedule are already
     * worked out to a certain degree.
     *
     * @param name       String specifying the project name
     * @param team       Team of people working on the project. Can be extended to the needs.
     *                   Only these people should can be used for working on activities
     * @param riskMatrix RiskMatrix with risks for this project
     * @param schedule   ProjectSchedule defining the temporal project outlines
     */
    public Project(String name, Team team, RiskMatrix riskMatrix, ProjectSchedule schedule) {
        this.setName(name);
        this.setTeam(team);
        this.setRiskMatrix(riskMatrix);
        this.setSchedule(schedule);

        onChange();
    }

    public void addMember(Member member) throws MemberAlreadyRegisteredException, MemberIsNullException {
        this.team.addMember(member);
        onChange();
    }

    public boolean addActivity(String name, int startWeek, int startYear, int endWeek, int endYear, Team team) throws ActivityAlreadyRegisteredException, ActivityIsNullException {

        // make sure the members are in the team
        for (Member member : team.getMembers()) {
            if (!team.getMembers().contains(member)) {
                return false;
            }
        }

        schedule.addActivity(new Activity(name, startWeek, startYear, endWeek, endYear, team));
        onChange();
        return true;
    }

    public void addTeam(Team team) throws TeamAlreadyRegisteredException, TeamIsNullException {
        if (team == null) {
            throw new TeamIsNullException("This team does not exist!");
        } else if ( (teams != null) &&  (teams.contains(team))) {
            throw new TeamAlreadyRegisteredException("A team with same name exists already!");
        } else {
            teams.add(team);
        }
        onChange();
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
        onChange();
    }

    /**
     * Function specifying what to do when the project data are changed
     * (e.g. save the project under the current path)
     */
    public void onChange() {
        saveProject();
    }

    /**
     * Function to save the project either to the associated file or to a file being newly picked
     * if no valid file is set for the project.
     *
     * @return returns whether the saving was successful
     */
    public boolean saveProject() {
        while (file == null) {
            setFile();
        }

        return JsonReaderWriter.save(this, file);
    }

    /**
     * Set the file the project is supposed to be saved to.
     *
     * @return success of setting the file.
     */
    public boolean setFile() {
        this.file = JsonReaderWriter.pickFile();

        return (this.file != null);
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

        sb.append(riskMatrix.toStringText());

        return sb.toString();
    }

    public void removeMember(Member member) {
        //TODO: remove the passed member

        onChange();
    }

    public String getBudget() {
        //TODO: is this all we want here?
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

    public void setName(String passedName) {
        if (passedName == null || passedName.trim().equals("")) {       // make sure to only use the passedName if it has a useful value
            // set name based on the current date if passedName and current this.name are useless
            if (this.name == null) {
                this.name = "Unnamed Project " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        } else {
            this.name = passedName;
        }
        onChange();
    }

    public ArrayList<Team> getTeams() {
        return this.teams;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        onChange();
    }

    public RiskMatrix getRiskMatrix() {
        return riskMatrix;
    }

    public void setRiskMatrix(RiskMatrix riskMatrix) {
        this.riskMatrix = riskMatrix;
        onChange();
    }

    public ProjectSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(ProjectSchedule schedule) {
        this.schedule = schedule;
        onChange();
    }

    /**
     * Method to test whether a project actually is a valid project.
     *
     * @return
     */
    public boolean isProject() {
        boolean isProject = true;

        try {
            getName().length();
        } catch (Exception e) {
            isProject = false;
        }
        return isProject;
    }


    public void offerFileChange() {
        int changeFile = JOptionPane.showConfirmDialog(null, "Do you want to change the save-path?",
                "Warning", JOptionPane.YES_NO_OPTION);
        if (changeFile == JOptionPane.YES_OPTION) {
            setFile();
        }
    }

    public Member retrieveMember(String name) {
        return team.retrieveMember(name);
    }

    public Member retrieveMember(int index) {
        return team.retrieveMember(index);
    }

}
