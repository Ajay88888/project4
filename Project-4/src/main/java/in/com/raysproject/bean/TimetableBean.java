package in.com.raysproject.bean;

import java.util.Date;

/**
 * imeTable JavaBean encapsulates TimeTable attributes
 * @author Ajay
 *
 */

public class TimetableBean extends BaseBean {
	private int courseId;
	private String courseName;
	private int subjectId;
	private String subjectName;
	private String semester;
	private String examTime;
	private Date examDate;

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getKey() {
		return id +"";
	}

	public String getValue() {
		return subjectName;
	}

	@Override
	public String toString() {
		return "TimetableBean [courseId=" + courseId + ", courseName=" + courseName + ", subjectId=" + subjectId
				+ ", subjectName=" + subjectName + ", semester=" + semester + ", examTime=" + examTime + ", examDate="
				+ examDate + ", id=" + id + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", createdDatetime=" + createdDatetime + ", modifiedDatetime=" + modifiedDatetime + "]";
	}

}
