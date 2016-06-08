package abstraction.commun;

import java.util.ArrayList;

import abstraction.fourni.*;

public class MarcheProd implements Acteur{

	private Indicateur coursCacao;

	private static double CoursMinimum=2000.0;
	private static double CoursMaximum=4000.0;

	private ArrayList<IProducteur> producteurs;
	private double quantiteTotaleFournise;

	private ArrayList<ITransformateur> transformateurs;
	private double quantiteTotaleDemandee;

	public MarcheProd() {
		this.coursCacao = new Indicateur("Cours de cacao du Marche",this,3000.0);
		this.producteurs= new ArrayList<IProducteur>();
		this.transformateurs= new ArrayList<ITransformateur>();
		this.quantiteTotaleDemandee=0.0;
		this.quantiteTotaleFournise=0.0;
	}

	public ArrayList<IProducteur> getProducteurs() {
		return this.producteurs;
	}

	public ArrayList<ITransformateur> getTransformateurs() {
		return this.transformateurs;
	}

	public double getQuantiteTotaleDemandee() {
		return this.quantiteTotaleDemandee;
	}

	public void setQuantiteTotaleDemandee(double quantiteTotaleDemandee) {
		this.quantiteTotaleDemandee = quantiteTotaleDemandee;
	}

	public void setQuantiteTotaleFournise(double quantiteTotaleFournise) {
		this.quantiteTotaleFournise = quantiteTotaleFournise;
	}

	public double getQuantiteTotaleFournise() {
		return this.quantiteTotaleFournise;
	}

	public Indicateur getCoursCacao() {
		return this.coursCacao;
	}



	public String getNom() {
		return "Marché d'échange entre producteurs et transformateurs";
	}



	public void AjoutProducteur(IProducteur p){
		this.getProducteurs().add(p);
	}

	public void AjoutTransformateur(ITransformateur t){
		this.getTransformateurs().add(t);
	}

	public void ActualisationCours(){
		this.setQuantiteTotaleDemandee(0.0);
		this.setQuantiteTotaleFournise(0.0);
		for (ITransformateur t : this.getTransformateurs()){
			this.setQuantiteTotaleDemandee(this.getQuantiteTotaleDemandee()+t.annonceQuantiteDemandee());
		}
		for (IProducteur p : this.getProducteurs()){
			this.setQuantiteTotaleFournise(this.getQuantiteTotaleFournise()+p.annonceQuantiteMiseEnVente());		
		}
		this.getCoursCacao().setValeur(this,this.getCoursCacao().getValeur()+(quantiteTotaleDemandee-quantiteTotaleFournise)/(quantiteTotaleFournise));
		if (this.getCoursCacao().getValeur()<this.CoursMinimum){
			this.getCoursCacao().setValeur(this, this.CoursMinimum);
		}
		if (this.getCoursCacao().getValeur()>this.CoursMaximum){
			this.getCoursCacao().setValeur(this, this.CoursMaximum);
		}
	}

	public void next() {
		this.ActualisationCours();
		if (this.getQuantiteTotaleDemandee()>this.getQuantiteTotaleFournise()){
			for(IProducteur p : this.getProducteurs()){
				p.notificationVente(new CommandeProduc(p.annonceQuantiteMiseEnVente(),this.getCoursCacao().getValeur()));
			}
			for (ITransformateur t : this.transformateurs){
				double quantiteLivree = t.annonceQuantiteDemandee()*(this.getQuantiteTotaleFournise()/this.getQuantiteTotaleDemandee());
				t.notificationVente(new CommandeProduc(quantiteLivree,this.getCoursCacao().getValeur()));
			}
		}else{
			for (ITransformateur t : this.getTransformateurs()){
				t.notificationVente(new CommandeProduc(t.annonceQuantiteDemandee(),this.getCoursCacao().getValeur()));	
			}
			for (IProducteur p : this.getProducteurs()){
				double quantiteVendue = p.annonceQuantiteMiseEnVente()*(this.getQuantiteTotaleDemandee()/this.getQuantiteTotaleFournise());
				p.notificationVente(new CommandeProduc(quantiteVendue,this.getCoursCacao().getValeur()));
			}
		}
		
	}

}
