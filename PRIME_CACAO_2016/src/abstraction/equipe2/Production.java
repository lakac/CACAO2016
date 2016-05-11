package abstraction.equipe2;
import abstraction.commun.*;

public class Production {
	private double production;
	
	public Production(double prod){
		this.production=prod;
	}

	public double getProduction() {
		return production;
	}
	
	

	public void setProduction(CommandeDis CommandeDis, Produit produit) {
		if (Stock.getstock()>=CommandeDis.getCommandeDis()/2){
			this.production=CommandeDis.getCommandeDis();
			
		}else{
			this.production = CommandeDis.getCommandeDis()
							+ CommandeDis.getCommandeDis()/2
							- Stock.getstock();
			
		}
	}
}
