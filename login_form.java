import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import java.awt.Color;
import java.awt.SystemColor;

public class login_form {

	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login_form window = new login_form();
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
	public login_form() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.inactiveCaption);
		frame.setBounds(200, 200, 500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUsername.setBounds(77, 84, 95, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPassword.setBounds(77, 136, 95, 14);
		frame.getContentPane().add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtUsername.setBounds(170, 78, 244, 29);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userAttempt = txtUsername.getText();
				String passAttempt = txtPassword.getText();
				
				try {
					// 1. Get a connection to database
					Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root" , "password");
					// 2. Create a statement
					Statement myStmt = myConn.createStatement();
					// 3. Execute SQL query
					ResultSet myRs = myStmt.executeQuery("select * from users");
					// 4. Process the result set
					
					// If userFound is false, then username hasn't been found
					boolean userFound = false;
					// Loop through all existing entries
					while (myRs.next()) {
						// Store the 'username' entry from database to string
						String user = myRs.getString("Username");
						// Store the 'password' entry from database to string
						String pass = myRs.getString("Password");
						
						// If username exists
						if (user.equals(userAttempt)) {
							userFound = true;
							
							if (pass.equals(passAttempt)) {
								txtUsername.setText(null);
								txtPassword.setText(null);
								JOptionPane.showMessageDialog(null, "Successful Login.");
								// When login is successful, call admin UI
								callTest test = new callTest();
								test.main(null);
							}else {
								JOptionPane.showMessageDialog(null, "Unsuccessful Login", "Login Error",JOptionPane.ERROR_MESSAGE);
							}
							break;
						}
					}
					// If username doesn't exist
					if (userFound==false) {
						JOptionPane.showMessageDialog(null, "Username doesn't exist.", "Login Error",JOptionPane.ERROR_MESSAGE);
					}
				}catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogin.setBounds(142, 208, 95, 29);
		frame.getContentPane().add(btnLogin);
		
		txtPassword = new JPasswordField();
		txtPassword.setEchoChar('*');
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPassword.setBounds(170, 130, 244, 29);
		frame.getContentPane().add(txtPassword);
		
		JCheckBox checkPassword = new JCheckBox("Show Password");
		checkPassword.setBackground(SystemColor.inactiveCaption);
		checkPassword.setBounds(170, 161, 124, 23);
		frame.getContentPane().add(checkPassword);
		checkPassword.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					txtPassword.setEchoChar((char) 0);
				} else {
					txtPassword.setEchoChar((char) '*');
				}
			}
		});
		
		JLabel lblLogin = new JLabel("Hotel Login");
		lblLogin.setForeground(new Color(0, 0, 0));
		lblLogin.setFont(new Font("Tekton Pro Cond", Font.PLAIN, 34));
		lblLogin.setBounds(170, 11, 244, 54);
		frame.getContentPane().add(lblLogin);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUsername.setText(null);
				txtPassword.setText(null);
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClear.setBounds(247, 208, 95, 29);
		frame.getContentPane().add(btnClear);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 195, 464, 2);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 63, 464, 2);
		frame.getContentPane().add(separator_1);
	}
}
