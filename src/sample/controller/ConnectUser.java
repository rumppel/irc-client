package sample.controller;
import javafx.scene.control.Label;
import sample.utils.DatabaseHandler;
import sample.model.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectUser {
    @FXML
    private TextField SignInNick;

    @FXML
    private PasswordField SignInPassword;

    @FXML
    private Button ToForm2;

    @FXML
    private Label error;

    @FXML
    void getNickNPass(ActionEvent event) {
        String nick = SignInNick.getText().trim();
        String password = SignInPassword.getText().trim();
        if(!nick.equals("") && !password.equals("")){
            System.out.println(nick + " " + password);
            connectUser(nick, password);
        }else {
            System.out.println("Fields Nick or Password are empty");
            showError("Fields Nick or Password are empty");
        }
    }

    private void connectUser(String nick, String password) {
        DatabaseHandler dt = new DatabaseHandler();
        User user = new User();
        user.setNick(nick);
        user.setPassword(password);
        ResultSet res = dt.getUser(user);
        int c = 0;
        try {
            while (res.next()){
                user.setIduser(res.getInt("USER_ID"));
                user.setNick(res.getString("USER_NICK"));
                user.setPassword(res.getString("USER_PASS"));
                c++;
                break;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            showError(e.getMessage());
        }
        if(c!=0){
            System.out.println("Success authorization");
            toForm2(user);
        }
        else {
            System.out.println("Incorrect nickname or password. Trying to sign up...");
            showError("Incorrect nickname or password. Trying to sign up...");
            dt.signUpUser(user);
            System.out.println("Success registration");
            toForm2(user);
        }
    }

    @FXML
    private void toForm2(User user) {
        ToForm2.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/ServerForm.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConnectToServer connectToServer = loader.getController();
        connectToServer.showUser(user);
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    void showError(String er){
        error.setText("Error: " + er);
    }
}

