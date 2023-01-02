package sample.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Channel implements Comparable<Channel> {
    private int idChannel;
    private String name;
    private String topic;
    private int serverId;
    private ArrayList<String> users;

    public Channel(int idChannel, String name, String topic, int serverid, ArrayList<String> users) {
        this.idChannel = idChannel;
        this.name = name;
        this.topic = topic;
        this.serverId = serverid;
        this.users = users;
    }

    public Channel(String name, String topic, int serverid, ArrayList<String> users) {
        this.name = name;
        this.topic = topic;
        this.serverId = serverid;
        this.users = users;
    }

    public Channel(String name) {
        this(0, name, "This channel has no topic", 0, null);
    }

    public Channel() {
    }

    public int getIdChannel() {
        return idChannel;
    }

    public void setIdChannel(int idChannel) {
        this.idChannel = idChannel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return String.format("%s %s%s", name, topic);
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    @Override
    public int compareTo(Channel o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Channel other = (Channel) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<String> getCurrentUsers(String serverAnswer){
        ArrayList<String> userlist = new ArrayList<String>();
        String str = serverAnswer.substring(serverAnswer.lastIndexOf(":")+1);
        String[] userNick = str.split(" ");
        userlist.addAll(Arrays.asList(userNick));
        this.setUsers(userlist);
        return userlist;
    }

    public String getCurrentTopic(String serverAnswer){
        String[] str = serverAnswer.split(":");
        String topic = str[2];
        this.setTopic(topic);
        return topic;
    }
}
