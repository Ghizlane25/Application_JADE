package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.*;
import java.util.*;

public class AgentStock extends Agent {
    protected void setup() {
        System.out.println(getLocalName() + " démarré.");

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    String contenu = msg.getContent().toLowerCase();
                    ACLMessage reply = msg.createReply();

                    // Requête pour vérifier disponibilité
                    if (msg.getPerformative() == ACLMessage.REQUEST) {
                        boolean disponible = false;
                        try (BufferedReader reader = new BufferedReader(new FileReader("data/livres.txt"))) {
                            String ligne;
                            while ((ligne = reader.readLine()) != null) {
                                if (ligne.toLowerCase().contains(contenu) && ligne.endsWith("disponible")) {
                                    disponible = true;
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent(disponible ? "disponible" : "non disponible");
                        send(reply);
                    }

                    // Demande de mise à jour d'état
                    else if (msg.getPerformative() == ACLMessage.INFORM) {
                        String[] parts = contenu.split("::");
                        if (parts.length == 2) {
                            String titre = parts[0];
                            String nouvelEtat = parts[1];

                            List<String> lignes = new ArrayList<>();
                            File fichier = new File("data/livres.txt");

                            try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
                                String ligne;
                                while ((ligne = reader.readLine()) != null) {
                                    if (ligne.toLowerCase().contains(titre) &&
                                        (ligne.endsWith("disponible") || ligne.endsWith("emprunte"))) {
                                        // remplacer l’état final par le nouveau
                                        ligne = ligne.replaceAll("(disponible|emprunte)", nouvelEtat);
                                    }
                                    lignes.add(ligne);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
                                for (String l : lignes) {
                                    writer.write(l + "\n");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    block();
                }
            }
        });
    }
    
}

    

