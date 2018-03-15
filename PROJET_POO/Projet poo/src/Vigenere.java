import java.util.Scanner;

/**
 * Nous avons vu premierement le code permettant de crypter et decrypter par la méthode
 * de Cesar qui n'est pas très compliqué.
 * Maintenant nous allons nous intéréssé au Vigenere code un dérivé du Code de Cesar.
 *
 * L'idée global est d'utiliser comme pour le code de Cesar les lettres de l'alphabet
 *
 * Donc pour le chiffrer nous utilisons le carré de Vigenere
 *
 * A - ABCDEFGHIJKLMNOPQRSTUVWXYZ
 * B - BCDEFGHIJKLMNOPQRSTUVWXYZA
 * C - CDEFGHIJKLMNOPQRSTUVWXYZAB
 * D - DEFGHIJKLMNOPQRSTUVWXYZABC
 * E - EFGHIJKLMNOPQRSTUVWXYZABCD
 * ...
 * H - GHIJKLMNOPQRSTUVWXYZABCDED
 * ...
 * Y - YZABCDEFGHIJKLMNOPQRSTUVWX
 * Z - ZABCDEFGHIJKLMNOPQRSTUVWXY
 *
 *Pour le cryptage de Vigenere la clé est un mot qui est répété sur le message à crypter.
 * Supposons que l'on veuille cryptrer "projetpoo" avec comme clé "ARTHUR"
 *
 * ARTHURART
 * projetpoo
 *
 * Maintenant pour crypter le message on utilise:
 *
 * - l'alphabet crypter a la colonne P et la ligne A ce qui donne un P
 * - l'alphabet crypter a la colonne R et la ligne R ce qui donne un I
 * - l'alphabet crypter a la colonne O et la ligne T ce qui donne un H
 *
 * Attention si la clé est A ou AA ou encore AAA le code ne sert a rien
 */
public class Vigenere {

    // la clé utilisé par Vigenere
    private String clé;

    /**
     * contructeur qui recois la clé en parametre
     */
    public Vigenere(String clé) {
        // appel la méthode standard pour créer la clé
        setClé(clé);
    }

    /**
     * La methode qui permet de créer/changer la clé
     */
    public void setClé(String clé) {
        // empty key ignore it
        if(clé == null) {
            this.clé = "";
            return;
        }

        // Garde seulement la lettre de la clé
        char[] digit = clé.toUpperCase().toCharArray();
        // utilise un StringBuilder pour la longueur de la clé
        StringBuilder sb = new StringBuilder(digit.length);
        for(char c : digit) {
            if(c >= 'A' && c <= 'Z')
                sb.append(c);
        }

        // sauvegarde la clé
        this.clé = sb.toString();
    }

    /**
     * Crypte un message en utilisant la clé enregistré précédement
     */
    public String cryptage(String crypte) {
        // ignore si nulle
        if(crypte == null)
            return "";
        // ignore si la longueur de la clé == 0
        if(clé.length() == 0)
            return crypte.toUpperCase();

        char[] digit = crypte.toLowerCase().toCharArray();

        // Construit une chaine de caractére avec la clé répété jusqu'a la taille du message
        String longClé = clé;
        while(longClé.length() < crypte.length())
            longClé += clé;

        for(int i = 0; i < digit.length; i++) {

            if(digit[i] < 'a' || digit[i] > 'z')
                continue;


            char offset = longClé.charAt(i);

            int nbShift = offset - 'A';

            digit[i] = Character.toUpperCase(digit[i]);

            digit[i] += nbShift;

            if(digit[i] > 'Z') {
                digit[i] -= 'Z';
                digit[i] += ('A' - 1);
            }
        }
        return new String(digit);
    }

    /**
     * cette méthode permet de retourner le decryptage de la méthode vigenere
     * en accord avec la clé donnée
     */
    public String décrypte(String decrypte) {
        // ignore si nulle
        if(decrypte == null)
            return "";
        // ignore si la longueur de la clé est 0
        if(clé.length() == 0)
            return decrypte.toLowerCase();

        char[] digit = decrypte.toUpperCase().toCharArray();

        // Construit une chaine de caractére avec la clé répété jusqu'a la taille du message
        String longKey = clé;
        while(longKey.length() < decrypte.length())
            longKey += clé;


        for(int i = 0; i < digit.length; i++) {

            if(digit[i] < 'A' || digit[i] > 'Z')
                continue;


            char offset = longKey.charAt(i);

            int nbShift = offset - 'A';

            digit[i] = Character.toLowerCase(digit[i]);

            digit[i] -= nbShift;

            if(digit[i] < 'a') {
                digit[i] += 'z';
                digit[i] -= ('a' - 1);
            }

        }
        return new String(digit);
    }

    /**
     * To test our class
     */
    public static void main(String[] args) {

        //------------------------------------------------------------------
        // test
        //------------------------------------------------------------------

        // test avec l'alphabet complet
        Vigenere vigenere = new Vigenere("DreamInCode");
        // code avec l'alphabet complet
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        // crypte
        String crypte = vigenere.cryptage(alphabet);
        System.out.println("Code alphabet est : " + crypte);
        // decrypte
        String decrypte = vigenere.décrypte(crypte);
        System.out.print("  Alphabet au début: " + decrypte);

        if(alphabet.equals(decrypte))
            System.out.println("   C'est bon ! ");
        else
            System.out.println("   Oups !!! Bug ...");
        System.out.println();


        String freq = "eeeeeeeeeeeeeeeeaaaaaaaaaaaaaaaazzzzzzzzzzzzzzz";
        // utilise une autre clé
        vigenere.setClé("cléautre");

        System.out.println("Frequency test");
        System.out.println("Frequency String: " + freq);
        // crypte
        crypte = vigenere.cryptage(freq);
        // print the coded string and its translation back
        System.out.println(" as coded String: " + crypte);
        System.out.println("     end back ? : " + vigenere.cryptage(crypte));
        System.out.println();

        //-------------------------------------------------------------------
        // fin des tests auto
        //-------------------------------------------------------------------

        // test de la partie user
        Scanner scan = new Scanner(System.in);
        System.out.print("Entrer la clé: ");
        // lit la clé et cré un object Vigenere
        String clé = scan.nextLine();
        Vigenere userVigenere = new Vigenere(clé);
        // entre un message
        System.out.print("Entrer un message: ");
        String message = scan.nextLine();
        System.out.println("          Original: " + message);
        // crypte et decrypte directe
        crypte = userVigenere.cryptage(message);
        System.out.println("message crypté est: " + crypte);
        System.out.println("décrypte message est: " + userVigenere.décrypte(crypte));
    }
}
