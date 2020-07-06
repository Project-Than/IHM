package modele;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Scanner;

import vues.Vue;

/**
 * Classe MeteoJour
 * Modélise une fiche météo
 */
public class MeteoJour {
	private int temp;	// température du jour
	private int prec;	// précipitation du jour
	private LocalDate date;	// date du jour

	/**
	 * Constructeur par défaut
	 * Initialise l'instance courante avec
	 * - une température de 15°C
	 * - précipitation de 0 mm
	 * - date du jour courant
	 */
	public MeteoJour() {
		this.temp = 15;
		this.prec = 0;
		this.date = LocalDate.now();
	}

	/**
	 * Constructeur d'initialisation champ à champ
	 * @param j jour
	 * @param m mois
	 * @param a année
	 * @param t température
	 * @param p précipitation
	 */
	public MeteoJour(int j, int m, int a, int t, int p) {
		this.temp = t;
		this.prec = p;
		this.setDate(j, m, a);
	}

	/**
	 * Accesseur pour la température du jour
	 * @return température du jour
	 */
	public int getTemp() {
		return this.temp;
	}

	/**
	 * Accesseur pour la précipitation du jour
	 * @return précipitation du jour
	 */
	public int getPrec() {
		return this.prec;
	}

	/**
	 * Retourne le nombre de seconde depuis le 1/1/1900
	 * @return
	 */
	public int getJours1900() {
		LocalDate zero = LocalDate.of(1900, Month.JANUARY, 1);
		return (int) ChronoUnit.DAYS.between(zero, date);
	}

	/**
	 * Mutateur pour la date du jour
	 * @param j jour
	 * @param m mois
	 * @param a année
	 */
	public void setDate(int j, int m, int a) {
		date = LocalDate.of(a, m, j);
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(date.toString());
		stringBuilder.append(" : (temp=");
		stringBuilder.append(temp);
		stringBuilder.append(",prec=");
		stringBuilder.append(prec);
		stringBuilder.append(")");
		return (stringBuilder.toString());
	}

