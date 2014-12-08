// Datei EM.java
/* ------------------------------------------------------------------------
Die Klasse EM (wie "Eingabe-Modul") enthaelt 11 Methoden, mit denen man
Werte verschiedener Typen von der Standardeingabe einlesen kann.
---------------------------------------------------------------------------
Beispiel:

import static de.tfh_berlin.fb6.EM.*;
import java.math.BigInteger;  // Falls man Werte dieses Typs einlesen will
import java.math.BigDecimal;  // Falls man Werte dieses Typs einlesen will

class BeispielKlasse {
   static public void BeispielMethode {
      int        otto = liesInt();
      BigInteger emil = liesBigInteger();
      ...                  // Die Werte in otto und emil werden verarbeitet
      otto = liesInt();
      emil = liesBigInteger();
      ...                  // Die Werte in otto und emil werden verarbeitet
   }
   ...
}

Weitere Beispiele findet man im Programm EMTest.java.
---------------------------------------------------------------------------
Die Namen der 11 lies-Methoden:

liesChar    liesFloat     liesBoolen   liesString
liesByte    liesDouble                 liesBigInteger
liesShort                              liesBigDecimal
liesInt
liesLong
---------------------------------------------------------------------------
Eingaben des Benutzers:

Der Benutzer sollte Zahlen wie Java-Literale eingeben, z.B. so:
0 oder 123 oder -123 oder +123 oder 1000000000 (nicht 1000,000,000) oder
12.345 (nicht 12,345) oder 1.5e3 oder -1.5e3 oder 1.5e-3 oder -1.5e-3.

Anstelle einer Ganzzahl des Typs short, int oder long darf der Benutzer auch
einen der folgenden Namen fuer einen "besonderen Wert des Typs" eingeben:
min, max, -min, -max.
Aufgrund eines internen Ueberlaufs bezeichnen min und -min denselben
(negativen) Wert.

Anstelle einer Bruchzahl des Typs float oder double darf der Benutzer auch
einen der folgenden Namen fuer einen "besonderen Wert des Typs" eingeben:
min, -min, minn, -minn, max, -max, nan, -nan, nann, -nann, inf, infinity,
-inf und -infinity. Dabei bezeichnet:

min   den kleinsten       normalisierten Wert groesser als 0.0
minn  den kleinsten nicht normalisierten Wert groesser als 0.0
nan   eine bestimmte Unzahl (engl. not a number)
nann  eine andere    Unzahl
inf   (oder infinity) den Gleitpunktwert Unendlich, der alle "zu grossen
      Zahlen" repraesentiert.

Wahrheitswerte (engl. boolean values) darf der Benutzer beliebig GROSS
oder klein schreiben, z.B. true, TRUE, True, false, FALSE etc.

Wenn der Benutzer einen String eingibt, darf er
beliebige Zeichen (auch transparente Zeichen, engl. white space) und
beliebig  viele Zeichen (auch 0 Zeichen sind erlaubt)
eingeben, bevor er seine Eingabe mit der Return-Taste abschliesst.

Jeder String muss mit der Return-Taste abgeschlossen werden. Fuer
alle anderen Eingaben gilt dagegen: Der Benutzer darf einen oder mehrere
Werte eingeben, ehe er auf die Return-Taste drueckt.
Wenn er mehrere Werte eingibt, muss er sie durch transparente Zeichen
(engl. white space), z.B. Blank-Zeichen oder Tab-Zeichen, voneinander
trennen. Blank-Zeichen gibt man mit der besonders grossen Taste zwischen
der <Alt>- und der <Alt Gr>-Taste ein.

Die hier skizzierten Regeln kann man mit Hilfe des Programms EMTst.java
praktisch ausprobieren und genauer erforschen.
---------------------------------------------------------------------------
Ausnahmen:

Tritt waehrend der Ausfuehrung einer lies-Methode eine Ausnahme auf, so
wird sie sofort behandelt indem
1. eine Fehlermeldung ausgegeben wird und
2. statt eines eingelesenen Wertes ein "Standard-Wert-fuer-den-Fehlerfall"
   als Ergebnis geliefert wird. Je nach Typ ist das einer der Werte
   0 bzw. 0.0 bzw. '?' bzw. "" bzw. false.
---------------------------------------------------------------------------
Nur Klassenmethoden:

Die Klasse EM enthaelt nur Klassenmethoden (engl. static methods), keine
Objektmethoden. Deshalb enthaelt sie keinen oeffentlichen Konstruktor und
wird hier auch als Modul bezeichnet (da ihr "Bauplanaspekt leer ist").
---------------------------------------------------------------------------
Ergebnistypen:

Die Methode liesShort liefert einen Wert des Typs short. Entsprechendes
gilt auch fuer die meisten anderen lies-Methoden. Von dieser Regel
gibt es 2 Ausnahmen:

1. Die Methode liesByte
liest ein Byte    ein und liefert es als int-Wert zwischen 0 und   255.
2. Die Methode liesChar
liest ein Zeichen ein und liefert es als int-Wert zwischen 0 und 65535.
---------------------------------------------------------------------------
Aenderungen:

Aenderung 04: Fuer float und double die Werte fuer min und minn vertauscht.
   min ist jetzt nicht-normalisiert und minn ist normalisiert (wie in den
   Programmen GleitBitsFloatApplet und GleitBitsDoubleApplet)

Aenderung 03: Die Kommentare wurden ueberarbeitet.

Aenderung 02: Diese Klasse EM gehoerte frueher zum namenlosen Paket, wurde
   jetzt aber einem Paket mit Namen (de.tfh_berlin.fb6) zugeordnet.
   Dadurch ist es (ab Java 5.0) Benutzern der Klasse moeglich, ihre
   Klassenmethoden (static methods) zu importieren, etwa so:

   import static de.tfh_berlin.fb6.EM.*;

   und dann statt z.B. EM.liesInt(); nur noch liesInt(); zu schreiben.

Aenderung 01: In den Methoden liesShort, liesInt und liesLong werden jetzt
   die Methoden namens decode anstelle von parseShort, parseInt bzw.
   parseLong verwendet. Die parse-Methoden koennen nur dezimale Zahlen
   umwandeln, decode "versteht" auch Hexadezimalzahlen wie "0xFF" und
   "#FF" und Oktalzahlen wie "0377".
------------------------------------------------------------------------ */
//package de.tfh_berlin.fb6;

