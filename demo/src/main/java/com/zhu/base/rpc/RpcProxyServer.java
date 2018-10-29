package com.zhu.base.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @Author: zhuhao
 * @Date: 2018/10/29 11:54 AM
 * @Description:
 */

//参考：https://blog.csdn.net/jijianshuai/article/details/78708220
public class RpcProxyServer {

    private IHello hello = new HelloService();

    public void publisherServer(int port){
        try (ServerSocket ss = new ServerSocket(port)) {
            while(true){
                Socket socket = ss.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                String method = ois.readUTF();
                Object[] objs = (Object[])ois.readObject();
                Class[] types = new Class[objs.length];
                for (int i = 0 ; i < types.length; i++){
                    types[i] = objs[i].getClass();
                }
                Method m = HelloService.class.getMethod(method, types);
                Object obj = m.invoke(hello, objs);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(obj);
                oos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args){
        new RpcProxyServer().publisherServer(8000);
    }

}
