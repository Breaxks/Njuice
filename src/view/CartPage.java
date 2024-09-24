package view;

import connect.Connect;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CartTable;
import model.MsUser;

public class CartPage extends Application {

    private MenuBar menuBar;
    Scene scene;
    BorderPane bp;
    GridPane gp;
    Label lblTitle, lblTotal, lblName, lblBrand, lblPrice;
    Button btnCheckOut, btnDelete;

    TableView<CartTable> tblCartList;
    TableColumn<CartTable, String> name;
    TableColumn<CartTable, String> brand;
    TableColumn<CartTable, Integer> price;
    TableColumn<CartTable, Integer> quantity;
    TableColumn<CartTable, Integer> total;

    private boolean isLoggedIn = true;

    public void init() {
        bp = new BorderPane();
        gp = new GridPane();
        scene = new Scene(bp, 800, 600);

        lblTitle = new Label("Your Cart List");
        lblName = new Label("Name :");
        lblBrand = new Label("Brand :");
        lblPrice = new Label("Price : ");
        lblTotal = new Label("Total Price : ");
        btnCheckOut = new Button("Checkout");
        btnDelete = new Button("Delete Product");
    }

    public void addComponent() {
        bp.setTop(gp);

        gp.add(lblTitle, 0, 0);
        gp.add(lblName, 0, 1);
        gp.add(lblBrand, 0, 2);
        gp.add(lblPrice, 0, 3);
        gp.add(lblTotal, 0, 4);
        gp.add(btnCheckOut, 0, 5);
        gp.add(btnDelete, 0, 6);

        gp.setAlignment(Pos.CENTER);
    }

    @Override
    public void start(Stage arg0) throws Exception {
        init();
        addComponent();

        VBox vbox = new VBox();
        vbox.getChildren().add(createProductTable());
        vbox.getChildren().add(gp);

        bp.setCenter(vbox);
        bp.setBottom(gp); // Place gp at the bottom of the BorderPane

        btnCheckOut.setOnAction(event -> {
            if (tblCartList.getItems().isEmpty()) {
                // Show warning if the cart is empty
                showWarningAlert("Your cart is empty.");
            } else {
                // Navigate to Checkout Cart Scene (implementation not shown)
                // You'll need to provide the implementation for this part
                // For example:
                // CheckoutCartScene checkoutScene = new CheckoutCartScene(...);
                // arg0.setScene(checkoutScene.getScene());
                // arg0.show();
            }
        });

        menuBar = new MenuBar();

        Menu pageMenu = new Menu("Page");
        MenuItem homeMenuItem = new MenuItem("Home");
        MenuItem cartMenuItem = new MenuItem("Cart");
        MenuItem historyMenuItem = new MenuItem("History");
        MenuItem logoutMenuItem = new MenuItem("Logout");

        pageMenu.getItems().addAll(homeMenuItem, cartMenuItem, historyMenuItem, logoutMenuItem);
        menuBar.getMenus().add(pageMenu);

        bp.setTop(menuBar);

        homeMenuItem.setOnAction(e -> {
			try {
				navigateHome(arg0);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        cartMenuItem.setOnAction(e -> showCartPage());

        historyMenuItem.setOnAction(e -> {
			try {
				navigateToHistoryPage(arg0);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        logoutMenuItem.setOnAction(e -> logout());

        arg0.setScene(scene);
        arg0.setTitle("Cart");
        arg0.show();
    }

    private Object navigateHome(Stage primaryStage) throws Exception {
    	UserPage userPage = new UserPage();
    	Stage userStage = new Stage();
    	userPage.start(userStage);
    	return userStage;
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
		
        HistoryPage historyPage = new HistoryPage();
        Stage historyStage = new Stage();
        historyPage.start(historyStage);
		primaryStage.close();
        return historyStage;
   
    }

    private void showCartPage() {
    	return;
    }

    private TableView<CartTable> createProductTable() {
        tblCartList = new TableView<>();
        name = new TableColumn<>("Name");
        brand = new TableColumn<>("Brand");
        price = new TableColumn<>("Price");
        quantity = new TableColumn<>("Quantity");
        total = new TableColumn<>("Total");
        tblCartList.getColumns().addAll(name, brand, price, quantity, total);
        return tblCartList;
    }

    private void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
    public static void main(String[] args) {
        launch(args);
    }

	public static void display() {
		// TODO Auto-generated method stub
		
	}
}
