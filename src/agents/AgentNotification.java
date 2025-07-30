package agents;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;

public class AgentNotification extends Agent {

    protected void setup() {
        System.out.println(getLocalName() + " démarré.");

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    String contenu = msg.getContent();
                    String expediteur = msg.getSender().getLocalName();
                    
                    // Créer une notification formatée
                    String notification = "📢 [" + expediteur + "] : " + contenu;
                    
                    // Envoyer la notification à l'utilisateur
                    ACLMessage notificationMsg = new ACLMessage(ACLMessage.INFORM);
                    notificationMsg.setContent(notification);
                    notificationMsg.addReceiver(new AID("AgentUtilisateur", AID.ISLOCALNAME));
                    
                    send(notificationMsg);
                    System.out.println("Notification envoyée : " + notification);
                } else {
                    block();
                }
            }
        });
    }
}