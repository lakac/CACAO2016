
package abstraction.commun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import abstraction.fourni.Acteur;


public class MarcheDistributeur implements Acteur {

	private List<ITransformateurD> lestransfos;
	private List<IDistributeur> lesdistris;
	private List<Produit> lesproduits;
	private HashMap<ITransformateurD,Catalogue> cat;
	private List<CommandeDistri> historiquecommande;
	private List<CommandeDistri> commandefinale;
	private List<CommandeDistri> livraisonglobale;
	public static MarcheDistributeur LE_MARCHE_DISTRIBUTEUR;

	public MarcheDistributeur()	 {
		this.lestransfos = new ArrayList<ITransformateurD>() ;
		this.lesdistris = new ArrayList<IDistributeur>() ;
		this.lesproduits = new ArrayList<Produit>() ;
		this.cat = new HashMap<ITransformateurD, Catalogue>() ;
		this.historiquecommande = new ArrayList<CommandeDistri>() ;
		this.commandefinale = new ArrayList<CommandeDistri>();
		this.livraisonglobale = new ArrayList<CommandeDistri>();
	}

	public void addCatalogue(ITransformateurD t, Catalogue c) {
		this.cat.replace(t, c);
	}

	public void addTransformateur(ITransformateurD t) {
		this.lestransfos.add(t);
	}

	public void addDistributeur(IDistributeur d) {
		this.lesdistris.add(d);
	}

	public void addProduit(Produit p) {
		this.lesproduits.add(p);
	}

	public List<ITransformateurD> getLesTransfos() {
		return this.lestransfos;
	}

	public List<IDistributeur> getLesDistris() {
		return this.lesdistris;
	}

	public List<Produit> getLesProduits() {
		return this.lesproduits;
	}

	public HashMap<ITransformateurD, Catalogue> getCatalogues() {
		return this.cat;
	}

	public List<CommandeDistri> getHistoriqueCommande() {
		return this.historiquecommande;
	}

	public void addCommandeToHistorique(List<CommandeDistri> cd) {
		this.getHistoriqueCommande().addAll(cd);
	}

	public List<CommandeDistri> getCommandeFinale() {
		return commandefinale;
	}

	public void setCommandeFinale(List<CommandeDistri> commandefinale) {
		this.commandefinale = commandefinale;
	}

	public List<CommandeDistri> getLivraisonglobale() {
		return livraisonglobale;
	}

	public void setLivraisonGlobale(List<CommandeDistri> livraisonglobale) {
		this.livraisonglobale = livraisonglobale;
	}



	public String getNom() {
		return "march� du chocolat";
	}


	public List<CommandeDistri> obtenirCommandeFinale(ITransformateurD t, IDistributeur d) {
		List<CommandeDistri> temp = new ArrayList<CommandeDistri>();
		for (int i=0; i<this.getCommandeFinale().size(); i++) {
			if (this.getCommandeFinale().get(i).getAcheteur() == d && this.getCommandeFinale().get(i).getVendeur() == t) {
				temp.add(this.getCommandeFinale().get(i));
			}
		}
		return temp;
	}

	public List<CommandeDistri> obtenirLivraisonEffective(ITransformateurD t, IDistributeur d) {
		List<CommandeDistri> temp = new ArrayList<CommandeDistri>();
		for (ITransformateurD t0 : this.getLesTransfos()) {
			for(IDistributeur d0 : this.getLesDistris()) {
				for (int i=0; i<this.getLivraisonglobale().size(); i++) {
					if (this.getLivraisonglobale().get(i).getAcheteur() == d0 && this.getLivraisonglobale().get(i).getVendeur() == t0) {
						temp.add(this.getLivraisonglobale().get(i));
					}
				}
			}
		}
		return temp;
	}

	public boolean distriValide( List<CommandeDistri> cd) {
		for (CommandeDistri d : cd ) {
			if (d.getValidation() == false) {
				return false;
			}
		}
		return true;
	}

	public boolean marcheValide( HashMap<IDistributeur, List<CommandeDistri>> hm) {
		for (int i=0; i<hm.size(); i++) {
			if (distriValide(hm.get(this.lesdistris.get(i))) == false) {
				return false;
			}
		}
		return true;
	}

	public HashMap<ITransformateurD, List<CommandeDistri>> RenvoiDistri(HashMap<IDistributeur, List<CommandeDistri> > hm) {
		HashMap<ITransformateurD, List<CommandeDistri> > NegoTransfo = new HashMap<ITransformateurD, List<CommandeDistri>>();
		for (ITransformateurD t : this.getLesTransfos()) {
			NegoTransfo.put(t, new ArrayList<CommandeDistri>());	
		}
		for (IDistributeur d : this.getLesDistris()) {
			for (ITransformateurD t : this.getLesTransfos()) {
				for (CommandeDistri com : hm.get(d)) {
					if (hm.get(d).get(hm.get(d).indexOf(com)).getVendeur() == t) {
						NegoTransfo.get(t).add(hm.get(d).get(hm.get(d).indexOf(com)));
					}
				}
			}
		}
		return NegoTransfo;

	}

