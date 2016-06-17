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

// Auteur : équipe 3

public class MarcheConsommateurs implements Acteur {
	
	private final static double VARIATION_FIDELITE=0.01;//part de clients changeant de bord lorsque difference de prix
	private final static double FIDELITE_MIN=0.20; //part minimum de clients fidèles a Leclerc et Carrefour

	//Pentes des courbes Demande = Cte-aplha*PrixMoyen 
	private HashMap <Produit, Double> ALPHA;//a compléter 
	
	//Liste des distributeurs
	//Leclerc se trouve donc à l'indice 0 et Carrefour  à l'indice 1
	private static ArrayList<IDistributeur> distributeurs;  	
	
	public static MarcheConsommateurs LE_MARCHE_CONSOMMATEURS;
	private String nom;
	private HashMap <IDistributeur,HashMap<Produit,Double>> fidelite;
	private HashMap <IDistributeur,HashMap<Produit,Double>> ventesEffectuees;
	private HashMap <Produit,Double> demandeAnnuelle; // volume des ventes annuelles d'un produit
	private HashMap <Produit,Double> demandeComposanteContinue;
	private HashMap <Produit,Double> demandeComposanteAleatoire;
	
	
	//Demande en fonction du step, par produit, et sans effet sur les prix
	//Demande continue réelle = calendrierdermande.get(Step)-ALPHA*PrixMoyen
	
	private HashMap <Integer, HashMap<Produit,Double>> calendrierDemande; 
	private HashMap <Produit,Double> pourcentageIncertitudeVentes;
	private HashMap <Produit,Double> offreTotale;
	private ArrayList<Produit> produits;
	
	//Monde.LE_MONDE.ajouterIndicateur(this.fidelite);
	
	public MarcheConsommateurs(String nom, ArrayList<Produit> produits){
		
		MarcheConsommateurs.distributeurs=new ArrayList<IDistributeur>();
		this.demandeComposanteContinue=new HashMap <Produit,Double>();
		this.demandeComposanteAleatoire=new HashMap <Produit,Double>();
		this.pourcentageIncertitudeVentes=new HashMap <Produit,Double>();
		this.offreTotale=new HashMap <Produit,Double>();
		this.demandeAnnuelle=new HashMap <Produit,Double>();
		this.nom = nom;
		this.produits=produits;
		this.fidelite = new HashMap <IDistributeur,HashMap<Produit,Double>>();
	}
	
	public String getNom(){
		return this.nom;
	}
	public ArrayList<Produit> getProduits(){
		return this.produits;
	}
	public static void ajouterDistributeur(IDistributeur distributeur){
		MarcheConsommateurs.distributeurs.add(distributeur);

	}// 
	
	public double getPrixMoyen(Produit p){
		double PrixMoyen=0;
		for (IDistributeur d : MarcheConsommateurs.distributeurs){
			PrixMoyen+=this.fidelite.get(d).get(p)*d.getPrixVente(p);
		}
		return PrixMoyen;
	}
	
	public void actualiserDemande(){ //A actualiser a chaque next()
		for (Produit p : this.getProduits()){ 
			double demandeDuStep = ((Double)calendrierDemande.get(Monde.LE_MONDE.getStep()%26).get(p)).doubleValue()-this.ALPHA.get(p)*getPrixMoyen(p);
			this.demandeComposanteContinue.put(p,demandeDuStep);
			this.demandeComposanteAleatoire.put(p, this.demandeComposanteContinue.get(p)*(1+2*Math.random())*this.pourcentageIncertitudeVentes.get(p));
		}
	}
	
	public void actualiserOffre(){
		for (Produit p : this.getProduits()){
			this.offreTotale.put(p,(double) 0);//initialise stocks par produit
			
			for (IDistributeur d : MarcheConsommateurs.distributeurs){
				this.offreTotale.put(p,this.offreTotale.get(p)+(d.getStock(p)));
			}
		}
	}
	
