import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.UnaryOperator;


public class LambdaExpressions {


    public static void main(String[] args) {

        String[] foods = {"Käse", "Semmel", "Aufstrich"};

        /*
         Wir wollen das Array sortieren. Dazu können wir die Methode sort der Hilfsklasse Arrays verwenden.
         (@link https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#sort-T:A-java.util.Comparator-)

         Diese Methode erwartet zwei Parameter: das zu sortierende Array und eine Implementierung des Interfaces Comparator
         (@link https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html)

         Beachte, die Methode erwartet somit sowohl Daten (das zu sortierende Array), als auch ein "Verhalten"
         (nach welchen Regeln sortiert werden soll). Wir wir dieses "Verhalten", also Code, an eine Methode
         übergeben können, darum geht es in diesem Kapitel.

         1. Möglichkeit: Wir implementieren das Interface Comparator in einer eigenen Klasse und
         instanzieren ein Objekt davon, das wir als Parameter verwenden können.

         Nachteile: Viel "unnötiger" Code, schwierig zu verstehendes Programm....vor allem wenn der Comparator in dieser Form
         nur an dieser einen Stelle gebraucht wird.
         */
        Arrays.sort(foods, new LastCharComparator());
        System.out.println("Comparator v1: " + Arrays.toString(foods));

        /*
         2. Möglichkeit: Eine anonyme Klasse - eine Klasse ohne Namen, weil direkt nur ein einziges Objekt erzeugt wird
         */
        Arrays.sort(foods, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.substring(s1.length() - 1).compareTo(s2.substring(s2.length() - 1));
            }
        });
        System.out.println("Comparator v2: " + Arrays.toString(foods));

        /*
          Java bietet seit der Version 8 dafür aber eine noch kompaktere einfachere Schreibweise: Lambda Ausdrücke!

          3. Möglichkeit: Wir schreiben die vorherige Version kompakt als Lambda-Ausdruck.
         */
        Arrays.sort(foods, (String s1, String s2) -> {
            return s1.substring(s1.length() - 1).compareTo(s2.substring(s2.length() - 1));
        });
        System.out.println("Comparator v3: " + Arrays.toString(foods));

        /*
            Die 2. (anonyome Klasse) und 3. Möglichkeit (Lambda Ausdruck) machen genau dasselbe.
            Sie unterscheiden sich lediglich in der Schreibweise.

            Alle Lambda-Ausdrücke lassen sich in einer Syntax formulieren, die die folgende allgemeine Form hat:
            ( LambdaParameter ) -> { Anweisungen }

            Alle Informationen, die im Lambda-Ausdruck nicht explizit angegeben sind, kann der Compiler implizit ableiten.
            Der Compiler erkennt, dass der zweite Parameter von Arrays.sort() eine Implementierung des Interface Comparator sein muss.
            Das Interface Comparator besitzt nur eine einzige Methode, daher ist auch diese Auswahl eindeutig.

            MERKE: Interfaces, die nur eine zu implementierende Methode besitzen, heißen funktionale Interfaces.
            Nur funktionale Interfaces erlauben eine verkürzte Schreibweise über einen Lambda-Ausdruck.
         */

        /*
            Da der Compiler den Datentyp der einzelnen Parameter kennt, können wir diese ebenfalls einfach weglassen.
         */
        Arrays.sort(foods, (s1, s2) -> {
            return s1.substring(s1.length() - 1).compareTo(s2.substring(s2.length() - 1));
        });


        /*
            Besteht der Rumpf eines Lambda-Ausdrucks nur aus einem einzelnen Ausdruck, kann eine verkürzte Schreibweise
            die Blockklammern und das Semikolon einsparen. Statt

            (LambdaParameter ) -> { return Ausdruck; }

            heißt es dann

            ( LambdaParameter ) -> Ausdruck
         */
        Arrays.sort(foods, (s1, s2) -> s1.substring(s1.length() - 1).compareTo(s2.substring(s2.length() - 1)));

        /*
            Beachte dabei, dass auch Methoden mit einem Rückgabetyp void verkürzt geschrieben werden können.
         */
        Arrays.asList(foods).forEach((e) -> System.out.println(e));

        /*
            Gibt es nur einen einzigen Parameter (und dessen Typ ist klar) können auch die runden Klammern der
            Parameterliste noch weggelassen werden.
         */
        Arrays.asList(foods).forEach(e -> System.out.println(e));

        /*
          Beachte aber bitte allgemein: Kurze Ausdrücke sind nicht unbedingt die verständlichsten.
          Eine explizite Schreibweise hilft auch der Lesbarkeit!
         */

        /*
           Im letzten Beispiel erwartet forEach eine Methode mit einem Parameter und dem Rückgabetyp void.
           Die Methode System.out.println passt z.B. ebenfalls genau in dieses Schema.

           Für genau solche Fälle gibt es eine weitere syntaktische Verkürzung, sodass im Code kein Lambda-Ausdruck,
           sondern nur noch eine Methodenreferenz notwendig ist.

           Eine Methodenreferenz ist ein Verweis auf eine Methode, ohne diese jedoch aufzurufen.
           Syntaktisch trennen zwei Doppelpunkte den Klassennamen oder die Referenz auf der linken Seite
           von dem Methodennamen auf der rechten.
         */
        Arrays.asList(foods).forEach(System.out::println);

        /*
            Zur Wiederholung: Interfaces, die nur eine zu implementierende Methode besitzen, heißen funktionale Interfaces.
            Nur funktionale Interfaces erlauben eine verkürzte Schreibweise über einen Lambda-Ausdruck.

            Schreiben wir doch selbst einmal ein solches funktionales Interface. Wechsle zu @see StringCombinator
            und schaue dir dieses Interface an.
         */

        /*
            Das Interface StringCombinator kann man nun für einen Lambda-Ausdruck verwenden.
         */
        StringCombinator firstHalves = (s1, s2) -> s1.substring(0, s1.length() / 2) + s2.substring(0, s2.length() / 2);
        System.out.println("Beispiel functional interface: " + firstHalves.combine("Katze", "Hund"));

         /*
            Java stellt uns im package java.util.function etliche functional Interfaces für
            allgemeine Anwendungsfälle zu Verfügung.

            Beispiel:
            Interface Predicate (@link https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html)

            Das Interface definiert die abstrakte Methode test, die einen Parameter besitzt und einen boolean zurückliefert.
            Damit kann ein Objekt "getestet" werden und je nach Ergebnis true oder false zurückliefern.

            Diese Interfaces besitzen generisch deklarierte Typen. Wir kennen Generics von Collections.
            Collections sind allgemein programmiert und können jeden beliebigen Datentyp speichern.
            Bei der Erstellung geben wir aber bekannt, welchen Datentyp wir in der konkreten Collection speichern wollen.

            Wenn du dir die Dokumentation des Interface "Predicate" ansiehst, hat der Parameter der Methode test den generischen
            Typ T. Der konkrete Datentyp wird erst bei der Verwendung festgelegt. Überall wo in der Dokumentation T steht,
            kannst du dir den gleichen konkreten Datentyp denken (z.B. String, Integer, Person,...)
        */

        /*
            Schauen wir ein Beispiel für die Verwendung des Interface "Predicate" an.

            Die Methode

            removeIf   (@link https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#removeIf-java.util.function.Predicate-)

            wird von jeder Collection implementiert und verlangt als Parameter ein Predicate. Das Interface Predicate definiert ja eine Methode
            mit einem Parameter und liefert einen boolean zurück. removeIf iteriert über alle Elemente der Collection und
            jene Elemente werden entfernt, für die die Implementierung der Predicate-Methode true zurückliefert.
          */
        Collection<Integer> values = new ArrayList<>(Arrays.asList(new Integer[] {1, 4, 6, 8}));
        values.removeIf(e -> e < 5);
        System.out.println("Ergebnis nach removeIf: "+values);


        /*
            Weitere Beispiele für functional Interfaces im package java.util.function:

           - BiFunction: Berechnet einen Wert aus zwei Argumenten. Die Argumente und der Returnwert können
                unterschiedliche Datentypen haben (@link https://docs.oracle.com/javase/8/docs/api/java/util/function/BiFunction.html)
           - UnaryOperator: Erzeugt aus einem Objekt ein neues Objekt vom selben Typ. (@link https://docs.oracle.com/javase/8/docs/api/java/util/function/UnaryOperator.html)
           - BinaryOperator: Erzeugt aus zwei Objekten ein neues Objekt vom selben Typ. (@link https://docs.oracle.com/javase/8/docs/api/java/util/function/BinaryOperator.html)
           - Supplier: Liefert einen Wert und hat keine Argumente. (@link https://docs.oracle.com/javase/8/docs/api/java/util/function/Supplier.html)
           - Consumer: Verarbeitet ein Argument und hat keinen Rückgabewert. (@link https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html)
          */

          /*
             Weitere spannende Methoden (die du vielleicht gut in der Übung gebrauchen kannst ;) :

            forEach für alle Collections (@see https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html#forEach-java.util.function.Consumer-)
            replaceAll für Listen (@link https://docs.oracle.com/javase/8/docs/api/java/util/List.html#replaceAll-java.util.function.UnaryOperator-)
           */

        List<String> words = new ArrayList<>(Arrays.asList(new String[] {"Adam", "und", "Eva"}));
        words.replaceAll(new UnaryOperator<String>() {
            @Override
            public String apply(String s) {
                return s.toUpperCase();
            }
        });
        //words.replaceAll(e-> e.toUpperCase());
        System.out.println(words);

    }

}
