package view;

import java.sql.SQLException;

import connect.Connect;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.MsUser;

public class Register extends Application {

	Label registerLabel, emailLabel, passwordLabel, confirmPasswordLabel, ageLabel, genderLabel, nationalityLabel;
    Spinner<Integer> ageSpinner;
    TextField emailField, ageField;
    PasswordField passwordField, confirmPasswordField;
    ToggleGroup genderGroup;
    RadioButton maleRadioButton, femaleRadioButton;
    ComboBox<String> nationalityComboBox;
    Button registerButton;
    GridPane gridPane;
    BorderPane root;
    
	private Connect databaseConnection;


    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeComponents();
        setLayout();
        addComponents();

        MenuBar menuBar = new MenuBar();
        Menu navigationMenu = new Menu("Page");
        MenuItem loginMenuItem = new MenuItem("Login");
        MenuItem registerMenuItem = new MenuItem("Register");
        navigationMenu.getItems().addAll(loginMenuItem, registerMenuItem);

        menuBar.getMenus().add(navigationMenu);

        loginMenuItem.setOnAction(e -> {
			try {
				showLoginScene(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        registerButton.setOnAction(e -> register());


        root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(gridPane);
     
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register");
        primaryStage.show();
    }

    private Object showLoginScene(Stage primaryStage) throws Exception {
    	Login login = new Login(); 
        Stage loginStage = new Stage(); 
        login.start(loginStage); // Call the start() method from the Main class
        primaryStage.close(); // Close the current stage (Register form)
		return loginStage;
	}

	private void initializeComponents() {
        // Initialize all UI components here
        registerLabel = new Label("Register");
        emailLabel = new Label("Email:");
        passwordLabel = new Label("Password:");
        confirmPasswordLabel = new Label("Confirm Password:");
        ageLabel = new Label("Age:");
        genderLabel = new Label("Gender:");
        nationalityLabel = new Label("Nationality:");
        ageSpinner = new Spinner<>();
        ageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99));
        emailField = new TextField();
        passwordField = new PasswordField();
        confirmPasswordField = new PasswordField();
        ageField = new TextField();
        genderGroup = new ToggleGroup();
        maleRadioButton = new RadioButton("Male");
        maleRadioButton.setToggleGroup(genderGroup);
        femaleRadioButton = new RadioButton("Female");
        femaleRadioButton.setToggleGroup(genderGroup);
        nationalityComboBox = new ComboBox<>();
        nationalityComboBox.getItems().addAll("China", "Vietnam", "Thailand", "Indonesia");
        registerButton = new Button("Register");
    }

    private void setLayout() {
        gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(10);
    }

    private void addComponents() {
    	gridPane.add(registerLabel, 0, 0, 4, 1);
        gridPane.add(emailLabel, 0, 1);
        gridPane.add(emailField, 1, 1);
        gridPane.add(genderLabel, 2, 1);
        gridPane.add(maleRadioButton, 3, 1);
        gridPane.add(femaleRadioButton, 4, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(nationalityLabel, 2, 2);
        gridPane.add(nationalityComboBox, 3, 2);
        gridPane.add(confirmPasswordLabel, 0, 3);
        gridPane.add(confirmPasswordField, 1, 3);
        gridPane.add(ageLabel, 0, 4);
        gridPane.add(ageSpinner, 1, 4);
        gridPane.add(registerButton, 3, 3);
    }

    private void register() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String gender = ((RadioButton) genderGroup.getSelectedToggle()).getText();
        String nationality = nationalityComboBox.getValue();
        int age = ageSpinner.getValue();
        String role = "user";

        if (password.equals(confirmPassword)) {
            // Panggil metode insertUser dengan informasi pengguna
			databaseConnection.insertUser(email, password, gender, nationality, age, role);

			// Menampilkan alert setelah registrasi berhasil
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Registration Successful");
			alert.setHeaderText(null);
			alert.setContentText("User registered successfully!");
			alert.showAndWait();

			// Mengarahkan pengguna ke halaman login
			Login login = new Login();
			Stage loginStage = new Stage();
			try {
				login.start(loginStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Menutup stage register (Register.java)
			Stage stage = (Stage) registerButton.getScene().getWindow();
			stage.close();
        } else {
            System.out.println("Password and Confirm Password do not match.");
        }
    }
    

    public static void main(String[] args) {
        launch(args);
    }

}