	/**
	 * Retourne un chaîne de caractères de l'instance courante
	 * formatées pour l'écriture dans un fichier texte
	 * @return
	 */
	public String versFichier() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(date.getDayOfMonth());
		stringBuilder.append(" ");
		stringBuilder.append(date.getMonthValue());
		stringBuilder.append(" ");
		stringBuilder.append(date.getYear());
		stringBuilder.append(" ");
		stringBuilder.append(temp);
		stringBuilder.append(" ");
		stringBuilder.append(prec);
		return (stringBuilder.toString());
	}

	/**
	 * Crée une instance de MeteoJour à partir d'une chaîne
	 * (qui a été formatée au préalable)
	 * @param str
	 * @return
	 */
	public static MeteoJour parse(String str) {
		MeteoJour mj = new MeteoJour();
		Scanner scan = new Scanner(str);
		scan.useDelimiter(" ");
		mj.setDate(scan.nextInt(), scan.nextInt(), scan.nextInt());
		mj.temp = scan.nextInt();
		mj.prec = scan.nextInt();
		scan.close();
		return mj;
	}


	/***
	 * Méthodes qui s'appuient sur l'implentation de l'interface Comparator qui nécessitent la
	 * redéfinition de la méthode compare
	 *
	 * Ces méthodes sont utiles notamment pour
	 * - les tris ; elles seront utilisées pour implémenter la règle de tri pour la méthode Collections.sort,
	 * - les recherches des min et max ; elles seront utilisées pour implémenter la règle de recherches pour
	 * respectivement les méthodes Collections.min, Collections.max
	 */

	/**
	 * Compare deux fiches MeteoJour par la température
	 */
	public static final Comparator<MeteoJour> ordreTemp = new Comparator<MeteoJour>() {


		/**
		 * Compare les instances mj1 et mj2 suivant l'ordre qui sera implémenté dans
		 * compare, ici comparaison suivant les températures
		 * @param mj1 instance de MeteJour, premier argument pour la comparaison
		 * @param mj2 instance de MeteJour, deuxième argument pour la comparaison
		 * @return (int)
		 * > 0 si mj1 est supérieur à mj2 suivant l'orde implémenté dans la méthode compare (à redéfinir)
		 * < 0 si mj1 est inférieur à mj2 suivant l'orde implémenté dans la méthode compare (à redéfinir)
		 * == 0 si mj1 est égale à mj2 suivant l'ordre implémenté dans la méthode compare (à redéfinir)
		 */
		public int compare(MeteoJour mj1, MeteoJour mj2) {

			/**
			 * 	Recupération et transtypage des températures des jour_1 et jour_2 pour
			 * 	comparaison des températures
			 */

			Integer temp_meteo_jour_1 = new Integer(mj1.temp);
			Integer temp_meteo_jour_2 = new Integer(mj2.temp);

			/**
			 * compareTo retourne une valeur entière
			 * > 0 si temp_meteo_jour_1 est supérieur à temp_meteo_jour_2
			 * < 0 si temp_meteo_jour_1 est inférieur à temp_meteo_jour_2
			 * == 0 si temp_meteo_jour_1 est égale à temp_meteo_jour_2
			 */
			return temp_meteo_jour_1.compareTo(temp_meteo_jour_2);
		}
	};


	/**
	 * Compare deux fiches MeteoJour par le taux de précipitations
	 */
	public static final Comparator<MeteoJour> ordrePrec = new Comparator<MeteoJour>() {



		/**
		 * Compare les instances mj1 et mj2 suivant l'ordre qui sera implémenté dans
		 * compare, ici comparaison suivant les taux de précipitation
		 * @param mj1 instance de MeteJour, premier argument pour la comparaison
		 * @param mj2 instance de MeteJour, deuxième argument pour la comparaison
		 * @return (int)
		 * > 0 si mj1 est supérieur à mj2 suivant l'orde implémenté dans la méthode compare (à redéfinir)
		 * < 0 si mj1 est inférieur à mj2 suivant l'orde implémenté dans la méthode compare (à redéfinir)
		 * == 0 si mj1 est égale à mj2 suivant l'ordre implémenté dans la méthode compare (à redéfinir)
		 */
		public int compare(MeteoJour mj1, MeteoJour mj2) {
			/**
			 * 	Recupération et transtypage des températures des jour_1 et jour_2 pour
			 * 	comparaison des taux de précipitations
			 */


			Integer prec_meteo_jour_1 = new Integer(mj1.prec);
			Integer prec_meteo_jour_2 = new Integer(mj2.prec);


			/**
			 * compareTo retourne une valeur entière
			 * > 0 si prec_meteo_jour_1 est supérieur à prec_meteo_jour_1
			 * < 0 si prec_meteo_jour_1 est inférieur à prec_meteo_jour_2
			 * == 0 si prec_meteo_jour_1 est égale à prec_meteo_jour_2
			 */
			return prec_meteo_jour_1.compareTo(prec_meteo_jour_2);
		}
	};

	/**
	 * Compare les instances mj1 et mj2 suivant l'ordre qui sera implémenté dans
	 * compare, ici comparaison suivant les dates
	 * @param mj1 instance de MeteJour, premier argument pour la comparaison
	 * @param mj2 instance de MeteJour, deuxième argument pour la comparaison
	 * @return (int)
	 * > 0 si mj1 est supérieur à mj2 suivant l'orde implémenté dans la méthode compare (à redéfinir)
	 * < 0 si mj1 est inférieur à mj2 suivant l'orde implémenté dans la méthode compare (à redéfinir)
	 * == 0 si mj1 est égale à mj2 suivant l'ordre implémenté dans la méthode compare (à redéfinir)
	 */
	public static final Comparator<MeteoJour> ordreTemps = new Comparator<MeteoJour>() {


		public int compare(MeteoJour mj1, MeteoJour mj2) {

			/**
			 * compareTo retourne une valeur entière
			 * > 0 si date_meteo_jour_1 est supérieur à date_meteo_jour_2
			 * < 0 si date_meteo_jour_1 est inférieur à date_meteo_jour_2
			 * == 0 si date_meteo_jour_1 est égale à date_meteo_jour_2
			 */
			return mj1.date.compareTo(mj2.date);
		}
	};


}	
	
