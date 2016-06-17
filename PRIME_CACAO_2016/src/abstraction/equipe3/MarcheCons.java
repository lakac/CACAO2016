package abstraction.equipe3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateurD;
import abstraction.commun.MarcheConsommateurs;
import abstraction.commun.Produit;
import abstraction.fourni.Monde;

public class MarcheCons {
	
	public String nom;
	public static MarcheCons LE_MARCHE_CONS;
	
	/*liste des produits*/
	
	private ArrayList<Produit> produits;
	
	/*part de client choisissant leur distributeur en fonction du prix*/
	
	private final static double VARIATION_FIDELITE=0.01;
	
	/*parti minimum de clients fidèles a chaque distributeur*/
	
	private final static double FIDELITE_MIN=0.20;
	private static final double ALPHA = 0; 
	
	/*liste des distributeurs*/
	
	public static ArrayList<IDistributeur> distributeurs;
	
	/*liste des transformateurs*/
	
	public static ArrayList<ITransformateurD> transformateurs;
	
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
		MarcheCons.transformateurs=new ArrayList<ITransformateurD>();
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
	public static void ajouterTransformateur(ITransformateurD transformateur){
		MarcheCons.transformateurs.add(transformateur);
	}
	
	/* Retourne la part de fidélité d'un distributeur d pour un produit p*/
	
	public double getPart(IDistributeur d,Produit p){
		double part=0;
		for (Fidelite f : this.fidelite){
			if (f.getDistri()==d && f.getProduit()==p){
				part=f.getPart();
			}
		}
		return part;
	}
	
	/*renvoie les commandes reelles du step au distributeur d*/
	
	public List<CommandeDistri> getVenteDistri(IDistributeur d){
		List<CommandeDistri> res = new ArrayList<CommandeDistri>();
		for (CommandeDistri com : this.ventesEffectuees){
			if (com.getAcheteur()==d){
				res.add(com);
			}
		}
		return res;
	}
	
	/* Retourne la demande continue fixe d'un produit au step step*/
	
	public double getDemande(Produit p, int step){
		double demande=0;
		for (Demande d : this.calendrierDemande){
			if (d.getStep()==step && d.getProduit()==p){
				demande=d.getQuantite();
			}
		}
		return demande;
	}
	
	/* retourne le prix moyen de vente d'un produit*/
	
	public double getPrixMoyen(Produit p){
		double PrixMoyen=0;
		for (IDistributeur d : MarcheCons.distributeurs){
			PrixMoyen+=this.getPart(d,p)*d.getPrixVente(p);
		}
		return PrixMoyen;
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
				if (i%26==6){ //Pâques
				this.calendrierDemande.add(new Demande(i,p,0.0735*this.demandeAnnuelle.get(p)));
				}
				if (i%26==25){ //Noël
					this.calendrierDemande.add(new Demande(i,p,0.1235*this.demandeAnnuelle.get(p)));	
				}
				else{
					this.calendrierDemande.add(new Demande(i,p,0.0335*this.demandeAnnuelle.get(p)));
				}
			}
		}
	}
	
	/*methode qui initialise pourcentageIncertitudeVentes*/
	
	public void initialiserPourcentageIncertitudeVentes(){
		for (Produit p : this.getProduits()){
			this.pourcentageIncertitudeVentes.put(p, (double) 5); //5% d'incertitude
			}
		}	
	
	/*methode qui initialise la fidelite*/
	
	public void initialiserFidelite(){
		for (Produit p : this.getProduits()){
			for (IDistributeur d : MarcheCons.distributeurs){
				if (d.getNom()=="3eme"){
					
					//A MODIFIER
				}
				else{
					this.fidelite.add(new Fidelite(p,0.2,d));
				}
				
				}
			}
		}
	
	/*
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
	*/
	
	/*methode qui actualise la demande à chaque step*/
	
	public void actualiserDemande(){ 
		for (Produit p : this.getProduits()){ 
			double demandeDuStep = this.getDemande(p, Monde.LE_MONDE.getStep())-ALPHA*this.getPrixMoyen(p);
			
			this.demandeComposanteContinue.put(p,demandeDuStep);
			this.demandeComposanteAleatoire.put(p, this.demandeComposanteContinue.get(p)*(1+2*Math.random())*this.pourcentageIncertitudeVentes.get(p));
		}
	}
	
	/*methode qui repartit les ventes du step*/
	
	public void repartirVentes(){
		this.ventesEffectuees=new ArrayList<CommandeDistri>();
		for (Produit p : this.getProduits()){
			double demandeTotale=0;
			demandeTotale = this.demandeComposanteContinue.get(p)+this.demandeComposanteAleatoire.get(p);
			for (IDistributeur d : MarcheCons.distributeurs){
				for (int i=0;i<transformateurs.size();i++){
					this.ventesEffectuees.add(new CommandeDistri(d, transformateurs.get(i), p, this.getPart(d, p)*demandeTotale, d.getPrixVente(p), Monde.LE_MONDE.getStep(), true));//! au step du prix de vente
					//rajouter ratio transfo pour la quantité
				}			
			}
		}	
	}
	
	}
	

