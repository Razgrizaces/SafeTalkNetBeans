package com.drexel.duca.frontend;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.json.JSONException;

import com.drexel.duca.backend.*;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.temboo.core.TembooException;

public class BuddyList {

	private JFrame frame;
	private JList<String> list;
	private ArrayList<STUser> friends;
	private static Application app;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuddyList window = new BuddyList(app);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public JFrame getFrame()
	{
		return frame;
	}

	/**
	 * Create the application.
	 */
	public BuddyList(Application app) {
		this.app = app;
		try {
			app.getFriendObjects();
		} catch (JsonIOException | JsonSyntaxException | TembooException
				| JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.friends = app.friends;
		new Thread(new Server(5556, friends)).start();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 200, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		list = new JList() {
		    @Override
            public int locationToIndex(Point location) {
                int index = super.locationToIndex(location);
                if (index != -1 && !getCellBounds(index, index).contains(location)) {
                    return -1;
                }
                else {
                    return index;
                }
            }
		};
		refresh();
		list.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int location = list.locationToIndex(e.getPoint());
				if (location == -1) {
				    e.consume();
				    return;
				}
				STUser clickedUser = friends.get(location);
				if (clickedUser.isOnline()) Client.start(clickedUser.getIp(), 5556, friends);

			}
		});
		JScrollPane scrollPane = new JScrollPane(list);
		frame.getContentPane().add(scrollPane);

		JButton btnAddFriend = new JButton("Add Friend");
		btnAddFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = JOptionPane.showInputDialog(null,
						"Enter the username of the person you'd like to add.",
						"Add New User", JOptionPane.PLAIN_MESSAGE);
				if (username != null && username != "") {
					try {
						app.addFriendManually(username);
						refresh();
					} catch (TembooException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RuntimeException e2) {
						JOptionPane.showMessageDialog(frame, e2.getMessage(), "Can't add friend!", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}
		});
		frame.getContentPane().add(btnAddFriend, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		frame.getContentPane().add(btnNewButton, BorderLayout.NORTH);
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                try {
					app.setOffline();
				} catch (TembooException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                System.exit(0);
            }
        } );
        
        
	}
	

	public void refresh() {
		try {
			app.getFriendObjects();
		} catch (JsonIOException | JsonSyntaxException | TembooException
				| JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for (STUser friend : this.friends) {
			String online = "Offline";
			if (friend.isOnline()) online = "Online";
			listModel.addElement(friend.getUsername()+", " + online);
		}

		list.setModel(listModel);
	}
}
