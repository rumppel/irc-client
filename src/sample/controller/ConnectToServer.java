package sample.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.utils.DatabaseHandler;

import java.io.IOException;

import java.net.*;
import java.util.*;

public class ConnectToServer {

    @FXML
    private TextField SignInPort;

    @FXML
    private ComboBox<Server> SignInServer;

    @FXML
    private Button ToChat;

    @FXML
    private Label account;

    @FXML
    private Label error;

    @FXML
    private Button toAddServ;

    private User user;

    @FXML
    void initialize() {
    }

    void showUser(User user) {
        String name = user.getNick();
        account.setText(name);
        this.user = user;
        getServers();
    }

    void showError(String er) {
        error.setText("Error: " + er);
    }

    void getServers(){
        DatabaseHandler dt = new DatabaseHandler();
        ArrayList<Server> servers = dt.getUserServersArray(user);
        System.out.println(servers);
        for (Server server : servers) {
            SignInServer.getItems().add(server);
        }
    }

    @FXML
    void getServerNPort(ActionEvent event) throws IOException {
        String serverstr = String.valueOf(SignInServer.getValue());
        String portstr = SignInPort.getText().trim();
        if (!serverstr.equals("") && !portstr.equals("")) {
            if (portstr.matches("[0-9]+")){
                int port = Integer.parseInt(SignInPort.getText().trim());
                connectToServ(serverstr, port);
            }
            else{
                String er = "Incorrect format of input (server or port)";
                System.out.println(er);
                showError(er);
            }
        } else {
            System.out.println("Fields Server or Port are empty");
            showError("Fields Server or Port are empty");
        }
    }

    private void connectToServ (String serverstr, int port) throws IOException {
        DatabaseHandler dt = new DatabaseHandler();
        Server server = dt.getServerByName(serverstr);
        server.setServerName(serverstr);
        System.out.println("address: "+server.getServerAddress());
        Socket socket = new Socket(server.getServerAddress(), port);
        System.out.println("Connected to server: " + server.getServerName() + " | PORT: " + port);
        goToChat(socket);
    }

    private void goToChat(Socket socket) throws IOException {
        ToChat.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/Client.fxml"));
        loader.load();
        IRCClient IRCClient = loader.getController();
        IRCClient.showUser(user);
        IRCClient.showServer(socket);
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void addServ(ActionEvent event) throws IOException {
        toAddServ.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/AddServer.fxml"));
        loader.load();
        AddServer addServer = loader.getController();
        Parent root = loader.getRoot();
        addServer.showUser(user);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}