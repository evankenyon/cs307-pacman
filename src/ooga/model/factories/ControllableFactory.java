package ooga.model.factories;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import ooga.model.interfaces.Controllable;

public class ControllableFactory {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      ControllableFactory.class.getPackageName() + ".resources.";
  private static final String PACKAGES_FILENAME = "Packages";

  public Controllable createControllable(String controllable)
      throws IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    ResourceBundle packages = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, PACKAGES_FILENAME));
    return (Controllable) Class.forName(
            String.format("%s%s", packages.getString("controllables"), controllable)).getConstructor()
        .newInstance();
  }
}
