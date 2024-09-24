package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MsUser;

public class UserPage extends Application {
    private BorderPane bp;
    private GridPane gp;
    private Label lblTitle, lblProductName, lblProductBrand, lblProductPrice, lblQuantity, lblTotalPrice;
    private Spinner<Integer> spQty;
    private Button addToCartButton;
    private TableView<MsProduct> tblProductList;
    private TableColumn<MsProduct, Integer> idCol;
    private TableColumn<MsProduct, String> nameCol;
    private TableColumn<MsProduct, String> brandCol;
    private TableColumn<MsProduct, Double> priceCol;
    private MenuBar menuBar;
    private boolean isLoggedIn = true;

    public void init() {
        bp = new BorderPane();
        gp = new GridPane();

        // Labels
        lblTitle = new Label("Product List");
        lblProductName = new Label("Product Name:");
        lblProductBrand = new Label("Product Brand:");
        lblProductPrice = new Label("Product Price:");
        lblQuantity = new Label("Quantity:");
        lblTotalPrice = new Label("Total Price:");

        // Spinner and Button
        spQty = new Spinner<>(1, 99, 1);
        addToCartButton = new Button("Add To Cart");

        // TableView
        tblProductList = new TableView<>();
        idCol = new TableColumn<>("ID");
        nameCol = new TableColumn<>("Name");
        brandCol = new TableColumn<>("Brand");
        priceCol = new TableColumn<>("Price");

        tblProductList.getColumns().addAll(idCol, nameCol, brandCol, priceCol);
    }

    public void addComponent() {
    	gp.add(lblTitle, 0, 0);

    	VBox leftColumn = new VBox(5);
    	leftColumn.getChildren().addAll(tblProductList);

    	VBox rightColumn = new VBox(5);

    	// Create HBoxes for labels and corresponding controls
    	HBox productNameBox = new HBox(5);
    	productNameBox.getChildren().addAll(new Label(""), lblProductName);

    	HBox productBrandBox = new HBox(5); // Add the HBox for brand
    	productBrandBox.getChildren().addAll(new Label(""), lblProductBrand); // Add the product brand label

    	HBox productPriceBox = new HBox(5);
    	productPriceBox.getChildren().addAll(new Label(""), lblProductPrice);

    	rightColumn.getChildren().addAll(productBrandBox, productNameBox, productPriceBox); // Arrange in the desired order

    	HBox quantityBox = new HBox(5);
    	quantityBox.getChildren().addAll(new Label(" Quantity:"), spQty);
    	rightColumn.getChildren().add(quantityBox);

    	HBox totalPriceBox = new HBox(5);
    	totalPriceBox.getChildren().addAll(new Label(""), lblTotalPrice);
    	rightColumn.getChildren().add(totalPriceBox);

    	rightColumn.getChildren().add(addToCartButton);

    	gp.add(leftColumn, 0, 1);
    	gp.add(rightColumn, 1, 1);

    	gp.setAlignment(Pos.CENTER);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	primaryStage.setTitle("User Page");
        init();
        createProductTable(); // Create an empty table

        menuBar = new MenuBar();

        Menu pageMenu = new Menu("Page");
        MenuItem homeMenuItem = new MenuItem("Home");
        MenuItem cartMenuItem = new MenuItem("Cart");
        MenuItem historyMenuItem = new MenuItem("History");
        MenuItem logoutMenuItem = new MenuItem("Logout");

        pageMenu.getItems().addAll(homeMenuItem, cartMenuItem, historyMenuItem, logoutMenuItem);
        menuBar.getMenus().add(pageMenu);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);  // Set MenuBar as the first child

        gp = new GridPane();
        addComponent();
        root.setCenter(gp);  // Set gp as the second child

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        homeMenuItem.setOnAction(e -> navigateHome());
        cartMenuItem.setOnAction(e -> {
			try {
				showCartPage(primaryStage);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		});
        historyMenuItem.setOnAction(e -> {
			try {
				navigateToHistoryPage(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        logoutMenuItem.setOnAction(e -> logout());

    }

    private void logout() {
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
	}

	private Object navigateToHistoryPage(Stage primaryStage) throws Exception {
		int userId = MsUser.getCurrentUserId(); // Gantilah ini sesuai logika aplikasi Anda

        // Membuat objek HistoryPage dan menampilkannya
		
        HistoryPage historyPage = new HistoryPage();
        Stage historyStage = new Stage();
        historyPage.start(historyStage);
		primaryStage.close();
        return historyStage;
   
    }


	private Object showCartPage(Stage primaryStage) throws Exception {
		CartPage cartPage = new CartPage();
		Stage cartStage = new Stage();
		cartPage.start(cartStage);
		primaryStage.close();
		return cartStage;
	}


	private void navigateHome() {
		return;
	}

	
	private void createProductTable() {
        // Create an empty list for demonstration purposes
        tblProductList.setItems(FXCollections.observableArrayList());

        // Set up the columns
        idCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
    }

    // Placeholder class, replace it with your actual MsProduct class
    private static class MsProduct {
        private final int productId;
        private final String productName;
        private final String productBrand;
        private final double productPrice;

        public MsProduct(int productId, String productName, String productBrand, double productPrice) {
            this.productId = productId;
            this.productName = productName;
            this.productBrand = productBrand;
            this.productPrice = productPrice;
        }

        public int getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public String getProductBrand() {
            return productBrand;
        }

        public double getProductPrice() {
            return productPrice;
        }
    }

	public void display() {
		// TODO Auto-generated method stub
		
	}
}
