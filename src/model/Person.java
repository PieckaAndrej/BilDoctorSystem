package model;

public class Person {
	private String name;
	private String surname;
	private String address;
	private String city;
	private String zipcode;
	private String phoneNumber;
	
	public Person(String name, String surname, String address, String city, String zipcode, String phoneNumber) {
		super();
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.city = city;
		this.zipcode = zipcode;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Get name.
	 *
	 * @return name as String.
	 */
	public String getName() {
	    return name;
	}

	/**
	 * Set name.
	 *
	 * @param name the value to set.
	 */
	public void setName(String name) {
	    this.name = name;
	}

	/**
	 * Get address.
	 *
	 * @return address as String.
	 */
	public String getAddress() {
	    return address;
	}

	/**
	 * Set address.
	 *
	 * @param address the value to set.
	 */
	public void setAddress(String address) {
	    this.address = address;
	}

	/**
	 * Get city.
	 *
	 * @return city as String.
	 */
	public String getCity() {
	    return city;
	}

	/**
	 * Set city.
	 *
	 * @param city the value to set.
	 */
	public void setCity(String city) {
	    this.city = city;
	}

	/**
	 * Get zipcode.
	 *
	 * @return zipcode as String.
	 */
	public String getZipcode() {
	    return zipcode;
	}

	/**
	 * Set zipcode.
	 *
	 * @param zipcode the value to set.
	 */
	public void setZipcode(String zipcode) {
	    this.zipcode = zipcode;
	}

	/**
	 * Get phoneNumber.
	 *
	 * @return phoneNumber as String.
	 */
	public String getPhoneNumber() {
	    return phoneNumber;
	}

	/**
	 * Set phoneNumber.
	 *
	 * @param phoneNumber the value to set.
	 */
	public void setPhoneNumber(String phoneNumber) {
	    this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Get surname.
	 *
	 * @return surname as String.
	 */
	public String getSurname() {
	    return surname;
	}

	/**
	 * Set surname.
	 *
	 * @param surname the value to set.
	 */
	public void setSurname(String surname) {
	    this.surname = surname;
	}
}
