package projet;

/**
 * Classe Periodique 
 * @author Clara LAMBERT et Mathilde MOREAU DE BELLAING
 *
 */
public class Periodique extends Document {
	
	// Initialisation des variables.
	private int numero;
	private int annee;
	
	/**
	 * Constructeur de la classe Periodique
	 * @param reference
	 * @param titre
	 * @param prix
	 * @param numero
	 * @param annee
	 */
	public Periodique(String reference, String titre, double prix, int numero, int annee) {
		super(reference, titre, prix);
		this.numero = numero;
		this.annee = annee;

	}


	/**
	 * getter de numero
	 * @return the numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * getter de annee
	 * @return the annee
	 */
	public int getAnnee() {
		return annee;
	}


	/**
	 * setter de numero
	 * @param numero the numero to set
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * setter de annee
	 * @param annee the annee to set
	 */
	public void setAnnee(int annee) {
		this.annee = annee;
	}
	
	
	public String toString() {
		return super.toString() + "\nNuméro : " + numero + "\nAnnee de parution : " + annee ;
	}
	
}
