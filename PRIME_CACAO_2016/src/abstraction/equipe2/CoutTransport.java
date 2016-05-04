package abstraction.equipe2;

public class CoutTransport {
	
	private double couttransport;
	public static final double DISTANCE_NESTLE_PROD1 = 5000.0;
	public static final double DISTANCE_NESTLE_PROD2 = 9000.0;
	
	public CoutTransport(double couttransp){
		this.couttransport=couttransp;
	}

	public double getCouttransport() {
		return couttransport;
	}

	public void setCouttransport(double couttransport) {
//Setter à faire une fois que la classe CommandesProd sera complète
	}
	
	public static void main(String[] args) {
		double couttransport=0.0000015;
		CoutTransport CoutT = new CoutTransport(couttransport);
		CoutT.setCouttransport(couttransport);
	}
	

}
