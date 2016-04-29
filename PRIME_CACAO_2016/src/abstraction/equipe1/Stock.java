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
   * Les feves les plus anciennes sont en tête de la pile
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

	
// Retirer et ajouter du stock (avec quantite>=0)
// Si des fèves sont périmées, les retirer du stock (ici on part sur 5 mois soit 10 steps, mais 
// Modifiable avec des infos valides
	
}
