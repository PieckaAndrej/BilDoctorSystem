package model;

import java.time.LocalDate;

public class Vehicle {
	
	private String plateNumber;
	private String brand;
	private LocalDate checkUpDate;
	
	private int year;
	
	private Customer owner;
	
	public Vehicle(String plateNumber, int year, String brand, LocalDate checkUpDate) {
		this.setPlateNumber(plateNumber);
		this.setYear(year);
		this.setBrand(brand);
		this.setCheckUpDate(checkUpDate);
	}
	
	public Vehicle(String plateNumber, int year, String brand, LocalDate checkUpdate, Customer owner) {
		this(plateNumber, year, brand, checkUpdate);
		this.setOwner(owner);
	}

	/**
	 * @return the plateNumber
	 */
	public String getPlateNumber() {
		return plateNumber;
	}

	/**
	 * @param plateNumber the plateNumber to set
	 */
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the owner
	 */
	public Customer getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Customer owner) {
		this.owner = owner;
	}

	/**
	 * @return the checkUpDate
	 */
	public LocalDate getCheckUpDate() {
		return checkUpDate;
	}

	/**
	 * @param checkUpDate the checkUpDate to set
	 */
	public void setCheckUpDate(LocalDate checkUpDate) {
		this.checkUpDate = checkUpDate;
	}
	
}
