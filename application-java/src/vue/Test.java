package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldIdBail;
    private JTextField textFieldDateDebut;
    private JTextField textFieldDateFin;
    private JTable tableBauxActuels;
    private CardLayout cardLayout;
    private JPanel panelChamps;
    private JPanel panelBailExistant;
    private JPanel panelNouveauBail;
    private JRadioButton rdbtnBailExistant;
    private JRadioButton rdbtnNouveauBail;
    private JPanel panel;

    // Main pour tester la fenêtre
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public Test() {
        setBounds(0, 0, 670, 470);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Titre principal
        JLabel lblTitreAjoutLoc = new JLabel("Ajouter un nouveau locataire 2/3");
        lblTitreAjoutLoc.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitreAjoutLoc.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPane.add(lblTitreAjoutLoc, BorderLayout.NORTH);


        // Ajouter un bouton pour Valider
        JPanel panelBoutons = new JPanel();
        JButton btnValider = new JButton("Valider");
        panelBoutons.add(btnValider);
        contentPane.add(panelBoutons, BorderLayout.SOUTH);
        
        panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));
        
        // CardLayout pour basculer entre les panels
        panelChamps = new JPanel();
        cardLayout = new CardLayout();
        panelChamps.setLayout(cardLayout);
        panel.add(panelChamps, BorderLayout.CENTER);

        // Panel pour les champs de bail existant
        panelBailExistant = new JPanel();
        panelBailExistant.setLayout(new BoxLayout(panelBailExistant, BoxLayout.Y_AXIS));
        panelBailExistant.add(new JLabel("Liste des baux existants :"));

        // Exemple de tableau de baux existants
        tableBauxActuels = new JTable(new Object[][]{
            {"Bail 1", "Adresse 1", "01/01/2023", "01/01/2024"},
            {"Bail 2", "Adresse 2", "01/06/2023", "01/06/2024"},
        }, new String[]{"Bail", "Adresse", "Date de début", "Date de fin"});
        JScrollPane scrollPaneBauxActuels = new JScrollPane(tableBauxActuels);
        panelBailExistant.add(scrollPaneBauxActuels);

        // Panel pour les champs de nouveau bail
        panelNouveauBail = new JPanel();
        panelNouveauBail.setLayout(new BoxLayout(panelNouveauBail, BoxLayout.Y_AXIS));
        panelNouveauBail.add(new JLabel("ID Bail :"));
        textFieldIdBail = new JTextField();
        panelNouveauBail.add(textFieldIdBail);

        panelNouveauBail.add(new JLabel("Date de début :"));
        textFieldDateDebut = new JTextField();
        panelNouveauBail.add(textFieldDateDebut);

        panelNouveauBail.add(new JLabel("Date de fin :"));
        textFieldDateFin = new JTextField();
        panelNouveauBail.add(textFieldDateFin);

        // Ajouter les panels au CardLayout
        panelChamps.add(panelBailExistant, "BailExistant");
        panelChamps.add(panelNouveauBail, "NouveauBail");

        // Panel des boutons radio
        JPanel panelRadio = new JPanel();
        rdbtnBailExistant = new JRadioButton("Rattacher à un bail existant");
        rdbtnNouveauBail = new JRadioButton("Créer un nouveau bail");

        // Groupe mutuellement exclusif
        ButtonGroup group = new ButtonGroup();
        group.add(rdbtnBailExistant);
        group.add(rdbtnNouveauBail);

        // ActionListeners pour basculer entre les vues
        rdbtnBailExistant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelChamps, "BailExistant");
            }
        });

        rdbtnNouveauBail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelChamps, "NouveauBail");
            }
        });

        // Ajouter les boutons radio au panel
        panelRadio.add(rdbtnBailExistant);
        panelRadio.add(rdbtnNouveauBail);
        panel.add(panelRadio, BorderLayout.NORTH);

        // Sélection par défaut
        rdbtnBailExistant.setSelected(true);
        cardLayout.show(panelChamps, "BailExistant");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdbtnBailExistant.isSelected()) {
                    // Logique pour rattacher à un bail existant
                    JOptionPane.showMessageDialog(null, "Bail existant sélectionné");
                } else if (rdbtnNouveauBail.isSelected()) {
                    // Logique pour créer un nouveau bail
                    JOptionPane.showMessageDialog(null, "Nouveau bail sélectionné");
                }
            }
        });
    }

}
