package sample.utils;
import sample.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class DatabaseHandler {
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localhost:3306/irc-client-db";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, "root", "12345");
        return dbConnection;
    }

    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USER_NICK + "," + Const.USER_PASSWORD + ")" + "VALUES(?,?)";

        try {
            PreparedStatement pr = getDbConnection().prepareStatement(insert);
            pr.setString(1, user.getNick());
            pr.setString(2, user.getPassword());
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUser(User user) {
        ResultSet res = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USER_NICK + " =? AND " + Const.USER_PASSWORD + " =? ";

        try {
            PreparedStatement pr = getDbConnection().prepareStatement(select);
            pr.setString(1, user.getNick());
            pr.setString(2, user.getPassword());
            res = pr.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return res;
    }

    public User getUserByNick(String nick) {
        ResultSet res = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USER_NICK + " =?";
        try {
            PreparedStatement pr = getDbConnection().prepareStatement(select);
            pr.setString(1, nick);
            res = pr.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        User user = new User();
        user.setNick(nick);
        ResultSet res2 = res;
        try {
            while (res.next()) {
                user.setIduser(res.getInt("USER_ID"));
                user.setNick(res.getString("USER_NICK"));
                user.setPassword(res.getString("USER_PASS"));
                user.setOp(Integer.parseInt(res.getString("USER_OP")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Server getServerByName(String name) {
        ResultSet res = null;
        String select = "SELECT * FROM " + Const.SERVER_TABLE + " WHERE " +
                Const.SERVER_NAME + " =?";
        try {
            PreparedStatement pr = getDbConnection().prepareStatement(select);
            pr.setString(1, name);
            res = pr.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Server server = new Server();
        server.setServerName(name);
        ResultSet res2 = res;
        try {
            while (res.next()) {
                server.setIdServer(res.getInt("SERVER_ID"));
                server.setServerAddress(res.getString("SERVER_ADDRESS"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return server;
    }

    public ResultSet getServersByUser(User user) {
        ResultSet res = null;
        String select = "SELECT * FROM " + Const.SERVERSBYUSERS_TABLE + " WHERE " +
                Const.SERVERSBYUSERS_IDUSER + " =?";

        try {
            PreparedStatement pr = getDbConnection().prepareStatement(select);
            pr.setString(1, String.valueOf(user.getIdUser()));
            res = pr.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return res;
    }

    public ResultSet getServersByUserById(User user) {
        ResultSet res = null;
        String select = "SELECT * FROM " + Const.SERVERSBYUSERS_TABLE + " WHERE " +
                Const.SERVERSBYUSERS_IDUSER + " =?";

        try {
            PreparedStatement pr = getDbConnection().prepareStatement(select);
            pr.setString(1, String.valueOf(user.getIdUser()));
            res = pr.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String select2 = "SELECT * FROM " + Const.SERVER_TABLE + " WHERE " +
                Const.SERVER_ID + " =?";

        try {
            PreparedStatement pr = getDbConnection().prepareStatement(select2);
            pr.setString(1, String.valueOf(user.getIdUser()));
            res = pr.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }

    public ArrayList<Server> getUserServersArray(User user) {
        ResultSet res = null;

        String select1 = "SELECT * FROM " + Const.SERVERSBYUSERS_TABLE +
                " WHERE " + Const.SERVERSBYUSERS_IDUSER + " = '" + user.getIdUser() + "' ";

        // SELECT * FROM Roles WHERE idrole IN
        // (SELECT idRoleBU FROM RolesByUsers WHERE idUserBU = '2');

        try {
            PreparedStatement pr = getDbConnection().prepareStatement(select1);
            //pr.setInt(1, Integer.parseInt(user.getId()));
            res = pr.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<ServerAndUser> sbu;
        sbu = ToArrListConverter.serverbuArray(res);

        ArrayList<Server> servers = new ArrayList<>();
        LinkedHashSet<Server> serversset = new LinkedHashSet<>();

        for (ServerAndUser f : sbu) {
            String select2 = "SELECT * FROM " + Const.SERVER_TABLE + " WHERE " +
                    Const.SERVER_ID + " = ? ";

            try {
                PreparedStatement pr = getDbConnection().prepareStatement(select2);
                pr.setString(1, String.valueOf(f.getIdServer()));
                res = pr.executeQuery();
                ArrayList<Server> fi;
                fi = ToArrListConverter.serverArray(res);
                serversset.addAll(fi);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
        servers.addAll(serversset);
        return servers;
    }

    public ArrayList<Server> getServersArray() {
        ResultSet res = null;

        ArrayList<Server> servers = new ArrayList<>();
        LinkedHashSet<Server> serversset = new LinkedHashSet<>();

        String select2 = "SELECT * FROM " + Const.SERVER_TABLE;

        try {
            PreparedStatement pr = getDbConnection().prepareStatement(select2);
            res = pr.executeQuery();
            ArrayList<Server> fi;
            fi = ToArrListConverter.serverArray(res);
            serversset.addAll(fi);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        servers.addAll(serversset);
        return servers;
    }


    public void addServer(Server server) {
        String insert = "INSERT INTO " + Const.SERVER_TABLE + "(" +
                Const.SERVER_NAME + "," + Const.SERVER_ADDRESS + ")" +
                "VALUES(?,?)";

        try {
            PreparedStatement pr = getDbConnection().prepareStatement(insert);
            pr.setString(1, server.getServerName());
            pr.setString(2, server.getServerAddress());
            pr.executeUpdate();
            // was execute
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addServerAndUser(Server server, User user) {
        String insert = "INSERT INTO " + Const.SERVERSBYUSERS_TABLE + "(" +
                Const.SERVERSBYUSERS_IDSERVER + "," + Const.SERVERSBYUSERS_IDUSER + ")" +
                "VALUES(?,?)";

        try {
            PreparedStatement pr = getDbConnection().prepareStatement(insert);
            System.out.println("server " + server.getIdServer());
            System.out.println("user " + user.getIdUser());
            pr.setInt(1, server.getIdServer());
            pr.setInt(2, user.getIdUser());
            pr.executeUpdate();
            // was execute
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}