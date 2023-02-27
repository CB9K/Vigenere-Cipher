import java.util.Scanner;
import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class vigenereCypher {

	public static void main(String[] args) {
		
		boolean validInput = false;
		
		while(!validInput){
			Scanner scanner = new Scanner(System.in);
			System.out.print("Would you like to decode or encode a message? (D/E): ");
			String response = scanner.nextLine();
		
			if(response.toLowerCase().equals("d")){							//DECODE
				validInput = true;
				System.out.println("Would you like to use Brute-Force or a Key? (B/K): ");
				response = scanner.nextLine();
				if(response.toLowerCase().equals("b")){							//BRUTEFORCE
					System.out.println("Enter message to be decoded: ");
					String message = scanner.nextLine();
					String foundKey = betterFindKey(message);
					String decoded = decode(message, foundKey);
					System.out.println(decoded);
				}
				else if(response.toLowerCase().equals("k")){						//KEY
					System.out.println("Enter message to be decoded: ");
					String message = scanner.nextLine();
					System.out.println("Enter key for decryption: ");
					String key = scanner.nextLine();
					key = key.toUpperCase();
					String decoded = decode(message,key);
					System.out.println(decoded);
				}
			}
		
			else if(response.toLowerCase().equals("e")){					//ENCODE
				validInput = true;
				System.out.println("Enter message to be encoded: ");
				String message = scanner.nextLine();
				System.out.println("Enter key for encryption: ");
				String key = scanner.nextLine();
				key = key.toUpperCase();
				encode(message,key);
			}
		
			else{
				System.out.println("Please input either 'D' or 'E'");
			}
		}
	}
	
//======================================================================================================DECODE=======================================================================

	public static String decode(String m, String k){
		String message = m;
		String key = k;
		char[] messageArray = message.toCharArray();
		char[] keyArray = key.toCharArray();
		
		//=====FORMULA FOR DECODING WITH KEY===================================================
		//Integer of the letter (a=0, b=1...) Minus Integer of Key (a=0, b=1...) Gets Value
		//If Value is Negative, add 26
		//=====================================================================================
		
		int j = 0;
		
		//Set keyArray values to start at 0 (Since ASCII values for uppercase letters start at 65)
		
		for(int n = 0; n < keyArray.length; n++){
			keyArray[n] = (char)(keyArray[n] - 65);
		}
		
		//Processes Message and Key Arrays
		
			for(int i = 0; i < messageArray.length; i++){
					
				if(Character.isUpperCase(messageArray[i])){
					
					// Sets value to 0 (Since ASCII values for uppercase letters start at 65)
					
					messageArray[i] = (char)(messageArray[i] - 65);
					
					//Uses Formuala to Encrypt
					
					if(messageArray[i] - keyArray[j] < 0){
						messageArray[i] = (char)(messageArray[i] - keyArray[j] + 91);	//(+65 to return to ASCII uppercase then +26 for formula = 91)
					}
					else{
						messageArray[i] = (char)(messageArray[i] - keyArray[j] + 65);	//(+65 to return to ASCII uppercase)
					}
				}
			
				if(Character.isLowerCase(messageArray[i])){
					
					// Sets value to 0 (Since ASCII values for lowercase letters start at 97)
					
					messageArray[i] = (char)(messageArray[i] - 97);

					//Uses Formuala to Encrypt
					
					if(messageArray[i] - keyArray[j] < 0){
						messageArray[i] = (char)(messageArray[i] - keyArray[j] + 123);	//(+97 to return to ASCII uppercase then +26 for formula = 123)
					}
					else{
						messageArray[i] = (char)(messageArray[i] - keyArray[j] + 97);	//(+97 to return to ASCII uppercase)
					}
				}
				
				//Loops the Key
				
				//Only increment the key when a letter was processed (Not a space or punctuation)
				
				if(Character.isLetter(messageArray[i])){
					j++;
				}
				
				if(j == keyArray.length){
					j = 0;
				}
				
			}
			
		String decoded = String.valueOf(messageArray);
		return decoded;
		
	}

//===================================================================================================================================================================================

//======================================================================================================ENCODE=======================================================================

	public static void encode(String m, String k){
		String message = m;
		String key = k;
		char[] messageArray = message.toCharArray();
		char[] keyArray = key.toCharArray();
		
		//=========FORMULA FOR ENCRYPTION WITH KEY=========================================
		//Integer of the letter (a=0, b=1...) + Integer of the Key (a=0, b=1...) modulus 26
		//=================================================================================
		
		int j = 0;
		
		//Set keyArray values to start at 0 (Since ASCII values for uppercase letters start at 65)
		
		for(int n = 0; n < keyArray.length; n++){
			keyArray[n] = (char)(keyArray[n] - 65);
		}
		
		//Processes Message and Key Arrays
		
			for(int i = 0; i < messageArray.length; i++){
					
				if(Character.isUpperCase(messageArray[i])){
					
					// Sets value to 0 (Since ASCII values for uppercase letters start at 65)
					
					messageArray[i] = (char)(messageArray[i] - 65);
					
					//Uses Formuala to Encrypt
					
					messageArray[i] = (char)(((messageArray[i] + keyArray[j])%26)+65);	//(+65 to return to ASCII uppercase)
					
				}
			
				if(Character.isLowerCase(messageArray[i])){
					
					// Sets value to 0 (Since ASCII values for lowercase letters start at 97)
					
					messageArray[i] = (char)(messageArray[i] - 97);

					//Uses Formuala to Encrypt
					
					messageArray[i] = (char)(((messageArray[i] + keyArray[j])%26)+97);	//(+97 to return to ASCII lowercase)
					
				}
				
				//Loops the Key
				
				//Only increment the key when a letter was processed (Not a space or punctuation)
				
				if(Character.isLetter(messageArray[i])){
					j++;
				}
				
				if(j == keyArray.length){
					j = 0;
				}
				
			}
			
			String encoded = String.valueOf(messageArray);
			System.out.println(encoded);
	}
	
//===================================================================================================================================================================================

//=========================================================================BETTER FIND KEY===========================================================================================

	public static String betterFindKey(String message){
		
		int keyLength = findKeyLength(message);
		char[] messageArray = message.toCharArray();
		char[] sortedMessageArray = new char[message.length];
		
		int count = 0;
		
		for(j=0; j < keyLength; j++){
			for(i=j; i < message.length; i = i + keyLength){
				sortedMessageArray[count] = messageArray[i];	//Organizes encoded message based on which letter of the key acted on it
				count++;
			}
		}
		
		int groupsWithExtras = message.length % keyLength;		//Determines how many of the groups will have more one more letter than the rest (will always be the groups towards the front
		
		
		//Chi Squared Testing
		int upperArrayBound = Math.floor(message.length % keyLength);		//Splits the sorted array into groups based on which letter of the key acted on them
		int lowerArrayBound = 0;
		
		for(i=0; i<keyLength; i++){
			if(groupsWithExtras > 0){
				upperArrayBound++;
				groupsWithExtras--;
			}
			
			//CHI SQUARED PROCESSING GOES HERE
			
			lowerArrayBound = upperArrayBound;
			upperArrayBound = upperArrayBound + keyLength;
		}
		
	}
	
//===================================================================================================================================================================================

//===============================================================================FIND KEY LENGTH=====================================================================================

	public static int findKeyLength(String message){
		
		message = message.toUpperCase();
		message = message.replaceAll("[^A-Z]", "");
		
		char[] messageArray = message.toCharArray();
		
		int[] values = new int[26];
    	for(int i=0; i<26; i++){
    		values[i] = 0;
    	}
		
		final int maxKeyLength=20;	//Max Key Length to Check For, Change to allow for longer key lengths
		double sum=0;
		double numInGroup=0;
		double IC=0;
		double[] ICArray = new double[maxKeyLength];
		
		for(int j=1; j<=maxKeyLength; j++){
			for(int k=0;k<j;k++){
				for(int i=k; i<messageArray.length; i+=j){
					values[messageArray[i]-65] = values[messageArray[i]-65] + 1;
					
					//System.out.println(messageArray[i]);	//DEBUG
				}
				for(int i=0; i<26; i++){
					
					//System.out.println(values[i]);	//DEBUG
					
					sum += (values[i]*(values[i]-1));			//Numerator of IC formula
					numInGroup += values[i];					//Part of denominatior of IC formula
				}
				
				IC = IC + sum/(numInGroup*(numInGroup-1));
				
				//System.out.println(sum);	//DEBUG
				
				//System.out.println(numInGroup);	//DEBUG
				
				//System.out.println(IC);	//DEBUG
				
				for(int i=0; i<26; i++){							//Resets values for next iteration
					values[i] = 0;
				}
				sum = 0;
				numInGroup = 0;
			}
			ICArray[j] = IC/j;									//"j" will equal the key length "ICArray[j]" will give IC value for that key length
			
			//System.out.println(ICArray[j]);	//DEBUG
			
			IC = 0;												//Resets IC for next iteration
		}
		
		Arrays.sort(ICArray);
		
		//System.out.println(ICArray[maxKeyLength]);			//DEBUG
		
		return ICArray[maxKeyLength];							//Returns highest IC value (most likely to be the key length)
	}
	
//===================================================================================================================================================================================









//======================================================================BELOW IS OLD CODE THAT WAS TOO INEFFICIENT===================================================================

/*

//===========================================================================FIND KEY================================================================================================

//TOO TIME CONSUMING, TRY A STATISTICAL APPROACH, CHI-SQUARED TEST INSTEAD?

	public static String findKey(String message){
		
		int keyLength = findKeyLength(message);
		char[] keyArray = new char[keyLength];
		
		for(int i=0; i<keyLength; i++){
			keyArray[i] = 'A';				//initialize starting key
		}
		
		String correctKey = "";
		int bestWordCount = 0;
		
		boolean loopFinished = false;
		while(!loopFinished){
			String decoded = decode(message, String.valueOf(keyArray));
			int correctWords = countWords(decoded);
			
			//System.out.println(decoded);										//DEBUG
			
			//System.out.println(String.valueOf(keyArray));						//DEBUG
			
			if(correctWords > bestWordCount){
				
				bestWordCount = correctWords;
				correctKey = String.valueOf(keyArray);
				
			}
			
			incrementKey(keyArray, keyLength, keyLength - 1);
			if(keyArray[0] == '?')
				loopFinished = true;
		}
		
		return correctKey;
		
	}

//===================================================================================================================================================================================

//====================================================================================INCREMENT KEY==================================================================================

//PART OF TIME CONSUMING FINDKEY ALGORITH, TRY CHI-SQUARED INSTEAD

	public static void incrementKey(char [] keyArray, int keyLength, int pos){
		
		if(pos<0)
			keyArray[0] = '?';							//Ends the loop after all positions have gone through the entire alphabet
		else if(keyArray[pos] >= 90){
			keyArray[pos] = 65;
			incrementKey(keyArray, keyLength, pos-1);	//increment next position in key
		}
		else
			keyArray[pos] = (char)(keyArray[pos] + 1);
	}
	
//===================================================================================================================================================================================

//=================================================================COUNT WORDS=======================================================================================================

//PART OF TIME CONSUMING FINDKEY ALGORITHM

	public static int countWords(String decoded){
		
		decoded = decoded.toLowerCase();
		decoded = decoded.replaceAll("[^a-z\\s]", "");
		String[] words = decoded.split("[\\s']");
		
		int englishWordCount = 0;
		boolean isAWord = false;
		
		for(int i=0; i<words.length; i++){
			try{
				isAWord = isWord(words[i]);
			}
			catch(Exception e){
				System.out.println("File not found in countWords");
			}
			
			if(isAWord)
				englishWordCount++;
			
		}
		
		return englishWordCount;
		
	}
	
//===================================================================================================================================================================================

//===================================================================IS WORD=========================================================================================================

//PART OF TIME CONSUMING FINDKEY ALGORITH, NEW APPORACH INSTEAD

public static boolean isWord(String word) throws Exception {
		
		String fileName;
		
		switch(word.length()){

			case 1:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthOne.txt";
			break;
			case 2:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthTwo.txt";
			break;
			case 3:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthThree.txt";
			break;
			case 4:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthFour.txt";
			break;
			case 5:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthFive.txt";
			break;
			case 6:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthSix.txt";
			break;
			case 7:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthSeven.txt";
			break;
			case 8:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthEight.txt";
			break;
			case 9:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthNine.txt";
			break;
			case 10:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthTen.txt";
			break;
			case 11:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthEleven.txt";
			break;
			case 12:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthTwelve.txt";
			break;
			case 13:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthThirteen.txt";
			break;
			case 14:
				fileName = "C:\\Users\\natha\\Desktop\\COF Cryptology\\Vigenere Cypher\\Dictionary\\wordsLengthFourteen.txt";
			break;
			default:
				return false;
				
		}
		
		
		
		try{
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			
			while(scanner.hasNextLine()){
				if(word.equals(scanner.nextLine())){
					return true;
				}
			}
		}
		catch(Exception e){
			System.out.println("File Not Found in isWord");
		}
				
		return false;
		
	}
	
//===================================================================================================================================================================================

*/
}