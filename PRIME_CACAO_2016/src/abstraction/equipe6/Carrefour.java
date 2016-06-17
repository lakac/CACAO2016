package abstraction.equipe6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.commun.MarcheConsommateurs;
import abstraction.commun.MarcheDistributeur;
import abstraction.commun.MondeV1;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Carrefour implements Acteur,IDistributeur {

	private static final Monde LE_MONDE = null;
	private MarcheDistributeur maDi;
	private MarcheConsommateurs maCo;
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
		this.maDi = new MarcheDistributeur();
	}
	//Méthode qui crée et initialise trois listes de taille neuf (pour chaque produit et chaque tranformateur) qui gèrent respectivement nos stocks, nos achats 
	//au transformateur et nos ventes aux clients. La dernière liste créée (demandeAnnuel) initialise la demande dans chaque produit des client

	public void creer() {
		List<Stock> lesStocks = new ArrayList<Stock>();
		List<Achats> lesAchats = new ArrayList<Achats>();
		List<Ventes> lesVentes = new ArrayList<Ventes>();
		HashMap<Produit,Double> demandeAnnuel = new HashMap<Produit,Double>();
		for (Produit p : this.getProduits()) {
			demandeAnnuel.put(p, 2500.0);
			besoinStep.put(p, 0.0);
			for (ITransformateur t : this.getTransformateurs()) {
				lesStocks.add(new Stock(p, 1000, t, new Indicateur("Stock de "+p.getNomProduit()+" de marque "+this.getNom(),this , 0.0)));
				lesAchats.add(new Achats(t, new Indicateur("Achats de "+p.getNomProduit()+" de marque "+t.getNom()+" de "+this.getNom(), this, 0.0), p));
				lesVentes.add(new Ventes(t, new Indicateur("Ventes de "+p.getNomProduit()+" de marque "+t.getNom()+" de "+this.getNom(), this, 0.0), p));
			}

		}
		this.setLesStocks(lesStocks);
		this.setDemandeAnnuel(demandeAnnuel);

	}
	
	//Accesseurs

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
	
	public MarcheConsommateurs getMaCo() {
		return maCo;
	}

	public void setMaCo(MarcheConsommateurs maCo) {
		this.maCo = maCo;
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

	public MarcheDistributeur getMaDi() {
		return maDi;
	}

	public void setMaDi(MarcheDistributeur maDi) {
		this.maDi = maDi;
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

	public void setVentes(Produit p, ITransformateur t, Double quantite) {
		for (int i=0; i<this.getLesVentes().size(); i++) {
			if (this.getLesVentes().get(i).getProduit() == p && this.getLesVentes().get(i).getMarque() == t) {
				this.lesVentes.get(i).setQuantite(quantite,this);
			}
		}
	}

	public static Monde getLeMonde() {
		return LE_MONDE;
	}

	public void ajouterVendeur(ITransformateur t) {
		this.transformateurs.add(t);

	}

   //Fonction qui compare les prix des deux tranformateurs 
	public List<ITransformateur> comparateurPrixProduit(HashMap<ITransformateur,Catalogue> hm, Produit p) {
		List<ITransformateur> transfo = new ArrayList<ITransformateur>();
		transfo.add(this.getTransformateurs().get(0));
		for (ITransformateur t : this.getTransformateurs()) {
			for (ITransformateur t2 : transfo) {
				int temp = 0;
				while (temp == 0) {
					if (hm.get(t2).getTarif(p).getPrixTonne()>hm.get(t).getTarif(p).getPrixTonne() && t!=t2) {
						transfo.add(transfo.indexOf(t2), t);
						temp++;
					}
				}
			}
		}
		return transfo;
	}
	
    //Affiche le demande par step pour chaque produit (la demande est supposée constante toute l'année, sauf à Pâques et Noël
	//On rajoute une partie random dans la demande de +/-10% de la demande initiale
	public void setBesoinStep(int step) {
		double besoin;
		for (int i=0; i<this.getProduits().size(); i++){	
			if (step%26 == 6 ) {
				besoin = (this.getDemandeAnnuel().get(this.getProduits().get(i))*0.06)*(1+(Math.random()*0.2 - 0.1));
				this.besoinStep.replace(this.getProduits().get(i), besoin); 
			}
			else {
				if (step%26 == 25) {
					besoin = (0.12*this.getDemandeAnnuel().get(this.getProduits().get(i))*(1+(Math.random()*0.2 - 0.1)));
					this.besoinStep.replace(this.getProduits().get(i), besoin);
				}
				else {
					besoin = (0.03416*this.getDemandeAnnuel().get(this.getProduits().get(i))*(1+(Math.random()*0.2 - 0.1)));
					this.besoinStep.replace(this.getProduits().get(i), besoin);
				}
			}
		}		
	}		

    //Fonction qui retourne un HahsMap fournissant des informations sur la commande chaque produit entre chaque Transformateur.
	public HashMap<ITransformateur,List<CommandeDistri>> commandeStep(HashMap<Produit,Double> besoinpro) {
		HashMap<ITransformateur, Catalogue> cat = new HashMap<ITransformateur,Catalogue>();
		HashMap<ITransformateur,List<CommandeDistri>> commande = new HashMap<ITransformateur,List<CommandeDistri>>();
		for (ITransformateur t : this.getTransformateurs()){
			cat.put(t, t.getCatalogue());
			commande.put(t, new ArrayList<CommandeDistri>());
		}
		for (Produit p : this.getProduits()) {
			int le = this.getTransformateurs().size();
			for (int i=0; i<le; i++) {
				ITransformateur letransfo = this.getTransformateurs().get(i); // Modif pour que �a marche, plus de comparateur de prix
				double quantite = (6*(le-i)^2)/((le*(le-1)*(2*le-1)));
				commande.get(letransfo).add(new CommandeDistri(this, letransfo, p, quantite, letransfo.getCatalogue().getTarif(p).getPrixTonne(), MondeV1.LE_MONDE.getStep()+3, false));
			}
		}
		return commande;
	}


	public List<CommandeDistri> demande(ITransformateur t, Catalogue c) {
		return this.commandeStep(this.getBesoinStep()).get(t);
	}


    //Méthode qui en cas de rupture de stock chez le tranformateur choisi, va commander les quantités de CACAO manquantes aux autres transfromateurs.
	public List<CommandeDistri> contreDemande(List<CommandeDistri> nouvelle,List<CommandeDistri> ancienne) {
		List <CommandeDistri> contreDemande= new ArrayList<CommandeDistri>();
		for (Produit p : this.getProduits()) {
			List<ITransformateur> enRupture = new ArrayList<ITransformateur>();
			double insatisfait = 0.0;
			for (CommandeDistri cd : nouvelle) {
				for (CommandeDistri cd2 : ancienne) {
					if (cd.getAcheteur() == cd2.getAcheteur() && cd.getPrixTonne() == cd2.getPrixTonne() && cd.getStepLivraison() == cd2.getStepLivraison() && cd.getVendeur() == cd2.getVendeur() && cd.getQuantite() != cd2.getQuantite()) {
						insatisfait+= cd2.getQuantite() - cd.getQuantite();
						contreDemande.add(cd);
						enRupture.add(cd.getVendeur());
					}
				}
			}
			for (ITransformateur t : this.getTransformateurs()) {
				if (enRupture.contains(t) == false && enRupture.size()!=0) {
					double quantite = insatisfait/enRupture.size();
					double prixTonne = 0.0;
					for (CommandeDistri cd3 : nouvelle) {
						if (cd3.getAcheteur() == this && cd3.getVendeur() == t && cd3.getProduit() == p) {
							quantite+= cd3.getQuantite();
							prixTonne = cd3.getPrixTonne();
						}
					}
					System.out.println("Le transfo --> "+t+"\n le produit -->"+p+"\n la quantite --> "+quantite+"\n le prixTonne --> "+prixTonne);
					contreDemande.add(new CommandeDistri(this, t, p, quantite, prixTonne, LE_MONDE.getStep(), false ));
				}
			}
		}
		return contreDemande;
	}
	
	//Méthode qui fixe le prix de vente avec bénéfice de 20% par rapport au prix d'achat au transformateur, 
	//pour chaque produit de chaque transformateur
	
	public void setPrix (HashMap<ITransformateur,List<CommandeDistri>> CommandeEffective) {
		for (ITransformateur t : this.getTransformateurs()) {
			for (CommandeDistri c : CommandeEffective.get(t) ) {
				for (PrixVente p : this.getPrixvente()){
					if (p.getTransformateur()==t && c.getProduit()==p.getProduit()){
						p.setPrix(1.2*c.getPrixTonne());
					}
				}
			}
		}    
	}
	
	public void next() {

		for (ITransformateur t : this.getTransformateurs()) {
			List<CommandeDistri> temp = new ArrayList<CommandeDistri>();
			List<CommandeDistri> temp2 = new ArrayList<CommandeDistri>();
			temp.addAll(this.getHistoCommande());
			System.out.println("Les transfo -->"+this.getTransformateurs());
			System.out.println("commande finale pass� � "+t+" -->"+maDi.obtenirCommandeFinale(t, this));
			temp.addAll(maDi.obtenirCommandeFinale(t, this));
			temp2.addAll(this.getHistoLivraison());
			temp2.addAll(maDi.obtenirLivraisonEffective(t, this));
			this.setHistoCommande(temp);
			this.setHistoLivraison(temp2);
			HashMap<Produit, Double> ventesStep = new HashMap<Produit, Double>();
			ventesStep = maCo.getVenteDistri(this);
			for (Produit p : this.getProduits()) {
				for (CommandeDistri d : this.getHistoLivraison()) {
					if (d.getAcheteur() == t && d.getStepLivraison() == LE_MONDE.getStep() && d.getVendeur() == this && d.getProduit()==p) {
						this.setStock(p, t, d.getQuantite(), true);
						this.setSolde(this.getSolde().getValeur() - d.getPrix());
						this.setAchats(p, t, d.getQuantite());
						if (this.getStock(p, t).getQuantite().getValeur() < ventesStep.get(p)/3) {
							this.setVentes(p, t, this.getStock(p, t).getQuantite().getValeur());
							this.setStock(p, t, this.getStock(p, t).getQuantite().getValeur(), false);
							this.setSolde(this.getSolde().getValeur() + this.getStock(p, t).getQuantite().getValeur()*this.getPrixVente(p));
						}
						else {
							this.setVentes(p, t, ventesStep.get(p)/3);
							this.setStock(p, t,ventesStep.get(p)/3, false);
							this.setSolde(this.getSolde().getValeur() + ventesStep.get(p)/3*this.getPrixVente(p));
						}
					}
				}
			}

		}

	}


	@Override
	public Double getPrixVente(Produit p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getStock(Produit p) {
		// TODO Auto-generated method stub
		return null;
	}
}







