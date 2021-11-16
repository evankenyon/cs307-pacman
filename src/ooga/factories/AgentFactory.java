package ooga.factories;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import ooga.model.interfaces.Agent;

public class AgentFactory {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      AgentFactory.class.getPackageName() + ".resources.";
  private static final String PACKAGES_FILENAME = "Packages";

  public Agent createAgent(String agent, int x, int y)
      throws IllegalArgumentException {
    Agent createdAgent = null;
    int numNot = 0;
    ResourceBundle packages = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, PACKAGES_FILENAME));
    for (String aPackage : packages.keySet()) {
      try {
        createdAgent = (Agent) Class.forName(
                String.format("%s%s", packages.getString(aPackage), agent)).getConstructor(int.class, int.class)
            .newInstance(x, y);
      } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
        numNot++;
      }
    }

    if (numNot == packages.keySet().size()) {
      throw new IllegalArgumentException();
    }
    return createdAgent;
  }
}
