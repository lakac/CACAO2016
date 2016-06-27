package abstraction.equipe2;
import abstraction.commun.*;

public class Constante {

public static final double RATIO_TRANSFO = 1.5;	
public static final double CHARGES_FIXES=13003370;
public static final double RATIO_TRANSFORMATION_50=0.5;
public static final double RATIO_TRANSFORMATION_60=0.6;
public static final double RATIO_TRANSFORMATION_70=0.7;
public static final double TRESORERIE_INITIALE=300000;
public static final double COUT_DE_TRANSFORMATION=3;
public static final double RATIO_TRANSFORMATION=0.6;
public static final double DEMANDE_MONDE=2./3.;
public static final double DISTANCE_MONDE=4000;
public static final double PRIX_MINIMUM=0.9;
public static final double VARIATION_PRIX=0.2;

public static final double ACHAT_SANS_PERTE=1;
public static final double PERTE_MINIMALE = 0.2;
public static final double VARIATION_PERTE =0.1;
public static final double MARGE_DE_SECURITE = PERTE_MINIMALE+VARIATION_PERTE;
public static final double MARGE = 0.3;

public static final double COUT_UNITAIRE_TRANSPORT = 0.01;

public static final double COUT_STOCK_UNITAIRE = 18.0;
public static final Produit PRODUIT_50 =new Produit("50%" , 0.5);
public static final Produit PRODUIT_60 =new Produit ("60%" , 0.6);
public static final Produit PRODUIT_70 =new Produit("70%",0.7);
public static final Produit CACAO=new Produit("100%",1);
}
