package abstraction.equipe2;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.*;
import abstraction.equipe6.Carrefour;


public class Transformation {
	
	//variables d'instance, 
	//repr�sente � l'�tape n la quantit� produite de chocolat de chaque esp�ce
	private HashMap<Produit,Double> transformation;
		
	//accesseur en lecture
	public HashMap<Produit,Double> getTransformation() {
		return this.transformation;
	}
	
	//ce constructeur ne sera jamais utilis�, 
	//il vaudrait mieux garder le constructeur vide et des m�thodes de production � mon sens
	//mais je vais l'utiliser pour les tests de la tr�sorerie
	//Constructeurs
	//Un constructeur g�n�ral 
	public Transformation(double chocolat50, double chocolat60, double chocolat70){
		this.transformation=new HashMap<Produit,Double>();
		this.transformation.put(Constante.PRODUIT_50, chocolat50);
		this.transformation.put(Constante.PRODUIT_60, chocolat60);
		this.transformation.put(Constante.PRODUIT_70, chocolat70);
	}
	
	//Remplit le dictionnaire avec 0 comme valeur de production pour chaque 
	//(� appeler dans le constructeur de Nestle)
	public Transformation(){
		this(0.0,0.0,0.0);
	}
	
	//Il faut maintenant d�terminer 
	//quel est le Distributeur qui a command� le plus lors de la step pr�c�dente
	//Il faut alors obtenir le total des commandes des distributeurs.
	//Cette m�thode statique prend une liste de commande en argument 
	//et renvoie un dictionnaire de IDistributeur, double 
	//indiquant la quantite totale de chocolat demande pour chaque IDistributeur
	private static HashMap<IDistributeur, Double> CommandesTotales(List<CommandeDistri> lcd) {
		HashMap<IDistributeur, Double> dictionnaire = new HashMap<IDistributeur, Double>();
		if(lcd.size()==0){
			System.out.println("La liste envoy�e par les distributeurs est vide");
			return dictionnaire;
		}else{
		for (CommandeDistri cd : lcd) {
			System.out.println("Les acheteurs sont : " + cd.getAcheteur());
			dictionnaire.put(cd.getAcheteur(), 0.);
			
		}
		for (CommandeDistri cd : lcd) {
			double quantite = dictionnaire.get(cd.getAcheteur());
			dictionnaire.put(cd.getAcheteur(), quantite+cd.getQuantite());
		}
		System.out.println("dictionnaire : "+ dictionnaire);
		return dictionnaire;
		}
		
	}
	
	//La m�thode qui suit me sert � trier une liste. 
	//Elle s'av�re n�cessaire pour classer les Distributeurs
	private static List<Double> TrierDecroissant(List<Double> l) {
		List<Double> resultat = new ArrayList<Double>();
		int taille = l.size();
		Collections.sort(l);
		for (int i=0; i<taille; i++) {
			resultat.add(l.get(taille-1-i));
		}
		return resultat;
	}
	
	//Cette m�thode prend en argument une liste de CommandeDistri
	//et renvoie une liste de distributeur class� par ordre de priorit� de transformation
	//le premier sera celui � faire en priorit�, et ainsi de suite.
	public static List<IDistributeur> Priorite(List<CommandeDistri> lcd) {
		List<IDistributeur> priorite = new ArrayList<IDistributeur>();
		List<Double> valeurscommandes = new ArrayList<Double>();
		HashMap<IDistributeur, Double> dictionnaire = CommandesTotales(lcd);
		//Ce for permet de remplir la liste des valeurs de commandes totales.
		if(lcd.size()==0){
			System.out.println("Liste de commande vide ...");
			return null;
		}else{
		for (IDistributeur d : dictionnaire.keySet()) {
			valeurscommandes.add(dictionnaire.get(d));
		}
		//on trie cette liste
		List<Double> valeurscommandestriees = TrierDecroissant(valeurscommandes);
		//On inclut la part de march� des distributeurs
		
		//ce for permet de remplir la liste des distributeur 
		//en s'aidant de la liste obtenue precedemment
		for (double quantite : valeurscommandestriees) {
			for (IDistributeur d : dictionnaire.keySet()) {
				if(dictionnaire.get(d) == quantite && !priorite.contains(d)) {
					priorite.add(d);
				}
			}
		}}
		return priorite;
	}
	
