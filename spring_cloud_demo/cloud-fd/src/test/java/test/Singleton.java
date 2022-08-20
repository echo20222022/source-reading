package test;

public class Singleton {
    private volatile Instance instance;
    public Instance getInstance() {
        if (instance == null) {
            synchronized (Instance.class) {
                if (instance == null) {
                    instance = new Instance();
                }
            }
        }
        return instance;
    }
}
class Instance{}
