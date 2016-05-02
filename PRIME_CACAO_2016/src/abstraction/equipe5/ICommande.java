package abstraction.equipe5;

import abstraction.fourni.Acteur; 

public interface ICommande {

	public Acteur getActeur(); /*voir ac prof comment mettre producteur et distributeur*/
	
	public double getQuantite();
	
	public double getPrixTonne();
}
