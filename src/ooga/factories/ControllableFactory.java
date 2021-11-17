package ooga.factories;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

public class ControllableFactory {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      ControllableFactory.class.getPackageName() + ".resources.";
  private static final String PACKAGES_FILENAME = "Packages";
  private static final String CLASS_NAMES_FILENAME = "classNames";

  public Controllable createControllable(String controllable, int x, int y)
      throws IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    ResourceBundle packages = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, PACKAGES_FILENAME));
    ResourceBundle classNames = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, CLASS_NAMES_FILENAME));
    return (Controllable) Class.forName(
            String.format("%s%s", packages.getString("controllables"), classNames.getString(controllable))).getConstructor(int.class, int.class)
        .newInstance(x, y);
  }
}
