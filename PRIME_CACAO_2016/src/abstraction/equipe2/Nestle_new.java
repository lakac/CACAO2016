package abstraction.equipe2;
import abstraction.commun.*;

import java.util.HashMap;
import java.util.List;


public class Nestle_new implements ITransformateurP, ITransformateurD {
	
	private HashMap<IDistributeur,List<CommandeDistri>> commandesdistri;
	private StockCacao stockCacao;
	private StockChocolats stockChocolats;
	private Tresorerie tresorerie;
	

	public void next() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Catalogue getCatalogue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandeDistri> livraisonEffective(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandeDistri> offre(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandeDistri> Offre(List<CommandeDistri> o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double annoncePrix() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public HashMap<IDistributeur, List<CommandeDistri>> getCommandesdistri() {
		return commandesdistri;
	}


	// Annonce de la quantite de CACAO souhait�e au marche
	public double annonceQuantiteDemandee() {
		double resultat = 0.0;
		for (IDistributeur d : this.getCommandesdistri().keySet()) {
		 	for (CommandeDistri c : this.getCommandesdistri().get(d)) {
				resultat+=c.getQuantite()*c.getProduit().getRatioCacao()
						*(Constante.ACHAT_SANS_PERTE+(Constante.PERTE_MINIMALE + Math.random()*(Constante.VARIATION_PERTE)))
						*Constante.DEMANDE_ACTEURS;
			}
		}
		return resultat;
	}

	//Methode inutile
	public double annonceQuantiteDemandee(IProducteur p) {
		// TODO Auto-generated method stub
		return 0;
	}

	// Declenche la mise a jour de la tresorerie de du stock de CACAO
	public void notificationVente(CommandeProduc c) {
		tresorerie.setTresorerieAchat(c);
		this.stockCacao.MiseAJourStockLivraison(Constante.CACAO,c.getQuantite());
	}

	//Methode inutile
	public void notificationVente(IProducteur p) {
		// TODO Auto-generated method stub
		
	}
	

}
