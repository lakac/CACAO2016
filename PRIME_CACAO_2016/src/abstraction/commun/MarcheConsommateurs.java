package abstraction.commun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import abstraction.fourni.Acteur;
import abstraction.fourni.Historique;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;


public class MarcheConsommateurs implements Acteur {
	
	private final static double VARIATION_FIDELITE=0.01;//part de clients changeant de bord lorsque difference de prix
	private final static double FIDELITE_MIN=0.20; //part minimum de clients fidèles a Leclerc et Carrefour
	//Pentes des courbes Demande = Cte-aplha*PrixMoyen 
	//private final static HashMap <Produit, Double> ALPHA=<>;//a compléter 
	private static ArrayList<IDistributeur> distributeurs;
	public static MarcheConsommateurs LE_MARCHE;

	//pour implémenter cette classe, on s'est inspiré du modèle du marché des producteurs et de ses HashMap qui sotn très pratiques.
	
	private HashMap <IDistributeur,HashMap<Produit,Double>> fidelite ;
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
		if (Monde.LE_MONDE.getStep()==0){ //ou step 1
			for (Produit p : cata.getProduits()){
				for (IDistributeur d : MarcheConsommateurs.distributeurs){
					
				
				}
			}
		}
		for (Produit p : cata.getProduits()){
			if (){//*Carrefour et Leclerc sont en concurrence sur ce produit*//)
					if ((Carrefour.getPrixVente(p)>Leclerc.getPrixVente(p))&&(this.fidelite.get("Carrefour").get(p)>FIDELITE_MIN)){//si prix carrefour superieur
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
			for (IDistributeur d : MarcheDistributeur.distributeurs){
				//Version à n dimension à déterminer 
			}
			}
		}
	}
	public void repartirVentes(){
		if (Monde.LE_MONDE.getStep()==0){ //ou step 1
			for (Produit p : cata.getProduits()){
				
			}
				//initialiser ventes en fonction des stocks disponibles de chaque distributeur
		}
		
		
		for (Produit p : cata.getProduits()){
			for (IDistributeur d : MarcheDistributeur.distributeurs){
					
				}
	}	
	public void getVentes(){ //renvoie le resultat des ventes du mois aux distributeurs 
		return this.
	}
	
	public void initialiserCalendrierDemande (){
		for p
	}
	
	public void next(){
		actualiser
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}
