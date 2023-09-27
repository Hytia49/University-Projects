package projet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe Centre
 * Programme qui g�re une biblioth�que.
 * @author Clara LAMBERT et Mathilde MOREAU DE BELLAING
 *
 */
public class Centre {
	
	private static ArrayList<Document> listeDocument = new ArrayList<Document>(); // Liste de document
	private static ArrayList<Lecteurs> listeLecteurs = new ArrayList<Lecteurs>(); // Liste de lecteur
	
		
	/**
	 * M�thode qui permet de rechercher un document dans la liste de document du centre.
	 * @param ref
	 * @return i ou -1
	 * @throws IllegalArgumentException
	 */
	public static int chercherDocument(String ref) throws IllegalArgumentException {
		if (Objects.isNull(ref)) throw new IllegalArgumentException("Le document est nul "); // Si le document est nul alors on l�ve une exception.
		for(int i = 0; i < listeDocument.size(); i++) { // Parcours la liste de document.
			if(listeDocument.get(i).getReference().equals(ref)) { // Si la r�f�rence du document est �gale � la r�f�rence du document recherch�
				return i; // Retourne l'index du document qui a la m�me r�f�rence que le document recherch�.
			}
		}
		return -1; // Retourne -1 si aucun document avec la m�me r�f�rence n'est trouv�.
	}
	
	
	/**
	 * M�thode qui permet de rechercher un lecteur dans la liste de lecteur du centre.
	 * @param adresseMail
	 * @return i ou -1
	 * @throws IllegalArgumentException
	 */
	public static int chercherLecteur(String adresseMail) throws IllegalArgumentException {
		if (Objects.isNull(adresseMail)) throw new IllegalArgumentException("Le lecteur est nul "); 
		for(int i = 0; i < listeLecteurs.size(); i++) { // Parcours la liste de document
			if(adresseMail.equals(listeLecteurs.get(i).getAdresseMail())) { // Si l'adresse mail du lecteur recherch� est �gal � l'adresse mail d'un lecteur dans la liste de lecteur.
				return i; // Retourne l'index du lecteur qui a la m�me adresse mail que le lecteur recherch�.
			}
		}
		return -1; // Retourne -1 si aucun lecteur avec la m�me adresse mail n'est trouv�.
	}
	
	/**
	 * M�thode qui recherche un document dans la liste de document d'un lecteur
	 * @param doc
	 * @param lect
	 * @return i ou -1
	 */
	public static int chercherDocumentDansListeLecteur(Document doc, Lecteurs lect) {
		for(int i = 0; i <= lect.getListeDocLecteur().size(); i++) { // Parcours la liste des documents d'un lecteur
			if(doc.getReference().equals(lect.getListeDocLecteur().get(i).getReference())) { // Si la r�f�rence du document recherch� dans la liste de document d'un lecteur est �gale � la r�f�rence d'un document dans la liste de document d'un lecteur
				return i; // Retourne l'index du document dans la liste de document d'un lecteur.
			}
		}
		return -1; // Retourne -1 si aucun document avec la m�me r�f�rence dans la liste n'est trouv�.
	}
	
		
	/**
	 * M�thode qui permet d'ajouter un document, elle ne retourne rien.
	 * @param doc
	 * @throws IllegalArgumentException
	 */
	public static void ajouterDocument(Document doc) throws IllegalArgumentException {
		int compteur = 1;
		boolean erreur = false;
		for (Document document : listeDocument) {
			if (doc.getReference().equals(document.getReference()) && (document instanceof Periodique || doc instanceof Periodique)) {
				erreur = true;
			}
		}
		if (erreur) {
			System.out.println("\nErreur dans l'ajout du document !");
		}
		else {
			for(Document document : listeDocument) { // Parcours la liste de document
				
				if(doc instanceof Livre) { //Si le document est un Livre.
					if (chercherDocument(doc.getReference()) != -1) { // Si le Livre que l'on veut ajouter est dans la liste de document
						document.nbreExemplairePlus(); //Appelle la m�thode d'incr�mentation dans Document
						compteur ++; // +1 au compteur.
					}
				}else { // Si le document n'est pas un Livre donc est un Periodique.
					if (chercherDocument(doc.getReference()) != -1) {  // Si le Periodique que l'on veut ajouter est dans la liste de document
						throw new IllegalArgumentException("\nCe p�riodique est d�j� dans la liste ! ");
					}
				}
			}		
			doc.setNbreExemplaire(compteur); // On appelle le setter de nbreExemplaire et on y change la valeur avec compteur.
			listeDocument.add(doc); // On ajoute � la liste de document du centre le document que l'on veut ajouter.
			System.out.println("\nLe document " + doc.getReference() + " | " + doc.getTitre() + " a bien �t� ajout� !");
		}
		
	}
	
	
	/**
	 * M�thode qui permet d'ajouter un lecteur, elle ne retourne rien.
	 * @param lect
	 * @throws IllegalArgumentException
	 */
	public static void ajouterLecteur(Lecteurs lect) throws IllegalArgumentException {
		if(chercherLecteur(lect.getAdresseMail()) == -1) { // Si le lecteur n'est pas dans la liste de lecteur du centre
			listeLecteurs.add(lect); // On ajoute le lecteur dans la liste de lecteur du centre.
			System.out.println("\nLe lecteur " + lect.getNom() + " | " + lect.getAdresseMail() + " a bien �t� ajout� !");
		}
		else {
			throw new IllegalArgumentException("\nLe lecteur que vous souhaitez ajouter existe d�j� ! ");
		}
	}
	
	
	
