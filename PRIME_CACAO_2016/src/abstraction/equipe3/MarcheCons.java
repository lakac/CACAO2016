package abstraction.equipe3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.commun.MarcheConsommateurs;
import abstraction.commun.Produit;

public class MarcheCons {
	
	public String nom;
	public static MarcheCons LE_MARCHE_CONS;
	private ArrayList<Produit> produits;
	
	/*part de client choisissant leur distributeur en fonction du prix*/
	
	private final static double VARIATION_FIDELITE=0.01;
	
	/*parti minimum de clients fidèles a chaque distributeur*/
	
	private final static double FIDELITE_MIN=0.20; 
	
	/*liste des distributeurs*/
	
	public static ArrayList<IDistributeur> distributeurs;
	
	/*liste des transformateurs*/
	
	public static ArrayList<ITransformateur> transformateurs;
	
	/*liste des commandes reelles des clients*/
	
	private List<CommandeDistri> ventesEffectuees;
	
	/*volume des ventes annuelles d'un produit*/
	
	private HashMap <Produit,Double> demandeAnnuelle; 
	
	/*composante continue de la demande par step*/
	
	private HashMap <Produit,Double> demandeComposanteContinue;
	
	/*composante aleatoire de la demande par step*/
	
	private HashMap <Produit,Double> demandeComposanteAleatoire;
	
	/*calendrier comportant la demande à chaque step*/
	
	private List<Demande> calendrierDemande; 
	
	/*variable qui regle l'amplitude de la composante aleatoire*/
	
	private HashMap <Produit,Double> pourcentageIncertitudeVentes;
	
	/*variable qui stocke la part de marche de chaque produit pour chaque distributeur*/
	
	private List<Fidelite> fidelite ;
	

	public MarcheCons(String nom, ArrayList<Produit> produits) {
		this.nom=nom;
		this.produits=produits;
		MarcheCons.distributeurs=new ArrayList<IDistributeur>();
		MarcheCons.transformateurs=new ArrayList<ITransformateur>();
		this.ventesEffectuees=new ArrayList<CommandeDistri>();
		this.calendrierDemande=new ArrayList<Demande>();
		this.demandeAnnuelle=new HashMap <Produit,Double>();
		this.demandeComposanteContinue=new HashMap <Produit,Double>();
		this.demandeComposanteAleatoire=new HashMap <Produit,Double>();
		this.pourcentageIncertitudeVentes=new HashMap <Produit,Double>();
		this.fidelite = new ArrayList<Fidelite>();
		// TODO Auto-generated constructor stub
	}
	
	public String getNom(){
		return this.nom;
	}
	public ArrayList<Produit> getProduits(){
		return this.produits;
	}
	public static void ajouterDistributeur(IDistributeur distributeur){
		MarcheCons.distributeurs.add(distributeur);
	}
	public static void ajouterTransformateur(ITransformateur transformateur){
		MarcheCons.transformateurs.add(transformateur);
	}
	
	
	/*methode qui initialise demandeAnnuelle*/
	
	public void initialiserDemandeAnnuelle(){
		for (Produit p : this.getProduits()){
			this.demandeAnnuelle.put(p, (double) 50000); // a faire varier en fonction du produit
			
				}
				
		}	
	
	/*methode qui initialise calendrierDemande*/
	
	public void initialiserCalendrierDemande(){
		
		for (Produit p : this.getProduits()){
			for (int i=1;i<=26;i++){
				if (i%26==6){
				//	this.calendrierDemande.get(i).put(p, 0.0735*this.demandeAnnuelle.get(p));
					
				}
				if (i%26==25){
				//	this.calendrierDemande.get(i).put(p, 0.1235*this.demandeAnnuelle.get(p));
					
				}
				else{
				//	this.calendrierDemande.get(i).put(p, 0.0335*this.demandeAnnuelle.get(p));	
				}
			}
		}
	}
	
	/*methode qui initialise pourcentageIncertitudeVentes*/
	
	public void initialiserPourcentageIncertitudeVentes(){
		for (Produit p : this.getProduits()){
			this.pourcentageIncertitudeVentes.put(p, (double) 5);
			}
		}
	
	
	//06/06/16
	
	/*methode qui initialise fidelite*/
	
	public void initialiserFidelite(){
		for (Produit p : this.getProduits()){
			//this.fidelite.get(MarcheCons.distributeurs.get(0)).put(p, 0.3);
			//this.fidelite.get(MarcheCons.distributeurs.get(1)).put(p, 0.3);
			for (IDistributeur d : MarcheCons.distributeurs){
				if(!(d.equals(MarcheCons.distributeurs.get(1))&&d.equals(MarcheCons.distributeurs.get(0)))){
				//	this.fidelite.get(d).put(p, 0.4); //0.4 n'est valable que si 3 distributeurs dans le monde
				}
				
			}
		}
	}
	
}
