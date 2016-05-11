package abstraction.equipe2;

public class CommandesProd {
	
	private double commandesprod;

	
	
	public CommandesProd(double commandesprod) {
		this.commandesprod = commandesprod;
	}


	public double getCommandesProd() {
		return commandesprod;
	}
	
	public void SetCommandesProd(CommandeDis commandedis, Stock stock) {
		double margedesecurite = 0.2+Math.random()*0.1;
		this.commandesprod = (commandedis.getCommandeDis()-stock.getStock())*(1+margedesecurite);
	}
	public static void main(String[] args) {
		CommandesProd commandes = new CommandesProd(100000.0);
		//Stock stock = new Stock(100.0);
		System.out.println(commandes.getCommandesProd());
		//System.out.println(SetCommandesProd(commandes, stock));
	} 
}

