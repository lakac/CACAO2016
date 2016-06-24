package abstraction.equipe2;
import abstraction.commun.*;

//import java.util.HashMap;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.CommandeProduc;
//import abstraction.commun.*;
import abstraction.commun.IProducteur;
import abstraction.fourni.Acteur;

public class Nestle_new implements Acteur, ITransformateurP, ITransformateurD {
	
	private int etape; //indique l'étape à laquelle on se trouve.
	
	private String nom; //le nom de l'acteur
	
	private List<IDistributeur> clients; // Liste des clients
	private List<IProducteur> fournisseurs; //Liste des fournisseurs
	
	private List<List<CommandeDistri>> historiquecommandesdistri; // Cet historique garde en mémoire les commandes que les distributeurs nous passent.
	private List<CommandeProduc> historiquecommandesprod; //Cet historique garde en mémoire les commandes que nous avons passées aux producteurs.
	
	private Catalogue catalogueinterne;
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
	
	
	/*public double annonceQuantiteDemandee() {
		double resultat = 0.0;
		for (IDistributeur d : this.getCommandesdistri().keySet()) {
		 	for (CommandeDistri c : this.getCommandesdistri().get(d)) {
				resultat+=c.getQuantite()*c.getProduit().getRatioCacao()
						*(Constante.ACHAT_SANS_PERTE+(Constante.PERTE_MINIMALE + Math.random()*(Constante.VARIATION_PERTE)))
						*Constante.DEMANDE_ACTEURS;
			}
		}
		return resultat;
	}*/
	
	//Méthode annexe qui retourne la quantité totale demandée par une liste de commandedistributeur
	//On suppose que la liste contient que des commandes concernant PRODUIT_50; PRODUIT_60 et PRODUIT_70
	public static double QuantiteCacaoNecessaire(List<CommandeDistri> l) {
		double quantite = 0;
		for (CommandeDistri cd : l) { //Pour les commandesdistri reçues à la step précédentes...
			quantite+=cd.getProduit().getRatioCacao()*cd.getQuantite();
		}
		return quantite;
	}
	
	//Retourne la quantité de cacao souhaitée par Nestle. Cette méthode est appelée par le marché.
	//Tels que nous avons implémenté les stocks de cacao, il faut prendre en compte la commande des distributeurs,
	//Calculer la quantité de cacao necessaire et y ajouter la marge de cacao souhaitée
	public double annonceQuantiteDemandee() {
		int etape = this.getEtape();
		if (etape == 0) { // Si on a pas encore reçu de commande, si rien ne s'est passé...
			return 0;
		}
		else { //Si on a reçu des commandesdes distributeurs
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
	}

	//Celle ci est dépréciée. Il est inutile de la remplir
	//Methode inutile
		public double annonceQuantiteDemandee(IProducteur p) {
			return 0;
		}
		
	//Méthode dépréciée
	//méthode inutile
		public void notificationVente(IProducteur p) {
		}

		public String getNom() {
			return this.nom;
		}

	

	@Override
	public Catalogue getCatalogue() {
		return this.catalogueinterne;
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
	public List<CommandeDistri> offre(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
	}

	//Méthode dépréciée
	//Méthode inutile
	public List<CommandeDistri> Offre(List<CommandeDistri> o) {
		return null;
	}

	//Annonce le prix auquel on propose d'acheter le cacao
	//Pour simplifier, on achète eu prix du marché avec de l'aléatoire 
	//renvoie le prix du marché + ou - 10%
	public double annoncePrix() {
		double alea = Math.random()*0.2-0.1;
		return MarcheProducteur.LE_MARCHE.getCours()*alea;
	}
	
	public void next() {
		// TODO Auto-generated method stub
	}
	
	
	//Début des tests sur la classe Nestlé
	//Il ne faudra tester que les méthodes de l'interface, les autres étant évidentes
	/*public static void main(String[] args) {
		Nestle_new nestle = new Nestle_new();
		System.out.println(nestle.annoncePrix());
		System.out.println(nestle.annoncePrix());
		System.out.println(nestle.annoncePrix());
		System.out.println("si les trois fluctuent entre le cours du marché +-10% alors le test est bon");
	}*/	
}
