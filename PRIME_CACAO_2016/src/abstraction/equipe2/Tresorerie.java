package abstraction.equipe2;

import abstraction.commun.*;
import abstraction.equipe1.Producteur;
import abstraction.fourni.Monde;

public class Tresorerie {
	private double fonds;
	
	//Un constructeur initial (initialise la tr�sorerie avec TRESORERIE INITIALE)
	public Tresorerie() {
		this.fonds = Constante.TRESORERIE_INITIALE;
	}
	//getter (un seul getter de tr�sorerie, va �tre appel� par Nestl�)
	public double getFonds() {
		return fonds;
	}
	
	
	//Toutes les m�thodes qui suivent calculent les diff�rents couts d'achat et de vente
	//M�thode qui calcule le cout d'achat d'une commande pass�e aux producteurs
	public double CoutdAchat(CommandeProduc cp) {
		double coutachat = cp.getPrixTonne()*cp.getQuantite();
		return coutachat;
	}
	
	//Calcule le cout de transport d'une commande en Cacao 
	//(les livraisons de chocolats �tant � la charges des distributeurs) 
	public double CoutTransport(CommandeProduc cp) {
			IProducteur p = cp.getVendeur();
			double distance = Constante.DISTANCE_MONDE;
			double couttransport = cp.getQuantite()*distance*Constante.COUT_UNITAIRE_TRANSPORT;
			return couttransport;
		}
	
	//setter d'achat de la tr�sorerie 
	//(c'est la m�thode qui va �tre appel� par Nestle lors d'une livraison en cacao)
	//Si l'achat rent la tr�so n�gatif, on ne fait rien, sinon on enl�ve l'achat et les couts de trasport
	public void setTresorerieAchat(CommandeProduc cp) {
		double achats = CoutdAchat(cp);
		double transport = CoutTransport(cp);
		if (this.getFonds()-achats-transport >=0) {
			this.fonds-=CoutdAchat(cp);
			this.fonds-=CoutTransport(cp);
		}
		else {
			System.out.print("pas assez de tr�sorerie, aucun achat n'a �t� effectu� ");
		}
	}
		
	
	
	//M�thode qui calcule le cout d'une vente d'une commande aux distributeurs
	public double CoutVente(CommandeDistri cd) {
		double coutvente = cd.getPrixTonne()*cd.getQuantite();
		return coutvente;
	}
	//setter de vente de la tr�sorerie (m�thode appel�e par Nestle)
	public void setTresorerieVente(CommandeDistri cd) {
		this.fonds+=CoutVente(cd);
	}
	
	
	
	//Cette m�thode calcule les couts de stock de cacao 
	public double CoutStockcacao (StockCacao scac) {
		double coutstockcac = scac.getStockcacao().get(Constante.CACAO)*Constante.COUT_STOCK_UNITAIRE;
		return coutstockcac;
	}
	
	//Cette m�thode quant � elle calcule les couts de stock de chocolat
	public double CoutStockchoc(StockChocolats schoc) {
		double coutstockchoc = schoc.Quantitetotalchoc()*Constante.COUT_STOCK_UNITAIRE;
		return coutstockchoc;
	}
	
	//setter de stock (la m�thode appel� par Nestle)
	public void setTresorerieStock(StockCacao scac, StockChocolats schoc) {
		double coutstockcacao = this.CoutStockcacao(scac);
		double coutstockchoc = this.CoutStockchoc(schoc);
		if (coutstockcacao+coutstockchoc<=this.getFonds()) {
			this.fonds-=coutstockcacao;
			this.fonds-=coutstockchoc;
		}
		else {
			this.fonds = 0.;
			System.out.print("les couts de stock sont trop �lev�s, la tr�sorerie est � sec");
		}
	}
	
	//La classe transformation est encore incompl�te,
	//n�cessit� de la remplir avant de pouvoir tester cette partie
	
	//Calcule le cout de transformation lorsuq'on effectue une transformation t
	public double CoutTransformation (Transformation t) {
		double couttransformation = t.CacaoTransforme()*Constante.COUT_DE_TRANSFORMATION;
		return couttransformation;
	}
	
