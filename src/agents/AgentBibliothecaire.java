package agents;

import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import jade.lang.acl.MessageTemplate;

public class AgentBibliothecaire extends Agent {
    
    // Méthode pour envoyer une notification
    private void envoyerNotification(String message) {
        ACLMessage notification = new ACLMessage(ACLMessage.INFORM);
        notification.addReceiver(new AID("AgentNotification", AID.ISLOCALNAME));
        notification.setContent(message);
        send(notification);
    }
    
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            
            public void action() {
                addBehaviour(new jade.core.behaviours.CyclicBehaviour() {
                    public void action() {
                        ACLMessage msg = receive();
                        if (msg != null && msg.getPerformative() == ACLMessage.REQUEST) {
                            String titre = msg.getContent().toLowerCase();
                            
                            // Notification de début de traitement
                            envoyerNotification("Traitement de la demande d'emprunt pour : " + titre);
                            
                            ACLMessage demandeStock = new ACLMessage(ACLMessage.REQUEST);
                            demandeStock.addReceiver(new AID("AgentStock", AID.ISLOCALNAME));
                            demandeStock.setContent(titre);
                            send(demandeStock);

                            // Attendre la réponse de l'AgentStock
                            MessageTemplate mt = MessageTemplate.and(
                                MessageTemplate.MatchSender(new AID("AgentStock", AID.ISLOCALNAME)),
                                MessageTemplate.MatchPerformative(ACLMessage.INFORM)
                            );

                            ACLMessage reponse = blockingReceive(mt, 2000); // attendre max 2 secondes

                            ACLMessage reponseFinale = msg.createReply();

                            if (reponse != null && reponse.getContent().equalsIgnoreCase("disponible")) {
                                // Demander la mise à jour de l'état du livre
                                ACLMessage maj = new ACLMessage(ACLMessage.INFORM);
                                maj.addReceiver(new AID("AgentStock", AID.ISLOCALNAME));
                                maj.setContent(titre + "::emprunte");
                                send(maj);

                                reponseFinale.setPerformative(ACLMessage.CONFIRM);
                                reponseFinale.setContent("Emprunt confirmé pour : " + titre);
                                
                                // Notification de succès
                                envoyerNotification("✅ Emprunt confirmé avec succès pour : " + titre);
                                
                            } else {
                                reponseFinale.setPerformative(ACLMessage.REFUSE);
                                reponseFinale.setContent("Livre non disponible : " + titre);
                                
                                // Notification d'échec
                                envoyerNotification("❌ Emprunt refusé - Livre non disponible : " + titre);
                            }
                            send(reponseFinale);
                        } else {
                            block();
                        }
                    }
                });
            }
        });
    }
}
