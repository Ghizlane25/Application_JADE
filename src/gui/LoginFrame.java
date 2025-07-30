package gui;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import agents.AgentUtilisateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private JLabel statusLabel;
    private AgentUtilisateur agentUtilisateur;

    // Palette de couleurs moderne
    private final Color BACKGROUND_PRIMARY = new Color(250, 251, 252);
    private final Color BACKGROUND_SECONDARY = new Color(255, 255, 255);
    private final Color ACCENT_BLUE = new Color(59, 130, 246);
    private final Color ACCENT_GREEN = new Color(34, 197, 94);
    private final Color ACCENT_RED = new Color(239, 68, 68);
    private final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private final Color TEXT_SECONDARY = new Color(107, 114, 128);
    private final Color BORDER_COLOR = new Color(209, 213, 219);
    private final Color SHADOW_COLOR = new Color(0, 0, 0, 10);

    public LoginFrame(AgentUtilisateur agentUtilisateur) {
        this.agentUtilisateur = agentUtilisateur;
        
        initializeComponents();
        setupLayout();
        setupStyling();
        setupEventHandlers();
        
        setTitle("🔐 Authentification - Système Bibliothèque");
        setSize(480, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initializeComponents() {
        // Champs de saisie avec style moderne
        usernameField = new JTextField("", 20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        passwordField = new JPasswordField("", 20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Boutons avec design moderne
        loginButton = createModernButton("🔓 Se connecter", ACCENT_BLUE);
        registerButton = createModernButton("👤 Créer un compte", ACCENT_GREEN);

        // Label de statut
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private JButton createModernButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(15, 25, 15, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 50));
        
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

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(TEXT_SECONDARY);
        field.setText(placeholder);
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_PRIMARY);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(TEXT_SECONDARY);
                }
            }
        });
        
        return field;
    }

    private void setupLayout() {
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(BACKGROUND_PRIMARY);

        // Panel principal avec effet carte
        JPanel mainCard = new JPanel();
        mainCard.setLayout(new BoxLayout(mainCard, BoxLayout.Y_AXIS));
        mainCard.setBackground(BACKGROUND_SECONDARY);
        mainCard.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(40, 40, 40, 40)
        ));

        // En-tête avec logo et titre
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BACKGROUND_SECONDARY);
        headerPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

        JLabel logoLabel = new JLabel("📚", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Authentification", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Accédez à votre bibliothèque universitaire", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(logoLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);

        // Panel du formulaire
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(BACKGROUND_SECONDARY);

        // Champ nom d'utilisateur
        JPanel userPanel = createFieldPanel("👤 Nom d'utilisateur", usernameField);
        
        // Champ mot de passe
        JPanel passPanel = createFieldPanel("🔑 Mot de passe", passwordField);

        // Panel des boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(BACKGROUND_SECONDARY);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(registerButton);

        // Panel de statut
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(BACKGROUND_SECONDARY);
        statusPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        statusPanel.add(statusLabel);

        // Assemblage
        formPanel.add(userPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(passPanel);
        formPanel.add(buttonPanel);
        formPanel.add(statusPanel);

        mainCard.add(headerPanel);
        mainCard.add(formPanel);

        // Panel conteneur avec marges
        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(BACKGROUND_PRIMARY);
        containerPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        containerPanel.add(mainCard);

        this.add(containerPanel, BorderLayout.CENTER);
    }

    private JPanel createFieldPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_SECONDARY);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(field);

        return panel;
    }

    private void setupStyling() {
        // Style des champs de texte
        styleTextField(usernameField);
        styleTextField(passwordField);
    }

    private void styleTextField(JTextField field) {
        field.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(12, 15, 12, 15)
        ));
        field.setBackground(BACKGROUND_SECONDARY);
        field.setForeground(TEXT_PRIMARY);
        field.setPreferredSize(new Dimension(300, 45));
        field.setMaximumSize(new Dimension(300, 45));
    }

    private void setupEventHandlers() {
        loginButton.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();
            
            if (user.isEmpty() || pass.isEmpty()) {
                showStatus("⚠️ Veuillez remplir tous les champs", ACCENT_RED);
                return;
            }
            
            showStatus("🔄 Vérification en cours...", ACCENT_BLUE);
            loginButton.setEnabled(false);
            
            // Simulation d'un délai de traitement
            Timer timer = new Timer(1000, event -> {
                if (validerUtilisateur(user, pass)) {
                    showStatus("✅ Connexion réussie !", ACCENT_GREEN);
                    Timer closeTimer = new Timer(500, closeEvent -> {
                        dispose();
                        agentUtilisateur.gui = new InterfaceBibliotheque(agentUtilisateur);
                    });
                    closeTimer.setRepeats(false);
                    closeTimer.start();
                } else {
                    showStatus("❌ Identifiants invalides", ACCENT_RED);
                    loginButton.setEnabled(true);
                    passwordField.setText("");
                }
            });
            timer.setRepeats(false);
            timer.start();
        });

        registerButton.addActionListener(e -> {
            RegisterFrame registerFrame = new RegisterFrame();
            registerFrame.setVisible(true);
        });

        // Permet de valider avec Entrée
        passwordField.addActionListener(e -> loginButton.doClick());
    }

    private void showStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    private boolean validerUtilisateur(String user, String pass) {
        try {
            // Créer le dossier data s'il n'existe pas
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            File usersFile = new File("data/users.txt");
            if (!usersFile.exists()) {
                return false; // Aucun utilisateur enregistré
            }
            
            try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
                String ligne;
                while ((ligne = br.readLine()) != null) {
                    String[] parts = ligne.trim().split(";");
                    if (parts.length == 2 && parts[0].trim().equals(user) && parts[1].trim().equals(pass)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            showStatus("❌ Erreur de lecture des données", ACCENT_RED);
        }
        return false;
    }
}