import java.io  .InputStreamReader;
import java.io  .BufferedReader;
import java.io  .IOException;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;

public class EM {
   // ---------------------------------------------------------------------
   static private InputStreamReader isr  = new InputStreamReader(System.in);
   static private BufferedReader    bufr = new BufferedReader(isr);
   // ---------------------------------------------------------------------
   static boolean istTrenner(int z) {
      // Liefert true wenn z ein transparentes Zeichen
      // (Blank, Tab, CR oder LF) ist.
      // In Englischen werden transparente Zeichen auch als
      // white space characters bezeichnet. Hier dienen sie zum Trennen
      // einzelner Eingaben und werden deshalb auch Trenner genannt.
      return (z == ' '  || z == '\t' ||z == '\r' || z == '\n');
   } // istTrenner
   // ---------------------------------------------------------------------
   static private String liesBisTrenner() throws IOException {
      // Liest aus dem Eingebstrom bufr alle transparenten Zeichen und
      // wirft sie weg. Liefert null, wenn dabei das Ende des Stroms
      // erreicht wird.
      // Sonst, nach dem Einlesen eines nicht-transparenten Zeichens Z,
      // wird Z zusammen mit allen danach stehenden nicht-transparenten
      // Zeichen (bis vor das naechste transparente Zeichen oder bis das
      // Ende des Stromes bufr erreicht wird) zu einem String zusammen-
      // gefasst und als Ergebnis geliefert.

      StringBuilder sb = new StringBuilder();
      int zeichen;

      // Alle transparenten Zeichen ueberlesen. Wenn dabei das Ende des
      // Eingabestroms bufr erreicht mit Ergebnis null beenden:
      while (true) {
         zeichen = bufr.read();

         if (zeichen == -1)       return null; // Ende von bufr erreicht
         if (istTrenner(zeichen)) continue;
         break;
      } // while (true)

      // Jetzt steht in zeichen ein nicht-tranparentes Zeichen. Dieses
      // Zeichen und weitere nicht-transparente Zeichen einlesen und in
      // sb sammeln. Wenn ein transparentes Zeichen gelesen oder das
      // Ende des Eingabestroms bufr erreicht wird, den Inhalt von sb
      // als String-Ergebnis liefern:
      while (true) {
         // Ein Zeichen sammeln:
         sb.append( (char) zeichen);
         zeichen = bufr.read();
         // Wenn ein tranparentes Zeichen gelesen ode das Ende des
         // Eingabestroms bufr erreicht wurde:
         if (istTrenner(zeichen) || zeichen == -1) {
            return sb.toString();
         }
      } // while (true)
   } // liesBisTrenner
   // ---------------------------------------------------------------------
   static public String liesString() {
      // Liest alle transparente Zeichen, die sofort im Eingabestrom bufr
      // zur Verfuegung stehen (und vermutlich noch von der vorigen Eingabe
      // des Benutzers uebrig geblieben sind und nicht zum jetzt einzu-
      // lesenden String gehoeren) und wirft sie weg.
      // Liest dann alle Zeichen (auch transparente) bis zum naechsten
      // Zeilenende und liefert sie als String-Ergebnis.
      // Falls eine Ausnahme des Typs IOException auftritt (was sehr un-
      // wahrscheinlich ist), wird ein leerer String als Ergebnis geliefert.

      try {
         // Solange im Eingabestrom bufr noch transparente Zeichen sofort
         // bereitliegen (z.B. weil der Benutzer sie schon bei frueheren
         // Eingaben eingegeben hat) werden sie ueberlesen.
         // Damit wird aufgehoert wenn keine Zeichen mehr bereitliegen
         // oder wenn ein nicht-transparentes Zeichen gelesen wird.
         // Das nicht-transparente Zeichen wird in den Eingabestrom
         // bufr zurueckgeschrieben (und wird damit zum ersten Zeichen
         // des Ergebnis-Strings, der anschliessend gelesen wird):
         while (bufr.ready()) {
            bufr.mark(1);
            int zeichen = bufr.read();
            if (zeichen == -1) return null;
            if (!istTrenner(zeichen)) {
               bufr.reset();
               break;
            }
         } // while
         return bufr.readLine();
      }  catch (IOException ausnahme01) {
         pln("Fehler beim Einlesen eines Strings!");
         pln("EM.liesString() liefert leeren String!");
         return new String();
      } // try/catch
   } // liesString
   // ---------------------------------------------------------------------
   static public int liesByte() {
      // Liest alle Zeichen aus dem Eingabestrom bufr die sofort bereit-
      // liegen und wirft sie weg. Liest dann ein Byte aus der Standard-
      // eingabe (d.h. aus dem InputStream System.in, mit dem bufr ver-
      // bunden ist) und liefert es als int-Wert zwischen 0 und 255.
      // Wenn das Ende der Standardeingabe erreicht ist, wird -1 als
      // Ergebnis geliefert. Wenn beim Lesen eine Ausnahme eintritt,
      // wird 0 als Ergebnis geliefert.
      try {
         while (bufr.ready()) bufr.read();
         return System.in.read();
      } catch (Throwable ausnahme01) {
         pln("Fehler beim Einlesen eines byte-Wertes!");
         pln("EM.liesByte() liefert den int-Wert 0!");
         return 0;
      } // try/catch
   } // liesByte
   // ---------------------------------------------------------------------
   static public int liesChar() {
      // Liest solange Zeichen aus dem Eingabestrom bufr, bis eines
      // kommt welches kein Zeilenwechselzeichen ('\n' oder '\r') ist.
      // Liefert dieses Zeichen als Ergebnis vom Typ int.
      // Falls kein Zeichen gelesen werden kann, weil das Ende der
      // Standardeingabe erreicht wurde, wird -1 als Ergebnis geliefert.
      // Falls beim Lesen eine Ausnahme eintritt wird das Zeichen '?' als
      // Ergebnis geliefert.

      try {
         while (true) {
            int e = bufr.read();
            if (e != '\n' && e != '\r') return e;
         }
      } catch (Throwable ausnahme01) {
         pln("Fehler beim Einlesen eines Zeichens!");
         pln("EM.liesChar() liefert den int-Wert '?'!");
         return '?';
      } // try/catch
   } // liesChar
   // ---------------------------------------------------------------------
   static Map<String, Short> mapS = new HashMap<String, Short>();

