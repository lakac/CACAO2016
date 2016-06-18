package abstraction.equipe2;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.*;


public class Transformation {
	
	//variables d'instance, 
	//représente à l'étape n la quantité produite de chocolat de chaque espèce
	private HashMap<Produit,Double> transformation;
		
	//accesseur en lecture
	public HashMap<Produit,Double> getTransformation() {
		return this.transformation;
	}
	
	//ce constructeur ne sera jamais utilisé, 
	//il vaudrait mieux garder le constructeur vide et des méthodes de production à mon sens
	//mais je vais l'utiliser pour les tests de la trésorerie
	//Constructeurs
	//Un constructeur général 
	public Transformation(double chocolat50, double chocolat60, double chocolat70){
		this.transformation=new HashMap<Produit,Double>();
		this.transformation.put(Constante.PRODUIT_50, chocolat50);
		this.transformation.put(Constante.PRODUIT_60, chocolat60);
		this.transformation.put(Constante.PRODUIT_70, chocolat70);
	}
	
	//Remplit le dictionnaire avec 0 comme valeur de production pour chaque 
	//(à appeler dans le constructeur de Nestle)
	public Transformation(){
		this(0.0,0.0,0.0);
	}
	
	//Il faut maintenant déterminer 
	//quel est le Distributeur qui a commandé le plus lors de la step précédente
	//Il faut alors obtenir le total des commandes des distributeurs.
	//Cette méthode statique prend une liste de commande en argument 
	//et renvoie un dictionnaire de IDistributeur, double 
	//indiquant la quantite totale de chocolat demande pour chaque IDistributeur
	private static HashMap<IDistributeur, Double> CommandesTotales(List<CommandeDistri> lcd) {
		HashMap<IDistributeur, Double> dictionnaire = new HashMap<IDistributeur, Double>();
		for (CommandeDistri cd : lcd) {
			dictionnaire.put(cd.getAcheteur(), 0.);
		}
		for (CommandeDistri cd : lcd) {
			double quantite = dictionnaire.get(cd.getAcheteur());
			dictionnaire.put(cd.getAcheteur(), quantite+cd.getQuantite());
		}
		return dictionnaire;
	}
	
	//La méthode qui suit me sert à trier une liste. 
	//Elle s'avère nécessaire pour classe les Distributeurs
	private static void TrierDecroissant(List<Double> l) {
		List<Double> resultat = new ArrayList<Double>();
		int taille = l.size();
		Collections.sort(l);
		for (int i=0; i<taille; i++) {
			resultat.add(l.get(taille-1-i));
		}
		l =  resultat;
	}
	
	//Cette méthode prend en argument une liste de CommandeDistri
	//et renvoie une liste de distributeur classé par ordre de priorité de transformation
	//le premier sera celui à faire en priorité, et ainsi de suite.
	public static List<IDistributeur> Priorite(List<CommandeDistri> lcd) {
		List<IDistributeur> priorite = new ArrayList<IDistributeur>();
		List<Double> valeurscommandes = new ArrayList<Double>();
		HashMap<IDistributeur, Double> dictionnaire = CommandesTotales(lcd);
		//Ce for permet de remplir la liste des valeurs de commandes totales.
		for (IDistributeur d : dictionnaire.keySet()) {
			valeurscommandes.add(dictionnaire.get(d));
		}
		//on trie cette liste
		TrierDecroissant(valeurscommandes);
		
		//ce for permet de remplir la liste des distributeur 
		//en s'aidant de la liste obtenue precedemment
		for (double quantite : valeurscommandes) {
			for (IDistributeur d : dictionnaire.keySet()) {
				if(dictionnaire.get(d) == quantite && !priorite.contains(d)) {
					priorite.add(d);
				}
			}
		}
		return priorite;
	}
	
