package Ressources;

import java.io.*;
import java.util.*;

public class Niveau extends PathHolder {
    //Attributs
    private Map<Deck, ArrayList<Carte>> Decks = new Hashtable<Deck, ArrayList<Carte>>();
    private String nomlvl;
    private int lvl;
    ////////////////////////////////////
    //GetAttributs
    public Map<Deck, ArrayList<Carte>> getDecks() {return this.Decks;}
    public int getLvl(){return this.lvl;}
    public String getNomLvl(){return this.nomlvl;}
    ////////////////////////////////////
    //SetAttributs
    private void setNom(String nom) {
        this.nomlvl = nom;
    }
    private void setlvl(int lvl) {
        this.lvl = lvl;
    }
    private void setDecks(Map<Deck, ArrayList<Carte>> decks) {
        this.Decks = decks;
    }
    ////////////////////////////////////
    private void extractDeck(ArrayList<String> temps) {
        /*
        initialise les carte et les deck d'un niveau les ligne de nbr paire sont les nom de deck,
        les ligne de nombre impaire sont les nom cartes du deck restant dans le niveau.
        ajoutes tout les deck et les cartes du niveau lu dans le dictionnaire avec la methode Set.
         */
        System.out.println(temps.size());
        for (int i = temps.size() - 1; i >= 0; i--) {
            if (i % 2 == 0) {
                Deck d = new Deck();
                d.initDeck(temps.get(i).trim());
                System.out.println(d.getNom());
                if (d.getNom() != null) {
                    this.Decks.put(d, d.getCartes());
                }
                String[] s = temps.get(i + 1).replaceAll(",\n", "").split(",");
                ArrayList<Carte> lc = new ArrayList<Carte>();
                for (String nomCartes : s) {
                    Carte c = new Carte();
                    c.initCarte(nomCartes.trim());
                    if (c.getNom() != null){
                        lc.add(c);
                    }

                }
                this.Decks.replace(d, this.Decks.get(d), lc);
            }



        }
        setDecks(this.Decks);
        modifLvlfile(this.getNomLvl() , getDecks());
    }
    public void addnewDeck(Deck d) {
        /*
        ajoute un deck dans un niveau ainsi que toutes ses cartes.
         */
        int i = this.Decks.keySet().stream().toList().size();
        for (Deck td : this.Decks.keySet()) {
            if (d.getNom().equals(td.getNom())) {
                break;
            } else {
                i -= 1;
            }
        }
        if (i == 0) {
            this.Decks.put(d, d.getCartes());
        }
        modifLvlfile(getNomLvl(), this.Decks);

    }
    public void lvlremoveDeck(Deck d) {
        // retire le deck du niveau 
        if (this.Decks.containsKey(d)) {
            this.Decks.remove(d);
        }
        else {
            System.out.println("le deck n'existe pas");
        }
        modifLvlfile(getNomLvl(), this.Decks);

    }
    public void lvladdCarte(Deck d, Carte c) {
        /*
        Ajoute une carte dans le niveau avec son deck correspondant.
        si j = 0 c'est que le niveau est vide.
        sinon si j n'est pas = la taille du tableau de valeur du deck correspondant, c'est que la carte n'est pas dans le niveau, il faut donc la rajouter
         */
        int j = 0;
        for (Deck td : this.Decks.keySet().stream().toList()){
             if (td.getNom().equals(d.getNom())) {
                for (Carte tc : this.Decks.get(td)) {
                    if (!c.getNom().equals(tc.getNom())) {
                        j += 1;
                    }
                }
                if (j == this.Decks.get(td).size()) {
                    this.Decks.get(td).add(c);
                }
            }
        }
        if (j == 0) {
            this.Decks.put(d, new ArrayList<Carte>());
            this.Decks.get(d).add(c);
        }

        System.out.println(this.getNomLvl() + "\n" + this.Decks);
        modifLvlfile(getNomLvl(),this.Decks);

    }
    public void lvlremoveCarte(Deck d , Carte c){
        /*
        retire une carte du deck d dans le niveau
        si le deck correspondant a la carte est vide on retire le deck
         */
        for(Deck td : this.Decks.keySet().stream().toList()){
            if (d.getNom().equals(td.getNom())){
                for (int i = 0 ; i < this.Decks.get(td).size() ; i++){
                    if (c.getNom().equals(this.Decks.get(td).get(i).getNom())){
                        this.Decks.get(td).remove(i);
                    }
                }
            }
            if(this.Decks.get(td).isEmpty()){
                lvlremoveDeck(td);
            }
        }
        System.out.println(this.getNomLvl() + "\n" + this.Decks);

        modifLvlfile(getNomLvl(),this.Decks);
    }