   static {
      mapS.put( "min", new Short(          Short.MIN_VALUE));
      mapS.put( "max", new Short(          Short.MAX_VALUE));
      mapS.put("-min", new Short((short) - Short.MIN_VALUE));
      mapS.put("-max", new Short((short) - Short.MAX_VALUE));
   }

   static public short liesShort() {
      // Liest einen short-Wert von der Standardeingabe und liefert ihn als
      // Ergebnis. Falls eine Ausnahme des Typs IOException oder des Typs
      // NumberFormatException auftritt, wird 0 als Ergebnis geliefert.
      try {
         String eingabe  = liesBisTrenner().toLowerCase();
         Short  shortObj = (Short) mapS.get(eingabe);
         if (shortObj != null) return shortObj.shortValue();
         return Short.decode(eingabe).shortValue();
      } catch (Throwable ausnahme01) {
         pln("Fehler beim Einlesen eines short-Wertes!");
         pln("EM.liesShort() liefert den short-Wert 0!");
         return 0;
      } // try/catch
   } // liesShort
   // ---------------------------------------------------------------------
   static Map<String, Integer> mapI = new HashMap<String, Integer>();

   static {
      mapI.put( "min", new Integer(  Integer.MIN_VALUE));
      mapI.put( "max", new Integer(  Integer.MAX_VALUE));
      mapI.put("-min", new Integer(- Integer.MIN_VALUE));
      mapI.put("-max", new Integer(- Integer.MAX_VALUE));
   }

