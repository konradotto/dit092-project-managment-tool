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

	public void addActivity(Activity activity) throws ActivityAlreadyRegisteredException, ActivityIsNullException {
		if(activity == null) {
			throw new ActivityIsNullException("Activity is NULL and cannot be added to the list of activities!");
		}
		if(activities.contains(activity)) {
			throw new ActivityAlreadyRegisteredException("This activity already exists!");
		}
		else activities.add(activity);
	}

	public void removeActivity(Activity activity) throws ActivityIsNullException {
        if (activity == null) {
            throw new ActivityIsNullException("Project schedule can not remove NULL value from schedule");
        }else {
            activities.remove(activity);
        }
	}
}
