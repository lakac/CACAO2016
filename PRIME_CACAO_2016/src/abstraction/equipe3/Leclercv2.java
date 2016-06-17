package abstraction.equipe3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.commun.MarcheConsommateurs;
import abstraction.commun.MarcheDistributeur;
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
	private ArrayList<Produit> produits;

	public Leclercv2(String nom, Monde monde, ArrayList<Produit> produits) {
		this.nom=nom;
		this.produits=produits;
		this.solde = new Indicateur("Solde de Leclerc", this, 1000000.0);
		Monde.LE_MONDE.ajouterIndicateur( this.solde );
    	this.transformateurs = new ArrayList<ITransformateur>();
		this.ratio = new ArrayList<Double>();
		this.transformateurs = new ArrayList<ITransformateur>();
		this.ventes=new Ventes();
		this.stock= new Stock(new ArrayList<Double[]>(), 0.0);
		this.prixdevente=new PrixDeVente();
		// TODO Auto-generated constructor stub
	}
	public Stock getStock(){
		return this.stock;
	}
	public ArrayList<Produit> getProduits(){
		return this.produits;
	}
	public Ventes getVentes(){
		return this.ventes;
	}
	public PrixDeVente getPrixDeVente(){
		return this.prixdevente;
	}
	public String getNom() {
		return this.nom;
	}
	public void ajouterVendeur(ITransformateur t) {
		this.transformateurs.add(t);
	}
	public ArrayList<ITransformateur> getTransformateurs(){
		return this.transformateurs;
	}
	
	public void initialiseRatio(){
		this.ratio.add(0.13);
		this.ratio.add(0.04);
	}
	
	/*methode qui classe les transfos du moins cher au sens du produit p au plus cher*/
	
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

	/*methode qui fait appel au distributeur suivant dans la liste renvoyee par la methode precedente*/
	
	public ITransformateur TransfoSuivant(CommandeDistri c){
		List<ITransformateur> liste = Classerparprix(c.getProduit());
		int i;
		if (c.getVendeur()==liste.get(0)){
			i=0;
		}
		else{
			if (c.getVendeur()==liste.get(1)){
				i=1;
			}
			else{
				i=2;
			}
		}
		i=(i+1)%(liste.size());
		return liste.get(i);
	}
	
	/*methode qui fait la moyenne des ventes de ce step des annees passees pour avoir une idee 
	 * du nombre de clients a ce step */

	public List<CommandeDistri> Demande(ITransformateur t, Catalogue c) {
		Double[] x = {0.0,0.0,0.0}; //moyenne des ventes des produit pour un step donné sur toutes les années
		Double[] sto = {0.0,0.0,0.0};
		sto[0] = this.stock.getStock(t,0);
		sto[1] = this.stock.getStock(t,1);
		sto[2] = this.stock.getStock(t,2);
		int l = 0;
		for (int j=0; j<Monde.LE_MONDE.getStep()+25;j+=26){
			x[0]+=this.ventes.getVentes(j)[0];
			x[1]+=this.ventes.getVentes(j)[1];
			x[2]+=this.ventes.getVentes(j)[2];
			l++;
		} for (int m=0; m<x.length;m++){
			x[m]=x[m]/l;
		} List<CommandeDistri> list = new ArrayList<CommandeDistri>();
		for (Produit p : c.getProduits()){
			CommandeDistri co = new CommandeDistri(this, t, p, 0, c.getTarif(p).getPrixTonne(), Monde.LE_MONDE.getStep()+3, false);
			list.add(co);
		}
		for (int j = 0; j<this.transformateurs.size();j++){
			if (t.equals(this.transformateurs.get(j))){
				for (int i=0;i<x.length; i++){
					list.get(i).setQuantite(this.ratio.get(j)*x[i]-sto[i]);
				} 
			}
		}
		return list;
	}
	
	/*methode qui change la quantite et passe au transfo suivant de la CommandeDistri c dans la List<CommandeDistri>
	 * ie on met a jour la commande c dans la liste pour renvoyer la nouvelle demande*/

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
	
	/*utilise la methode precedente avec toutes les commandes de l'ancienne liste pour renvoyer une liste
	 * actualisee*/

	public List<CommandeDistri> ContreDemande(List<CommandeDistri> nouvelle, List<CommandeDistri> ancienne) {
		List<CommandeDistri> a = ancienne;
		for (CommandeDistri c : nouvelle){
			this.ActualiserCommande(a, c);			
		}
		return a;
	}
	
	/*methode qui renvoie les gains des ventes des differents produits*/
	
	public double recette(){
		double recette = 0.0;
		int j =0;
		for (Produit p : this.getProduits()){ 
			recette+=MarcheConsommateurs.LE_MARCHE_CONSOMMATEURS.getVenteDistri(this).get(p)*this.prixdevente.getPrixDeVente().get(j);
			j++;
		}
		return recette;
	}
	
	/*methode qui renvoie les pertes provenant des frais de stockage et de l'achat du chocolat aux tranfos*/
	
	public double depenses(List<CommandeDistri> l){
		double depenses = 0.0;
		depenses+=this.stock.getFraisDeStockTotal();
		for (CommandeDistri com : l){
			depenses+=com.getPrixTonne()*com.getQuantite();
		}
		return depenses;
	}
	
	/*methode qui renvoie le stock du produit p, utilisee par LE_MARCHE_CONSOMMATEURS*/
	
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
	
	/*methode qui renvoie le prix de vente du produit p, utilisee par LE_MARCHE_CONSOMMATEURS*/
	
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
		/*
		//récupérer commande finale
		List<CommandeDistri> commandefinale = MarcheDistributeur.LE_MARCHE_DISTRIBUTEUR.getCommandeFinale();
		//récupérer livraison effective
		List<CommandeDistri> livraisoneffective = MarcheDistributeur.LE_MARCHE_DISTRIBUTEUR.getLivraisonglobale();
		//recuperer ventes effectives
		HashMap<Produit, Double> venteeffective = MarcheConsommateurs.LE_MARCHE_CONSOMMATEURS.getVenteDistri(this);
		//gérer le stock
		this.getStock().ajouterStock(livraisoneffective);
		//this.getStock().retirerStock(venteeffective);
		//gérer le solde
		this.solde.setValeur(this, this.solde.getValeur()+recette()-depenses(commandefinale));
		//gérer ventes (rajouter ventes réelles du step)
		//this.getVentes().actualiserVentes(venteeffective);
		//gérer prixdevente
		this.getPrixDeVente().actualisePrixDeVente();
		// TODO Auto-generated method stub
		  */
	}

}