   static public int liesInt() {
      // Liest einen int-Wert von der Standardeingabe und liefert ihn als
      // Ergebnis. Falls eine Ausnahme des Typs IOException oder des Typs
      // NumberFormatException auftritt, wird 0 als Ergebnis geliefert.
      try {
         String eingabe = liesBisTrenner().toLowerCase();
         Integer intObj = (Integer) mapI.get(eingabe);
         if (intObj != null) return intObj.intValue();
//       return Integer.parseInt(eingabe);
         return Integer.decode(eingabe).intValue();
      } catch (Throwable ausnahme01) {
         pln("Fehler beim Einlesen eines int-Wertes!");
         pln("EM.liesInt() liefert den int-Wert 0!");
         return 0;
      } // try/catch
   } // liesInt
   // ---------------------------------------------------------------------
   static Map<String, Long> mapL = new HashMap<String, Long>();

   static {
      mapL.put( "min", new Long(  Long.MIN_VALUE));
      mapL.put( "max", new Long(  Long.MAX_VALUE));
      mapL.put("-min", new Long(- Long.MIN_VALUE));
      mapL.put("-max", new Long(- Long.MAX_VALUE));
   }

   static public long liesLong() {
      // Liest einen long-Wert von der Standardeingabe und liefert ihn als
      // Ergebnis. Falls eine Ausnahme des Typs IOException oder des Typs
      // NumberFormatException auftritt, wird 0 als Ergebnis geliefert.
      try {
         String eingabe = liesBisTrenner().toLowerCase();
         Long   longObj = (Long) mapL.get(eingabe);
         if (longObj != null) return longObj.longValue();
//       return Long.parseLong(eingabe);
         return Long.decode(eingabe).longValue();
      } catch (Throwable ausnahme01) {
         pln("Fehler beim Einlesen eines long-Wertes!");
         pln("EM.liesLong() liefert den long-Wert 0L!");
         return 0L;
      } // try/catch
   } // liesLong
   // ---------------------------------------------------------------------
   static Map<String, Float> mapF = new HashMap<String, Float>();

