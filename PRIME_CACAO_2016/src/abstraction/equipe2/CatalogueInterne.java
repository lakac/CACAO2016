package abstraction.equipe2;
import abstraction.commun.*;

public class CatalogueInterne {

	private Catalogue catalogueinterne;

	public CatalogueInterne() {
		this.catalogueinterne = new Catalogue();
	}
	
	
	public Catalogue getCatalogueinterne() {
		return catalogueinterne;
	}


	public void setCatalogueinterne(Tarif tarif50, Tarif tarif60, Tarif tarif70) {
		this.catalogueinterne.add(Constante.PRODUIT_50 , tarif50);
		this.catalogueinterne.add(Constante.PRODUIT_60, tarif60);
		this.catalogueinterne.add(Constante.PRODUIT_70, tarif70);
	}
	
	
	
	

}
