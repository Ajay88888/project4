package in.com.raysproject.bean;

import java.util.Date;


/**
 *  faculty JavaBean encapsulates faculty attributes
 * @author Ajay
 *
 */
public class FacultyBean extends BaseBean {

	private String firstName;
	private String lastName;
	private String gender;
	private String loginId;
	private Date dateOfJoining;
	private String qualification;
	private String mobileNo;
	private int collegeId;
	private String collegeName;
	private int courseId;
	private String courseName;
	private int subjectId;
	private String subjectName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public int getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

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

	public String getKey() {
		return id+ "";
	}

	public String getValue() {
		return firstName+""+lastName;
	}

	@Override
	public String toString() {
		return "FacultyBean [firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender + ", loginId="
				+ loginId + ", dateOfJoining=" + dateOfJoining + ", qualification=" + qualification + ", mobileNo="
				+ mobileNo + ", collegeId=" + collegeId + ", collegeName=" + collegeName + ", courseId=" + courseId
				+ ", courseName=" + courseName + ", subjectId=" + subjectId + ", subjectName=" + subjectName + "]";
	}

}
