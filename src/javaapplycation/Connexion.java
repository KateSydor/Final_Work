package javaapplication;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JTextArea;

public class Connexion implements Runnable{
    //Ми оголошуємо змінні, які використовує потік для отримання та надсилання повідомлень
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    JTextArea textServer;
    //Список користувачів, підключених до сервера
    private LinkedList<Socket> clients = new LinkedList<Socket>();
    String names[] = {"Ігор", "Дмитро", "Катерина"};
    private String connecter;
    private static int nb=0;

    //Конструктор, який отримує сокет, який буде брати участь у потоці, і список підключених користувачів
    public Connexion(Socket s,LinkedList client,JTextArea textServer){
        socket = s;
        clients = client;
        this.textServer = textServer;
       
    }
      
    
    @Override
    public void run() {
        try {
            //Ми ініціалізуємо канали зв’язку та надсилаємо вітальне повідомлення
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream()); 
            if(nb==0)
            textServer.setText("Сервер  "+"\n_________________________________________\n"+"сервер Підключається....\n");
            else  if(nb==1){
            textServer.append("Ігор підключився... \n");
            textServer.append("Дмитро підключився... \n");
            }

            else if(nb==2){
            textServer.append("Ігор підключився... \n");
            textServer.append("Дмитро підключився... \n");
            textServer.append("Катерина підключилась... \n"); }
            
            out.writeUTF("Клієнт  "+"\n_______________________________________________\n");
          
             //textServer.append(connecter+" підключився... \n");
               nb++;
            //Нескінченний цикл прослуховування повідомлень клієнтів
            while(true){
               String msg = in.readUTF();
                                 String[] t4=msg.split("@");
                                 String destinataire=t4[1];
                                 String expediteur=t4[2];
                                 msg=t4[0];
                                  
	    			textServer.append(expediteur+" надіслав повідомлення: "+destinataire+" \n");
                          for(int i = 0; i< names.length; i++){
                              if(names[i].equals(destinataire)){
	    		    	  out = new DataOutputStream(clients.get(i).getOutputStream());
	    		     out.writeUTF(expediteur+" :"+msg+"\n");  
                              }  
                                 if(names[i].equals(expediteur)){
	    		    	  out = new DataOutputStream(clients.get(i).getOutputStream());
	    		     out.writeUTF("ви :"+msg+"\n");
                              }  
                              }
                                
            }
            } catch (IOException e) {
            //Якщо трапляється виняток, найбезпечніше, щоб клієнт був відключений, тому ми видаляємо його зі списку підключених
            for (int i = 0; i < clients.size(); i++) {
                if(clients.get(i) == socket){
                	clients.remove(i);
                    break;
                } 
            }
        }
    }
}