	//Setter de transformation de la tr�sorerie (appel� par Nestle)
	public void setTresorerieTransformation(Transformation t) {
		double couttransformation = CoutTransformation(t);
		if (this.getFonds()-couttransformation >0) {
			this.fonds-=couttransformation;
		}
		else {
			double difference = couttransformation-this.getFonds();
			this.fonds = 0;
			System.out.print("la tr�sorerie est � sec, "+difference+" n'ont pas pu �tre pay�");
		}
	}
	
	//fin des m�thodes 
	//d�but des tests
	
	public static void main(String[] args) {
		//test du constructeur
		Tresorerie tres = new Tresorerie();
		if (tres.getFonds() != Constante.TRESORERIE_INITIALE) {
			System.out.println("le constructeur initialise mal la tr�sorerie");
		}
		else {
			System.out.println("le constructeur semble correct");
		}
		
		//test des m�thodes relatives au setter d'achat et test du setter d'achat.
		//Initialisation de commandeProducs
		ITransformateurP acheteurtest = new Nestle_new();
		IProducteur vendeurtest = new Producteur("vendeurtest");
		CommandeProduc cp1 = new CommandeProduc(acheteurtest, vendeurtest, 0, 0);
		CommandeProduc cp2 = new CommandeProduc(acheteurtest, vendeurtest, 500, 6);
		CommandeProduc cp3 = new CommandeProduc(acheteurtest, vendeurtest, 5000, 3);
		//test de cout d'achat
		if (tres.CoutdAchat(cp1)!=0) {
			System.out.println("a�e, sur une commande vide, son cout d'achat n'est pas nul");
		}
		else {
			if (tres.CoutdAchat(cp2) == cp2.getQuantite()) {
			System.out.println("a�e, la commande ne prend pas en compte le prix d'achat");
			}
			else if (tres.CoutdAchat(cp2) != cp2.getQuantite()*cp2.getPrixTonne()) {
				System.out.println("a�e, une commande de "+cp2.getQuantite()+" au prix de "+cp2.getPrixTonne()+" est achet�e "+tres.CoutdAchat(cp2));
			}
			else {
				if (tres.CoutdAchat(cp3) == cp3.getPrixTonne()) {
					System.out.println("a�e, la commande ne prend pas en compte la quantit�");
				}
				else if (tres.CoutdAchat(cp3) != cp3.getQuantite()*cp3.getPrixTonne()) {
					System.out.println("a�e, une commande de "+cp3.getQuantite()+" au prix de "+cp3.getPrixTonne()+" est achet�e "+tres.CoutdAchat(cp3));					
				}
				else {
					System.out.println("CoutdAchat semble correct");
				}
			}
		}
		//Test de CoutTransport
		//Initialisation du dictionnaire des distances 
		IProducteur producteurtest1 = new Producteur("producteurtest1");
		IProducteur producteurtest2 = new Producteur("producteurtest2");
		IProducteur producteurtest3 = new Producteur("producteurtest3");
		//nouvelles commandes 
		CommandeProduc cp4 = new CommandeProduc(acheteurtest, producteurtest1, 400, 2);
		CommandeProduc cp5 = new CommandeProduc(acheteurtest, producteurtest2, 9000, 3);
		CommandeProduc cp6 = new CommandeProduc(acheteurtest, producteurtest3, 60000, 9);
		if (tres.CoutTransport(cp4) != cp4.getQuantite()*Constante.COUT_UNITAIRE_TRANSPORT*Constante.DISTANCE_MONDE) {
			if (tres.CoutTransport(cp4) == cp4.getQuantite()) {
				System.out.println("A�e, CoutTransport renvoie juste la quantit� d'une commande");
			}
			else {
				if (tres.CoutTransport(cp5) == cp5.getQuantite()) {
					System.out.println("A�e, CoutTransport ne prend pas en compte le cout unitaire");
				}
				else if (tres.CoutTransport(cp6) == Constante.DISTANCE_MONDE*Constante.COUT_UNITAIRE_TRANSPORT) {
					System.out.println("A�e, CoutTransport ne pred pas en compte la quantit� de cacao");
				}
				else if (tres.CoutTransport(cp4) == cp5.getQuantite()*Constante.COUT_UNITAIRE_TRANSPORT) {
					System.out.println("A�e, CoutTransport ne prend pas en compte l'�loignement");
				}
			}
		}
		else {
			System.out.println("Ok, CoutTransport semble correct");
		}
	
		//test du setter d'achat
		double tres_old = tres.getFonds();
		tres.setTresorerieAchat(cp4);
		if (tres.getFonds() != tres_old - tres.CoutdAchat(cp4)-tres.CoutTransport(cp4)) {
			if (tres.getFonds() == tres_old - tres.CoutdAchat(cp4)) {
				System.out.println("Aie, il manque les couts de transports");
			}
			else {
				if (tres.getFonds() == tres_old - tres.CoutTransport(cp4)) {
					System.out.println("A�e, il manque les couts d'achat");
				}
				else {
					System.out.println("A�e, setTresorerie initialise la tr�so � "+tres.getFonds()+" au lieu de "+(tres_old - tres.CoutdAchat(cp4)-tres.CoutTransport(cp4)));
				}
			}
		}
		tres.setTresorerieAchat(cp5);
		if (tres.getFonds() < 0) {
			System.out.println("A�e, la tr�sorerie est n�gative. aucun d�couvert n'est autoris�");
		}
		else {
			System.out.println("--> c'est normal si vous voyez appara�tre un message avertissant que rien n'a �t� effectu�");
			System.out.println("Ok, la m�thode setTresorerieAchat semble correcte");
		}
		//fin des test des m�thodes d'achats
		//Tests des m�thodes de vente (CoutVente et setTresorerieVente)
		CommandeDistri cd1 = new CommandeDistri(Constante.PRODUIT_50, 500, 6);
		CommandeDistri cd2 = new CommandeDistri(Constante.PRODUIT_60, 800, 9);
		CommandeDistri cd3 = new CommandeDistri(Constante.PRODUIT_70, 0, 0);
		if (tres.CoutVente(cd1) != 3000) {
			if (tres.CoutVente(cd2) == 800) {
				System.out.println("A�e, CoutVente ne prend pas en compte le prix � la tonne");
			}
			else if (tres.CoutVente(cd3) != 0) {
				System.out.println("A�e, une commande de 0 est pay� "+tres.CoutVente(cd3)+" au lieu de 0");
			}
			else {
				if (tres.CoutVente(cd1) == 6) {
					System.out.println("A�e, CoutVente ne prend pas en compte la quantit�");
				}
				else if (tres.CoutVente(cd3) != 5600) {
					System.out.println("A�e, CoutVente sur une achat de 800t � 9� renvoie "+tres.CoutVente(cd3)+" au lieu de 5600");
				}
			}
		}
		else {
			System.out.println("Ok, CoutVente semble correct");
		}
		//test de la m�thode setVentes
		double tres_old2 = tres.getFonds();
		tres.setTresorerieVente(cd1);
		if (tres.getFonds() != tres_old2 + 3000) {
			if (tres.getFonds() == tres_old2) {
				System.out.println("A�e, setTresorerieVente ne fait rien");
			}
			else {
				System.out.println("A�e, setTresorerieVente initialise la tr�sorerie � "+tres.getFonds()+" au lieu de "+(tres_old2+3000));
			}
		}
		else {
			System.out.println("Ok, la m�thode setTresorerieVente semble correcte");
		}
		//fin des tests des m�thodes de ventes
		//teste des m�thodes de stock
		//initialisation du stock de cacao
		StockCacao scac = new StockCacao();
		scac.MiseAJourStockLivraison(Constante.CACAO, 1000);
		scac.MiseAJourStockLivraison(Constante.CACAO, 1000);
		scac.MiseAJourStockLivraison(Constante.CACAO, 1000);
		//test de coutstockcacao
		if (tres.CoutStockcacao(scac) != 3000*Constante.COUT_STOCK_UNITAIRE) {
			if (tres.CoutStockcacao(scac) == 1000) {
				System.out.println("A�e, Coutstockcacao ne prend pas en compte le cout de stock unitaire");
			}
			else {
				System.out.println("A�e, Coutstockcacao renvoie "+tres.CoutStockcacao(scac)+" au lieu de "+(3000*Constante.COUT_STOCK_UNITAIRE));
			}
		}
		else {
			System.out.println("Ok, la m�thode Coutstockcacao semble bonne");
		}
		//initialisation du stock de chocolat
		StockChocolats schoc = new StockChocolats();
		schoc.MiseAJourStockTransformation(Constante.PRODUIT_50, 1000);
		schoc.MiseAJourStockTransformation(Constante.PRODUIT_60, 1000);
		schoc.MiseAJourStockTransformation(Constante.PRODUIT_70, 1000);
		//test de coutstockchocolat
		if (tres.CoutStockchoc(schoc) != 3000*Constante.COUT_STOCK_UNITAIRE) {
			if (tres.CoutStockchoc(schoc) == 3000) {
				System.out.println("A�e, Coutstockchocolat ne prend pas en compte le cout de stock unitaire");
			}
			else {
				System.out.println("A�e, Coutstockchocolat renvoie "+tres.CoutStockchoc(schoc)+" au lieu de "+(3000*Constante.COUT_STOCK_UNITAIRE));
			}
		}
		else {
			System.out.println("Ok, la m�thode Coutstockchocolat semble bonne");
		}
		//test de setTresoreriestock
		double tres_old3 = tres.getFonds();
		tres.setTresorerieStock(scac, schoc);
		if (tres.getFonds() != tres_old3-tres.CoutStockcacao(scac)-tres.CoutStockchoc(schoc)) {
			if (tres.getFonds() == tres_old3-tres.CoutStockcacao(scac)) {
				System.out.println("A�e, la m�thode setTresorerieStock ne prend pas en compte les stocks de chocolats");
			}
			else if (tres.getFonds() == tres_old3-tres.CoutStockchoc(schoc)) {
				System.out.println("A�e, la m�thode setTresorerieStock ne prend pas en compte les stocks de cacao");
			}
			else {
				System.out.println("A�e, la m�thode setTresorerieStock initialise la tr�sorerie � "+tres.getFonds()+"au lieu de "+(tres_old3-tres.CoutStockcacao(scac)-tres.CoutStockchoc(schoc)));
			}
		}
		schoc.MiseAJourStockTransformation(Constante.PRODUIT_50, 90000); 
		tres.setTresorerieStock(scac, schoc);
		if (tres.getFonds()<0) {
			System.out.println("A�e, la tr�sorerie devient n�gative et vaut "+tres.getFonds());
		}
		else {
			System.out.println(" <-- si vous voyez un message indiquant que la tr�sorerie est � sec, c'est normal");
			System.out.println("Ok, setTresorerieStock semble correcte");
		}
		//fin des tests de couts de stock
		Tresorerie tres2 = new Tresorerie(); 
		//d�but des tests de couts de transformation
		//initialisation de la transformation
		Transformation trans1 = new Transformation();
		Transformation trans2 = new Transformation (500, 500, 500);
		Transformation trans3 = new Transformation (1000000, 1000000, 1000000);
		
		if (tres2.CoutTransformation(trans1) != 0) {
			System.out.println("A�e, s'il n'y a aucune transformation, le cout de transformation est de "+tres2.CoutTransformation(trans1)+" au lieu de 0");
		}
		else {
			//System.out.println(tres2.CoutTransformation(trans2));
			if (tres2.CoutTransformation(trans2) != 900*Constante.COUT_DE_TRANSFORMATION) {
				if (tres2.CoutTransformation(trans2) == 900) {
					System.out.println("A�e, le cout de transformation ne prend pas en compte le cout unitaire de transformation");
				}
				else {
					System.out.println("A�e, pour 1500 tonnes de chocolats le cout de transformation vaut "+tres2.CoutTransformation(trans2)+" au lieu de "+(Constante.COUT_DE_TRANSFORMATION*900));
				}
			}
			else {
				System.out.println("Ok, CoutTransformation semble correcte");
			}
		}
		double tres2_old = tres2.getFonds();
		tres2.setTresorerieTransformation(trans2);
		if (tres2.getFonds() != tres2_old-900*Constante.COUT_DE_TRANSFORMATION) {
			if (tres2.getFonds() == tres2_old) {
				System.out.println("la m�thode setCoutTranformation ne fait rien");
			}
		}
		tres2_old = tres2.getFonds();
		tres2.setTresorerieTransformation(trans3);
		if (tres2.getFonds()<0) {
			System.out.println("A�e, la tr�sorerie vaut "+tres2.getFonds());
		}
		else {
			System.out.println("<-- si vous voyez appara�tre un message d'alerte, c'est normal");
			System.out.println("Ok, setTransformation semble correcte");
		}
		//fin des tests
	}
	//fin de la classe
}


