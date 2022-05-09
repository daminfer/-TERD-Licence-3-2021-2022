package Ressources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Carte extends PathHolder {
	private String Nom; 
	private Face recto;
	private Face verso;
	private String rectofile ;
	private String versofile ;


	private void setNom(String nom){
		this.Nom = nom;
	}
	private void setRectoFile(String text) {this.rectofile = "<recto%>\n"+text+"\n";}
	private void setVersoFile(String text) {this.versofile = "<verso%>\n"+text+"\n";}
	private void setRecto(String text) {this.recto = new Face(text);
										setRectoFile(text);}
	private void setVerso(String text) {this.verso = new Face(text);
										setVersoFile(text);}

	private void extractRV (ArrayList<String> temp){
		boolean b=false;
		String rect = "";
		String vers = "";
		for (String s: temp) {
				if (s.equals("<recto%>\n")){b = false;}
				if (s.equals("<verso%>\n")){b = true;}
				if (b == false && !s.equals("<recto%>\n")) rect+= s;
				if (b == true && !s.equals("<verso%>\n")) vers += s;
		}

		this.setRecto(rect);this.setVerso(vers);
	}
	private ArrayList<String> modiftable(ArrayList<String> temp){
		Boolean b = false;
		for (String s: temp) {
			if(s.equals("ENDOFRV\n")){
				break;
			}
			else{

			}
		}
		ArrayList<String> newtemp = new ArrayList<String>();

		newtemp.add(getVersoFile());
		newtemp.addAll(temp);
		return newtemp;
	}
	public String getRectoFile(){
		return this.rectofile;
	}
	public String getVersoFile(){
		return this.versofile;
	}
	public String getNom() {
		return this.Nom; 
	}
	public Face getFaceVerso() {
		return this.verso;
	}
	public Face getFaceRecto() {
		return this.recto;
	}
	
	public Carte initCarte(String nom){
		//Initialiser une carte traite sous forme de liste

		try
        {

			File file = new File(getPathCarte() + nom + ".txt");
            if (file.exists()){
				ArrayList<String> temp = new ArrayList<>();
				FileInputStream f = new FileInputStream(file);
                //System.out.println("file content: ");
                Scanner obj = new Scanner(f);
				while (obj.hasNextLine()) {
					String text = obj.nextLine();
					temp.add(text+'\n');
				}
				//System.out.print(temp);
				obj.close();f.close();
				this.setNom(nom);
				extractRV(temp); // stock les données de la carte dans les atributs de la classe
			}
			else{
				System.out.println("La carte n'existe pas");
			}
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
		return this;
	}
	public void creatCarte( String nom,String question,String reponse) {
		ArrayList<String> temp = new ArrayList<>();
		this.setNom(nom);
		this.setRecto(question);
		this.setVerso(reponse);

		try {
			File file = new File(getPathCarte()  + this.Nom + ".txt");
			if(file.createNewFile()){
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				fw.write(this.getRectoFile());
				fw.write(this.getVersoFile());
				fw.close();
			}
			else{
				System.out.println("La carte existe deja");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	public void modifCarte(String nom, String newquestion , String newreponse){
		ArrayList<String> temp = new ArrayList<>();
		try {
			File file = new File(getPathCarte() + nom + ".txt");
			if (file.exists()){
				file.delete();
				creatCarte(nom,newquestion,newreponse);

			}else{
				System.out.println("La n'existe pas veuillez la creer ");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void delete_carte (){

		try {
			File file = new File(getPathCarte() + this.getNom() + ".txt");
			if (file.exists()) {
				file.delete();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Carte c = new Carte();
		c.creatCarte("carte1" , "qmsfj","reponse");
	}

}
