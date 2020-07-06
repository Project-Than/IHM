package vues;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modele.Meteo;
import modele.MeteoJour;
import modele.Modele;

public class TableauMeteo extends JFrame implements Vue {
	
    private ModeleTableau modeletab = new ModeleTableau();
    private JTable tableau;
    private Meteo modele;
 
    public TableauMeteo() {
        super();
        this.setVisible(true);
        setTitle("Tableau Météo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        tableau = new JTable(modeletab);
 
        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
 
 
        pack();
    }
    
    public ModeleTableau getModeletab() {
		return modeletab;
	}

	public void setModeletab(ModeleTableau modeletab) {
		this.modeletab = modeletab;
	}

	public JTable getTableau() {
		return tableau;
	}

	public void setTableau(JTable tableau) {
		this.tableau = tableau;
	}

/*
 * Méthode permettant d'ajouter une méteo au modèle du JTable
 */
	public void addMeteo(MeteoJour m) {
    	modeletab.addMeteo(m);
    }
	
/*
 * Méthode notifiant les changements à toutes les vues du modèle et qui met à jour le modèle du JTable */
	@Override
	public void notifierChangement() {
		modeletab.removeAll();
		for(int i = 0 ; i<=modele.getListe().size()-1;i++) {
			modeletab.addMeteo(modele.getListe().get(i));
		}
	}

/*
 * Méthode permettant de set le modèle de la vue
 */
	@Override
	public void setModele(Modele m) {
		this.modele = (Meteo) m;
	}

/*
 * Méthode permettant d'initialiser les données du modèle du JTable
 */
	@Override
	public void initialiser() {
		modeletab.getMliste().clear();
	}
	
 

}