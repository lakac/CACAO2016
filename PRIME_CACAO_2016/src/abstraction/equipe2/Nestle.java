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
	private CoutTransport couttransport;
	
	private ArrayList<IDistributeur> clients;
	private ArrayList<IProducteur> fournisseurs;
	
	
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
	
	public CoutTransport getCouttransport() {
		return couttransport;
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
		//initialisation des plages de prix compte tenu des production précédentes
		PlageInterne plageinterne = this.getProd().plageinterne();
		
		//Catalogue
		CatalogueInterne catalogueinterne = new CatalogueInterne();
		catalogueinterne.setCatalogueinterne(plageinterne);
		
		//début de la phase d'échange à proprement dit.
		//On donne le catalogue.
		this.getCatalogue();
		//On négocie avec les distributeurs.
		for (IDistributeur d : this.getClients()) {
			this.setCommandesdistri(d,d.Demande(null));
			this.Offre(d.Demande(null));// null a changer quand l'équipe aura fait une pul request.
		}
		for (IDistributeur d : this.getClients()) {
			this.setCommandesdistri(d, d.ContreDemande(this.getCommandesdistri().get(d)));
			this.CommandeFinale(this.getCommandesdistri().get(d));
		}
		for (IDistributeur d : this.getClients()) {
			this.setCommandesdistri(d, d.CommandeFinale(this.getCommandesdistri().get(d)));
		}
		//On négocie avec les Producteurs et on actualise nos commande aux producteurs
		this.annonceQuantiteDemandee();
		double prix = this.annoncePrix();
		for (int i = 0; i<this.getCommandeproduc().size(); i++) {
			CommandeProduc commande = new CommandeProduc(this, this.getFournisseurs().get(i), 
					this.getFournisseurs().get(i).annonceQuantiteMiseEnVente(this), prix);
			this.setCommandeproduc(i, commande);
		}
		//chacun des producteurs nous envoie leur offre et on achète leur cacao
		//et on met à jour l'historique
		//et la trésorerie (on achète quelque chose)
		for (IProducteur p : this.achats.keySet()) {
			this.achats.get(p).setCacaoAchete(this, p);
			this.achats.get(p).MiseAJourHistorique(this, Monde.LE_MONDE.getStep());
			this.banque.retirer(this.achats.get(p).getCacaoachete());
		}
		//Le cacao est alors livré, on met a jour le stock de cacao.
		//et la trésorerie (cout de transport à notre charge)
		for (IProducteur p : this.achats.keySet()) {
			this.stockcacao.AjouterStockCacao(this.achats.get(p));
			this.banque.retirer(this.getCouttransport().getDistances().get(p)*this.getCouttransport().getCouttransport());
		}
		
		//Le stock de cacao est à jour, on lance la production de chocolat, et on met
		//a jour le stock de cacao et de chocolat au fur et a mesure
		//et on retranche les couts de production à la banque
		for (IDistributeur d : this.commandesdistri.keySet()) {
			for (CommandeDistri cd : this.commandesdistri.get(d)) {
				this.production.setProduction(this, cd);
				this.stockcacao.RetirerStockCacao(cd.getProduit(), this.production);
				this.stockchocolats.AjouterStockProduit(cd.getProduit(), this.production);
				this.banque.retirer(this.production.CoutTransformation(cd.getProduit()));
			}
		}
		//La production étant faite, on peut alors mettre a jour les ventes 
		//(car c'est la production de la vente finale qui a été faite)
		//ainsi que leur historique
		//ainsi que la trésorerie de Nestle
		for (IDistributeur d : this.ventes.keySet()) {
			int i = 0;
			for (Produit p : this.ventes.get(d).getQuantitevendue().keySet()) {
				this.ventes.get(d).setquantitevendue(this, this.commandesdistri.get(d).get(i), p);
				this.ventes.get(d).MiseAJourHistorique(this, Monde.LE_MONDE.getStep(), p);
				i++;
				this.banque.ajouter(this.getVentes().get(d).Prixdevente(plageinterne.getTarifproduit().get(p)
						,this.getVentes().get(d).getQuantitevendue().get(p)));
			}
		}
		//fin du next
	}

	
	public void setCommandeproduc( int i, CommandeProduc commandeproduc) {
		this.commandeproduc.add(i, commandeproduc);
	}

	public void setCommandesdistri(IDistributeur d, List<CommandeDistri> commandesdistri) {
		this.commandesdistri.put(d, commandesdistri);
	}

	public ArrayList<IDistributeur> getClients() {
		return clients;
	}

	public ArrayList<IProducteur> getFournisseurs() {
		return fournisseurs;
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


