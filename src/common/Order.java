package common;

import java.sql.SQLException;
import java.util.List;

public class Order {
	
	private int id;
	private List<Ingredient> ingredients;
	private int status;
	private long timestamp;
	private long modified;
	
	private Customer customer;

	public Order(int id, List<Ingredient> ingredients, int status, long dateTime, long modified, Customer customer) {
		this.id = id;
		this.ingredients = ingredients;
		this.status = status;
		this.timestamp = dateTime;
		this.modified = modified;
		this.customer = customer;
	}

	public int getId() {
		return id;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public int getStatus() {
		return status;
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	public long getModified() {
		return modified;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public double getTotalCost() {
		double cost = 0d;
		for(Ingredient i : ingredients)
			cost += i.getPrice();
		return cost;
	}
	
	public boolean setStatus(int status) throws SQLException {
		this.status = status;
		return Database.run("UPDATE sale SET status = " + status + " WHERE id = " + this.id);
	}
}
