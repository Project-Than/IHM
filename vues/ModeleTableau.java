package vues;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import modele.MeteoJour;

public class ModeleTableau extends AbstractTableModel {
    private final ArrayList<MeteoJour> mliste = new ArrayList<MeteoJour>();
    
    /*entête du JTable*/
    private final String[] entetes = {"Date","Température","Précipitation"};
 
    public ModeleTableau() {
        super();
    } 
    public int getRowCount() {
        return mliste.size();
    }
 
    public int getColumnCount() {
        return entetes.length;
    }
 
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }
 
 
    public ArrayList<MeteoJour> getMliste() {
		return mliste;
	}
    
    /*Méthode permettant d'ajouter une méteo à la liste du modèle*/
	public void addMeteo(MeteoJour m) {
        mliste.add(m);
 
        fireTableRowsInserted(mliste.size() -1, mliste.size() -1);
    }
 
	/*Méthode permettant de supprimer une méteo de la liste du modèle*/
    public void removeMeteo(int rowIndex) {
        mliste.remove(rowIndex);
 
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    
    /*Méthode qui supprime toutes les valeurs du modèle et réinitialise l'affichage du modèle*/
    
    public void removeAll() {
    	int size = mliste.size();
    	mliste.clear();
    	fireTableRowsDeleted(0,size);
    }
	@Override
	/*
	 * Méthode permettant de récupérer la date,la température et la précipitation du modèle */
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return mliste.get(rowIndex).getDate();
		case 1:
			return mliste.get(rowIndex).getTemp();
		case 2:
			return mliste.get(rowIndex).getPrec();
		default:
			return null;
		}
	}
}