   static {
      // min : kleinste positive       normalisierte float-Zahl
      // minn: kleinste positive nicht-normalisierte float-Zahl
      // max:  groesse                               float-Zahl
      // nan:  not a number, eine Unzahl vom Typ float
      // nann: not a number, eine andere Unzahl vom Typ float

      mapF.put( "min",      new Float( Float.MIN_VALUE));
      mapF.put("-min",      new Float(-Float.MIN_VALUE));
      mapF.put( "minn",     new Float( Float.intBitsToFloat(0x00800000)));
      mapF.put("-minn",     new Float(-Float.intBitsToFloat(0x00800000)));
      mapF.put( "max",      new Float( Float.MAX_VALUE));
      mapF.put("-max",      new Float(-Float.MAX_VALUE));
      mapF.put( "nan",      new Float( Float.NaN));
      mapF.put("-nan",      new Float( Float.NaN));
      mapF.put( "nann",     new Float( Float.intBitsToFloat(0xFFFFFFFF)));
      mapF.put("-nann",     new Float( Float.intBitsToFloat(0xFFFFFFFF)));
      mapF.put( "inf",      new Float( Float.POSITIVE_INFINITY));
      mapF.put( "infinity", new Float( Float.POSITIVE_INFINITY));
      mapF.put("-inf",      new Float( Float.NEGATIVE_INFINITY));
      mapF.put("-infinity", new Float( Float.NEGATIVE_INFINITY));
   }

   static public float liesFloat() {
      // Liest einen float-Wert von der Standardeingabe und liefert ihn als
      // Ergebnis. Falls eine Ausnahme des Typs IOException oder des Typs
      // NumberFormatException auftritt, wird 0.0F als Ergebnis geliefert.
      try {
         String eingabe  = liesBisTrenner().toLowerCase();
         Float  floatObj = (Float) mapF.get(eingabe);
         if (floatObj != null) return floatObj.floatValue();
         return Float.parseFloat(eingabe);
      } catch (Throwable ausnahme01) {
         pln("Fehler beim Einlesen eines float-Wertes!");
         pln("EM.liesFloat() liefert den float-Wert 0.0F!");
         return 0.0F;
      } // try/catch
   } // liesFloat
   // ---------------------------------------------------------------------
   static Map<String, Double> mapD = new HashMap<String, Double>();

   static {
      // min : kleinste positive       normalisierte double-Zahl
      // minn: kleinste positive nicht-normalisierte double-Zahl
      // max:  groesste                              double-Zahl
      // nan:  not a number, eine Unzahl vom Typ double
      // nann: not a number, eine andere Unzahl vom Typ double

      mapD.put( "min",      new Double( Double.MIN_VALUE));
      mapD.put("-min",      new Double(-Double.MIN_VALUE));
      mapD.put( "minn",     new Double( Double.longBitsToDouble(0x0010000000000000L)));
      mapD.put("-minn",     new Double(-Double.longBitsToDouble(0x0010000000000000L)));
      mapD.put( "max",      new Double( Double.MAX_VALUE));
      mapD.put("-max",      new Double(-Double.MAX_VALUE));
      mapD.put( "nan",      new Double( Double.NaN));
      mapD.put("-nan",      new Double( Double.NaN));
      mapD.put( "nann",     new Double( Double.longBitsToDouble(0xFFFFFFFFFFFFFFFFL)));
      mapD.put("-nann",     new Double( Double.longBitsToDouble(0xFFFFFFFFFFFFFFFFL)));
      mapD.put( "inf",      new Double( Double.POSITIVE_INFINITY));
      mapD.put( "infinity", new Double( Double.POSITIVE_INFINITY));
      mapD.put("-inf",      new Double( Double.NEGATIVE_INFINITY));
      mapD.put("-infinity", new Double( Double.NEGATIVE_INFINITY));
   }

