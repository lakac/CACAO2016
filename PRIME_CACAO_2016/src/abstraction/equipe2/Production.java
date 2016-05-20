package abstraction.equipe2;

public class Production {
	private double production;
	private static final double PRODUCTION_MAX = 0.0;
	
	public Production(double prod){
		this.production=prod;
	}

	public double getProduction() {
		return production;
	}

	public void setProduction(CommandeDis CommandeDis)  {
		this.production = Math.min(PRODUCTION_MAX,CommandeDis.getCommandeDis());
	}
}
