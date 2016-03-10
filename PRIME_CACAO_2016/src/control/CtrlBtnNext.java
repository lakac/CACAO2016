package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import abstraction.fourni.Monde;

public class CtrlBtnNext implements ActionListener {

   private Monde monde;
   
	public CtrlBtnNext(Monde monde) {
		this.monde = monde;
	}
	public void actionPerformed(ActionEvent arg0) {
		this.monde.next();
	}
}
