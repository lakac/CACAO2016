package abstraction.equipe2;
//import abstraction.commun.*;
import abstraction.commun.*;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;

import abstraction.equipe6.Carrefour;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;
import abstraction.fourni.v0.Marche;

public class Nestle_new implements Acteur, ITransformateurP, ITransformateurD {
	
	private int etape; //indique l'�tape � laquelle on se trouve.
	
	private String nom; //le nom de l'acteur
	
	private List<IDistributeur> clients; // Liste des clients
	private List<IProducteur> fournisseurs; //Liste des fournisseurs
	
	private List<List<CommandeDistri>> historiquecommandesdistri; // Cet historique garde en m�moire les commandes que les distributeurs nous passent.
	private List<CommandeProduc> historiquecommandesprod; //Cet historique garde en m�moire les commandes que nous avons pass�es aux producteurs.
	
	private StockCacao stockcacao; //Le stock de cacao de Nestle
	private StockChocolats stockchocolat; //le stock des diff�rents chocolats de Nestle.
	private Transformation transformation; //La production de Nestle � chaque Step
	private Tresorerie tresorerie; //La tr�sorerie de Nestle.
	private List<Tresorerie> historiquetresorerie; 
	private Catalogue catalogue; // Le catalogue qui permet de lancer les commandes des distributeurs
	
	private Indicateur iTresorerie;
	private Indicateur iStockcacao;
	//diff�rents getters utiles et setters.
	//permet d'acc�der au catalogue
	public Catalogue getCatalogue() {
		return this.catalogue;
	}
	
	
	
	//un getter pour l'historique de la tr�sorerie
	public List<Tresorerie> getHistoriquetresorerie() {
		return historiquetresorerie;
	}
	 
	//Permet d'acc�der en lecture au num�ro d'�tape
	public int getEtape() {
		return this.etape;
	}
	
	//Permet de passer d'une �tape � une autre
	public void setEtape() {
		this.etape++;
	}
	
	//retourne la liste des clients
	public List<IDistributeur> getClients() {
		return this.clients;
	}
	
	//ajoute un client � la liste
	public void ajouterClient(IDistributeur d) {
		this.clients.add(d);
	}
	
	
	//retourne la liste des fournisseurs
	public List<IProducteur> getFournisseurs() {
		return this.fournisseurs;
	}
	
	//ajoute un fournisseur � la liste
	public void ajouterFournisseurs(IProducteur p) {
		this.fournisseurs.add(p);
	}
	
	
	//Permet d'ajouter une liste de commandes de distributeurs � l'hitorique
	public void ajouterCommandeDistri(List<CommandeDistri> lcd) {
		this.historiquecommandesdistri.add(lcd);
	}
	
	//Acc�de � la liste de commande des distributeurs de l'�tape k.
	public List<CommandeDistri> getCommandeDistri(int k) {
		if (k>=0 && k<=this.getEtape()) {
			System.out.println(this.historiquecommandesdistri);
			return this.historiquecommandesdistri.get(k);
		}
		else {
			return null;
		}
	}
	
	//Permet d'ajouter une liste de commandes de distributeurs � l'hitorique
	public void ajouterCommandeProduc(CommandeProduc cp) {
		this.historiquecommandesprod.add(cp);
	}
	
	//Acc�de � la liste de commande des distributeurs de l'�tape k.
	public CommandeProduc getCommandeProduc(int k) {
		return this.historiquecommandesprod.get(k);
	}
	
	//Acc�de en lecture au stock de cacao.
	public StockCacao getStockcacao() {
		return stockcacao;
	}
	
	//Acc�de en lecture aux stocks de chocolats
	public StockChocolats getStockchocolat() {
		return stockchocolat;
	}

	//Acc�de en lecture � la transformation
	public Transformation getTransformation() {
		return transformation;
	}

	//Acc�de en lecture � la tr�sorerie
	public Tresorerie getTresorerie() {
		return tresorerie;
	}
	
	//permet de construire la catalogue au d�part
	//Permet de construire le catalogue (tous les tarifs sont � z�ro)
		public void Catalogue(double prix50, double prix60, double prix70) {
			this.catalogue = new Catalogue();
			Plage plage1 = new Plage(0., 500., 0);
			Plage plage2 = new Plage(500., 1000., 0.03);
			Plage plage3 = new Plage(1000., 2000., 0.05);
			Plage plage4 = new Plage(2000., 1000000000, 0.07);
			List<Plage> liste = new ArrayList<Plage>();
			liste.add(plage1); liste.add(plage2); liste.add(plage3); liste.add(plage4);
			Tarif tarif1 = new Tarif(prix50, liste);
			Tarif tarif2 = new Tarif(prix60, liste);
			Tarif tarif3 = new Tarif(prix70, liste);
			this.catalogue.add(Constante.PRODUIT_50, tarif1);
			this.catalogue.add(Constante.PRODUIT_60, tarif2);
			this.catalogue.add(Constante.PRODUIT_70, tarif3);
		}
		
