package projet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe Enseignant
 * @author Clara LAMBERT et Mathilde MOREAU DE BELLAING
 *
 */
public class Enseignant extends Lecteurs {
	
	private String numBureau; // Obligation de mettre le numéro de teléphone en String car certains numéros ne fonctionnent pas en type long (trop long pour le type)

	/**
	 * Constructeur de la classe Enseignant
	 * @param nom
	 * @param adresseMail
	 * @param nomFacOuInstitut
	 * @param nombreMaxEmprunt
	 * @param delai
	 * @param numBureau
	 */
	public Enseignant(String nom, String adresseMail, String nomFacOuInstitut, int nombreMaxEmprunt, int delai, String numBureau) {
		super(nom, adresseMail,nomFacOuInstitut, nombreMaxEmprunt,  delai);
		this.numBureau = numBureau;
	}
	
	/**
	 * getter de numBureau
	 * @return the numBureau
	 */
	public String getNumBureau() {
		return numBureau;
	}

	/**
	 * setter de numBureau
	 * @param numBureau the numBureau to set
	 */
	public void setNumBureau(String numBureau) {
		this.numBureau = numBureau;
	}
	
	
	public String ToString() {
		return super.toString() + "\nNuméro de téléphone du bureau : " + numBureau + "\n\n";
	}

	
	
}
