package abstraction.equipe3;

public class Tresorerie {
	
	private double solde;

	public Tresorerie(double solde) {
		// TODO Auto-generated constructor stub
		this.solde =solde;
	}
	
	public Tresorerie() {
		this(5000000);  // 5 000 000 € de capital de départ
	}
	
	public double getSolde() {
		return this.solde;
	}
	
	public void setSolde(double s) {
		this.solde =s;
	}

}
