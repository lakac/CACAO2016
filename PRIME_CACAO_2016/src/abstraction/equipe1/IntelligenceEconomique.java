package abstraction.equipe1;

import abstraction.commun.MarcheProd;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

/**
 * Classe pour calculer la quantite mise en vente pour chaque transformateur
 */
public class IntelligenceEconomique {

	/** Stock de notre producteur */
	private Stock stock;

	/**
	 * Coefficients associes au stock produit a differentes dates.
	 * Le cacao le plus ancien est en tete.
	 */
	private final double[] coeffPerissabilite = {1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1};

	Journal journal;
	private static final String NOM_JOURNAL = "Intelligence économique de l'"+Producteur.NOM_PRODUCTEUR;

	public IntelligenceEconomique(Stock s) {
		this.stock = s;
		this.journal = new Journal(IntelligenceEconomique.NOM_JOURNAL);
		Monde.LE_MONDE.ajouterJournal(this.journal);
	}

	private double calculerEnvieDeVendre() {
		double rapport = (MarcheProd.LE_MARCHE.getCoursCacao().getValeur()-MarcheProd.CoursMinimum)/(MarcheProd.CoursMaximum-MarcheProd.CoursMinimum);
		return (1.0 + Math.sin((rapport - 0.5) * Math.PI)) / 2.0;
	}

	private double calculerBesoinDeVendre() {
		double besoin = 0.0;
		for (int i = 0; i<this.coeffPerissabilite.length; i++) {
			besoin += this.coeffPerissabilite[i]*this.stock.getStockParStep(i);
		}
		return besoin;
	}

	public double calculerOffreTotale() {
		double envie = calculerEnvieDeVendre();
		double besoin = calculerBesoinDeVendre();
		double offre = besoin*(1.0-envie) + this.stock.getQuantite()*envie; 
		
//		this.journal.ajouter("<hr>");
//		this.journal.ajouter("Actualisation step "+Monde.LE_MONDE.getStep());
//		this.journal.ajouter("Envie de vendre : "+envie);
//		this.journal.ajouter("<table>" +
//				"<tr><td>Besoin de vendre</td> <td>"+besoin+"</td></tr>" +
//				"<tr><td>Quantité mise en vente</td> <td>"+offre+"</td></tr>" +
//				"<tr><td>Stock total</td> <td>"+this.stock.getQuantite()+"</td></tr>" +
//				"</table>");
		
		return offre;
	}
}
