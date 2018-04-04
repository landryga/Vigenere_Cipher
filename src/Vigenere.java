import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Vigenere {
	
	public static String alphabet = "abcdefghijklmnoprstuvwxyz";

	public static void main(String[] args) throws IOException {
		String path;
		String plainTxt;
		String key;
		String cryptogram;
		int[] plainTxtNmbs;
		int[] keyNmbs;
		
		//path = "C:\\Users\\SourceJW\\test.txt";
		
		System.out.println("Podaj sciezke do pliku (format 'X:\\folder\\file.txt') \n");
		
		//Odczytywanie zawartosci pliku tekstowego
		Scanner reader = new Scanner(System.in);
		path = reader.nextLine();
		
		plainTxt = readFile(path);
		
		//Zmienna przechowujaca dlugosc slowa do zaszyfrowania
		int inputLng = plainTxt.length();
		
		plainTxtNmbs = new int[inputLng];
		plainTxtNmbs = getStringNmbs(plainTxt);
		
		for(int i=0; i < inputLng; i++) {
			System.out.print(plainTxtNmbs[i]+ " ");
		}
		
		//Generowanie klucza (z uzyciem klasy generujacej losowy obiekt String o zadanej dlugosci, za https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
		System.out.println("\n Podaj klucz: \n");
		key = reader.nextLine();
		
		if(key.length()>plainTxt.length()) {
			System.out.println("Klucz nie moze byc dluzszy od tekstu jawnego \n");
			return;
		}
		
		keyNmbs = new int[inputLng];
		keyNmbs = getStringNmbs(key);
		
		System.out.println("\n");
		
		System.out.println("Klucz: ");
		System.out.println(key);
		
		for(int i=0; i < keyNmbs.length; i++) {
			System.out.print(keyNmbs[i]+ " ");
		}
		System.out.println("\n");
		
		//tablica przechowujaca indeksy szyfrogramu
		int[] cryptogramIndexes = new int[inputLng];
		
		int key_index = 0;
		
		//wyznaczanie szyfrogramu
		for(int i = 0; i < inputLng; i++) {
			
			
			
			if(plainTxtNmbs[i]+keyNmbs[key_index] >= alphabet.length()) {
				cryptogramIndexes[i] = (plainTxtNmbs[i]+keyNmbs[key_index]) - (alphabet.length());
			}
			else
				cryptogramIndexes[i] = plainTxtNmbs[i]+keyNmbs[key_index];
			
			if(key_index == keyNmbs.length-1)
				key_index = 0;
			else
				key_index++;
		}
		
		cryptogram = getCryptogram(cryptogramIndexes);
		
		System.out.println("Szyfrogram:");
		System.out.println(cryptogram);
		for(int i=0; i < inputLng; i++) {
			System.out.print(cryptogramIndexes[i]+ " ");
		}
		
		System.out.println("");
		
		replaceFile(path, cryptogram);
	}
	
	static String readFile(String strpath) throws IOException 
	{
		
		Path path = Paths.get(strpath);
		
		Charset charset = StandardCharsets.UTF_8;

		String content = new String(Files.readAllBytes(path), charset);
		return content;
	}
	
	static void replaceFile(String strpath,  String text) throws IOException 
	{
		Path path = Paths.get(strpath);
		
		Charset charset = StandardCharsets.UTF_8;

		Files.write(path, text.getBytes(charset));
	}
	
	//metoda zwracajaca tablice indeksow znakow slowa
	public static int[] getStringNmbs(String txt) {
		
		int[] output;
		
		output = new int[txt.length()];
		
		for(int i = 0; i< txt.length(); i++) {
			for(int j=0; j<alphabet.length(); j++) {
				if(txt.charAt(i) == alphabet.charAt(j)) {
					output[i] = j;
				}
			}
		}
		
		return output;
		
	}
	
	//metoda zwracajaca ciag znakow na podstawie tablicy indeksow
	public static String getCryptogram(int[] indexes) {
		String output;
		char[] chars;
		chars = new char[indexes.length];
		
		for(int i = 0; i< indexes.length; i++) {
			chars[i] = alphabet.charAt(indexes[i]);
		}
		
		output = new String(chars);
		
		return output;
	}
	
}
