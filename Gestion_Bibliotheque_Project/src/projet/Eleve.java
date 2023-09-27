package projet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe Eleve
 * @author Clara LAMBERT et Mathilde MOREAU DE BELLAING
 *
 */
public class Eleve extends Lecteurs{
	
	private String adressePostale;
	
	/**
	 * Constructeur de la classe Eleve
	 * @param nom
	 * @param adresseMail
	 * @param nomFacOuInstitut
	 * @param nombreMaxEmprunt
	 * @param delai
	 * @param adressePostale
	 */
	public Eleve(String nom, String adresseMail, String nomFacOuInstitut, int nombreMaxEmprunt, int delai, String adressePostale) {
		super(nom, adresseMail,nomFacOuInstitut, nombreMaxEmprunt, delai);
		this.adressePostale = adressePostale;
	}
	

	/**
	 * getter de adressePostale.
	 * @return the adressePostale
	 */
	public String getAdressePostale() {
		return adressePostale;
	}

	/**
	 * setter de adressePostale
	 * @param adressePostale the adressePostale to set
	 */
	public void setAdressePostale(String adressePostale) {
		this.adressePostale = adressePostale;
	}
	
	
	public String toString() {
		return super.toString() + "\nAdresse postale : " + adressePostale + "\n\n";
	}
	
}
