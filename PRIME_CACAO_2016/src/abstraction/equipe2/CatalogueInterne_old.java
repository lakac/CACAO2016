package abstraction.equipe2;
import abstraction.commun.*;

public class CatalogueInterne_old {

	private Catalogue catalogueinterne;

	public CatalogueInterne_old() {
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
