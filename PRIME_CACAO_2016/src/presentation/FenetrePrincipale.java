package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.CtrlBtnNext;
import control.CtrlCheckBoxHistorique;
import control.CtrlCheckBoxJournal;
import control.CtrlJTextField;
import control.CtrlCheckBoxGraphique;
import control.CtrlLabelEtape;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.commun.MondeV1;
import abstraction.equipe4.IndicateurDouble;

/**
 * Classe modelisant la fenetre principale de l'interface.
 * C'est cette classe qui comporte la methode main de l'application.
 * 
 * @author Romuald Debruyne
 */
public class FenetrePrincipale extends JFrame {

	private static final long serialVersionUID = 1L;

	public FenetrePrincipale() {
		super("Prime CACAO");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		Monde.LE_MONDE = new MondeV1();
		Monde.LE_MONDE.peupler();
		this.setLayout(new BorderLayout());

		// LABEL Etape indiquant l'etape a laquelle on est.
		JLabel labelEtape = new JLabel("Etape : 0");
		Monde.LE_MONDE.addObserver(new CtrlLabelEtape(labelEtape));
		this.add(labelEtape, BorderLayout.NORTH);

		// Indicateurs
		JPanel pLab = new JPanel();
		pLab.setLayout(new BoxLayout(pLab, BoxLayout.Y_AXIS));
		JPanel pGauche = new JPanel();
		pGauche.setLayout(new BoxLayout(pGauche, BoxLayout.Y_AXIS));

		ArrayList<Indicateur> indicateurs = Monde.LE_MONDE.getIndicateurs();
		for (Indicateur i : indicateurs){
			JPanel pIndic = new JPanel();
			pIndic.setLayout(new FlowLayout(FlowLayout.RIGHT));

			// Nom de l'indicateur
			JLabel lIndic = new JLabel( i.getNom());
			lIndic.setAlignmentX(RIGHT_ALIGNMENT);
			pIndic.add(lIndic);

			// Case a cocher "Graphique" permettant d'afficher/cacher le graphique de l'indicateur
			JCheckBox cGraphiqueIndic = new JCheckBox("Graphique"); 
			FenetreGraphique graphique = new FenetreGraphique(i.getNom(), 800, 600);
			graphique.ajouter(i.getCourbe());
			if (i instanceof IndicateurDouble){
				graphique.ajouter(((IndicateurDouble) i).getCourbe2());
			}
			// Controleur permettant quand on clique sur la fermeture 
			// de la fenetre graphique de mettre a jour la case a cocher "graphique"
			// et quand on clique sur la case a cocher d'afficher/masquer le graphique
			CtrlCheckBoxGraphique ctg = new CtrlCheckBoxGraphique(graphique, cGraphiqueIndic);
			i.addObserver(ctg);
			cGraphiqueIndic.addActionListener(ctg);
			graphique.addWindowListener(ctg);
			cGraphiqueIndic.setAlignmentX(RIGHT_ALIGNMENT);
			pIndic.add(cGraphiqueIndic);

			// Case a cocher "Historique" permettant d'afficher/cacher l'historique de l'indicateur
			JCheckBox cHistorique = new JCheckBox("Historique");
			FenetreHistorique fenetreHistorique = new FenetreHistorique(i);
			CtrlCheckBoxHistorique cth = new CtrlCheckBoxHistorique(cHistorique, fenetreHistorique);
			fenetreHistorique.addWindowListener(cth);
			i.getHistorique().addObserver(cth);
			cHistorique.addActionListener(cth);
			cHistorique.setAlignmentX(RIGHT_ALIGNMENT);
			pIndic.add(cHistorique);

			// Champ de saisie permettant de modifier la valeur de l'indicateur
			JTextField tIndic = new JTextField(20);
			tIndic.setText(i.getValeur()+"");
			CtrlJTextField controlJTextField = new CtrlJTextField(tIndic, i);
			tIndic.addActionListener(controlJTextField);
			i.addObserver(controlJTextField);
			tIndic.setAlignmentX(RIGHT_ALIGNMENT);

			pIndic.setMinimumSize(new Dimension(300,20));
			tIndic.setMinimumSize(new Dimension(300,20));
			pLab.add(pIndic);
			pGauche.add(tIndic);
		}
		JPanel pIndicateurs = new JPanel();
		pIndicateurs.setBorder(BorderFactory.createTitledBorder("Indicateurs"));

		pIndicateurs.setLayout(new BorderLayout());
		pIndicateurs.add(pLab, BorderLayout.WEST);
		pIndicateurs.add(pGauche, BorderLayout.CENTER);
		this.add(pIndicateurs, BorderLayout.CENTER);


		JPanel pDroit = new JPanel();
		pDroit.setLayout(new BoxLayout(pDroit, BoxLayout.Y_AXIS));
		pDroit.setBorder(BorderFactory.createTitledBorder("Journaux"));
		for (Journal j : Monde.LE_MONDE.getJournaux()) {
			JPanel pJournal = new JPanel();
			JLabel lJournal = new JLabel( j.getNom());
			lJournal.setAlignmentX(RIGHT_ALIGNMENT);
			pJournal.setLayout(new BorderLayout());
			pJournal.add(lJournal, BorderLayout.CENTER);
			JCheckBox cJournal = new JCheckBox("visible"); 
			FenetreJournal fenetreJournal = new FenetreJournal(j);
			fenetreJournal.setCheckBox(cJournal);
			CtrlCheckBoxJournal controlJournal = new CtrlCheckBoxJournal(cJournal, fenetreJournal);
			j.addObserver(controlJournal);
			cJournal.addActionListener(controlJournal);

			cJournal.setAlignmentX(RIGHT_ALIGNMENT);
			pJournal.add(cJournal, BorderLayout.EAST);
			pDroit.add(Box.createVerticalGlue());
			pDroit.add(pJournal);
		}
		this.add(pDroit, BorderLayout.EAST);

		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new CtrlBtnNext(Monde.LE_MONDE));
		this.add(btnNext, BorderLayout.SOUTH);
		this.pack();
	}

	public static void main(String[] args) {
		new FenetrePrincipale().setVisible(true);
	}
}
