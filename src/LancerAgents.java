
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.core.Runtime;

public class LancerAgents {
    public static void main(String[] args) {
        Runtime rt = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.GUI, "true");
        profile.setParameter(Profile.LOCAL_PORT, "1500");
        AgentContainer container = rt.createMainContainer(profile);

        try {
            container.createNewAgent("AgentUtilisateur", "agents.AgentUtilisateur", null).start();
            container.createNewAgent("AgentCatalogue", "agents.AgentCatalogue", null).start();
            container.createNewAgent("AgentBibliothecaire", "agents.AgentBibliothecaire", null).start();
            container.createNewAgent("AgentStock", "agents.AgentStock", null).start();
            container.createNewAgent("AgentNotification", "agents.AgentNotification", null).start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
