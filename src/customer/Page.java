package customer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import common.Ingredient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

public class Page {
	String name;
	GridPane body = new GridPane();
	CustomerUI parent;
	ArrayList<Ingredient> foodItem;
	Map<Ingredient, VBox> samsMap;
	int i = 0;
	public Page(String name, CustomerUI parent) throws SQLException, FileNotFoundException {
		samsMap = new HashMap<Ingredient, VBox>();
		this.parent = parent;
		this.name = name;
				
		body.setVgap(3);
		body.setHgap(10);
		//body.setGridLinesVisible(true);
		
		ColumnConstraints col1 = new ColumnConstraints();
	    col1.setPercentWidth(30);
	    ColumnConstraints col2 = new ColumnConstraints();
	    col2.setPercentWidth(30);
	    ColumnConstraints col3 = new ColumnConstraints();
	    col3.setPercentWidth(30);
	    ColumnConstraints col4 = new ColumnConstraints();
	    col4.setPercentWidth(10);
	    body.getColumnConstraints().addAll(col1,col2,col3, col4);
	    
		
	    foodItem = new ArrayList<Ingredient>();
	    
	    
	    switch (name) {
	    case "Breads": foodItem = (ArrayList<Ingredient>) parent.database.getBuns();
	    break;
	    
	    case "Filling": foodItem = (ArrayList<Ingredient>) parent.database.getFillings();
	    break;
	    
	    case "Cheese": foodItem = (ArrayList<Ingredient>) parent.database.getCheese();
	    break;
	    
	    case "Toppings": foodItem = (ArrayList<Ingredient>) parent.database.getToppings();
	    break;
	    
	    case "Sauce": foodItem = (ArrayList<Ingredient>) parent.database.getCondiments();
	    break;
	    }
	    
	    int row = 1;
	    
	    for(i = 1; i < foodItem.size() + 1; i++){
	    	if(i % 3 == 0){
	    		VBox btn = new VBox();
	    		samsMap.put(foodItem.get(i-1), btn);
	    		btn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-background-radius: 25");
	    		btn.setPadding(new Insets(10));
	    		
	    		Text name1 = new Text();
	    		String foodName = foodItem.get(i-1).getName();
	    		name1.setText(foodName);
	    		name1.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
	    		name1.setFill(Color.WHITE);
	    		name1.setOpacity(0.9);
	    		
	    			    		
	    		Image image = new Image(new FileInputStream(foodItem.get(i-1).getUrl()));
	    		ImageView imageView = new ImageView();
	    		imageView.setImage(image);
	    		imageView.setFitHeight(70);
	    		imageView.setFitWidth(70);
	    		
	    		
	    		Text price = new Text("$" + String.valueOf(new DecimalFormat("0.00").format(foodItem.get(i-1).getPrice())));
	    		price.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 15));
	    		price.setFill(Color.WHITE);
	    		price.setOpacity(0.9);
	    		
	    		btn.getChildren().addAll(name1, imageView, price);
	    		btn.setAlignment(Pos.CENTER);
	    		btn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	    			int n = i;
	    			@Override
	    			public void handle(MouseEvent event) {
	    				if(parent.orderArray.size() < 13 && foodItem.get(n-1).getStockCount() - Collections.frequency(parent.orderArray, foodItem.get(n-1))>0){
	    				parent.orderArray.add(foodItem.get(n-1));
	    				parent.total += foodItem.get(n-1).getPrice();
	    				parent.totalPrice.setText("Total: $" + new DecimalFormat("0.00").format(parent.total)); 
	    				
	    				parent.update();
	    				if(foodItem.get(n-1).getStockCount() - Collections.frequency(parent.orderArray, foodItem.get(n-1))<1){
	    					btn.setOpacity(0.3);
	    				}
	    				}
	    				}	
	    			}
	    		);
	    		if(foodItem.get(i-1).getStockCount() <1) {
	    			btn.setOpacity(0.3);
	    		}
	    		
	    		GridPane.setHalignment(btn, HPos.CENTER);
	    		
	    		body.add(btn, 2, row);

	    		row += 3;
	    	}
	    	else if(i % 3 == 1){
	    		VBox btn = new VBox();
	    		samsMap.put(foodItem.get(i-1), btn);
	    		btn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-background-radius: 25");
	    		btn.setPadding(new Insets(10));
	    		
	    		Text name1 = new Text();
	    		String foodName = foodItem.get(i-1).getName();
	    		name1.setText(foodName);
	    		name1.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
	    		name1.setFill(Color.WHITE);
	    		name1.setOpacity(0.9);
	    		
	    			    		
	    		Image image = new Image(new FileInputStream(foodItem.get(i-1).getUrl()));
	    		ImageView imageView = new ImageView();
	    		imageView.setImage(image);
	    		imageView.setFitHeight(70);
	    		imageView.setFitWidth(70);
	    		
	    		Text price = new Text("$" + String.valueOf(new DecimalFormat("0.00").format(foodItem.get(i-1).getPrice())));

	    		price.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 15));
	    		price.setFill(Color.WHITE);
	    		price.setOpacity(0.9);
	    		
	    		btn.getChildren().addAll(name1, imageView, price);
	    		btn.setAlignment(Pos.CENTER);
	    		btn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	    			int n = i;
	    			@Override
	    			public void handle(MouseEvent event) {
	    				if(parent.orderArray.size() < 13 && foodItem.get(n-1).getStockCount() - Collections.frequency(parent.orderArray, foodItem.get(n-1))>0){
		    				parent.orderArray.add(foodItem.get(n-1));
		    				parent.total += foodItem.get(n-1).getPrice();
		    				parent.totalPrice.setText("Total: $" + new DecimalFormat("0.00").format(parent.total)); 
		    				
		    				parent.update();
		    				if(foodItem.get(n-1).getStockCount() - Collections.frequency(parent.orderArray, foodItem.get(n-1))<1){
		    					btn.setOpacity(0.3);
		    				}
		    				}
	    				}	
	    			}
	    		);
	    		
	    		GridPane.setHalignment(btn, HPos.CENTER);
	    		if(foodItem.get(i-1).getStockCount() <1) {
	    			btn.setOpacity(0.3);
	    		}
	    		
	    		
	    		body.add(btn, 1, row);

	    		
	    	}
	    	
	    	else{
	    		VBox btn = new VBox();
	    		samsMap.put(foodItem.get(i-1), btn);
	    		btn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-background-radius: 25");
	    		btn.setPadding(new Insets(10));
	    		
	    		Text name1 = new Text();
	    		String foodName = foodItem.get(i-1).getName();
	    		name1.setText(foodName);
	    		name1.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
	    		name1.setFill(Color.WHITE);
	    		name1.setOpacity(0.9);
	    		
	    			    		
	    		Image image = new Image(new FileInputStream(foodItem.get(i-1).getUrl()));
	    		ImageView imageView = new ImageView();
	    		imageView.setImage(image);
	    		imageView.setFitHeight(70);
	    		imageView.setFitWidth(70);
	    		
	    		
	    		
	    		Text price = new Text("$" + String.valueOf(new DecimalFormat("0.00").format(foodItem.get(i-1).getPrice())));
	    		price.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 15));
	    		price.setFill(Color.WHITE);
	    		price.setOpacity(0.9);
	    		
	    		btn.getChildren().addAll(name1, imageView, price);
	    		btn.setAlignment(Pos.CENTER);
	    		btn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	    			int n = i;
	    			@Override
	    			public void handle(MouseEvent event) {
	    				if(parent.orderArray.size() < 13 && foodItem.get(n-1).getStockCount() - Collections.frequency(parent.orderArray, foodItem.get(n-1)) >0){
		    				parent.orderArray.add(foodItem.get(n-1));
		    				parent.total += foodItem.get(n-1).getPrice();
		    				parent.totalPrice.setText("Total: $" + new DecimalFormat("0.00").format(parent.total)); 
		    				parent.update();
		    				if(foodItem.get(n-1).getStockCount() - Collections.frequency(parent.orderArray, foodItem.get(n-1))<1){
		    					btn.setOpacity(0.3);
		    				}
		    				}
	    				}	
	    			}
	    		);
	    		if(foodItem.get(i-1).getStockCount() <1) {
	    			btn.setOpacity(0.3);
	    		}
	    		
	    		GridPane.setHalignment(btn, HPos.CENTER);
	    		body.add(btn, 0, row);
	    	}		
	    }

	}
	
		
	public GridPane getBody(){
		return this.body;
	}
	
	public String getName(){
		return this.name;
	}
	
		
		
	
	
	public void updateButtons(Ingredient item) {
		if(item.getStockCount() - Collections.frequency(parent.orderArray, item)>0 && samsMap.containsKey(item)){
			samsMap.get(item).setOpacity(1);
		}
	}
	
}
