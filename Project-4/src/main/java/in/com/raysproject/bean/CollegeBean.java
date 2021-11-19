package in.com.raysproject.bean;

/**
 * College JavaBean encapsulates College attributes
 * @author Ajay
 *
 */
public class CollegeBean extends BaseBean {
	private String name;
	private String address;
	private String state;
	private String city;
	private String phoneNo;

	public CollegeBean() {
	} /* default counstructor */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return name;
	}

	@Override
	public String toString() {
		return "CollegeBean [name=" + name + ", address=" + address + ", state=" + state + ", city=" + city
				+ ", phoneNo=" + phoneNo + "]";
	}
}
