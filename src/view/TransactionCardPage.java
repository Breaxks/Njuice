package view;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//import javafx.stage.Window;

public class TransactionCardPage extends Application{
	
	Scene scene;
	BorderPane bp ;
	GridPane gp; 
	VBox vbCard;
	Label lblTitle, lblProduct, lblCourier, lblTotal; 
	ComboBox<String> couriercmb;
	CheckBox chck; 
	Button btnco;
	
	public void init() {
		
		bp = new BorderPane();
		gp = new GridPane();
		vbCard = new VBox();
//		scene = new Scene(bp, 800,400);
//		scene.setFill(Color.SKYBLUE);
		
		couriercmb = new ComboBox<>();
        couriercmb.getItems().addAll("Nanji Express", "Gejok", "J&E", "JET");
        
     // Create CheckBox
        chck = new CheckBox("Use Insurance");
		
		lblTitle = new Label("List");
		lblProduct = new Label("Product Detail");
		lblCourier = new Label("Courier");
		lblTotal = new Label("Total Price : ");
		
		btnco = new Button("Checkout");
		
		scene = new Scene(bp, 800,400);
		scene.setFill(Color.SKYBLUE);
		
	}

	public void addComponent () {
		
		gp.add(lblTitle, 0, 0);

        gp.add(lblProduct, 0, 1);
        gp.add(lblCourier, 0, 2);
        gp.add(couriercmb, 0, 3);
        gp.add(lblTotal, 0, 5);
        gp.add(chck, 0, 4);
        gp.add(btnco, 0, 6);
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(10);
        gp.setVgap(30);
        gp.setPadding(new Insets(10));
        
        chck.selectedProperty().addListener((observable, oldValue, newValue) -> {
            long totalPrice = Long.parseLong(lblTotal.getText().substring(13));
            if (newValue) {
                totalPrice += 90000;
            } else {
                totalPrice -= 90000;
            }
            lblTotal.setText("Total Price : " + totalPrice);
        });
        
        btnco.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure want to Checkout all the item?");
            alert.setContentText("Need Confirmation");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                ButtonType buttonType = result.get();
                if (buttonType == ButtonType.OK) {
                    // Generate transaction ID
                    String transactionID = generateTransactionID();

                    // Process checkout logic (including cart clearing)
                    System.out.println("Order confirmed! Transaction ID: " + transactionID);
                }

                // Navigate to CartPage class
                try {
                    Stage cartStage = new Stage(); // Create a new Stage
                    CartPage cartPage = new CartPage();
                    cartPage.start(cartStage); // Pass the new Stage
                    closeCurrentStage(); // Close the current stage
                } catch (Exception exception) {
                    // Handle errors launching CartPage
                    exception.printStackTrace();
                }
            }
            
        });
        
	}
	
	private void closeCurrentStage() {
		Stage stage = (Stage) btnco.getScene().getWindow();
	    stage.close();		
	}

	private String generateTransactionID() {
	    // Implement your logic to generate a unique transaction ID with the format THXXX
	    // Example:
	    int nextTransactionIndex = 1; // Assuming you have a way to track the next index
	    String transactionID = "TH" + String.format("%03d", nextTransactionIndex);
	    // Increment nextTransactionIndex for the next transaction
	    return transactionID;
	}

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		
		init(); 
		
		
		bp.setTop(gp);
        bp.setCenter(vbCard); // initially empty, used for Transaction Card
        
		addComponent(); 
		 arg0.setScene(scene);
	      arg0.setTitle("Transaction Card Pop-up");
	      arg0.show();
		
	}

}
