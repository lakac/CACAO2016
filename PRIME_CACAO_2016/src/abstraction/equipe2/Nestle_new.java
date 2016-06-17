package abstraction.equipe2;

import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.CommandeProduc;
import abstraction.commun.*;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;

public class Nestle_new implements Acteur, ITransformateur {
	
	private int etape; //indique l'étape à laquelle on se trouve.
	
	private List<IDistributeur> clients; // Liste des clients
	private List<IProducteur> fournisseurs; //Liste des fournisseurs
	
	private List<List<CommandeDistri>> historiquecommandesdistri; // Cet historique garde en mémoire les commandes que les distributeurs nous passent.
	private List<CommandeProduc> historiquecommandesprod; //Cet historique garde en mémoire les commandes que nous avons passées aux producteurs.
	
	private StockCacao stockcacao; //Le stock de cacao de Nestle
	private StockChocolats stockchocolat; //le stock des différents chocolats de Nestle.
	private Transformation transformation; //La production de Nestle à chaque Step
	private Tresorerie tresorerie; //La trésorerie de Nestle.
	
	//différents getters utiles et setters.
	
	//Permet d'accéder en lecture au numéro d'étape
	public int getEtape() {
		return this.etape;
	}
	
	//Permet de passer d'une étape à une autre
	public void setEtape() {
		this.etape++;
	}
	
	//retourne la liste des clients
	public List<IDistributeur> getClients() {
		return this.clients;
	}
	
	//ajoute un client à la liste
	public void ajouterClient(IDistributeur d) {
		this.clients.add(d);
	}
	
	
	//retourne la liste des fournisseurs
	public List<IProducteur> getFournisseurs() {
		return this.fournisseurs;
	}
	
	//ajoute un fournisseur à la liste
	public void ajouterFournisseurs(IProducteur p) {
		this.fournisseurs.add(p);
	}
	
	
	//Permet d'ajouter une liste de commandes de distributeurs à l'hitorique
	public void ajouterCommandeDistri(List<CommandeDistri> lcd) {
		this.historiquecommandesdistri.add(lcd);
	}
	
	//Accède à la liste de commande des distributeurs de l'étape k.
	public List<CommandeDistri> getCommandeDistri(int k) {
		return this.historiquecommandesdistri.get(k);
	}
	
	//Permet d'ajouter une liste de commandes de distributeurs à l'hitorique
	public void ajouterCommandeProduc(CommandeProduc cp) {
		this.historiquecommandesprod.add(cp);
	}
	
	//Accède à la liste de commande des distributeurs de l'étape k.
	public void getCommandeProduc(int k) {
		this.historiquecommandesprod.get(k);
	}
	
	//Accède en lecture au stock de cacao.
	public StockCacao getStockcacao() {
		return stockcacao;
	}
	
	//Accède en lecture aux stocks de chocolats
	public StockChocolats getStockchocolat() {
		return stockchocolat;
	}

	//Accède en lecture à la transformation
	public Transformation getTransformation() {
		return transformation;
	}

	//Accède en lecture à la trésorerie
	public Tresorerie getTresorerie() {
		return tresorerie;
	}

	
	//Méthodes des interfaces
	
	//Interface ITransformateurP
	
	//Méthode annexe qui retourne la quantité totale demandée par une liste de commandedistributeur
	//On suppose que la liste contient que des cmmandes concernant PRODUIT_50; PRODUIT_60 et PRODUIT_70
	public static double QuantiteCacaoNecessaire(List<CommandeDistri> l) {
		double quantite = 0;
		for (CommandeDistri cd : l) { //Pour les commandesdistri reçues à la step précédentes...
			if (cd.getProduit() == Constante.PRODUIT_50) { //Pour du chocolat à 50%
				quantite+=cd.getQuantite()*0.5;
			}
			else if (cd.getProduit() == Constante.PRODUIT_60) {
				quantite+=cd.getQuantite()*0.6;
			}
			else  {
				quantite+=cd.getQuantite()*0.7;
			}
		}
		return quantite;
	}
	
	//Retourne la quantité de cacao souhaitée par Nestle. Cette méthode est appelée par le marché.
	//Tels que nous avons implémenté les stocks de cacao, il faut prendre en compte la commande des distributeurs,
	//Calculer la quantité de cacao necessaire et y ajouter la marge de cacao souhaitée
	@Override
	public double annonceQuantiteDemandee() {
		int etape = this.getEtape();
		if (etape == 0) { // Si on a pas encore reçu de commande, si rien ne s'est passé...
			return 0;
		}
		else { //Si on a reçu des commandesdes distributeurs
			double quantitenecessaire = QuantiteCacaoNecessaire(this.getCommandeDistri(etape-1));
			double quantitestockcacao = this.getStockcacao().getStockcacao().get(Constante.CACAO);
			return (quantitenecessaire - quantitestockcacao)*(1+Constante.MARGE_DE_SECURITE);
		}
	}

	//Celle ci est dépréciée. Il est inutile de la remplir
	@Override
	public double annonceQuantiteDemandee(IProducteur p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double annoncePrix() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Catalogue getCatalogue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notificationVente(CommandeProduc c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificationVente(IProducteur p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandeDistri> livraisonEffective(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandeDistri> Offre(List<CommandeDistri> o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandeDistri> offre(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}
	

}
