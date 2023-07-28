package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.ServerForm;

public class ServerListener implements ActionListener { 
	private ServerForm svForm;
	
	public ServerListener(ServerForm svForm) {
		this.svForm = svForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String button = e.getActionCommand();
		
		if (button.equals("Start"))
		{
			this.svForm.;
		}
		
	}


}
