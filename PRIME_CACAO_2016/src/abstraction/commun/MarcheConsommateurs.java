package abstraction.commun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.equipe3.Leclerc;
import abstraction.equipe3.Leclercv2;
import abstraction.equipe6.Carrefour;
import abstraction.fourni.Acteur;
import abstraction.fourni.Historique;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;


public class MarcheConsommateurs implements Acteur {
	
	//pour implémenter cette classe, on s'est inspiré du modèle du marché des producteurs et de ses HashMap qui sotn très pratiques.
	
	private final static double VARIATION_FIDELITE=0.01;//part de clients changeant de bord lorsque difference de prix
	private final static double FIDELITE_MIN=0.20; //part minimum de clients fidèles a Leclerc et Carrefour
	private final static double[][] CALENDRIER =new double[26][3]; 
	
	//Pentes des courbes Demande = Cte-aplha*PrixMoyen 
	private HashMap <Produit, Double> ALPHA;//a compléter 
	
	//Liste des distributeurs
	private static ArrayList<IDistributeur> distributeurs;
	public static MarcheConsommateurs LE_MARCHE;

	
	private HashMap <IDistributeur,HashMap<Produit,Double>> fidelite ;
	private HashMap <IDistributeur,Double> ventesEffectuees;
	private HashMap <Produit,Double> demandeComposanteContinue;
	private HashMap <Produit,Double> demandeComposanteAleatoire;
	
	
	//Demande en fonction du step, par produit et sans effet sur les prix
	//Demande continue réelle = calendrierdermande.get(Step)-ALPHA*PrixMoyen
	private HashMap <Integer, HashMap<Produit,Double>> calendrierDemande; 
	
	private HashMap <Produit,Double> pourcentageIncertitudeVentes;
	private HashMap <Produit,Double> offreTotale;
	private Catalogue cata;
	//Monde.LE_MONDE.ajouterIndicateur(this.fidelite);
	
	public void ajouterDistributeur(IDistributeur distributeur){
		MarcheConsommateurs.distributeurs.add(distributeur);
	}//  
	
	public MarcheConsommateurs(){
		MarcheConsommateurs.distributeurs=new ArrayList<IDistributeur>();
		this.demandeComposanteContinue=new HashMap <Produit,Double>();
		this.demandeComposanteAleatoire=new HashMap <Produit,Double>();
		this.pourcentageIncertitudeVentes=new HashMap <Produit,Double>();
		this.offreTotale=new HashMap <Produit,Double>();
		
	}
	
	public void actualiserDemande(){ //A actualiser a chaque next()
		for (Produit p : cata.getProduits()){ 
			double demandeDuStep = ((Double)calendrierDemande.get(Monde.LE_MONDE.getStep()%26).get(p)).doubleValue();
			this.demandeComposanteContinue.put(p,demandeDuStep);
			this.demandeComposanteAleatoire.put(p, this.demandeComposanteContinue.get(p)*(1+2*Math.random())*this.pourcentageIncertitudeVentes.get(p));
		}
	}
	
	public void actualiserOffre(){
		for (Produit p : cata.getProduits()){
			this.offreTotale.put(p,(double) 0);//initialise stocks par produit
			
			for (IDistributeur d : MarcheConsommateurs.distributeurs){
				this.offreTotale.put(p,this.offreTotale.get(p)+(d.getStock(p)));
			}
		}
	}
	
	public void actualiserFidelite(){
		for (Produit p : cata.getProduits()){
			if (){//*Carrefour et Leclerc sont en concurrence sur ce produit/)
				if ((Carrefour.getPrixVente(p)>Leclercv2.getPrixVente(p))&&(this.fidelite.get("Carrefour").get(p)>FIDELITE_MIN)){//si prix carrefour superieur
						this.fidelite.get("Leclerc").put(p,this.fidelite.get("Leclerc").get(p)+VARIATION_FIDELITE);
						this.fidelite.get("Carrefour").put(p,this.fidelite.get("Carrefour").get(p)-VARIATION_FIDELITE);
					}
					if ((Carrefour.getPrixVente(p)<Leclerc.getPrixVente(p))&&(this.fidelite.get("Leclerc").get(p)>FIDELITE_MIN)){//si prix carrefour superieur
						this.fidelite.get("Leclerc").put(p,this.fidelite.get("Leclerc").get(p)-VARIATION_FIDELITE);
						this.fidelite.get("Carrefour").put(p,this.fidelite.get("Carrefour").get(p)+VARIATION_FIDELITE);
					}
					//si prix leclerc inferieur
					//...
					//Fidelite.get(Ca)-=VARIATION_FIDELITE
			}	
			for (IDistributeur d : MarcheConsommateurs.distributeurs){
				//Version à n dimension à déterminer 
			}
			}
		}
	
	public void repartirVentes(){
		if (Monde.LE_MONDE.getStep()==0){ //ou step 1
			for (Produit p : cata.getProduits()){
				
			}
				//initialiser ventes en fonction des stocks disponibles de chaque distributeur
		}
		for (IDistributeur d : MarcheConsommateurs.distributeurs){
			this.ventesEffectuees.put(d,(double) 0);
			for (Produit p : cata.getProduits()){
				this.ventesEffectuees.put(d,this.ventesEffectuees.get(d)+this.fidelite.get(d).get(p)*(this.demandeComposanteContinue.get(p)+this.demandeComposanteAleatoire.get(p)));
			}
		}
		
		for (Produit p : cata.getProduits()){
			
		}
			
	}	
	
	public void initialiserCalendrierDemande (){
		for (int i=0;i<MarcheConsommateurs.CALENDRIER.length; i++){
			if
		}
	}
	

	
	
	public void next(){
		
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}
	
}