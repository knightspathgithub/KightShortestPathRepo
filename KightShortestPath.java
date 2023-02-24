import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;


/**
* The KightShortestPath program identifies the shortest path a knight can take between two points on a standard 8x8 chessboard.
*  
*/
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
			//Reading Each Line
			inputLine = inputScanner.nextLine();
			
			//For the termination of input lines, type R and press Enter
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
				
				//Converting into corresponding 1 to 8 x-axis index for the given input line X-axis values from A to H.
				int startingPointXYIndexes[] = { xAxisLetterToNumberMap.get(sourceX), yAxisIndexToReverseIndexMap.get(sourceY) };
				
				//Converting into corresponding 1 to 8 y-axis index for the given input line Y-axis values
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
	
	/** 
	 * This method checks whether the given xIndex and yIndex are inside the board or outside the board.
	 * 
	 * @param  xIndex  an absolute URL giving the base location of the image
	 * @param  yIndex the location of the image, relative to the url argument
	 * @param  sizeOfBoard is the size of the Chess Board.
	 * @return Returns true if the xIndex and yIndex are inside the chess board else Returns false
	 */

	static boolean isNextMoveInsideBoard(int xIndex, int yIndex, int sizeOfBoard) {
		if (xIndex >= 1 && xIndex <= sizeOfBoard && yIndex >= 1 && yIndex <= sizeOfBoard)
			return true;
		return false;
	}
	

	/** 
	 * This method finds the shortest path for the given starting point position and target point position.
	 * 
	 * @param  startingPointXYIndexes  starting point position, for example {D,4}
	 * @param  targetPointXYIndexes target point position, for example {G,7}
	 * @param  sizeOfBoard is the size of the Chess Board.
	 * @return Returns true if the xIndex and yIndex are inside the chess board else Returns false
	 */
	static String shortestPathToTarget(int startingPointXYIndexes[], int targetPointXYIndexes[], int sizeOfBoard) {
		//Possible x-axis and y-axis values from any given place for the night. I.e L-shape movements: 2 squares along one dimension, 1 square along the other.
		int possibleXmovableIndexes[] = { 2, 1, -2, -1, 2, 1, -2, -1 };
		int possibleYmovableIndexes[] = { 1, 2, 1, 2, -1, -2, -1, -2 };
		
		Vector<Square> movedPlaces = new Vector<>();

		movedPlaces.add(new Square(startingPointXYIndexes[0], startingPointXYIndexes[1], xAxisIndexToLettersMap.get(startingPointXYIndexes[0]) + yAxisIndexToReverseIndexMap.get(startingPointXYIndexes[1]) + " "));

		Square movedToSquare;
		int nextXindex, nextYindex;
		
		
		//Maintaining the 2 dimensional array for the chessboard and assigning initial visited values as false for all the chess board squares.
		boolean visit[][] = new boolean[sizeOfBoard + 1][sizeOfBoard + 1];
		for (int xIndex = 1; xIndex <= sizeOfBoard; xIndex++) {
			for (int yIndex = 1; yIndex <= sizeOfBoard; yIndex++) {
				visit[xIndex][yIndex] = false;
			}
		}
			
		//Making the starting square as visited.
		visit[startingPointXYIndexes[0]][startingPointXYIndexes[1]] = true;
		
		while (!movedPlaces.isEmpty()) {
			movedToSquare = movedPlaces.firstElement();
			movedPlaces.remove(0);
			
			//Returning the path when the target is reached.
			if (movedToSquare.xIndex == targetPointXYIndexes[0] && movedToSquare.yIndex == targetPointXYIndexes[1]) {
				return movedToSquare.path;
			}

			//If the target is not reached, moving to the next possible Knight's square (i.e in L- Shape)
			for (int squareIndex = 0; squareIndex < 8; squareIndex++) {
				
				nextXindex = movedToSquare.xIndex + possibleXmovableIndexes[squareIndex];
				nextYindex = movedToSquare.yIndex + possibleYmovableIndexes[squareIndex];
				//move to the next possible square only if it is inside the board and the square is not already visited.
				if (isNextMoveInsideBoard(nextXindex, nextYindex, sizeOfBoard) && !visit[nextXindex][nextYindex]) {
					visit[nextXindex][nextYindex] = true;
					//Moving to the next possible square
					movedPlaces.add(new Square(nextXindex, nextYindex, movedToSquare.path + xAxisIndexToLettersMap.get(nextXindex) + yAxisIndexToReverseIndexMap.get(nextYindex) + " "));
				}
			}
		}
		return "";
	}
	
	/**
	* The Square to represent each box of the chess board with it's x-axis, y-axis values.
	*  
	*/
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
