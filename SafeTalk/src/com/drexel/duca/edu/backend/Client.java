package com.drexel.duca.backend;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.drexel.duca.frontend.ChatWindow;

public class Client {

    public static void start(String ip, int port, ArrayList<STUser> friends) {
        try {
            Socket socket = new Socket(ip, port);
            java.awt.EventQueue.invokeLater(new ChatWindowCreator(socket, friends));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ChatWindowCreator implements Runnable {
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

}
