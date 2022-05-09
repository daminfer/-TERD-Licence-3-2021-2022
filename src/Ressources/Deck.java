package Ressources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Deck extends  PathHolder{
    //Attributs
    private String Nom;
	private ArrayList<Carte> Cartes = new ArrayList<Carte>( );
    ////////////////////////////////////
    //SetAttributs
    private void setNom (String Nom) {Nom.trim();this.Nom = Nom;}
    private void setCartes (ArrayList<Carte> c) {this.Cartes = c;}
    ////////////////////////////////////
    //GetAttributs
    public String getNom () {return this.Nom;}
    public ArrayList<Carte> getCartes() {return this.Cartes;}
    ////////////////////////////////////

    public void addCarte(Carte c){
        this.Cartes.add(c);
        modifDeck(this.getNom(), this.getCartes());
    }
    public void removeCarte (Carte c) {
        this.Cartes.remove(c);
        modifDeck(this.getNom(), this.getCartes());
    }

    public Deck initDeck(String nom){
        /*
            Initialiser un deck
            et initialise chaque carte du deck.
        */
        try
        {

            File file = new File(getPathDeck() + nom + ".txt");
            if (file.exists()){
                ArrayList<String> temps = new ArrayList<>();
                ArrayList<Carte> tempc = new ArrayList<>();
                FileInputStream f = new FileInputStream(file);
                Scanner obj = new Scanner(f);
                while (obj.hasNextLine()) {
                    String text = obj.nextLine();
                    temps.add(text+'\n');
                }
                obj.close();f.close();
                this.setNom(nom);
                for (String s: temps){
                    Carte o = new Carte().initCarte(s.trim());
                    tempc.add(o);
                }
                this.setCartes(tempc); // stock les données de la carte dans les attributs de la classe

            }
            else{
                System.out.println("Le deck n'existe pas");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    return this;
    }
    public void creatDeck(String nom,ArrayList<Carte> Cartes) {


        ArrayList<String> temp = new ArrayList<>();
        this.setNom(nom);
        this.setCartes(Cartes);
        try {
            File file = new File(getPathDeck() + this.Nom + ".txt");
            if(file.createNewFile()){
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                for (Carte c: this.Cartes) {
                    fw.write(c.getNom()+'\n');
                }
                fw.close();
            }
            else{
                System.out.println("Le deck existe deja");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    public void modifDeck(String nom,ArrayList<Carte> newCartes){
        try {
            File file = new File(getPathDeck() + nom + ".txt");
            if (file.exists()){
                file.delete();
                creatDeck(nom,newCartes);
            }else{
                System.out.println("Le deck n'existe pas veuillez la creer ");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // EXEMPLE D'UTILISATION
        Deck d1 = new Deck().initDeck("deck1.txt");
        Deck d2 = new Deck().initDeck("deck2");
        System.out.println(d2.getCartes().get(2).getFaceRecto().getText());
    }



}
