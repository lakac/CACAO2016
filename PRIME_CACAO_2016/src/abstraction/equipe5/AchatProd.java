package abstraction.equipe5;

import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.Constantes;
import abstraction.commun.IDistributeur;
import abstraction.commun.IProducteur;
import abstraction.commun.MarcheProducteur;
import abstraction.commun.MondeV1;

class CommandeInterne {
	private double quantite, prix;
	
	public CommandeInterne(double q, double p) {
		this.quantite=q;
		this.prix=p;
	}
	
	public double getQuantite() {
		return this.quantite;
	}
	
	public double getPrix() {
		return this.prix;
	}
}

public class AchatProd {
	private HistoriqueCommandeProduc hist;
	private Lindt lindt;

	public AchatProd(HistoriqueCommandeProduc hist, Lindt lindt){
		this.hist = hist;
		this.lindt = lindt;
	}
	
	// Création d'une fonction qui calcule la quantité demandée en comparant les 2prods
	public CommandeInterne calculQuantiteDemandee(List<CommandeDistri> listeCommandesDist){
		double besoinCacao=0;
		for (CommandeDistri c : listeCommandesDist){
			for (int i=0; i<3 ; i++){
				if (c.getProduit().equals(Constante.listeProduit[i]))
					besoinCacao += c.getQuantite()*Constante.listeProduit[i].getRatioCacao();
		}}
		double stockCacao=lindt.getStockCacao().getStock();
		if (stockCacao<besoinCacao){
			besoinCacao=besoinCacao-stockCacao;
		}
		double quantiteEnVente = 0;
		for (IProducteur p: lindt.getProducteurs()){
			quantiteEnVente += p.annonceQuantiteMiseEnVente(lindt);
		}
		// Dans cette partie: Est-ce que je fais du stock? Prix d'achat?
		double prixDemande;
		if (besoinCacao <= quantiteEnVente){
			if (besoinCacao <= 0.7*quantiteEnVente){
				besoinCacao += 0.2*besoinCacao;
				prixDemande=MarcheProducteur.LE_MARCHE.getCours();}
				if (besoinCacao <= 0.7*quantiteEnVente){
					prixDemande=0.85*MarcheProducteur.LE_MARCHE.getCours();
				}
				else{
					prixDemande=MarcheProducteur.LE_MARCHE.getCours();
					}
			}
		else {
			prixDemande=1.2*MarcheProducteur.LE_MARCHE.getCours();
		}
		return new CommandeInterne(besoinCacao, prixDemande);
	}
	
	/**
	 * Indique la quantite demandee au producteur p.
	 * 
	 */
	// TODO Ne pas oublier de faire un methode permettant de recup la listeCommande...
	public double annonceQuantiteDemandee(){ 
		return 0.0;//this.calculQuantiteDemandee(listeCommandesDist).getQuantite();
	}
	public double getPrix(){
		return 0.0; //this.calculQuantiteDemandee(listeCommandesDist).getPrix();
	}
}