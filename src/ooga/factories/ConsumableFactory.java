package ooga.factories;

import java.lang.reflect.InvocationTargetException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;

public class ConsumableFactory {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      ConsumableFactory.class.getPackageName() + ".resources.";
  private static final String PACKAGES_FILENAME = "Packages";
  private static final String CLASS_NAMES_FILENAME = "classNames";

  public Consumable createConsumable(String consumable, int x, int y)
      throws IllegalArgumentException{
    Consumable createdConsumable = null;
    int numNot = 0;
    ResourceBundle packages = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, PACKAGES_FILENAME));
    ResourceBundle classNames = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, CLASS_NAMES_FILENAME));
    String actualConsumable = "";
    try {
      actualConsumable = classNames.getString(consumable);
    } catch (MissingResourceException e) {
      actualConsumable = consumable;
    }

    for (String aPackage : packages.keySet()) {
      try {
        createdConsumable = (Consumable) Class.forName(
                String.format("%s%s", packages.getString(aPackage), actualConsumable)).getConstructor(int.class, int.class)
            .newInstance(x, y);
      } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
        numNot++;
      }
    }
    if (numNot == packages.keySet().size()) {
      throw new IllegalArgumentException();
    }
    return createdConsumable;
  }

}
