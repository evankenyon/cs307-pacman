package ooga.model.factories;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import ooga.controller.IO.JsonParser;
import ooga.model.Agent;

public class AgentFactory {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      AgentFactory.class.getPackageName() + ".resources.";
  private static final String PACKAGES_FILENAME = "Packages";

  public Agent createAgent(String agent)
      throws IllegalArgumentException {
    Agent createdAgent = null;
    int numNot = 0;
    ResourceBundle packages = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, PACKAGES_FILENAME));
    for (String aPackage : packages.keySet()) {
      try {
        createdAgent = (Agent) Class.forName(
                String.format("%s%s", packages.getString(aPackage), agent)).getConstructor()
            .newInstance();
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
