package abstraction.equipe6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateurD;
import abstraction.commun.MondeV1;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Carrefour implements Acteur,IDistributeur {

	private static final Monde LE_MONDE = null;
	private String nom;
	private double prixachat;
	private int prixvente;
	private Indicateur solde;
	private Indicateur achats;
	private double demandeannuel;
	private double demandeperstep;
	private double fraisdedistri;
	private List<Double> quantitesouhaite;
	private List<Produit> produits;
	private HashMap<Produit,Double> besoinStep;
    private List<Double> commandeparproduit;
    
    
	public List<Produit> getProduits() {
		return produits;
	}


	public void setProduits(List<Produit> produits) {
		this.produits = produits;
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

	public Carrefour(){
		this.commandeparproduit= new ArrayList<Double>();
	}
	// Fixe la demande selon la p�riode de l'ann�e.


	
	

	// Fixe la demande selon la p�riode de l'ann�e.
	
	public void  setdemandePerStep (int step ){

		if (step%26 == 6 ) {
			this.demandeperstep = (0.06*(1+(Math.random()-0.5)*0.2))*this.getDemandeAnnuel();
		}
		if (step%26 == 25) {
			this.demandeperstep = (0.12*(1+(Math.random()-0.5)*0.2))*this.getDemandeAnnuel();
		}
		else {
			this.demandeperstep = (0.03416*(1+(Math.random()-0.5)*0.2))*this.getDemandeAnnuel();
		}
	}
	

	// Reglage des frais de distribution choisi arbitrairement de 2% de la demande du step en cours

	public void ajouterVendeur(ITransformateurD t) {
		this.transformateurs.add(t);
	}
	

	// R�glage de la quantit� � acheter en fonction du transformateur (12.5% Nestl�, 3.6% Lindt et 83.9% Others)
	



	// Reglage de la quantite a acheter en fonction du transformateur (12.5% Nestle, 3.6% Lindt et 83.9% Others)

	public double getPrix() {

		return this.prixachat;
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

	
	public List<Double> getCommandeParProduit() {
		return this.commandeparproduit;
	}
	
	
	
	
	/*public void setCommandeBasique(int step, Carrefour carrefour, List<Indicateur> I) {
		int c = 0;
		for (Double d : this.commandeparproduit) {
			if (step%26 == 6 ) {
				d = carrefour.getDemandeAnnuel()*0.06;
			}
			
			else {
				if (step%26 == 25) {
					d = 0.12*carrefour.getDemandeAnnuel();
				}
				else {
					d = 0.03416*carrefour.getDemandeAnnuel();
				}
			}
			d = d*(1+(Math.random()*0.2 - 0.1)); // fluctuation al�atoire de 10% de la commandeparstep
		    */
			/*if (d>I.get(c).getValeur()){
				d=d;
				
			}else{
			if ( d >= 0.5*I.get(c).getValeur() && d<I.get(c).getValeur()){
		    	d=0.80*d;
		    }else{
		    	d=0.65*d;
		    	
		    }
		    c++;*/
		
		


	
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



	public List<CommandeDistri> contreDemande(List<CommandeDistri> cd) {
		for (Produit p : this.getProduits()) {
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
		return null;
	}


	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}
	

	
	/*public void next() {
		setdemandePerStep( MondeV1.LE_MONDE.getStep());
		setFraisdeDistri();
		for (ITransformateur t : this.transformateurs) {
			double q = this.getDemande(t);
			this.solde.setValeur(this, this.solde.getValeur()-q*this.getPrixAchat());
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


	@Override
	public List<CommandeDistri> ContreDemande(List<CommandeDistri> nouvelle, List<CommandeDistri> ancienne) {
		// TODO Auto-generated method stub
		return null;
	}





}


