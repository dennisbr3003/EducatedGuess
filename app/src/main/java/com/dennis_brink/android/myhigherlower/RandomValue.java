package com.dennis_brink.android.myhigherlower;

import java.util.Random;

public class RandomValue implements IRandomValueConstants {

	/*  Usage:
		RandomValue.getRandomString(15))                    random string (full punctuation)      11PZxrx?i?;W_6W
		RandomValue.getRandomNumericalString(15))           random numerical string (only digits) 320458716456606
		RandomValue.getRandomKeyString(15))                 random key string (some punctuation)  nxv@Ev3kEg@w4uX
		RandomValue.getRandomNoPunctuationString(15))       random string (no punctuation)        wXp8AysHlWOLi87
    */

	public static String getRandomNoPunctuationString(int length) {

		return createString(LOWER_LIMITC, UPPER_LIMITC, length, PUNCTUATION_A);
		
	}

	public static String getRandomKeyString(int length) {

		return createString(LOWER_LIMITC, UPPER_LIMITC, length, PUNCTUATION_B);
		
	}	
	
	public static String getRandomString(int length) {
		
		return createString(LOWER_LIMITC, UPPER_LIMITC, length);
		
	}

	public static String getRandomNumericalString(int length) {
		
		return createString(LOWER_LIMITD, UPPER_LIMITD, length);
		
	}

	public static String getRandomNumericalString(int length, boolean allowLeadingZero) {

		StringBuilder stringBuilder = new StringBuilder();
		int p;
		for(int i=0;i<length;i++) {
			if (!allowLeadingZero && i==0) {
				p = getRandomNumberUsingInts(LOWER_LIMITD + 1, UPPER_LIMITD);
			} else {
				p = getRandomNumberUsingInts(LOWER_LIMITD, UPPER_LIMITD);
			}
			stringBuilder.append((char) p);
		}
		return stringBuilder.toString();

	}

	private static String createString(int lowerLimit, int upperLimit, int length) {
		
		return createString(lowerLimit, upperLimit, length, null);
		
	}

	private static int getRandomNumberUsingInts(int min, int max) {
		
	    Random random = new Random();
	    return random.ints(min, max)
	      .findFirst()
	      .getAsInt();
	    
	}	
	
	private static String createString(int lowerLimit, int upperLimit, int length, int[] exclusionArray) {

		StringBuilder stringBuilder = new StringBuilder();
		int p;

		for(int i=0;i<length;i++) {		
			if(!(exclusionArray == null)) {
				do {
					p = getRandomNumberUsingInts(lowerLimit, upperLimit);
				} while (isElement(p, exclusionArray)); 
			} else {
				p = getRandomNumberUsingInts(lowerLimit, upperLimit);	
			}
			stringBuilder.append((char) p); 
		}		
		return stringBuilder.toString();		

	}

	private static boolean isElement(int key, int[] array) {
		
	    for (int i : array) {
	        if (i == key) {
	            return true;
	        }
	    }
	    return false;
	    
	}	
	
}
