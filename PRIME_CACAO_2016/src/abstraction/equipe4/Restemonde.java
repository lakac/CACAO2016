package abstraction.equipe4;

import abstraction.fourni.*;
import abstraction.commun.*;


public class Restemonde implements Acteur {
	private double demande;
	
	
	public double Coutvente ( double demande ){
		return this.getCours()*demande;
		
	}
	public void vente (double demande){
		this.getTreso().setFond(Coutvente(demande));
	}


	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
