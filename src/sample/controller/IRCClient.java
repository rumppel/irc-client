package sample.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.utils.DatabaseHandler;

import java.io.IOException;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

import sample.model.*;
import sample.utils.SingletonFXMLLoader;

public class IRCClient{
    @FXML
    public TextArea channelArea;
    public Tab channelTab;
    public Tab serverTab;
    public TextArea users;
    public TextArea topic;
    public TextArea aboutArea;

    @FXML
    TextArea serverArea;

    @FXML
    private Label servertext;

    @FXML
    private Label porttext;

    @FXML
    private Label account;

    @FXML
    private TextField Usermes;


    @FXML
    private TabPane tabPane;

    @FXML
    private Button Connect;

    private BufferedWriter writer;
    private String username;
    private String nick;
    private String realname;
    private User user;
    private Server server;
    private Socket socket;
    private String currentChannel;
    private Channel channel;

    void showUser(User user) {
        String name = user.getNick();
        account.setText(name);
        System.out.println("nick: "+ user.getNick());
        this.user = user;
    }
    void showServer(Socket socket){
        String servname = "Server: " + socket.getInetAddress().getHostName();
        DatabaseHandler dt = new DatabaseHandler();
        this.server = dt.getServerByName(servname);
        String port = "Port: " + socket.getPort();
        servertext.setText(servname);
        porttext.setText(port);
        this.socket = socket;
        serverArea.appendText("морис я так більше не можу\n");
    }

    @FXML
    void connectToServ(ActionEvent event)throws IOException{
        nick = user.getNick();
        username = nick + "User";
        realname = nick + "Real";

        Usermes.setVisible(true);
        Connect.setVisible(false);

        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("NICK " + nick + "\r\n");
        writer.write("USER " + username + " 8 * :" + realname + "\r\n");
        writer.flush();

        SingletonFXMLLoader.getInstance().clientFXMLLoader = this;

        ServerListener listener = new ServerListener(socket);
        listener.start();
        System.out.println("Listener started!");
    }

    @FXML
    void onEnter(ActionEvent event) throws IOException {
        currentChannel = tabPane.getSelectionModel().getSelectedItem().getText();
        String usermes = Usermes.getText().trim();
        Usermes.clear();
        Message message = new Message(usermes, currentChannel);
        message.setChannelName(currentChannel);
        String command = message.convertToCommand();
        updateClientAfterCom(command);
        Thread sender = new ServerSender(socket, message);
        sender.start();
    }

    void updateClientAfterCom(String command){
        if (command.startsWith("JOIN")){
            channelTab.setText(command.substring(6));
            channelArea.setText("");
            tabPane.getSelectionModel().select(channelTab);
            channel = new Channel(command.substring(6));
        }
        else if (command.startsWith("PART")){
            channelTab.setText("No channel");
            tabPane.getSelectionModel().select(serverTab);
            channelArea.setText("");
        }
    }

    void getUsers(String serverAnswer){
        users.setText("");
        ArrayList<String> userslist = channel.getCurrentUsers(serverAnswer);
        for (String user: userslist){
            users.appendText(user+"\n");
        }
    }
    void getChannelTopic(String serverAnswer){
        topic.setText("");
        topic.appendText(channel.getCurrentTopic(serverAnswer)+"\n");
    }
    void pingResponse(){
        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("PONG " + "pingcontents" + "\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void onUserMsg(String serverAnswer, Message message){
        int index = serverAnswer.indexOf("#");
        String line = serverAnswer.substring(index);
        String[] linee= line.split(" ");
        TabPane tabPane = channelTab.getTabPane();
        if (("#"+channelTab.getText()).equalsIgnoreCase(linee[0])){
            tabPane.getSelectionModel().select(channelTab);
            channelArea.appendText(message.convertToMes()+"\n");
            if (serverAnswer.contains("PART")||serverAnswer.contains("JOIN")){
                try {
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write("NAMES #" + channelTab.getText() + "\r\n");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            serverArea.appendText(message.convertToMes()+"\n");
        }
    }

    void updateClientIfAction(String serverAnswer, Message message){
        if (serverAnswer.contains("JOIN")||serverAnswer.contains("PART")|| (serverAnswer.contains("PRIVMSG")&serverAnswer.contains("#"))) {
            onUserMsg(serverAnswer, message);
        }else if (serverAnswer.startsWith("PING")){
            pingResponse();
        }else if ((serverAnswer.contains("/NAMES list"))||(serverAnswer.contains("353"))){
            System.out.println("Server talkin: " + serverAnswer);
        }
        else{
            serverArea.appendText(message.convertToMes() + "\n");
        }
        if((serverAnswer.contains("353"))&&(serverAnswer.contains("#"+channelTab.getText()))){
            getUsers(serverAnswer);
        }
        if ((serverAnswer.contains("332"))&&(serverAnswer.contains("#"+channelTab.getText()))){
            getChannelTopic(serverAnswer);
        }
    }

    @FXML
    void initialize() {
        String str = "This is IRC-client made by Sofiia Liemieshova as coursework. " +
                "You'll be authorized automatically with entered in UserForm nickname. " +
                "You can only see messages from one channel at a time. Client automatically responds to Server`s ping" +
                "After connecting to server via Connect button, you can use this commands: \n" +
                "message  - to sent message in current channel;\n"+
                "/join #channel  - to join entered channel;\n"+
                "/part #channel  - to leave entered channel;\n"+
                "@NickName message  - to send message to user NickName;\n"+
                "/names  - you can not use this command, but you can see users of current channel on the right;\n"+
                "/topic  - you can not use this command, but you can see topic of current channel on the right;\n"+
                "/quit  - to disconnect from server and close client;\n";
        aboutArea.setText(str);
    }

}