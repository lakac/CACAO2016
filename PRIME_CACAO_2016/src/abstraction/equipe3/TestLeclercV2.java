package abstraction.equipe3;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.*;

public class TestLeclercV2 {

	public static void Classerparprix(Produit p,List<Catalogue> catalogues){ 
		List<Catalogue> liste = new ArrayList<Catalogue>();
		List<Catalogue> transfo=new ArrayList<Catalogue>();
		for (Catalogue c : catalogues){
			transfo.add(c);
		}
		int n;
		while(transfo.size() !=0){
			double a=transfo.get(0).getTarif(p).getPrixTonne();
			n=0;
			for (int j=0;j<transfo.size();j++){
				if (transfo.get(j).getTarif(p).getPrixTonne()<a){
					a=transfo.get(j).getTarif(p).getPrixTonne();
					n=j;
					liste.add(transfo.get(j));
				}	
			}
		transfo.remove(n);	
		}
		System.out.println(liste.get(1).getTarif(p).getPrixTonne());

	}
	
	public static void testClasserParPrix(){
		Produit p = new Produit("50%",50);
		List<Catalogue> catas = new ArrayList<Catalogue>();
		catas.add(new Catalogue());
		Tarif t1 = new Tarif(25, new ArrayList<Plage>());
		catas.get(0).add(p, t1);
		catas.add(new Catalogue());
		Tarif t2 = new Tarif(9,new ArrayList<Plage>());
		catas.get(1).add(p, t2);
		catas.add(new Catalogue());
		Tarif t3 = new Tarif(17,new ArrayList<Plage>());
		catas.get(2).add(p,t3);
		//System.out.println(catas.get(2).getTarif(p).getPrixTonne());
		Classerparprix(p, catas);

		
	}
	
	public static void main(String[] args) {
		testClasserParPrix();
		/*ArrayList<Double[]> stock = new ArrayList<Double[]>();
		Double[] l = {0.0,0.0,0.0};
		for (int i=0;i<3;i++){
			stock.add(l);
		}
		stock.get(2)[2]=5.0;
		System.out.println(stock.get(2)[2]);*/
		
		// TODO Auto-generated method stub

	}

}
