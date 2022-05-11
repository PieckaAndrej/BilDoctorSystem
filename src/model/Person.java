package model;

public abstract class Person {
	private String name;
	private String address;
	private String city;
	private String zipcode;
	private String phoneNumber;
	
	public Person(String name, String address, String city, String zipcode, String phoneNumber) {
		super();
		this.name = name;
		this.address = address;
		this.city = city;
		this.zipcode = zipcode;
		this.phoneNumber = phoneNumber;
	}

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
