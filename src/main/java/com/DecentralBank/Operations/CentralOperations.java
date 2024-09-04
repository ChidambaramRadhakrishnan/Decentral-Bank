package com.DecentralBank.Operations;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CentralOperations {
	
	/*
	 * This method is for Generator Admin id
	 */
	public static String EmployeeIdGenerator() {
		String ref ="1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        while(sb.length()<5){
            int index = (int) (random.nextFloat() * ref.length());
            sb.append(ref.charAt(index));
        }
        String randomNum = "DCB"+sb.toString();

        return randomNum;
	}
	
	/*
	 * This method is for Generator Account Number
	 */
	public static String CustomerAccountNumberGenerator() {
		String ref ="1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        while(sb.length()<8){
            int index = (int) (random.nextFloat() * ref.length());
            sb.append(ref.charAt(index));
        }
        String randomNum = "DB"+sb.toString();

        return randomNum;
	}
	
	public static String RupeeConvertion(String number) {
		NumberFormat rupee = NumberFormat.getCurrencyInstance(new Locale("en","IN"));
		
		int formatNumber = Integer.parseInt(number);
		
		String INR = rupee.format(formatNumber);
		
		return INR;
		
	}
	
	/*
	 * Pattern matcher
	 */
	public static Boolean ValidateInputValue(String InputText,String pattern) {
		
		boolean status = Pattern.compile(pattern).matcher(InputText).matches();
		
        return status;
	}
	

}
