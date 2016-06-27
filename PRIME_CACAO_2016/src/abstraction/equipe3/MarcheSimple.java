package abstraction.equipe3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateurD;
import abstraction.commun.MarcheCons;
import abstraction.commun.Produit;
import abstraction.equipe3.*;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

public class MarcheSimple implements Acteur {
	
	private String nom;
	public static MarcheSimple LE_MARCHE_SIMPLE;
	private List<Produit> produits; //liste des produits
	public static ArrayList<IDistributeur> distributeurs; //liste des distributeurs
	public static ArrayList<ITransformateurD> transformateurs; //liste des transformateurs
	private List<CommandeDistri> ventesEffectuees; //vente reelles donnees aux distri a chaque step
	private HashMap <Produit,Double> demandeAnnuelle;  //volume des ventes annuelles d'un produit
	private List<Demande> calendrierDemande; //volume des ventes par step d'un produit
	private HashMap<IDistributeur,List<Double[]>> partDeMarche; //part de marche du distributeur d pour un produit p de marque t
	
	public MarcheSimple(String nom, List<Produit> produits){
		this.nom=nom;
		this.produits=produits;
		this.distributeurs= new ArrayList<IDistributeur>();
		this.transformateurs = new ArrayList<ITransformateurD>();
		this.ventesEffectuees= new ArrayList<CommandeDistri>();
		this.demandeAnnuelle = new HashMap<Produit,Double>();
		this.partDeMarche = new HashMap<IDistributeur, List<Double[]>>();
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return this.nom;
	}
	public static void ajouterDistributeur(IDistributeur distributeur){
		MarcheCons.distributeurs.add(distributeur);
	}
	public static void ajouterTransformateur(ITransformateurD transformateur){
		MarcheCons.transformateurs.add(transformateur);
	}
	public double getCD(Produit p, int s){
		double res = 0;
		int step = s%26;
		for (Demande d : this.calendrierDemande){
			if (d.getProduit()==p && d.getStep()==step)
				res = d.getQuantite();
		}
		return res;
	}
/*methode qui initialise demandeAnnuelle*/
	
	public void initialiserDemandeAnnuelle(){
		this.demandeAnnuelle = new HashMap<Produit,Double>();
		for (Produit p : this.produits){
			this.demandeAnnuelle.put(p, (double) 50000); // a faire varier en fonction du produit
				}	
		}	
	
	/*methode qui initialise le ratio*/
	
	public void initialiserPartDeMarche(){
		Double[] ratio = {0.2,0.2,0.2}; //ratios initiaux de Leclerc et Carrefour
		Double[] ratio3 = {0.6,0.6,0.6}; //ratio du troisieme distributeur
		List<Double[]> liste = new ArrayList<Double[]>();
		liste.add(ratio);
		List<Double[]> liste3 = new ArrayList<Double[]>();
		liste3.add(ratio3);
		this.partDeMarche = new HashMap<IDistributeur, List<Double[]>>();
		this.partDeMarche.put(this.distributeurs.get(0), liste);
		this.partDeMarche.put(this.distributeurs.get(1), liste);
		this.partDeMarche.put(this.distributeurs.get(2), liste3);
	}
	
	/*methode qui initialise calendrierDemande*/
	
	public void initialiserCalendrierDemande(){
		
		for (Produit p : this.produits){
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
	
	/*methode qui renvoie une demande continue*/
	
	public List<CommandeDistri> DemandeContinue(){
		List<CommandeDistri> DemandeContinue = new ArrayList<CommandeDistri>();
		int step = Monde.LE_MONDE.getStep();
		for (IDistributeur d : this.distributeurs){
			for (ITransformateurD t : this.transformateurs){
				for (Produit p : this.produits){
					double q = this.getCD(p,step);
					CommandeDistri com = new CommandeDistri(d,t,p,q,d.getPrixVente(p, t),step,true);
					DemandeContinue.add(com);
				}
			}
		}
		return DemandeContinue;
	}
	
	/*methode qui ajoute la demande aleatoire a une liste*/
	
	public List<CommandeDistri> DemandeAleatoire(List<CommandeDistri> DemandeContinue){
		List<CommandeDistri> DemandeAleatoire = new ArrayList<CommandeDistri>();
		for (CommandeDistri com : DemandeContinue){
			IDistributeur acheteur = com.getAcheteur();
			ITransformateurD vendeur = com.getVendeur();
			Produit produit = com.getProduit();
			double quantite = com.getQuantite()*(1+(10*Math.random()-5)/100);
			double prixtonne = com.getPrixTonne();
			int step = com.getStepLivraison();
			boolean valid = com.getValidation();
			CommandeDistri c = new CommandeDistri(acheteur,vendeur,produit,quantite,prixtonne,step,valid);
			DemandeAleatoire.add(c);
		}
		return DemandeAleatoire;
	}
	
	
	
	/*fonction qui set venteEffectuees en utilisant les methodes precedentes*/
	
	public void repartirVentes(){
		this.ventesEffectuees= new ArrayList<CommandeDistri>();
		List<CommandeDistri> demande = this.DemandeAleatoire((this.DemandeContinue()));
		for (CommandeDistri com : demande){
			this.ventesEffectuees.add(com);
		}
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
	
	@Override
	public void next() {
		this.repartirVentes();
		// TODO Auto-generated method stub
		
	}

}
