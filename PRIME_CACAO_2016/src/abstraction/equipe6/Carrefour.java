package abstraction.equipe6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;

import abstraction.commun.MarcheConsommateurs;
import abstraction.commun.MarcheDistributeur;

import abstraction.commun.ITransformateurD;

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
		this.transformateurs = new ArrayList<ITransformateurD>();
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
			for (ITransformateur t : this.getTransformateursD()) {
				lesStocks.add(new Stock(p, 1000, t, new Indicateur("Stock de "+p.getNomProduit()+" de marque "+this.getNom(),this , 0.0)));
				lesAchats.add(new Achats(t, new Indicateur("Achats de "+p.getNomProduit()+" de marque "+t.getNom()+" de "+this.getNom(), this, 0.0), p));
				lesVentes.add(new Ventes(t, new Indicateur("Ventes de "+p.getNomProduit()+" de marque "+t.getNom()+" de "+this.getNom(), this, 0.0), p));
			}

		}
		this.setLesStocks(lesStocks);
		this.setDemandeAnnuel(demandeAnnuel);

	}

	//Accesseurs

	

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

	
	public void setBesoinStep(HashMap<Produit, Double> besoinStep) {
		this.besoinStep = besoinStep;
	}

	public ArrayList<ITransformateurD> getTransformateurs() {
		return transformateurs;
	}



	public void setTransformateurs(ArrayList<ITransformateurD> transformateurs) {

		this.transformateurs = transformateurs;
	}




	private ArrayList<ITransformateurD> transformateurs;
	
	public Carrefour(String nom, Monde monde, double prixachat, int i, double demandeannuel) {
		this.nom = nom;
		this.prixachat=prixachat;
		this.prixvente=i;
		this.achats = new Indicateur("Achats de "+this.nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+this.nom, this, 1000000.0);
    	Monde.LE_MONDE.ajouterIndicateur( this.achats );
    	Monde.LE_MONDE.ajouterIndicateur( this.solde );
    	this.transformateurs = new ArrayList<ITransformateurD>();
    	

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


	public Stock getStock(Produit p, ITransformateurD t) {
		for (int i=0; i<this.getLesStocks().size(); i++) {
			if (this.getLesStocks().get(i).getProduit() == p && this.getLesStocks().get(i).getMarque() == t) {
				return this.getLesStocks().get(i);
			}
		}
		return null;
	}

	public void setStock(Produit p, ITransformateurD t, Double quantite, boolean rajout) {
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

	public void setAchats(Produit p, ITransformateurD t, Double quantite) {
		for (int i=0; i<this.getLesAchats().size(); i++) {
			if (this.getLesAchats().get(i).getProduit() == p && this.getLesAchats().get(i).getMarque() == t) {
				this.lesAchats.get(i).setQuantite(quantite, this);
			}
		}
	}

	public void setVentes(Produit p, ITransformateurD t, Double quantite) {
		for (int i=0; i<this.getLesVentes().size(); i++) {
			if (this.getLesVentes().get(i).getProduit() == p && this.getLesVentes().get(i).getMarque() == t) {
				this.lesVentes.get(i).setQuantite(quantite,this);
			}
		}
	}

	public static Monde getLeMonde() {
		return LE_MONDE;
	}

	public void ajouterVendeur(ITransformateurD t) {
		this.transformateurs.add(t);

	}


	public double getDemande(ITransformateurD t) {
		this.setdemandePerStep(MondeV1.LE_MONDE.getStep()+3);
		if (t.equals(transformateurs.get(0))) {
			return this.demandeperstep*0.125;
		}
		if (t.equals(transformateurs.get(1))) {
			return this.demandeperstep*0.036;
		}
		else {
			return this.demandeperstep*0.839;
		}
	}
	
	public String getNom() {
		return this.nom;
	}
	public double getDemandeAnnuel() {
		return this.demandeannuel;
	}

	public List<ITransformateurD> comparateurPrixProduit(HashMap<ITransformateurD,Catalogue> hm, Produit p) {
		List<ITransformateurD> transfo = new ArrayList<ITransformateurD>();

		transfo.add(this.getTransformateurs().get(0));

		for (ITransformateurD t : this.getTransformateurs()) {
			for (ITransformateurD t2 : transfo) {
				if (hm.get(t2).getTarif(p).getPrixTonne()>hm.get(t).getTarif(p).getPrixTonne() && t!=t2) {
					transfo.add(transfo.indexOf(t2), t);
					break;

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



	
	public HashMap<Produit,Double> getBesoinStep() {
		return this.besoinStep;
	}

	public void setBesoinStep(HashMap<Produit,Double> bs, int step,Carrefour carrefour) {
		this.besoinStep = bs;
	    double de;
	    for (int i=0; i<3; i++){	
	         if (step%26 == 6 ) {
	        	 
	           de=(carrefour.getDemandeAnnuel()*0.06)*(1+(Math.random()*0.2 - 0.1));
			   this.besoinStep.put(produits.get(i),de); 
			
			 }else{
				if (step%26 == 25) {
					
					de =( 0.12*carrefour.getDemandeAnnuel())*(1+(Math.random()*0.2 - 0.1));
					this.besoinStep.put(produits.get(i), de);
					
			    }else{
				    de= (0.03416*carrefour.getDemandeAnnuel())*(1+(Math.random()*0.2 - 0.1));
				    this.besoinStep.put(produits.get(i),de);
	}
	}
    }		
    }		
	
	
	public HashMap<ITransformateurD,List<CommandeDistri>> commandeStep(HashMap<Produit,Double> besoinpro) {
		HashMap<ITransformateurD, Catalogue> cat = new HashMap<ITransformateurD,Catalogue>();
		HashMap<ITransformateurD,List<CommandeDistri>> commande = new HashMap<ITransformateurD,List<CommandeDistri>>();
		for (ITransformateurD t : this.getTransformateurs()){

			cat.put(t, t.getCatalogue());
			commande.put(t, new ArrayList<CommandeDistri>());
		}
		for (Produit p : this.getProduits()) {

			for (int i=0; i<this.getTransformateurs().size(); i++) {
				int le = this.getTransformateurs().size();
				ITransformateurD letransfo = this.comparateurPrixProduit(cat, p).get(i);

				double quantite = (6*(le-i)^2)/((le*(le-1)*(2*le-1)));
				commande.get(letransfo).add(new CommandeDistri(this, letransfo, p, quantite, letransfo.getCatalogue().getTarif(p).getPrixTonne(), MondeV1.LE_MONDE.getStep()+3, false));
			}
		}
		return commande;
	}



	public List<CommandeDistri> demande(ITransformateurD t, Catalogue c) {

		return this.commandeStep(this.getBesoinStep()).get(t);
	}


    //Méthode qui en cas de rupture de stock chez le tranformateur choisi, va commander les quantités de CACAO manquantes aux autres transfromateurs.
	public List<CommandeDistri> contreDemande(List<CommandeDistri> nouvelle,List<CommandeDistri> ancienne) {
		List <CommandeDistri> contreDemande= new ArrayList<CommandeDistri>();
		boolean verification = false;
		for (Produit p : this.getProduits()) {
			List<ITransformateur> enRupture = new ArrayList<ITransformateur>();
			double insatisfait = 0.0;

			List<ITransformateurD> avecstock = new ArrayList<ITransformateurD>();
			for (CommandeDistri c : cd) {
				if (c.getValidation()) {
					avecstock.add(c.getVendeur());
				}
				else {
					insatisfait+=c.getQuantite();

				}
			}

			for (ITransformateurD t : avecstock) {
				

			}
		}
		if (verification) {
			return contreDemande;
		}
		else {
			return nouvelle;
		}

	}

	//Méthode qui fixe le prix de vente avec bénéfice de 20% par rapport au prix d'achat au transformateur, 
	//pour chaque produit de chaque transformateur

	public void setPrix (HashMap<ITransformateurD,List<CommandeDistri>> CommandeEffective) {
		for (ITransformateurD t : this.getTransformateurs()) {
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

		for (ITransformateurD t : this.getTransformateurs()) {
			List<CommandeDistri> temp = new ArrayList<CommandeDistri>();
			List<CommandeDistri> temp2 = new ArrayList<CommandeDistri>();
			temp.addAll(this.getHistoCommande());
			// System.out.println("Les transfo -->"+this.getTransformateurs());
			// System.out.println("commande finale pass� � "+t+" -->"+maDi.obtenirCommandeFinale(t, this));
			temp.addAll(maDi.obtenirCommandeFinale(t, this));
			temp2.addAll(this.getHistoLivraison());
			temp2.addAll(maDi.obtenirLivraisonEffective(t, this));
			this.setHistoCommande(temp);
			this.setHistoLivraison(temp2);
			HashMap<Produit, Double> ventesStep = new HashMap<Produit, Double>();
			// ventesStep = maCo.getVenteDistri(this);
			for (Produit p : this.getProduits()) {
				System.out.println("histo livraison --> "+this.getHistoLivraison());
				for (CommandeDistri d : this.getHistoLivraison()) {
					if (d.getAcheteur() == this && d.getStepLivraison() == LE_MONDE.getStep() && d.getVendeur() == t && d.getProduit()==p) {
						this.setStock(p, t, d.getQuantite(), true);
						System.out.println("Quantite de : "+p+" --> "+d.getQuantite());
						this.setSolde(this.getSolde().getValeur() - d.getPrix());
						this.setAchats(p, t, d.getQuantite());
						/*if (this.getStock(p, t).getQuantite().getValeur() < ventesStep.get(p)/3) {
							this.setVentes(p, t, this.getStock(p, t).getQuantite().getValeur());
							this.setStock(p, t, this.getStock(p, t).getQuantite().getValeur(), false);
							this.setSolde(this.getSolde().getValeur() + this.getStock(p, t).getQuantite().getValeur()*this.getPrixVente(p));
						}
						else {
							this.setVentes(p, t, ventesStep.get(p)/3);
							this.setStock(p, t,ventesStep.get(p)/3, false);
							this.setSolde(this.getSolde().getValeur() + ventesStep.get(p)/3*this.getPrixVente(p));
						}*/
					}
				}
			}
			System.out.println("Stock de Carrefour de "+this.getLesStocks().get(1).getMarque()+ "-->" +this.getLesStocks().get(1).getQuantite().getValeur());

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


	@Override
	public List<CommandeDistri> Demande(ITransformateurD t, Catalogue c) {
		// TODO Auto-generated method stub
		return null;
	}


	/*@Override
	public List<CommandeDistri> ContreDemande(List<CommandeDistri> nouvelle, List<CommandeDistri> ancienne) {
		// TODO Auto-generated method stub
		return null;
	}*/






}







