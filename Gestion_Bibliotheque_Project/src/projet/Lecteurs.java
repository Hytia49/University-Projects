package projet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;import javax.print.Doc;

/**
 * Classe Lecteurs
 * @author Clara LAMBERT et Mathilde MOREAU DE BELLAING
 *
 */
public abstract class Lecteurs {
	
	// Initialisation des variables.
	private String nom;
	private String adresseMail;
	private String nomFacOuInstitut;
	private int nombreMaxEmprunt;
	private int nombreActuelEmprunt;
	private int delai;
	private HashMap<LocalDate, Document> historiqueEmprunt = new HashMap<LocalDate, Document>() ;
	private ArrayList<Document> listeDocLecteur = new ArrayList<Document>();
	



	/**
	 * Constructeur de la classe Lecteurs
	 * @param nom
	 * @param adresseMail
	 * @param nomFacOuInstitut
	 * @param nombreMaxEmprunt
	 * @param delai
	 */
	public Lecteurs(String nom, String adresseMail, String nomFacOuInstitut, int nombreMaxEmprunt,  int delai) {
		this.nombreActuelEmprunt=0;
		this.nom = nom;
		this.adresseMail = adresseMail;
		this.nomFacOuInstitut = nomFacOuInstitut;
		this.nombreMaxEmprunt = nombreMaxEmprunt;
		this.delai = delai;


	}


	/**
	 * getter de nom 
	 * @return nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * getter de adresseMail
	 * @return l'adresse mail 
	 */
	public String getAdresseMail() {
		return adresseMail;
	}

	/**
	 * getter de nomFacOuInstitut
	 * @return le nom de la fac ou de l'institut d
	 */
	public String getNomFacOuInstitut() {
		return nomFacOuInstitut;
	}
	
	/**
	 * getter de NombreMaxEmprunt
	 * @return le nombre maximum d'emprunt de document 
	 */
	public int getNombreMaxEmprunt() {
		return nombreMaxEmprunt;
	}
	
	
	/**
	 * getter de nombreActuelEmprunt
	 * @return le nombre d'emprunt actuel 
	 */
	public int getNombreActuelEmprunt() {
		return nombreActuelEmprunt;
	}
	
	/**
	 * getter de delai
	 * @return le delai de pr�t
	 */
	public int getDelai() {
		return delai;
	}

	
	
	/**
	 * getter de listeDocLecteur
	 * @return la liste de document emprunt� du lecteur
	 */
	public ArrayList<Document> getListeDocLecteur() {
		return listeDocLecteur;
	}



	/**
	 * setter de nom d'un lecteur
	 * @param newNom d'un lecteur
	 */
	public void setNom(String newNom) {
		this.nom = newNom;
	}
	
	/**
	 * setter de adresse mail d'un lecteur
	 * @param newAdresseMail d'un lecteur
	 */
	public void setAdresseMail(String newAdresseMail) {
		this.adresseMail = newAdresseMail;
	}

	/**
	 * setter de nomFacOuInstitut
	 * @param newNomFacOuInstitut d'un lecteur
	 */
	public void setNomFacOuInstitut(String newNomFacOuInstitut) {
		this.nomFacOuInstitut = newNomFacOuInstitut;
	}
	
	/**
	 * setter de nombreMaxEmprunt
	 * @param newNombreMaxEmprunt d'un lecteur
	 */
	public void setNombreMaxEmprunt(int newNombreMaxEmprunt) {
		this.nombreMaxEmprunt = newNombreMaxEmprunt;
	}
	
	/**
	 * setter de delai
	 * @param delai 
	 */
	public void setDelai(int delai) {
		this.delai = delai;
	}

	/**
	 * setter de listeDocLecteur
	 * @param listeDocLecteur 
	 */
	public void setListeDocLecteur(ArrayList<Document> listeDocLecteur) {
		this.listeDocLecteur = listeDocLecteur;
	}

	
	/**
	 * M�thode d'incr�mentation du nombre d'emprunt d'un lecteur
	 */
	public void nombreEmpruntPlus() {
		 nombreActuelEmprunt = nombreActuelEmprunt + 1;
	}
	
	/**
	 * M�thode de decr�mentation du nombre d'emprunt d'un lecteur
	 */
	public void nombreEmpruntMoins() {
		nombreActuelEmprunt = nombreActuelEmprunt - 1;
	}
	
	/**
	 * M�thode qui permet de connaitre la date du jour de l'emprunt d'un document
	 * @param doc
	 */
	public void ajouterEmpruntHistorique(Document doc) {
		this.historiqueEmprunt.put(LocalDate.now(), doc);
	}

	/**
	 * M�thode qui ajoute un document dans la liste du lecteur
	 * @param doc
	 */
	public void ajouterDocumentListe(Document doc) {
		this.listeDocLecteur.add(doc);
	}
	
	/**
	 * M�thode qui enl�ve un document de la liste de document emprunter du lecteur
	 * @param doc
	 */
	public void enleverDocumentListe(Document doc) {
		this.listeDocLecteur.remove(doc);
	}
	/**
	 * �volution de la date de rendu de chaque document emprunt� par le lecteur en fonction du nouveau d�lai d'emprunt
	 * @param nouveauDelai
	 */
	public void modifierDelai(int nouveauDelai) {
		int diffDelai = nouveauDelai - this.delai; // Initialisation de la variable diffDelai qui est la diff�rence entre le nouveau d�lai et le d�lai actuel d'emprunt.
		if(diffDelai>=0) { // Si diffDelai est sup�rieure ou �gale � 0
			for (Document document : listeDocLecteur) { // Parcours la liste de document du lecteur.
				document.setDateRendre(document.getDateRendre().plusDays(diffDelai)); // Appelle la m�thode setDateRendre.
			}
		}
		else { // diffDelai est inf�rieure � 0.
			for (Document document : listeDocLecteur) { // Parcours la liste de document du lecteur.
				document.setDateRendre(document.getDateRendre().minusDays(diffDelai)); // Appelle la m�thode setDateRendre.
			}
		}
		setDelai(nouveauDelai); // Appelle la m�thode setDelai.		
	}
	
	
	public String toString() {
		return "Nom : " + nom + "\nAdresse Mail : " + adresseMail + "\nNom de la facult�/institut d'�tude ou de travail : " + nomFacOuInstitut +"\nNombre maximum d'emprunt : " + nombreMaxEmprunt + "\nNombre actuel d'emprunt : " + nombreActuelEmprunt + "\nHistorique d'emprunt :\n" + historiqueEmprunt + "\nListe de document(s) emprunt�(s) : " + listeDocLecteur;
	}
	
}
