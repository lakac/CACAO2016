package abstraction.commun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.fourni.Acteur;


public class MarcheDistributeur implements Acteur {
	
		private List<ITransformateur> lestransfos;
		private List<IDistributeur> lesdistris;
		private static double COURS = 15.0;
		private List<Produit> lesproduits;
		private HashMap<ITransformateur,Catalogue> cat;
		
	public MarcheDistributeur()	 {
		this.lestransfos = new ArrayList<ITransformateur>() ;
		this.lesdistris = new ArrayList<IDistributeur>() ;
		this.lesproduits = new ArrayList<Produit>() ;
		this.cat = new HashMap<ITransformateur, Catalogue>() ;
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
		

	public String getNom() {
		return "marché du chocolat";
	}
	
	public boolean distriValide( List<CommandeDistri> cd) {
		for (CommandeDistri d : cd ) {
			if (d.getValidation == false) {
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
		
		// Discussions concernant les livraisons à venir.
		
		HashMap<IDistributeur, List<CommandeDistri> > NegoDistri = new HashMap<IDistributeur, List<CommandeDistri>>();
		HashMap<ITransformateur, List<CommandeDistri> > NegoTransfo = new HashMap<ITransformateur, List<CommandeDistri>>();
		
		for (ITransformateur t : this.getLesTransfos()) {
			this.getCatalogues().put(t, t.getCatalogue());
		}
		
		for (IDistributeur d : this.getLesDitris()) {
			NegoDistri.put(d, d.demandeDistri(this.getCatalogues()));
		}
		
		while (marcheValide(NegoDistri) == false) {
			NegoTransfo = this.RenvoiDistri(NegoDistri);
			for (ITransformateur t : this.getLesTransfos()) {
				NegoTransfo.replace(t, t.offre(NegoTransfo.get(t)));
						}
			NegoDistri = this.RenvoiTransfo(NegoTransfo);
			for (IDistributeur d : this.getLesDitris()) {
				NegoDistri.replace(d, d.contreDemandeDistri(NegoDistri.get(d)));
						}
				}
		
		// Livraisons effectives chez les distributeurs et paiements.
		
		}	

}
