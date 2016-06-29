package abstraction.equipe5;

import javax.swing.JOptionPane;

import abstraction.commun.Commande;
import abstraction.commun.CommandeDistri;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class TransformationCacaoChocolat {
	private Lindt lindt;
	private Journal journal;

	public TransformationCacaoChocolat(Lindt lindt, Journal journal) {
		this.lindt = lindt;
		this.journal = journal;
	}
	
	public Journal getJournal() {
		return this.journal;
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
						lindt.getTreso().retrait(c.getQuantite()*(((CommandeDistri)c).getProduit().getRatioCacao()/100)*Constante.COUT_TRANSFORMATION);
					}
				}
			}
		}
	}
}

