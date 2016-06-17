package abstraction.equipe2;

/** Fonctionnement de la classe PartDeMarche:
 * 
 * Recuperer l'historique des commandes de chaque distributeur
 * 
 * chaque distributeur represente un certain pourcentage du Marche variable a chaque step
 * 		
 * ResteDesDistributeurs = Monde - PartLeclerc% - PartCarrefour%
 * 
 * normaliser ces parts de Marche afin de determiner quel est notre meilleur Client
 * 
 * Faire un ordre de priorite
 * */

public class PartDeMarche {
	
	/**Variables d'instance*/
	
	private double partLeclerc;
	private double partCarrefour;
	private double partAutresDistri;
	private Nestle nestle;
	
	
	/**Constructeurs*/
	
	public PartDeMarche(double leclerc, double carrefour, double autresDistri){
		this.partLeclerc = leclerc;
		this.partCarrefour = carrefour;
		this.partAutresDistri = autresDistri;
	}
	
	public PartDeMarche(){
		this(0.0,0.0,0.0);
	}
	
	
	/**Accesseurs en lecture*/
	
	public double getPartLeclerc(){
		return this.partLeclerc;
	}
	
	public double getPartCarrefour(){
		return this.partCarrefour;
	}
	
	public double getPartAutresDistri(){
		return this.partAutresDistri;
	}
	
	
	/**Accesseurs en ecriture*/
	
	public void setPartLeclerc(double part){
		this.partLeclerc = part;
	}
	
	public void setPartCarrefour(double part){
		this.partCarrefour = part;
	}
	
	public void setPartAutresDistri(double part){
		this.partAutresDistri = part;
	}
	
	
	/**methodes toString() et equals*/
	
	public String toString(){
		return "Part de Marche de Leclerc : "+this.getPartLeclerc()+"\n"+
				"Part de Marche de Carrefour : "+this.getPartCarrefour()+"\n"+
				"Part de Marche des Autres Distributeurs : "+this.getPartAutresDistri();
	}
	
	public boolean equals(Object o){
		return (o instanceof PartDeMarche)
				&& (this.getPartLeclerc() == ((PartDeMarche)o).getPartLeclerc())
				&& (this.getPartCarrefour() == ((PartDeMarche)o).getPartCarrefour())
				&& (this.getPartAutresDistri() == ((PartDeMarche)o).getPartAutresDistri());
	}
	
	
	
	
	/**methode void qui recupere l'historique des commandes de chaque distributeur 
	 * a chaque step
	 *  
	 * */
	public void recupererHistoriqueCommande(){
		int i=0;
		nestle.getHistoriqueCommandeDistri().get(i);
		i++;
	}

}
