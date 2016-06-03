package abstraction.equipe6;

import java.util.Arrays;
import java.util.HashMap;
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
	public ITransformateur getTransformateur() {
		return this.transformateur;
	}
	public Produit getProduit(){
		return this.nomproduit;
	}
	public void setPrix(Double prix) {
		this.prix = prix;
	}
	public void setTransformateur(ITransformateur transformateur) {
		this.transformateur = transformateur;
	}
	public void setNomproduit(Produit nomproduit) {
		this.nomproduit = nomproduit;
	}



	
		   
		
			

		
	
	}
	

	
	


