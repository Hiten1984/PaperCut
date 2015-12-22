/**
 * @author Hiten Sahai Bahri
 *
 * This file is part of the P
 *
 * Copyright @author Hiten Sahai Bahri
 * All Rights Reserved
 * 
 * Create on 23/12/2015
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *  Simple Java that takes a list of A4 print jobs and calculates the cost of each job, 
 *   given the total number of pages, number of color pages and whether printing is double sided.  
 * 
 * */

public class PaperCutPrintCostCalculator {
	static int blackPage = 0;
	static double costTotal = 0;

	public static void main(String... args) {
		// Path of the file is used in the local directory.	
		List<PaperPrint> printCSV = readPrintJobsFromCSV("printjobs.csv");
		calculateJob(printCSV);	
	}

	private static void calculateJob(List<PaperPrint> pList) {		
		String printSide = null;
		int tPage = 0;
		int cPage = 0;

		for (PaperPrint paper : pList) {			
			printSide = paper.getPrintSide();		
			tPage = paper.getTotalPage();
			cPage = paper.getColorPage();
			countJobs(printSide,tPage, cPage);
		}
	}

	public static void countJobs(String printSide, int tPage, int cPage) {
		double bCost = 0;
		double cCost = 0;
		blackPage = tPage - cPage;
		
		if(printSide.contains("true")) {
			System.out.println("");
			System.out.println("--- Cost Estimate ---");			
			System.out.printf("Total number of pages - "+ tPage);
			System.out.printf("%n");
			System.out.printf("Total color pages - "+ cPage);
			System.out.printf("%n");
			System.out.printf("Total black and white pages - "+ blackPage);
			cCost = cPage * 0.20;
			System.out.printf("%n");
			System.out.printf("Total color pages cost- $  "+ cCost);
			bCost = blackPage * 0.10;
			System.out.printf("%n");
			System.out.printf("Total black pages cost- $  "+ bCost);			
			costTotal = cCost + bCost;
			System.out.printf("%n");
			System.out.printf("Total Cost: single sided - $%6.2f%n", costTotal);
		} else {
			System.out.println("");
			System.out.println("--- Cost Estimate ---");
			System.out.printf("Total number of pages - "+ tPage);
			System.out.printf("%n");
			System.out.printf("Total color pages - "+ cPage);
			System.out.printf("%n");
			System.out.printf("Total black and white pages - "+ blackPage);
			cCost = cPage * 0.25;
			System.out.printf("%n");
			System.out.printf("Total color pages cost- $  "+ cCost);
			bCost = blackPage * 0.15;
			System.out.printf("%n");
			System.out.printf("Total black pages cost- $  "+ bCost);			
			costTotal = cCost + bCost;
			System.out.printf("%n");
			System.out.printf("Total Cost: single sided - $%6.2f%n", costTotal);
		}
	}

	private static List<PaperPrint> readPrintJobsFromCSV(String fileName) {
		List<PaperPrint> print = new ArrayList<>();
		Path pathToFile = Paths.get(fileName);
		// create an instance of BufferedReader
		// using try with resource, Java 7 feature to close resources
		try (BufferedReader br = Files.newBufferedReader(pathToFile,
				StandardCharsets.US_ASCII)) {

			String line = br.readLine();

			while (line != null) {
				String[] attributes = line.split("\n");				
				PaperPrint paperPrint = createPrintJob(attributes);
				print.add(paperPrint);				
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return print;
	}

	private static PaperPrint createPrintJob(String[] printData) {
		int tPage = 0;
		int cPage = 0;		
		String pSide = null;		
		for(String s : printData) {
			String[] s2 = s.split(",");
			tPage = Integer.parseInt(s2[0]);
			cPage = Integer.parseInt(s2[1]);		  		
			pSide = s2[2];				
		}

		// create and return Paper Print object from Print Data 
		return new PaperPrint(tPage, cPage, pSide);
	}

}

class PaperPrint {
	private int totalPage;
	private int colorPage;
	private String printSide;

	public String getPrintSide() {
		return printSide;
	}

	public int getColorPage() {
		return colorPage;
	}


	public int getTotalPage() {
		return totalPage;
	}

	public PaperPrint(int totalPage, int colorPage, String printSide) {
		this.totalPage = totalPage;
		this.colorPage = colorPage;
		this.printSide = printSide;
	}


	@Override
	public String toString() {
		return "Total Page = " + totalPage + 
				", color page = " + colorPage +
				", Double Print Side(True/False) = " + printSide;
	}
}
