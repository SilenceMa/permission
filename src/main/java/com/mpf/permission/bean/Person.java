package com.mpf.permission.bean;

import lombok.Data;

import java.io.*;

@Data
public class Person {

    public static void main(String []args){
        try {
            FileOutputStream fos = new FileOutputStream("/Users/mapengfei/Desktop/mpf.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            Writer writer = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(writer);
            String s= "Got answer:\n" +
                    ";; ->>HEADER<<- opcode: QUERY, status: NOERROR, id: 59790\n" +
                    ";; flags: qr rd ra; QUERY: 1, ANSWER: 3, AUTHORITY: 0, ADDITIONAL: 1\n" +
                    "\n" +
                    ";; OPT PSEUDOSECTION:\n" +
                    "; EDNS: version: 0, flags:; udp: 4096\n" +
                    ";; QUESTION SECTION:\n" +
                    ";www.baidu.com.\t\t\tIN\tA\n" +
                    "\n" +
                    ";; ANSWER SECTION:\n" +
                    "www.baidu.com.\t\t30\tIN\tCNAME\twww.a.shifen.com.\n" +
                    "www.a.shifen.com.\t30\tIN\tA\t61.135.169.121\n" +
                    "www.a.shifen.com.\t30\tIN\tA\t61.135.169.125\n" +
                    "\n" +
                    ";; Query time: 57 msec\n" +
                    ";; SERVER: 192.168.31.1#53(192.168.31.1)\n" +
                    ";; WHEN: Sat Nov 24 22:47:09 CST 201";
            /*Serializable serializable = new Serializable() {
                @Override
                public int hashCode() {
                    return super.hashCode();
                }
            };*/
            bw.write(s);
//            oos.writeObject();
//            oos.flush();
//            oos.close();
            bw.flush();
            bw.close();
        }catch (IOException e){
            System.out.println("io"+e);
        }catch (Exception e){
            System.out.print("e"+e);
        }
        System.out.println("aa");
    }
}