   static public double liesDouble() {
      // Liest einen double-Wert von der Standardeingabe und liefert ihn als
      // Ergebnis. Falls eine Ausnahme des Typs IOException oder des Typs
      // NumberFormatException auftritt, wird 0.0 als Ergebnis geliefert.
      try {
         String eingabe   = liesBisTrenner().toLowerCase();
         Double doubleObj = (Double) mapD.get(eingabe);
         if (doubleObj != null) return doubleObj.doubleValue();
         return Double.parseDouble(eingabe);
      } catch (Throwable ausnahme01) {
         pln("Fehler beim Einlesen eines double-Wertes!");
         pln("EM.liesDouble() liefert den double-Wert 0.0!");
         return 0.0;
      } // try/catch
   } // liesDouble
   // ---------------------------------------------------------------------
   static public boolean liesBoolean() {
      // Liest einen boolean-Wert von der Standardeingabe und liefert ihn
      // als Ergebnis. Falls der Benutzer nicht true eingibt, wird der
      // Wert false als Ergebnis geliefert.
      try {
         return Boolean.valueOf(liesBisTrenner()).booleanValue();
      } catch (IOException ausnahme01) {
         pln("Fehler beim Einlesen eines boolean-Wertes!");
         pln("EM.liesBoolean() liefert false!");
         return false;
      } // try/catch
   } // liesBoolean
   // ---------------------------------------------------------------------
   static public BigInteger liesBigInteger() {
      // Liest einen BigInteger-Wert von der Standardeingabe und liefert
      // ihn als Ergebnis. Falls eine Ausnahme des Typs IOException oder
      // des Typs NumberFormatException auftritt, wird der BigInteger-
      // Wert 0 geliefert
      try {
         return new BigInteger(liesBisTrenner());
      } catch (Throwable ausnahme01) {
         pln("Fehler beim Einlesen eines BigInteger-Wertes!");
         pln("EM.liesBigInteger() liefert den BigInteger-Wert 0!");
         return new BigInteger("0");
      } // try/catch
   } // BigInteger
   // ---------------------------------------------------------------------
   static public BigDecimal liesBigDecimal() {
      // Liest einen BigDecimal-Wert von der Standardeingabe und liefert
      // ihn als Ergebnis. Falls eine Ausnahme des Typs IOException oder
      // des Typs NumberFormatException auftritt, wird der BigDecimal-
      // Wert 0.0 geliefert
      try {
         return new BigDecimal(liesBisTrenner());
      } catch (Throwable ausnahme01) {
         pln("Fehler beim Einlesen eines BigDecimal-Wertes!");
         pln("EM.liesBigDecimal() liefert den BigDecimal-Wert 0.0!");
         return new BigDecimal("0.0");
      } // try/catch
   } // BigDecimal
   // ---------------------------------------------------------------------
   // Zwei Methoden mit kurzen Namen:
   static void pln(Object ob) {System.out.println(ob);}
   static void p  (Object ob) {System.out.print  (ob);}
   // ---------------------------------------------------------------------
   static public void main(String[] susi) {
      // Ein kleiner Test der Methoden in diesem Modul:
      pln("EM: Die Klasse EM ist nur ein Modul, aber kein Programm!");
      pln("    Der Eingabe-Modul EM ethaelt Funktionen (liesByte,");
      pln("    liesInt, liesString etc.), mit denen man Daten von der");
      pln("    Standardeingabe (Tastatur) einlesen kann. Mit dem");
      pln("    Programm EMTst kann man den Modul EM testen!");
   } // main
   // ---------------------------------------------------------------------
} // end class EM