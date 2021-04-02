import java.util.Scanner;

public class GUI {
    private int lyrTab;
    private String[] original, translate;


    public GUI(Parser parser) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Введите имя исполнителя: ");
        String person = sc.nextLine();
        System.out.print("\nВведите название композиции: ");
        String track = sc.nextLine();
        System.out.println("\n");

        parser = new Parser(person, track);
        original = parser.originals;
        translate = parser.translates;

        lyrTab = getLen(original);

        System.out.println(parser.site+":\n\n");
        for (int i=0; i<original.length; i++ ){
            int tmp = lyrTab- original[i].length();
            printLine(original[i], translate[i], tmp);
        }

    }


    private int getLen(String[] s){
        int tmp = 0;
        for (String a : s){
            tmp = a.length()>tmp?a.length():tmp;
        }
        return tmp+5;
    }

    private void printLine (String first, String second, int divide){
        System.out.println(first+ " ".repeat(divide)+second);
    }



}
