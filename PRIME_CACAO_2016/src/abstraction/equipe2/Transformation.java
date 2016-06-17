package abstraction.equipe2;

public class Transformation {

	public double getCacaotransforme() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//variables d'instance, représente à l'étape n la quantité produite de chocolat de chaque espèce
	private double chocolat_50;
	private double chocolat_60;
	private double chocolat_70;
	
	//ce constructeur ne sera jamais utilisé, 
	//il vaudrait mieux garder le constructeur vide et des méthodes de production à mon sens
	//mais je vais l'utiliser pour les tests de la trésorerie
	
	//Constructeurs
	public Transformation(double chocolat50, double chocolat60, double chocolat70){
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
	 * 				ex. chocolat50% : 0.5*(cacao + margeDeSecurite - perte) + 0.5*autresIngredients  
	 * 			autresIngredient toujours disponibles et compris dans COUT_TRANSFORMATION
	 * 
	 * 		6. Stocker le chocolat fabrique dans Stockchocolat
	 */
	
	public double quantiteCommandeDistri(){
		return 0; //A COMPLETER
	}
	
	public double calculPerteCacao(){
		return 0; //A COMPLETER
	}
	
	public double calculMargedesecurite(){
		return 0; //A COMPLETER
	}
	
	public double cacaoTransformer(){
		return 0; //A COMPLETER
	}
	
	public Transformation transformerCacaoChocolat(Transformation transformation){
		return transformation;	//A COMPLETER
	}
	
	public void ajouterChocolatStock(){
		;	//A COMPLETER
	}
	

}
