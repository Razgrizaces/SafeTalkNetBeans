package com.drexel.duca.frontend;

import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextPane;

import org.json.JSONException;

import com.drexel.duca.backend.*;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.temboo.core.TembooException;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

public class SafeTalkAuth {

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField textField;
	private JButton Done;
	private boolean needOauth;
	private SafeTalkLogin stLogin;
	private JTextPane textPane;
	private static Application app;
	private boolean usingFB;
	private Facebook fb;

	/**
	 * Launch the application.
	 */
	public JFrame getFrame()
	{
		return frame;
	}

	/**
	 * Create the application.
	 */
	public SafeTalkAuth(boolean facebook, Application app) {
		SafeTalkAuth.app = app;
		usingFB = facebook;
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
		
		passwordField = new JPasswordField();
		passwordField.setBounds(200, 60, 130, 20);
		frame.getContentPane().add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(200, 29, 130, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		Done = new JButton("Done");
		Done.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					doneButtonActionPerformed(e);
				} catch (TembooException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JsonIOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JsonSyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		Done.setBounds(147, 184, 130, 47);
		frame.getContentPane().add(Done);
		
		JLabel lblSafetalkUsername = new JLabel("SafeTalk Username:");
		lblSafetalkUsername.setBounds(57, 29, 130, 20);
		frame.getContentPane().add(lblSafetalkUsername);
		
		JLabel lblSafetalkPassword = new JLabel("SafeTalk Password:");
		lblSafetalkPassword.setBounds(55, 63, 133, 14);
		frame.getContentPane().add(lblSafetalkPassword);
		
		textPane = new JTextPane();
		textPane.setText("Do NOT enter a password you normally use.");
		textPane.setEditable(false);
		textPane.setBackground(new Color(240,240,240));
		textPane.setBounds(65, 92, 309, 20);
		frame.getContentPane().add(textPane);
		if(usingFB){

			JButton btnAuthenticateWFacebook = new JButton("Authenticate W/ Facebook");
			btnAuthenticateWFacebook.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						facebookButtonActionPerformed(e);
					} catch (TembooException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnAuthenticateWFacebook.setBounds(114, 130, 197, 25);
			frame.getContentPane().add(btnAuthenticateWFacebook);
		}
	}
	private void facebookButtonActionPerformed(java.awt.event.ActionEvent evt) throws TembooException, IOException {
		fb = new Facebook();
		String authURL = fb.startOAuth();
		
		File htmlFile = new File(authURL);

		java.awt.Desktop.getDesktop().browse(java.net.URI.create(authURL));
				
	}
	
	private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) throws TembooException, JsonIOException, JsonSyntaxException, IOException {
		String username = textField.getText();
		char[] password = passwordField.getPassword();
		String pass = "";
		for(int i = 0; i<password.length;i++)
		{
			pass = pass + password[i];
		}
		try {
			if (usingFB) {
				String accessToken = fb.finishOAuth();
				app.createSTUserFromFB(username, pass, accessToken);
				app.pushUser();
			}
			else {
				app.createSTUserManually(username, pass);
				app.pushUser();

			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(frame, e.getMessage(), "Create User Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		BuddyList window = new BuddyList(app);
		window.getFrame().setVisible(true);
		frame.dispose();
		
		//OPEN FRIENDS LIST.
		
	}
}