    public void modifLvlfile(String nom, Map<Deck, ArrayList<Carte>> decks){
        /*
        supprime puis reecrit le fichier avec le nouveau deck
         */
        try {
            File file = new File(getPathLvl() + nom + ".txt");
            if (file.exists()) {
                file.delete();
                createLvlfile(Integer.valueOf(nom.split("_")[1]) , decks);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    public void createLvlfile (int n ,  Map<Deck, ArrayList<Carte>> decks) {
       /*
		cree un nouveau fichier de niveau dans le repertoire Racine/Niveau/Nom du profil/ avec ses parametres :
		int indiquant le niveau, dictionnaire Deck : [Cartes] respectivement pour clé : valeurs

		si le fichier n'existe pas il écrit les deck et les cartes du dictionaire mis en parammetre
		 */
        setNom("Niveau_"+n);
        setlvl(n);
        setDecks(decks);

        try {
            File file = new File(getPathLvl()+getNomLvl()+".txt");
            if (file.createNewFile()){
                FileWriter fw = new FileWriter(file.getAbsolutePath());
                if (decks.keySet().size() > 0){
                    for (Deck d : decks.keySet().stream().toList() ){
                        fw.write(d.getNom() + "\n");
                        for (Carte c : decks.get(d)){
                            fw.write(c.getNom()+",");
                        }
                        fw.write("\n");
                    }
                }
                fw.close();

            }
            else{
                System.out.println("lvl deja cree");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Niveau initlvl (String nom){
        /*
		initialise un niveau existant, il va chercher le nom du niveau dans le repertoire Racine/Niveau/Nom du Profil/
		s'il existe, il va lire le fichier et mettre chaque ligne dans un tableau (temp)
		il va ensuite traiter les informations de ce tableau dans la fonction extractDeck.
		 */
        try
        {
            File file = new File(getPathLvl()+nom+".txt");
            if (file.exists()){
                ArrayList<String> temps = new ArrayList<String>();
                FileInputStream f = new FileInputStream(file);
                Scanner obj = new Scanner(f);
                while (obj.hasNextLine()) {
                    String text = obj.nextLine();
                    temps.add(text+'\n');
                }
                obj.close();f.close();
                this.setNom(nom);
                this.setlvl(Integer.valueOf(nom.substring(7)));
                if (temps.size() > 1){
                    extractDeck(temps);
                }
            }
            else{
                System.out.println("Le niveau n'existe pas");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return this;

    }

    public static void main(String[] args) {
        /*lecteurcarteapp.Niveau n = new lecteurcarteapp.Niveau().initlvl("Niveau_1");
        lecteurcarteapp.Deck newdeck1 = new lecteurcarteapp.Deck().initDeck("deck1.txt");
        lecteurcarteapp.Deck newdeck2 = new lecteurcarteapp.Deck().initDeck("deck2");
        n.addnewDeck(newdeck2);
        lecteurcarteapp.Deck d = n.getDecks().keySet().stream().toList().get(0);
        n.lvlremoveCarte(d,n.getDecks().get(d).get(0));
        System.out.println(n.getNomLvl()+" "+n.getLvl());
        System.out.println(n.getDecks());*/

    }


}