	/**
	 * M�thode qui supprime un document, elle ne retourne rien.
	 * @param ref
	 * @throws IllegalArgumentException
	 */
	public static void supprimerDocument(String ref) throws IllegalArgumentException {
		int index = chercherDocument(ref); // Initialisation de la variable index qui est l'index du document recherch� avec sa r�f�rence.
		if(index != -1) { // Si le document est dans la liste de document du centre.
			listeDocument.remove(index); // Enleve le document de la liste de document du centre.
			System.out.println("\nLe document avec la r�f�rence suivante : " + ref + " a bien �t� supprim�.");
		}
		else {
			throw new IllegalArgumentException("\nLe document n'existe pas ! ");
		}
	}
	
	
	/**
	 * M�thode qui supprime un lecteur, elle ne renvoie rien.
	 * @param lect
	 * @throws IllegalArgumentException
	 */
	public static void supprimerLecteur(String adresseMail) throws IllegalArgumentException {
		int index = chercherLecteur(adresseMail); // Initialisation de la variable index qui est l'index du lecteur recherch� avec son adresse mail.
		if(index != -1) { // Si le lecteur est dans la liste de lecteur du centre.
			listeLecteurs.remove(index); // Enl�ve le lecteur de la liste de lecteur du centre
			System.out.println("\nLe lecteur avec l'adresse mail suivante : " + adresseMail + " a bien �t� supprim�.");
		}
		else {
			throw new IllegalArgumentException("\nLe lecteur que vous souhaiter supprimer n'existe pas ! ");
		}
	}
	
	
	
	/**
	 * M�thode qui affiche tous les documents du centre.
	 * @throws IllegalArgumentException
	 */
	public static void afficherDocument() throws IllegalArgumentException {
		if (!listeDocument.isEmpty()) {	// Si la liste de document n'est pas vide.
			for(Document document : listeDocument) { // Parcours la liste des documents du centre.
				System.out.println("\n---------------------\n");
				System.out.println(document.toString()); // Affiche le document.
			}
		}
		else {	
			throw new IllegalArgumentException("\nIl n'y a aucun document � afficher. ");
		}
	}	
	/**
	 * M�thode qui affiche le ou les lecteur(s) ou donne une exception s'il n'y en a aucun dans la liste
	 * @throws IllegalArgumentException
	 */
	public static void afficherLecteur() throws IllegalArgumentException {
		if(!listeLecteurs.isEmpty()) { // Si la liste de lecteur n'est pas vide.
			for(Lecteurs lect : listeLecteurs) { // Parcours la liste de lecteur du centre.
				System.out.println("\n---------------------\n");
				System.out.println(lect.toString()); // Affiche un lecteur.
			}
		}
		else {
			throw new IllegalArgumentException("\nIl n'y a aucun lecteur � afficher. ");
	
		}
	}	
	
	/**
	 * M�thode qui v�riei si le lecteur peut emprunter un document.
	 * @param lect
	 * @return true ou false
	 */
	public static boolean verifEmprunt(Lecteurs lect) {
		if(lect.getNombreActuelEmprunt() < lect.getNombreMaxEmprunt()) { // Si le nombre actuel d'emprunt du lecteur est inf�rieur � son nombre d'emprunt maximum.
			return true; // Retourne true.
		}
		return false; // Retourne false.
		
	}
	
	/**
	 * M�thode qui v�rifie si un lecteur peut rendre un document.
	 * @param lect
	 * @return true ou false
	 */
	public static boolean verifRendre(Lecteurs lect) {
		if(lect.getNombreActuelEmprunt() > 0) { // Si le lecteur � bien emprunt� au moins un document.
			return true; // Retourne true.
		}
		return false; // Retourne false.
	}
	
	
	/**
	 * M�thode qui permet d'emprunter un document, elle ne renvoie rien.
	 * @param ref
	 * @param adresseMail
	 * @throws IllegalArgumentException
	 */
	public static void emprunterDocument(String ref, String adresseMail) throws IllegalArgumentException {
		if(chercherLecteur(adresseMail) != -1 && chercherDocument(ref) != -1) { // Si le lecteur est dans la liste de lecteur du centre et si le document est dans la liste de document du centre.
			Lecteurs lect = listeLecteurs.get(chercherLecteur(adresseMail)); // Cr�ation d'un objet Lecteurs.
			Document doc = listeDocument.get(chercherDocument(ref)); // Cr�ation d'un objet Document.
			if(verifEmprunt(lect) == true && doc.isDisponible() == true) { // Si le lecteur peut emprunter un document et si le document � emprunter est disponible.
					lect.ajouterEmpruntHistorique(doc); // Ajoute le document � l'historique d'emprunt du lecteur.
					lect.ajouterDocumentListe(doc); // Ajoute le document � la liste d'emprunt de document du lecteur.
					doc.setDisponible(false); // Rend le document non disponible.
					lect.nombreEmpruntPlus(); // Ajoute +1 au nombre d'emprunt actuel du lecteur.
					doc.setDateRendre(LocalDate.now().plusDays(lect.getDelai())); // Change la date de rendu du document pour le lecteur.
					for(Document document : listeDocument) { // Parcours la liste de document du centre.
						if(chercherDocument(ref) != -1) { // Si le document recherch� est dans la liste de document du centre.
							document.nbreExemplaireMoins(); // Enleve un exemplaire disponible � tous les documents ayant la m�me r�f�rence que le document emprunt�.
						}						
					}
					System.out.println("\nLe document " + doc.getReference() + " | " + doc.getTitre() + " est emprunt� par "+ lect.getNom() + " pour une dur�e de " + lect.getDelai() + " jour(s). ");
					System.out.println("\nIl reste " + doc.getNbreExemplaire() + " exemplaire(s) disponible(s) de ce document. ");
			}
			else {
				throw new IllegalArgumentException("\nLe document n'est pas disponible ou vous avez d�j� atteint le nombre maximum d'emprunt.");
			}
	}
	else {
		throw new IllegalArgumentException("\nLe document ou le lecteur n'existe pas. ");
	}
}
	
