package common;

import java.sql.SQLException;

public class Ingredient {

	private String name;
	private double price;
	private int id;
	private int stockCount;
	private String url;
	
	public Ingredient(String name, double price, int id, int stockCount, String url){
		this.name = name;
		this.id = id;
		this.price = price;
		this.stockCount = stockCount;
		this.url = url;
	}
	
	public String getName(){
		return this.name;
	}
	
	public double getPrice(){
		return this.price;
	}
	
	public int getId(){
		return this.id;
	}
	
	public int getStockCount(){
		return this.stockCount;
	}
	
	public String getUrl(){
		return this.url;
	}
	
	public boolean setStock(int amount) throws SQLException {
		this.stockCount = amount;
		return Database.run("UPDATE inventory SET stock_count = " + amount + " WHERE id = " + this.id);
	}
}
