package view;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.ServerListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ServerForm extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerForm frame = new ServerForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Server Form");
		setBounds(100, 100, 554, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(128,29, 311, 140);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Arial", Font.PLAIN, 18));
		lblPort.setBounds(71, 51, 48, 26);
		panel.add(lblPort);
		
		JTextField txt_Port = new JTextField();
		txt_Port.setText("5905");
		txt_Port.setEditable(false);
		txt_Port.setFont(new Font("Arial", Font.PLAIN, 14));
		txt_Port.setBounds(123, 53, 120, 23);
		panel.add(txt_Port);
		txt_Port.setColumns(10);
		
		
		ServerListener svListerner = new ServerListener(this);
		

		
		JButton btn_StartServer = new JButton("Start");
		btn_StartServer.setFont(new Font("Arial", Font.PLAIN, 14));
		btn_StartServer.setBounds(203, 195, 161, 37);
		contentPane.add(btn_StartServer);
		btn_StartServer.addActionListener(svListerner);
		
		
		this.setVisible(true);
		
	}
	
	public void isDispose()
	{	
		new ServerManagementForm();
		this.dispose();
	}

}
