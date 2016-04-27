package abstraction.equipe3;

import abstraction.fourni.Acteur;
import abstraction.fourni.v0.Transformateur;


public interface ILeclerc extends Acteur{
	
	public void ajouterVendeur(Transformateur t);
	public double getPrix();
	public String getNom();
	public double getQte();
	public double getT1();
	public double getT2();
	public double getT3();
	public void setQte(double commande);
	public void commande();
	public void next();



}
