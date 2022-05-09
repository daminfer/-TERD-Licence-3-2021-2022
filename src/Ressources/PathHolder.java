package Ressources;

public abstract class PathHolder {
    //Chemin vers les dossiers pour le fonctionement de l'application.
    private final String PathCarte = "Racine/Ressources/Cartes/" ;
    private final String PathDeck="Racine/Ressources/Decks/";
    private final String PathLvl="Racine/Niveau/";
    private final String PathProfils = "Racine/Ressources/Profils/" ;
    private String SE = System.getProperty("os.name").toLowerCase(); //revoie la version du systeme s'exploitation en minuscule

    public String getPathCarte() {
        if (SE.indexOf("win") >= 0) {// si il detecte le mot clé win pour Windows il remplace / par \
            this.PathCarte.replace("/","\\");
        }
        return this.PathCarte;
       }
    public String getPathDeck() {
        if (SE.indexOf("win") >= 0) {
            this.PathDeck.replace("/","\\");
        }
        return this.PathDeck;}
    public String getPathLvl() {
        if (SE.indexOf("win") >= 0) {
            this.PathLvl.replace("/","\\");
            return this.PathLvl+RepEspacee.getNomProfil()+"\\";
        }else{
            return this.PathLvl+RepEspacee.getNomProfil()+"/";
        }}

    public String getPathLvlforProfil() {
        if (SE.indexOf("win") >= 0) {
            this.PathLvl.replace("/","\\");
        }
        return this.PathLvl;}
    public String getPathProfils() {
        if (SE.indexOf("win") >= 0) {
            this.PathProfils.replace("/","\\");
        }
        return this.PathProfils;}

}
