import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;//#
import java.util.Scanner;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
//made by William Li Liu
public class Scraper {

	public static void main(String[] args) throws Exception, IOException, FileNotFoundException {
		PrintWriter writer = null;
		try {
			BufferedReader searchs = new BufferedReader(new FileReader("F:\\PDFs\\input.txt")); //made by William Li Liu
			Scanner sc = new Scanner(searchs);
			String search = sc.nextLine();
			String csvFile = "F:\\PDFs\\output.csv";
			writer = new PrintWriter(csvFile);
			writer.println("Precinct Number, Name, Votes, Percent");
			for(int i = 1; i < 14; i++) {
				for(int j = 1; j < 90; j++) {
					String names;
					if(i > 9) {
						if(j>9) {
							names = "" + i + "-" + j;
						}//#
						else {
							names = "" + i + "-0" + j;
						}
					}
					else {
						if(j>9) {
							names = "0" + i + "-" + j;
						}
						else {
							names = "0" + i + "-0" + j;
						}
					}
					Boolean flag = false;
					try {
						File x = new File("F:\\PDFs\\" + names + ".pdf");
						PDDocument document = PDDocument.load(x);
					}
					catch(FileNotFoundException fileEx) {
						flag = true;
					}
					if (flag) {
						
					}
					else {
						File x = new File("F:\\PDFs\\" + names + ".pdf");
						PDDocument document = PDDocument.load(x);
						PDFTextStripper s = new PDFTextStripper();
						String full = s.getText(document);
						int index = full.indexOf(search);
						String total= "";
						while(full.charAt(index) != '%') {
							total = total + full.substring(index, index+1);
							index++;
						}
						total = total + full.substring(index, index+1);
						int splitIndex = 0;
						while(((int)total.charAt(splitIndex))<48 || ((int)total.charAt(splitIndex))>57) {//made by William Li Liu
							splitIndex++;
						}
						String name = total.substring(0, splitIndex-1);
						String stat = total.substring(splitIndex);
						System.out.println(name);
						name = name.split(",")[0];
						System.out.println(stat);
						String[] stats = stat.split(" ");
						writer.println(" "+names +","+ name+"," +stats[0] + "," +stats[1]);
						document.close();
					}
				}
			}

		}
		finally {
			if(writer != null) {
				writer.close();
			}
		}

	}
}
//made by William Li Liu