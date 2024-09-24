package view;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connect.Connect;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.MsUser;

public class Login extends Application {
	Scene scene;

	BorderPane bContainer;

	Label lUsername, lPass, lLogin, lError;
	Button loginButton;
	GridPane gContainer;
	VBox titleContainer, loginContainer;

	GridPane gridPane;
	BorderPane root;

	TextField userInput;
	PasswordField passInput;

	private Connection con;

	public void initialize() {

		bContainer = new BorderPane();
		gContainer = new GridPane();

		lLogin = new Label("Login");
		lUsername = new Label("Username");
		lPass = new Label("Password");
		lError = new Label("Error!");

		loginButton = new Button("Login!");

		userInput = new TextField();
		passInput = new PasswordField();

		titleContainer = new VBox();
		loginContainer = new VBox();

		scene = new Scene(bContainer, 800, 400);
	}

	public void setLayout() {
//		loginContainer.getChildren().add(loginButton);
		bContainer.setCenter(gContainer);

		gContainer.setAlignment(Pos.CENTER);
		loginContainer.setAlignment(Pos.CENTER);
		titleContainer.setAlignment(Pos.CENTER);

		lLogin.setFont(Font.font(50));

		userInput.setMinWidth(250);
		passInput.setMinWidth(250);

		gContainer.setVgap(10);
		lError.setVisible(false);
		lError.setTextFill(Color.RED);
	}

	public void addComponent() {
		gContainer.add(titleContainer, 0, 0);
		gContainer.add(lUsername, 0, 1);
		gContainer.add(userInput, 0, 2);
		gContainer.add(lPass, 0, 3);
		gContainer.add(passInput, 0, 4);
		gContainer.add(lError, 0, 5);
		gContainer.add(loginContainer, 0, 7);
		gContainer.add(loginButton, 0, 8);
	}

	public void start(Stage primaryStage) throws Exception {
		initialize();
		addComponent();
		setLayout();

		MenuBar menuBar = new MenuBar();

		Menu navigationMenu = new Menu("Page");
		MenuItem loginMenuItem = new MenuItem("Login");
		MenuItem registerMenuItem = new MenuItem("Register");
		navigationMenu.getItems().addAll(loginMenuItem, registerMenuItem);

		menuBar.getMenus().add(navigationMenu);

		// Menambahkan aksi saat menu "Login" dipilih
		loginMenuItem.setOnAction(e -> showLoginScene(primaryStage));

		// Menambahkan aksi saat menu "Register" dipilih
		registerMenuItem.setOnAction(e -> {
			try {
				switchToRegisterScene(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		loginButton.setOnAction(e -> {
			try {
				loginButton();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		root = new BorderPane(); // Create the root node
		root.setTop(menuBar); // Add menu bar to the top of the root
		root.setCenter(gContainer); // Set the GridPane as the center content

		scene = new Scene(root, 800, 400); // Create scene using the root node

		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.show();

	}

	private void loginButton() {
		String email = userInput.getText();
	    String password = passInput.getText();

	    if (email.isEmpty() || password.isEmpty()) {
	        // Show an alert indicating which field(s) need to be filled
	        String alertMessage = "Please fill in the following field(s):\n";
	        if (email.isEmpty()) {
	            alertMessage += "- Email\n";
	        }
	        if (password.isEmpty()) {
	            alertMessage += "- Password\n";
	        }

	        showAlert(AlertType.ERROR, "Empty Fields", null, alertMessage);
	        return; // Exit the method, as login should not proceed
	    }

	    try {
	        if (con == null || con.isClosed()) {
	            // If the connection is null or closed, attempt to reconnect
	            con = Connect.getConnection().con;
	        }

	        String query = "SELECT * FROM msuser WHERE UserEmail = ? AND UserPassword = ?";
	        try (PreparedStatement statement = con.prepareStatement(query)) {
	            statement.setString(1, email);
	            statement.setString(2, password);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                	String role = resultSet.getString("UserRole");
						if ("admin".equalsIgnoreCase(role)) {
							System.out.println("Login berhasil sebagai ADMIN");
							try {
								showAdminDashboard();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if ("user".equalsIgnoreCase("AdminRole")) {
							System.out.println("Login berhasil sebagai USER!");
							showUserDashboard();
						} else {
							System.out.println("Login berhasil dengan role: " + role);
						}
	                } else {
	                    showAlert(AlertType.ERROR, "Invalid Credentials", null, "Incorrect email or password.");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        }
	    }
		
		
		
		
		
		
		
		
//		
//		
//		
//		
//		String email = userInput.getText();
//		String password = passInput.getText();
//		
//		
//
//		try {
//			
//			  if (con == null || con.isClosed()) {
//		            // If the connection is null or closed, attempt to reconnect
//		            con = Connect.getConnection().con;
//		        }
//
//			String query = "SELECT * FROM msuser WHERE UserEmail = ? AND UserPassword = ?";
//			try (PreparedStatement statement = con.prepareStatement(query)) {
//				statement.setString(1, email);
//				statement.setString(2, password);
//
//				try (ResultSet resultSet = statement.executeQuery()) {
//					if (resultSet.next()) {
//
//						String role = resultSet.getString("UserRole");
//						if ("admin".equalsIgnoreCase(role)) {
//							System.out.println("Login berhasil sebagai ADMIN");
//							try {
//								showAdminDashboard();
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						} else if ("user".equalsIgnoreCase("AdminRole")) {
//							System.out.println("Login berhasil sebagai USER!");
//							showUserDashboard();
//						} else {
//							System.out.println("Login berhasil dengan role: " + role);
//						}
//					} else {
////						showAlert(AlertType.ERROR, "Wrong Password Or Email", null,
////								"Email or Password must be Filled!");
//					}
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	private void showAdminDashboard() throws Exception {
		ManageMsProductPage AdminDash = new ManageMsProductPage(); // Membuat objek AdminDashboard
		Stage PageStage = new Stage(); // Membuat stage baru untuk dashboard admin
		AdminDash.start(PageStage); // Memanggil metode start() dari objek Admin Page

		// Menutup stage login (App.java)
		Stage stage = (Stage) loginButton.getScene().getWindow();
		stage.close();
	}

	private void showUserDashboard() {
		UserPage UserDash = new UserPage(); // membuat objek user
		Stage PageStage = new Stage(); // Membuat stage baru untuk dashboard admin
		try {
			UserDash.start(PageStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Memanggil metode start() dari objek

		// Menutup stage login (App.java)
		Stage stage = (Stage) loginButton.getScene().getWindow();
		stage.close();
	}

	private Object switchToRegisterScene(Stage primaryStage) throws Exception {
		Register register = new Register(); // Membuat objek Register
		Stage registerStage = new Stage(); // Membuat stage baru untuk register
		register.start(registerStage); // Memanggil metode start() dari objek Register
		primaryStage.close(); // Menutup stage login (App.java)
		return registerStage;
	}

	private Object showLoginScene(Stage primaryStage) {
		return null;
	}

	public Parent getRoot() {
		return root;
	}

	private void showAlert(AlertType errorType, String Title, String HeaderText, String ContentText) {
		Alert alert = new Alert(errorType);
		alert.setTitle(Title);
		alert.setHeaderText(HeaderText);
		alert.setContentText(ContentText);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
