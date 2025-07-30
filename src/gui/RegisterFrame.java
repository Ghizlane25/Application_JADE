package gui;

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

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton cancelButton;
    private JLabel statusLabel;

    // Palette de couleurs moderne
    private final Color BACKGROUND_PRIMARY = new Color(250, 251, 252);
    private final Color BACKGROUND_SECONDARY = new Color(255, 255, 255);
    private final Color ACCENT_GREEN = new Color(34, 197, 94);
    private final Color ACCENT_RED = new Color(239, 68, 68);
    private final Color ACCENT_GRAY = new Color(107, 114, 128);
    private final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private final Color TEXT_SECONDARY = new Color(107, 114, 128);
    private final Color BORDER_COLOR = new Color(209, 213, 219);

    public RegisterFrame() {
        initializeComponents();
        setupLayout();
        setupStyling();
        setupEventHandlers();
        
        setTitle("📝 Créer un compte - Système Bibliothèque");
        setSize(520, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void initializeComponents() {
        // Champs de saisie
        usernameField = new JTextField("", 20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        passwordField = new JPasswordField("", 20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        confirmPasswordField = new JPasswordField("", 20);
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Boutons
        registerButton = createModernButton("📝 Créer le compte", ACCENT_GREEN);
        cancelButton = createModernButton("❌ Annuler", ACCENT_GRAY);

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
        button.setPreferredSize(new Dimension(180, 50));
        
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

        // En-tête
        JPanel headerPanel = createHeaderPanel();
        
        // Panel du formulaire
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(BACKGROUND_SECONDARY);
        formPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Champs du formulaire
        JPanel userPanel = createFieldPanel("👤 Nom d'utilisateur", usernameField, 
            "Choisissez un nom d'utilisateur unique");
        JPanel passPanel = createFieldPanel("🔑 Mot de passe", passwordField, 
            "Minimum 6 caractères recommandés");
        JPanel confirmPanel = createFieldPanel("🔒 Confirmer le mot de passe", confirmPasswordField, 
            "Retapez votre mot de passe");

        // Panel des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setBackground(BACKGROUND_SECONDARY);
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        // Panel de statut
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(BACKGROUND_SECONDARY);
        statusPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        statusPanel.add(statusLabel);

        // Assemblage
        formPanel.add(userPanel);
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(passPanel);
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(confirmPanel);
        formPanel.add(buttonPanel);
        formPanel.add(statusPanel);

        mainCard.add(headerPanel);
        mainCard.add(formPanel);

        // Panel conteneur avec marges
        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(BACKGROUND_PRIMARY);
        containerPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        containerPanel.add(mainCard);

        this.add(containerPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BACKGROUND_SECONDARY);
        headerPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

        JLabel logoLabel = new JLabel("👤", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Créer un nouveau compte", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Rejoignez la communauté universitaire", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(logoLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);

        return headerPanel;
    }

    private JPanel createFieldPanel(String labelText, JComponent field, String helpText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_SECONDARY);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel helpLabel = new JLabel(helpText);
        helpLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        helpLabel.setForeground(TEXT_SECONDARY);
        helpLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(helpLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(field);

        return panel;
    }

    private void setupStyling() {
        // Style des champs de texte
        styleTextField(usernameField);
        styleTextField(passwordField);
        styleTextField(confirmPasswordField);
    }

    private void styleTextField(JTextField field) {
        field.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(12, 15, 12, 15)
        ));
        field.setBackground(BACKGROUND_SECONDARY);
        field.setForeground(TEXT_PRIMARY);
        field.setPreferredSize(new Dimension(320, 45));
        field.setMaximumSize(new Dimension(320, 45));
    }

    private void setupEventHandlers() {
        registerButton.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();
            String confirmPass = new String(confirmPasswordField.getPassword()).trim();

            // Validation des champs
            if (user.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                showStatus("⚠️ Veuillez remplir tous les champs", ACCENT_RED);
                return;
            }

            if (user.length() < 3) {
                showStatus("⚠️ Le nom d'utilisateur doit contenir au moins 3 caractères", ACCENT_RED);
                return;
            }

            if (pass.length() < 4) {
                showStatus("⚠️ Le mot de passe doit contenir au moins 4 caractères", ACCENT_RED);
                return;
            }

            if (!pass.equals(confirmPass)) {
                showStatus("⚠️ Les mots de passe ne correspondent pas", ACCENT_RED);
                return;
            }

            // Vérifier si l'utilisateur existe déjà
            if (utilisateurExiste(user)) {
                showStatus("⚠️ Ce nom d'utilisateur existe déjà", ACCENT_RED);
                return;
            }

            showStatus("🔄 Création du compte en cours...", TEXT_SECONDARY);
            registerButton.setEnabled(false);

            // Simulation d'un délai de traitement
            Timer timer = new Timer(1500, event -> {
                if (creerUtilisateur(user, pass)) {
                    showStatus("✅ Compte créé avec succès !", ACCENT_GREEN);
                    Timer closeTimer = new Timer(1500, closeEvent -> dispose());
                    closeTimer.setRepeats(false);
                    closeTimer.start();
                } else {
                    showStatus("❌ Erreur lors de la création du compte", ACCENT_RED);
                    registerButton.setEnabled(true);
                }
            });
            timer.setRepeats(false);
            timer.start();
        });

        cancelButton.addActionListener(e -> dispose());

        // Validation en temps réel
        confirmPasswordField.addActionListener(e -> registerButton.doClick());
    }

    private void showStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    private boolean utilisateurExiste(String username) {
        try {
            File usersFile = new File("data/users.txt");
            if (!usersFile.exists()) {
                return false;
            }
            
            try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
                String ligne;
                while ((ligne = br.readLine()) != null) {
                    String[] parts = ligne.trim().split(";");
                    if (parts.length >= 1 && parts[0].trim().equals(username)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            showStatus("❌ Erreur de vérification", ACCENT_RED);
        }
        return false;
    }

    private boolean creerUtilisateur(String user, String pass) {
        try {
            // Créer le dossier data s'il n'existe pas
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/users.txt", true))) {
                writer.write(user + ";" + pass);
                writer.newLine();
                return true;
            }
        } catch (IOException ex) {
            return false;
        }
    }
}