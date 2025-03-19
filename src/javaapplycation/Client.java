package javaapplication;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static javaapplication.Application.jTextArea3;

import javax.swing.JTextArea;


public class Client implements Runnable {
    //Оголошуємо змінні, необхідні для підключення та зв'язку
    private Socket client;
    public static String noms;
    private DataInputStream in;
    private DataOutputStream out;
    //Порт має бути тим самим портом, який прослуховує сервер
    private int port = 12345;
    //Якщо ми знаходимося на нашій одній машині, ми використовуємо localhost, інакше це IP-адреса серверної машини
    private String host = "localhost";
    private String messages = "";

    JTextArea textClient;
    public static ArrayList<String> ch = new ArrayList();

    //Конструктор отримує як параметр панель, на якій будуть відображатися повідомлення
    public Client(JTextArea textClient, String name, String host) {

        this.host = host;
        this.textClient = textClient;
        try {

            client = new Socket(host, port);
            jTextArea3.append("Сервер  \n" + "_________________________________________\n" + name + " успішно підключено\n");

            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {

//Нескінченний цикл, який прослуховує повідомлення від сервера та відображає їх на панелі
            while (true) {
                messages += in.readUTF();
                textClient.setText(messages);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Функція, яка використовується для надсилання повідомлень на сервер
    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
    
