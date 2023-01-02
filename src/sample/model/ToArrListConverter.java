package sample.model;

import sample.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ToArrListConverter {
    public static ArrayList<Server> serverArray(ResultSet res){
        ArrayList arr = new ArrayList<>();
        try {
            while (res.next()){
                Server server = new Server();
                server.setIdServer(Integer.parseInt(res.getString("SERVER_ID")));
                server.setServerName(res.getString("SERVER_NAME"));
                server.setServerAddress(res.getString("SERVER_ADDRESS"));
                arr.add(server);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<ServerAndUser> serverbuArray(ResultSet res){
        ArrayList arr = new ArrayList<>();
        try {
            while (res.next()){
                ServerAndUser serverAndUser = new ServerAndUser();
                serverAndUser.setIdServerBU(res.getInt("SERVERSBYUSERS_ID"));
                serverAndUser.setIdServer(res.getInt("SERVERSBYUSERS_IDSERVER"));
                serverAndUser.setIdUser(res.getInt("SERVERSBYUSERS_IDUSER"));
                arr.add(serverAndUser);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;  //ready
    }
}
