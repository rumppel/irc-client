package sample.controller;

import javafx.application.Platform;
import sample.model.Message;
import sample.utils.SingletonFXMLLoader;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerListener extends Thread implements Runnable {

    Socket ircSocket;
    String serverAnswer = null;
    BufferedReader in;

    public ServerListener(Socket irc) throws IOException{
        ircSocket = irc;
        in = new BufferedReader(new InputStreamReader(irc.getInputStream()));
    }

    @Override
    public void run() {
        while(true) {
            try {
                serverAnswer = in.readLine();
                Message message = new Message(serverAnswer);
                if (serverAnswer != null) {
                    IRCClient ircClient = SingletonFXMLLoader.getInstance().controller;

                    System.out.println("Server talkin: " + serverAnswer);

                    Platform.runLater(() -> {
                        ircClient.updateClientIfAction(serverAnswer, message);
                    });
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