	public HashMap<IDistributeur, List<CommandeDistri>> RenvoiTransfo(HashMap<ITransformateurD, List<CommandeDistri> > hm) {
		HashMap<IDistributeur, List<CommandeDistri> > NegoDistri = new HashMap<IDistributeur, List<CommandeDistri>>();
		for (IDistributeur d : this.getLesDistris()) {
			NegoDistri.put(d, new ArrayList<CommandeDistri>());	
		}
		System.out.println("les distris : "+this.getLesDistris());
		System.out.println("NegoDistri :"+NegoDistri);
		for (ITransformateurD t : this.getLesTransfos()) {
			for (IDistributeur d : this.getLesDistris()) {
				for (CommandeDistri com : hm.get(t)) {;
				if (hm.get(t).get(hm.get(t).indexOf(com)).getAcheteur() == d) {
					NegoDistri.get(d).add(hm.get(t).get(hm.get(t).indexOf(com)));
				}
				}
			}
		}
		return NegoDistri;

	}

	public HashMap<IDistributeur, List<CommandeDistri>> copieProfonde (HashMap<IDistributeur, List<CommandeDistri>> aCopie) {
		HashMap<IDistributeur, List<CommandeDistri> > copieProfonde = new HashMap<IDistributeur, List<CommandeDistri>>();
		for (IDistributeur d : this.getLesDistris()) {
			copieProfonde.put(d, new ArrayList<CommandeDistri>());
			for (CommandeDistri cd : aCopie.get(d)) {
				copieProfonde.get(d).add(new CommandeDistri(cd.getAcheteur(),cd.getVendeur(),cd.getProduit(),cd.getQuantite(), cd.getPrixTonne(),cd.getStepLivraison(), cd.getValidation()));
			}
		}
		return copieProfonde;
	}

	public void next() {

		// Discussions concernant les livraisons � venir.

		HashMap<IDistributeur, List<CommandeDistri> > NegoDistri = new HashMap<IDistributeur, List<CommandeDistri>>();
		HashMap<ITransformateurD, List<CommandeDistri> > NegoTransfo = new HashMap<ITransformateurD, List<CommandeDistri>>();

		for (ITransformateurD t : this.getLesTransfos()) {
			if (this.getCatalogues().size() < this.getLesTransfos().size()) {
				this.cat.put(t, t.getCatalogue());
			}
			this.addCatalogue(t, t.getCatalogue());;
		}
		for (IDistributeur d : this.getLesDistris()) {
			NegoDistri.put(d, new ArrayList<CommandeDistri>());
			for (ITransformateurD t : this.getLesTransfos()) {
				NegoDistri.get(d).addAll(d.demande(t, this.getCatalogues().get(t)));
				for (CommandeDistri cd : d.demande(t, this.getCatalogues().get(t))) {
					System.out.println("La quantit� apr�s demande --> :"+cd.getQuantite());
				}
			}
			// System.out.println("NegoDistri avant offre --> "+NegoDistri);
			int i = 0;
			while (marcheValide(NegoDistri) == false) {
				i+=1;
				NegoTransfo = this.RenvoiDistri(NegoDistri);
				
				HashMap<IDistributeur, List<CommandeDistri>> NegoDistriTemp = copieProfonde(NegoDistri);
				System.out.println("NegoTransfo avant offre --> "+NegoTransfo);
				for (ITransformateurD t : this.getLesTransfos()) {
					System.out.println("Le march� connait comme transformateurs " + this.getLesTransfos());
					System.out.println("Liste de commandes pour "+t+" :"+NegoTransfo.get(t));
					NegoTransfo.replace(t, t.offre(NegoTransfo.get(t)));
				}
				System.out.println("NegoTransfo boucle num�ro "+i+" apr�s offre -->"+NegoTransfo);
				NegoDistri = this.RenvoiTransfo(NegoTransfo);
				System.out.println("NegoDistri boucle num�ro "+i+" apr�s offre -->"+NegoDistri);
				for (IDistributeur d1 : this.getLesDistris()) {
					System.out.println("La contre-demande -->"+d1.contreDemande(NegoDistri.get(d1),NegoDistriTemp.get(d1)));
					NegoDistri.replace(d1, d1.contreDemande(NegoDistri.get(d1),NegoDistriTemp.get(d1)));
				}
				System.out.println("NegoDistri boucle num�ro "+i+" apr�s contreDemande -->"+NegoDistri);
				if (NegoDistri.equals(NegoDistriTemp)) {
					break;
				}
			}
			List<CommandeDistri> commandefinale = new ArrayList<CommandeDistri>();
			for (ITransformateurD t : this.getLesTransfos()) {
				commandefinale.addAll(NegoTransfo.get(t));
			}
			this.setCommandeFinale(commandefinale);
			this.addCommandeToHistorique(commandefinale);
			System.out.println("La commande finale --> "+this.getCommandeFinale());


			// Livraisons effectives chez les distributeurs et paiements.

			List<CommandeDistri> livraisonglobale = new ArrayList<CommandeDistri>();
			for (ITransformateurD t : this.getLesTransfos()) {
				for (IDistributeur d4 : this.getLesDistris()) {
					for (CommandeDistri cd : this.getHistoriqueCommande()) {
						System.out.println("La quantit� de la commande --> "+cd.getQuantite());
						List<CommandeDistri> temp = new ArrayList<CommandeDistri>();
						//System.out.println("Commande consider� --> "+cd);
						if (cd.getStepLivraison() == MondeV1.LE_MONDE.getStep() && t == cd.getVendeur() && d4 == cd.getAcheteur()) {
							temp.add(cd);
							//System.out.println("Temp --> "+temp);
						}
						System.out.println("Le transfo -->"+t);
						System.out.println("Temp -->"+temp);
						System.out.println("La livraison effective --> "+t.livraisonEffective(temp));
						livraisonglobale.addAll(t.livraisonEffective(temp));
					}
				} 
				System.out.println("Livraison globale --> "+livraisonglobale);
				this.setLivraisonGlobale(livraisonglobale);
			}	

		}
	}
}

