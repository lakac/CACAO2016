package abstraction.equipe2;
import java.util.HashMap;

import OldV1transformateur.CommandeProd;
import abstraction.commun.*;
import abstraction.equipe1.Producteur;
import abstraction.fourni.*;


public class CoutTransport {
	
	public static final double COUTUNITAIRE = 0.01;
	private double couttransport;
	private HashMap<IProducteur,Double> distances;
	
	
	public CoutTransport(double couttransp){
		this.couttransport=couttransp;
		this.distances=new HashMap<IProducteur, Double>();
	}

	public void addDistance(IProducteur p, Double dist) {
		this.distances.put(p,dist);
	}
	
	public HashMap<IProducteur, Double> getDistances() {
		return distances;
	}
	
	public double getCouttransport() {
		return couttransport;
	}

	public void setCouttransport(IProducteur p, Achat achat) {
		this.couttransport=COUTUNITAIRE*this.distances.get(p)*achat.getDernierecommandeachetee().getCommandesProd();
	}
	
	/*public static void main(String[] args) {
		double couttransport=0.0015;
		CommandesProd commande = new CommandesProd(1000.0);
		Producteur p1 = new Producteur(Constantes.NOM_PRODUCTEUR_1, 1000.0, 0.0, Monde.LE_MONDE);
		Producteur p2 = new Producteur(Constantes.NOM_PRODUCTEUR_2, 1000.0, 0.0, Monde.LE_MONDE);
		CoutTransport CoutT = new CoutTransport(couttransport);
		CoutT.addDistance(p1,5000.0);
		CoutT.addDistance(p2,9000.0);
		CoutT.setCouttransport(couttransport,p1,commande);
		System.out.println(CoutT.getCouttransport());
		System.out.println(CoutT.getDistances());
		}*/
}
