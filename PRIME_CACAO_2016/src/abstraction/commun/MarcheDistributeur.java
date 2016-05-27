package abstraction.commun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.fourni.Acteur;


public class MarcheDistributeur implements Acteur {

	private List<ITransformateur> lestransfos;
	private List<IDistributeur> lesdistris;
	private List<Produit> lesproduits;
	private HashMap<ITransformateur,Catalogue> cat;
	private List<CommandeDistri> historiquecommande;
	private List<CommandeDistri> commandefinale;
	private List<CommandeDistri> livraisonglobale;

	public MarcheDistributeur()	 {
		this.lestransfos = new ArrayList<ITransformateur>() ;
		this.lesdistris = new ArrayList<IDistributeur>() ;
		this.lesproduits = new ArrayList<Produit>() ;
		this.cat = new HashMap<ITransformateur, Catalogue>() ;
		this.historiquecommande = new ArrayList<CommandeDistri>() ;
		this.commandefinale = new ArrayList<CommandeDistri>();
		this.livraisonglobale = new ArrayList<CommandeDistri>();
	}

	public void addCatalogue(ITransformateur t, Catalogue c) {
		this.cat.put(t, c);
	}

	public void addTransformateur(ITransformateur t) {
		this.lestransfos.add(t);
	}

	public void addDistributeur(IDistributeur d) {
		this.lesdistris.add(d);
	}

	public void addProduit(Produit p) {
		this.lesproduits.add(p);
	}

	public List<ITransformateur> getLesTransfos() {
		return this.lestransfos;
	}

	public List<IDistributeur> getLesDitris() {
		return this.lesdistris;
	}

	public List<Produit> getLesProduits() {
		return this.lesproduits;
	}

	public HashMap<ITransformateur, Catalogue> getCatalogues() {
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


	public List<CommandeDistri> obtenirCommandeFinale(ITransformateur t, IDistributeur d) {
		List<CommandeDistri> temp = new ArrayList<CommandeDistri>();
		for (ITransformateur t0 : this.getLesTransfos()) {
			for(IDistributeur d0 : this.getLesDitris()) {
				for (int i=0; i<this.getCommandeFinale().size(); i++) {
					if (this.getCommandeFinale().get(i).getAcheteur() == d0 && this.getCommandeFinale().get(i).getVendeur() == t0) {
						temp.add(this.getCommandeFinale().get(i));
					}
				}
			}
		}
		return temp;
	}
	
	public List<CommandeDistri> obtenirLivraisonEffective(ITransformateur t, IDistributeur d) {
		List<CommandeDistri> temp = new ArrayList<CommandeDistri>();
		for (ITransformateur t0 : this.getLesTransfos()) {
			for(IDistributeur d0 : this.getLesDitris()) {
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

	public HashMap<ITransformateur, List<CommandeDistri>> RenvoiDistri(HashMap<IDistributeur, List<CommandeDistri> > hm) {
		HashMap<ITransformateur, List<CommandeDistri> > NegoTransfo = new HashMap<ITransformateur, List<CommandeDistri>>();
		for (ITransformateur t : this.getLesTransfos()) {
			NegoTransfo.put(t, new ArrayList<CommandeDistri>());	
		}
		for (IDistributeur d : this.getLesDitris()) {
			for (ITransformateur t : this.getLesTransfos()) {
				for (int i=0; i<NegoTransfo.get(d).size(); i++) {
					if (hm.get(d).get(i).getVendeur() == t) {
						NegoTransfo.get(t).add(hm.get(d).get(i));
					}
				}
			}
		}
		return NegoTransfo;

	}

	public HashMap<IDistributeur, List<CommandeDistri>> RenvoiTransfo(HashMap<ITransformateur, List<CommandeDistri> > hm) {
		HashMap<IDistributeur, List<CommandeDistri> > NegoDistri = new HashMap<IDistributeur, List<CommandeDistri>>();
		for (IDistributeur d : this.getLesDitris()) {
			NegoDistri.put(d, new ArrayList<CommandeDistri>());	
		}
		for (ITransformateur t : this.getLesTransfos()) {
			for (IDistributeur d : this.getLesDitris()) {
				for (int i=0; i<NegoDistri.get(t).size(); i++) {
					if (hm.get(t).get(i).getAcheteur() == d) {
						NegoDistri.get(d).add(hm.get(t).get(i));
					}
				}
			}
		}
		return NegoDistri;

	}

	public void next() {

		// Discussions concernant les livraisons � venir.

		HashMap<IDistributeur, List<CommandeDistri> > NegoDistri = new HashMap<IDistributeur, List<CommandeDistri>>();
		HashMap<ITransformateur, List<CommandeDistri> > NegoTransfo = new HashMap<ITransformateur, List<CommandeDistri>>();

		for (ITransformateur t : this.getLesTransfos()) {
			this.getCatalogues().put(t, t.getCatalogue());
		}

		for (IDistributeur d : this.getLesDitris()) {
			for (ITransformateur t : this.getLesTransfos()) {
				NegoDistri.put(d, d.demande(t, this.getCatalogues().get(t)));
			}
			HashMap<IDistributeur, List<CommandeDistri> > NegoDistriTemp = new HashMap<IDistributeur, List<CommandeDistri>>();
			while (marcheValide(NegoDistri) == false) {
				NegoTransfo = this.RenvoiDistri(NegoDistri);
				NegoDistriTemp = NegoDistri;
				for (ITransformateur t : this.getLesTransfos()) {
					NegoTransfo.replace(t, t.offre(NegoTransfo.get(t)));
				}
				NegoDistri = this.RenvoiTransfo(NegoTransfo);
				for (IDistributeur d1 : this.getLesDitris()) {
					NegoDistri.replace(d1, d1.contreDemande(NegoDistri.get(d1),NegoDistriTemp.get(d1)));
				}
			}
			List<CommandeDistri> commandefinale = new ArrayList<CommandeDistri>();
			for (ITransformateur t : this.getLesTransfos()) {
				commandefinale.addAll(NegoTransfo.get(t));
			}
			this.setCommandeFinale(commandefinale);


			// Livraisons effectives chez les distributeurs et paiements.
			
			List<CommandeDistri> livraisonglobale = new ArrayList<CommandeDistri>();
			for (ITransformateur t : this.getLesTransfos()) {
				for (IDistributeur d4 : this.getLesDitris()) {
					for (CommandeDistri cd : this.getHistoriqueCommande()) {
						List<CommandeDistri> temp = new ArrayList<CommandeDistri>();
						if (cd.getStepLivraison() == MondeV1.LE_MONDE.getStep() && t == cd.getVendeur() && d4 == cd.getAcheteur()) {
							temp.add(cd);
						}
						livraisonglobale.addAll(t.livraisonEffective(temp));
					}
				} 

				this.setLivraisonGlobale(livraisonglobale);
			}	

		}
	}

}
