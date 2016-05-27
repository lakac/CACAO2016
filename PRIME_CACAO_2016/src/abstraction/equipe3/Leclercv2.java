package abstraction.equipe3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Leclercv2 implements Acteur,IDistributeur{
	
	private String nom;
	private Indicateur solde;
	private Ventes ventes;
	private Stock stock;
	private PrixDeVente prixdevente;
	private ArrayList<Double> ratio;
	private ArrayList<ITransformateur> transformateurs;

	public Leclercv2(String nom, Monde monde) {
		this.nom=nom;
		this.solde = new Indicateur("Solde de "+nom, this, 1000000.0);
		this.stock.initialiseStock();
		Monde.LE_MONDE.ajouterIndicateur( this.solde );
    	this.transformateurs = new ArrayList<ITransformateur>();
		this.ratio = new ArrayList<Double>();
		Monde.LE_MONDE.ajouterIndicateur( this.solde );
		this.transformateurs = new ArrayList<ITransformateur>();
		this.ventes=ventes;
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return this.nom;
	}
	
	public void ajouterVendeur(ITransformateur t) {
		this.transformateurs.add(t);
	}
	public ArrayList<ITransformateur> getTransformateurs(){
		return this.transformateurs;
	}
	
	public List<ITransformateur> Classerparprix(Produit p){ 
		List<ITransformateur> liste = new ArrayList<ITransformateur>();
		double t0 = this.getTransformateurs().get(0).getCatalogue().getTarif(p).getPrixTonne();
		double t1 = this.getTransformateurs().get(1).getCatalogue().getTarif(p).getPrixTonne();
		double t2 = this.getTransformateurs().get(2).getCatalogue().getTarif(p).getPrixTonne();
		if (t0<=t1 && t1<=t2){
			
		}
		else{
			if (t0<=t1 && t1>t2){
				
			}
			else{
				
			}
		}
		return liste;
	}

	public ITransformateur TransfoSuivant(CommandeDistri c){
		return c.getVendeur();	 // a modifier	
	}
	@Override
	public List<CommandeDistri> Demande(ITransformateur t, Catalogue c) {
		Double[] x = {0.0,0.0,0.0}; //moyenne des ventes des produit pour un step donné sur toutes les années
		Double[] sto = {0.0,0.0,0.0};
		for (int i=0; i<this.transformateurs.size();i++){
			if (t.equals(this.transformateurs.get(i))){
				sto = this.stock.getStock(t);
			}
		} int l = 0;
		for (int j=0; j<Monde.LE_MONDE.getStep()+25;j+=26){
			for (int m=0; m<x.length;m++){
				x[m]+=this.ventes.getVentes(j)[m];
			}
			l++;
		} for (int m=0; m<x.length;m++){
			x[m]=x[m]/l;
		} List<CommandeDistri> list = new ArrayList<CommandeDistri>();
		for (Produit p : c.getProduits()){
			CommandeDistri co = new CommandeDistri(this, t, p, 0, c.getTarif(p).getPrixTonne(), Monde.LE_MONDE.getStep()+3, false);
			list.add(co);
		}
		for (int i=0;i<x.length; i++){
			list.get(i).setQuantite(this.ratio.get(i)*x[i]-sto[i]);
		} 
		return list;
		// TODO Auto-generated method stub
	}

	public void ActualiserCommande(List<CommandeDistri> cd, CommandeDistri c){
		for (int i=0;i<cd.size();i++){
			if (cd.get(i).equals(c)){
				double q = cd.get(i).getQuantite()-c.getQuantite();
				boolean valid = (cd.get(i).getQuantite()-c.getQuantite()==0);
				ITransformateur vendeur = c.getVendeur();
				if(!valid){
					vendeur=TransfoSuivant(c);
				}
				CommandeDistri commande=new CommandeDistri(c.getAcheteur(), vendeur, c.getProduit(), q, c.getPrix(), c.getStepLivraison(), valid);
				cd.set(i,commande);
			}
		
		}
	}

	@Override
	public List<CommandeDistri> ContreDemande(List<CommandeDistri> nouvelle, List<CommandeDistri> ancienne) {
		List<CommandeDistri> a = ancienne;
		for (CommandeDistri c : nouvelle){
			this.ActualiserCommande(a, c);			
		}
		// TODO Auto-generated method stub
		return a;
	}
	
	
	
	public double recette(){
		double recette = 0.0;
		for (int i=0;i<3;i++){
			//recette+=DEMANDE.get(i)*this.prixdevente.getPrixDeVente().get(i);
		}
		return recette;
	}
	public double depenses(List<CommandeDistri> l){
		double depenses = 0.0;
		depenses+=this.stock.getFraisDeStockTotal();
		for (CommandeDistri com : l){
			depenses+=com.getPrix()*com.getQuantite();
		}
		return depenses;
	}


	public void next() {
		/*//récupérer commande finale
		List<CommandeDistri> commandefinale = LEMARCHE.CommandeFinale();*/
		/*récupérer livraison effective
		//List<CommandeDistri> livraisoneffective = LEMARCHE.LivraisonEffective();*/
		/*gérer le stock
		this.stock.rajoutStock(livraisoneffective);
		this.stock.retraitStock(DEMANDE.get())
		this.stock.setFraisDeStock();*/
		//gérer le solde
		//this.solde.setValeur(this, this.solde.getValeur()+recette()-depenses(commandefinale));
		//gérer prix de vente
		// TODO Auto-generated method stub
		
	}

}
