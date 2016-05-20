package abstraction.equipe2;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;
import abstraction.fourni.v0.Marche;
import abstraction.commun.*;

import java.util.ArrayList;
import java.util.List;

public class Nestle implements Acteur, ITransformateur{
	
	private String nom;
	private Indicateur achats;
	private Indicateur ventes;
	private Indicateur solde;
	
	private StockCacao cacao;
	private StockChocolats chocolats;
	
	public Nestle(Monde monde) {
		this.nom = Constantes.NOM_TRANSFORMATEUR_1;
		this.achats = new Indicateur("Achats de "+this.nom, this, 0.0);
		this.ventes = new Indicateur("Ventes de "+this.nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+this.nom, this, 10000000.0);
		Monde.LE_MONDE.ajouterIndicateur( this.achats );
		Monde.LE_MONDE.ajouterIndicateur( this.ventes );
		Monde.LE_MONDE.ajouterIndicateur( this.solde );		
	}
	
	
	
	

	public StockCacao getStockcac() {
		return cacao;
	}





	public StockChocolats getStockchoc() {
		return chocolats;
	}





	public String getNom() {
		return "Producteur "+this.nom;
	}
	
	public static double prixDeVente(){
		return 15.0;
	}
	
	
	//ce code calcule le cout de revient et le cout de revient unitaire de Nestlé France !
	//p en euros, q en kilos
	public static double[] CoutInts (double p, double []T){ 
		double[] CI =new double[2] ;
		CI[0] = 13003370+T[1]*(5+p);
		CI[1] = CI[0]*0.6/T[1];
		// 600g de cacao équivalent à 1kg de chocolat
		return CI;
	}
	
	private List<IProducteur> getProducteurs() {
		List<IProducteur> prod = new ArrayList<IProducteur>();
		for (Acteur a : Monde.LE_MONDE.getActeurs()) {
			if (a instanceof IProducteur) {
				prod.add((IProducteur)(a));
			}
		}
		return prod;
	}

	
	//la quantité demandée aux producteurs est proportionnelle 

	
	// Quantité annoncée aux producteurs 
	
	public double annonceQuantiteDemandee(IProducteur p) {
		return 0.0;
	}
		/*if(MondeV1.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1)==p){
			//return Math.min(commandes.quantiteDemandee(0.3), p.annonceQuantiteMiseEnVente(this)) ;
		}
		else if (MondeV1.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_2)==p){
				//return Math.min(commandes.quantiteDemandee(0.3), p.annonceQuantiteMiseEnVente(this)) ;
			}
			else{
				return 0.0;
			}
		}*/
	
	public List<IDistributeur> getDistributeurs() {
		List<IDistributeur> distributeurs = new ArrayList<IDistributeur>();
		for (Acteur a : Monde.LE_MONDE.getActeurs()) {
			if (a instanceof IDistributeur) {
				distributeurs.add((IDistributeur)(a));
			}
		}
		return distributeurs;
	}

	
	public void notificationVente(IProducteur p) {
		double commande = this.annonceQuantiteDemandee(p);
		this.solde.setValeur(this, this.solde.getValeur()-p.annoncePrix()*commande);
		//stock_cacao.ajoutCacao();
	}

	public void next() {
		StockCacao stockcacao= new StockCacao();
		StockChocolats stockchocolat = new StockChocolats();
		Banque banque=new Banque();
		List<Plage> listplageproduit = new ArrayList<Plage>();
		Plage plage1= new Plage(0,200,0);
		Plage plage2= new Plage(200.1,500,0.03);
		Plage plage3= new Plage(500.1,1000,0.07);
		Plage plage4= new Plage(1000.1,0.10);
		listplageproduit.add(plage1);
		listplageproduit.add(plage2);
		listplageproduit.add(plage3);
		listplageproduit.add(plage4);
		PlageInterne plageinterne = new PlageInterne();
		plageinterne.setTarifproduit(Constante.PRODUIT_50,listplageproduit);
		plageinterne.setTarifproduit(Constante.PRODUIT_60,listplageproduit);
		plageinterne.setTarifproduit(Constante.PRODUIT_70,listplageproduit);
		
	}

	public double annonceQuantiteDemandee() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double annoncePrix() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void notificationVente(CommandeProduc c) {
		// TODO Auto-generated method stub
		
	}
	
	/*double qdd = 0;
		for (IDistributeur d : this.getDistributeurs()) {
		qdd += d.getDemande(this);
		}
		commandes.setCommandes(qdd);
		for (IProducteur p : this.getProducteurs()) {	
		commandes.quantiteDemandee (0.3);
		notificationVente(p);
		}
		commandes.quantiteDemandeeMonde(0.4);
		stock_chocolat.ajoutChocolat();
		//tresorerie.setTresorerie(tresorerie.Tresorerie(this.getProducteurs().get(0), this.getProducteurs().get(1)));
		
		this.achats.setValeur(this, commandes.getCommandes()[2]);
		this.solde.setValeur(this, tresorerie.getTresorerie());
		this.ventes.setValeur(this, commandes.getCommandes()[0]);
	}		
	
}*/
}


