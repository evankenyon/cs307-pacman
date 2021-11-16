package ooga.factories;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import ooga.model.interfaces.Consumable;

public class ConsumableFactory {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      ConsumableFactory.class.getPackageName() + ".resources.";
  private static final String PACKAGES_FILENAME = "Packages";
  private static final String CLASS_NAMES_FILENAME = "classNames";

  public Consumable createConsumable(String consumable, int x, int y)
      throws IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    ResourceBundle packages = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, PACKAGES_FILENAME));
    ResourceBundle classNames = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, CLASS_NAMES_FILENAME));
    return (Consumable) Class.forName(
            String.format("%s%s", packages.getString("consumables"), classNames.getString(consumable))).getConstructor(int.class, int.class)
        .newInstance(x, y);
  }
}