	public void actualiserFidelite(){
		for (Produit p : this.getProduits()){
			//if Carrefour et Leclerc sont en concurrence sur ce produit/) (V3)
				if ((MarcheConsommateurs.distributeurs.get(1).getPrixVente(p)>MarcheConsommateurs.distributeurs.get(0).getPrixVente(p))&&(this.fidelite.get("Carrefour").get(p)>FIDELITE_MIN)){//si prix carrefour superieur
						this.fidelite.get(MarcheConsommateurs.distributeurs.get(0)).put(p,this.fidelite.get(MarcheConsommateurs.distributeurs.get(0)).get(p)+VARIATION_FIDELITE);
						this.fidelite.get(MarcheConsommateurs.distributeurs.get(1)).put(p,this.fidelite.get(MarcheConsommateurs.distributeurs.get(1)).get(p)-VARIATION_FIDELITE);
					}
					if ((MarcheConsommateurs.distributeurs.get(1).getPrixVente(p)<MarcheConsommateurs.distributeurs.get(0).getPrixVente(p))&&(this.fidelite.get("Leclerc").get(p)>FIDELITE_MIN)){//si prix carrefour superieur
						this.fidelite.get(MarcheConsommateurs.distributeurs.get(0)).put(p,this.fidelite.get(MarcheConsommateurs.distributeurs.get(0)).get(p)-VARIATION_FIDELITE);
						this.fidelite.get(MarcheConsommateurs.distributeurs.get(1)).put(p,this.fidelite.get(MarcheConsommateurs.distributeurs.get(1)).get(p)+VARIATION_FIDELITE);
					}
			}	
			//for (IDistributeur d : MarcheConsommateurs.distributeurs){
				//Version à n dimensions à déterminer mathematiquement
			//}
			
		}

	public void repartirVentes(){
		for (IDistributeur d : MarcheConsommateurs.distributeurs){
			for (Produit p : this.getProduits()){
				
				this.ventesEffectuees.get(d).put(p,this.fidelite.get(d).get(p)*(this.demandeComposanteContinue.get(p)+this.demandeComposanteAleatoire.get(p)));
			}
		}	
	}	

	public HashMap<Produit, Double> getVenteDistri(IDistributeur d){
		return this.ventesEffectuees.get(d);
	}
	
	public void initialiserDemandeAnnuelle(){
		for (Produit p : this.getProduits()){
			this.demandeAnnuelle.put(p, (double) 50000); // a faire varier en fonction du produit
			
				}
				
			}	

	public void initialiserCalendrierDemande(){
		
		for (Produit p : this.getProduits()){
			for (int i=1;i<=26;i++){
				if (i%26==6){
					this.calendrierDemande.get(i).put(p, 0.0735*this.demandeAnnuelle.get(p));
					
				}
				if (i%26==25){
					this.calendrierDemande.get(i).put(p, 0.1235*this.demandeAnnuelle.get(p));
					
				}
				else{
					this.calendrierDemande.get(i).put(p, 0.0335*this.demandeAnnuelle.get(p));	
				}
			}
		}
	}
	
	public void initialiserFidelite(){
		for (Produit p : this.getProduits()){
			this.fidelite.get(MarcheConsommateurs.distributeurs.get(0)).put(p, 0.3);
			this.fidelite.get(MarcheConsommateurs.distributeurs.get(1)).put(p, 0.3);
			for (IDistributeur d : MarcheConsommateurs.distributeurs){
				if(!(d.equals(MarcheConsommateurs.distributeurs.get(1))&&d.equals(MarcheConsommateurs.distributeurs.get(0)))){
					this.fidelite.get(d).put(p, 0.4); //0.4 n'est valable que si 3 distributeurs dans le monde
				}
				
			}
		}
	}
	
	public void initialiserPourcentageIncertitudeVentes(){
		for (Produit p : this.getProduits()){
			this.pourcentageIncertitudeVentes.put(p, (double) 5);
			}
		}
	
	public void initialiser(){
		this.initialiserDemandeAnnuelle();
		this.initialiserCalendrierDemande();
		this.initialiserPourcentageIncertitudeVentes();
		this.initialiserFidelite();
	}
	
	public void next(){
		
		this.actualiserDemande();
		this.actualiserOffre();
		this.actualiserFidelite();
		this.repartirVentes();
		
		
	}

	
}
