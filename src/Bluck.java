import parcs.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Bluck{

    public static void main(String[] args) throws Exception {
        task curtask = new task();
        curtask.addJarFile("BoyerMoore.jar");

        List<String> slist = fromFile( curtask.findFile("input") );
        String text = slist.get(0);
        String pattern = slist.get(1);

        AMInfo info = new AMInfo(curtask, null);

        int N = 2;
        int n = text.length() / N;
        int M = pattern.length();

        List<String> texts = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int l = i * n;
            int r = (i + 1) * n;
            String textPart = text.substring(l, r);
            texts.add(textPart);
            if (i < N - 1) {
                int ll = r - (M - 1);
                int rr = r + M;
                String text1 = text.substring(ll, rr);
                texts.add(text1);
            }
        }

        List<point> points = new ArrayList<>();
        List<channel> channels = new ArrayList<>();

        for (String t : texts) {
            point p = info.createPoint();
            channel c = p.createChannel();

            points.add(p);
            channels.add(c);

            Input input = new Input(t, pattern);

            p.execute("BoyerMoore");
            c.write(input);

            System.out.println("Waiting for result .. ");

            List<Integer> ins = (ArrayList<Integer>) c.readObject();
            if (ins.size() > 0) {
                System.out.println("Pattern ins : {");
                for (int index : ins) {
                    System.out.print(index + " ");
                }
                System.out.println("}");
            }
        }

        curtask.end();
    }
    public static List<String> fromFile(String filename) throws Exception {
        List<String> s = new ArrayList<>();

        Scanner sc = new Scanner(new File(filename));

        String text = sc.nextLine();
        String pattern = sc.nextLine();

        s.add(text);
        s.add(pattern);

        return s;
    }

}
