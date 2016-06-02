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
	private Indicateur solde;
	private List<Ventes> lesVentes;
	private List<Achats> lesAchats;
	private HashMap<Produit,Double> demandeAnnuel;
	private double fraisdedistri;
	private List<Produit> produits;
	private HashMap<Produit,Double> besoinStep;
	private ArrayList<ITransformateur> transformateurs;
	private List<CommandeDistri> histoCommande;
	private List<CommandeDistri> histoLivraison;
	private List<Stock> lesStocks;

	public Carrefour(String nom, List<Produit> produits) {
		super();
		this.nom = nom;
		this.prixvente = new ArrayList<PrixVente>();
		this.solde = new Indicateur(this.getNom(), this, 1000000);
		this.demandeAnnuel = new HashMap<Produit,Double>();
		this.fraisdedistri = 0.0;
		this.produits = produits;
		this.besoinStep = new HashMap<Produit,Double>();
		this.transformateurs = new ArrayList<ITransformateur>();
		this.histoCommande = new ArrayList<CommandeDistri>();
		this.histoLivraison = new ArrayList<CommandeDistri>();
		this.lesStocks = new ArrayList<Stock>();

	}

	public void creer() {
		List<Stock> lesStocks = new ArrayList<Stock>();
		List<Achats> lesAchats = new ArrayList<Achats>();
		HashMap<Produit,Double> demandeAnnuel = new HashMap<Produit,Double>();
		for (Produit p : this.getProduits()) {
			demandeAnnuel.put(p, 2500.0);
			for (ITransformateur t : this.getTransformateurs()) {
				lesStocks.add(new Stock(p, 1000, t, new Indicateur("Stock de "+p.getNomProduit()+" de marque "+this.getNom(),this , 0.0)));
			//	lesAchats.add(new Achats(t, new Indicateur("Achats de "+p.getNomProduit()+" de marque "+t.getNom()+" de "+this.getNom(), this, 0.0), p));
			}

		}
		this.setLesStocks(lesStocks);
		this.setDemandeAnnuel(demandeAnnuel);

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<PrixVente> getPrixvente() {
		return prixvente;
	}

	public void setPrixvente(List<PrixVente> prixvente) {
		this.prixvente = prixvente;
	}

	public Indicateur getSolde() {
		return solde;
	}

	public void setSolde(Double quantite) {
		this.solde.setValeur(this,quantite);
	}

	public List<Ventes> getLesVentes() {
		return lesVentes;
	}

	public void setLesVentes(List<Ventes> lesVentes) {
		this.lesVentes = lesVentes;
	}


	public List<Achats> getLesAchats() {
		return lesAchats;
	}

	public void setLesAchats(List<Achats> lesAchats) {
		this.lesAchats = lesAchats;
	}

	public HashMap<Produit, Double> getDemandeAnnuel() {
		return demandeAnnuel;
	}

	public void setDemandeAnnuel(HashMap<Produit, Double> demandeannuel) {
		this.demandeAnnuel = demandeannuel;
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

	public List<CommandeDistri> getHistoCommande() {
		return histoCommande;
	}

	public void setHistoCommande(List<CommandeDistri> histoCommande) {
		this.histoCommande = histoCommande;
	}

	public List<CommandeDistri> getHistoLivraison() {
		return histoLivraison;
	}

	public void setHistoLivraison(List<CommandeDistri> histoLivraison) {
		this.histoLivraison = histoLivraison;
	}

	public List<Stock> getLesStocks() {
		return lesStocks;
	}

	public void setLesStocks(List<Stock> lesStocks) {
		this.lesStocks = lesStocks;
	}

	public Stock getStock(Produit p, ITransformateur t) {
		for (int i=0; i<this.getLesStocks().size(); i++) {
			if (this.getLesStocks().get(i).getProduit() == p && this.getLesStocks().get(i).getMarque() == t) {
				return this.getLesStocks().get(i);
			}
		}
		return null;
	}

	public void setStock(Produit p, ITransformateur t, Double quantite, boolean rajout) {
		for (int i=0; i<this.getLesStocks().size(); i++) {
			if (this.getLesStocks().get(i).getProduit() == p && this.getLesStocks().get(i).getMarque() == t) {
				if (rajout) {
					this.lesStocks.get(i).ajout(quantite, this);
				}
				else {
					this.lesStocks.get(i).retrait(quantite, this);
				}
			}
		}
	}

	public void setAchats(Produit p, ITransformateur t, Double quantite) {
		for (int i=0; i<this.getLesAchats().size(); i++) {
			if (this.getLesAchats().get(i).getProduit() == p && this.getLesAchats().get(i).getMarque() == t) {
				this.lesAchats.get(i).setQuantite(quantite, this);
			}
		}
	}

	public static Monde getLeMonde() {
		return LE_MONDE;
	}

	public void ajouterVendeur(ITransformateur t) {
		this.transformateurs.add(t);
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

	public void next() {
		this.setBesoinStep(this.LE_MONDE.getStep()+1);
		for (ITransformateur t : this.getTransformateurs()) {
		//	this.setHistoCommande(this.getHistoCommande().addAll(LE_MARCHE_DISTRIBUTEUR.obtenirCommandeFinale(t, this)));
		//	this.setHistoLivraison(this.getHistoLivraison().addAll(LE_MARCHE_DISTRIBUTEUR.obtenirLivraisonEffective(t, this)));
			for (Produit p : this.getProduits()) {
				for (CommandeDistri d : this.getHistoLivraison()) {
					if (d.getAcheteur() == t && d.getStepLivraison() == this.getLeMonde().getStep() && d.getVendeur() == this && d.getProduit()==p) {
						this.setStock(p, t, d.getQuantite(), true);
						this.setSolde(this.getSolde().getValeur() - d.getPrix());
						this.setAchats(p, t, d.getQuantite());
					}
				}
			}
		}

	}

	@Override
	public HashMap<Produit, Double> getPrix() {
		// TODO Auto-generated method stub
		return null;
	}

}