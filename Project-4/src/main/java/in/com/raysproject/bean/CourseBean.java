package in.com.raysproject.bean;

/**
 * course JavaBean encapsulates course attributes
 * @author Ajay
 *
 */

public class CourseBean extends BaseBean {
	private String courseName;
	private String description;
	private String duration;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id +"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return courseName;
	}

}
