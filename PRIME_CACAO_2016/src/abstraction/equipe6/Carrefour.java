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


	public ArrayList<ITransformateur> getTransformateurs() {
		return transformateurs;
	}


	public void setTransformateurs(ArrayList<ITransformateur> transformateurs) {
		this.transformateurs = transformateurs;
	}



	private ArrayList<ITransformateur> transformateurs;
	
	public Carrefour(String nom, Monde monde, double prixachat, int i, double demandeannuel, List<Double> cpp) {
		this.nom = nom;
		this.prixachat=prixachat;
		this.prixvente=i;
		this.achats = new Indicateur("Achats de "+this.nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+this.nom, this, 1000000.0);
    	Monde.LE_MONDE.ajouterIndicateur( this.achats );
    	Monde.LE_MONDE.ajouterIndicateur( this.solde );
    	this.transformateurs = new ArrayList<ITransformateur>();
    	this.commandeparproduit=cpp;
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

	public void ajouterVendeur(ITransformateur t) {
		this.transformateurs.add(t);
	}
	

	// Reglage de la quantite a acheter en fonction du transformateur (12.5% Nestle, 3.6% Lindt et 83.9% Others)

	public double getPrix() {
		return this.prixachat;
	}

	public double getDemande(ITransformateur t) {
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

	
	public List<Double> getCommandeParProduit() {
		return this.commandeparproduit;
	}
	
	
	
	
	public void setCommandeBasique(int step, Carrefour carrefour, List<Indicateur> I) {
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
		    
			/*if (d>I.get(c).getValeur()){
				d=d;
				
			}else{
			if ( d >= 0.5*I.get(c).getValeur() && d<I.get(c).getValeur()){
		    	d=0.80*d;
		    }else{
		    	d=0.65*d;
		    	
		    }
		    c++;*/
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


	@Override
	public List<CommandeDistri> demande(ITransformateur t, Catalogue c) {
		return this.commandeStep(this.getBesoinStep()).get(t);
	}


	@Override
	public List<CommandeDistri> contreDemande(List<CommandeDistri> cd) {
		for (Produit p : this.getProduits()) {
			double insatisfait = 0.0;
			List<ITransformateur> avecstock = new ArrayList<ITransformateur>();
			for (CommandeDistri c : cd) {
				if (c.getValidation()) {
					avecstock.add(c.getVendeur());
				}
				else {
					insatisfait+=c.getQuantite();
				}
			}
			for (ITransformateur t : avecstock) {
				
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









	@Override
	public List<CommandeDistri> LivraisonEffective(List<CommandeDistri> liste) {
		// TODO Auto-generated method stub
		return null;
	}

}