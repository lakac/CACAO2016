package abstraction.equipe6;

import java.util.Arrays;
import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.Produit;

public class PrixVente {
	private List<Double> prixparproduits;
	private List<Produit> nomproduits;
	private int step;
	
	public PrixVente(List<Double> ppp,  List<Produit> nomproduits, int step ){
		this.prixparproduits=ppp;
		this.nomproduits=nomproduits;
		this.step =step;
	}
	
	public PrixVente() {
		this(Arrays.asList(3.0,3.0,3.0),
				Arrays.asList(new Produit("p1",5.0),new Produit("p2",5.0),new Produit("p3",1.0)),1);
	}

	public List<Double> getPrixVente(){
		return this.prixparproduits;
	}


	public void setPrix ( List<CommandeDistri> t1, List<CommandeDistri> t2, List<CommandeDistri> tautres, List<Double> PrixVenteLeclerc ) {
		for (int i=0;i<=24;i++) {
			int c = 0;
			for (Double d : this.prixparproduits){
		        d=1.25*(t1.get(c).getQuantite()*t1.get(c).getPrixTonne()+t2.get(c).getQuantite()*t2.get(c).getPrixTonne()+
		        		tautres.get(c).getQuantite()*tautres.get(c).getPrixTonne())/
		        		(t1.get(c).getQuantite()+t2.get(c).getQuantite()+tautres.get(c).getQuantite());
		        c++;
			}
			
	
		}
		
		/*if (prixréf>1.2*p2) {
			this.prix = (prixréf - p2);			
		}
		else if (prixréf<0.95*p2) {
			this.prix = prixréf+0.05*p2;
		}*/
		
		
	
	}
	
}
	
	
	


