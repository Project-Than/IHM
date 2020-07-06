package vues;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import modele.Meteo;
import modele.Modele;

public class TexteMeteo extends JScrollPane implements Vue {
 
	private Meteo modele;
	private JTextArea txtMeteo;
 
	public TexteMeteo() {
		super();
		txtMeteo = new JTextArea(15, 40);
		txtMeteo.setText("");
		txtMeteo.setEditable(false);
		this.setViewportView(txtMeteo);
	}
 
	public void afficherModele() {
		txtMeteo.setText(this.modele.toString());
	}
	public void notifierChangement() {
		this.afficherModele();
	}

	@Override
	public void setModele(Modele m) {
		this.modele = (Meteo) m;
		this.afficherModele();
	}
	
	public void initialiser() {
		this.txtMeteo.setText("");
	}
	
}
