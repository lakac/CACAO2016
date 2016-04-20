package abstraction.equipe3;

import abstraction.fourni.Indicateur;

public class ActeurLeclerc {
	
	private String nom;
	private double quantite;
	private double prix;
	private Indicateur solde;
	private Indicateur achats;
	

	public ActeurLeclerc() {
		// TODO Auto-generated constructor stub
	}
	
	public void setQte(double commande){
		this.quantite=commande;
	}
	
	public void commande(int step){
		if (step==4){
			setQte(3812.5);
		}
		else{
			if (step==21){
				setQte(6312.5);
			}
			else{
				setQte(1812.5);
			}
		}
	}

}
