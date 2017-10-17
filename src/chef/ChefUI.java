package chef;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.Customer;
import common.Database;
import common.Ingredient;
import common.Order;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
 
public class ChefUI extends Application {
	
	private GridPane gridpane;
	private int framecount = 0;

    public static void main(String[] args) throws SQLException {
    	Database.open();
        launch(args);
    }
 
    @Override
    public void start(Stage stage) throws SQLException {
        stage.setTitle("Burger Orders");
        stage.setMaximized(true);
        stage.setWidth(1200);
        stage.setHeight(800);

        //Setup Grid
        gridpane = new GridPane();
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        gridpane.setPadding(new Insets(10));
        gridpane.setStyle("-fx-background-color:black;");
        
        final BorderPane root = new BorderPane();
        
        HBox header = new HBox();
        header.setStyle("-fx-background-color:blue;-fx-padding:10;-fx-border-color:white;-fx-border-width:0 0 2 0;");
        
        root.setCenter(gridpane);   
        root.setTop(header);
        
        Text title = new Text("Rocket Burgers - Employee System");
        title.setFill(Color.WHITE);
        title.setFont(Font.font(24));

        Text time = new Text("Current Time: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        time.setFont(Font.font(24));
        time.setFill(Color.WHITE);
        
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);
        
        header.getChildren().addAll(title,spacer,time);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {
        	time.setText("Current Time: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        	
        	if(framecount % (5000/16) == 0) update(); //update every 5 seconds
        	
        	framecount++;
        }));
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        update();
    }
    
    private void update() {
		try {
			List<Order> orders = getOrders();
			int cols = (int) Math.ceil(orders.size() / 2d);
			int rows = orders.size() <= 1 ? 1 : 2;
			
	        ColumnConstraints cc = new ColumnConstraints();
	        RowConstraints rc = new RowConstraints();
	        cc.setPercentWidth(100d);
	        rc.setPercentHeight(100d);
	        
	        this.gridpane.getColumnConstraints().clear();
	        for(int i = 0; i < cols; ++i)
	        	this.gridpane.getColumnConstraints().add(cc);

	        this.gridpane.getRowConstraints().clear();
	        for(int i = 0; i < rows; ++i)
	        	this.gridpane.getRowConstraints().add(rc);

	        this.gridpane.getChildren().clear();
			for(int i = 0; i < orders.size(); ++i) {
				Docket d = new Docket(orders.get(i));
				this.gridpane.add(d, i % cols, i / cols);
			}
			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    }
    
	private List<Order> getOrders() throws SQLException {
		ResultSet rs = Database.fetch("SELECT * FROM sale LEFT JOIN customer on customer.id = customer_id WHERE status IN (0,1) OR modified_time BETWEEN timestamp(DATE_SUB(NOW(), INTERVAL 30 SECOND)) AND timestamp(NOW()) ORDER BY date ASC LIMIT 12");
		
		List<Order> orders = new ArrayList<Order>();
	    
	    while(rs.next()) {
	    	int id = rs.getInt(1);
	    	int customer = rs.getInt("customer_id");
	    	long time = rs.getTimestamp("date").getTime();
	    	long mod = rs.getTimestamp("modified_time").getTime();
	    	int status = rs.getInt("status");
	    	Customer c = null;
	    	if(customer != 0) {
	    		int cid = rs.getInt(5);
	    		String cname = rs.getString("name");
	    		String caddress = rs.getString("address");
	    		String cemail = rs.getString("email");
	    		String cphone = rs.getString("phone_number");
	    		c = new Customer(cid, cname, caddress, cemail, cphone);
	    	}
	    	
			ResultSet irs = Database.fetch("SELECT * FROM `order` JOIN `inventory` ON id = inventory_id WHERE sale_id = " + id);

			List<Ingredient> ingredients = new ArrayList<Ingredient>();
		    while(irs.next()) {
		    	int iid = irs.getInt("id");
		    	String iname = irs.getString("name");
		    	float icost = irs.getFloat("cost");
		    	int istock = irs.getInt("stock_count");
		    	String iimage = irs.getString("image_url");
		    	
		    	ingredients.add(new Ingredient(iname, icost, iid, istock, iimage));	    	
		    }
			
	    	orders.add(new Order(id, ingredients, status, time, mod, c));
	    }
	    
	    return orders;
	}
    
}
