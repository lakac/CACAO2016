package abstraction.equipe6;

import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Indicateur;

public class PrixVente {
	private List<Double> prixparproduits;
	private List<Indicateur> historiquedesproduits;
	private List<String> nomproduits;
	private int step;
	
	public PrixVente(List<Double> ppp, List<Indicateur> hdp, List<String> nomproduits, int step ){
		this.historiquedesproduits=hdp;
		this.prixparproduits=ppp;
		this.nomproduits=nomproduits;
		this.step =step;
	}

	public List<Double> getPrixVente(){
		return this.prixparproduits;
	}
	public List<Indicateur> getHistoriquePrix(){
		return this.historiquedesproduits;
	}

}
	
	/*public void setPrix ( List<PrixAchat> p1, List<PrixAchat> p2, List<PrixAchat> pautres, PrixVenteLeclerc ) {
		double prixréf = 1.25*p1+;
		if (prixréf>1.2*p2) {
			this.prix = (prixréf - p2);			
		}
		else if (prixréf<0.95*p2) {
			this.prix = prixréf+0.05*p2;
		}
	}
	
	public void Promotion*/
	
	
	


