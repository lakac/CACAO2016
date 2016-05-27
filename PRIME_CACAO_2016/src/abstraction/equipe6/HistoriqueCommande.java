
	
package abstraction.equipe6;

import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Indicateur;

public class HistoriqueCommande {

	private List<Indicateur> historiquedescommandes;


public HistoriqueCommande (List<Indicateur> hdc){
	this.historiquedescommandes=hdc;
	
}
public HistoriqueCommande(){
	this.historiquedescommandes = new ArrayList<Indicateur>();
}
public List<Indicateur> getHistoriquedesCommandes() {
	return this.historiquedescommandes;
}

}

