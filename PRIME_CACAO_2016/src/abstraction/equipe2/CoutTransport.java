package abstraction.equipe2;
import java.util.HashMap;

import abstraction.commun.*;


public class CoutTransport {
	
	private double couttransportglobal;
	private HashMap<IProducteur,Double> distances;
	
	
	public CoutTransport(double couttransp){
		this.couttransportglobal=couttransp;
		this.distances=new HashMap<IProducteur, Double>();
	}

	public void addDistance(IProducteur p, Double dist) {
		this.distances.put(p,dist);
	}
	
	public HashMap<IProducteur, Double> getDistances() {
		return distances;
	}
	
	public double getCouttransport() {
		return couttransportglobal;
		}
	
	public double CouttransportGlobal(Achat achatp1, Achat achatp2, Achat ResteDuMonde) {
		this.setCouttransportglobal((achatp1.getCacaoachete()+achatp2.getCacaoachete()+ResteDuMonde.getCacaoachete())
				*Constante.COUT_UNITAIRE_TRANSPORT);
		return this.getCouttransport();
	}

	public void setCouttransportglobal(double couttransportglobal) {
		this.couttransportglobal = couttransportglobal;
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
