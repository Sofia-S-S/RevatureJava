package eg1;

public class TitleCase {

	public static void main(String[] args) {
		
//		String s="hello hi good evening how are you doing today";
//		StringBuilder sb=new StringBuilder();
//		String ar[]=s.split(" ");
//		for (int i = 0; i < ar.length; i++) {
//			sb.append(Character.toUpperCase(ar[i].charAt(0))).append(ar[i].substring(1)).append(" ");
//		}
//		System.out.println(sb.toString().trim());
		
		//Task - Convert every word's last character to uppercase and print back the sentence.
		
		String s="hello hi good evening how are you doing today";
		StringBuilder sb=new StringBuilder();
		String ar[]=s.split(" ");
		for (int i = 0; i < ar.length; i++) {
			sb.append(ar[i].substring(0,ar[i].length()-1)).append(Character.toUpperCase(ar[i].charAt(ar[i].length()-1))).append(" ");
		}
		System.out.println(sb.toString().trim());

	}

}

