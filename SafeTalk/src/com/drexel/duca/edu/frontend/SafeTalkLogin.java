package com.drexel.duca.frontend;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextPane;

import java.awt.Color;

import javax.swing.UIManager;

import org.json.JSONException;

import com.drexel.duca.backend.*;
import com.temboo.core.TembooException;

import java.awt.SystemColor;

public class SafeTalkLogin {

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField textField;
	private JLabel lblSafetalkUsername;
	private JLabel passwordLabel;
	private ImagePanel iPanel;
	private static Application app;
	private boolean facebook;
	private SafeTalkAuth stAuth;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SafeTalkLogin window = new SafeTalkLogin(app);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SafeTalkLogin(Application app) {
		SafeTalkLogin.app = app;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton EnterButton = new JButton("Enter");
		EnterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				try {
					enterButtonActionPerformed(arg0);
				} catch (TembooException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		EnterButton.setBounds(344, 70, 80, 51);
		frame.getContentPane().add(EnterButton);
		passwordField = new JPasswordField();
		passwordField.setBounds(191, 101, 132, 20);
		frame.getContentPane().add(passwordField);
		textField = new JTextField();
		textField.setBounds(191, 70, 132, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		final JCheckBox chckbxNewCheckBox = new JCheckBox("Facebook");
		chckbxNewCheckBox.setBounds(287, 200, 97, 23);
		frame.getContentPane().add(chckbxNewCheckBox);
		JButton btnNewButton_1 = new JButton("Create New Account");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				facebook = chckbxNewCheckBox.isSelected();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							SafeTalkAuth window = new SafeTalkAuth(facebook, app);
							window.getFrame().setVisible(true);
							frame.dispose();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnNewButton_1.setBounds(109, 200, 172, 30);
		frame.getContentPane().add(btnNewButton_1);
		//checkbox will pass a boolean to activate the FB authenticate.
		lblSafetalkUsername = new JLabel("SafeTalk Username");
		lblSafetalkUsername.setBounds(61, 70, 120, 19);
		frame.getContentPane().add(lblSafetalkUsername);
		ImageIcon jj = new ImageIcon(this.getClass().getResource("SafeTalk.png"));
		Image abc = jj.getImage();
		ImagePanel iPanel = new ImagePanel();
		iPanel.setImage(abc);
		frame.getContentPane().add(iPanel);
		iPanel.setBounds(133,11,190,48);
		passwordLabel = new JLabel("SafeTalk Password");
		passwordLabel.setBounds(61, 104, 120, 17);
		frame.getContentPane().add(passwordLabel);
	}
	
	private void enterButtonActionPerformed(java.awt.event.ActionEvent evt) throws TembooException, JSONException {
		String username = textField.getText();
		char[] password = passwordField.getPassword();
		String pass = "";
		for(int i = 0; i<password.length;i++)
		{
			pass = pass + password[i];
		}
		try {
			app.logIn(username, pass);
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(frame, e.getMessage(), "Log In Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		BuddyList window = new BuddyList(app);
		window.getFrame().setVisible(true);
		frame.dispose();
		
	}
	private void textFieldActionPerformed(java.awt.event.ActionEvent evt) {
        //code to get user's password and compare text entered to the databases's username
    }
	private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt)
	{
		//code to get uesr's password and compare text entered to the database's password.
	}
	public JFrame getJFrame()
	{
		return frame;
	}
	public boolean getFBStatus()
	{
		return facebook;
	}
	
}
