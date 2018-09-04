import tc.TC;

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
	
	private static String liretime (Traitement t){
		int h = Integer.parseInt ("" + TC.lireChar () + TC.lireChar ());
		TC.lireChar ();
		int m = Integer.parseInt ("" + TC.lireChar ()+ TC.lireChar ());
		TC.lireChar ();
		int s = Integer.parseInt("" + TC.lireChar ()+ TC.lireChar ());
		TC.lireChar ();
		int mill = Integer.parseInt ("" + TC.lireChar ()+ TC.lireChar ()+TC.lireChar ());
		int mill2 = mill + t.milliemes;
		int s2 = (mill2 >= 0) ? s + t.secondes + mill2/1000 : s + t.secondes + mill2/1000 - 1 ;
		int m2 = (s2 >= 0) ? m + t.minutes + s2/60 : m + t.minutes + s2/60 - 1;
		int h2 = (m2>=0) ? h + m2/60 : h + m2/60 - 1;
		return toStringh (h2) + ':' + toStringh (mod (m2,60)) + ':' + toStringh (mod (s2,60)) + ',' + fmil (mod (mill2,1000));
	}
	
	
	private static String readTimeToTime(Traitement t) {
	    StringBuilder  sb = new StringBuilder ();
	    sb.append (liretime (t)).append (" --> ");
		for (int i = 0; i < 5; ++i) TC.lireChar ();
        sb.append (liretime (t)).append ("\r\n");
		return sb.toString();
	}
	
	private static void Recrirefichier(Traitement t) {
	    int offset = 0;
		TC.lectureDansFichier ("in.txt");
		TC.ecritureDansNouveauFichier ("out.srt");
		TC.lireChar ();
		StringBuilder sb = new StringBuilder ();
		StringBuilder sbBlock;
        while (! TC.finEntree ()) {
            sbBlock = new StringBuilder ();
            sbBlock.append(String.valueOf(TC.lireInt() + offset)).append("\r\n");
			TC.lireLigne ();
            sbBlock.append (readTimeToTime(t));
			TC.lireLigne ();
			String s;
			do {
			    s = TC.lireLigne ();
                sbBlock.append (s).append ("\r\n");
            }
			while (! TC.finEntree () && !s.equals (""));
            String timeBlock = sbBlock.toString().split("\r\n")[1];
            if (!timeBlock.substring(0, timeBlock.indexOf (" ")).contains("-")) sb.append (sbBlock.toString ());
			else offset -= 1;
		}
		TC.print (sb.toString ());
	}
	
	private static int mod(int a, int b) {
		if (a >= 0) return a % b;
		return b + (a % b);
	}

	public static void main(String[] args){
		Traitement t = new Traitement (0,-56,-600);
		Recrirefichier (t);
	}
}		

