package abstraction.equipe4;
import abstraction.fourni.*;
import abstraction.commun.*;
import java.util.ArrayList;

public class Producteur implements Acteur,IProducteur{

	private String nom; 
	private Stock stock; 
	private Journal journal;
	private Tresorerie treso;
	private ProductionBiannuelle prodBiannu;
	private ArrayList<ITransformateurP> transformateurs;
	private Vente vente;

	//Constructeur de l'acteur Producteur 2

	public Producteur(Monde monde) {
		this.nom = Constantes.NOM_PRODUCTEUR_2;
		this.treso = new Tresorerie(this);
		this.stock = new Stock(this);
		this.journal = new Journal("Journal de "+this.nom);
		this.prodBiannu=new ProductionBiannuelle(this,1200000);
		this.transformateurs= new ArrayList<ITransformateurP>();
		Monde.LE_MONDE.ajouterJournal(this.journal);
	}

	public void AjoutVariableVente(){
		this.vente = new Vente(this.stock, this);
	}


	// getter

	public Journal getJournal() {
		return this.journal;
	}

	public String getNom() {
		return this.nom;
	}


	public ProductionBiannuelle getProdBiannu() {
		return this.prodBiannu;
	}

	public Stock getStock() {
		return this.stock;
	}

	public ArrayList<ITransformateurP> getTransformateurs() {
		return this.transformateurs;
	}

	public Tresorerie getTreso() {
		return this.treso;
	}

	public Vente getVente() {
		return this.vente;
	}


	//Ajout des clients à la liste transformateurs
	public void ajoutClient(ITransformateurP a){
		this.getTransformateurs().add(a);
	}


	// le next du producteur 2	
	public void next(){

		//production semi annuelle
		if (Monde.LE_MONDE.getStep()%12==1){
			// actualisation de toutes les variables du à la récolte semestrielle.
			this.getProdBiannu().production();
			this.getJournal().ajouter("Production de semi annuelle de " + this.getProdBiannu().getProductionFinale() + " en comptant les pertes de "+ this.getProdBiannu().getPerteProduction());

		}
		// modifications des stocks pour causes naturelles et prise en compte des couts de stock
		this.getStock().gererLesStock();
	}

	// retourne un double valant la quantité disponible 
	// pour chaque transformateur a chaque step
	public double annonceQuantiteMiseEnVente(ITransformateurD t) {
		if (((Acteur)t).getNom().equals(((Acteur)this.getTransformateurs().get(0)).getNom())) {
			return this.getVente().ventesStep()[0];
		}
		if (((Acteur)t).getNom().equals(((Acteur)this.getTransformateurs().get(1)).getNom())) {
			return this.getVente().ventesStep()[1];
		}
		if (((Acteur)t).getNom().equals(((Acteur)this.getTransformateurs().get(2)).getNom())) {
			return this.getVente().ventesStep()[2];
		}
		return 0.0;
	}

	//Modification du stock et de la tresorerie suite a une vente
	public void venteRealisee(CommandeProduc c) {
		// modifie la tresorerie
		this.vente(c.getQuantite(), c.getPrixTonne());
		// modife les stocks
		this.getStock().reductionStock(c.getQuantite());
		// le note dans le journal
		this.getJournal().ajouter("Vente de " + c.getQuantite()+" auprès de " + ((Acteur)c.getAcheteur()).getNom() + " au step numéro "+ Monde.LE_MONDE.getStep());
	}

	// ajout de le somme récolté à la trésorerie après une vente
	public void vente(double qtVendue, double prix){		
		this.getTreso().getFond().setValeur(this, this.getTreso().getFond().getValeur()+ qtVendue*prix);
	}

	public void notificationVente(CommandeProduc c) {
		this.venteRealisee(c);

	}


	@Override
	public double annonceQuantiteMiseEnVente(ITransformateurP t) {
		return 0.0;
	}
	//Réunion du 03/06 Ajout par l'équipe 2 le 8/06
	@Override
	public double annonceQuantitePropose() {
		// TODO Auto-generated method stub
		return 0;
	}

}
