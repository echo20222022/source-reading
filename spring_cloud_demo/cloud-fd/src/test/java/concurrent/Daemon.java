package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Daemon {
    public static void main(String[] args) {
        Thread t = new Thread(new DaemonRunner());
        t.setDaemon(true);
        t.start();
    }
}

 class DaemonRunner implements Runnable {
    @Override
    public void run() {
        try  {
            TimeUnit.SECONDS.sleep(10);
        }catch (Exception ex) {}finally {
            //无效，主线程退出后，守护线程会立马终止
            System.out.println("Daemon finally");
        }
    }
}
