package projet;

import java.time.LocalDate;

/**
 * Classe Document
 * @author Clara LAMBERT et Mathilde MOREAU DE BELLAING
 *
 */
public abstract class Document {
	
	//Initialisation des variables.
	private String reference;
	private String titre;
	private double prix;
	private boolean disponible = true;
	private int nbreExemplaire;
	private LocalDate dateRendre;
	
	
	/**
	 * Constructeur de la classe Document 
	 * @param reference
	 * @param titre
	 * @param prix
	 */
	public Document(String reference, String titre, double prix) {
		this.reference = reference;
		this.titre = titre;
		this.prix = prix;
		this.disponible = true;
		this.nbreExemplaire = 1;
	}
	
	
	/**
	 * MGetter de disponible
	 * @return disponible
	 */
	public boolean isDisponible() {
		return this.disponible;
	}
		
	/**
	 * getter de reference
	 * @return reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * getter de titre
	 * @return titre
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * getter de prix
	 * @return prix
	 */
	public double getPrix() {
		return prix;
	}
	/**
	 * getter de nbreExemplaire
	 * @return nbreExemplaire
	 */
	public int getNbreExemplaire() {
		return nbreExemplaire;
	}


	/**
	 * getter de dateRendre
	 * @return dateRendre
	 */
	public LocalDate getDateRendre() {
		return dateRendre;
	}
	
	/**
	 * setter de reference
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * setter de titre
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * setter de prix
	 * @param prix the prix to set
	 */
	public void setPrix(double prix) {
		this.prix = prix;
	}
	

	/**
	 * setter de disponible
	 * @param disponible the disponible to set
	 */
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}


	/**
	 * setter de nbreExemplaire
	 * @param nbreExemplaire the nbreExemplaire to set
	 */
	public void setNbreExemplaire(int nbreExemplaire) {
		this.nbreExemplaire = nbreExemplaire;
	}

	/**
	 * setter de dateRendre
	 * @param dateRendre the dateRendre to set
	 */
	public void setDateRendre(LocalDate dateRendre) {
		this.dateRendre = dateRendre;
	}

	/**
	 * Méthode d'incrémentation de nbreExemplaire d'un document
	 */
	public void nbreExemplairePlus() {
		nbreExemplaire = nbreExemplaire + 1;
	}
	
	/**
	 * Méthode de decrémentation de nbreExemplaire d'un document
	 */
	public void nbreExemplaireMoins() {
		nbreExemplaire = nbreExemplaire - 1;
	}
	
	
	/**
	 * Méthode de prolongation du pret
	 * @param duree
	 * @return date prolongée
	 */
	public LocalDate prolongementPret(long duree) {
		return dateRendre.plusDays(duree);
		
	}
	
	public String toString() {
		String ret ="Reference : " + reference + "\nTitre : " + titre + "\nPrix : " + prix + "\nNombre d'exemplaire : " + nbreExemplaire + "\nDisponibilité : " + disponible;
		if (!this.disponible) ret +="\nDate de rendu : " + dateRendre;
		return ret;
	}
	
}
