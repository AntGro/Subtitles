import java.io.*;

public class Traitement {
	private int minutes;
	private int secondes;
	private int milliemes;
	
	private Traitement (int m, int s, int mi){
		minutes = m;
		secondes = s;
		milliemes = mi;
	}
	
	private static String toStringh (int h) {
		if (h < 10) {
			return ("0" + String.valueOf (h));
		}
		return String.valueOf (h);
	}
	
	private static String fmil (int mill) {
		if (mill < 100) {
			if (mill < 10) return "00" + String.valueOf (mill);
			return "0"+String.valueOf (mill);
		}
		return String.valueOf (mill);
	}
	
	private static String readTime(Traitement t, BufferedReader in) throws IOException {
	    int h = Integer.parseInt ("" + (char) in.read () + (char) in.read ());
	    in.read ();
	    int m = Integer.parseInt ("" + (char) in.read () + (char) in.read ());
	    in.read ();
	    int s = Integer.parseInt("" + (char) in.read () + (char) in.read ());
	    in.read ();
	    int mill = Integer.parseInt ("" + (char) in.read () + (char) in.read () + (char) in.read ());
	    int mill2 = mill + t.milliemes;
	    int s2 = (mill2 >= 0) ? s + t.secondes + mill2/1000 : s + t.secondes + mill2/1000 - 1 ;
	    int m2 = (s2 >= 0) ? m + t.minutes + s2/60 : m + t.minutes + s2/60 - 1;
	    int h2 = (m2>=0) ? h + m2/60 : h + m2/60 - 1;
	    return toStringh (h2) + ':' + toStringh (mod (m2,60)) + ':' + toStringh (mod (s2,60)) + ',' + fmil (mod (mill2,1000));
	}

	private static String readTimeToTime(Traitement t, BufferedReader in) throws IOException {
	    StringBuilder  sb = new StringBuilder ();
	    sb.append (readTime(t, in)).append (" --> ");
		for (int i = 0; i < 5; ++i) in.read ();
        sb.append (readTime(t, in)).append ("\r\n");
		return sb.toString();
	}
	
	private static void Recrirefichier(Traitement t) throws IOException {
	    int offset = 0;
        try (BufferedReader in = new BufferedReader(new FileReader("in.txt"))) {
            StringBuilder sb = new StringBuilder ();
            StringBuilder sbBlock;
            String nbBlock;
            in.read();
            while ((nbBlock = in.readLine()) != null) {
                sbBlock = new StringBuilder ();
                sbBlock.append(String.valueOf(Integer.valueOf(nbBlock) + offset)).append("\r\n");
                sbBlock.append (readTimeToTime(t, in));
                in.readLine();
                String s;
                do {
                    s = in.readLine();
                    sbBlock.append (s).append ("\r\n");
                }
                while (s != null && !s.equals (""));
                String timeBlock = sbBlock.toString().split("\r\n")[1];
                if (!timeBlock.substring(0, timeBlock.indexOf (" ")).contains("-")) sb.append (sbBlock.toString ());
                else offset -= 1;
            }
            PrintWriter writer;
            try {
                writer = new PrintWriter("out.srt", "UTF-8");
                writer.print(sb.toString());
                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
	}
	
	private static int mod(int a, int b) {
		if (a >= 0) return a % b;
		return b + (a % b);
	}

	public static void main(String[] args) throws IOException {
		Traitement t = new Traitement (-1,-10,-950);
		Recrirefichier (t);
	}
}		

