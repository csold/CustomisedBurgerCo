package common;

public class Customer {
	
	private int id;
	private String name;
	private String address;
	private String email;
	private String phone;

	public Customer(int id, String name, String address, String email, String phone) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPhone() {
		return this.phone;
	}

}
