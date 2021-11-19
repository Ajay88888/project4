package in.com.raysproject.bean;

import java.util.Date;
/**
 * student JavaBean encapsulates student attributes
 * @author Ajay
 *
 */
public class StudentBean extends BaseBean {

	private String firstName;
	private String lastName;
	private Date dob;
	private String mobileNo;
	private String emailId;
	private long collegeId;
	private String collegeName;

	public StudentBean()/* default counstructor */ {

	}

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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return firstName + "  " + lastName;
	}

	@Override
	public String toString() {
		return "StudentBean [firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + ", mobileNo="
				+ mobileNo + ", emailId=" + emailId + ", collegeId=" + collegeId + ", collegeName=" + collegeName
				+ ", id=" + id + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", createdDatetime="
				+ createdDatetime + ", modifiedDatetime=" + modifiedDatetime + "]";
	}

}