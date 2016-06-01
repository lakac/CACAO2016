package abstraction.equipe3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.commun.MarcheConsommateurs;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Leclercv2 implements Acteur,IDistributeur{
	
	private String nom;
	private Ventes ventes;
	private Stock stock;
	private Indicateur solde; //SoldeDeLeclerc
	private PrixDeVente prixdevente;
	private ArrayList<Double> ratio;
	private ArrayList<ITransformateur> transformateurs;
	private MarcheConsommateurs marche; //a retirer
	private Catalogue cata; //a retirer

	public Leclercv2(String nom, Monde monde) {
		this.nom=nom;
		this.solde = new Indicateur("Solde de Leclerc", this, 1000000.0);
		Monde.LE_MONDE.ajouterIndicateur( this.solde );
    	this.transformateurs = new ArrayList<ITransformateur>();
		this.ratio = new ArrayList<Double>();
		Monde.LE_MONDE.ajouterIndicateur( this.solde );
		this.transformateurs = new ArrayList<ITransformateur>();
		this.ventes=new Ventes();
		this.stock= new Stock(this, new ArrayList<Double[]>(), 0.0);
		this.prixdevente=new PrixDeVente();
		// TODO Auto-generated constructor stub
	}
	public Stock getStock(){
		return this.stock;
	}
	public PrixDeVente getPrixDeVente(){
		return this.prixdevente;
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
		List<ITransformateur> transfo=this.getTransformateurs();
		int i=0;
		int n;
		while(transfo!=null){
			double a=transfo.get(0).getCatalogue().getTarif(p).getPrixTonne();
			n=0;
			for (int j=0;j<transfo.size();j++){
				if (transfo.get(j).getCatalogue().getTarif(p).getPrixTonne()<a){
					a=transfo.get(j).getCatalogue().getTarif(p).getPrixTonne();
					n=j;
					liste.set(i, transfo.get(j));
				}	
			}
		transfo.remove(n);
		i++;	
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
				CommandeDistri commande=new CommandeDistri(c.getAcheteur(), vendeur, c.getProduit(), q, c.getPrixTonne(), c.getStepLivraison(), valid);
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
		int j =0;
		for (Produit p : this.cata.getProduits()){ // a arranger
			recette+=this.marche.getVenteDistri(this).get(p)*this.prixdevente.getPrixDeVente().get(j);
			j++;
		}
		return recette;
	}
	public double depenses(List<CommandeDistri> l){
		double depenses = 0.0;
		depenses+=this.stock.getFraisDeStockTotal();
		for (CommandeDistri com : l){
			depenses+=com.getPrixTonne()*com.getQuantite();
		}
		return depenses;
	}
	
	public double getPrixDeVente(Produit p){
		if (p.getNomProduit()=="50%"){
			return this.prixdevente.getPrixDeVente().get(0);
		} else {
			if (p.getNomProduit()=="60%"){
				return this.prixdevente.getPrixDeVente().get(1);
			} else {
				return this.prixdevente.getPrixDeVente().get(2);
			}
		}
	}
	public Double getStock(Produit p) {
		double x = 0;
		if (p.getNomProduit()=="50%"){
			for (ITransformateur t : this.transformateurs){
				x+=this.stock.getStock(t,0);
			}
		} else {
			if (p.getNomProduit()=="60%"){
				for (ITransformateur t : this.transformateurs){
					x+=this.stock.getStock(t,1);
				}
			} else {
				for (ITransformateur t : this.transformateurs){
					x+=this.stock.getStock(t,2);
				}
			}
		} return x;
	}
	
	public Double getPrixVente(Produit p) {
		if (p.getNomProduit()=="50%"){
			return this.prixdevente.getPrixDeVente().get(0);
		} else {
			if (p.getNomProduit()=="60%"){
				return this.prixdevente.getPrixDeVente().get(1);
			} else {
				return this.prixdevente.getPrixDeVente().get(2);
			}
		}
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
		//gérer ventes (rajouter ventes réelles du step)
		//gérer prixdevente
		//this.getPrixDeVente.actualise();
		// TODO Auto-generated method stub

		
	}

	}

