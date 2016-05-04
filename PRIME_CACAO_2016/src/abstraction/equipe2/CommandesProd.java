package abstraction.equipe2;

public class CommandesProd {
	
	public CommandesProd(double commandesprod) {
		this.commandesprod = commandesprod;
	}

	private double commandesprod;

	public double getCommandesProd() {
		return commandesprod;
	}
	
	public void SetCommandesProd(CommandeDis commandedis, Stock stock) {
		double margedesecurite = 0.2+Math.random()*0.1;
		this.commandesprod = (commandedis.getCommandeDis()-stock.getStock())*(1+margedesecurite);
	}
}
