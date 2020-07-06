import java.io.IOException;

import controleur.FenMeteo;
import modele.Meteo;
import vues.GraphiqueMeteo;
import vues.TableauMeteo;
import vues.TexteMeteo;
/*Classe permettant de lancer la fenêtre Méteo et la fenêtre contenant le JTable*/
public class TestModeleMeteo {
public static void main(String[] args) {
			Meteo m = new Meteo();
			TexteMeteo t = new TexteMeteo();
			GraphiqueMeteo g = new GraphiqueMeteo();
			FenMeteo f = new FenMeteo(m);
			f.ajouterPanneau("Liste",t);
			f.ajouterPanneau("Graphique", g);
			TableauMeteo tabmeteo = new TableauMeteo();
			f.ajoutTabMeteo(tabmeteo);
		}
}
