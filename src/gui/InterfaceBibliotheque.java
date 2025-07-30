package gui;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfaceBibliotheque extends JFrame {
    private JTextField champRecherche;
    private JButton boutonRechercher;
    private JButton boutonEmprunter;
    private JButton boutonActualiser;
    private JTextArea zoneResultats;
    private JLabel labelStatut;

    // Palette de couleurs moderne
    private final Color BACKGROUND_PRIMARY = new Color(250, 251, 252);
    private final Color BACKGROUND_SECONDARY = new Color(255, 255, 255);
    private final Color ACCENT_BLUE = new Color(59, 130, 246);
    private final Color ACCENT_GREEN = new Color(34, 197, 94);
    private final Color ACCENT_ORANGE = new Color(249, 115, 22);
    private final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private final Color TEXT_SECONDARY = new Color(107, 114, 128);
    private final Color BORDER_COLOR = new Color(229, 231, 235);
    private final Color HOVER_COLOR = new Color(243, 244, 246);

    public InterfaceBibliotheque(Agent agent) {
        super("📚 Système de Gestion Bibliothèque - Université");
        
        initializeComponents(agent);
        setupLayout();
        setupStyling();
        setupEventHandlers(agent);
        
        this.setSize(900, 650);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initializeComponents(Agent agent) {
        // Champ de recherche avec placeholder effet
        champRecherche = new JTextField("Saisissez le titre du livre...", 25);
        champRecherche.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        champRecherche.setForeground(TEXT_SECONDARY);
        
        // Boutons avec design moderne
        boutonRechercher = createModernButton("🔍 Rechercher", ACCENT_BLUE);
        boutonEmprunter = createModernButton("📖 Emprunter", ACCENT_GREEN);
        boutonActualiser = createModernButton("🔄 Actualiser", ACCENT_ORANGE);

        // Zone de résultats plus grande et mieux stylée
        zoneResultats = new JTextArea(20, 40);
        zoneResultats.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        zoneResultats.setEditable(false);
        zoneResultats.setLineWrap(true);
        zoneResultats.setWrapStyleWord(true);
        zoneResultats.setBackground(BACKGROUND_SECONDARY);
        zoneResultats.setForeground(TEXT_PRIMARY);
        zoneResultats.setText("📋 Bienvenue dans le système de gestion de bibliothèque\n\n" +
                             "🔍 Utilisez le champ de recherche pour trouver des livres\n" +
                             "📖 Cliquez sur 'Emprunter' pour emprunter un livre\n" +
                             "🔄 Utilisez 'Actualiser' pour réinitialiser l'interface\n\n" +
                             "✨ Prêt à vous servir !");

        // Label de statut
        labelStatut = new JLabel("🟢 Système prêt");
        labelStatut.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelStatut.setForeground(ACCENT_GREEN);
    }

    private JButton createModernButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Effet hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
    }

    private void setupLayout() {
        this.setLayout(new BorderLayout(0, 10));
        
        // Panel principal avec titre
        JPanel panelTitre = new JPanel(new BorderLayout());
        panelTitre.setBackground(BACKGROUND_PRIMARY);
        panelTitre.setBorder(new EmptyBorder(20, 30, 15, 30));
        
        JLabel titre = new JLabel("📚 Système de Gestion Bibliothèque");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titre.setForeground(TEXT_PRIMARY);
        
        JLabel sousTitre = new JLabel("Recherchez et empruntez vos livres facilement");
        sousTitre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sousTitre.setForeground(TEXT_SECONDARY);
        
        JPanel panelTitres = new JPanel(new BorderLayout());
        panelTitres.setBackground(BACKGROUND_PRIMARY);
        panelTitres.add(titre, BorderLayout.NORTH);
        panelTitres.add(sousTitre, BorderLayout.SOUTH);
        
        panelTitre.add(panelTitres, BorderLayout.WEST);
        panelTitre.add(labelStatut, BorderLayout.EAST);

        // Panel de recherche
        JPanel panelRecherche = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        panelRecherche.setBackground(BACKGROUND_SECONDARY);
        panelRecherche.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(15, 25, 15, 25)
        ));
        
        JLabel labelRecherche = new JLabel("🔎 Recherche :");
        labelRecherche.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelRecherche.setForeground(TEXT_PRIMARY);
        
        panelRecherche.add(labelRecherche);
        panelRecherche.add(champRecherche);
        panelRecherche.add(boutonRechercher);
        panelRecherche.add(boutonEmprunter);
        panelRecherche.add(boutonActualiser);

        // Panel des résultats
        JPanel panelResultats = new JPanel(new BorderLayout());
        panelResultats.setBackground(BACKGROUND_PRIMARY);
        panelResultats.setBorder(new EmptyBorder(10, 30, 30, 30));
        
        JLabel labelResultats = new JLabel("📋 Résultats et Informations");
        labelResultats.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelResultats.setForeground(TEXT_PRIMARY);
        labelResultats.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JScrollPane scrollPane = new JScrollPane(zoneResultats);
        scrollPane.setBorder(new LineBorder(BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(BACKGROUND_SECONDARY);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        panelResultats.add(labelResultats, BorderLayout.NORTH);
        panelResultats.add(scrollPane, BorderLayout.CENTER);

        // Assemblage final
        this.add(panelTitre, BorderLayout.NORTH);
        this.add(panelRecherche, BorderLayout.CENTER);
        this.add(panelResultats, BorderLayout.SOUTH);
    }

    private void setupStyling() {
        this.getContentPane().setBackground(BACKGROUND_PRIMARY);
        
        // Style du champ de recherche
        champRecherche.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        champRecherche.setBackground(BACKGROUND_SECONDARY);
    }

    private void setupEventHandlers(Agent agent) {
        // Effet placeholder pour le champ de recherche
        champRecherche.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (champRecherche.getText().equals("Saisissez le titre du livre...")) {
                    champRecherche.setText("");
                    champRecherche.setForeground(TEXT_PRIMARY);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (champRecherche.getText().isEmpty()) {
                    champRecherche.setText("Saisissez le titre du livre...");
                    champRecherche.setForeground(TEXT_SECONDARY);
                }
            }
        });

        boutonRechercher.addActionListener(e -> {
            String requete = getTextFieldValue();
            if (!requete.isEmpty()) {
                labelStatut.setText("🔍 Recherche en cours...");
                labelStatut.setForeground(ACCENT_BLUE);
                
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID("AgentCatalogue", AID.ISLOCALNAME));
                msg.setContent(requete);
                agent.send(msg);
            } else {
                afficherResultat("⚠️ Erreur de saisie\n\nVeuillez saisir un titre de livre à rechercher.\n\n💡 Conseil : Tapez le titre complet ou partiel du livre que vous recherchez.");
                labelStatut.setText("⚠️ Saisie requise");
                labelStatut.setForeground(ACCENT_ORANGE);
            }
        });

        boutonEmprunter.addActionListener(e -> {
            String titre = getTextFieldValue();
            if (!titre.isEmpty()) {
                labelStatut.setText("📖 Traitement de l'emprunt...");
                labelStatut.setForeground(ACCENT_GREEN);
                
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID("AgentBibliothecaire", AID.ISLOCALNAME));
                msg.setContent(titre);
                agent.send(msg);
            } else {
                afficherResultat("⚠️ Erreur de saisie\n\nVeuillez saisir un titre de livre à emprunter.\n\n💡 Conseil : Assurez-vous que le livre est disponible avant de l'emprunter.");
                labelStatut.setText("⚠️ Saisie requise");
                labelStatut.setForeground(ACCENT_ORANGE);
            }
        });

        boutonActualiser.addActionListener(e -> {
            champRecherche.setText("Saisissez le titre du livre...");
            champRecherche.setForeground(TEXT_SECONDARY);
            
            zoneResultats.setText("✅ Interface actualisée avec succès\n\n" +
                                 "📋 Système de gestion bibliothèque prêt\n\n" +
                                 "🔍 Vous pouvez maintenant :\n" +
                                 "   • Rechercher des livres par titre\n" +
                                 "   • Emprunter des ouvrages disponibles\n" +
                                 "   • Consulter les informations détaillées\n\n" +
                                 "✨ Bonne recherche !");
            
            labelStatut.setText("🟢 Système prêt");
            labelStatut.setForeground(ACCENT_GREEN);
            
            ACLMessage msgNotification = new ACLMessage(ACLMessage.INFORM);
            msgNotification.addReceiver(new AID("AgentNotification", AID.ISLOCALNAME));
            msgNotification.setContent("Interface actualisée par l'utilisateur");
            agent.send(msgNotification);
        });
    }

    private String getTextFieldValue() {
        String text = champRecherche.getText().trim();
        return text.equals("Saisissez le titre du livre...") ? "" : text;
    }

    public void afficherResultat(String texte) {
        zoneResultats.setText(texte);
        labelStatut.setText("✅ Opération terminée");
        labelStatut.setForeground(ACCENT_GREEN);
    }
}