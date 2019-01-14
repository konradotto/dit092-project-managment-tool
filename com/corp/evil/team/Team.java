import java.util.*;

public class Team {

    // constants
    private final static int COLUMN_WIDTH = 30;
    private final static int COLUMNS = 5;
    private final static String newline = Print.LS;

    // members
    private String name;
    private List<Member> members;

    public Team(String name, ArrayList<Member> members) throws NameIsEmptyException {
        if (name.trim().isEmpty()) {
            throw new NameIsEmptyException("The name-field of a team can not be empty!");
        }
        this.name = name;
        this.members = members;
    }

    public Team(String name) throws NameIsEmptyException {
        this(name, new ArrayList<>());
    }

    public void addMember(Member member) throws MemberIsNullException, MemberAlreadyRegisteredException {
        if (member == null) {
            throw new MemberIsNullException("This member does not exist!");
        } else if (members.contains(member)) {
            throw new MemberAlreadyRegisteredException("The member is already registered!");
        } else {
            members.add(member);
        }
    }

    public void removeMember(Member member) throws MemberIsNullException {
        if (member == null) {
            throw new MemberIsNullException("This member does not exist!");
        }

        members.remove(member);
    }

    public void solveCopies(Member member) {
        int i = 0;
        for (Member teamMember : members) {
            if (member.getName().equals(teamMember.getName())) {
                members.set(i, member);
            }
            i++;
        }
    }

    public boolean contains(Member member) {
        return members.contains(member);
    }

    public Member retrieveMember(String name) {
        for (Member member : members) {
            if (member.getName().equals(name)) {
                return member;
            }
        }
        return null;
    }

    public Member retrieveMember(Member member) {
        for (Member memberInHere : members) {
            if (memberInHere.equals(member)) {
                return memberInHere;
            }
        }
        return null;
    }

    public Member retrieveMember(int index) {
        if (index >= 0 && index < members.size()) {
            return members.get(index);
        }
        return null;
    }

    public void replaceMembers(Team team) {
        List<Member> temp = new ArrayList<>(members);

        for (Member member : members) {
            temp.remove(member);
            temp.add(team.retrieveMember(member));
        }
        members = temp;
    }

    /**
     * Assume everyone on the team will do the same amount of work on any task.
     *
     * @return average salary of the team
     */
    public double getAverageSalary() {
        double totalSalary = 0;
        for (Member member : members) {
            totalSalary += member.getSalaryPerHour();
        }
        return totalSalary / (double) members.size();
    }

    public List<Member> getMembers() {
        return members;
    }


    @Override
    public String toString() {
        return formatTable();
    }

    private String formatTableRow(String[] columns) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < COLUMNS - 2; ++i) {
            sb.append(String.format("%1$-" + COLUMN_WIDTH + "s", columns[i]));
        }
        sb.append(String.format(columns[3] + "%n"));

        return sb.toString();
    }

    public String toNumberedString() {
        StringBuilder sb = new StringBuilder();

        if (members.isEmpty()) {
            sb.append("There are currently no members registered for this team." + System.lineSeparator());
        } else {
            int l = (int) Math.ceil(Math.log10(members.size()) + 0.005);   // number of digits needed for left aligned formatting
            for (int i = 0; i < members.size(); i++) {
                Member mem = members.get(i);
                sb.append(String.format("%1$" + String.valueOf(l) + "s %2$-20s\t%3$s%n", i + 1+") ", mem.getName(), mem.getID()));
            }
        }

        return sb.toString();
    }

    public void alphaSort() {
        members.sort(Comparator.comparing(Member::getName));
    }


    public String formatTable() {
        StringBuilder sb = new StringBuilder();

        sb.append("\t\t\t Team: " + getName() + newline);

        sb.append(String.join("", Collections.nCopies((COLUMNS - 2) * COLUMN_WIDTH + 1, "-")));
        sb.append(newline);
        if (members.isEmpty()) {
            sb.append("There are no members registered in this team yet." + newline);
            return sb.toString();
        }

        // format table content
        sb.append(formatTableRow(new String[]{"| Member name:", "| Salary/h:", "| Time spent:", "|"}));

        // separator line

        sb.append(String.join("", Collections.nCopies((COLUMNS - 2) * COLUMN_WIDTH + 1, "-")));
        sb.append(newline);

        alphaSort();

        for (Member member : members) {
            sb.append(formatTableRow(new String[]{"| " + member.getName(),
                    String.format("| %.2f %s", member.getSalaryPerHour(), Project.CURRENCY),
                    "| " + member.getTimeSpent() + " h", "|"}));

            sb.append(String.join("", Collections.nCopies((COLUMNS - 2) * COLUMN_WIDTH + 1, "-")));
            sb.append(newline);
        }
        return sb.toString();
    }

    //GETTERS AND SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name) &&
                Objects.equals(members, team.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, members);
    }
}