	/**
	 * M�thode qui permet de rendre un document, elle ne renvoie rien.
	 * @param ref
	 * @param adresseMail
	 * @throws IllegalArgumentException
	 */
	public static void rendreDocument(String ref, String adresseMail) throws IllegalArgumentException {
		if(chercherDocument(ref) != -1 && chercherLecteur(adresseMail) != -1) { // Si le document est dans la liste de document du centre et si le lecteur est dans la liste de lecteur du centre.
			Lecteurs lect = listeLecteurs.get(chercherLecteur(adresseMail)); // Cr�ation d'un objet Lecteurs.
			Document doc = listeDocument.get(chercherDocument(ref)); // Cr�ation d'un objet Document.
			if(verifRendre(lect) == true) {  // Si le lecteur � au moins un document emprunt�.
				if(doc.isDisponible() == false) { // Si le document est bien emprunt�.
					rendreDocumentRetard(doc); // Appelle la m�thode qui affiche un message SI le lecteur � rendu le document en retard.
					doc.setDisponible(true); // Rend le document de nouveau disponible.
					lect.nombreEmpruntMoins(); // Enl�ve 1 au nombre d'emprunt actuel du lecteur.
					lect.enleverDocumentListe(doc); // Enl�ve le document de la liste de document emprunt� du lecteur.
					for(Document document : listeDocument) { // Parcours la liste de document du centre.
						if(chercherDocument(ref) != -1) { // Si le document est dans la liste de document du centre.
							document.nbreExemplairePlus(); // Ajoute un exemplaire � tous les documents ayant la m�me r�f�rence.
						}
					}
					System.out.println("\nLe document " + doc.getReference() + " | " + doc.getTitre() + " est rendu. ");
					System.out.println(doc.getNbreExemplaire() + " exemplaire(s) de ce document sont d�sormais disponibles. ");
				}
				else {
					throw new IllegalArgumentException("\nLe document que vous souhaitez rendre est d�j� rendu. ");
				}
			}else {
				throw new IllegalArgumentException("\nVous n'avez rien � rendre ! ");
			}
			
		}else {
			throw new IllegalArgumentException("\nLe document ou le lecteur n'existe pas !");
		}
			
		
}
	
	
	/**
	 * M�thode qui traite la perte d'un document, ne renvoie rien.
	 * @param ref
	 * @param adresseMail
	 * @throws IllegalArgumentException
	 */
	public static void perteDocument(String ref, String adresseMail) throws IllegalArgumentException {
		double prix = 0; // Initialisation de la variable prix � 0.
		Lecteurs lect = listeLecteurs.get(chercherLecteur(adresseMail)); // Cr�ation d'un objet Lecteurs.
		Document doc = listeDocument.get(chercherDocument(ref)); // Cr�ation d'un objet Document.
		if(chercherDocument(ref) != -1 && chercherLecteur(adresseMail) != -1) { // Si le document est dans la liste de document du centre et si le lecteur est dans la liste de lecteur du centre.
			if(doc.isDisponible() == false) { // Si le document n'est pas disponible.
				if(doc instanceof Periodique) { // Si le document est un Periodique.
					prix = doc.getPrix(); // Prix prend la valeur du prix du document.
					lect.nombreEmpruntMoins(); // Enleve 1 au nombre d'emprunt actuel du lecteur.
					lect.enleverDocumentListe(doc); // Enleve le document � la liste de document emprunt� par le lecteur.
					listeDocument.remove(doc); // Enl�ve le document � la liste des documents du centre.
					System.out.println("\nLa perte du document " + doc.getReference() + " | " + doc.getTitre() + "a bien �t� prise en compte. ");
				}
				else{ // Le document n'est pas un Periodique donc est un Livre.
					prix = doc.getPrix() + doc.getPrix()*((Livre) doc).getTaux(); // Prix prend la valeur du prix du document plus le prix multipli� par le taux de remboursement du Livre.
					lect.nombreEmpruntMoins(); // Enleve 1 au nombre d'emprunt actuel du lecteur.
					lect.enleverDocumentListe(doc); // Enleve le document � la liste de document emprunt� par le lecteur.
					listeDocument.remove(doc); // Enl�ve le document � la liste des documents du centre.
					System.out.println("\nLa perte du document " + doc.getReference() + " | " + doc.getTitre() + "a bien �t� prise en compte. ");	

				}
				System.out.println("\nIl reste " + doc.getNbreExemplaire() + " exemplaire(s) de ce document. ");
				System.out.println("\nLe montant du remboursement est : " + Math.round(prix*100.00)/100.00 + " �"); //On arrondi � 2 chiffres apr�s la virgule.
				
			}
			else {
				throw new IllegalArgumentException("\nVous n'avez pas emprunt� ce document. ");	
			}
		}
		else {
			throw new IllegalArgumentException("\nLe document ou le lecteur n'existe pas. ");
		}
	}
	
