import java.io.File;
import java.util.ArrayList;

public class ProjectSchedule {
	
	private ArrayList<Activity> activities;
	private int startWeek;
	private int endWeek;
	
	public ProjectSchedule(int startWeek, int endWeek, ArrayList<Activity> activities) {
		this.startWeek = startWeek;
		this.endWeek = endWeek;
		activities = new ArrayList<Activity>();
	}
	
	public ProjectSchedule(File file) {
		this(ProjectSchedule.fromJsonFile(file));		
	}
	
	public ProjectSchedule(String jsonText) {
		//TODO!!!
	}
	
	public static String fromJsonFile(File file) {
		String jsonText = "TODO!";
		return jsonText;
	}
	
	public void addActivity(Activity activity) {
		
	}
	
	public void removeActivity(Activity activity) {
		
	}
	
	
}
