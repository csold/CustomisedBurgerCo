package chef;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import common.Ingredient;
import common.Order;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Docket extends BorderPane {

	private Text status;
	private VBox title;
	private ScrollPane body;
	private HBox footer;
	private Order order;

	public Docket(Order order) {
		this.order = order;

		// Title
		title = new VBox(); {
			
			HBox top = new HBox(); {
				Text customer = new Text(order.getCustomer() == null ? "Guest" : order.getCustomer().getName());
				customer.setFill(Color.WHITE);
				customer.setFont(Font.font(26));
				
				Pane spacer = new Pane();
				HBox.setHgrow(spacer, Priority.ALWAYS);
				spacer.setMinSize(10, 1);
				
				top.getChildren().addAll(customer, spacer);
			}
			
			HBox bottom = new HBox(); {
				status = new Text("{STATUS}");
				status.setFont(Font.font(20));
				status.setFill(Color.WHITE);
	
				Pane spacer = new Pane();
				HBox.setHgrow(spacer, Priority.ALWAYS);
				spacer.setMinSize(10, 1);
				
				Text time = new Text(new SimpleDateFormat("HH:mm:ss").format(new Date(order.getTimestamp())));
				time.setFont(Font.font(20));
				time.setFill(Color.WHITE);
				
				bottom.getChildren().addAll(status, spacer, time);
			}
			
			title.getChildren().addAll(top, bottom);
		}

		// Ingredients
		body = new ScrollPane();
		VBox bodyContent = new VBox();
		{
			for (Ingredient i : order.getIngredients()) {
				HBox ingredient = new HBox();
				
				Text name = new Text(i.getName());
				name.setFont(Font.font(16));
				name.setFill(Color.WHITE);
				
				Text price = new Text("$" + new DecimalFormat("0.00").format(i.getPrice()));
				price.setFont(Font.font(16));
				price.setFill(Color.WHITE);

				Pane spacer = new Pane();
				HBox.setHgrow(spacer, Priority.ALWAYS);
				spacer.setMinSize(10, 1);
				
				ingredient.getChildren().addAll(name, spacer, price);
				bodyContent.getChildren().add(ingredient);
			}
			body.setContent(bodyContent);
			body.setHbarPolicy(ScrollBarPolicy.NEVER);
			body.setFitToWidth(true);
			body.setStyle("-fx-background:black;-fx-padding:10;");
			bodyContent.setStyle("-fx-background-color:transparent;");
			body.setMaxHeight(Double.MAX_VALUE);
			
		}
		
		// Footer
		footer = new HBox();
		{
			Text desc = new Text("Total cost:");
			desc.setFont(Font.font(18));
			desc.setFill(Color.WHITE);
			
			Pane spacer = new Pane();
			HBox.setHgrow(spacer, Priority.ALWAYS);
			spacer.setMinSize(10, 1);
			
			Text total = new Text("$" + new DecimalFormat("0.00").format(this.order.getTotalCost()));
			total.setFont(Font.font(18));
			total.setFill(Color.WHITE);

			footer.getChildren().addAll(desc, spacer, total);
			footer.setStyle("-fx-background-color:transparent;-fx-padding:10;-fx-border-color:white;-fx-border-width:2 0 0 0;");
		}

		this.setTop(title);
		this.setCenter(body);
		this.setBottom(footer);
		
		this.updateStyle();

		this.setOnMousePressed(e -> {
			try {
				if (e.isPrimaryButtonDown()) {
					if (this.order.getStatus() == 0 || this.order.getStatus() == 2) {
						this.order.setStatus(1);
					} else if (this.order.getStatus() == 1) {
						this.order.setStatus(2);
					}
				} else if (e.isSecondaryButtonDown()) {
					this.order.setStatus(0);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				this.updateStyle();
			}
		});
	}

	private void updateStyle() {
		switch (this.order.getStatus()) {
		case 0:
			this.setStyle("-fx-background-color:black;-fx-border-color:white;-fx-border-width:2;");
			this.title.setStyle("-fx-background-color:black;-fx-padding:10;-fx-border-color:white;-fx-border-width:0 0 2 0;");
			this.status.setText("Waiting");
			break;
		case 1:
			this.setStyle("-fx-background-color:black;-fx-border-color:white;-fx-border-width:2;");
			this.title.setStyle("-fx-background-color:blue;-fx-padding:10;-fx-border-color:white;-fx-border-width:0 0 2 0;");
			this.status.setText("In Progress");
			break;
		case 2:
			this.setStyle("-fx-background-color:black;-fx-border-color:white;-fx-border-width:2;");
			this.title.setStyle("-fx-background-color:green;-fx-padding:10;-fx-border-color:white;-fx-border-width:0 0 2 0;");
			this.status.setText("Ready");
			break;
		}
	}

}
