package abstraction.equipe2;



import java.util.HashMap;
import abstraction.commun.*;


public class Transformation {

	public double getCacaotransforme() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//variables d'instance, représente à l'étape n la quantité produite de chocolat de chaque espèce
	private double chocolat_50;
	private double chocolat_60;
	private double chocolat_70;
	private HashMap<Produit,Double> transformation;
	
	//ce constructeur ne sera jamais utilisé, 
	//il vaudrait mieux garder le constructeur vide et des méthodes de production à mon sens
	//mais je vais l'utiliser pour les tests de la trésorerie
	
	//Constructeurs
	public Transformation(double chocolat50, double chocolat60, double chocolat70){
		this.transformation=new HashMap<Produit,Double>();
		this.transformation.put(Constante.PRODUIT_50, chocolat50);
		this.transformation.put(Constante.PRODUIT_60, chocolat60);
		this.transformation.put(Constante.PRODUIT_70, chocolat70);
		this.chocolat_50 = chocolat50;
		this.chocolat_60 = chocolat60;
		this.chocolat_70 = chocolat70;
	}
	
	public Transformation(){
		this(0.0,0.0,0.0);
	}
	
	//Accesseurs en lecture
	public double getChocolat50(){
		return this.chocolat_50;
	}
	
	public double getChocolat60(){
		return this.chocolat_60;
	}
	
	public double getChocolat70(){
		return this.chocolat_70;
	}
	
	public HashMap<Produit, Double> getTransformation() {
		return transformation;
	}

	//Accesseurs en ecriture
	public void setChocolat50(double quantite){
		this.chocolat_50 = quantite;
	}
	
	public void setChocolat60(double quantite){
		this.chocolat_60 = quantite;
	}
	
	public void setChocolat70(double quantite){
		this.chocolat_70 = quantite;
	}
	
	
	//Methode permettant de savoir quel chocolat on privilégie ( on privilégie le chocolat 
	//que veut le meilleur acheteur du step précédent en % )
	public void repartitionChocolat(){
//A COMPLETER
	}
	
// Methode permettant de transformer le cacao en chocolat : doit retirer le cacao du stock de cacao
// doit ajouter le chocolat créé dans le stock de chocolat
	public void setTransformation(HashMap<Produit, Double> transformation) {
//A COMPLETER
	}
	
	
	//Methodes toString et equals
	public String toString(){
		return "La quantite de chocolat_50% transformee est egale a "+this.getChocolat50()+" T. "+"\n"
				+"La quantite de chocolat_60% transformee est egale a "+this.getChocolat60()+" T. "+"\n"
				+"La quantite de chocolat_70% transformee est egale a "+this.getChocolat70()+" T. "+"\n";
	}
	
	public boolean equals(Object o){
		return (o instanceof Transformation)
				&& (this.getChocolat50() == ((Transformation)o).getChocolat50())
				&& (this.getChocolat60() == ((Transformation)o).getChocolat60())
				&& (this.getChocolat70() == ((Transformation)o).getChocolat70());
	}
	
	
	/**Etapes de la transformation :
	 * 
	 * 		1. Recuperer la Commande des distributeurs
	 * 
	 * 		2. Calculer la perte de cacao transformee a partir de Constante : PERTE_MINIMALE + VARIATION_PERTE
	 * 
	 * 		3. Calculer la Marge de securite a partir de Constante : MARGE_DE_SECURITE 
	 * 
	 * 		4. Prendre le cacao de Stockcacao : determiner la quantite a transformer 
	 * 			en fonction de la Commande des distributeurs + marge de securite
	 * 
	 * 		5. Transformation en chocolat : 
	 * 				ex. chocolat50% : 0.5*(cacaoATransformer) (+ 0.5*autresIngredients  
	 * 			autresIngredient toujours disponibles et compris dans COUT_TRANSFORMATION ?)
	 * 
	 * 		6. Stocker le chocolat fabrique dans Stockchocolat
	 */
	
	public double quantiteCommandeDistri(){
		
		//List<CommandeDistri> commande = this.CommandeFinale();
		/**pb de next dans les echanges distri-transfo ?*/
		return 0; //A COMPLETER
	}
	
	public double calculPerteCacao(){
		return 0; //A COMPLETER
	}
	
	public double calculMargedesecurite(){
		return 0; //A COMPLETER
	}
	
	/**Calcul du cacao a transformer en fonction de 
	 * la commande , la perte et la marge de securite
	 * 
	 * 
	 *  */
	public double cacaoATransformer(){
		double cacaoATransformer = 0;
		
		double commande = this.quantiteCommandeDistri();
		double perte = this.calculPerteCacao();
		double marge =this.calculMargedesecurite();
		
		cacaoATransformer = commande + perte + marge;
		
		return cacaoATransformer; 
	}
	
	
	/**Mise a jour du stock de cacao
	 * on retire le cacaoATransformer() de StockCacao
	 *  
	 * */
	
	public void retirerCacaoStock(){
		StockCacao stockCacao = new StockCacao();
		double cacaoATransformer = this.cacaoATransformer();
		
		;//A COMPLETER
	}
	

	/**Transformation transformerCacaoChocolat(): 
	 * methode qui recupere la quantite de cacao a transformer 
	 * puis retourne les quantites de chocolat transformes 
	 * dans un objet de type Transformation
	 * 
	 * 
	 * ps: on peut eventuellement completer en rajoutant les autres ingredients 
	 * */
	
	public Transformation transformerCacaoChocolat(){
		
		double cacaoATransformer = this.cacaoATransformer();
		
		double chocolat50 = this.getChocolat50();
		double chocolat60 = this.getChocolat60();
		double chocolat70 = this.getChocolat70();
		
		chocolat50 = 0.5*cacaoATransformer;
		chocolat60 = 0.6*cacaoATransformer;
		chocolat70 = 0.7*cacaoATransformer;
		
		//Transformation t1 = new Transformation(chocolat50,chocolat60,chocolat70);
		
		Transformation t = new Transformation();
		t.setChocolat50(chocolat50);
		t.setChocolat60(chocolat60);
		t.setChocolat70(chocolat70);
		
		return t;	
	}
	
	
	/**Mise a jour du Stock de chocolats transformes 
	 * appel methode transformerCacaoChocolat()
	 * */
	
	public void ajouterChocolatStock(){
		Transformation t = this.transformerCacaoChocolat();
		StockChocolats stockchocolats = new StockChocolats();
		
		stockchocolats.MiseAJourStockTransformation(Constante.PRODUIT_50, t.getChocolat50());
		stockchocolats.MiseAJourStockTransformation(Constante.PRODUIT_60, t.getChocolat60());
		stockchocolats.MiseAJourStockTransformation(Constante.PRODUIT_70, t.getChocolat70());
		;	
	}
	

}
