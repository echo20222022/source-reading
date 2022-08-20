package test;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.IntBinaryOperator;

public class UUIDTest {

    @Test
    public void test1() {

        System.out.println(1 << 13);
        int[] intArr = (int[]) Array.newInstance(int.class, 10);
        Array.set(intArr, 0, 10);
        System.out.println(Array.get(intArr, 0));

        //int array3 = {12,3};
        //CountDownLatch
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.nameUUIDFromBytes("peking".getBytes()));

        //String
        //FutureTask

        String str = "abc";
        System.out.println(str.length());
        System.out.println(str.isEmpty());
        System.out.println(str.charAt(1));
        System.out.println(str.codePointAt(1));

        System.out.println(str.contentEquals(new String("abc")));

        StringTokenizer stringTokenizer = new StringTokenizer("a b  cs\r\nbbb,aaaaa");
        while (stringTokenizer.hasMoreElements()) {
            System.out.println(stringTokenizer.nextToken());
        }

        StringJoiner stringJoiner = new StringJoiner(",", "prefix", "suffix");
        stringJoiner.add("a");
        stringJoiner.add("b");
        stringJoiner.add("c");
        System.out.println(stringJoiner.toString());

        System.out.println("=========");

        int[] arr = new int[3];
        Arrays.parallelSetAll(arr, i -> i);

        Arrays.parallelPrefix(arr, new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left + right;
            }
        });

        for (int i = 0;i < arr.length;i ++) {
            System.out.println(arr[i]);
        }

        System.out.println("=============");


        Spliterator.OfInt sp = Arrays.spliterator(arr);
        sp.forEachRemaining((int value) -> {
            System.out.println("--" + value);
        });

        for (int i = 0;i < arr.length;i ++) {
            System.out.println(arr[i]);
        }

        for (int value : arr) {
            System.out.println(value);
        }
        /*Spliterator.OfInt sp1 = sp.trySplit();
        sp1.forEachRemaining((int value) -> {
            System.out.println("--" + value);
        });*/


    }

    @Test
    public void test2() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("===============");
            }
        });


        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(() -> {
            try {
                System.out.println("A:" + Thread.currentThread() + " 1");
                cyclicBarrier.wait();
                System.out.println("A:" + Thread.currentThread() + " 2");
                cyclicBarrier.wait();
                System.out.println("A:" + Thread.currentThread() + " 3");
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                System.out.println("B:" + Thread.currentThread() + " 1");
                cyclicBarrier.wait();
                System.out.println("B:" + Thread.currentThread() + " 2");
                cyclicBarrier.wait();
                System.out.println("B:" + Thread.currentThread() + " 3");
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Test
    public void test3() {
        String a = "Geeks";
        String b = new String("Geeks2");
        b.intern();
        String c = "Geeks";

        //ssSystem.out.println(a);
        System.out.println(a == c);

        String s = String.join(",", Arrays.asList("a","b", "c"));
        System.out.println(s);
    }

    /**
     * 慢开始
     * 拥塞避免
     * 快重传
     * 快恢复
     * */



    /**
        一个完整的http请求的过程：https://www.google.com

        开始连接

        DNS解析：
          1. 查询本地缓存
          2. 查询本地hosts表
          3. 查询本地DNS服务器
             3.1 查询根域名服务器
             3.2 查询顶级域名服务器
             3.3 查询权威域名服务器
          4. 本地DNS缓存结果并返回
          5. DNS解析完成
        TCP握手：
          1. 客户端发起sync(seq=x)
          2. 服务端回复sync-ack(seq=y,ack=x+1)
          3. 客户端回复ack(y+1)
        SSL握手：
          1. 客户端发送hello client(包含SSL版本及可选加密算法)
          2. 服务端返回server hello(包含SSLb版本、加密算法和包含公钥的证书)
          3. 客户端通过CA验证证书的合法性
          4. 客户端创建随机的对称加密秘钥，并用公钥加密后发送给服务端(如果是双向验证则将客户端证书发送给服务端)
          5. 服务端通过秘钥解密对称加密秘钥后，返回给客户端ack
          6. SSL握手完成
        客户端发送HTTP报文：
          1. 应用程序组装http协议数据，通过SSL/TSL发送数据
        服务端响应HTTP报文：
          1. 服务端处理数，并组装http协议对客户端进行响应
        客户端读取数据：
          1. 客户端从http协议的响应体中解析数据
        TCP挥手：
          1. 客户端发起断连请求(FIN,seq=u)
          2. 服务端给客户端反馈(ACK,seq=v,ack=u+1)
          3. 服务端数据传输完成,向客户端发起断连(FIN,ACK,seq=w,ack=u+1)
          4. 客户端给服务端反馈(ACK,seq=u+1,ack=w+1)
        连接断开

        https://wiki.wireshark.org/TCP%204-times%20close
        https://www.tutorialsteacher.com/https/how-ssl-works
        https://www.geeksforgeeks.org/tcp-3-way-handshake-process/?ref=lbp
        https://www.geeksforgeeks.org/tcp-connection-termination/?ref=lbp
        https://www.geeksforgeeks.org/dns-spoofing-or-dns-cache-poisoning/?ref=lbp
     * */
}
