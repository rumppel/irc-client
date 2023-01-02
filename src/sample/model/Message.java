package sample.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private final String message;
    private Server server;
    private String channelName;
    private String user;

    public Message(String message, String channelName, Server server, String user) {
        this.message = message;
        this.channelName = channelName;
        this.server = server;
        this.user = user;
    }

    public Message(String message) {
        this(message, null, null, null);
    }
    public Message(String message, String channelName) {
        this(message, channelName, null, null);
    }
    public Message(String message, String channelName, String user) {
        this(message, channelName, null, user);
    }



    public String getMessage() {
        return message;
    }

    public String convertToCommand(){
        if (message.startsWith("/")){
            return this.getCommand(message);
        }
        else if (message.startsWith("@")){
            return this.sendPrivateMes(message);
        }
        else {
            return this.sendMesToChannel(message, channelName);
        }
    }

    public String convertToMes(){
        String[] arr = message.split(" ");
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        if(arr[1].equals("PRIVMSG")&&message.contains("#")){
            String[] user = arr[0].split("!");
            StringBuilder mes = new StringBuilder();
            for (int i = 3; i < arr.length; i++) {
                mes.append(arr[i] + " ");
            }
            String chan = "";
            if(arr[2].charAt(0) == '#'){
                chan = arr[2];
            } else{
                chan = user[0].substring(1);
            }
            return time.format(formatter) +" <"+chan+"> "+user[0].substring(1)+": "+mes.substring(1);
        }
        else if(arr[1].equals("JOIN")){
            String[] user = arr[0].split("!");
            StringBuilder mes = new StringBuilder();
            String chan = "";
            chan = arr[2];
            return time.format(formatter) +" <"+chan+"> "+user[0].substring(1)+" just joined "+chan+" channel :)";
        }
        else if(arr[1].equals("PART")){
            String[] user = arr[0].split("!");
            StringBuilder mes = new StringBuilder();
            String chan = "";
            chan = arr[2];
            return time.format(formatter) +" <"+chan+"> "+user[0].substring(1)+" just left "+chan+" channel :(";
        }
        else if(arr[1].equals("QUIT")){
            System.exit(0);
        }
        else if(arr[1].equals("332")){
            StringBuilder mes = new StringBuilder();
            String chan = "";
            chan = arr[3];
            return time.format(formatter) +" Topic of "+"<"+chan+"> channel: "+message.substring(message.lastIndexOf(":")+1);
        }
        else if(arr[1].equals("353")){
            String[] user = arr[0].split("!");
            StringBuilder mes = new StringBuilder();
            String chan = "";
            chan = arr[4];
            return time.format(formatter) +" Users on "+"<"+chan+"> channel: "+message.substring(message.lastIndexOf(":")+1);
        }
        if(arr[1].equals("PRIVMSG")&&!(message.contains("#"))){
            String[] user = arr[0].split("!");
            StringBuilder mes = new StringBuilder();
            for (int i = 3; i < arr.length; i++) {
                mes.append(arr[i] + " ");
            }
            String reciever = arr[2];
            System.out.println("user:::"+user[0].substring(1)+"\nrec::::"+reciever);
            return time.format(formatter) +" <"+user[0].substring(1)+"> send private message to <"+reciever+"> :"+mes.substring(1);
        }
        String arr2[] = message.split(" ");
        return time.format(formatter)+" "+arr2[0].substring(1)+" : "+message.substring(message.lastIndexOf(":")+1);
    }

    public String sendMesToChannel(String message, String channelName){
        return "PRIVMSG #" + channelName + " :" + message;
    }

    public String sendPrivateMes(String message){
        String[] rec = message.split(" ");
        int mess = message.indexOf(" ");
        String mes = message.substring(mess+1);
        return "PRIVMSG " + rec[0].substring(1) + " :" + mes;
    }

    public String getCommand(String message) {
        if (message.toLowerCase().startsWith("/join")){
            String channelName = message.substring(7);
            return this.connectToChannel(channelName);
        }else if (message.toLowerCase().startsWith("/part")){
            String channelName = message.substring(7);
            return this.partChannel(channelName);
        }else if (message.toLowerCase().startsWith("/names")){
            String channelName = message.substring(8);
            return this.channelUsers(channelName);
        }else if (message.toLowerCase().startsWith("/topic")){
            String channelName = message.substring(8);
            return this.channelTopic(channelName);
        }else if (message.toLowerCase().startsWith("/quit")){
            return this.quitServer();
        }
        else {
            return message;
        }
    }

    public String connectToChannel(String message){
        return "JOIN #" + message;
    }

    public String partChannel(String message){
        return "PART #" + message;
    }

    public String channelUsers(String message){
        return "NAMES #" + message;
    }

    public String channelTopic(String message){
        return "TOPIC #" + message;
    }

    public String quitServer(){
        return "QUIT";
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + message;
    }
}
