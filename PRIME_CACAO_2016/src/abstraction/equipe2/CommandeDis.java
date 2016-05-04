package abstraction.equipe2;

public class CommandeDis {
	
	private double commande;
	
	public CommandeDis(double commande){
		this.commande=commande;
	}

	public double getCommandeDis() {
		return this.commande;
	}
	
	//Classe finit, test à faire
	public static void main(String[] args) {
		double comm=200.0;
		CommandeDis CommandeD = new CommandeDis(comm);
		if (CommandeD.getCommandeDis()!=comm){
			System.out.println("la commande du distributeur est de : " + CommandeD.getCommandeDis());
		}else{
			System.out.println("la commande du distributeur est à la bonne valeur :" + CommandeD.getCommandeDis());
		}
	}
}
