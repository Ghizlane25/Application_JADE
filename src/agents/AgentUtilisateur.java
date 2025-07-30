
package agents;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;
import gui.InterfaceBibliotheque;
import gui.LoginFrame;

import javax.swing.*;

public class AgentUtilisateur extends Agent {
    public InterfaceBibliotheque gui;

    protected void setup() {
        System.out.println(getLocalName() + " démarré.");

        //  Lancer l'interface de connexion (login) au démarrage
        SwingUtilities.invokeLater(() -> {
            new LoginFrame(this).setVisible(true);
        });

        // Comportement pour recevoir les messages
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null && gui != null) {
                    gui.afficherResultat(msg.getContent());
                }
                block();
            }
        });
    }
}