	//on poss�de donc l'ordre dans lequel faire les commandes, 
	//c'est l'ordre d�fini par la liste renvoy�e par la methode ci dessus
	//maintenant, il faut executer les commandes. 
	//Pour cela il faut d'abord calculer la quantit� de cacao n�cessaire
	//prends un IDistributeur en argument, et une Liste de commandesdistri
	//calcule la quantite de cacao necessaire pour
	//la r�alisation de toutes les commandes de ce distributeur

	private static double CacaoNecessaire(IDistributeur dist, List<CommandeDistri> lcd) {
		double cacaonecessaire = 0.;
		for (CommandeDistri cd : lcd) {
			if (cd.getAcheteur().equals(dist)) {
				cacaonecessaire+=cd.getProduit().getRatioCacao()*cd.getQuantite();
				}
			}
		return cacaonecessaire;
	}
	
	//Enfin, une fois que l'on a la cacao n�cessaire, pour le IDistributeur d 
	//il faut alors produire en tenant compte des stocks
	//Cette m�thode produit pour un Distributeur particulier
	private void setTransformation(IDistributeur d, List<CommandeDistri> lcd, StockCacao scac, StockChocolats schoc) {
		double q = CacaoNecessaire(d, lcd);
		double stockcacao = scac.getStockcacao().get(Constante.CACAO);
		double limite = Constante.RATIO_TRANSFO;
		for (CommandeDistri cd : lcd) {
			Produit produit = cd.getProduit();
			double dejaproduit = this.getTransformation().get(produit);
			double r = produit.getRatioCacao();
			double stockchoc = schoc.getStock().get(produit);
			double qint = cd.getQuantite()*r;
			if (cd.getAcheteur()== d) {
				if (stockcacao>limite*q) { // si on a beaucoup de cacao en stock
					if (!(stockchoc>limite*qint/r)) { // et peu ou moyennement de chocolat
						double production = limite*qint/r - stockchoc;
						this.transformation.put(produit,dejaproduit+production);
						stockcacao -= r*production;
						stockchoc +=production;
						q-=qint;
					}
				}
				else if (stockcacao<limite*q && stockcacao>q) {//si le stock en cacao est moyen
					if (stockchoc>qint/r && stockchoc<limite*qint/r) {// si le stock de chocolat est moyen
						double production = qint/r; // on produit q
						this.transformation.put(produit, dejaproduit+production);
						stockcacao -= r*production;
						stockchoc +=production;
						q-=qint;
					}
					else {//le stock de chocolat est bas
						double production = qint/r-stockchoc; // on produit q-schoc
						this.transformation.put(produit, dejaproduit+production);
						stockcacao -= r*production;
						stockchoc +=production;
						q-=qint;
					}	
				}
				else {//si le stock de cacao est bas
					if (stockchoc<q/r) {
						if (stockcacao>(qint-stockchoc)) {
							double production = (qint-stockchoc)/r;
							this.transformation.put(produit, dejaproduit+production);
							stockcacao -= r*production;
							stockchoc +=production;
							q-=qint;
						}
						else {
							double production = stockcacao/r;
							this.transformation.put(produit, dejaproduit+production);
							stockcacao -= r*production;
							stockchoc +=production;
							q-=qint;
						}
					}
				}
			}
		}
	}
	
	//maintenant que l'on sait attribuer la production en fonction des distributeurs, alors 
	//il suffit de le faire pour tous les IDIstriuteurs de la liste tri�e
	//c'est cette m�thode (et uniquement celle l�, sauf peut �tre le getter) 
	//qui sera appel� par nestle
	public void setTransformation (List<CommandeDistri> lcd, StockCacao scac, StockChocolats schoc) {
		List<IDistributeur> priorite = Priorite(lcd);
		for (IDistributeur d : priorite) {
			this.setTransformation(d, lcd, scac, schoc);
		}
	}
	
