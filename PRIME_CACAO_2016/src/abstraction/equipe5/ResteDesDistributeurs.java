package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateurD;
import abstraction.commun.MarcheDistributeur;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;

public class ResteDesDistributeurs{
	
	//distributeur 3 en interne, on lui vend 75% de notre stock de chocolat
	
	private List<CommandeDistri> commande;
	private Lindt lindt;
	private VenteDist venteDist;
	private IDistributeur distributeurRestant;
		
	class DistributeurRestant implements Acteur, IDistributeur {
		
		public void next() {
	}


		public String getNom() {
			// TODO Auto-generated method stub
			return "Ditributeur restant de Lindt";
		}

	/*	@Override
		public List<CommandeDistri> Demande(ITransformateurD t, Catalogue c) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CommandeDistri> ContreDemande(List<CommandeDistri> nouvelle, List<CommandeDistri> ancienne) {
			// TODO Auto-generated method stub
			return null;
		}
<<<<<<< HEAD
		
=======
*/

		@Override
		public Double getStock(Produit p, ITransformateurD t) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Double getPrixVente(Produit p, ITransformateurD t) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public List<CommandeDistri> demande(ITransformateurD t, Catalogue c) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CommandeDistri> contreDemande(List<CommandeDistri> nouvelle, List<CommandeDistri> ancienne) {
			// TODO Auto-generated method stub
			return null;
		}




		


		

	}
	public ResteDesDistributeurs(Lindt lindt, VenteDist venteDist) {
		this.commande = new ArrayList<CommandeDistri>();
		this.lindt = lindt;
		this.venteDist=venteDist;
		this.distributeurRestant= new DistributeurRestant();
	}
	
	// prendre les commandes finales de leclerc et carrefour et les multiplier par 3 pour obtenir les commandes du 3eme distributeur.
	//Ne pas oublier de les rajouter à l'historique de commande distri!
	
	public List<CommandeDistri> commandesDistributeurRestant(){
		List<CommandeDistri> commandesResteDuMonde= new ArrayList<CommandeDistri>();
		int i=0;
		double ratioCommandeResteDuMonde= 3;
		for(IDistributeur d: lindt.getDistributeurs()){ 
			for (CommandeDistri cd : MarcheDistributeur.LE_MARCHE_DISTRIBUTEUR.obtenirCommandeFinale(lindt,d)){// commandes finales de leclerc et carrefour
				commandesResteDuMonde.add(cd);
				commandesResteDuMonde.get(i).setAcheteur(distributeurRestant);
				
				double qteCommandeResteDuMonde=ratioCommandeResteDuMonde*cd.getQuantite();//quantité que le distributeur restant doit commander
				commandesResteDuMonde.get(i).setQuantite(qteCommandeResteDuMonde);//mise à jour de la bonne quantité dans la commande
				lindt.getHistCommandeDistri().ajouter(cd);
				i++;
			}
		}
		return commandesResteDuMonde;
	}
	
	
	public Lindt getLindt() {
		return lindt;
	}
	public List<CommandeDistri>getCommande() {
		return commande;
	}
	public void setCommande(List<CommandeDistri> commande) {
		this.commande = commande;
	}
	
}

