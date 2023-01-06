package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.utils.DatabaseHandler;
import sample.model.Server;
import sample.model.User;

import java.io.IOException;
import java.util.ArrayList;

public class AddServer {

    public TextField RegServerName;
    public TextField RegServerAdr;
    public Button toAddServ;
    public Button toServForm;
    @FXML
    private Label account;

    @FXML
    private Label error;

    private User user;
    String serverAdr;
    String serverName;

    void showUser(User user) {
        String name = user.getNick();
        account.setText(name);
        this.user = user;
    }

    void showError(String er) {
        error.setText("Error: " + er);
    }

    public void addServ(javafx.event.ActionEvent event) throws IOException {
        serverAdr = RegServerAdr.getText().trim();
        serverName = RegServerName.getText().trim();
        if (!serverAdr.equals("") && !serverName.equals("")) {
            if (serverAdr.matches("^[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&//=]*)$")){
                addServToDb(serverAdr, serverName);
            }
            else{
                String er = "Incorrect format of input";
                System.out.println(er);
                showError(er);
            }
        } else {
            System.out.println("Fields Server Address or Server Name are empty");
            showError("Fields Server Address or Server Name are empty");
        }
    }

    void addServToDb(String serverAdr, String serverName) throws IOException {
        Server server = new Server();
        server.setServerAddress(serverAdr);
        server.setServerName(serverName);
        DatabaseHandler dt = new DatabaseHandler();
        User user1 = dt.getUserByNick(user.getNick());
        dt.addServer(server);
        server = dt.getServerByName(serverName);
        dt.addServerAndUser(server, user1);


        toServForm.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/ServerForm.fxml"));
        loader.load();
        ConnectToServer connectToServer = loader.getController();
        Parent root = loader.getRoot();
        connectToServer.showUser(user);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void toServForm(javafx.event.ActionEvent event) throws IOException {
        toServForm.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/ServerForm.fxml"));
        loader.load();
        ConnectToServer connectToServer = loader.getController();
        Parent root = loader.getRoot();
        connectToServer.showUser(user);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

}
