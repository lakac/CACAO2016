package abstraction.equipe1;
import java.util.LinkedList;

public class Stock {

	/**
	 * Nombre de steps avant que le cacao ne soit perime
	 */
	private final int PEREMPTION = 10;
  
	/**
	 * Quantite totale de cacao presente dans le stock
	 */
	private double quantite;
  
	/**
	 * Repartition du stock selon la date de peremption
	 * 
	 * Les feves les plus anciennes sont en tete de la pile
	 */
	private LinkedList<Double> stockParStep;
	
	/**
	 * Creation du stock
	 * 
	 * Le parametre indique la quantite presente dans le stock avant le debut de la simulation
	 * 
	 * Cette quantite initiale est repartie equitablement dans la pile entre les differents steps
	 */
	public Stock(int stockInitial) {
		this.quantite = stockInitial;
		for (int i=1;i<=this.PEREMPTION;i++) {
			this.stockParStep.add(this.quantite/this.PEREMPTION);
		}
	}
	
	public double getQuantite() {
		return this.quantite;
	}
	
	/**
	 * Met a jour la quantite totale du stock
	 * Enleve le stock perime de la pile
	 * Ajoute la production du step a la pile
	 * @param production
	 */
	public void ajouterProd(double production) {
		this.quantite += production - this.stockParStep.remove();
		this.stockParStep.add(production);
	}
	
	/**
	 * Met a jour la pile des stocks apres une vente
	 * Ne teste pas si la quantite vendue est acceptable : on suppose que les tests ont ete effectues auparavant
	 * @param quantiteVendue
	 */
	public void retirerVente(double quantiteVendue) {
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
}
