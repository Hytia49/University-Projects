package projet;

/**
 * Classe Livre
 * @author Clara LAMBERT et Mathilde MOREAU DE BELLAING
 *
 */
public class Livre extends Document {
	
	// Initialisation des variables.
	private String nomAuteur;
	private double taux;
	
	/**
	 * Constructeur de la classe Livre
	 * @param reference
	 * @param titre
	 * @param prix
	 * @param nomAuteur
	 * @param taux
	 */
	public Livre(String reference, String titre, double prix, String nomAuteur, double taux) {
		super(reference, titre, prix);
		this.nomAuteur = nomAuteur;
		this.taux = taux;

	}	
	
	/**
	 * gatter de nomAuteur
	 * @return the nomAuteur
	 */
	public String getNomAuteur() {
		return nomAuteur;
	}

	/**
	 * getter de taux
	 * @return the taux
	 */
	public double getTaux() {
		return taux;
	}


	/**
	 * setter de nomAuteur
	 * @param nomAuteur the nomAuteur to set
	 */
	public void setNomAuteur(String nomAuteur) {
		this.nomAuteur = nomAuteur;
	}

	/**
	 * setter de taux
	 * @param taux the taux to set
	 */
	public void setTaux(double taux) {
		this.taux = taux;
	}

	
	public String toString() {
		return super.toString() + "\nNom de l'auteur : " + nomAuteur + "\nTaux de remboursement : " + taux;
	}

}
