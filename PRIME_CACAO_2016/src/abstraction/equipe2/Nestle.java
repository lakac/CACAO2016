package abstraction.equipe2;

import abstraction.fourni.*;
import abstraction.commun.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Nestle implements Acteur, ITransformateur{
	
	private String nom;
	
	private Indicateur totalachats;
	private HashMap<Produit,Indicateur> totalventesproduit;
	
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
	
	
	public Nestle() {
		//le nom
			this.nom = Constantes.NOM_TRANSFORMATEUR_1;
		//Les listes des clients et fournisseurs
				this.clients = new ArrayList<IDistributeur>();
				this.fournisseurs = new ArrayList<IProducteur>();
		//les HashMaps et liste
				this.achats = new HashMap<IProducteur, Achat>();
				this.SetAchats(this.fournisseurs);
				this.ventes = new HashMap<IDistributeur, Vente>();
				this.SetVentes(clients);
				this.commandesdistri = new HashMap<IDistributeur, List<CommandeDistri>>();
				this.SetCommandesDistriInit(this.clients);
				this.commandeproduc = new ArrayList<CommandeProduc>();
				this.SetCommandesProduc(fournisseurs);
			//	this.SetTotalVentesProduit();
		
		//Stock et les informations de transport et production
			/*	this.stockcacao = new StockCacao();
				this.stockchocolats = new StockChocolats();
				this.couttransport = new CoutTransport(Constante.COUT_UNITAIRE_TRANSPORT);
				this.production = new Production();
		//Ajout d'indicateurs visibles
				Monde.LE_MONDE.ajouterIndicateur(this.banque.getTresorerie());
				Monde.LE_MONDE.ajouterIndicateur(this.totalachats);
				for (Produit p : this.totalventesproduit.keySet()) {
					Monde.LE_MONDE.ajouterIndicateur( this.totalventesproduit.get(p));
				}*/
		}
	public void creer() {
		this.SetTotalVentesProduit();
		//Stock et les informations de transport et production
		this.stockcacao = new StockCacao();
		this.stockchocolats = new StockChocolats();
		this.couttransport = new CoutTransport(Constante.COUT_UNITAIRE_TRANSPORT);
		for (IProducteur p : this.fournisseurs) {
			this.couttransport.addDistance(p, 5000.);
		}
		this.production = new Production();
//Ajout d'indicateurs visibles
		this.banque=new Banque(this);
		System.out.println("treso "+this.banque.getTresorerie());
		Monde.LE_MONDE.ajouterIndicateur(this.banque.getTresorerie());
		this.totalachats = new Indicateur("totalachats", this, 0.);
		System.out.println("total "+this.totalachats);
		Monde.LE_MONDE.ajouterIndicateur(this.totalachats);
		
		for (Produit p : this.totalventesproduit.keySet()) {
			Indicateur indicateurp =new Indicateur(""+p, this, 0.);
			Monde.LE_MONDE.ajouterIndicateur( indicateurp);
		}
		this.catalogue = new CatalogueInterne();

	}
	//Ajout de clients et de fournisseurs
	//@copyright �quipe 3
	public void AjouterClient(IDistributeur d) {
		this.clients.add(d);
	}
	public void AjouterFournisseur(IProducteur p) {
		this.fournisseurs.add(p);
	}
	
	
//Setters par defauts (utilises dans le constructeurs)	
	//Setter du dictionnaire des achats
	public void SetAchats(List<IProducteur> producteurs) {
		for (IProducteur p : producteurs) {
			Achat achat = new Achat(this);
			this.achats.put(p, achat);
		}
	}
	//Setter du dictionnaire des ventes
	public void SetVentes(List<IDistributeur> distributeur) {
		for (IDistributeur d : distributeur) {
			Vente vente = new Vente(this);
			this.ventes.put(d, vente);
		}
	}
	//Setter initial du dictionnaire des commandes
	public void SetCommandesDistriInit(List<IDistributeur> distributeur) {
		for (IDistributeur d : distributeur) {
			this.setCommandesdistri(d, null);
		}
	}
	//setter utilis� par la suite
	public void setCommandesdistri(IDistributeur d, List<CommandeDistri> commandesdistri) {
		this.commandesdistri.put(d, commandesdistri);
	}

	//Setter de la liste des commandes aux producteurs
	public void SetCommandesProduc(List<IProducteur> producteurs) {
		ArrayList<CommandeProduc> listecommandes = new ArrayList<CommandeProduc>();
		for (IProducteur p : producteurs) {
			CommandeProduc commande = new CommandeProduc(this, p, 0., 0.);
			listecommandes.add(commande);
		}
		this.commandeproduc = listecommandes;
	}
	
	public void setCommandeproduc(int i, CommandeProduc commandeproduc) {
		this.commandeproduc.add(i, commandeproduc);
	}
	//Setter initial pour l'historique des ventes
	public void SetTotalVentesProduit() {
		this.totalventesproduit = new HashMap<Produit, Indicateur>();
		Indicateur indicateur50 = new Indicateur(this.nom, this, 0.);
		this.totalventesproduit.put(Constante.PRODUIT_50, indicateur50);
		Indicateur indicateur60 = new Indicateur(this.nom, this, 0.);
		this.totalventesproduit.put(Constante.PRODUIT_60, indicateur60);
		Indicateur indicateur70 = new Indicateur(this.nom, this, 0.);
		this.totalventesproduit.put(Constante.PRODUIT_70, indicateur70);
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

	

//getters des clients et fournisseurs
	public ArrayList<IDistributeur> getClients() {
		return clients;
	}

	public ArrayList<IProducteur> getFournisseurs() {
		return fournisseurs;
	}
//M�thodes de l'interface
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
		for (CommandeDistri C : o) {
			if (this.getStockchoc().getStockschocolats().get(C.getProduit())
					<C.getQuantite()/2) {
				Offre.add(C);
			}
			else {
				C.setQuantite(C.getQuantite()/2+
						this.getStockchoc().getStockschocolats().get(C.getProduit()));
				Offre.add(C);
			}
		}
		return Offre;
	}


	@Override

	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> cf) {
		return Offre(cf);
	}

	
