package sample.model;

public class ServerAndUser {
    private int idServerBU;
    private int idUser;
    private int idServer;

    public ServerAndUser(int idServerBU, int idUser, int idServer) {
        this.idServerBU = idServerBU;
        this.idServer = idServer;
        this.idUser = idUser;
    }

    public ServerAndUser(int idUser, int idServer) {
        this.idServer = idServer;
        this.idUser = idUser;
    }

    public ServerAndUser() {
    }

    public int getIdServerBU() {
        return idServerBU;
    }

    public void setIdServerBU(int idServerBU) {
        this.idServerBU = idServerBU;
    }

    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "ServerAndUser{" +
                "idServerBU=" + idServerBU +
                ", idUser=" + idUser +
                ", idServer=" + idServer +
                '}';
    }
}
