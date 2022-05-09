package Ressources;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RepEspacee extends PathHolder{

	//Attributs
	private int nbCartesAjouter;// nombre de carte a ajouter chaque jours
	private int nbNiveau;//nombre de niveau du profil.
	private long joursApp; // nb de jours d'ancienneté du profil depuis sa date de creation.
	private boolean lecturedone; //!\ si false je peux rajouter des decks si true je dois attendre le lendemain
	private List<Integer> planning = new ArrayList<Integer>();
	private static String nomProfil;
	/////////////////////////////////////
	// GetAttribut
	public boolean getLecturedone() {return this.lecturedone;}
	public static String getNomProfil() {return nomProfil;}
	public int getNbCartesAjouter() {return this.nbCartesAjouter;}
	public int getNbNiveau() {return this.nbNiveau;}
	public long getJoursApp() {return this.joursApp;}
	public List<Integer> getPlanning(){return this.planning;}
	public String getDayDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		// les minutes ont ete rajouté dans le but de faire des tests rapide;
		String date = dtf.format(LocalDateTime.now());
		return date;
	}
	/////////////////////////////////////
	// SetAttribut
	private void setLectureDone(boolean b){this.lecturedone = b;}
	private void setNomProfil(String p){this.nomProfil = p;}
	private void setNbCartesAjouter(int nbCartesAjouter) {this.nbCartesAjouter = nbCartesAjouter ;}
	private void setNbNiveau(int Nbniveau) {this.nbNiveau = Nbniveau;}
	private void setJoursApp(long joursApp) {this.joursApp = joursApp ;}
	////////////////////////////////////

	//rajouter une methode pour savoir si un deck est fini dans la repetition espacé, si toute les cartes du deck sont dans le dernier niveau du profil.

	public ArrayList <Integer> PlanningEDT (long ja ){
		ArrayList <Integer> planning = new ArrayList<>();
		if(this.nbNiveau > 0){
			for(int niveau = 1; niveau <= this.nbNiveau; niveau++) {
				if((ja % niveau) == 0) {planning.add(niveau);}
			}
		}
		else {planning.add(1);}
		return planning ;

	}
	private void setPlanningDuJour (long jourApp){
		/*
		calcul la liste des niveaux a effectuer grace au jour d'ancienneté du profil.
		 */
		System.out.println(jourApp +" "+getNbNiveau()+" " +this.getPlanning());
		if(this.nbNiveau > 0){
			for(int niveau = 1; niveau <= this.nbNiveau; niveau++) {
				if((this.joursApp % niveau) == 0) {this.planning.add(niveau);}
			}
		}
		else {this.planning.add(1);}
		System.out.println(jourApp +" "+getNbNiveau()+" " +this.getPlanning());
	}
	private void setParameter (List<String> temp ){
		/*
		chaque String du tableau temp sera toujours composé de la sorte : "nom du parametre : parametre"
		les elements sont rangé dans un ordre precis pour le bon fonctionement de cette fonction.
		le but et de parcourir temp et de modifier les attributs de l'objet de la classe grace a leurs fonctions respective setattribut()
		de plus si le jours a changé (if (!getDayDate().equals(conf.trim()))) :
			il changera expressement le nb de carte a ajouter chaque jour, le jour d'ancieneté du profil depuis sa date de creation (joursApp) , le planning du jour;
			puis il passera les nouvelles carte du niveau0 au niveau1.
		 */
		if (temp.size() > 0) {
			for (String s : temp) {
				String parameter = s.split(":")[0];
				String conf = s.split(":")[1];
				if (parameter.equals("LastJourConnection")) {
					if (!getDayDate().equals(conf.trim())) {
						temp.set(2, "LastJourConnection:" + getDayDate() + '\n');
						setJoursApp(Long.valueOf(temp.get(4).split(":")[1].trim()) + 1);
						setNbCartesAjouter(Integer.valueOf(Integer.valueOf(temp.get(1).split(":")[1].trim())));
						setPlanningDuJour(this.joursApp);
						passagecarten0n1();// si c'est un nouveau jour pour l'appli, il faut mettre les carte du niveau 0 dans le niveau 1
					}

				}
				if (parameter.equals("Nblvl")) {
					setNbNiveau(Integer.valueOf(conf.trim()));

				}
				if (parameter.equals("Nbcartesajout")) {
					setNbCartesAjouter(Integer.valueOf(conf.trim()));
				}
				if (parameter.equals("Lecturedone")) {
					isLectureDone(this);
				}

			}
		}
		modifProfil(getNomProfil(),getNbNiveau(),getNbCartesAjouter(),getJoursApp() , getLecturedone());
	}
	private void passagecarten0n1(){

			Niveau n0 = new Niveau().initlvl("Niveau_0");
			Niveau n1 = new Niveau().initlvl("Niveau_1");
			ArrayList<Carte> lc = new ArrayList<Carte>();

			for (ArrayList<Carte> cts : n0.getDecks().values()){
				lc.addAll(cts); //fusionne toutes les valeurs en une seule ArrayList [[c1,c2][c3,4c]] -> [c1,c2,c3,c4]
			}

			//si le nb de carte dans le niv 0 et < au nb de carte a envoyer chaque jours on prend la valeurs la plus petite
			//k est valeur du nombre de carte a transvaser du niv0 au niv 1.
			int k = lc.size() ;
			if (k >= getNbCartesAjouter()){
				k = getNbCartesAjouter();
			}

			for(int i = 0 ; i<k;i++){//répète les operations k fois.
				Deck selectd = n0.getDecks().keySet().stream().toList().get(0);//selectionne le premier objet Deck affiché dans le niveau 0
				n1.lvladdCarte(selectd,n0.getDecks().get(selectd).get(0)); //ajoute la premier carte du deck selectionné dans le niv 0 et l'ajoute au niv 1
				n0.lvlremoveCarte(selectd,n0.getDecks().get(selectd).get(0));//supprime la carte du deck selectionné dans le niv 0 du niv 0
			}
	}


	public void passageCarteAuNivSuivant(String nomCarte, String nivPrecedent , String nivSuivant){
		/*
		initialise les niveaux et la carte
		parcours les cartes dans le niveau,
		selctionne le deck correspondant a la carte
		ajoute la carte et le deck correspondant dans le niveau suivant
		supprime la carte au deck corresondant dans le niveau en cours.
		 */

		Niveau np= new Niveau().initlvl(nivPrecedent);
		Niveau ns = new Niveau().initlvl(nivSuivant);
		Carte c = new Carte().initCarte(nomCarte);
		System.out.println("je suis dans passageCarteAuNivSuivant" );

			for (ArrayList<Carte> lc : np.getDecks().values()) {
				for (Carte tc : lc) {
					System.out.println(tc.getNom() + "<=>" + nomCarte );
					if (tc.getNom().equals(nomCarte)) {
						// index du deck correspondant au nom de la carte
						Deck selected = np.getDecks().keySet().stream().toList().get(np.getDecks().values().stream().toList().indexOf(lc));
						ns.lvladdCarte(selected, c);
						np.lvlremoveCarte(selected, c);
						break;
					}
				}
			}

	}
	public void isLectureDone (RepEspacee rep) {
		/*
		regarde si les toutes les cartes des niveaux correspondant au planning ont ete faites
		si le niveau est vide appliquer un ET LOGIQUE sur un boolean avec la valeur true
		sinon appliquer un ET LOGIQUE sur un boolean avec la valeur false
		si il y a au moins une carte dans un niveau le False primera grace au ET LOGIQUE
		*/
		Boolean b = true;
		if (this.joursApp > 0) {
			for (int i : rep.getPlanning()) {
				Niveau niv = new Niveau().initlvl("Niveau_" + String.valueOf(i));
				if (niv.getDecks().values().isEmpty()) {
					b &= true;
				} else {
					b &= false;
				}

			}
			setLectureDone(b);
		} else {
			Niveau niv0 = new Niveau().initlvl("Niveau_0");
			if (niv0.getDecks().isEmpty()) {
				setLectureDone(true);
			}else {

			}

		}
	}
	public boolean DeckinNiv(Deck d) {
		// renvoie true si un le deck d est dans un des niveaux du profil, false sinon.
		for (int i = 0 ; i<= getNbNiveau() ; i++){
			Niveau niv = new Niveau().initlvl("Niveau_" + String.valueOf(i));
			for (Deck deck : niv.getDecks().keySet()){
				if (deck.getNom().equals(d.getNom())){
					return true;
				}
			}
		}
		return false;
	}


	public void createProfil(String nomProfil, int nbNiv, int nbCarte, long joursApp, boolean lecturedone){
		/*
		cree un nouveau fichier de profil dans le repertoire Racine/Ressources/Profils/ avec ses parametres :
		nom du profil, nombre de niveau , nombre de carte a rajouter chaque jours, si la session à été faite ou non

		si le fichier existe il va modifier le profil existant avec les nouveaux parametres.

		il va ensuite cree les fichier de niveau dans le repertoire Racine/Niveau/nomduprofil/ si ce n'est pas deja fait.
		 */

		setLectureDone(lecturedone);
		setNbNiveau(nbNiv);
		setNbCartesAjouter(nbCarte);
		setJoursApp(joursApp);
		setNomProfil(nomProfil);
		try
		{
			File file = new File(getPathProfils()+nomProfil+".txt");
			if (!file.exists()){ //si le fichier n'existe pas on le cree
				file.createNewFile();
				File d = new File(getPathLvlforProfil()+nomProfil);
				d.mkdir();
				FileWriter fw = new FileWriter(file.getAbsolutePath());
				fw.write("Nblvl:"+ String.valueOf(getNbNiveau())+ '\n');
				fw.write("Nbcartesajout:"+String.valueOf(getNbCartesAjouter())+ '\n');
				fw.write("LastJourConnection:"+getDayDate() + '\n');
				fw.write("Lecturedone:"+String.valueOf(getLecturedone())+ '\n');
				fw.write("JourApp:"+(String.valueOf(getJoursApp()))+ '\n');

				fw.close();
				System.out.println("Fichier ProfilRepEsp cree");
			}
			else{
				modifProfil(nomProfil,nbNiv,nbCarte,joursApp,lecturedone);
			}



		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		for(int i = 0 ; i <= this.getNbNiveau() ; i++){
			Niveau n = new Niveau();
			n.createLvlfile(i, new Hashtable<>());
		}

	}
	public RepEspacee initProfil(String nomProfil) {
		/*
		initialise un profil existant, il va chercher le nom du profil dans le repertoire Racine/Ressources/Profil/
		si il existe il va lire le fichier et mettre chaque ligne dans un tableau (temp)
		il va ensuite traiter les informations de ce tableau dans la fonction setParameter.
		 */
		setNomProfil(nomProfil);
		System.out.println(nomProfil);
		try {
			File file = new File(getPathProfils()+ nomProfil + ".txt");
			if (file.exists()) {
				List<String> temp = new ArrayList<>();
				FileInputStream f = new FileInputStream(file);
				Scanner obj = new Scanner(f);
				while (obj.hasNextLine()) {
					String text = obj.nextLine();
					text.replaceAll(" ", "");
					temp.add(text + '\n');
				}
				obj.close();
				f.close();
				setParameter(temp);
				chargerNiveaux();

			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	private void chargerNiveaux() {
		File dossierN = new File (getPathLvl());
		if(dossierN.isDirectory()){
		for (File f : dossierN.listFiles()) {
			String s = f.getName().replaceAll(".txt" , "").trim();
			Niveau n = new Niveau().initlvl(s);
		}
		}

	}

	void modifProfil(String nomProfil, int nbNiveau, int nbCartesAjouter, long joursApp,boolean lectureDone){
		/*
		modifie un profil existant avec ses nouveaux parametres.
		si les parametres de niveau on changé il va supprimer les niveaux en trop dans le repertoire Racine/Niveau/nomduprofil
		supprime le fichier de profil pour le recreer ensuite.
		 */
		try
		{
			File file = new File(getPathProfils()+nomProfil+".txt");
			if (file.exists()){ //si le fichier on le supprime pour en faire un nouveau
				File dossierNiv = new File(getPathLvl());
				if (dossierNiv.isDirectory()){
					for(File niv : dossierNiv.listFiles()){
						System.out.println(niv.getName());
						String s = niv.getName().split("_")[1].replaceAll(".txt","").trim();
						int i = Integer.valueOf(s) ;
						if ((i > nbNiveau)){
							if (!niv.isDirectory()){
								niv.delete();
							}

						}
					}
				}

				file.delete();
				this.createProfil(nomProfil,nbNiveau,nbCartesAjouter,joursApp , lectureDone);

			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}




	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RepEspacee r = new RepEspacee().initProfil("Oren");



	}

}
