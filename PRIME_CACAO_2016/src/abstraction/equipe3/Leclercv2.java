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
	public List<CommandeDistri> Demande(HashMap<ITransformateur, Catalogue> d) {
		List<CommandeDistri> dem = new ArrayList<CommandeDistri>();
		List<CommandeDistri> a = new ArrayList<CommandeDistri>();
		for (ITransformateur t : this.getTransformateurs()){
			//a=this.Demande(t,(d.get(t).getCata())); //créer un constructeur avec Hashmap en argument
			for (CommandeDistri c : a){
				dem.add(c);
			}
			
		}
		return dem;
		// TODO Auto-generated method stub
	}

	@Override
	public List<CommandeDistri> ContreDemande(List<CommandeDistri> cd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> cf) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandeDistri> LivraisonEffective(List<CommandeDistri> liste) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}

}
