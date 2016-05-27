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
		} return list;
		// TODO Auto-generated method stub
	}


	@Override
	public List<CommandeDistri> ContreDemande(List<CommandeDistri> cd) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void rajoutStock(List<CommandeDistri> l){
		for (CommandeDistri com : l){
			this.stock.ajouterStock(com);
		}
	}
	public void retraitStock(List<CommandeDistri> l){
		for (CommandeDistri com : l){
			this.stock.retirerStock(com);
		}
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
		rajoutStock(livraisoneffective);
		retraitStock(DEMANDE.get())*/
		//gérer le solde
		//this.solde.setValeur(this, this.solde.getValeur()+recette()-depenses(commandefinale));
		//gérer prix de vente
		//gérer demande
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<CommandeDistri> LivraisonEffective(List<CommandeDistri> liste) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<CommandeDistri> Demande(HashMap<ITransformateur, Catalogue> d) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> cf) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Double getStock(Produit p) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Double getPrixVente(Produit p) {
		// TODO Auto-generated method stub
		return null;
	}

}
