package ooga.data;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Grid;
import ooga.gamelogic.grid.ImmutableGrid;


/**
 * A tool to read CSV files and convert their data into a 2d integer array, as well as create CSV
 * files from a grid.
 */
public class CsvFileReader {

    /**
     * Reads a CSV file, and returns its content as a 2d array of integers.
     *
     * @param filePath the CSV file path to read.
     * @return a 2d array that contains the values of the CSV file.
     */
    public static Grid readFile(String filePath) throws IOException, InvalidParameterException {
        try {
            File currentFile = new File(filePath);
            CSVReader csvReader = new CSVReader(new FileReader(currentFile));
            int[] dimensions = toIntArray(csvReader.readNext());
            Grid myGrid = new Grid(dimensions[0], dimensions[1]);
            int[] fileRow;
            for (int r = 0; r < dimensions[0]; r++) {
                fileRow = toIntArray(csvReader.readNext());
                for (int c = 0; c < dimensions[1]; c++) {
                    myGrid.addCell(new Cell(r, c, fileRow[c]));
                }
            }
            return myGrid;
        } catch (IOException | CsvValidationException e) {
            throw new IOException("Error in finding the file to read.");
        } catch (Exception e) {
            throw new InvalidParameterException("Error in CSV file, please correct error and retry.");
        }
    }

    /**
     * Converts an array of strings to an array of integers.
     *
     * @param myArray the array to convert.
     * @return an array of integers that have the same values as those in the string array.
     */
    private static int[] toIntArray(String[] myArray) {
        int[] myIntArray = new int[myArray.length];
        for (int i = 0; i < myArray.length; i++) {
            myIntArray[i] = Integer.parseInt(myArray[i]);
        }
        return myIntArray;
    }

    /**
     * Saves current grid states to a csv.
     */
    public static void createCsv(ImmutableGrid currentGrid, String currentGame) throws IOException {
        StringBuilder csv = new StringBuilder();
        int rows = currentGrid.getNumberOfRows();
        int cols = currentGrid.getNumberOfColumns();
        Iterator<Integer> it = currentGrid.iterator();
        csv.append(String.format("%s,%s\n", rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                csv.append(String.format("%s,", it.next()));
            }
            csv.delete(csv.length() - 1, csv.length());
            csv.append("\n");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH;mm;ss");
        Date date = new Date();
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(String.format("data/saved/%s/%s_%s.csv", currentGame, formatter.format(date),
                        currentGame)));
        writer.write(csv.toString());
        writer.close();
    }


}