		//initialise le catalogue avec les prix � 0
		public void CatalogueInitial() {
			this.Catalogue(8,8,8);
		}
		

	//M�thodes des interfaces
	
	//Interface ITransformateurP
	
	
	//M�thode annexe qui retourne la quantit� totale demand�e par une liste de commandedistributeur
	//On suppose que la liste contient que des commandes concernant PRODUIT_50; PRODUIT_60 et PRODUIT_70
	public static double QuantiteCacaoNecessaire(List<CommandeDistri> l) {
		double quantite = 0;
		for (CommandeDistri cd : l) { //Pour les commandesdistri re�ues � la step pr�c�dentes...
			quantite+=cd.getProduit().getRatioCacao()*cd.getQuantite();
		}
		return quantite;
	}
	
	//Retourne la quantit� de cacao souhait�e par Nestle. Cette m�thode est appel�e par le march�.
	//Tels que nous avons impl�ment� les stocks de cacao, il faut prendre en compte la commande des distributeurs,
	//Calculer la quantit� de cacao necessaire et y ajouter la marge de cacao souhait�e
	public double annonceQuantiteDemandee() {
		int etape = this.getEtape();
		if (etape == 0 || etape == 1) { // Si on a pas encore re�u de commande, si rien ne s'est pass�...
			return 0;
		}
		else { //Si on a re�u des commandesdes distributeurs
			System.out.println("Nestle"+this.getCommandeDistri(this.getEtape()-2));
			double quantitenecessaire = QuantiteCacaoNecessaire(this.getCommandeDistri(etape-1));
			double quantitestockcacao = this.getStockcacao().getStockcacao().get(Constante.CACAO);
			return (quantitenecessaire - quantitestockcacao)*(1+Constante.MARGE_DE_SECURITE)*Constante.DEMANDE_ACTEURS;
		}
	}
	
	// Declenche la mise a jour de la tresorerie de du stock de CACAO
	//et l'historique des commandes
	public void notificationVente(CommandeProduc c) {
		tresorerie.setTresorerieAchat(c);
		this.stockcacao.MiseAJourStockLivraison(Constante.CACAO,c.getQuantite());
		this.historiquecommandesprod.add(c);
		this.tresorerie.setTresorerieAchat(c);
	}

	//Celle ci est d�pr�ci�e. Il est inutile de la remplir
	//Methode inutile
		public double annonceQuantiteDemandee(IProducteur p) {
			return 0;
		}
		
	//M�thode d�pr�ci�e
	//m�thode inutile
		public void notificationVente(IProducteur p) {
		}