// m�thodes vou�es � dispara�tre
	@Override
	public double annonceQuantiteDemandee(IProducteur p) {
		return 0;
	}

	@Override
	public void notificationVente(IProducteur p){
	}

	public static void main(String[] args) {
		Nestle nestle = new Nestle();
		Monde.LE_MONDE.getIndicateurs();
	}
	
	public void next() {
		//initialisation des plages de prix compte tenu des production pr�c�dentes
		PlageInterne plageinterne = this.getProd().plageinterne();
		//Catalogue
		this.catalogue.setCatalogueinterne(plageinterne);
		
		//d�but de la phase d'�change � proprement dit.
		//On donne le catalogue.
		this.getCatalogue();
		/*HashMap<ITransformateur, Catalogue>  dictionnaire = new HashMap<ITransformateur, Catalogue>();
		dictionnaire.put(this, this.getCatalogue());
		//On n�gocie avec les distributeurs.
		for (IDistributeur d : this.getClients()) {
			this.setCommandesdistri(d,d.Demande(dictionnaire));
			this.Offre(d.Demande(dictionnaire));
		}
		for (IDistributeur d : this.getClients()) {
			this.setCommandesdistri(d, d.ContreDemande(this.getCommandesdistri().get(d)));
			this.CommandeFinale(this.getCommandesdistri().get(d));
		}
		for (IDistributeur d : this.getClients()) {
			this.setCommandesdistri(d, d.CommandeFinale(this.getCommandesdistri().get(d)));
		}
		//On n�gocie avec les Producteurs et on actualise nos commande aux producteurs
		 */
		this.annonceQuantiteDemandee();
		double prix = this.annoncePrix();
		for (int i = 0; i<this.getCommandeproduc().size(); i++) {
			CommandeProduc commande = new CommandeProduc(this, this.getFournisseurs().get(i), 
					this.getFournisseurs().get(i).annonceQuantiteMiseEnVente(this), prix);
			this.setCommandeproduc(i, commande);
		}
		//chacun des producteurs nous envoie leur offre et on ach�te leur cacao
		//et on met � jour l'historique
		//et la tr�sorerie (on ach�te quelque chose)
		double achattotal = 0.;
		for (IProducteur p : this.achats.keySet()) {
			this.achats.get(p).setCacaoAchete(this, p);
			achattotal+=this.getAchats().get(p).getCacaoachete();
			this.banque.retirer(this.achats.get(p).getCacaoachete());
		}
		this.totalachats.setValeur(this, achattotal);
		
		//Le cacao est alors livr�, on met a jour le stock de cacao.
		//et la tr�sorerie (cout de transport � notre charge)
		for (IProducteur p : this.achats.keySet()) {
			this.stockcacao.AjouterStockCacao(this.achats.get(p));
			this.banque.retirer(this.getCouttransport().getDistances().get(p)*
					Constante.COUT_UNITAIRE_TRANSPORT*this.getAchats().get(p).getCacaoachete());
		}
		
		//Le stock de cacao est � jour, on lance la production de chocolat, et on met
		//a jour le stock de cacao et de chocolat au fur et a mesure
		//et on retranche les couts de production � la banque
		//on calcule le cout de stock � ce moment l�
		for (IDistributeur d : this.commandesdistri.keySet()) {
			for (CommandeDistri cd : this.commandesdistri.get(d)) {
				this.production.setProduction(this, cd);
				this.stockcacao.RetirerStockCacao(cd.getProduit(), this.production);
				this.stockchocolats.AjouterStockProduit(cd.getProduit(), this.production);
				this.banque.retirer(this.production.CoutTransformation(cd.getProduit()));
				this.banque.retirer(this.getStockcac().CoutStockCacao());
				this.banque.retirer(this.getStockchoc().CoutStockChocolat());
			}
		}
		//La production �tant faite, on peut alors mettre a jour les ventes 
		//(car c'est la production de la vente finale qui a �t� faite)
		//ainsi que leur historique
		//ainsi que la tr�sorerie de Nestle
		for (IDistributeur d : this.ventes.keySet()) {
			int i = 0;
			for (Produit p : this.ventes.get(d).getQuantitevendue().keySet()) {
				this.ventes.get(d).setquantitevendue(this, this.commandesdistri.get(d).get(i), p);
				this.ventes.get(d).MiseAJourHistorique(this, Monde.LE_MONDE.getStep(), p);
				this.totalventesproduit.get(p).setValeur(this, this.ventes.get(d).Quantitevendue(p));
				i++;
				this.banque.ajouter(this.getVentes().get(d).Prixdevente(plageinterne.getTarifproduit().get(p)
						,this.getVentes().get(d).getQuantitevendue().get(p)));
			}
		}
		//Enfin, mise � jour de l'historique de la banque
		this.banque.MiseAJourHistorique(this, Monde.LE_MONDE.getStep());
		//fin du next
	}
	@Override
	public List<CommandeDistri> livraisonEffective(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
	}
}




