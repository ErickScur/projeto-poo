package primeirob.projeto3;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorChat {
    private static Set<Socket> clientes = new HashSet<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Servidor de chat iniciado na porta 12345...");

        while (true) {
            Socket clienteSocket = serverSocket.accept();
            System.out.println("Novo cliente conectado: " + clienteSocket.getInetAddress());


            clientes.add(clienteSocket);

            new Thread(() -> handleClient(clienteSocket)).start();
        }
    }


    private static void handleClient(Socket clienteSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            String mensagem;

            while ((mensagem = in.readLine()) != null) {
                System.out.println("Recebido: " + mensagem);
                broadcastMessage(mensagem, clienteSocket);
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado: " + clienteSocket.getInetAddress());
        } finally {
            try {
                clienteSocket.close();
                clientes.remove(clienteSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void broadcastMessage(String mensagem, Socket remetente) throws IOException {
        for (Socket cliente : clientes) {
            if (cliente != remetente) {
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                out.println(mensagem);
            }
        }
    }
}