	//on possède donc l'ordre dans lequel faire les commandes, 
	//c'est l'ordre défini par la liste renvoyée par la methode ci dessus
	//maintenant, il faut executer les commandes. 
	//Pour cela il faut d'abord calculer la quantité de cacao nécessaire
	//prends un IDistributeur en argument, et une Liste de commandesdistri
	//calcule la quantite de cacao necessaire pour
	//la réalisation de toutes les commandes de ce distributeur

	private static double CacaoNecessaire(IDistributeur dist, List<CommandeDistri> lcd) {
		double cacaonecessaire = 0.;
		for (CommandeDistri cd : lcd) {
			if (cd.getAcheteur().equals(dist)) {
				cacaonecessaire+=cd.getProduit().getRatioCacao()*cd.getQuantite();
				}
			}
		return cacaonecessaire;
	}
	
	//Enfin, une fois que l'on a la cacao nécessaire, pour le IDistributeur d 
	//il faut alors produire en tenant compte des stocks
	//Cette méthode produit pour un Distributeur particulier
	private void setTransformation(IDistributeur d, List<CommandeDistri> lcd, StockCacao scac, StockChocolats schoc) {
		double q = CacaoNecessaire(d, lcd);
		for (CommandeDistri cd : lcd) {
			double stockcacao = scac.getStockcacao().get(Constante.CACAO);
			double limite = Constante.RATIO_TRANSFO;
			Produit produit = cd.getProduit();
			double dejaproduit = this.getTransformation().get(produit);
			double r = produit.getRatioCacao();
			double stockchoc = schoc.getStock().get(produit);
			if (cd.getAcheteur().equals(d)) {
				if (stockcacao>limite*q) { // si on a beaucoup de cacao en stock
					if (!(stockchoc>limite*q/r)) { // et peu ou moyennement de chocolat
						double production = limite*q/r - stockchoc;
						this.transformation.put(produit,dejaproduit+production);
						stockcacao -= r*production;
						stockchoc +=production;
					}
				}
				else if (stockcacao<limite*q && stockcacao>q) {//si le stock en cacao est moyen
					if (stockchoc>q/r && stockchoc<limite*q/r) {// si le stock de chocolat est moyen
						double production = q/r; // on produit q
						this.transformation.put(produit, dejaproduit+production);
						stockcacao -= r*production;
						stockchoc +=production;
					}
					else {//le stock de chocolat est bas
						double production = q/r-stockchoc; // on produit q-schoc
						this.transformation.put(produit, dejaproduit+production);
						stockcacao -= r*production;
						stockchoc +=production;
					}	
				}
				else {//si le stock de cacao est bas
					if (stockchoc<q/r) {
						if (stockcacao>(q-stockchoc)) {
							double production = (q-stockchoc)/r;
							this.transformation.put(produit, dejaproduit+production);
							stockcacao -= r*production;
							stockchoc +=production;
						}
						else {
							double production = stockcacao/r;
							this.transformation.put(produit, dejaproduit+production);
							stockcacao -= r*production;
							stockchoc +=production;
						}
					}
				}
			}
		}
	}
	
	//maintenant que l'on sait attribuer la production en fonction des distributeurs, alors 
	//il suffit de le faire pour tous les IDIstriuteurs de la liste triée
	//c'est cette méthode (et uniquement celle là, sauf peut être le getter) 
	//qui sera appelé par nestle
	public void setTransformation (List<CommandeDistri> lcd, StockCacao scac, StockChocolats schoc) {
		List<IDistributeur> priorite = Priorite(lcd);
		for (IDistributeur d : priorite) {
			this.setTransformation(d, lcd, scac, schoc);
		}
	}
	
	//Cette dernière méthode retourne la quantite tolate de cacao transformée après la transformation 
	//(idéale pour les calculs de couts de transformation)
	public double CacaoTransforme() {
		double resultat = 0.;
		for (Produit p : this.getTransformation().keySet()) {
			resultat+=p.getRatioCacao()*this.getTransformation().get(p);
		}
		return resultat;
	}
	
	
	//début des tests de la classe transformation
	public static void main(String[] args) {
		//à faire
	}
}
