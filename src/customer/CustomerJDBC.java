package customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import common.Ingredient;

public class CustomerJDBC {
	
	private Connection conn;

	public CustomerJDBC() throws SQLException {
		this.open();
	}
	
	public void open() throws SQLException {
		this.conn = DriverManager.getConnection("jdbc:mysql://10.140.69.227/burgers?user=root&password=password");
	}
	
	public void close() throws SQLException {
		this.conn.close();
	}
	
	public List<Ingredient> getBuns() throws SQLException {		
		return this.resultSetToList(conn.createStatement().executeQuery("SELECT * FROM inventory WHERE category_id = 1"));
	}
	
	public List<Ingredient> getCondiments() throws SQLException {
		return this.resultSetToList(conn.createStatement().executeQuery("SELECT * FROM inventory WHERE category_id = 3"));
	}
	
	public List<Ingredient> getToppings() throws SQLException {
		return this.resultSetToList(conn.createStatement().executeQuery("SELECT * FROM inventory WHERE category_id = 5"));
	}
	
	public List<Ingredient> getCheese() throws SQLException {
		return this.resultSetToList(conn.createStatement().executeQuery("SELECT * FROM inventory WHERE category_id = 2"));
	}
	
	public List<Ingredient> getFillings() throws SQLException {
		return this.resultSetToList(conn.createStatement().executeQuery("SELECT * FROM inventory WHERE category_id = 4"));
	}
	
	public List<Ingredient> getSalads() throws SQLException {
		return this.resultSetToList(conn.createStatement().executeQuery("SELECT * FROM inventory WHERE category_id = 6"));
	}
	
	public boolean submitOrder(List<Ingredient> ingredients, int custID) throws SQLException {
		conn.createStatement().execute("INSERT INTO sale(customer_id) VALUES("+custID+"); ");
		ResultSet r = conn.createStatement().executeQuery("SELECT LAST_INSERT_ID();");
		r.next();
		int saleID = r.getInt(1);		
		for(Ingredient ingredient : ingredients) {
			conn.createStatement().execute("INSERT INTO `order` (sale_id, inventory_id) VALUES (" + saleID + ", " + ingredient.getId() + ")");
			conn.createStatement().execute("UPDATE inventory SET stock_count = stock_count - 1 WHERE id = " + ingredient.getId());
		}
		
		return true;
	}
	
	private List<Ingredient> resultSetToList(ResultSet rs) throws SQLException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
	    
	    while(rs.next()) {
	    	int id = rs.getInt("id");
	    	String name = rs.getString("name");
	    	double cost = rs.getDouble("cost");
	    	int stock = rs.getInt("stock_count");
	    	String image = rs.getString("image_url");
	    	
	    	ingredients.add(new Ingredient(name, cost, id, stock, image));	    	
	    }    
	    return ingredients;
	}
	
	public int createUser(String number, String name, String address, String email) throws SQLException {
        String name1 =  name;
        String number1 = number;
        String email1 = email;
        String address1 = address;
        
        conn.createStatement().execute("INSERT INTO `customer` (name, address, email, phone_number) "
                + "VALUES ('" + name1 + "', '" + address1 + "', '" + email1 + "', '" + number1 + "'); ");
        ResultSet r = conn.createStatement().executeQuery("SELECT LAST_INSERT_ID();");
        r.next();
        int cusID = r.getInt(1);
        
    
    return cusID;
    }

	
	
	
}
	
//	public ArrayList<Ingredient> getFillings() {}
//	public ArrayList<Ingredient> getCheese() {}
//	public ArrayList<Ingredient> getCondiments() {}
//	
//	public void setOrder(int saleID, long dateTime, ArrayList<int> ingredients /**CustomerID and Status later*/) {
//		stmt = conn.createStatement();
//		ResultSet rs = stmt.executeQuery(query);
//		//Set status to 0
//		
//		for (int i: ingredients) {
//			
//		}
//	}
//
//}
