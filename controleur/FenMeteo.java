package controleur;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import modele.Meteo;
import modele.MeteoJour;
import vues.GraphiqueMeteo;
import vues.TableauMeteo;
import vues.TexteMeteo;
import vues.Vue;

public class FenMeteo extends JFrame implements Controleur {

	static final String[] MOIS = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre",
			"Octobre", "Novembre", "Décembre" };
	static final Integer[] ANNEES = { new Integer(2019), new Integer(2020), new Integer(2021), new Integer(2022) };

	private Meteo modele;
	
	private JPanel panPrincipal;
	private JButton btnSauver;
	private JButton btnCharger;
	/*private JTextArea txtListe;*/
	private JButton btnSup;
	private JButton[] btnJours;
	private JButton[] btnMois;
	private JComboBox<Integer> cbbAnnees;
	private JButton btnAjouter;
	private JTabbedPane tabbedPane;
	private JTextField txtTemp;
	private JTextField txtPrec;
	private JLabel lblDate;
	private JLabel temp;
	private JLabel prec;
	private String jcourant;
	private String mcourant;
	private JTabbedPane tbpVues;
	private TableauMeteo tm;
	private int value = 0; /*compteur permettant d'activer l'onglet Graphique lorsque la valeur minimal des MeteoJour sera atteinte dans la liste*/
	static final int ZONE_JOURS = 1;
	static final int ZONE_MOIS = 2;
	static final int ZONE_AUTRE = 3;
	static final int CODE_AJOUTER = 1;
	static final int CODE_CHARGER = 2;
	static final int CODE_SAUVER = 3;
	static final int CODE_SUPP = 4;

	public FenMeteo(String str) {

		super("Météo");
		if (str != null)
			modele = new Meteo(str);
		else
			modele = new Meteo();
		this.initComposants();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.centrer(0.5);
		this.setVisible(true);
		this.initEcouteurs();
	}
	
	public FenMeteo(Meteo m) {
		super("Météo");
		this.modele = m;
		this.initComposants();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.centrer(0.5);
		this.setVisible(true);
		this.initEcouteurs();
	}

	private void initComposants() throws NullPointerException {
		try{
		panPrincipal = new JPanel();
		this.add(panPrincipal);
		panPrincipal.setLayout(new BorderLayout());
		panPrincipal.add(buildPanelFichiers(), BorderLayout.SOUTH);
		panPrincipal.add(buildPanelAjout(), BorderLayout.NORTH);
		panPrincipal.add(buildPanelMeteo(), BorderLayout.CENTER);
		panPrincipal.add(buildPanelJours(), BorderLayout.WEST);
		panPrincipal.add(buildPanelMois(), BorderLayout.EAST);
		} 
		catch (NullPointerException e) {
			System.out.println("null");
		}

	}

	public void initEcouteurs() {
		this.btnCharger.addActionListener(new EcouteurBoutons(ZONE_AUTRE, CODE_CHARGER));
		this.btnSauver.addActionListener(new EcouteurBoutons(ZONE_AUTRE, CODE_SAUVER));
		this.btnAjouter.addActionListener(new EcouteurBoutons(ZONE_AUTRE, CODE_AJOUTER));
		this.btnSup.addActionListener(new EcouteurBoutons(ZONE_AUTRE, CODE_SUPP));
		for (int i = 0; i < this.btnJours.length; i++) {
			this.btnJours[i].addActionListener(new EcouteurBoutons(ZONE_JOURS, i));
		}
		for (int j = 0; j < this.btnMois.length; j++) {
			this.btnMois[j].addActionListener(new EcouteurBoutons(ZONE_MOIS, j));
		}
	}
	

	public JPanel buildPanelFichiers() {
		JPanel pan = new JPanel();

		btnCharger = new JButton("Charger");
		pan.add(btnCharger);

		btnSauver = new JButton("Sauver");
		pan.add(btnSauver);

		pan.setBorder(BorderFactory.createEtchedBorder());

		return pan;
	}

	public JPanel buildPanelAjout() {

		JPanel pan = new JPanel();
		this.jcourant = "";
		this.mcourant = "";
		lblDate = new JLabel(this.jcourant + "/" + this.mcourant);
		pan.add(lblDate);
		cbbAnnees = new JComboBox(this.ANNEES);
		pan.add(cbbAnnees);

		temp = new JLabel("Température:");
		pan.add(temp);
		txtTemp = new JTextField("", 10);
		pan.add(txtTemp);

		prec = new JLabel("Précipitation:");
		pan.add(prec);
		txtPrec = new JTextField("", 10);
		pan.add(txtPrec);
		btnAjouter = new JButton("Ajouter");
		pan.add(btnAjouter);
		btnSup = new JButton("Initialiser");
		pan.add(btnSup);
		pan.setBorder(BorderFactory.createEtchedBorder());

		return pan;
	}

	public JTabbedPane buildPanelMeteo() {
		this.tbpVues = new JTabbedPane();
		return this.tbpVues;
	}
	
	public void ajoutTabMeteo(Vue v) {
		this.modele.enregistrer(v);
		tm = (TableauMeteo) v;
	}
	
	public void ajouterPanneau(String nom,Vue v) {
		this.modele.enregistrer(v);
		tbpVues.addTab(nom,(JComponent) v);
		this.panPrincipal.add(this.tbpVues);
		if (this.tbpVues.getTabCount()==2) {
				this.tbpVues.setEnabledAt(1,false);
		}
	}

	public JPanel buildPanelJours() {

		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(11, 1, -1, -1));
		btnJours = new JButton[31];
		for (int i = 0; i < 31; i++) {
			btnJours[i] = new JButton(Integer.toString(i + 1));
			pan.add(btnJours[i]);
		}
		pan.setBorder(BorderFactory.createEtchedBorder());
		return pan;
	}

	public JPanel buildPanelMois() {

		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(12, 1, -1, -1));
		btnMois = new JButton[12];
		for (int i = 0; i < this.MOIS.length; i++) {
			btnMois[i] = new JButton(this.MOIS[i]);
			pan.add(btnMois[i]);
		}
		pan.setBorder(BorderFactory.createEtchedBorder());
		return pan;
	}
	/*
	 * Méthode permettant de centrer le JFrame
	 */
	public void centrer(double d) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int largeur = (int) (d * dim.width);
		int longueur = (int) (d * dim.height);
		this.setBounds((dim.width - largeur) / 2, (dim.height - longueur) / 2, largeur, longueur);
	}
	/*
	 * Méthode permettant de charger une base de donnnées de météo depuis un fichier .txt
	 */
	public void chargerMeteo() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setFileFilter(new FileNameExtensionFilter("txt file","txt"));
		File fileselected = null;
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			fileselected = fileChooser.getSelectedFile();
			JOptionPane.showMessageDialog(null, "Chargement des données effectuées!");
			System.out.println(
					"Les données ont été chargées depuis le fichier suivant: " + fileselected.getAbsolutePath());
			this.modele.charger(fileselected.getName());
			/*readFile(txtListe, fileselected);*/
			this.modele.notifierToutesVues();
			for (MeteoJour j : this.modele.getListe()) {
				value++;
			}
			if (value>=3) {
				this.tbpVues.setEnabledAt(1, true);
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(null, "Aucunes données n'ont été chargées...");
		}
	}

	/*
	 * Méthode permettant de lire le contenu texte d'un fichier et de le stocker
	 * dans un JTextArea
	 */
	public void readFile(JTextArea j, File name) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(name));
		StringBuffer sb = new StringBuffer();
		String st;
		j.setText("");
		while ((st = br.readLine()) != null) {
			sb.append(st);
			sb.append("\n");

		}
		j.append(sb.toString());
		br.close();
	}

	/*
	 * Méthode permettant d'initialiser les données de la base de données
	 */
	public void Initialiser() {
		String[] options = { "Confirmer", "Annuler" };
		int result = JOptionPane.showOptionDialog(this, "Voulez vous vraiment initialiser les données ?",
				"Confirmer l'initialisation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[0]);
		if (result == JOptionPane.YES_OPTION) {
			this.modele.InitialiserModele();
			this.tbpVues.setEnabledAt(1,false);
			value = 0;
		} else if (result == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Aucune action n'a été faite");
		}
	}

	/*
	 * Méthode permettant de sauvegarder les données entrées dans la base de données
	 */
	public void sauver() throws IOException {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("txt file","txt"));
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fc.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "Sauvegarde des donneés éffectuées!");
			File f = fc.getSelectedFile();
			String filename = f.getAbsolutePath();
			if (!filename.endsWith(".txt")) {
				f = new File(filename+".txt");
			}
			FileWriter fw = new FileWriter(f);
			String text = this.modele.versFichier();
			fw.write(text);
			fw.close();
		} else if (result == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(null, "Sauvegarde annulée...");
		}
	}

	/*
	 * Méthode permettant d'ajouter une météo du jour dans la base de données
	 */
	public void ajoutMeteo(int jour, int mois, int annee, int t, int p) {
		try {
			this.modele.ajouter(jour, mois, annee, t, p);
			System.out.println(this.modele.toString());
			this.modele.notifierToutesVues();
			value++;
		} catch (NumberFormatException e) {
			System.out.println(e);
		}
	}

	/*
	 * Inner classe qui implémente les écouteurs pour chaque action effectuées (appuie sur un bouton etc..)
	 */
	class EcouteurBoutons implements ActionListener {
		private int zone;
		private int code;

		public EcouteurBoutons(int z, int c) {
			this.zone = z;
			this.code = c;
		}

		public void actionPerformed(ActionEvent e) {
			switch (zone) {
			case ZONE_JOURS: {
				System.out.println("Bouton JOUR no " + code);
				jcourant = Integer.toString(code + 1);
				lblDate.setText(jcourant + "/" + mcourant);
				break;
			}
			case ZONE_MOIS: {
				System.out.println("Bouton MOIS no " + code);
				mcourant = Integer.toString(code + 1);
				lblDate.setText(jcourant + "/" + mcourant);
				break;
			}
			case ZONE_AUTRE:
				switch (code) {
				case CODE_AJOUTER:
					System.out.println("J'ajoute");
					try {
						ajoutMeteo(Integer.valueOf(jcourant), Integer.valueOf(mcourant),
								cbbAnnees.getSelectedItem().hashCode(), Integer.valueOf(txtTemp.getText()),
								Integer.valueOf(txtPrec.getText()));
						if (value == 3) {
							tbpVues.setEnabledAt(1,true);
						}
					} catch (NumberFormatException e3) {
						System.out.println("Veuillez remplir le champs 'Ajout' svp");
					}
					break;
				case CODE_CHARGER:
					System.out.println("Je charge");
					try {
						chargerMeteo();
					} catch (IOException e2) {
						e2.printStackTrace();
						System.out.println("Le champ est vide");
					}
					break;
				case CODE_SAUVER:
					System.out.println("Je sauve");
					try {
						sauver();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				case CODE_SUPP:
					System.out.println("Je Supprime");
					Initialiser();
					break;
				default:
					break;
				}
			default:
				break;
			}
		}
	}
	
}
