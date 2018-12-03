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
	
}
