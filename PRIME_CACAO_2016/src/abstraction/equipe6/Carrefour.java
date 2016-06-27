package abstraction.equipe6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.MarcheConsommateur;
import abstraction.commun.MarcheDistributeur;
import abstraction.commun.ITransformateurD;
import abstraction.commun.MarcheCons;
import abstraction.commun.MondeV1;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Carrefour implements Acteur,IDistributeur {

	private MarcheDistributeur maDi;
	private MarcheCons maCo;
	private String nom;
	private List<PrixVente> prixvente;
	private Indicateur solde;
	private List<Flux> lesVentes;
	private List<Flux> lesAchats;
	private HashMap<Produit,Double> demandeAnnuel;
	private double fraisdedistri;
	private List<Produit> produits;

	private HashMap<Produit,Double> besoinStep;


	private ArrayList<ITransformateurD> transformateurs;

	private ArrayList<IDistributeur> distributeurs;

	


	private List<CommandeDistri> histoCommande;
	private List<CommandeDistri> histoLivraison;
	private List<Stock> lesStocks;
	
	//Ajout 26/06 par l'�quipe 2 pour les tests
	public Carrefour() {
	}

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
		List<Flux> lesAchats = new ArrayList<Flux>();
		List<Flux> lesVentes = new ArrayList<Flux>();
		HashMap<Produit,Double> demandeAnnuel = new HashMap<Produit,Double>();
		for (Produit p : this.getProduits()) {
			if (p.getNomProduit()=="60%") {
				demandeAnnuel.put(p, 20000.0);
			}
			else {
				demandeAnnuel.put(p, 15000.0);
			}
			besoinStep.put(p, 0.0);
			for (ITransformateurD t : this.getTransformateurs()) {

				lesStocks.add(new Stock(p, 2000, t, new Indicateur("Stock de "+p.getNomProduit()+" de marque "+t.getNom()+" de "+this.getNom(),this , 300.0)));
				lesAchats.add(new Achats(t, new Indicateur("Achats de "+p.getNomProduit()+" de marque "+t.getNom()+" de "+this.getNom(), this, 300.0), p));

				lesVentes.add(new Ventes(t, new Indicateur("Ventes de "+p.getNomProduit()+" de marque "+t.getNom()+" de "+this.getNom(), this, 300.0), p));
			}
		}
		this.setLesStocks(lesStocks);
		this.demandeAnnuel= demandeAnnuel;
		this.lesAchats = lesAchats;
		this.lesStocks = lesStocks;
		this.lesVentes = lesVentes;
		for (int i = 0; i<lesAchats.size(); i++) {
			Monde.LE_MONDE.ajouterIndicateur(this.lesAchats.get(i).getQuantite());
		}
		for (int i = 0; i<lesVentes.size(); i++) {
			Monde.LE_MONDE.ajouterIndicateur(this.lesVentes.get(i).getQuantite());
		}
		for (int i = 0; i<lesStocks.size(); i++) {
			Monde.LE_MONDE.ajouterIndicateur(this.lesStocks.get(i).getQuantite());
		}
		Monde.LE_MONDE.ajouterIndicateur(this.solde);
		this.setBesoinStep(Monde.LE_MONDE.getStep());
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

	public MarcheCons getMaCo() {
		return maCo;
	}

	public void setMaCo(MarcheCons maCo) {
		this.maCo = maCo;
	}

	public Indicateur getSolde() {
		return solde;
	}

	public void setSolde(Double quantite) {
		this.solde.setValeur(this,quantite);
	}

	public List<Flux> getLesVentes() {
		return lesVentes;
	}

/*	public void setLesVentes(List<Ventes> lesVentes) {
		for (ITransformateurD t : this.getTransformateurs()) {
			for (Produit p : this.getProduits()) {
				for (int i=0; i<lesVentes.size(); i++) {
					if (t.equals(lesVentes.get(i).getMarque()) && p.equals(lesVentes.get(i).getProduit())) {
						double vente = this.get
					}
				}
			}
		}
	} */

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

	public Double getStock(Produit p) {
		return this.getLesStocks().get(this.getLesStocks().indexOf(p)).getQuantite().getValeur();
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
	
	public ArrayList<IDistributeur> getDistributeurs() {
		return distributeurs;
	}
	
	public void setDistributeurs(ArrayList<IDistributeur> distributeurs) {
		this.distributeurs = distributeurs;
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
	public void ajouterVendeur(ITransformateurD t) {
		this.transformateurs.add(t);

	}
	
	public String getNom() {
		return this.nom;
	}
	
	public HashMap<Produit,Double> getBesoinStep() {
		return this.besoinStep;
	}
	
	public HashMap<Produit, Double> getDemandeAnnuel() {
		return demandeAnnuel;
	}


	public List<CommandeDistri> getHistoCommande() {
		return histoCommande;
	}

	public void setSolde(Indicateur solde) {
		this.solde = solde;
	}


	public List<CommandeDistri> getHistoCommande() {
		return histoCommande;
	}

	public void setSolde(Indicateur solde) {
		this.solde = solde;
	}

	/*public Stock getStock(Produit p, ITransformateurD t) {
		/*for (int i=0; i<this.getLesStocks().size(); i++) {
			if (this.getLesStocks().get(i).getProduit() == p && this.getLesStocks().get(i).getMarque() == t) {
				return this.getLesStocks().get(i);
			}
		}
		return null;
	}*/

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
		for (Produit p : this.getProduits()) {	
			if (step%26 == 6 ) {
				System.out.println("La demande annuel  :"+ this.getDemandeAnnuel().get(p));
				besoin = (this.getDemandeAnnuel().get(p)*0.06)*(1+(Math.random()*0.2 - 0.1));
				this.besoinStep.replace(p, besoin); 
				System.out.println("Le besoin step de "+p+" --> "+this.besoinStep.get(p));
			}
			
			else {
				if (step%26 == 25) {
					besoin = (0.12*this.getDemandeAnnuel().get(p)*(1+(Math.random()*0.2 - 0.1)));
					this.besoinStep.replace(p, besoin);
					System.out.println("Le besoin step de "+p+" --> "+this.besoinStep.get(p));
				}
				else {
					System.out.println("La demande annuel  :"+ this.getDemandeAnnuel().get(p));
					besoin = (0.03416*this.getDemandeAnnuel().get(p)*(1+(Math.random()*0.2 - 0.1)));
					this.besoinStep.replace(p, besoin);
					System.out.println("Le besoin step de "+p+" --> "+this.besoinStep.get(p));
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

			double dem_min = this.getBesoinStep().get(p)*0.50/this.getTransformateurs().size();

			int le = this.getTransformateurs().size();
			for (int i=0; i<le; i++) {
				ITransformateurD letransfo = this.getTransformateurs().get(i); // Modif pour que �a marche, plus de comparateur de prix
				double quantite = (le-i+1)/((le+1)*(le+1))*0.50*this.getBesoinStep().get(p);

				System.out.println("La quantit� de la demande --> "+this.getBesoinStep().get(p));
				commande.get(letransfo).add(new CommandeDistri(this, letransfo, p, dem_min + quantite, letransfo.getCatalogue().getTarif(p).getPrixTonne(), MondeV1.LE_MONDE.getStep()+3, false));

				System.out.println(p);
				System.out.println(this.getBesoinStep().get(p));
				System.out.println(letransfo.getNom());
				System.out.println(letransfo.getCatalogue());
				commande.get(letransfo).add(new CommandeDistri(this, letransfo, p, this.getBesoinStep().get(p), letransfo.getCatalogue().getTarif(p).getPrixTonne(), Monde.LE_MONDE.getStep()+3, false));


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
			List<ITransformateurD> enRupture = new ArrayList<ITransformateurD>();
			double insatisfait = 0.0;
			List<ITransformateurD> avecstock = new ArrayList<ITransformateurD>();
			for (CommandeDistri c : contreDemande) {
				if (c.getValidation()) {
					avecstock.add(c.getVendeur());
				}
				else {
					insatisfait+=c.getQuantite();

				}
			}

			for (ITransformateurD t : this.getTransformateurs()) {
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
					contreDemande.add(new CommandeDistri(this, t, p, quantite, prixTonne, Monde.LE_MONDE.getStep(), false ));
				}

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
		for (PrixVente p : this.getPrixvente()){
			int i=1;
			for (IDistributeur d : this.getDistributeurs()){
				p.setPrix(p.getPrixVente()+d.getPrixVente(p.getProduit()));
				i+=1;
					
			}
			p.setPrix(p.getPrixVente()/i);
		}
	}

	public void next() {
		this.setBesoinStep(Monde.LE_MONDE.getStep());
		for (ITransformateurD t : this.getTransformateurs()) {
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
			// ventesStep = maCo.getVenteDistri(this);
			for (Produit p : this.getProduits()) {
				System.out.println("histo livraison --> "+this.getHistoLivraison());
				for (CommandeDistri d : this.getHistoLivraison()) {
					System.out.println("La quantit� -->"+d.getQuantite());
					System.out.println("Le prix -->"+d.getPrixTonne()*d.getQuantite());
					boolean a, b, c, e;
					a = d.getAcheteur() == this;
					b =d.getStepLivraison() == Monde.LE_MONDE.getStep();
					c =d.getVendeur().equals(t);
					e =d.getProduit().equals(p);
					System.out.println("Acheteur :"+a+" step :"+b+" Vendeur:"+c+" produit :"+e);
					if (d.getAcheteur() == this && d.getStepLivraison() == Monde.LE_MONDE.getStep() && d.getVendeur().equals(t) && d.getProduit().equals(p)) {
						this.setStock(p, t, d.getQuantite(), true);
						System.out.println("Quantite de : "+p+" --> "+d.getQuantite());
						this.setSolde(this.getSolde().getValeur() - d.getPrixTonne()*d.getQuantite());
						this.setAchats(p, t, d.getQuantite());
						double vente = d.getQuantite()*(1-0.1+0.3*Math.random());
						/* if (this.getStock(p, t).getQuantite().getValeur() < vente) {
							this.setVentes(p, t, this.getStock(p, t).getQuantite().getValeur());
							this.setStock(p, t, this.getStock(p, t).getQuantite().getValeur(), false);
							this.setSolde(this.getSolde().getValeur() + this.getStock(p, t).getQuantite().getValeur()*this.getPrixVente(p));
						}
						else {
							this.setVentes(p, t, vente);
							this.setStock(p, t,vente, false);
							this.setSolde(this.getSolde().getValeur() + vente/3*this.getPrixVente(p));
						} */
					} 
				} 
			}
			System.out.println("Solde de Carrefour :" +this.getSolde().getValeur());
			System.out.println("Stock de Carrefour de "+this.getLesStocks().get(1).getMarque()+ "-->" +this.getLesStocks().get(1).getQuantite().getValeur());
		}
	}
	@Override
	public Double getPrixVente(Produit p) {
		// TODO Auto-generated method stub
		return null;

	}

	

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
}