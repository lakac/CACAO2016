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


	public void setCatalogueinterne(PlageInterne plageinterne) {
		for (Produit p : plageinterne.getTarifproduit().keySet()) {
		this.catalogueinterne.add(p, plageinterne.getTarifproduit().get(p));
		}		
	}
}
