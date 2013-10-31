package com.drexel.duca.backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.drexel.duca.frontend.ChatWindow;

public class Server implements Runnable {
    private int port;
    private ArrayList<STUser> friends;

    public Server(int port, ArrayList<STUser> friends) {
        this.port = port;
        this.friends = friends;
    }

    private class ChatWindowCreator implements Runnable {
        private Socket chatWindowSocket;
        private ArrayList<STUser> friends;

        ChatWindowCreator(Socket chatWindowSocket, ArrayList<STUser> friends) {
            this.chatWindowSocket = chatWindowSocket;
            this.friends = friends;
        }

        @Override
        public void run() {
            try {
                ChatWindow chatWindow = new ChatWindow(chatWindowSocket, friends);
                chatWindow.getChatWindowFrame().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                java.awt.EventQueue.invokeLater(new ChatWindowCreator(socket, friends));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
