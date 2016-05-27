package abstraction.equipe6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.commun.MondeV1;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Carrefour implements Acteur,IDistributeur {

	private static final Monde LE_MONDE = null;
	private String nom;
	private List<PrixVente> prixvente;
	private List<Indicateur> solde;
	private List<Indicateur> achats;
	private HashMap<Produit,Double> demandeannuel;
	private double fraisdedistri;
	private List<Produit> produits;
	private HashMap<Produit,Double> besoinStep;
	private ArrayList<ITransformateur> transformateurs;


	
	public void ajouterVendeur(ITransformateur t) {
		this.transformateurs.add(t);
	}


	// Reglage de la quantite a acheter en fonction du transformateur (12.5% Nestle, 3.6% Lindt et 83.9% Others)

	public String getNom() {
		return this.nom;
	}


	public List<ITransformateur> comparateurPrixProduit(HashMap<ITransformateur,Catalogue> hm, Produit p) {
		List<ITransformateur> transfo = new ArrayList<ITransformateur>();
		transfo.add(this.getTransformateurs().get(0));
		for (ITransformateur t : this.getTransformateurs()) {
			for (ITransformateur t2 : transfo) {
				if (hm.get(t2).getTarif(p).getPrixTonne()>hm.get(t).getTarif(p).getPrixTonne() && t!=t2) {
					transfo.add(transfo.indexOf(t2), t);
					break;
				}
			}
		}
		return transfo;
	}

	public List<PrixVente> getPrixvente() {
		return prixvente;
	}


	public void setPrixvente(List<PrixVente> prixvente) {
		this.prixvente = prixvente;
	}


	public List<Indicateur> getSolde() {
		return solde;
	}


	public void setSolde(List<Indicateur> solde) {
		this.solde = solde;
	}


	public List<Indicateur> getAchats() {
		return achats;
	}


	public void setAchats(List<Indicateur> achats) {
		this.achats = achats;
	}


	public HashMap<Produit, Double> getDemandeAnnuel() {
		return demandeannuel;
	}


	public void setDemandeannuel(HashMap<Produit, Double> demandeannuel) {
		this.demandeannuel = demandeannuel;
	}


	public double getFraisdedistri() {
		return fraisdedistri;
	}


	public void setFraisdedistri(double fraisdedistri) {
		this.fraisdedistri = fraisdedistri;
	}


	public List<Produit> getProduits() {
		return produits;
	}


	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}


	public HashMap<Produit, Double> getBesoinStep() {
		return besoinStep;
	}


	public void setBesoinStep(HashMap<Produit, Double> besoinStep) {
		this.besoinStep = besoinStep;
	}


	public ArrayList<ITransformateur> getTransformateurs() {
		return transformateurs;
	}


	public void setTransformateurs(ArrayList<ITransformateur> transformateurs) {
		this.transformateurs = transformateurs;
	}


	public static Monde getLeMonde() {
		return LE_MONDE;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public void setBesoinStep(int step) {
		double besoin;
		for (int i=0; i<this.getProduits().size(); i++){	
			if (step%26 == 6 ) {
				besoin = (this.getDemandeAnnuel().get(this.getProduits().get(i))*0.06)*(1+(Math.random()*0.2 - 0.1));
				this.besoinStep.put(produits.get(i),besoin); 
			}
			else {
				if (step%26 == 25) {
					besoin = (0.12*this.getDemandeAnnuel().get(this.getProduits().get(i))*(1+(Math.random()*0.2 - 0.1)));
					this.besoinStep.put(produits.get(i), besoin);
				}
				else {
					besoin = (0.03416*this.getDemandeAnnuel().get(this.getProduits().get(i))*(1+(Math.random()*0.2 - 0.1)));
					this.besoinStep.put(produits.get(i),besoin);
				}
			}
		}		
	}		


	public HashMap<ITransformateur,List<CommandeDistri>> commandeStep(HashMap<Produit,Double> besoinpro) {
		HashMap<ITransformateur, Catalogue> cat = new HashMap<ITransformateur,Catalogue>();
		HashMap<ITransformateur,List<CommandeDistri>> commande = new HashMap<ITransformateur,List<CommandeDistri>>();
		for (ITransformateur t : this.getTransformateurs()){
			cat.put(t, t.getCatalogue());
			commande.put(t, new ArrayList<CommandeDistri>());
		}
		for (Produit p : this.getProduits()) {
			for (int i=0; i<this.getTransformateurs().size(); i++) {
				int le = this.getTransformateurs().size();
				ITransformateur letransfo = this.comparateurPrixProduit(cat, p).get(i);
				double quantite = (6*(le-i)^2)/((le*(le-1)*(2*le-1)));
				commande.get(letransfo).add(new CommandeDistri(this, letransfo, p, quantite, letransfo.getCatalogue().getTarif(p).getPrixTonne(), MondeV1.LE_MONDE.getStep()+3, false));
			}
		}
		return commande;
	}

	public List<CommandeDistri> demande(ITransformateur t, Catalogue c) {
		return this.commandeStep(this.getBesoinStep()).get(t);
	}

	public List<CommandeDistri> contreDemande(List<CommandeDistri> nouvelle,List<CommandeDistri> ancienne) {
		List<CommandeDistri> contreDemande = new ArrayList<CommandeDistri>();
		for (Produit p : this.getProduits()) {
			List<ITransformateur> enRupture = new ArrayList<ITransformateur>();
			double insatisfait = 0.0;
			for (CommandeDistri cd : nouvelle) {
				for (CommandeDistri cd2 : ancienne) {
					if (cd.getAcheteur() == cd2.getAcheteur() && cd.getPrixTonne() == cd2.getPrixTonne() && cd.getStepLivraison() == cd2.getStepLivraison() && cd.getVendeur() == cd2.getVendeur() && cd.getQuantite() != cd2.getQuantite()) {
						insatisfait+= cd2.getQuantite() - cd.getQuantite();
						contreDemande.add(cd);
					}
				}
			}
			for (ITransformateur t : this.getTransformateurs()) {
				if (enRupture.contains(t) == false) {
					double quantite = insatisfait/enRupture.size();
					double prixTonne = 0.0;
					for (CommandeDistri cd : nouvelle) {
						if (cd.getAcheteur() == this && cd.getVendeur() == t && cd.getProduit() == p) {
							quantite+= cd.getQuantite();
							prixTonne = cd.getPrixTonne();
						}
					}
					contreDemande.add(new CommandeDistri(this, t, p, quantite, prixTonne, this.getLeMonde().getStep(), false ));
					
				}
			}
			
		}
		return contreDemande;
	}
	public Double getFraisDeDistribution(){
		return this.fraisdedistri;
	}
	public void setFraisDeDistribution(double solde){
		solde=solde*1.1;
	}


	@Override
	public void next() {
		// TODO Auto-generated method stub

	}


	@Override
	public HashMap<Produit, Double> getPrix() {
		// TODO Auto-generated method stub
		return null;
	}



	/*public void next() {
		setdemandePerStep( MondeV1.LE_MONDE.getStep());
		setFraisdeDistri();
		for (ITransformateur t : this.transformateurs) {
			double q = this.getDemande(t);
			this.solde.setValeur(this, this.solde.getValeur()-q*this.getPrix());
		}
		this.achats.setValeur(this, this.demandeperstep);
		this.solde.setValeur(this,this.solde.getValeur()+this.demandeperstep*this.prixvente
										-this.fraisdedistri); 

<<<<<<< HEAD

		// Solde = Solde precedent + Ventes - Achats - Frais de Distribution
=======
		// Solde = Solde pr�c�dent + Ventes - Achats - Frais de Distribution
>>>>>>> refs/remotes/choose_remote_name/master
	}
	 */


}