	/**
	 * M�thode qui alerte le lecteur qui n'a pas rendu un document � temps.
	 * @param doc
	 */
	public static void rendreDocumentRetard  (Document doc) {
			if(LocalDate.now().isAfter(doc.getDateRendre()) ) {
				System.out.println("\nAttention, vous avez rendu ce document en retard !");
			}
	}
	
	/**
	 * M�thode qui enregistre toutes les donn�es du centre et les r�cup�re � partir d'un fichier texte.
	 * @param NomFichierURL
	 * @throws FileNotFoundException
	 */
	public static void enregistrer(String NomFichierURL) throws FileNotFoundException{
		File file = new File(NomFichierURL); // Cr�ation d'un fichier.
		PrintWriter fichier = new PrintWriter(file); // Cr�ation de l'outil permettant de saisir dans un fichier.
		fichier.println("---------- SAUVEGARDE DU CENTRE LE "+LocalDate.now().toString().toUpperCase()+" ----------");
		for (Lecteurs lecteur : listeLecteurs) { // Parcours la liste de lecteur du centre.
			fichier.println(lecteur); // Affiche dans le fichier le lecteur.
		}
		for(Document doc : listeDocument) { // Parcours la liste de document du centre.
			fichier.println(doc); // Affiche dans le fichier le document.
		}
		
		System.out.println("Fichier enregistr� : "+file.getAbsolutePath());
		fichier.close(); // Ferme l'outil.
	}
	
	/**
	 * M�thode qui modifie le d�lai de pr�t des enseignants, elle ne renvoie rien.
	 * @param nouveauDelai
	 */
	public static void modifierDelaiEnseignant(int nouveauDelai) {
		for(Lecteurs lect : listeLecteurs) { // Parcours la liste de lecteur du centre.
			if(lect instanceof Enseignant) { // Si le lecteur est un Enseignant.
				lect.modifierDelai(nouveauDelai); // Change le d�lai de pr�t d'un enseignant.
			}
		}
	}
	
	/**
	 * M�thode qui modifie le d�lai de pr�t des �l�ves, elle ne renvoie rien.
	 * @param nouveauDelai
	 */
	public static void modifierDelaiEleve(int nouveauDelai) {
		for(Lecteurs lect : listeLecteurs) {  // Parcours la liste de lecteur du centre.
			if(lect instanceof Eleve) { // Si le lecteur est un �l�ve.
				lect.modifierDelai(nouveauDelai); // Change le d�lai de pr�t d'un �l�ve.
			}
		}
	}
	
	/**
	 * M�thode qui modifie le nombre maximum d'emprunt des enseignants, elle ne renvoie rien.
	 * @param nouveauNombreMaxEmprunt
	 */
	public static void modifierNombreMaxEmpruntEnseignant(int nouveauNombreMaxEmprunt) {
		for(Lecteurs lect : listeLecteurs) { // Parcours la liste de lecteur du centre.
			if(lect instanceof Enseignant) { // Si le lecteur est un Enseignant.
				lect.setNombreMaxEmprunt(nouveauNombreMaxEmprunt); // Change le nombre d'emprunt maximum d'un enseignant.
			}
		}
	}
	
	/**
	 * M�thode qui modifie le nombre maximum d'emprunt des enseignants, elle ne renvoie rien.
	 * @param nouveauNombreMaxEmprunt
	 */
	public static void modifierNombreMaxEmpruntEleve(int nouveauNombreMaxEmprunt) {
		for(Lecteurs lect : listeLecteurs) { // Parcours la liste de lecteur du centre.
			if(lect instanceof Eleve) { // Si le lecteur est un Eleve.
				lect.setNombreMaxEmprunt(nouveauNombreMaxEmprunt); // Change le nombre d'emprunt maximum d'un eleve.
			}
		}
	}
	
	/**
	 * M�thode qui prolonge le pr�t d'un document.
	 * @param duree
	 * @param doc
	 */
	public static void prolongementPret(long duree, Document doc) {
		doc.prolongementPret(duree); // Change la duree de pr�t d'un document.
	}
	
