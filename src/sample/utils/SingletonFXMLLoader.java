package sample.utils;
import sample.controller.IRCClient;

public final class SingletonFXMLLoader {
    private static volatile SingletonFXMLLoader instance;
    public IRCClient clientFXMLLoader;

    public static SingletonFXMLLoader getInstance() {
        SingletonFXMLLoader localInstance = instance;
        if (localInstance == null) {
            synchronized (SingletonFXMLLoader.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SingletonFXMLLoader();
                }
            }
        }
        return localInstance;
    }
}
