package primeirob.projeto3;

import java.io.*;
import java.net.*;

public class ClienteChat {

    public static void main(String[] args) {
        try {

            Socket socket = new Socket("localhost", 12345);

            new Thread(new Listener(socket)).start();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Conectado ao servidor de chat!");
            String mensagem;

            while (true) {
                System.out.print("Digite uma mensagem: ");
                mensagem = teclado.readLine();
                if (mensagem.equalsIgnoreCase("exit")) {
                    break;
                }
                out.println(mensagem);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Listener implements Runnable {
        private Socket socket;

        public Listener(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String mensagem;

                while ((mensagem = in.readLine()) != null) {
                    System.out.println("Mensagem recebida: " + mensagem);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