	/**
	 * Menu du centre
	 */
	public static void menu() {
		
		Scanner saisie = new Scanner(System.in); // Cr�ation d'un Scanner qui sera la saisie de l'utilisateur.
		
		try {
			
			int nombreEmpruntMaxEleve; // Initialisation des variables.
			int delaiEleve;
			int nombreEmpruntMaxEnseignant;
			int delaiEnseignant;
			int choix;			
			
				System.out.println("------------------ BIENVENUE DANS LE CENTRE ----------------\n");
				System.out.println("Avant d'acc�der au menu, saisissez le nombre maximum d'emprunt et le d�lai d'un �l�ve et d'un enseignant :  ( sup�rieur � 0 )\n");
				
				do {
				System.out.println("\nNombre d'emprunt maximum d'un �l�ve (sup�rieur � 0) :  ");
				nombreEmpruntMaxEleve = saisie.nextInt(); // nombreEmpruntMaxEleve prend la valeur saisie par l'utilisateur.
				}while(nombreEmpruntMaxEleve <= 0); // Tant que le nombre rentr� est in�rieur ou �gal � 0
				
				do {
				System.out.println("D�lai de pr�t d'un �l�ve (en jour) (sup�rieur � 0) :  ");
				delaiEleve = saisie.nextInt(); // delaiEleve prend la valeur saisie par l'utilisateur.
				}while(delaiEleve <= 0); // Tant que le nombre rentr� est in�rieur ou �gal � 0
				
				do {
				System.out.println("Nombre d'emprunt maximum d'un enseignant (sup�rieur � 0) :  ");
				nombreEmpruntMaxEnseignant = saisie.nextInt(); // nombreEmpruntMaxEnseignant prend la valeur saisie par l'utilisateur.
				}while(nombreEmpruntMaxEnseignant <= 0); // Tant que le nombre rentr� est in�rieur ou �gal � 0
				
				do {
				System.out.println("D�lai de pr�t d'un enseignant (en jour) (sup�rieur � 0) : ");
				delaiEnseignant = saisie.nextInt(); // delaiEnseignant prend la valeur saisie par l'utilisateur.
				}while(delaiEnseignant <= 0); // Tant que le nombre rentr� est in�rieur ou �gal � 0
				
			
			do {
				System.out.println("\n\n\n\n----------------------------------------------------------- CENTRE -----------------------------------------------------------");
				System.out.println("\nQue souhaitez vous faire ? ");
				System.out.println("\n1 - Ajouter un lecteur");
				System.out.println("2 - Ajouter un document");
				System.out.println("3 - Supprimer un lecteur");
				System.out.println("4 - Supprimer un document");
				System.out.println("5 - Afficher les lecteurs du centre");
				System.out.println("6 - Afficher les documents du centre");
				System.out.println("7 - Effectuer un pr�t");
				System.out.println("8 - Retourner un document");
				System.out.println("9 - Traiter une d�claration de perte");
				System.out.println("10 - Modifier le d�lai de pr�t des lecteurs");
				System.out.println("11 - Modifier le nombre d'emprunt maximum des lecteurs");
				System.out.println("12 - Prolonger le pr�t d'un document");
				System.out.println("13 - Enregistrer toutes les donn�es du centre");
				System.out.println("0 - Quitter");
				choix = saisie.nextInt(); // // choix prend la valeur saisie par l'utilisateur.
				if(choix>=0 || choix<=13) traitementMenu(choix, nombreEmpruntMaxEleve, delaiEleve, nombreEmpruntMaxEnseignant, delaiEnseignant); // Si le choix est entre 0 et 13 compris alors on appelle la m�thode de traitement du menu.
			} while (choix !=0); // Tant que le choix est diff�rent de 0 (quitter).
			
		} catch (Exception e) {
			System.out.println(e.getMessage()); // Affiche une exception
		}
	}
	
