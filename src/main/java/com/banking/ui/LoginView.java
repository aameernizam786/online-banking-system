package com.banking.ui;



import com.banking.dao.UserDAO;
import com.banking.model.User;
import com.banking.util.PasswordUtil;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginView {
    private final UserDAO userDAO = new UserDAO();

    public void start(Stage stage) {
        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);

        Label userL = new Label("Username:");
        TextField userF = new TextField();
        Label passL = new Label("Password:");
        PasswordField passF = new PasswordField();
        Button loginBtn = new Button("Login");
        Label info = new Label();

        root.add(userL, 0, 0);
        root.add(userF, 1, 0);
        root.add(passL, 0, 1);
        root.add(passF, 1, 1);
        root.add(loginBtn, 1, 2);
        root.add(info, 1, 3);

        loginBtn.setOnAction(e -> {
            try {
                User u = userDAO.findByUsername(userF.getText().trim());
                if (u != null && PasswordUtil.verify(passF.getText(), u.getPasswordHash())) {
                    DashboardView dv = new DashboardView(u);
                    dv.start(new Stage());
                    stage.close();
                } else {
                    info.setText("Invalid credentials");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                info.setText("Error: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(root, 380, 200);
        stage.setTitle("Online Bank - Login");
        stage.setScene(scene);
        stage.show();
    }
}

