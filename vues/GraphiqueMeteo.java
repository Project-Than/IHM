package vues;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import modele.Meteo;
import modele.Modele;

public class GraphiqueMeteo extends JPanel implements Vue {

	private Meteo modele;
	
	public GraphiqueMeteo(/*Meteo m*/) {
		super();
		/*this.modele = m;*/
	}
	
	public void paintComponent(Graphics g) throws NullPointerException {
		try {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.scale(1, -1);
		g2d.translate(0, -getHeight());
		modele.triDate();
		int[] absJours = this.absJours1900();
		int[] ordTemp = this.ordTemp();
		int[] ordPrec = this.ordPrec();
 
		tracerCourbe(absJours, ordTemp, Color.RED, g2d);
		tracerCourbe(absJours, ordPrec, Color.BLUE, g2d);
		}
		catch (NullPointerException ex) {
			System.out.println("Null");
		}
	}
	
	public int[] absJours1900() {
		int[] tab = new int[modele.nbJours()];
 
		long tempsMin = modele.minJours1900();
		long tempsMax = modele.maxJours1900();
 
		for (int i = 0; i < tab.length; i++) {
			long temps = modele.getJours1900(i);
			tab[i] = (int) (this.getWidth() * (temps - tempsMin) / (tempsMax - tempsMin));
		}
		return tab;
		
	}
	
	public int[] ordTemp() {
		int[] tab = new int[modele.nbJours()];
 
		long tempMin = modele.minTemp();
		long tempMax = modele.maxTemp();
 
		for (int i = 0; i < tab.length; i++) {
			long temp = modele.getTemp(i);
			tab[i] = (int) (this.getHeight() * (temp - tempMin) / (tempMax - tempMin));
		}
		return tab;
		
	}
	
	public int[] ordPrec() {
		int[] tab = new int[modele.nbJours()];
 
		long precMin = modele.minPrec();
		long precMax = modele.maxPrec();
 
		for (int i = 0; i < tab.length; i++) {
			long prec = modele.getPrec(i);
			tab[i] = (int) (this.getHeight() * (prec - precMin) / (precMax - precMin));
		}
		return tab;
		
	}
	
	public void tracerCourbe(int[] abs, int[] ord, Color couleur, Graphics g)  {
		g.setColor(couleur);
		for(int i = 0 ;i<this.modele.nbJours()-1;i++) {
			g.drawLine(abs[i],ord[i],abs[i+1], ord[i+1]);
		}
}
	
	public void initialiser() {
		this.repaint();
	}
	@Override
	public void notifierChangement() {
		this.repaint();
	}

	@Override
	public void setModele(Modele m) {
		this.modele = (Meteo) m;
	}
}