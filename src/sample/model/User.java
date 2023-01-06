package sample.model;
import java.util.Objects;

public class User {
    private int idUser;
    private String nick;
    private String password;

    public User(int id, String nick, String password) {
        this.idUser = id;
        this.nick = nick;
        this.password = password;
    }
    public User(String nick, String password) {
        this.nick = nick;
        this.password = password;
    }
    public User() {
    }

    public int getIdUser() {
        return idUser;
    }


    public void setIduser(int idUser) {
        this.idUser = idUser;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.nick, other.nick)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.nick != null ? this.nick.hashCode() : 0);
        return hash;
    }
    private static User instance = null;
    private void User(){}
    private static User getInstance(){
        if (instance==null){
            instance = new User();
        }
        return instance;
    }
}
