import javax.swing.*;
import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Project {

    // constants
    public static final int MARGIN = 3;
    private static final String LS = System.lineSeparator();
    public static final String CURRENCY = "SEK";

    // member variables
    private String name;
    private Team team;
    private RiskMatrix riskMatrix;
    private ProjectSchedule schedule;
    ArrayList<Team> teams;  //TODO: decide whether we really need this...
    private YearWeek currentWeek;
    private DayOfWeek lastWeekday;

    private File file;

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
        this.name = name;
        this.setTeam(team);
        this.setRiskMatrix(riskMatrix);
        this.setSchedule(schedule);
        this.teams = new ArrayList<>();

        Calendar now = new GregorianCalendar();
        this.currentWeek = new YearWeek(now.get(Calendar.YEAR), now.get(Calendar.WEEK_OF_YEAR));
        this.lastWeekday = DayOfWeek.of((now.get(Calendar.DAY_OF_WEEK) - 1) % 7);

        onChange();             // save all changes
    }

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
    public Project(String name, ProjectSchedule schedule) throws NameIsEmptyException {
        this(name, new Team(name), new RiskMatrix(), schedule);
        onChange();
    }

    public void addMember(Member member) throws MemberAlreadyRegisteredException, MemberIsNullException {
        this.team.addMember(member);
    }

    public void addActivity(Activity activity) {
        try {
            schedule.addActivity(activity);
            onChange();
        } catch (ActivityAlreadyRegisteredException | ActivityIsNullException | IllegalArgumentException e) {
            Print.println(e.getMessage() + Print.LS);
        }
    }

    //TODO: what is this doing?
    public boolean addActivity(String name, TimePeriod timePeriod, Team team) throws ActivityAlreadyRegisteredException, ActivityIsNullException {

        // make sure the members are in the team
        for (Member member : team.getMembers()) {
            if (!team.getMembers().contains(member)) {
                return false;
            }
        }

        schedule.addActivity(new Activity(name, timePeriod, team));
        onChange();
        return true;
    }

    public void removeActivity(Activity activity) {
        if (activity == null) {
            return;
        }

    }

    public void addTeam(Team team) throws TeamAlreadyRegisteredException, TeamIsNullException {
        if (team == null) {
            throw new TeamIsNullException("This team does not exist!");
        } else if ((teams != null) && (teams.contains(team))) {
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
        onChange();
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

    /**
     * After loading it can happen that copies of objects are saved instead of copies.
     * This leads to divergence when editing a particular instance.
     * This function solves that issue.
     */
    public void solveObjectCopies() {
        solveCopiesInProject();         // make sure teams references the members from team
        schedule.solveCopies(team);     // make sure every activity references the members from team
    }

    public void solveCopiesInProject() {
        for (Team smallTeam : teams) {
            smallTeam.replaceMembers(team);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.join("", Collections.nCopies(getTitle().length() + 2 * MARGIN, "=")) + LS);
        sb.append(String.join("", Collections.nCopies(MARGIN, " ")) + getTitle() + LS);
        sb.append(String.join("", Collections.nCopies(getTitle().length() + 2 * MARGIN, "=")) + LS + LS);

        sb.append(getTeamString());
        sb.append(LS + LS);

        sb.append(getBudgetString());
        sb.append(LS + LS);

        sb.append(schedule);
        sb.append(LS + LS);

        sb.append(riskMatrix.toStringText());

        return sb.toString();
    }

    public String weeksString() {
        return String.format("   (From week %d, %d to week %d, %d)", schedule.getStartWeek(),
                schedule.getTimePeriod().getStart().getYear(), schedule.getEndWeek(),
                schedule.getTimePeriod().getEnd().getYear());
    }

    public String getTitle() {
        return getName() + weeksString();
    }

    public void removeMember(Member member) {
        try {
            team.removeMember(member);
            onChange();
        } catch (MemberIsNullException e) {
            Print.println(e.getMessage() + Print.LS);
        }
    }

    public String getTeamsString() {
        if (teams.isEmpty()) {
            return "No registered teams!" + Print.LS;
        }

        StringBuilder sb = new StringBuilder();
        for (Team team : teams) {
            sb.append(team + Print.LS);
        }

        return sb.toString();
    }

    public String getTeamString() {
        if (team.getMembers().isEmpty()) {
            return "No registered members!" + Print.LS;
        }
        return team.toString();
    }

    public String getBudgetString() {
        String headline = "Project Budget for " + name + ":" + LS;

        StringBuilder sb = new StringBuilder();
        sb.append(headline);
        sb.append(String.join("", Collections.nCopies(headline.length() + 2 * MARGIN, "-")) + LS);
        sb.append(getEarnedValueString());
        sb.append(getCostVarianceString());
        sb.append(getScheduleVarianceString());

        return sb.toString();
    }

    public String getEarnedValueString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Earned Value:      %.2f %s", schedule.getEarnedValue(), CURRENCY + LS));

        return sb.toString();
    }

    public String getCostVarianceString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Cost Variance:     %.2f %s", schedule.getCostVariance(), CURRENCY + LS));

        return sb.toString();
    }

    public String getScheduleVarianceString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Schedule Variance: %.2f %s", schedule.getScheduleVariance(currentWeek, lastWeekday), CURRENCY + LS));
        sb.append(dateAssumption() + LS);
        return sb.toString();
    }

    public double getTimeSpent(Member member) {
        return member.getTimeSpent();
    }

    public double getCostVariance() {
        return schedule.getCostVariance();
    }

    public String dateAssumption() {
        return "(Assuming it to be " + lastWeekday.toString() + " evening in week " + currentWeek.getWeek() +
                ", " + currentWeek.getYear() + ")";
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
            this.team.setName(name);
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

    public void setEndWeek(YearWeek endWeek) {
        try {
            TimePeriod updatedPeriod = new TimePeriod(schedule.getTimePeriod().getStart(), endWeek);
            schedule.setTimePeriod(updatedPeriod);
            onChange();
        } catch (IllegalArgumentException e) {
            Print.println(e.getMessage() + Print.LS);
        }
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

    /**
     * Method to allow the user to save all future changes on the project in a new file instead of
     * overwriting the source-file.
     * Keeps the old file if no new file is selected.
     */
    public void offerFileChange() {
        int changeFile = JOptionPane.showConfirmDialog(null, "Do you want to change the save-path?",
                "Warning", JOptionPane.YES_NO_OPTION);
        if (changeFile == JOptionPane.YES_OPTION) {
            File currentFile = this.file;
            setFile();
            if (file == null) {
                file = currentFile;
                Print.println("Selecting a new save-path failed. Using the old file instead." + LS);
            }
        }
    }

    public Member retrieveMember(String name) {
        return team.retrieveMember(name);
    }

    public Member retrieveMember(int index) {
        return team.retrieveMember(index);
    }

    public void memberNameChanger(Member member, String name) {
        for (Team team : getTeams()) {
            if (team.getMembers().contains(member)) {
                member.setName(name);
            }
        }
        onChange();
    }


    public  Team retrieveTeam(String name) {
        for (Team team : getTeams()) {
            if (team.getName().equals(name)) {
                return team;
            }
        }
        return null;
    }

    public  Activity retrieveActivity(String name) {
        for (Activity activity : getSchedule().getActivities()) {
            if (activity.getName().equals(name)) {
                return activity;
            }
        }
        return null;
    }

    public  Risk retrieveRisk(String name) {
        for (Risk risk : getRiskMatrix().getRisks()) {
            if (risk.getRiskName().equals(name)) {
                return risk;
            }
        }
        return null;
    }




    public void memberSalaryChanger(Member member, double salary) {
        for (Team team : getTeams()) {
            if (team.getMembers().contains(member)) {
                member.setSalaryPerHour(salary);
            }
        }
        onChange();
    }

    public void setDate(YearWeek yearWeek, DayOfWeek lastWeekday) {
        if (yearWeek == null || lastWeekday == null) {
            throw new IllegalArgumentException("Date contains a null-pointer!");
        }
        this.currentWeek = yearWeek;
        this.lastWeekday = lastWeekday;
        onChange();
    }

    public void assignTask(Activity activity, Team team) {
        if (activity == null) {
            throw new IllegalArgumentException("Can only set a team for non-null activities.");
        }
        activity.setTeam(team);
    }
}
