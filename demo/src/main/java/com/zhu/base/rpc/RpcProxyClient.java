package com.zhu.base.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @Author: zhuhao
 * @Date: 2018/10/29 2:54 PM
 * @Description:
 */
public class RpcProxyClient<T> {

    private static int port = 8000;

    public T proxyClient(Class<T> clazz) {
        return (T)(Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try (Socket socket = new Socket("127.0.0.1", port)) {
                    try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                        oos.writeUTF(method.getName());
                        oos.writeObject(args);
                        oos.flush();
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        return ois.readObject();
                    }
                }
            }
        }));
    }

    public T proxyClient1(Class<T> clazz) {
        return (T)(Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), (Object proxy, Method method, Object[] args) -> {
                try (Socket socket = new Socket("127.0.0.1", port)) {
                    try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                        oos.writeUTF(method.getName());
                        oos.writeObject(args);
                        oos.flush();
                        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                            return ois.readObject();
                        }
                    }
                }

        }));
    }

    // 调用服务
    public static void main(String[] args) {

        RpcProxyClient<HelloService> rpcClient = new RpcProxyClient<>();

        IHello hello = rpcClient.proxyClient1(HelloService.class);

        String s = hello.sayHello("朱昊");

        System.out.println(s);
    }



}
