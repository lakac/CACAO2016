package abstraction.equipe5;

public class Produits extends Stock{
	// Ici on a mettre les 3 types de chocolats que l'on vend
	// 70 , 80 , 90% de cacao? :)
	private String[] listeProduit;

	// Constructeur (que je n'arrive pas à initialiser)
	public Produits(){
		this.listeProduit="70%","80%","90%";
	}
	
	// Getter
	public String getProduit(int i) {
		return this.listeProduit[i];
	}

	// Variation du stock en fonction du chocolat commandé
	public void retirerStock(String p, double quantite){
		int i=0;
		for (String h:this.listeProduit){	
			if (p==h){
				if (this.getStock()-quantite*Constante.RATIO_CACAO_CHOCOLAT[i] >= 0) {
					this.setStock(this.getStock()-quantite*Constante.RATIO_CACAO_CHOCOLAT[i]);
						}}
			i++;
		}}
	
}