	/*
	 * M�thode de traitement du menu.
	 */
	private static void traitementMenu(int choix, int nombreEmpruntMaxEleve, int delaiEleve, int nombreEmpruntMaxEnseignant, int delaiEnseignant) {
		
		Scanner saisie = new Scanner(System.in); // Cr�ation d'un Scanner qui sera la saisie de l'utilisateur.	 			
		
		try {
					
			switch (choix) {
			
			case 1: // Choix : Ajouter un lecteur
				
				String nomLecteur; // Initialisation des variables.
				String adresseMail;
				String facOuInstitut;
				String numero;
				String adresse;
				String regexMail = "^^[A-Za-z0-9+_.-]+@(.+)$"; // expression reguli�re pour l'adresse mail (xxxx@xxx.xxx)
				Pattern pattern = Pattern.compile(regexMail); // cr�� le pattern de l'adresse mail
				Matcher matcher; //cr�e le matcher qui sera ensuite utilis� pour verifier si l'adresse mail respecte le pattern
				
				System.out.println("Nom : ");
				nomLecteur = saisie.next(); // nomLecteur prend la valeur saisie par l'utilisateur.
				

				do {
					System.out.println("Adresse mail : (xxxxx@xxx.xxx)");
					adresseMail = saisie.next(); // saisie de l'adresse mail
					matcher = pattern.matcher(adresseMail); // affecte le valeur au matcher en fonction de si l'adresse mail saisie respecte le pattern
				} while (!matcher.matches());// tant que l'adresse mail saisie ne respect pas le pattern
				
				System.out.println("Nom de la FAC ou de l'institut o� il travaille/�tudie : ");
				facOuInstitut = saisie.next(); // facOuInstitut prend la valeur saisie par l'utilisateur.
				
				int choixLecteur;
				
				do {
					
					System.out.println("Est-ce un enseignant (1) ou un �l�ve (2) ? : ");
					choixLecteur = saisie.nextInt(); // choixLecteur prend la valeur saisie par l'utilisateur.
					
				}while(choixLecteur != 1 && choixLecteur != 2); // Tant que choixLecteur est diff�rent de 1 et 2.
				
				if(choixLecteur == 1) { // Si l'utilisateur choisi 1.
					
					System.out.println("Num�ro de t�l�phone du bureau : ");
					numero = saisie.next(); // numero prend la valeur saisie par l'utilisateur.
					
					Centre.ajouterLecteur(new Enseignant(nomLecteur, adresseMail, facOuInstitut, nombreEmpruntMaxEnseignant, delaiEnseignant, numero)); // Appelle la m�thode ajouterLecteur qui ajoute l'enseignant au centre.
				}
				else { // L'utilisateur n'a pas choisi 1 donc 2.
					
					System.out.println("Adresse postale : ");
					adresse = saisie.next(); // adresse prend la valeur saisie par l'utilisateur.
					
					Centre.ajouterLecteur(new Eleve(nomLecteur, adresseMail, facOuInstitut, nombreEmpruntMaxEleve, delaiEleve, adresse)); // Appelle la m�thode ajouterLecteur qui ajoute l'�l�ve au centre.
				}
				
				System.out.println("Appuyer sur Entr�e pour continuer...");
				System.in.read();

				
				break;
				
			case 2 : // Choix : Ajouter un document
				
				String reference; // Initialisation des variables.
				String titre;
				double prix;
				int numeroPeriodique;
				int annee;
				String nomAuteur;
				double tauxLivre;
				int choixDocument;
				
				System.out.println("R�f�rence : ");
				reference = saisie.next(); // reference prend la valeur saisie par l'utilisateur.
				System.out.println("Titre : ");
				titre = saisie.next(); // titre prend la valeur saisie par l'utilisateur.
				System.out.println("Prix : ");
				prix = saisie.nextDouble(); // prix prend la valeur saisie par l'utilisateur.
				
				do {
					System.out.println("Est-ce un p�riodique (1) ou un livre (2) ?");
					choixDocument = saisie.nextInt(); // choixDocument prend la valeur saisie par l'utilisateur.
				}while(choixDocument != 1 && choixDocument != 2); // Tant que choixDocument est diff�rent de 1 et 2.
				
				if(choixDocument == 1) { // Si l'utilisateur saisi 1
					
					System.out.println("Num�ro : ");
					numeroPeriodique = saisie.nextInt(); // numeroPeriodique prend la valeur saisie par l'utilisateur.
					System.out.println("Ann�e de parution : ");
					annee = saisie.nextInt(); // annee prend la valeur saisie par l'utilisateur.
					
					Centre.ajouterDocument(new Periodique(reference, titre, prix, numeroPeriodique, annee)); // Appelle la m�thode ajouterDocument qui ajoute le P�riodique au centre.
				}
				else { // L'utilisateur n'a pas saisi 1 mais 2.

					System.out.println("Nom de l'auteur : ");
					nomAuteur = saisie.next(); // nomAuteur prend la valeur saisie par l'utilisateur.
					System.out.println("Taux de remboursement : (utiliser ',' pas '.')");
					tauxLivre = saisie.nextDouble(); // tauxLivre prend la valeur saisie par l'utilisateur.
					
					
					Centre.ajouterDocument(new Livre(reference, titre, prix, nomAuteur, tauxLivre)); // Appelle la m�thode ajouterDocument qui ajoute le Livre au centre.
				}
				
				System.out.println("Appuyer sur Entr�e pour continuer...");
				System.in.read();

				
				break;
				
			case 3 : // Choix : Supprimer un lecteur
				
				regexMail = "^^[A-Za-z0-9+_.-]+@(.+)$";
				pattern = Pattern.compile(regexMail); // cr�� le pattern de l'adresse mail
				do {
					System.out.println("Saisir l'adresse mail du lecteur � supprimer: (xxxxx@xxx.xxx)");
					adresseMail = saisie.next(); // saisie de l'adresse mail
					matcher = pattern.matcher(adresseMail); // affecte le valeur au matcher en fonction de si l'adresse mail saisie respecte le pattern
				} while (!matcher.matches());// tant que l'adresse mail saisie ne respect pas le pattern
				
				Centre.supprimerLecteur(adresseMail); // Appelle la m�thode supprimerLecteur qui supprime le lecteur avec l'adresse mail saisie.
				
				System.out.println("Appuyer sur Entr�e pour continuer...");
				System.in.read();

				
				break;
				
			case 4 : // Choix : Supprimer un document
				
				System.out.println("Saisir le document � supprimer : ");
				reference = saisie.next(); // reference prend la valeur saisie par l'utilisateur.
				
				Centre.supprimerDocument(reference); // Appelle la m�thode supprimerDocument qui supprime le document avec la r�f�rence saisie.
				
				System.out.println("Appuyer sur Entr�e pour continuer...");
				System.in.read();

				
				break;
				
			case 5 : // Choix : Afficher les lecteurs du centre
				
				Centre.afficherLecteur(); // Appelle la m�thode qui affiche tous les lecteurs du centre.
				
				System.out.println("Appuyer sur Entr�e pour continuer...");
				System.in.read();

				
				break;
			
			case 6 : // Choix : Afficher les documents du centre
				
				Centre.afficherDocument(); // Appelle la m�thode qui affiche tous les documents du centre.
				
				System.out.println("Appuyer sur Entr�e pour continuer...");
				System.in.read();

				
				break;
				
			case 7 : // Choix : Effectuer un pr�t

				System.out.println("Saisir la reference du document � emprunter : ");
				reference = saisie.next(); // reference prend la valeur saisie par l'utilisateur.
				System.out.println("Saisir l'adresse mail du lecteur qui souhaite emprunter ce document : ");
				adresseMail = saisie.next(); // adresseMail prend la valeur saisie par l'utilisateur.
				
				Centre.emprunterDocument(reference, adresseMail); // Appelle la m�thode emprunterDocument qui permet au lecteur qui a l'adresse mail saisie d'emprunter le document qui a la r�f�rence saisie.
				
				System.out.println("Appuyer sur Entr�e pour continuer...");
				System.in.read();

				
				break;
			
			case 8 : // Choix : Retourner un document
				
				System.out.println("Saisir la r�f�rence du document � rendre : ");
				reference = saisie.next(); // reference prend la valeur saisie par l'utilisateur.
				
				regexMail = "^^[A-Za-z0-9+_.-]+@(.+)$";
				pattern = Pattern.compile(regexMail); // cr�� le pattern de l'adresse mail
				do {
					System.out.println("Saisir l'adresse mail du lecteur qui souhaite rendre ce document : (xxxxx@xxx.xxx)");
					adresseMail = saisie.next(); // saisie de l'adresse mail
					matcher = pattern.matcher(adresseMail); // affecte le valeur au matcher en fonction de si l'adresse mail saisie respecte le pattern
				} while (!matcher.matches());// tant que l'adresse mail saisie ne respect pas le pattern
				
				Centre.rendreDocument(reference, adresseMail); // Appelle la m�thode rendreDocument qui permet au lecteur qui a l'adresse mail saisie de rendre le document qui a la r�f�rence saisie.
				
				System.out.println("Appuyer sur Entr�e pour continuer...");
				System.in.read();

				
				break;
				
			case 9 : // Choix : Traiter une d�claration de perte
				
				System.out.println("Saisir la r�f�rence du document perdu : ");
				reference = saisie.next(); // reference prend la valeur saisie par l'utilisateur.
				regexMail = "^^[A-Za-z0-9+_.-]+@(.+)$";
				pattern = Pattern.compile(regexMail); // cr�� le pattern de l'adresse mail
				do {
					System.out.println("Saisir l'adresse mail du lecteur qui a perdu le document :  (xxxxx@xxx.xxx)");
					adresseMail = saisie.next(); // saisie de l'adresse mail
					matcher = pattern.matcher(adresseMail); // affecte le valeur au matcher en fonction de si l'adresse mail saisie respecte le pattern
				} while (!matcher.matches());// tant que l'adresse mail saisie ne respect pas le pattern
				
				Centre.perteDocument(reference, adresseMail); // Appelle la m�thode perteDocument qui permet au lecteur qui a l'adresse mail saisie de confirmer la perte du document qui a la r�f�rence saisie.
				
				System.out.println("Appuyer sur Entr�e pour continuer...");
				System.in.read();
				
				break;
				
			case 10 : // Choix : Modifier le d�lai de pr�t des lecteurs
				
				int choixLecteurDelai; // Initialisation des variables.
				int nouveauDelai;
				
				do {
					System.out.println("Voulez vous modifier le d�lai des �l�ves (1) ou des enseignants (2) ?");
					choixLecteurDelai = saisie.nextInt(); // choixLecteurDelai prend la valeur saisie par l'utilisateur.
				}while(choixLecteurDelai != 1 && choixLecteurDelai != 2); // Tant que choixLecteurDelai est diff�rent de 1 et 2.
				
				do {
					System.out.println("Entrez le nouveau d�lai : (sup�rieur � 0) ");
					nouveauDelai = saisie.nextInt(); // nouveauDelai prend la valeur saisie par l'utilisateur.
				}while(nouveauDelai <= 0); // Tant que nouveauDelai est inf�rieur ou �gal � 0.
				
				if(choixLecteurDelai == 1) { // Si l'utilisateur saisit 1.
					Centre.modifierDelaiEnseignant(nouveauDelai); // Appelle la m�thode modifierDelaiEnsaignant qui modifie le d�lai de tous les enseignants avec le d�lai saisi.
				}else { // L'utilisateur ne saisit pas 1 donc 2.
					Centre.modifierDelaiEleve(nouveauDelai); // Appelle la m�thode modifierDelaiEleve qui modifie le d�lai de tous les eleves avec le d�lai saisi.
				}
				
				System.out.println("Appuyer sur Entr�e pour continuer...");
				System.in.read();
				
				break;
				
			case 11 : // Choix : Modifier le nombre d'emprunt maximum des lecteurs
				
				int choixLecteurNombreEmpruntMax; // Initialisation des variables.
				int nouveauNombreEmprunt;
				
				do {
					System.out.println("Voulez vous modifier le nombre d'emprunt maximum des �l�ves (1) ou des enseignants (2) ?");
					choixLecteurNombreEmpruntMax = saisie.nextInt(); // choixLecteurNombreEmpruntMax prend la valeur saisie par l'utilisateur. 			
				}while(choixLecteurNombreEmpruntMax != 1 && choixLecteurNombreEmpruntMax != 2); // Si choixLecteurNombreEmpruntMax est diff�rent de 1 et 2.
				
				do {
					System.out.println("Entrez le nouveau nombre d'emprunt maximum : (sup�rieur � 0) ");
					nouveauNombreEmprunt = saisie.nextInt(); // nouveauNombreEmprunt prend la valeur saisie par l'utilisateur.
				} while (nouveauNombreEmprunt <= 0); // Tant que nouveauNombreEmprunt est inf�rieur ou �gal � 0.
				
				if(choixLecteurNombreEmpruntMax == 1) { // Si l'utilisateur saisi 1.
					Centre.modifierNombreMaxEmpruntEnseignant(choixLecteurNombreEmpruntMax); // Appelle la m�thode modifierNombreMaxEnseignant qui modifie le nombre maximum d'emprunt de tous les enseignants avec la valeur saisie.
				}else {
					Centre.modifierNombreMaxEmpruntEleve(choixLecteurNombreEmpruntMax); // Appelle la m�thode modifierNombreMaxEleve qui modifie le nombre maximum d'emprunt de tous les eleves avec la valeur saisie.
				}
				
				System.out.println("Appuyer sur Entr�e pour continuer...");
				System.in.read();

				
				break;
				
			case 12 : // Choix : Prolonger le pr�t d'un document
				
				int choixProlongement ; // Initialisation des variables.
				long dureeSupplementaire;
				
				regexMail = "^^[A-Za-z0-9+_.-]+@(.+)$";
				pattern = Pattern.compile(regexMail); // cr�� le pattern de l'adresse mail
				do {
					System.out.println("Saisissez l'adresse mail du lecteur qui souhaite prolonger son pr�t : (xxxxx@xxx.xxx)");
					adresseMail = saisie.next(); // saisie de l'adresse mail
					matcher = pattern.matcher(adresseMail); // affecte le valeur au matcher en fonction de si l'adresse mail saisie respecte le pattern
				} while (!matcher.matches());// tant que l'adresse mail saisie ne respect pas le pattern
				
				
				Lecteurs lect = listeLecteurs.get(chercherLecteur(adresseMail)); // Cr�ation d'un objet Lecteurs.
				if (lect.getListeDocLecteur().size()==0) { // Si le lecteur n'a pas de document dans sa liste d'emprunt.
					System.out.println("Le lecteur n'a pas d'emprunt en cours.");
				}
				else{
					
					for(int i = 0; i < lect.getListeDocLecteur().size(); i++) { // Parcours la liste de document du lecteur.
						System.out.println((i + 1) + " - " + lect.getListeDocLecteur().get(i).getTitre() + " reference : " + lect.getListeDocLecteur().get(i).getReference());
					}
					
					do {
						System.out.println("De quel document souhaitez vous prolonger le pr�t ? ");
						choixProlongement = saisie.nextInt(); // choixProlongement prend la valeur saisie par l'utilisateur.
					} while (choixProlongement < lect.getListeDocLecteur().size() || choixProlongement > 1); // Tant que choixProlongement est sup�rieur � 1 ou inf�rieur au nombre de document dans la liste d'emprunt du lecteur.
					
					Document doc = lect.getListeDocLecteur().get(choixProlongement - 1); // Cr�ation d'un objet Document.
					
					do {
					System.out.println("Entrez le nombre de jour(s) suppl�mentaire(s) : ( sup�rieur � 0 ) ");
					dureeSupplementaire = saisie.nextLong(); // dureeSupplementaire prend la valeur saisie par l'utilisateur.
					}while(dureeSupplementaire <= 0); // Tant que dureeSupplementaire est inf�rieure ou �gal � 0.
					
					Centre.prolongementPret(dureeSupplementaire, doc); // Appelle la m�thode prolongementPret qui permet de prolonger le pr�t d'un document.
					
				}
				
				System.out.println("Appuyer sur Entr�e pour continuer...");
				System.in.read();
				
				break;
				
			case 13 : // Choix : Enregistrer toutes les donn�es du centre
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
				LocalDateTime dateTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth() , LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),LocalDateTime.now().getSecond());
				String formattedDateTime = dateTime.format(formatter); // "1986-04-08 12:30"
				Centre.enregistrer("save_"+formattedDateTime+".txt");
				
				System.out.println("Appuyer sur Entr�e pour continuer...");
				System.in.read();
				
				break;
				
			case 0 : // Choix : Quitter
				
				System.out.println("Au revoir");
				
				break;
				
		
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}
		
}

