package abstraction.equipe2;

public class CommandeProd {
	
	private double commandesprod;
	
	
	public CommandeProd(double commandesprod) {
		this.commandesprod = commandesprod;
	}


	public double getCommandesProd() {
		return commandesprod;
	}
	
	public void SetCommandesProd(CommandeDis commandedis, Stock stock) {

		this.commandesprod = (commandedis.getCommandeDis()-stock.getStock())*(Constante.ACHAT_SANS_PERTE+Constante.MARGE_DE_SECURITE);

	}
	
	public static void main(String[] args) {
		CommandeProd commandes = new CommandeProd(100000.0);
		//Stock stock = new Stock(100.0);
		System.out.println(commandes.getCommandesProd());
		//System.out.println(SetCommandesProd(commandes, stock));
	} 
}

