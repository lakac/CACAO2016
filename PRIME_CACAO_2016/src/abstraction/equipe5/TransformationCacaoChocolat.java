package abstraction.equipe5;

import abstraction.commun.Commande;
import abstraction.commun.CommandeDistri;
import abstraction.fourni.Monde;

public class TransformationCacaoChocolat {
	private Lindt lindt;

	public TransformationCacaoChocolat(Lindt lindt) {
		this.lindt = lindt;
	}
	
	/**
	 * Transformation() est une fonction qui ne retourne rien, 
	 * lorsqu elle est appelee, elle met a jour les stocks.
	 */
	
	public void Transformation(){
		for (Commande c : lindt.getHistCommandeDistri().getHist()){
			if (((CommandeDistri)c).getStepLivraison()==Monde.LE_MONDE.getStep()+2){
				lindt.getStockCacao().setStock((lindt.getStockCacao().getStock()-c.getQuantite()*((CommandeDistri)c).getProduit().getRatioCacao()));
				for (int i=0; i<lindt.getStocksChocolat().size(); i++){
					if (((CommandeDistri)c).getProduit().getNomProduit()==lindt.getStocksChocolat().get(i).getNom()){
						lindt.getStocksChocolat().get(i).setStock(lindt.getStocksChocolat().get(i).getStock()+c.getQuantite());
						lindt.getTreso().retrait(c.getQuantite()*((CommandeDistri)c).getProduit().getRatioCacao()*Constante.COUT_TRANSFORMATION);;
					}
				}
			}
		}
	}
	
	
	
	public static void main(String[] args){
		//Test
	}
}

