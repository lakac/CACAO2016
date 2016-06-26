package abstraction.commun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import abstraction.equipe3.*;
import abstraction.fourni.Acteur;

public class MarcheCons implements Acteur {
	
	public String nom;
	public static MarcheCons LE_MARCHE_CONS;
	
	/*liste des produits*/
	
	private ArrayList<Produit> produits;
	
	/*part de client choisissant leur distributeur en fonction du prix*/
	
	private final static double VARIATION_FIDELITE=0.01;
	
	/*part minimum de clients fidèles a chaque distributeur*/
	
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
	
	/*variable qui détermine le ratio entre les différents distributeurs pour un même produit*/
	
	private List<Double> ratio;
	
	
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
	
	public int getIndexFidelite(IDistributeur d,Produit p){
		for (int j=0;j<this.fidelite.size();j++){
			if (this.fidelite.get(j).getDistri()==d && this.fidelite.get(j).getProduit()==p){
				return j;
			}
		}
		return 0;
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
	
	/* retourne le prix moyen de vente d'un produit p de la marque t*/
	
	public double getPrixMoyen(Produit p,ITransformateurD t){
		double PrixMoyen=0;
		for (IDistributeur d : MarcheCons.distributeurs){
			PrixMoyen+=this.getPart(d,p)*d.getPrixVente(p,t);
		}
		return (PrixMoyen/MarcheCons.distributeurs.size());
	}
	
	/*methode qui initialise demandeAnnuelle*/
	
	public void initialiserDemandeAnnuelle(){
		for (Produit p : this.getProduits()){
			this.demandeAnnuelle.put(p, (double) 50000); // a faire varier en fonction du produit
				}	
		}	
	
	/*methode qui initialise le ratio*/
	
	public void initialiserRatio(){
		this.ratio.add(0.13);
		this.ratio.add(0.04);
		
	}
	
	/*methode qui initialise calendrierDemande*/
	
	public void initialiserCalendrierDemande(){
		
		for (Produit p : this
				.getProduits()){
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
	
	/* Actualisation de la fidelite a chaque step
	 * Cette fonction procède de la manière suivante:
	 * Pour chaque produit en vente, on crée un tableau [distributeur, intervalle([a, a+inverse du prix du produit/somme des prix du produit])]
	 * Tous ces intervalles sont inclus dans [0,1]
	 * 
	 * On répète alors x fois la procédure suivante (x étant proportionnel au nombre de concurrents sur le marché)
	 * Une variable aleatoire génère un nombre i entre 0 et 1
	 * Si i appartient à l'intervalle correspondant au distributeur d, d gagne en fidelité, les autres distributeurs perdent en fidelité
	 * 
	 * De cette manière, comme chaque distributeur possède un intervalle de longueur proportionnelle à l'inverse de son prix de vente,
	 * plus celui-ci est faible, plus il a de chance de gagner en fidélité client.
	 * 
	 * Après calibrage, on pourra décider de passer les longueurs d'intervalles en carré de l'inverse du prix 
	 * si les différences de prix sont négligeables devant le prix moyen.
	 */
	
	/*
	public void actualiserFidelite(){
		
		HashMap<Produit, HashMap<IDistributeur, ArrayList<Double>>> M = new HashMap <Produit, HashMap<IDistributeur, ArrayList<Double>>>();
		
		//Initialisation des variables
		double sum = 0; //Somme des prix des distributeurs 
		double a=0; //Fixation des intervalles correspondant a chaque distributeurs
		double i=0; //Variable aleatoire
		
		int nombre_iterations = 20*MarcheCons.distributeurs.size();
		
			for (Produit p : this.getProduits()){
				
				sum=0;
				a=0;
				M.put(p, null);
				
				for (IDistributeur d : MarcheCons.distributeurs){
					if (d.getPrixVente(p)!=0){
							sum+=1/d.getPrixVente(p);
							M.get(p).put(d, null);
						}
					}
				for (IDistributeur d : MarcheCons.distributeurs){
					if (d.getPrixVente(p)!=0){
							M.get(p).get(d).add(a);
							M.get(p).get(d).add(a+1/d.getPrixVente(p)/sum);
							a=a+1/d.getPrixVente(p)/sum;
						}
					}
				
				
				for (int j=0;j<nombre_iterations;j++){
					i=Math.random();
					for (IDistributeur d : MarcheCons.distributeurs){
						if (i>=M.get(p).get(d).get(0) && i < M.get(p).get(d).get(1)){ //La variable aléatoire se trouve dans l'intervalle du distri D
							//Augmente le ratio de fidelite de d
							this.fidelite.get(getIndexFidelite(d,p)).setPart(this.getPart(d,p)+VARIATION_FIDELITE/nombre_iterations);
						}
						else{
							//reduit le ratio de fidelite des autres distributeurs
							this.fidelite.get(getIndexFidelite(d,p)).setPart(this.getPart(d,p)-VARIATION_FIDELITE/nombre_iterations/(MarcheCons.distributeurs.size()-1));
						}
					}
				}	
			}
		}
	*/
	
	/*methode qui actualise la demande à chaque step*/
	
	/*public void actualiserDemande(){ 
		for (Produit p : this.getProduits()){ 
			double demandeDuStep = this.getDemande(p, Monde.LE_MONDE.getStep())-ALPHA*this.getPrixMoyen(p);
			
			this.demandeComposanteContinue.put(p,demandeDuStep);
			this.demandeComposanteAleatoire.put(p, this.demandeComposanteContinue.get(p)*(1+2*Math.random())*this.pourcentageIncertitudeVentes.get(p));
		}
	}
	*/
	
	/*methode qui repartit les ventes du step*/
	
	public void repartirVentes(){
		this.ventesEffectuees=new ArrayList<CommandeDistri>();
		for (Produit p : this.getProduits()){
			double demandeTotale = this.demandeComposanteContinue.get(p)+this.demandeComposanteAleatoire.get(p);
			for (IDistributeur d : MarcheCons.distributeurs){
				for (int i=0;i<transformateurs.size();i++){
					//this.ventesEffectuees.add(new CommandeDistri(d, transformateurs.get(i), p, this.ratio.get(i)*this.getPart(d, p)*demandeTotale, d.getPrixVente(p), Monde.LE_MONDE.getStep(), true));//! au step du prix de vente
					//rajouter ratio transfo pour la quantité
				}			
			}
		}	
	}

	@Override
	public void next() {
		this.repartirVentes();
		//this.actualiserDemande();
		// TODO Auto-generated method stub
		
	}
	
	}

