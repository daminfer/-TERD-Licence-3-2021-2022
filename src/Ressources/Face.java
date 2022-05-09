package Ressources;

public class Face {

	private String texte;

	Face (String t){
		setText(t);
	}
	public void setText(String texte) {
		this.texte = texte;
	}
	
	public String getText() {
		return this.texte; 
	}
	
	public static void main(String[] args) {

	}
}
