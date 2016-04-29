package abstraction.equipe2;

public class Stock {
	
	private double[] stock=new double[2];
	
	// le stosk à l'instant t dépend de la quantité demandé pour l'instant t+2 
		//et de la quantité produite pour l'instant t+1
		public double stock_cacao (Stock S1) {	
			if(this.getStock()[1]<0){
				return -1;
			}else{
			double s = this.getStock()[1]+this.getStock()[0]-0.6*Nestle.commandes.getCommandes()[1];
			if (s>=0){
				return s;}
			else{
				return -1;
			}
			}
			//Test OK
		}
		
		public double[] getStock() {
			return this.stock;
		}
		
		
// Si le stock est une fois inférieur a 0, il sera tout le temps égal a -1 et on le verra à la fin
		public double stock_chocolat (double[] T, Stock S2) {
			if(this.getStock()[1]<0){
				return -1;
			}else{
			double s= this.getStock()[1]+this.getStock()[0]-T[0];
			if(s>=0){
				return s;
				}else{
					return -1;
				}
			}
		
			//Test OK
		}
		
		public Stock(){
			stock=new double[2];
		}
		
		//Suivi du stock de cacao au fil des step
		
		public void ajout_cacao(){
			this.getStock()[1]+=this.getStock()[0];
			this.getStock()[1]-=0.6*Nestle.commandes.getCommandes()[1];
			this.getStock()[0]=0.6*Nestle.commandes.getCommandes()[2];
		}
		
		
		//suivi du stock de chocolat au fil des step
		public void ajout_chocolat(){
			this.getStock()[1]+=this.getStock()[0];
			this.getStock()[1]-=0.6*Nestle.commandes.getCommandes()[0];
			this.getStock()[0]=Nestle.commandes.getCommandes()[1];
		}
		
		public static void main(String[] args) {
		Stock S1=new Stock();
		
		}

}
