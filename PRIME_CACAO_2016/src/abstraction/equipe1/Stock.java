package abstraction.equipe1;
import java.util.LinkedList;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Stock {

	/**
	 * Nombre de steps avant que le cacao ne soit perime
	 */
	private final int PEREMPTION = 10;
  
	/**
	 * Quantite totale de cacao presente dans le stock
	 */
	private Indicateur quantite;
  
	/**
	 * Repartition du stock selon la date de peremption
	 * 
	 * Les feves les plus anciennes sont en tete de la file
	 */
	private LinkedList<Double> stockParStep;
	
	/**
	 * Creation du stock
	 * 
	 * Le parametre indique la quantite presente dans le stock avant le debut de la simulation
	 * 
	 * Cette quantite initiale est repartie equitablement dans la pile entre les differents steps
	 */
	public Stock(Acteur createur, double stockInitial) {
		this.quantite = new Indicateur("Stock de "+createur.getNom(), createur);
		Monde.LE_MONDE.ajouterIndicateur(this.quantite);
		this.quantite.setValeur(createur, stockInitial);
		
		this.stockParStep = new LinkedList<Double>();
		for (int i=1;i<=this.PEREMPTION;i++) {
			this.stockParStep.add(stockInitial/this.PEREMPTION);
		}
	}
	
	public double getQuantite() {
		return this.quantite.getValeur();
	}
	
	/**
	 * Met a jour la quantite totale du stock
	 * Enleve le stock perime de la pile
	 * Ajoute la production du step a la pile
	 * @param production
	 */
	public void ajouterProd(double production) {
		this.quantite.setValeur(this.quantite.getCreateur(), this.getQuantite() + production - this.stockParStep.remove());
		this.stockParStep.add(production);
	}
	
	/**
	 * Met a jour la pile des stocks apres une vente
	 * Ne teste pas si la quantite vendue est acceptable : on suppose que les tests ont ete effectues auparavant
	 * @param acheteur
	 * @param quantiteVendue
	 */
	public void retirerVente(Acteur acheteur, double quantiteVendue) {
		this.quantite.setValeur(acheteur, this.quantite.getValeur() - quantiteVendue);
		double qte = quantiteVendue;
		int i=0;
		while (qte>0) {
			if (this.stockParStep.get(i)>qte) {
				this.stockParStep.set(i, this.stockParStep.get(i)-qte);
				qte = 0;
			} else {
				qte -= this.stockParStep.get(i);
				this.stockParStep.set(i, 0.0);
			}
			i++;
		}
	}
	
	public String toString() {
		String res = "Nous possedons "+this.getQuantite()+" tonnes de cacao reparties ainsi : [";
		for (int i=0;i<this.PEREMPTION-1;i++) {
			res += this.stockParStep.get(i) + ", ";
		}
		res += this.stockParStep.peekLast() +"]";
		return res;
	}
	
	public double getStockParStep(int i) {
		return this.stockParStep.get(i);
	}

	public void reductionStock(double quantite2) {
		// TODO Auto-generated method stub
		
	}

	public Indicateur getStockCacao() {
		// TODO Auto-generated method stub
		return null;
	}
}
