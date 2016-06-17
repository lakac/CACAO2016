package abstraction.equipe3;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.ITransformateurD;

public class Stock {
	
	/*classe qui g�re les stocks des diff�rents produits */
	
	private ArrayList<Double[]> stock; //on adopte tacitement la convention indice 0 correspond au chocolat 50%, indice 1 au chocolat 60% ...
	private double fraisDeStock;
	private ArrayList<ITransformateurD> transfos;
	
	public Stock(ArrayList<Double[]> stock, double fraisDeStock){
		this.stock=stock;
		this.fraisDeStock=fraisDeStock;
		this.transfos=new ArrayList<ITransformateurD>();
	}
	
	public ArrayList<ITransformateurD> getTransfos(){
		return this.transfos;
	}
	public void setFraisDeStock(double fraisDeStock){
		this.fraisDeStock=fraisDeStock;
	}
	
	public double getStock(ITransformateurD t, int  indexproduit){
		double stock=0;
		for (int i=0;i<this.getTransfos().size();i++){
			if (t.equals(this.getTransfos().get(i))){
				stock=this.stock.get(i)[indexproduit];
			}
		} return stock;
	}
	public Double[] getStock(ITransformateurD t){
		Double[] stock = {0.0, 0.0, 0.0};
		for (int i=0;i<this.getTransfos().size();i++){
			if (t.equals(this.getTransfos().get(i))){
				stock=this.stock.get(i);
			}
		} return stock;
	}
	
	/*m�thode qui renvoie les frais du stock total en parcourant chaque stock de chaque produit provenant de tous les 
	 *transformateurs*/
	
	public double getFraisDeStockTotal(){
		double fraisDeStockTotal= 0.0;
		for (ITransformateurD t : this.getTransfos()){
			for (int i=0; i<3;i++){
				fraisDeStockTotal += this.getStock(t,i)*this.fraisDeStock;
			}
		} return fraisDeStockTotal;
	}
	
	/*m�thode qui initalise le stock*/
	
	public void initialiseStock(Leclercv2 Leclerc){
		this.fraisDeStock=0.1;
		this.stock = new ArrayList<Double[]>();
		Double[] l = {0.0,0.0,0.0};
		for (int i=0;i<Leclerc.getTransformateurs().size();i++){
			this.stock.add(l);
		}
		this.transfos=Leclerc.getTransformateurs();
	}
	
	/*les m�thodes suivantes permettent de g�rer le stock en ajoutant ou retirant des commandes, ou des listes de commandes*/
	
	public void ajouterStock (CommandeDistri com) {
		Double[] x;
		for (int i=0;i<this.getTransfos().size();i++){
			if (com.getVendeur().equals(this.getTransfos().get(i))){
				x=this.stock.get(i);
				this.stock.remove(i);
				if (com.getProduit().getNomProduit()=="50%"){
					x[0]+=com.getQuantite();
				}
				else{
					if (com.getProduit().getNomProduit()=="60%"){
						x[1]+=com.getQuantite();
					}
					else{
						x[2]+=com.getQuantite();
					}
				}
				this.stock.add(i, x);
			}
		} 
	}
	public void retirerStock (CommandeDistri com) {
		Double[] x;
		for (int i=0;i<this.getTransfos().size();i++){
			if (com.getVendeur().equals(this.getTransfos().get(i))){
				x=this.stock.get(i);
				this.stock.remove(i);
				if (com.getProduit().getNomProduit()=="50%"){
					x[0]-=com.getQuantite();
				}
				else{
					if (com.getProduit().getNomProduit()=="60%"){
						x[1]-=com.getQuantite();
					}
					else{
						x[2]-=com.getQuantite();
					}
				}
				this.stock.add(i, x);
			}
		} 
	}
	public void ajouterStock(List<CommandeDistri> l){
		for (CommandeDistri com : l){
			this.ajouterStock(com);
		}
	}
	public void retirerStock(List<CommandeDistri> l){
		for (CommandeDistri com : l){
			this.retirerStock(com);
		}
	}
}
