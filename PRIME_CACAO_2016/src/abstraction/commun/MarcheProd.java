package abstraction.commun;

import java.util.ArrayList;

import abstraction.fourni.*;

public class MarcheProd implements Acteur{

	private Indicateur coursCacao;
	
	private Historique historique;

	private static double CoursMinimum=2000.0;
	private static double CoursMaximum=4000.0;

	private ArrayList<IProducteur> producteurs;
	private double quantiteTotaleFournise;

	private ArrayList<ITransformateurP> transformateurs;
	private double quantiteTotaleDemandee;

	public MarcheProd() {
		this.coursCacao = new Indicateur("Cours de cacao du Marche",this,3000.0);
		this.producteurs= new ArrayList<IProducteur>();
		this.transformateurs= new ArrayList<ITransformateurP>();
		this.quantiteTotaleDemandee=0.0;
		this.quantiteTotaleFournise=0.0;
		this.historique= new Historique();
		this.historique.ajouter(this, Monde.LE_MONDE.getStep(), this.getCoursCacao().getValeur());
	}

	public ArrayList<IProducteur> getProducteurs() {
		return this.producteurs;
	}

	public ArrayList<ITransformateurP> getTransformateurs() {
		return this.transformateurs;
	}

	public double getQuantiteTotaleDemandee() {
		return this.quantiteTotaleDemandee;
	}

	public Historique getHistorique() {
		return this.historique;
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

	public void AjoutTransformateur(ITransformateurP t){
		this.getTransformateurs().add(t);
	}

	public void ActualisationCours(){
		this.setQuantiteTotaleDemandee(0.0);
		this.setQuantiteTotaleFournise(0.0);
		for (ITransformateurP t : this.getTransformateurs()){
			this.setQuantiteTotaleDemandee(this.getQuantiteTotaleDemandee()+t.annonceQuantiteDemandee());
		}
		for (IProducteur p : this.getProducteurs()){
			this.setQuantiteTotaleFournise(this.getQuantiteTotaleFournise()+p.annonceQuantiteProposee());		
		}
		this.getCoursCacao().setValeur(this,this.getCoursCacao().getValeur()+(this.getQuantiteTotaleDemandee()-this.getQuantiteTotaleFournise())/(this.getQuantiteTotaleFournise()));
		if (this.getCoursCacao().getValeur()<MarcheProd.CoursMinimum){
			this.getCoursCacao().setValeur(this, MarcheProd.CoursMinimum);
		}
		if (this.getCoursCacao().getValeur()>MarcheProd.CoursMaximum){
			this.getCoursCacao().setValeur(this, MarcheProd.CoursMaximum);
		}
		this.getHistorique().ajouter(this, Monde.LE_MONDE.getStep(), this.getCoursCacao().getValeur());
	}

	public void next() {
		//on actualise le cours du marché selon les données de ce step
		this.ActualisationCours();
		//on répartit les commandes équitablement
		if (this.getQuantiteTotaleDemandee()>this.getQuantiteTotaleFournise()){
			//si la demande est plus forte, les producteurs vendent tout et on répartit, proportionellement à leur demande,le cacao au transformateurs 
			for(IProducteur p : this.getProducteurs()){
				p.notificationVente(new CommandeProduc(p.annonceQuantiteProposee(),this.getCoursCacao().getValeur()));
			}
			for (ITransformateurP t : this.transformateurs){
				double quantiteLivree = t.annonceQuantiteDemandee()*(this.getQuantiteTotaleFournise()/this.getQuantiteTotaleDemandee());
				t.notificationVente(new CommandeProduc(quantiteLivree,this.getCoursCacao().getValeur()));
			}
		}else{
			//Cas contraire : les transformateurs sont sûr d'avoir leur commande et on répartit les ventes des producteurs proportionnelement à ce qu'ils ont mis sur le marché
			for (ITransformateurP t : this.getTransformateurs()){
				t.notificationVente(new CommandeProduc(t.annonceQuantiteDemandee(),this.getCoursCacao().getValeur()));	
			}
			for (IProducteur p : this.getProducteurs()){
				double quantiteVendue = p.annonceQuantiteProposee()*(this.getQuantiteTotaleDemandee()/this.getQuantiteTotaleFournise());
				p.notificationVente(new CommandeProduc(quantiteVendue,this.getCoursCacao().getValeur()));
			}
			
		}
		
	}

}
