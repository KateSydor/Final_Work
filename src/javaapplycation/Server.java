package javaapplication;

 
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

 
import javax.swing.JTextArea; 

public class Server implements Runnable {
       public static JTextArea textServer;
public Server(JTextArea textServer){
 
    this.textServer = textServer;
}

    //ініціалізація порту та максимальної кількості підключень, які приймає сервер
    private final int port = 12345;
    private DataInputStream in;
    private DataOutputStream out;
    //Створюємо список сокетів, де будемо зберігати підключені сокети
    private LinkedList<Socket> clients = new LinkedList<Socket>();


    //Функція для того, щоб сервер почав отримувати підключення клієнтів
    public void listener(){
        try {
            //Створюємо серверний сокет
            ServerSocket server = new ServerSocket(12345);
            //Нескінченний цикл прослуховування нових клієнтів
            while(true){
               
               textServer.setText("Сервер  "+"\n_________________________________________\n"+"сервер підключено....\n");

                //Коли клієнт підключається, ми зберігаємо сокет у нашому списку
                Socket s = server.accept();
                clients.add(s);

                //Створюємо екземпляр потоку, який обслуговуватиме клієнта, і ми його слухаємо
                Runnable  run = new Connexion(s,clients, textServer);
                Thread t = new Thread(run);
                t.start();
             
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

@Override
public void run() {
listener();
	
}
}