		public String getNom() {
			return this.nom;
		}



//
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> list) {
		List<CommandeDistri> l2 = offre(list);
		this.ajouterCommandeDistri(l2);
		return l2;
	}

	
	@Override
	public List<CommandeDistri> livraisonEffective(List<CommandeDistri> list) {
		if(list.size()==0){
			System.out.println("mais qu'est ce qu'ils font chier ces distributeurs a pas envoyer de commandes");
			return list;
		}else{
		List<IDistributeur> liste = Transformation.Priorite(list);
		for (IDistributeur d : liste) {
			this.livraisoneffective(d, list);
		}
		this.historiquecommandesdistri.set(etape-3, list);
		return list;
		}
	}

	public void livraisoneffective(IDistributeur d, List<CommandeDistri> lcd) {
		
		for (CommandeDistri cd : lcd) {
			double reserves = this.getStockchocolat().getStockchocolats().get(cd.getProduit());
			if (reserves<cd.getQuantite()) {
				cd.setQuantite(reserves);
			}
			if (cd.getAcheteur()==d) {
				this.stockchocolat.MiseAJourStockLivraison(cd.getProduit(), cd.getQuantite());
				this.tresorerie.setTresorerieVente(cd);
			}
		}
	}
	@Override
	public List<CommandeDistri> offre(List<CommandeDistri> list) {
		for(int i=0;i<list.size();i++){
			CommandeDistri cd=list.get(i);
			System.out.println(cd.getQuantite());
			if(cd.getProduit().equals(Constante.PRODUIT_50)){
				if(stockchocolat.getStockchocolats().get(Constante.PRODUIT_50)>=0.5*cd.getQuantite()){
				}
				else{
					cd.setVendeur(this);
					cd.setQuantite(cd.getQuantite()/2);
					list.set(i, cd);
				}
			}
			else{
				if(cd.getProduit().equals(Constante.PRODUIT_60)){
					if(stockchocolat.getStockchocolats().get(Constante.PRODUIT_60)>=0.5*cd.getQuantite()){
					}
					else{
						cd.setVendeur(this);
						cd.setQuantite(cd.getQuantite()/2);
						list.set(i, cd);
					}
				}
				else{
					if(cd.getProduit().equals(Constante.PRODUIT_70)){
						if(stockchocolat.getStockchocolats().get(Constante.PRODUIT_70)>=0.5*cd.getQuantite()){
						}
						else{
							cd.setVendeur(this);
							cd.setQuantite(cd.getQuantite()/2);
							list.set(i, cd);
						}
					}
					else{
						System.out.println("Le produit que vous demandez n'est pas disponible");
						}
					}
				}
			}
		return list;
		 }
		

	//M�thode d�pr�ci�e
	//M�thode inutile
	public List<CommandeDistri> Offre(List<CommandeDistri> o) {
		return null;
	}

	//Annonce le prix auquel on propose d'acheter le cacao
	//Pour simplifier, on ach�te eu prix du march� avec de l'al�atoire 
	//renvoie le prix du march� + ou - 10%
	public double annoncePrix() {
		double alea = Math.random()*0.2-0.1;
		return MarcheProd.LE_MARCHE.getCoursCacao().getValeur()*(1+alea);
	}
	
	//m�thode annexe qui ajuste les stock de cacao et la tr�sorerie lors d'une transformation
	public void MiseAJourCacaoChocEtTreso(Transformation t) {
		for (Produit p : t.getTransformation().keySet()) {
			this.stockcacao.MiseAJourStockTransformation(p, t.getTransformation().get(p));
			this.stockchocolat.MiseAJourStockTransformation(p, t.getTransformation().get(p));
		}
		this.tresorerie.CoutTransformation(t);
	}
	
	public void MaJ_HCD() {
		List<CommandeDistri> lcd = new ArrayList<CommandeDistri>();
		MarcheDistributeur marche = MarcheDistributeur.LE_MARCHE_DISTRIBUTEUR;
		System.out.println(marche);
		for (IDistributeur d : this.getClients()) {
			for (CommandeDistri cd : marche.obtenirCommandeFinale(this, d)) {
				lcd.add(cd);
			}
		}
		this.ajouterCommandeDistri(lcd);
		System.out.println(this.historiquecommandesdistri);
	}
	
	
	public void next() {
		this.setEtape();
		//obtention des commandes finales : 
		this.MaJ_HCD();
		this.transformation.setTransformation(this.getCommandeDistri(0),this.getStockcacao(), this.getStockchocolat());
		System.out.println("la transfo a �t� faite");
		this.MiseAJourCacaoChocEtTreso(this.transformation);
		this.iTresorerie.setValeur(this, this.getTresorerie().getFonds());
		this.iStockcacao.setValeur(this, this.getStockcacao().getStockcacao().get(Constante.CACAO));
	}
	
	
	//Constructeur neste
	
	
	public Nestle_new() {
		this.etape = 0;
		this.nom = "Nestle";
		this.clients = new ArrayList<IDistributeur>();
		this.fournisseurs = new ArrayList<IProducteur>();;
		this.historiquecommandesdistri = new ArrayList<List<CommandeDistri>>();
		this.historiquecommandesprod = new ArrayList<CommandeProduc>();
		this.stockcacao = new StockCacao();
		this.stockchocolat = new StockChocolats();
		this.transformation = new Transformation();
		this.tresorerie = new Tresorerie();
		this.historiquetresorerie = new ArrayList<Tresorerie>();
		this.CatalogueInitial();
	}
	
	public void creer(Monde monde) {
		this.iTresorerie = new Indicateur("fonds propres Nestle", this, this.getTresorerie().getFonds());
		Monde.LE_MONDE.ajouterIndicateur(iTresorerie);
		this.iStockcacao = new Indicateur("Stock cacao de Nestle", this, this.getStockcacao().getStockcacao().get(Constante.CACAO));
		Monde.LE_MONDE.ajouterIndicateur(iStockcacao);
	}
	



	//D�but des tests sur la classe Nestl�
	//Il ne faudra tester que les m�thodes de l'interface, les autres �tant �videntes
	public static void main(String[] args) {
		Monde.LE_MONDE = new Monde();
		MarcheProd.LE_MARCHE = new MarcheProd();
		StockChocolats stockchoco=new StockChocolats();
		Nestle_new nestle = new Nestle_new();
		nestle.stockchocolat=stockchoco;
		nestle.creer(Monde.LE_MONDE);
		System.out.println(nestle.annoncePrix());
		System.out.println(nestle.annoncePrix());
		System.out.println(nestle.annoncePrix());
		System.out.println("si les trois fluctuent entre le cours du march� +-10% alors le test est bon");
		
		
		
		//Test de la m�thode offre :
		
		List<CommandeDistri> list= new ArrayList<CommandeDistri>();
		Produit prod=new Produit("Chocolat80",0.8);
		Carrefour c1=new Carrefour();
		Carrefour c2=new Carrefour();
		Carrefour c3=new Carrefour();
		CommandeDistri cd1= new CommandeDistri(c1,Constante.PRODUIT_50,200,3000);
		CommandeDistri cd2= new CommandeDistri(c2,Constante.PRODUIT_50,20,1100);
		CommandeDistri cd3= new CommandeDistri(c3,Constante.PRODUIT_50,500,2050);
		CommandeDistri cd4= new CommandeDistri(c1,Constante.PRODUIT_60,100,1400);
		CommandeDistri cd5= new CommandeDistri(c2,Constante.PRODUIT_70,70,2000);
		CommandeDistri cd6= new CommandeDistri(c3,prod,50,1800);
		list.add(cd1);
		list.add(cd2);
		list.add(cd3);
		list.add(cd4);
		list.add(cd5);
		list.add(cd6);
		stockchoco.MiseAJourStockTransformation(Constante.PRODUIT_50, 30);
		List<CommandeDistri> offre = nestle.offre(list);
		if(offre.size()==6){
			System.out.println("Erreur,on accepte de livrer des produits dont on ne dispose pas");
		}else{
			if(offre.get(0).getQuantite()==200){
				System.out.println("Erreur, on accepte de tout livrer alors que le stock de chocolat n'est pas suffisant");
			}else{
				if(offre.get(1).getQuantite()==10){
					System.out.println("Erreur, la commande des ditributeurs est tout le temps divis�e par deux");
				}else{
					if(offre.get(3).getProduit()!=Constante.PRODUIT_60){
						System.out.println("On ajoute pas le bon produit dans le commande, probl�me ajout PRODUIT_60");
					}else{
						if(offre.get(0).getProduit()!=Constante.PRODUIT_50){
							System.out.println("On ajoute pas le bon produit dans la commande, probl�me ajout PRODUIT_50 ");
						}else{
							if(offre.get(4).getProduit()!=Constante.PRODUIT_70){
								System.out.println("On ajoute pas le bon produit dans la commande, probl�me ajout PRODUIT_70");
							}else{
								System.out.println("Il semble que la m�thode fonctionne");
							}
						}
					}
				}
			}
		}
	}
	
