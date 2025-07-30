
package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;

import java.io.*;
import java.util.*;
import java.text.Normalizer;

public class AgentCatalogue extends Agent {
    
    // Fonction utilitaire pour supprimer les accents et mettre en minuscule
    private String nettoyer(String texte) {
        return Normalizer.normalize(texte, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase();
    }

    // Méthode pour envoyer une notification
    private void envoyerNotification(String message) {
        ACLMessage notification = new ACLMessage(ACLMessage.INFORM);
        notification.addReceiver(new AID("AgentNotification", AID.ISLOCALNAME));
        notification.setContent(message);
        send(notification);
    }
    
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null && msg.getPerformative() == ACLMessage.REQUEST) {
                    String recherche = msg.getContent();

                    //  Notification de début de recherche
                    envoyerNotification("🔍 Recherche en cours pour : " + recherche);
                    
                    StringBuilder resultats = new StringBuilder();
                    int nombreResultats = 0;
                    
                   try (BufferedReader br = new BufferedReader(
         new InputStreamReader(new FileInputStream("data/livres.txt"), "UTF-8"))) {
    String ligne;
    while ((ligne = br.readLine()) != null) {
        if (nettoyer(ligne).contains(nettoyer(recherche))) {
            resultats.append(ligne).append("\n");
            nombreResultats++;
        }
    }
} catch (IOException e) {
    resultats.append("Erreur de lecture du fichier.");
    envoyerNotification("❌ Erreur lors de la recherche dans le catalogue");
}

                    
                    //  Notification du résultat de recherche
                    if (nombreResultats > 0) {
                        envoyerNotification("📚 " + nombreResultats + " livre(s) trouvé(s) pour : " + recherche);
                    } else {
                        envoyerNotification("📭 Aucun livre trouvé pour : " + recherche);
                    }
                    
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent(resultats.toString());
                    send(reply);
                } else {
                    block();
                }
            }
        });
    }
}
