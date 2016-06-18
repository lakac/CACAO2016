package abstraction.equipe2;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.*;
import abstraction.equipe6.Carrefour;


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
	//Elle s'avère nécessaire pour classer les Distributeurs
	private static List<Double> TrierDecroissant(List<Double> l) {
		List<Double> resultat = new ArrayList<Double>();
		int taille = l.size();
		Collections.sort(l);
		for (int i=0; i<taille; i++) {
			resultat.add(l.get(taille-1-i));
		}
		return resultat;
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
		List<Double> valeurscommandestriees = TrierDecroissant(valeurscommandes);
		
		//ce for permet de remplir la liste des distributeur 
		//en s'aidant de la liste obtenue precedemment
		for (double quantite : valeurscommandestriees) {
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
		//test des constructeurs
		Transformation trans0 = new Transformation();
		Transformation trans1 = new Transformation(100, 200, 300);
		if (trans0.getTransformation().get(Constante.PRODUIT_50) != 0) {
			System.out.println("Aïe, l constructeur par éfaut n'initialise pas le chocolat 50% à 0");
		}
		else if (trans0.getTransformation().get(Constante.PRODUIT_60) != 0) {
			System.out.println("Aïe, l constructeur par éfaut n'initialise pas le chocolat 60% à 0");
		}
		else if (trans0.getTransformation().get(Constante.PRODUIT_70) != 0) {
			System.out.println("Aïe, l constructeur par éfaut n'initialise pas le chocolat 70% à 0");
		}
		else {
			System.out.println("Ok, le constructeur par défaut passe le test");
		}
		if (trans1.getTransformation().get(Constante.PRODUIT_50) != 100) {
			System.out.println("Aïe, l constructeur par éfaut n'initialise pas le chocolat 50% à 0");
		}
		else if (trans1.getTransformation().get(Constante.PRODUIT_60) != 200) {
			System.out.println("Aïe, l constructeur par éfaut n'initialise pas le chocolat 60% à 0");
		}
		else if (trans1.getTransformation().get(Constante.PRODUIT_70) != 300) {
			System.out.println("Aïe, l constructeur par éfaut n'initialise pas le chocolat 70% à 0");
		}
		else {
			System.out.println("Ok, le constructeur passe le test");
		}
		
		//Test de Commandes Totales
		//Initialisation des commandes 
		//création des IDistributeurs
		Carrefour c1 = new Carrefour();
		Carrefour c2 = new Carrefour();
		Carrefour c3 = new Carrefour();
		//Création des commandes
		CommandeDistri cd1 = new CommandeDistri(c1, Constante.PRODUIT_50, 500, 6);
		CommandeDistri cd2 = new CommandeDistri(c1, Constante.PRODUIT_60, 800, 9);
		CommandeDistri cd3 = new CommandeDistri(c2, Constante.PRODUIT_70, 460, 32);
		CommandeDistri cd4 = new CommandeDistri(c2, Constante.PRODUIT_70, 540, 6);
		CommandeDistri cd5 = new CommandeDistri(c3, Constante.PRODUIT_60, 900, 9);
		CommandeDistri cd6 = new CommandeDistri(c3, Constante.PRODUIT_50, 500, 6);
		//Initialisation de la liste des commandes
		List<CommandeDistri> lcd =  new ArrayList<CommandeDistri>();
		lcd.add(cd1); lcd.add(cd2); lcd.add(cd3);lcd.add(cd4); lcd.add(cd5); lcd.add(cd6);
		//Initialisation du dictionnaire
		HashMap<IDistributeur, Double> dico = CommandesTotales(lcd);
		//début des tests de Commandes totales
		if (dico.get(c1) != 1300) {
			if (dico.get(c1) == 800) {
				System.out.println("Aïe, on dirait que la méthode ne fonctionne pas si plusieurs commandes sont passées par le même distributeur");
			}
			else {
				if (dico.get(c2) == 1300) {
					System.out.println("La méthode n'attribue pas le con total de commande au bon distributeur");
				}
				else {
					if (dico.get(c3) == 8600) {
						System.out.println("Aië, la méthode Commandes totale tient compte des prix de ventes");
					}
					else {
						if (dico.get(c1) == cd1.getQuantite()+cd6.getQuantite()) {
							System.out.println("Aïe, la méthode s'ajuste sur les produits et non sur les distributeurs");
						}
						else {
							System.out.println("Aïe, la méthode est incorrecte");
						}
					}
				}
			}
		}
		else {
			System.out.println("Ok, CommandesTotales à l'air correcte");
		}
		
		//test de TrierDecroissant
		//création de la liste de commandes totales
		List<Double> li = new ArrayList<Double>();
		for (IDistributeur d : dico.keySet()) {
			li.add(dico.get(d));
		}
		//On trie la liste 
		int n = li.size();
		List<Double> li2 = TrierDecroissant(li);
		System.out.print(""+li2.get(0)+" "+li2.get(1)+" "+li2.get(2));
		System.out.println(" <-- voici les valeurs de la liste");
		//Début des tests
		if (li2.get(n-1)>li2.get(n-2)) {
			if (li2.get(n-2)>=li2.get(n-3)) {
				System.out.println("Aïe, La liste est triée dans l'ordre croissant");				}
			else {
				System.out.println("Aïe, la liste n'est pas triée");
			}
		}
		else {
			if (li2.get(n-2)>=li2.get(n-3)) {
				System.out.println("Aïe, la liste n'est pas triée");
			}
			else {
				System.out.println("Ok, TrierDecroissant semble correcte");
			}
		}
		
		//Test de priorite (à continuer)
		
	}
}
