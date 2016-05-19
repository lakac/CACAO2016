package abstraction.equipe2;
import java.util.HashMap;
import abstraction.commun.Produit;

public class CommandeDis {
	
	private HashMap<Produit,Double> commandes;
	
	public HashMap<Produit, Double> getCommandes() {
		return commandes;
	}
	
	public void add(Produit p) {
		this.commandes.put(p,0.0);
	}

	public double getCommandeDis() {
		return this.commandes;
	}
	
	public double setCommandeDis(
	
	//Classe finie, test à faire
	public static void main(String[] args) {
		double comm=200.0;
		CommandeDis CommandeD = new CommandeDis(comm);
		if (CommandeD.getCommandeDis()!=comm){
			System.out.println("la commande du distributeur est de : " + CommandeD.getCommandeDis());
		}else{
			System.out.println("la commande du distributeur est à la bonne valeur :" + CommandeD.getCommandeDis());
		}
	}
}
