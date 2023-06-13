import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ObjectInputStream readFile;
    private ObjectOutputStream writeFile;
    private ArrayList<Data> stroka = new ArrayList<>();
    private ArrayList<Data> strokaTmp = new ArrayList<>();

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        in = new ObjectInputStream(clientSocket.getInputStream());
        out = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            boolean error = false;
            strokaTmp = (ArrayList<Data>) in.readObject();
            boolean replace = true;
            try {
                readFile = new ObjectInputStream(new BufferedInputStream(
                        new FileInputStream("A.ser")));
                stroka = (ArrayList<Data>) readFile.readObject();
                readFile.close();
            } catch(IOException ex) {
                System.out.println("Отсутствует необходимый файл. Будет создан новый");
                error = true;
            } catch (ClassNotFoundException e) {
                System.out.println("Что-то пошло не так");
            }


            stroka.add(new Data(strokaTmp.get(0).getName(), strokaTmp.get(0).getPoints()));
            if(!error) {
                for(int j = 0; j < stroka.size() - 1; j++){

                    int i = stroka.size() - 1;
                    if(stroka.get(i).getName().equals(stroka.get(j).getName())){
                        if(stroka.get(i).getPoints() > stroka.get(j).getPoints()){
                            stroka.remove(j);
                        }
                        else {
                            stroka.remove(i);
                        }
                    }
                }
                Data temp;
                while (replace) {
                    replace = false;
                    for (int i = stroka.size() - 1; i > 0; i--) {
                        if(stroka.get(i).getPoints() > stroka.get(i-1).getPoints()){

                            temp = stroka.get(i - 1);
                            stroka.set(i - 1, stroka.get(i));
                            stroka.set(i,temp);
                            replace = true;
                        }
                    }
                }
            }

            writeFile = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream("A.ser")));
            writeFile.writeObject(stroka);
            writeFile.close();

            out.writeObject(stroka);

            System.out.println("Соединение разорвано с " + clientSocket);
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}