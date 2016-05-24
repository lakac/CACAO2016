package abstraction.equipe2;

import abstraction.fourni.*;
import abstraction.commun.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Nestle implements Acteur, ITransformateur{
	
	private String nom;
	private Indicateur historiqueachats;
	private Indicateur historiqueventes;
	private Indicateur balance;

	private HashMap<IDistributeur,List<CommandeDistri>> commandesdistri;
	private List<CommandeProduc> commandeproduc;
	
	private CatalogueInterne catalogue;
	
	private HashMap<IProducteur, Achat> achats;
	private HashMap<IDistributeur, Vente> ventes;
	private StockCacao stockcacao;
	private StockChocolats stockchocolats;
	private Production production;
	private Banque banque;
	
	public Nestle(Monde monde) {
		this.nom = Constantes.NOM_TRANSFORMATEUR_1;
		this.historiqueachats = new Indicateur("Achats de "+this.nom, this, 0.0);
		this.historiqueventes = new Indicateur("Ventes de "+this.nom, this, 0.0);
		this.balance = new Indicateur("Solde de "+this.nom, this, 10000000.0);
		Monde.LE_MONDE.ajouterIndicateur( this.historiqueachats );
		Monde.LE_MONDE.ajouterIndicateur( this.historiqueventes );
		Monde.LE_MONDE.ajouterIndicateur( this.balance );
		this.banque =new Banque();
		this.stockcacao = new StockCacao ();
		this.stockchocolats = new StockChocolats ();
	}

	public StockCacao getStockcac() {
		return stockcacao;
	}

	public StockChocolats getStockchoc() {
		return stockchocolats;
	}
	
	public Production getProd() {
		return production;
	}

	public String getNom() {
		return "Producteur "+this.nom;
	}
	

	public HashMap<IDistributeur, List<CommandeDistri>> getCommandesdistri() {
		return commandesdistri;
	}

	public List<CommandeProduc> getCommandeproduc() {
		return commandeproduc;
	}
	

	public HashMap<IProducteur, Achat> getAchats() {
		return achats;
	}

	public HashMap<IDistributeur, Vente> getVentes() {
		return ventes;
	}

	public Banque getBanque() {
		return banque;
	}
	
	public void setAchats(IProducteur p, Achat achat) {
		this.achats.put(p, achat);
	}

	public void next() {
		// Initialisation
		List<Plage> listplageproduit = new ArrayList<Plage>();
		Plage plage1= new Plage(0,200,0);
		Plage plage2= new Plage(200.1,500,0.03);
		Plage plage3= new Plage(500.1,1000,0.07);
		Plage plage4= new Plage(1000.1,0.10);
		listplageproduit.add(plage1);
		listplageproduit.add(plage2);
		listplageproduit.add(plage3);
		listplageproduit.add(plage4);
		this.production = new Production();
		PlageInterne plageinterne = new PlageInterne();
		plageinterne.setTarifproduit(Constante.PRODUIT_50,listplageproduit);
		plageinterne.setTarifproduit(Constante.PRODUIT_60,listplageproduit);
		plageinterne.setTarifproduit(Constante.PRODUIT_70,listplageproduit);
		Tarif tarifproduit1 = new Tarif(this.getProd().PrixdeventeDeBase(Constante.PRODUIT_50),listplageproduit);
		Tarif tarifproduit2 = new Tarif(this.getProd().PrixdeventeDeBase(Constante.PRODUIT_60),listplageproduit);
		Tarif tarifproduit3 = new Tarif(this.getProd().PrixdeventeDeBase(Constante.PRODUIT_70),listplageproduit);
		CatalogueInterne catalogueinterne = new CatalogueInterne();
		catalogueinterne.setCatalogueinterne(tarifproduit1, tarifproduit2, tarifproduit3);
		
		
		// A faire a chaque step
		
		
		

	}

	public double annonceQuantiteDemandee() {
		double resultat = 0.0;
		for (IDistributeur d : this.getCommandesdistri().keySet()) {
			for (CommandeDistri c : this.getCommandesdistri().get(d)) {
				resultat+=c.getQuantite()*c.getProduit().getRatioCacao();
			}
		}
		return resultat;
	}

	public double annoncePrix() {
			return MarcheProducteur.LE_MARCHE.getCours()*(1+0.1*Math.random());
		}

	public void notificationVente(CommandeProduc c) {
		Achat achat = new Achat(c.getQuantite());
		this.setAchats(c.getVendeur(), achat);
	}

	@Override
	public Catalogue getCatalogue() {
		return this.catalogue.getCatalogueinterne();
	}

	@Override
	public List<CommandeDistri> Offre(List<CommandeDistri> o) {
		ArrayList<CommandeDistri> Offre = new ArrayList<CommandeDistri>();
		for (int i=0; i<=o.size(); i++) {
			CommandeDistri C = o.get(i);
			if (this.getStockchoc().getStockschocolats().get(o.get(i).getProduit())
					>=o.get(i).getQuantite()/2) {
				o.add(i, o.get(i));
			}
			else {
				o.get(i).setQuantite(o.get(i).getQuantite()/2+
						this.getStockchoc().getStockschocolats().get(C.getProduit()));
				Offre.add(i,C);
			}
		}
		return Offre;
	}

	@Override
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> cf) {
		return Offre(cf);
	}

	@Override
	public double annonceQuantiteDemandee(IProducteur p) {
		return 0;
	}

	@Override
	public void notificationVente(IProducteur p){
	}	
}


