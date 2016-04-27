package abstraction.equipe2;

public class Banque {

	private double tresorerie;

	public double getTresorerie() {
		return tresorerie;
	}

	public void setTresorerie(double tresorerie) {
		this.tresorerie = tresorerie;
	}
	
	public Banque(){
		tresorerie=(double)0;
	}
	
}
