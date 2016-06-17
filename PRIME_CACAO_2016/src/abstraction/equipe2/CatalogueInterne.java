package abstraction.equipe2;

import java.util.List;

import abstraction.commun.*;

public class CatalogueInterne extends Catalogue {
	
	private Catalogue catalogueinterne;
	
	public CatalogueInterne(Tarif tarifinitial){
		super();
		super.add(Constante.PRODUIT_50, tarifinitial);
		super.add(Constante.PRODUIT_60, tarifinitial);
		super.add(Constante.PRODUIT_70, tarifinitial);	
	}
	
	public Tarif getTarif(Produit p){
		return this.catalogueinterne.getCata().get(p);
	}

	public void setTarif(Tarif tarif, List<PlageInterne> plageinterne) {
		for (Produit p : plageinterne.getTarifproduit().keySet()) {
			this.catalogueinterne.add(p, plageinterne.getTarifproduit().get(p));
			}
	}
	
	public static void main(String[] args) {
		
		Tarif tarifini = new Tarif()
		CatalogueInterne catalogueint = new CatalogueInterne(tarifini);
	}
	
		
	}

