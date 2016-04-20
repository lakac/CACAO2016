package abstraction.equipe4;

public class Tresorerie {
	private double commande;
	private Indicateur fond;
	private Indicateur prix;
	private double CoutProd;
	private Acteur Prod;
	
	
	public Acteur getProd() {
		return Prod;
	}

	public void setProd(Acteur prod) {
		Prod = prod;
	}

	public Tresorerie(Indicateur fond, Indicateur prix, double coutProd,Acteur a ) {
		this.fond = fond;
		this.prix = prix;
		CoutProd = coutProd;
		this.Prod= a;
	}

	public double getCommande() {
		return this.commande;
	}
	
	public Indicateur getFond() {
		return this.fond;
	}

	public Indicateur getPrix() {
		return this.prix;
	}

	public double Vente(){
		return this.getCommande()*this.getPrix().getValeur();
	}
	public double getvente(){
		return this.Vente();
	}
		
	public void modiftréso(){
		
		this.fond.setValeur(this.Prod, this.getvente() + this.fond.getValeur()- this.CoutProd);
	
	}
		
	}
	