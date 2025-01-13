package controleur;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UtilitaireTable {
	
	private UtilitaireTable() {}
	
    public static void viderTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

}