// Test avec cr�ation de distributeurs, qui ach�tent lin�airement
	public List<CommandeDistri> demande (){
		Carrefour c1=new Carrefour();
		Carrefour c2=new Carrefour();
		Carrefour c3=new Carrefour();
		CommandeDistri cd3= new CommandeDistri(c3,Constante.PRODUIT_50,500,2050);
		CommandeDistri cd4= new CommandeDistri(c1,Constante.PRODUIT_60,100,1400);
		CommandeDistri cd5= new CommandeDistri(c2,Constante.PRODUIT_70,70,2000);
		List<CommandeDistri> demande=new ArrayList<CommandeDistri>();
		demande.add(cd3);
		demande.add(cd4);
		demande.add(cd5);
		return demande;
	}
	
	public List<CommandeDistri> contreDemande (){
		Carrefour c1=new Carrefour();
		Carrefour c2=new Carrefour();
		Carrefour c3=new Carrefour();
		CommandeDistri cd3= new CommandeDistri(c3,Constante.PRODUIT_50,500,2050);
		CommandeDistri cd4= new CommandeDistri(c1,Constante.PRODUIT_60,100,1400);
		CommandeDistri cd5= new CommandeDistri(c2,Constante.PRODUIT_70,70,2000);
		List<CommandeDistri> demande=new ArrayList<CommandeDistri>();
		demande.add(cd3);
		demande.add(cd4);
		demande.add(cd5);
		return demande;
	}
	
	
}
