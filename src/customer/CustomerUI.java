package customer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import common.Ingredient;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class CustomerUI extends Application{

	//
	//
	
	int width = 1000, height = 650;
	HBox headerBox = new HBox();
	BorderPane borderPane = new BorderPane();
	Text title = new Text("ROCKET    BURGER");
	Button next;
	Button previous;
	VBox orderBox;
	int currentPage = 1;
	double total = 0.00;
	public ArrayList<Ingredient> orderArray = new ArrayList<Ingredient>();
	public ArrayList<String> customerInfo = new ArrayList<String>();
	public Text totalPrice;
	CustomerJDBC database;
	
	String name;
	String number;
	String email;
	String address;
	
	TextField userTextField = new TextField();
	TextField phoneField = new TextField();
	TextField emailField = new TextField();
	TextField addressField = new TextField();
	Page landing, buns, fillings, cheese, toppings, sauce;
	//hiJOe
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		database = new CustomerJDBC();

				
		borderPane.setPrefSize(width, height);
		
		landing = new Page("JOE'S BURGERS", this);
		buns = new Page("Breads", this);
		fillings = new Page("Filling", this);
		cheese = new Page("Cheese", this);
		toppings = new Page("Toppings", this);
		sauce = new Page("Sauce", this);
		
		orderBox = new VBox();
		orderBox.setSpacing(8);
		orderBox.setMaxWidth(200);
		orderBox.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-background-radius: 15");
		orderBox.setPadding(new Insets(20));
		
		
		VBox alignR = new VBox();
		alignR.setPrefWidth(300);
		alignR.getChildren().add(orderBox);
		alignR.setAlignment(Pos.TOP_LEFT);
		borderPane.setRight(alignR);
		alignR.setVisible(false);
		
		VBox alignL = new VBox();
		alignL.setPrefWidth(200);
		borderPane.setLeft(alignL);
		
		
		update();
		
		//Header
		HBox header = new HBox();
		title.setFont(Font.font("Impact", FontWeight.BOLD, 42));
		title.setFill(Color.WHITE);
		title.setOpacity(0.9);
		header.setPadding(new Insets(40));
		header.getChildren().addAll(title);
		header.setAlignment(Pos.CENTER);
		borderPane.setTop(header);
		
		//Center
		
		GridPane sorry = new GridPane();
		VBox sorryH = new VBox();
		HBox sorryW = new HBox();
		HBox soSorry = new HBox();
		sorryH.setPrefHeight(150);
		sorryW.setPrefWidth(350);
		soSorry.setPrefWidth(260);
		HBox center = new HBox();
		Button order = new Button("Start Order");
		
		File lFile = new File("Images/Logo.png"); 							
		Image lImg = new Image(lFile.toURI().toString());
		Rectangle lRect = new Rectangle(300,300);
		lRect.setFill(new ImagePattern(lImg));
		sorryH.getChildren().add(lRect);
		sorryH.setAlignment(Pos.CENTER);
		
		order.setStyle("-fx-padding: 8 15 15 15; -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; -fx-background-radius: 8; -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a, radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c); -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 ); -fx-font-weight: bold; -fx-font-size: 1.1em;");
		order.setTextFill(Color.WHITESMOKE);

		
		center.setPadding(new Insets(40));
		center.getChildren().addAll(order);
		center.setAlignment(Pos.CENTER);
		sorry.add(sorryH, 1, 0);
		sorry.add(sorryW, 0, 2);
		sorry.add(soSorry, 2, 0);
		sorry.add(center, 1, 2);
		borderPane.setCenter(sorry);
		
		//Footer
		HBox footer = new HBox();
		footer.setPadding(new Insets(40, 100, 40 , 100));
		next = new Button("Next");
		next.setStyle("-fx-padding: 8 15 15 15; -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; -fx-background-radius: 8; -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a, radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c); -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 ); -fx-font-weight: bold; -fx-font-size: 1.1em;");
		next.setTextFill(Color.WHITESMOKE);
		next.setPrefWidth(140);
		
		previous = new Button("Back");
		previous.setStyle("-fx-padding: 8 15 15 15; -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; -fx-background-radius: 8; -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a, radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c); -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 ); -fx-font-weight: bold; -fx-font-size: 1.1em;");
		previous.setTextFill(Color.WHITESMOKE);
		previous.setPrefWidth(140);
		
		totalPrice = new Text("Total: $" + new DecimalFormat("0.00").format(total));
		totalPrice.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		totalPrice.setFill(Color.WHITE);
		totalPrice.setOpacity(0.9);
		Pane spacerL = new Pane();
		Pane spacerR = new Pane();
		HBox.setHgrow(spacerL, Priority.ALWAYS);
		HBox.setHgrow(spacerR, Priority.ALWAYS);
	    spacerL.setMinSize(10, 1);
	    spacerR.setMinSize(10, 1);
		footer.getChildren().addAll(previous, spacerL, totalPrice, spacerR, next);
		footer.setVisible(false);
		borderPane.setBottom(footer);
		
		
		//Receipt
		GridPane personalInfo = new GridPane();
		
		personalInfo.setAlignment(Pos.CENTER);
		personalInfo.setHgap(10);
		personalInfo.setVgap(10);
		personalInfo.setPadding(new Insets(25, 25, 25, 25));
		
	    
	    //Success
	    GridPane success = new GridPane();
	    VBox spc = new VBox();
	    spc.setPrefWidth(90);
	    success.add(spc, 0, 0);
	    File sFile = new File("Images/Success/OK.png"); 							
		Image img = new Image(sFile.toURI().toString());
		Rectangle rect = new Rectangle(300,300);
		rect.setFill(new ImagePattern(img));
		success.add(rect, 1, 0);
		success.setAlignment(Pos.CENTER);
	    
		
		
		order.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				title.setText("CHOOSE YOUR BUN");
				borderPane.setCenter(buns.getBody());
				footer.setVisible(true);
				alignR.setVisible(true);
				previous.setVisible(false);
				currentPage++;
		
			}
		});
				
		next.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				switch(currentPage){
				
				case 1: title.setText("CHOOSE YOUR BUN");
				borderPane.setCenter(buns.getBody());
				previous.setVisible(false);
				currentPage++;
				break;
				
				case 2: title.setText("CHOOSE YOUR FILLING");
				borderPane.setCenter(fillings.getBody());
				previous.setVisible(true);
				currentPage++;
				break;
				
				case 3: title.setText("CHOOSE YOUR CHEESE");
				borderPane.setCenter(cheese.getBody());
				currentPage++;
				break;
				
				case 4: title.setText("CHOOSE YOUR TOPPINGS");
				borderPane.setCenter(toppings.getBody());
				currentPage++;
				break;
				
				case 5: title.setText("CHOOSE YOUR SAUCE");
				borderPane.setCenter(sauce.getBody());
				next.setText("Checkout");
				currentPage++;

				
				break;
				
				case 6: title.setText("Confirm Your Order");
				borderPane.setCenter(personalInfo);
				previous.setText("Change Order");
				next.setText("Confirm");
				//next.setVisible(false);
				
				int fontSize = 14;
				
				Text scenetitle = new Text("Please Enter Your Details");
				scenetitle.setFont(Font.font("Impact", FontWeight.LIGHT, 20));
				scenetitle.setFill(Color.WHITE);
				personalInfo.add(scenetitle, 0, 0, 2, 1);

				Label userName = new Label("Full Name:");
				userName.setFont(Font.font("Verdana", FontWeight.MEDIUM, fontSize));
				userName.setTextFill(Color.WHITE);
				personalInfo.add(userName, 0, 3);

				
				personalInfo.add(userTextField, 1, 3);
				
				
				Label phoneNumber = new Label("Phone Number:");
				phoneNumber.setFont(Font.font("Verdana", FontWeight.MEDIUM, fontSize));
				phoneNumber.setTextFill(Color.WHITE);
				personalInfo.add(phoneNumber, 0, 4);

				
				personalInfo.add(phoneField, 1, 4);
				
				
				Label emailTitle = new Label("Email:");
				emailTitle.setFont(Font.font("Verdana", FontWeight.MEDIUM, fontSize));
				emailTitle.setTextFill(Color.WHITE);
				personalInfo.add(emailTitle, 0, 5);

				
				personalInfo.add(emailField, 1, 5);
				
				Label addressTitle = new Label("Address:");
				addressTitle.setFont(Font.font("Verdana", FontWeight.MEDIUM, fontSize));
				addressTitle.setTextFill(Color.WHITE);
				personalInfo.add(addressTitle, 0, 6);

				
				personalInfo.add(addressField, 1, 6);
				
				currentPage++;

				
				break;
				
				case 7: title.setText("SUCCESS!");
				borderPane.setCenter(success);
				next.setVisible(false);
				previous.setVisible(false);
				alignR.setVisible(false);
				
				name = userTextField.getText();
				number = phoneField.getText();
				email = emailField.getText();
				address = addressField.getText();
				
				customerInfo.add(name);
				customerInfo.add(number);
				customerInfo.add(email);
				customerInfo.add(address);
				
				int custID = 0;
					try {
						custID = database.createUser(number, name, address, email);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
//				System.out.println(name);
//				System.out.println(number);
//				System.out.println(email);
//				System.out.println(address);
				
				// change to later
				try {
					database.submitOrder(orderArray, custID);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				}
			}
		});
		
		previous.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
			
				switch(currentPage){
				
				case 3: title.setText("CHOOSE YOUR BUNS");
				previous.setVisible(false);
				borderPane.setCenter(buns.getBody());
				currentPage--;
				break;
				
				case 4: title.setText("CHOOSE YOUR FILLINGS");
				borderPane.setCenter(fillings.getBody());
				currentPage--;
				break;
				
				case 5: title.setText("CHOOSE YOUR CHEESE");
				borderPane.setCenter(cheese.getBody());
				next.setText("Next");
				currentPage--;
				break;
				
				case 6: title.setText("CHOOSE YOUR TOPPINGS");
				borderPane.setCenter(toppings.getBody());
				next.setVisible(true);
				next.setText("Next");
				previous.setText("Back");
				currentPage--;
				break;
				
				
				case 7: title.setText("CHOOSE YOUR SAUCE");
				borderPane.setCenter(sauce.getBody());
				next.setVisible(true);
				next.setText("Checkout");
				previous.setText("Back");
				personalInfo.getChildren().removeAll(userTextField, phoneField, emailField, addressField);
				currentPage--;
				break;
				}		
					
			}
		});
		
		//Runs the scene
		
		
		
		
		Group root = new Group();
		root.getChildren().addAll(borderPane);
		Scene scene = new Scene(root);
		
		File f = new File("Images/Background/Choppingboard.png"); 							// Finds the  background image
		Image img2 = new Image(f.toURI().toString()); 										// Converts the file to an image
		scene.setFill(new ImagePattern(img2)); 
		
		primaryStage.setTitle("Rocket Burger");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void update() {
		orderBox.getChildren().clear();
		
		Text orderBoxTitle = new Text("Order");
		orderBoxTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		orderBoxTitle.setFill(Color.WHITE);
		orderBox.getChildren().addAll(orderBoxTitle);
		orderBox.setAlignment(Pos.TOP_CENTER);
		
		
		for (Ingredient i: orderArray) {
			HBox ingrdntBox = new HBox();
			
			Text ingrdnt = new Text(i.getName());
			ingrdnt.setFont(Font.font("Arial", 15));
			ingrdnt.setFill(Color.WHITE);
			
			ImageView cancel = new ImageView();
			try {
				cancel = new ImageView(new Image(new FileInputStream("Images/x.png")));
				cancel.setFitWidth(15);
				cancel.setFitHeight(15);
				ingrdntBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
					HBox parent = ingrdntBox;
					Ingredient ingr = i;
					
					@Override
					public void handle(MouseEvent event) {
						orderBox.getChildren().remove(parent);
	    				total -= ingr.getPrice();
	    				totalPrice.setText("Total: $" + new DecimalFormat("0.00").format(total)); 
						orderArray.remove(ingr);
	    				if(ingr.getStockCount() - Collections.frequency(orderArray, ingr)>0){
	    					buns.updateButtons(ingr);
	    					fillings.updateButtons(ingr);
	    					cheese.updateButtons(ingr);
	    					toppings.updateButtons(ingr);
	    					sauce.updateButtons(ingr);
	    		
	    				}
						
					}
				});
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//adding a spacer
			Pane spacer = new Pane();
			
			
			ingrdntBox.setHgrow(spacer, Priority.ALWAYS);
		    spacer.setMinSize(10, 1);
			ingrdntBox.getChildren().addAll(ingrdnt, spacer, cancel);
			ingrdntBox.setAlignment(Pos.CENTER_RIGHT);
			orderBox.getChildren().add(ingrdntBox);
			
			
		}
	}
	
	public static void main(String[] args) {
		launch(args);	
	}	
}