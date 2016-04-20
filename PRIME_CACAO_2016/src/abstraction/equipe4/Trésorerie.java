package abstraction.equipe4;

public class Trésorerie {
	private double commande;
	private Indicateur fond;
	private Indicateur prix;
	private double CoutProd;
	
	
	
	public Trésorerie(Indicateur fond, Indicateur prix, double coutProd) {
		this.fond = fond;
		this.prix = prix;
		CoutProd = coutProd;
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
		
		this.fond.setValeur(this, this.getvente() + this.fond.getValeur()- this.CoutProd);
	
	}
		
	}
	