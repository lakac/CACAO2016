package abstraction.commun;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import abstraction.equipe3.Leclercv2;
import abstraction.equipe6.Carrefour;
import abstraction.fourni.Acteur;
import abstraction.fourni.Historique;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

// Auteur : �quipe 3

public class MarcheConsommateurs implements Acteur {
	
	//pour impl�menter cette classe, on s'est inspir� du mod�le du march� des producteurs et de ses HashMap qui sotn tr�s pratiques.
	
	private final static double VARIATION_FIDELITE=0.01;//part de clients changeant de bord lorsque difference de prix
	private final static double FIDELITE_MIN=0.20; //part minimum de clients fid�les a Leclerc et Carrefour
	private final static double[][] CALENDRIER =new double[3][26]; //calendrier demande
	//private Leclercv2 leclerc;
	//private Carrefour carrefour;
	//Pentes des courbes Demande = Cte-aplha*PrixMoyen 
	private HashMap <Produit, Double> ALPHA;//a compl�ter 
	
	//Liste des distributeurs
	//A l'initialisation, on ajoute d'abord Leclerc puis Carrefour
	//Leclerc se trouve donc � l'indice 0 et Carrefour  � l'indice 1
	private static ArrayList<IDistributeur> distributeurs;
	
	public static MarcheConsommateurs LE_MARCHE_CONSOMMATEURS;
	private String nom;

	
	private HashMap <IDistributeur,HashMap<Produit,Double>> fidelite ;
	private HashMap <IDistributeur,HashMap<Produit,Double>> ventesEffectuees;
	
	private HashMap <Produit,Double> demandeAnnuelle; // volume des ventes annuelles d'un produit
	private HashMap <Produit,Double> demandeComposanteContinue;
	private HashMap <Produit,Double> demandeComposanteAleatoire;
	
	
	//Demande en fonction du step, par produit et sans effet sur les prix
	//Demande continue r�elle = calendrierdermande.get(Step)-ALPHA*PrixMoyen
	private HashMap <Integer, HashMap<Produit,Double>> calendrierDemande; 
	
	private HashMap <Produit,Double> pourcentageIncertitudeVentes;
	private HashMap <Produit,Double> offreTotale;
	private Catalogue cata;
	//Monde.LE_MONDE.ajouterIndicateur(this.fidelite);
	

	
	public MarcheConsommateurs(String nom){
		MarcheConsommateurs.distributeurs=new ArrayList<IDistributeur>();
		this.demandeComposanteContinue=new HashMap <Produit,Double>();
		this.demandeComposanteAleatoire=new HashMap <Produit,Double>();
		this.pourcentageIncertitudeVentes=new HashMap <Produit,Double>();
		this.offreTotale=new HashMap <Produit,Double>();
		this.demandeAnnuelle=new HashMap <Produit,Double>();
		this.nom=nom;

		//this.initialiser();
	}
	
	public String getNom(){
		return this.nom;
	}
	public static void ajouterDistributeur(IDistributeur distributeur){
		MarcheConsommateurs.distributeurs.add(distributeur);
	}//  
	
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
				//Version � n dimensions � d�terminer mathematiquement
			//}
			
		}

	public void repartirVentes(){
		for (IDistributeur d : MarcheConsommateurs.distributeurs){
			for (Produit p : cata.getProduits()){
				
				this.ventesEffectuees.get(d).put(p,this.fidelite.get(d).get(p)*(this.demandeComposanteContinue.get(p)+this.demandeComposanteAleatoire.get(p)));
			}
		}	
	}	

	public HashMap<Produit, Double> getVenteDistri(IDistributeur d){
		return this.ventesEffectuees.get(d);
	}

	

	
	
	public void initialiserDemandeAnnuelle(){
		for (Produit p : cata.getProduits()){
			this.demandeAnnuelle.put(p, (double) 50000); // a faire varier en fonction du produit
			
				}
				
			}
		

	public void initialiserCalendrierDemande(){
		
		for (Produit p : cata.getProduits()){
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
		for (Produit p : cata.getProduits()){
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
		for (Produit p : cata.getProduits()){
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
		
		//this.actualiserDemande();
		//this.actualiserOffre();
		//this.actualiserFidelite();
		//this.repartirVentes();
		
	}

	
}

































