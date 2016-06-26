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
	
/*methode qui initialise demandeAnnuelle*/
	
	public void initialiserDemandeAnnuelle(){
		for (Produit p : this.produits){
			this.demandeAnnuelle.put(p, (double) 50000); // a faire varier en fonction du produit
				}	
		}	
	
	/*methode qui initialise le ratio*/
	
	public void initialiserPartDeMarche(){
		Double[] ratio = {0.2,0.2,0.2}; //ratios initiaux de Leclerc et Carrefour
		Double[] ratio3 = {0.6,0.6,0.6}; //ratio du troisieme distributeur

		
		
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

	
	
	
	
	
	
	
	
	
	
	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}

}
