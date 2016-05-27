package abstraction.equipe6;

import java.util.Arrays;
import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.ITransformateur;
import abstraction.commun.Produit;

public class PrixVente {
	private Double prix;
	private ITransformateur transformateur;
	private Produit nomproduit;
	
	public PrixVente(Double prix, Produit nomproduit, ITransformateur transf ){
		this.prix=prix;
		this.nomproduit=nomproduit;
		this.transformateur =transf;
	}
	

	public Double getPrixVente(){
		return this.prix;
	}


	/*public void setPrix (CommandeDistri c ) {
			this.prix=1.25*(c.getPrixTonne());
			;
		}
	

		/*if (prixréf>1.2*p2) {
			this.prix = (prixréf - p2);			
		}
		else if (prixréf<0.95*p2) {
			this.prix = prixréf+0.05*p2;
		}*/
		
		
	
	}
	

	
	
	


