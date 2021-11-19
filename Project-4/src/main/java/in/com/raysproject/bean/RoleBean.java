package in.com.raysproject.bean;


/**
 * role JavaBean encapsulates role attributes
 * @author Ajay
 *
 */

public class RoleBean extends BaseBean {

	/**
	 * predefined role
	 */

	public static final int ADMIN = 1;
	public static final int STUDENT = 2;
	public static final int COLLEGE_SCHOOL = 3;
	public static final int KIOSK = 4;

	public String name;
	public String description;

	public RoleBean() {
	} /* default counstructor */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return name;
	}

	@Override
	public String toString() {
		return "RoleBean [name=" + name + ", description=" + description + ", id=" + id + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", createdDatetime=" + createdDatetime + ", modifiedDatetime="
				+ modifiedDatetime + "]";
	}

}
