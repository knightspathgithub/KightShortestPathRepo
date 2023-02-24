import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;


public class KightShortestPath {
	static Map<String, Integer> xAxisLetterToNumberMap = new HashMap<String, Integer>();
	static Map<Integer, Integer> yAxisIndexToReverseIndexMap = new HashMap<Integer, Integer>();
	static Map<Integer, String> xAxisIndexToLettersMap = new HashMap<Integer, String>();
		
	static {
		xAxisLetterToNumberMap.put("A", 1);
		xAxisLetterToNumberMap.put("B", 2);
		xAxisLetterToNumberMap.put("C", 3);
		xAxisLetterToNumberMap.put("D", 4);
		xAxisLetterToNumberMap.put("E", 5);
		xAxisLetterToNumberMap.put("F", 6);
		xAxisLetterToNumberMap.put("G", 7);
		xAxisLetterToNumberMap.put("H", 8);
		
		yAxisIndexToReverseIndexMap.put(8, 1);
		yAxisIndexToReverseIndexMap.put(7, 2);
		yAxisIndexToReverseIndexMap.put(6, 3);
		yAxisIndexToReverseIndexMap.put(5, 4);
		yAxisIndexToReverseIndexMap.put(4, 5);
		yAxisIndexToReverseIndexMap.put(3, 6);
		yAxisIndexToReverseIndexMap.put(2, 7);
		yAxisIndexToReverseIndexMap.put(1, 8);
		
		xAxisIndexToLettersMap.put(1, "A");
		xAxisIndexToLettersMap.put(2, "B");
		xAxisIndexToLettersMap.put(3, "C");
		xAxisIndexToLettersMap.put(4, "D");
		xAxisIndexToLettersMap.put(5, "E");
		xAxisIndexToLettersMap.put(6, "F");
		xAxisIndexToLettersMap.put(7, "G");
		xAxisIndexToLettersMap.put(8, "H");
	}
	

	
	
	public static void main(String[] args) {
		int sizeOfBoard = 8;
		
		Scanner  inputScanner    = new Scanner(System.in);
		ArrayList<String> inpuPositions = new ArrayList<String>();
		String inputLine;
		while (inputScanner.hasNextLine() ) {
			inputLine = inputScanner.nextLine();
			
			if(inputLine.equalsIgnoreCase("R")) {
				break;
			}
			inpuPositions.add(inputLine);
			
		}
	    
	    System.out.printf("\nOutput is :\n");
	    for(String inpuPosition : inpuPositions) {
	    	try {
	    		// Get sourceX, sourceY, destX, destY from inputline
				int sourceY, destY;
				String sourceX, destX;

				sourceX = inpuPosition.charAt(0) + "";
				sourceY = Integer.parseInt(inpuPosition.charAt(1)+"");
				
				destX = inpuPosition.charAt(3) + "";
				destY = Integer.parseInt(inpuPosition.charAt(4)+"");

				int startingPointXYIndexes[] = { xAxisLetterToNumberMap.get(sourceX), yAxisIndexToReverseIndexMap.get(sourceY) };
				int targetPointXYIndexes[] = { xAxisLetterToNumberMap.get(destX), yAxisIndexToReverseIndexMap.get(destY) };

				System.out.println(shortestPathToTarget(startingPointXYIndexes, targetPointXYIndexes, sizeOfBoard));
	    		
	    	} catch(Exception e) {
	    		System.out.println("Please enter valid input lines in the below format");
	    		System.out.println("D4 G7");
	    		System.out.println("D4 D5");
	    	}
	    }

		inputScanner.close();
	}

	static boolean isNextMoveInsideBoard(int xIndex, int yIndex, int sizeOfBoard) {
		if (xIndex >= 1 && xIndex <= sizeOfBoard && yIndex >= 1 && yIndex <= sizeOfBoard)
			return true;
		return false;
	}
	

	static String shortestPathToTarget(int startingPointXYIndexes[], int targetPointXYIndexes[], int sizeOfBoard) {
		int possibleXmovableIndexes[] = { 2, 1, -2, -1, 2, 1, -2, -1 };
		int possibleYmovableIndexes[] = { 1, 2, 1, 2, -1, -2, -1, -2 };
		
		Vector<Square> movedPlaces = new Vector<>();

		movedPlaces.add(new Square(startingPointXYIndexes[0], startingPointXYIndexes[1], xAxisIndexToLettersMap.get(startingPointXYIndexes[0]) + yAxisIndexToReverseIndexMap.get(startingPointXYIndexes[1]) + " "));

		Square movedToSquare;
		int nextXindex, nextYindex;
		boolean visit[][] = new boolean[sizeOfBoard + 1][sizeOfBoard + 1];

		for (int xIndex = 1; xIndex <= sizeOfBoard; xIndex++)
			for (int yIndex = 1; yIndex <= sizeOfBoard; yIndex++)
				visit[xIndex][yIndex] = false;

		visit[startingPointXYIndexes[0]][startingPointXYIndexes[1]] = true;
		while (!movedPlaces.isEmpty()) {
			movedToSquare = movedPlaces.firstElement();
			movedPlaces.remove(0);
			
			if (movedToSquare.xIndex == targetPointXYIndexes[0] && movedToSquare.yIndex == targetPointXYIndexes[1]) {
				return movedToSquare.path;
			}

			for (int squareIndex = 0; squareIndex < 8; squareIndex++) {
				
				nextXindex = movedToSquare.xIndex + possibleXmovableIndexes[squareIndex];
				nextYindex = movedToSquare.yIndex + possibleYmovableIndexes[squareIndex];

				if (isNextMoveInsideBoard(nextXindex, nextYindex, sizeOfBoard) && !visit[nextXindex][nextYindex]) {
					visit[nextXindex][nextYindex] = true;
					movedPlaces.add(new Square(nextXindex, nextYindex, movedToSquare.path + xAxisIndexToLettersMap.get(nextXindex) + yAxisIndexToReverseIndexMap.get(nextYindex) + " "));
				}
			}
		}
		return "";
	}
	
	static class Square {
		int xIndex, yIndex;
		String path = "";

		public Square(int pXindex, int pYindex, String pPath) {
			this.xIndex = pXindex;
			this.yIndex = pYindex;
			this.path = pPath;
		}
	}

	

}
