package view;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import connect.Connect;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.MsProduct;


public class ManageMsProductPage extends Application {
	
	private Connect databaseConnection;
    private TableView<MsProduct> productTable;
    private Label productIdLabel, productNameLabel, productMerkLabel, productPriceLabel, productStockLabel, totalLabel;
    private Spinner<Integer> quantitySpinner;
    private Button addToCartButton;
    private MenuBar menuBar;
    private Menu menu;
    private MenuItem menuItem, menuItem1, menuItem2,menuItem3 ;
    private boolean isLoggedIn = true;

    

    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Manage Product");

        // Creating DatabaseManager object
    //  databaseConnection = new Connect();

        // Creating BorderPane as the root layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Creating TableView to display products
        productTable = createProductTable();
        root.setLeft(productTable);

        // Creating GridPane for product details
        GridPane productDetailsPane = createProductDetailsPane();
        root.setCenter(productDetailsPane);

        // Displaying scene
        
        menuBar = new MenuBar();
        
        menu = new Menu("Admin");
        
        menuItem = new MenuItem("Manage Product");
        menuItem1 = new MenuItem("View History");
        menuItem2 = new MenuItem("Logout");
        
		menu.getItems().addAll(menuItem, menuItem1, menuItem2);
        menuBar.getMenus().add(menu);
        
		root.setTop(menuBar);
		
		menuItem.setOnAction(e -> showManage((primaryStage)));
		menuItem1.setOnAction(e -> {
			try {
				showHistory(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		menuItem2.setOnAction(e -> logout());

        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();	
        
	}

	private Object logout() {
		 // Menghapus sesi pengguna atau mengatur status logout
        isLoggedIn = false;

        // Mendapatkan referensi ke Stage yang menaungi UserDashboard
        Stage primaryStage = (Stage) menuBar.getScene().getWindow();

        // Contoh: Tampilkan dialog informasi dan tutup aplikasi
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Anda telah berhasil logout.");

        alert.setOnHidden(evt -> {
            // Buka halaman login
            Login loginWindow = new Login();
            Stage loginStage = new Stage();
            try {
                loginWindow.start(loginStage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Tutup aplikasi setelah dialog ditutup
            primaryStage.close();
        });

        alert.show();
		return alert;
	}

	private Object showHistory(Stage primaryStage) throws Exception {
		HistoryAdminPage historyAdminPage = new HistoryAdminPage();
		Stage historyStage = new Stage();
		historyAdminPage.start(historyStage); 
        primaryStage.close();
        return historyAdminPage;
	}

	private void showManage(Stage stage) {
		return;
	}

	private TableView<MsProduct> createProductTable() {
		TableView<MsProduct> table = new TableView<>();
        table.setEditable(true);

        // Defining columns
        TableColumn<MsProduct, String> nameCol = new TableColumn<>("Product Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<MsProduct, String> merkCol = new TableColumn<>("Product Merk");
        merkCol.setCellValueFactory(new PropertyValueFactory<>("productMerk"));

        TableColumn<MsProduct, Double> priceCol = new TableColumn<>("Product Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("produckPrice"));

        TableColumn<MsProduct, Integer> stockCol = new TableColumn<>("Product Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("productStock"));

        // Adding columns to table
        table.getColumns().addAll(nameCol, merkCol, priceCol, stockCol);

        // Handling row selection
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Update product details pane with selected product details
                updateProductDetailsPane(newSelection);
            }
        });

        // Populating table with data from the database
  //      refreshProductTable();

        return table;
	}
	
	
	private GridPane createProductDetailsPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        productIdLabel = new Label("Product ID:");
        productNameLabel = new Label("Product Name:");
        productMerkLabel = new Label("Product Merk:");
        productPriceLabel = new Label("Product Price:");
        productStockLabel = new Label("Product Stock:");
//        menuBar = new MenuBar();
       

        quantitySpinner = new Spinner<>(1, 99, 1); // Minimum value: 1, initial value: 1
        totalLabel = new Label("Total Price: $0.00");

        addToCartButton = new Button("Add to Cart");
        addToCartButton.setOnAction(event -> addToCart());

        // Adding components to the pane
//        pane.add(menuBar, 0, 0);
        pane.add(productIdLabel, 0, 1);
        pane.add(productNameLabel, 0, 2);
        pane.add(productMerkLabel, 0, 3);
        pane.add(productPriceLabel, 0, 4);
        pane.add(productStockLabel, 0, 5);
        pane.add(new Label("Quantity:"), 0, 6);
        pane.add(quantitySpinner, 1, 7);
        pane.add(totalLabel, 0, 8);
        pane.add(addToCartButton, 1, 9);
        
       
        

        return pane;
    }
	
	
	
	
	 private void updateProductDetailsPane(MsProduct product) {
	        productIdLabel.setText("Product ID: " + product.getProduckPrice());
	        productNameLabel.setText("Product Name: " + product.getProductName());
	        productMerkLabel.setText("Product Merk: " + product.getProductMerk());
	        productPriceLabel.setText("Product Price: $" + product.getProduckPrice());
	        productStockLabel.setText("Product Stock: " + product.getProductStock());
	        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, product.getProductStock(), 1));
	        updateTotalLabel();
	    }
	 
	 private void updateTotalLabel() {
	        int quantity = quantitySpinner.getValue();
	        MsProduct selectedProduct = productTable.getSelectionModel().getSelectedItem();
	        if (selectedProduct != null) {
	            double totalPrice = quantity * selectedProduct.getProduckPrice();
	            totalLabel.setText("Total Price: $" + String.format("%.2f", totalPrice));
	        }
	    }
	 
	 private void addToCart() {
	        MsProduct selectedProduct = productTable.getSelectionModel().getSelectedItem();

	        if (selectedProduct != null) {
	            int quantity = quantitySpinner.getValue();
	            int newStock = selectedProduct.getProductStock() - quantity;

	            if (newStock >= 0) {
	                String updateQuery = "UPDATE products SET productStock = ? WHERE productId = ?";
					databaseConnection.updateProductStock(updateQuery, newStock, selectedProduct.getProduckPrice());

					// TODO: Add the selected product to the user's cart in the database

					// Refresh the product table
//					refreshProductTable();

					// Reset the product details pane
					clearProductDetailsPane();

					// Show a success message or handle accordingly
					System.out.println("Product added to cart successfully!");
	            } else {
	                // Show a warning message
	                showAlert("Insufficient Stock", "Not enough stock available for the selected product.");
	            }
	        } else {
	            // Show a warning message
	            showAlert("Warning", "Please Choose 1 Item.");
	        }
	    }

	    private void clearProductDetailsPane() {
	        productIdLabel.setText("Product ID:");
	        productNameLabel.setText("Product Name:");
	        productMerkLabel.setText("Product Merk:");
	        productPriceLabel.setText("Product Price:");
	        productStockLabel.setText("Product Stock:");
	        quantitySpinner.getValueFactory().setValue(1);
	        totalLabel.setText("Total Price: $0.00");
	    }

	    private void showAlert(String title, String content) {
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(content);
	        alert.showAndWait();
	    }

		public static void display() {
			// TODO Auto-generated method stub
			
		}

		public void start(ManageMsProductPage managePage) {
			// TODO Auto-generated method stub
			
		}
	}
	



	

