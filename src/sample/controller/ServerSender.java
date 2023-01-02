package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import sample.model.Message;
import sample.model.SingletonFXMLLoader;

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ServerSender extends Thread {

    Socket ircSocket;
    String serverCommand = null;
    BufferedWriter out = null;
    BufferedReader stdIn = null;
    Message message;
    ServerSender(Socket irc, Message message) throws IOException {
        ircSocket = irc;
        out = new BufferedWriter(new OutputStreamWriter(irc.getOutputStream()));
        stdIn = new BufferedReader(new InputStreamReader(irc.getInputStream()));
        this.message = message;
    }

    @Override
    public void run() {
            try {
                serverCommand = message.convertToCommand();
                if (serverCommand != null) {
                    out.write(serverCommand + "\r\n");
                    out.flush();
                    FXMLLoader loader = SingletonFXMLLoader.getInstance().fxmlLoader;
                    IRCClient ircClient = SingletonFXMLLoader.getInstance().controller;
                    LocalTime time = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (!(message.getMessage().startsWith("/"))){
                                ircClient.channelArea.appendText(time.format(formatter)
                                        + " <#"+message.getChannelName()+"> ME: "+message.getMessage()
                                                +"\n");
                            }

                        }
                    });
                    System.out.println("Sent: " + serverCommand);
                }
            }
            catch(IOException e) {
                System.out.println("Server fed up");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Sleep failed!");
            }
//        }
    }
}
