package sample.model;

public class Server {
    private int idServer;
    private String ServerName;
    private String ServerAddress;

    public Server(int idServer, String ServerName, String ServerAddress) {
        this.idServer = idServer;
        this.ServerName = ServerName;
        this.ServerAddress = ServerAddress;

    }

    public Server(String serverName, String ServerAddress) {
        this.ServerName = serverName;
        this.ServerAddress = ServerAddress;
    }

    public Server(String serverAddress) {
        ServerAddress = serverAddress;
    }
    public Server() {
    }
    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public String getServerName() {
        return ServerName;
    }

    public void setServerName(String serverName) {
        ServerName = serverName;
    }

    public String getServerAddress() {
        return ServerAddress;
    }

    public void setServerAddress(String serverAddress) {
        ServerAddress = serverAddress;
    }

    @Override
    public String toString() {
        return ServerName;
    }
}
