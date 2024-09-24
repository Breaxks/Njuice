package view;

import java.util.List;

import connect.Connect;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
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
import javafx.stage.Stage;
//import model.MsUser;
import model.TransactionDetail;
import model.TransactionHeader;

public class HistoryPage extends Application {
	
	Scene scene;
	BorderPane bp ;
	GridPane gp; 
	Label lblTransaction,lblTDetail, lblTotal;
//	Button btnCheckOut, btnDelete;
	
	 MenuBar menuBar;
	 Menu menu;
	 MenuItem menuItem, menuItem1, menuItem2,menuItem3 ;
	
	//Tabletransaction
	TableView<TransactionHeader> tblTrHeader;
	TableColumn<TransactionHeader, String> trId;
	TableColumn<TransactionHeader, String> email ;
	TableColumn<TransactionHeader, String> date;
	
	//tableDetail
	TableView<TransactionDetail> tblTrDetail;
	TableColumn<TransactionDetail, String> detailtrId;
	TableColumn<TransactionDetail, String> detailProduct;
	TableColumn<TransactionDetail, Integer> detailPrice;
	TableColumn<TransactionDetail, Integer> detailQty;
	TableColumn<TransactionDetail, Integer> detailTotal;
    private boolean isLoggedIn = true;
	
	
	
	public void init() {
		
	
		bp = new BorderPane();
		gp = new GridPane();
		
		//initcomponents
		lblTransaction= new Label("My Transaction");
		lblTDetail = new Label("Transaction Detail");
		lblTotal = new Label("Total Price: ");
		
		// init menubar
		
		
		//init tabletransaction
		tblTrHeader = new TableView<>();
		trId = new TableColumn<>("ID");
		email = new TableColumn<>("Email");
		date = new TableColumn<>("Date");
		tblTrHeader.getColumns().addAll(trId,email, date);
		
		//inittableDetail
		tblTrDetail = new TableView<>();
		detailtrId = new TableColumn<>("ID");
		detailProduct = new TableColumn<>("Product");
		detailPrice = new TableColumn<>("Price");
		detailQty = new TableColumn<>("Quantity");
		detailTotal = new TableColumn<>("Total Price");
		tblTrDetail.getColumns().addAll(detailtrId, detailProduct, detailPrice, detailQty, detailTotal);
		
		scene = new Scene(bp, 800,600);
		
	}
	
	public void addComponent () {
		
		gp.add(lblTransaction, 0, 0);
		gp.add(tblTrHeader, 0, 1);
		gp.add(lblTDetail, 1, 0);
		gp.add(tblTrDetail, 1, 1);
		gp.add(lblTotal, 1, 2);
		
		 gp.setAlignment(Pos.CENTER);
	        gp.setHgap(10);
	        gp.setVgap(30);
		
	}
	

	public void HistoryPage(Connect databaseConnection, int userId) {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		launch(args);

	}

	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(Stage arg0) throws Exception {
		
		init();
		addComponent();
		bp.setTop(gp);

		
		// ... (existing code)

        tblTrHeader.getSelectionModel().selectedItemProperty().addListener((obs, oldTransaction, newTransaction) -> {
            if (newTransaction != null) {
                // Fetch details of the selected transaction
                List<TransactionDetail> details = fetchTransactionDetails(newTransaction.getId());

                // Update tblTrDetail with the fetched details
                tblTrDetail.setItems(FXCollections.observableArrayList(details));

                // Calculate and display total price
               // int totalPrice = details.stream().mapToInt(detail -> detail.getPrice() * detail.getQty()).sum();
             //   lblTotal.setText("Total Price: " + totalPrice);
            } else {
                // Clear tblTrDetail if no transaction is selected
                tblTrDetail.setItems(null);
                lblTotal.setText("Total Price: ");
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

        BorderPane root = new BorderPane();
        root.setTop(menuBar);  // Set MenuBar as the first child

        gp = new GridPane();
        addComponent();
        root.setCenter(gp);  // Set gp as the second child

        Scene scene = new Scene(root, 800, 600);
        arg0.setScene(scene);
        arg0.show();

        homeMenuItem.setOnAction(e -> {
			try {
				navigateHome(arg0);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		});
        cartMenuItem.setOnAction(e -> {
			try {
				showCartPage(arg0);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        historyMenuItem.setOnAction(e -> navigateToHistoryPage());
        logoutMenuItem.setOnAction(e -> logout());
		
		arg0.setScene(scene);
	    arg0.setTitle("My History");
	    arg0.show();
	    
	    
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

	private Object navigateToHistoryPage() {
		return null;
	}

	private Object showCartPage(Stage primaryStage) throws Exception {
		CartPage cartPage = new CartPage();
		Stage cartStage = new Stage();
		cartPage.start(cartStage);
		primaryStage.close();
		return cartStage;
	}

	private Object navigateHome(Stage primaryStage) throws Exception {
    	UserPage userPage = new UserPage();
    	Stage userStage = new Stage();
    	userPage.start(userStage);
    	return userStage;
    }

	private List<TransactionDetail> fetchTransactionDetails(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
