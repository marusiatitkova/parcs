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
        List<Integer> shifts = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int l = i * n;
            int r = (i + 1) * n;
            String textPart = text.substring(l, r);
            texts.add(textPart);
            shifts.add(l);
            if (i < N - 1) {
                int ll = r - (M - 1);
                int rr = r + M - 1;
                String text1 = text.substring(ll, rr);
                texts.add(text1);
                shifts.add(ll);
            }
        }

        List<point> points = new ArrayList<>();
        List<channel> channels = new ArrayList<>();

        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < texts.size(); i++) {
                String t = texts.get(i);
                Integer shift = shifts.get(i);

                point p = info.createPoint();
                channel c = p.createChannel();

                points.add(p);
                channels.add(c);

                Input input = new Input(t, pattern);

                p.execute("BoyerMoore");
                c.write(input);

                System.out.println("Waiting for result .. ");

                Result result = (Result)( c.readObject());
                List<Integer> ins = result.getRes();
                if (ins.size() > 0) {
                    System.out.println("Pattern ins : {");
                    for (int index : ins) {
                        System.out.print(shift + index + " ");
                    }
                    System.out.println("}");
                }
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
