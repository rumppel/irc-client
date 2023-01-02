package sample.model;

public class Const {
    public static final String USER_TABLE = "USERS";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NICK = "USER_NICK";
    public static final String USER_PASSWORD = "USER_PASS";
    public static final boolean USER_OP = true;

    public static final String SERVER_TABLE = "SERVERS";
    public static final String SERVER_ID = "SERVER_ID";
    public static final String SERVER_NAME = "SERVER_NAME";
    public static final String SERVER_ADDRESS = "SERVER_ADDRESS";
    public static final String SERVER_PORT = "SERVER_PORT";


    public static final String CHANNEL_TABLE = "CHANNELS";
    public static final String CHANNEL_ID = "CHANNEL_ID";
    public static final String CHANNEL_NAME = "CHANNEL_NAME";
    public static final String CHANNEL_TOPIC = "CHANNEL_TOPIC";
    public static final int CHANNEL_USERS = 0;
    public static final int CHANNEL_SERVERID = 0;

    public static final String SERVERSBYUSERS_TABLE = "SERVERSBYUSERS";
    public static final String SERVERSBYUSERS_ID = "SERVERSBYUSERS_ID";
    public static final String SERVERSBYUSERS_IDSERVER= "SERVERSBYUSERS_IDSERVER";
    public static final String SERVERSBYUSERS_IDUSER = "SERVERSBYUSERS_IDUSER";
}
