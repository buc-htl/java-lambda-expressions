/**
 * Dieses Interface ist ein funktionales Interface (functional interfaces), da es nur eine einzige
 * abstrakte Methode besitzt.
 *
 * D.h. implementiert eine Klasse dieses Interface, muss nur eine einzige Methode ausprogrammiert werden.
 *
 * Die Annotation @FunctionalInterface ist optional und kann auch weggelassen werden.
 */

@FunctionalInterface
public interface StringCombinator {

    String combine (String s1, String s2);

}
