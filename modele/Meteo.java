package modele;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import vues.Vue;

public class Meteo implements Modele {
	private ArrayList<MeteoJour> liste;
	private ArrayList<Vue> vues;

	public Meteo() {
		liste = new ArrayList<MeteoJour>();
		vues = new ArrayList<Vue>();
	}

	public Meteo(String fichier) {
		this();
		this.charger(fichier);
	}

// méthode qui ajoute à une liste une nouvelle météo du jour
	public void ajouter(int j, int m, int a, int t, int p) {
		liste.add(new MeteoJour(j, m, a, t, p));
	}

	public ArrayList<MeteoJour> getListe() {
		return liste;
	}
	public int nbJours() {
		return liste.size();
	}
 
	public int getTemp(int i) {
		return liste.get(i).getTemp();
	}
 
	public int getPrec(int i) {
		return liste.get(i).getPrec();
	}
 
	public int getJours1900(int i) {
		return (int) liste.get(i).getJours1900();
	}
	
	
	
	
// méthode qui permet de charger à partir d'un fichier et retourner les données météorologiques contenues dans le fichier
	public void charger(String nomFichier) {
		try {
			System.out.println(nomFichier);
			liste = new ArrayList<MeteoJour>();
			

			BufferedReader br = Files.newBufferedReader(Paths.get(nomFichier));
			String ligne;

			ligne = br.readLine();
			while (ligne != null) {
				liste.add(MeteoJour.parse(ligne));
				ligne = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void sauver(String nomFichier) throws IOException {
		try {
			FileWriter f = new FileWriter(nomFichier, true);
			BufferedWriter b = new BufferedWriter(f);
			b.write(this.toString());
			b.close();
		} catch (IOException e) {
			System.out.println("I/O Error");
		}

	}

	public void InitialiserModele() {
		this.liste.clear();
		for (Vue v : vues) {
			v.initialiser();
		}
	}
	public void enregistrer(Vue v) {
		v.setModele(this);
		vues.add(v);
	}
	
	public void notifierToutesVues() {
		for (Vue v:vues) {
			v.notifierChangement();
		}
	}
	
	// méthode qui affiche la météo avec la date , le temps et la précipitation pour
	// chaque mj de la liste
	public String toString() {
		String str = "";
		for (MeteoJour mj : liste) {
			str += mj.toString();
			str += "\n";
		}
		return str;
	}

	// méthode qui affiche les données météorologiques et la date
	public String versFichier() {
		String str = "";
		for (MeteoJour mj : liste) {
			str += mj.versFichier();
			str += "\n";
		}
		return str;
	}

	public int minTemp() {
		try {
			return Collections.min(liste, MeteoJour.ordreTemp).getTemp();
		} catch (java.util.NoSuchElementException e) {
			return Integer.MIN_VALUE;
		}
	}

	public int maxTemp() {
		try {
			return Collections.max(liste, MeteoJour.ordreTemp).getTemp();
		} catch (java.util.NoSuchElementException e) {
			return Integer.MAX_VALUE;
		}
	}

	public int minPrec() {
		try {
			return Collections.min(liste, MeteoJour.ordrePrec).getPrec();
		} catch (java.util.NoSuchElementException e) {
			return Integer.MIN_VALUE;
		}
	}

	public int maxPrec() {
		try {
			return Collections.max(liste, MeteoJour.ordrePrec).getPrec();
		} catch (java.util.NoSuchElementException e) {
			return Integer.MAX_VALUE;
		}
	}

	public int maxJours1900() {
		try {
			return Collections.max(liste, MeteoJour.ordreTemps).getJours1900();
		} catch (java.util.NoSuchElementException e) {
			return Integer.MAX_VALUE;
		}
	}

	public int minJours1900() {
		try {
			return Collections.min(liste, MeteoJour.ordreTemps).getJours1900();
		} catch (java.util.NoSuchElementException e) {
			return Integer.MIN_VALUE;
		}

	}
	
	public void triDate() {
		try {
			Collections.sort(liste, MeteoJour.ordreTemps);
		} catch (java.util.NoSuchElementException e) {
		}
	}
}
