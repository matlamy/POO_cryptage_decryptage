import java.util.Scanner;
/**
 * Le premier message codé à été utilisé par Cesar
 * pendant la guerre de la Gaule
 * Toute les lettres de l'alphabet on été chiffré par un certain nombre de lettre
 * Le nombre de lettre caché doit etre connu par l'envoyeur et le receveur
 * Si c'est 1
 * abcdefghijklmnopqrstuvwxyz sera crypté par
 * BCDEFGHIJKLMNOPQRSTUVWXYZA
 * Si c'est = 5
 * abcdefghijklmnopqrstuvwxyz sera crypté par
 * FGHIJKLMNOPQRSTUVWXYZABCDE
 */
public class Cesar {
    // nombre de lettre crypté
    private int nbShift;

    /**
     *  Constructeur qui recoit le nombre de lettre que l'on veut chiffré
     * @param nbShift
     */
    public Cesar(int nbShift) {
        setShiftValue(nbShift);
    }

    /**
     * Pour changer ce que l'on veut chiffrer
     * @param nbShift
     */
    public void setShiftValue(int nbShift) {

        if(nbShift < 0)
            nbShift = -nbShift;
        // enregistre le nombre qui va etre utilisé
        this.nbShift = nbShift % 26;
    }

    /**
     * La méthode va retourner la clé crypté
     */
    public String cryptage(String crypte) {
        // ignore si nulle
        if(crypte == null)
            return "";

        char[] digit = crypte.toLowerCase().toCharArray();

        for(int i = 0; i < digit.length; i++) {

            if(digit[i] < 'a' || digit[i] > 'z')
                continue;


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
     * Cette méthode va décrypter le message
     */
    public String decryptage(String decrypte) {
        // ignore si nulle
        if(decrypte == null)
            return "";

        char[] digit = decrypte.toUpperCase().toCharArray();

        for(int i = 0; i < digit.length; i++) {

            if(digit[i] < 'A' || digit[i] > 'Z') {
                continue;
            }

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
     *  la méthode principale pour tester Cesar
     *  @param args
     */
    public static void main(String[] args) {

        //--------------------------------------------------------
        // tests automatiques
        //--------------------------------------------------------

        // Pour tester avec toutes les possibilité
        testAllShifts();

        //--------------------------------------------------------
        // fin du test automatiques
        //--------------------------------------------------------

        // test du vrai cas
        // crée une entrée clavier pour lire ce que l'utilisateur rentre
        Scanner scan = new Scanner(System.in);

        int nbShift = 0;

        while(true) {
            System.out.print("Entrer le nombre de lettre à chiffré: ");
            String chi = scan.nextLine();

            try {
                nbShift = Integer.parseInt(chi);
                break;
            }
            catch(Exception e) {
                System.out.println("Désolé le nomnbre rentré est invalide");
            }
        }


        Cesar cesar = new Cesar(nbShift);

        System.out.print("Entrer le message à crypter: ");
        String clearMsg = scan.nextLine();

        String crypteString = cesar.cryptage(clearMsg);

        System.out.println("le cryptage est     " + crypteString);

        System.out.println("Le décryptage est " + cesar.decryptage(crypteString));
    }

    private static void testAllShifts() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        // test les 26 possibilitées
        System.out.println("             " + alphabet);
        for(int i = 0; i < 30; i++) {
            Cesar cesar = new Cesar(i);
            System.out.format("Shift: %02d -> %s\n", i, cesar.cryptage(alphabet));
        }
        System.out.println();
    }
}
