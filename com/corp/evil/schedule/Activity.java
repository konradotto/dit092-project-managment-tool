public class Activity {
	
	private int startWeek;
	private int endWeek;
	private String name;
	private Team team;
	private Budget budget;
	
	public Activity(String name, int startWeek, int endWeek, Team team, Budget budget) {
		this.name = name;
		this.startWeek = startWeek;
		this.endWeek = endWeek;
		this.team = team;
		this.budget = budget;
	}

	//Accessor methods
	public int getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(int startWeek) {
		this.startWeek = startWeek;
	}

	public int getEndWeek() {
		return endWeek;
	}

	public void setEndWeek(int endWeek) {
		this.endWeek = endWeek;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	//End of Accessor methods
}
