package abstraction.equipe5;

import java.util.ArrayList;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.IProducteur;
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
		for (CommandeDistri c : lindt.getHistCommandeDistri().getHist()){
			if (c.getStepLivraison()==Monde.LE_MONDE.getStep()+2){
				lindt.getStockCacao().setStock((lindt.getStockCacao().getStock()-c.getQuantite()*c.getProduit().getRatioCacao()));
				for (int i=0; i<lindt.getStocksChocolat().size(); i++){
					if (c.getProduit().getNomProduit()==lindt.getStocksChocolat().get(i).getNom()){
						lindt.getStocksChocolat().get(i).setStock(lindt.getStocksChocolat().get(i).getStock()+c.getQuantite());
						lindt.getTreso().retrait(c.getQuantite()*c.getProduit().getRatioCacao()*Constante.COUT_TRANSFORMATION);;
					}
				}
			}
		}
	}
	
	
	
	public static void main(String[] args){
		//Test
	}
}