	//Cette m�thode retourne la quantite tolate de cacao transform�e apr�s la transformation 
	//(id�ale pour les calculs de couts de transformation)
	public double CacaoTransforme() {
		double resultat = 0.;
		for (Produit p : this.getTransformation().keySet()) {
			resultat+=p.getRatioCacao()*this.getTransformation().get(p);
		}
		return resultat;
	}
	
	//Une m�thode pour remettre la transformation � z�ro
	public void razTransformation() {
		for (Produit p : this.getTransformation().keySet()) {
			this.transformation.put(p, 0.);
		}
	}
	
	public String toString() {
		return ("50% : "+this.getTransformation().get(Constante.PRODUIT_50)+", "+
				"60% : "+this.getTransformation().get(Constante.PRODUIT_60)+", "+
				"70% : "+this.getTransformation().get(Constante.PRODUIT_70));
	}
	
	
	//d�but des tests de la classe transformation
	public static void main(String[] args) {
		//test des constructeurs
		Transformation trans0 = new Transformation();
		Transformation trans1 = new Transformation(100, 200, 300);
		if (trans0.getTransformation().get(Constante.PRODUIT_50) != 0) {
			System.out.println("A�e, le constructeur par d�faut n'initialise pas le chocolat 50% � 0");
		}
		else if (trans0.getTransformation().get(Constante.PRODUIT_60) != 0) {
			System.out.println("A�e, le constructeur par d�faut n'initialise pas le chocolat 60% � 0");
		}
		else if (trans0.getTransformation().get(Constante.PRODUIT_70) != 0) {
			System.out.println("A�e, le constructeur par d�faut n'initialise pas le chocolat 70% � 0");
		}
		else {
			System.out.println("Ok, le constructeur par d�faut passe le test");
		}
		if (trans1.getTransformation().get(Constante.PRODUIT_50) != 100) {
			System.out.println("A�e, le constructeur par d�faut n'initialise pas le chocolat 50% � 100");
		}
		else if (trans1.getTransformation().get(Constante.PRODUIT_60) != 200) {
			System.out.println("A�e, le constructeur par d�faut n'initialise pas le chocolat 60% � 200");
		}
		else if (trans1.getTransformation().get(Constante.PRODUIT_70) != 300) {
			System.out.println("A�e, le constructeur par d�faut n'initialise pas le chocolat 70% � 300");
		}
		else {
			System.out.println("Ok, le constructeur passe le test");
		}
		
		//Test de Commandes Totales
		//Initialisation des commandes 
		//cr�ation des IDistributeurs
		Carrefour c1 = new Carrefour();
		Carrefour c2 = new Carrefour();
		Carrefour c3 = new Carrefour();
		//Cr�ation des commandes
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
		//d�but des tests de Commandes totales
		if (dico.get(c1) != 1300) {
			if (dico.get(c1) == 800) {
				System.out.println("A�e, on dirait que la m�thode ne fonctionne pas si plusieurs commandes sont pass�es par le m�me distributeur");
			}
			else {
				if (dico.get(c2) == 1300) {
					System.out.println("La m�thode n'attribue pas le con total de commande au bon distributeur");
				}
				else {
					if (dico.get(c3) == 8600) {
						System.out.println("Ai�, la m�thode Commandes totale tient compte des prix de ventes");
					}
					else {
						if (dico.get(c1) == cd1.getQuantite()+cd6.getQuantite()) {
							System.out.println("A�e, la m�thode s'ajuste sur les produits et non sur les distributeurs");
						}
						else {
							System.out.println("A�e, la m�thode est incorrecte");
						}
					}
				}
			}
		}
		else {
			System.out.println("Ok, CommandesTotales � l'air correcte");
		}
		
		//test de TrierDecroissant
		//cr�ation de la liste de commandes totales
		List<Double> li = new ArrayList<Double>();
		for (IDistributeur d : dico.keySet()) {
			li.add(dico.get(d));
		}
		//On trie la liste 
		int n = li.size();
		List<Double> li2 = TrierDecroissant(li);
		System.out.print(""+li2.get(0)+" "+li2.get(1)+" "+li2.get(2));
		System.out.println(" <-- voici les valeurs de la liste");
		//D�but des tests
		if (li2.get(n-1)>li2.get(n-2)) {
			if (li2.get(n-2)>=li2.get(n-3)) {
				System.out.println("A�e, La liste est tri�e dans l'ordre croissant");				}
			else {
				System.out.println("A�e, la liste n'est pas tri�e");
			}
		}
		else {
			if (li2.get(n-2)>=li2.get(n-3)) {
				System.out.println("A�e, la liste n'est pas tri�e");
			}
			else {
				System.out.println("Ok, TrierDecroissant semble correcte");
			}
		}
		//Test de priorite
		List<IDistributeur> ld = Priorite(lcd);
		int n2 = ld.size();
		if (n2 != 3) {
			System.out.println("Priorite renvoie une liste de longueur "+n2+" au lien de 3");
		}
		else {
			if (ld.get(0) != c3) {
				if (ld.get(1) == c1) {
					System.out.println("A�e, la liste est tri�e dans l'ordre croissant");
				}
				else {
					System.out.println("A�e, la liste n'est pas tri�e");
				}
			}
			else {
				if (ld.get(1) != c1) {
					System.out.println("A�, la liste n'est pas tri�e");
				}
				else {
					System.out.println("Ok, Priorite passe le test");
				}
			}
			//Test de CacaoNecessaire
			//initialisation
			double cac1 = CacaoNecessaire(c1, lcd);
			double cac2 = CacaoNecessaire(c2, lcd);
			double cac3 = CacaoNecessaire(c3, lcd);
			//d�but des tests
			if (cac1 != 730) {//730 = 500*0.5+800*0.6
				if (cac1 == 1300) {
					System.out.println("A�, CacaoNecessaire ne tient pas compte des ratios");
				}
				else if (cac1 == 700) {
					System.out.println("A�e, les proportions de prises en compte sont invers�es");
				}
				else {
					System.out.println("A�e, la m�thode Cacaonecessaire sur cd1 renvoie "+cac1+" au lieu de 630");
				}
			}
			else {
				if (cac2 == 700 && cac3 == 790) {
					System.out.println("Ok, CacaoNecessaire � l'air correct");
				}
				else {
					System.out.println("A�e, CacaoNecessaire sur c2 et c3 renvoie "+cac2 +" et "+cac3+"au lieu de 700 et 790");
				}
			}
		}
		//test du premier setTransformation
		//initialisation des stocks de cacao
		double q = CacaoNecessaire(c1, lcd);
		Produit cacao = Constante.CACAO;
		Produit p50 = Constante.PRODUIT_50;
		Produit p60 = Constante.PRODUIT_60;
		double limite = Constante.RATIO_TRANSFO;
		double r1 = Constante.PRODUIT_50.getRatioCacao();
		double r2 = Constante.PRODUIT_60.getRatioCacao();
		StockCacao scac1 = new StockCacao(); //stock de cacao suffisant
		scac1.MiseAJourStockLivraison(cacao, q*limite);
		StockCacao scac2 = new StockCacao(); //stock de cacao moyen
		scac2.MiseAJourStockLivraison(cacao, 5*q/4);
		StockCacao scac3 = new StockCacao(); //stock de cacao insuffisant
		scac3.MiseAJourStockLivraison(cacao, q/2);
		
		//initialisation des stocks de chocolat
		StockChocolats schoc1 = new StockChocolats(); //50% assez, 60% assez
		schoc1.MiseAJourStockTransformation(p50, limite*q/r1);
		schoc1.MiseAJourStockTransformation(p60, limite*q/r2);
		StockChocolats schoc2 = new StockChocolats(); //50% assez, 60% moyen
		schoc1.MiseAJourStockTransformation(p50, limite*q/r1);
		schoc1.MiseAJourStockTransformation(p60, 5*q/(4*r2));
		StockChocolats schoc3 = new StockChocolats(); //50% assez, 60% bas
		schoc1.MiseAJourStockTransformation(p50, limite*q/r1);
		schoc1.MiseAJourStockTransformation(p60, q/(2*r2));
		StockChocolats schoc4 = new StockChocolats(); //50% moyen, 60% assez
		schoc1.MiseAJourStockTransformation(p50, 5*q/(4*r1));
		schoc1.MiseAJourStockTransformation(p60, limite*q/r2);
		StockChocolats schoc5 = new StockChocolats(); //50% moyen, 60% moyen
		schoc1.MiseAJourStockTransformation(p50, 5*q/(4*r1));
		schoc1.MiseAJourStockTransformation(p60, 5*q/(4*r2));
		StockChocolats schoc6 = new StockChocolats(); //50% moyen, 60% bas
		schoc1.MiseAJourStockTransformation(p50, 5*q/(4*r1));
		schoc1.MiseAJourStockTransformation(p60, q/(2*r2));
		StockChocolats schoc7 = new StockChocolats(); //50% bas, 60% assez
		schoc1.MiseAJourStockTransformation(p50, q/(2*r1));
		schoc1.MiseAJourStockTransformation(p60, limite*q/r2);
		StockChocolats schoc8 = new StockChocolats(); //50% bas, 60% moyen
		schoc1.MiseAJourStockTransformation(p50, q/(2*r1));
		schoc1.MiseAJourStockTransformation(p60, 5*q/(4*r2));
		StockChocolats schoc9 = new StockChocolats(); //50% bas, 60% bas
		schoc1.MiseAJourStockTransformation(p50, q/(2*r1));
		schoc1.MiseAJourStockTransformation(p60, q/(2*r2));
		
		//d�but des tests
		trans0.setTransformation(c1, lcd, scac1, schoc1); //stock de chocolat et de cacao tr�s larges
		if (trans0.getTransformation().get(p50) != 0 && trans0.getTransformation().get(p60) != 0) {
			System.out.println("A�e, pour des stocks tr�s larges, il ne doit pas y avoir de production de faite");
		}
		else {
			System.out.println(trans0.toString());
			trans0.razTransformation();
			trans0.setTransformation(c1, lcd, scac1, schoc2);
			System.out.println(trans0.toString());
			trans0.razTransformation();
			trans0.setTransformation(c1, lcd, scac1, schoc3);
			System.out.println(trans0.toString());
			trans0.razTransformation();
			trans0.setTransformation(c1, lcd, scac2, schoc4);
			System.out.println(trans0.toString());
			trans0.razTransformation();
			trans0.setTransformation(c1, lcd, scac2, schoc5);
			System.out.println(trans0.toString());
			trans0.razTransformation();
			trans0.setTransformation(c1, lcd, scac2, schoc6);
			System.out.println(trans0.toString());
			trans0.razTransformation();
			trans0.setTransformation(c1, lcd, scac3, schoc7);
			System.out.println(trans0.toString());
			trans0.razTransformation();
			trans0.setTransformation(c1, lcd, scac3, schoc8);
			System.out.println(trans0.toString());
			trans0.razTransformation();
			trans0.setTransformation(c1, lcd, scac3, schoc9);
			System.out.println(trans0.toString());
			System.out.println("Si les valeurs vous semblent coh�rentes, c'est que setTransformation ne fait pas n'importe quoi");
		}
		//test du second setTransformation
		StockCacao scac4 = new StockCacao();
		scac4.MiseAJourStockLivraison(cacao, 10000000);
		StockChocolats schoc10 = new StockChocolats();
		schoc10.MiseAJourStockTransformation(p50, 0);
		schoc10.MiseAJourStockTransformation(p60, 0);
		schoc10.MiseAJourStockTransformation(Constante.PRODUIT_70, 0.);
		trans0.razTransformation();
		trans0.setTransformation(lcd, scac4, schoc10);
		System.out.println(trans0.toString());
		System.out.println("vous devriez obtenir dans l'ordre 1500, 2550, 1500");
		
		//test de CacaoTransforme
		System.out.println(trans0.CacaoTransforme());

		System.out.println("Si vous obtenez 3330 le test est bon, sinon il y a uen erreur");

		
		//fin des tests
	}
}
