/**
* @author Will Luttmann
* @version 1.0
* cs570 Project 1	Final Draft Code
*
*/

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MoonPhase{

	//************GLOBAL*************
	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	//seed for know new moon date/time from /https://www.almanac.com/astronomy/moon/calendar/zipcode/19382/2020-01
	private static final LocalDateTime dateSeed = LocalDateTime.parse("2020-01-24 16:44:00", dateFormat);

	//from https://www.moontracks.com/lunar_ingress.html
	private static final LocalDateTime moonSignSeed = LocalDateTime.parse("2020-04-02 18:26:00", dateFormat);

	//from https://minkukel.com/en/various/calculating-moon-phase/
	private static final double lunarCycle = 29.53058770576; //days

	//from https://en.wikipedia.org/wiki/Moon
	private static final double moonRevolution = 655.719864; //27.321661 days * 24h = 655.719864 hours for 1 full orbit revolution

	//********END GLOBAL*************

	//**********METHODS**************
	/**
	* formats date to humanreadable
	* @param d (date) timestamp of type LocalDateTime
	* @return human readable timestamp format using dateFormat, private global final variable
	*
	*/
	public static String formatDate(LocalDateTime d){
		return d.format(dateFormat);
	}

	/**
	* formats date to humanreadable
	* @param d (date) timestamp of type LocalDateTime
	* @return human readable date format using dayFormat, private global final variable
	*
	*/
	public static String formatDay(LocalDateTime d){
		return d.format(dayFormat);
	}

	/**
	* print welcome method
	* @param d (date) timestamp of type LocalDateTime
	* prints welcome message displaying current time with moonphase and age
	*
	*/
	public static void printWelcome(LocalDateTime d){
		double age = getMoonAge(d);

		System.out.println("*********************************************************");
		System.out.printf("%-16s %-19s %19s%n","* Hello! Today is", formatDate(d),"*");
		System.out.printf("%-38s %-5.2f %12s%n","* The moon age since last New Moon is:",age,"*");
		System.out.printf("%-22s %-18s %15s%n","* The moon's phase is:",getMoonPhase(age), "*");
		System.out.printf("%-16s %-13s %25s%n","* The sun is in:", getSunSign(d), "*");
		System.out.printf("%-17s %-13s %24s%n","* The moon is in:", getMoonSign(d),"*");
		System.out.println("*********************************************************");
	}

	/**
	* @param d (date) timestamp of type LocalDateTime
	* recursive method to find next full moon using 1 hour increments
	* @return day difference as double between now and next full moon
	*
	*/
	public static double daysUntilNextFullMoon(LocalDateTime d){

		Duration dateDiff = Duration.between(LocalDateTime.now(),d);
		double daysDiff = dateDiff.toHours()/24.0;
		int moonAge = (int)Math.round(getMoonAge(d));

		if(moonAge==15){
			return daysDiff;
		}
		else{
			return daysUntilNextFullMoon(d.plusHours(1));
		}
	}

	/**
	* @param d (date) timestamp of type LocalDateTime
	* recursive method to find next new moon using 1 hour increments
	* @return day difference as double between now and next new moon
	*
	*/
	public static double daysUntilNextNewMoon(LocalDateTime d){

		Duration dateDiff = Duration.between(LocalDateTime.now(),d);
		double daysDiff = dateDiff.toHours()/24.0;
		int moonAge = (int)Math.round(getMoonAge(d));

		if(moonAge==0){
			return daysDiff;
		}
		else{
			return daysUntilNextNewMoon(d.plusHours(1));	
		}
	}

	/**
	* @param d (date) timestamp of type LocalDateTime
	* recursive method to find next first quarter moon using 1 hour increments
	* @return day difference as double between now and next first quarter moon
	*
	*/
	public static double daysUntilNextFirstQuarterMoon(LocalDateTime d){

		Duration dateDiff = Duration.between(LocalDateTime.now(),d);
		double daysDiff = dateDiff.toHours()/24.0;
		int moonAge = (int)Math.round(getMoonAge(d));

		if(moonAge==7){
			return daysDiff;
		}
		else{
			return daysUntilNextFirstQuarterMoon(d.plusHours(1));	
		}
	}

	/**
	* @param d (date) timestamp of type LocalDateTime
	* recursive method to find next third quarter moon using 1 hour increments
	* @return day difference as double between now and next third quarter moon
	*
	*/
	public static double daysUntilNextThirdQuarterMoon(LocalDateTime d){

		Duration dateDiff = Duration.between(LocalDateTime.now(),d);
		double daysDiff = dateDiff.toHours()/24.0;
		int moonAge = (int)Math.round(getMoonAge(d));

		if(moonAge==22){
			return daysDiff;
		}
		else{
			return daysUntilNextThirdQuarterMoon(d.plusHours(1));
		}
	}

	/**
	* gets moon age regardless of past/present/future dates
	* @param d (date) timestamp of type LocalDateTime
	* method to calculate the moon age as an int, using gloabl date seed variable
	* @return integer of moon's age past/present/future dates
	*
	*/
	public static double getMoonAge (LocalDateTime d){
		Duration dateDiff = Duration.between(dateSeed,d);
		double daysDiff = dateDiff.toHours()/24.0; //gets day difference with double precision -- Duration.toDays() returns a long

		if(daysDiff >=0)
			return (daysDiff % lunarCycle);
		else
			return (lunarCycle + (daysDiff % lunarCycle));
	}

	/**
	* outputs moonphase according to age
	* @param moonAge (int) 
	* method to calculate the moon phase, given the age since last new moon
	* @return string phase of moon
	*
	*/
	public static String getMoonPhase(double moonAge){
		int ageInDays = (int)Math.round(moonAge);
		switch(ageInDays){
			case 0: return "new moon 🌑";
			case 1: 
			case 2: 
			case 3: 
			case 4:  
			case 5: 
			case 6: return "waxing crescent 🌒";
			case 7: return "first quarter 🌓";
			case 8: 
			case 9: 
			case 10: 
			case 11: 
			case 12: 
			case 13: 
			case 14: return "waxing gibbous 🌔";
			case 15: return "full moon 🌕";
			case 16:  
			case 17: 
			case 18: 
			case 19: 
			case 20:
			case 21: return "waning gibbous 🌖";
			case 22: return "third quarter 🌗";
			case 23:  
			case 24: 
			case 25: 
			case 26: 
			case 27: 
			case 28: 
			case 29: return "waning crescent 🌘";
			default: return "UNKNOWN";
		} 

	}

	/**
	* outputs sun sign according to day of year
	* @param d (LocalDateTime) 
	* 
	* @return string sun sign
	*
	*/
	public static String getSunSign(LocalDateTime d){

		DateTimeFormatter monthDay = DateTimeFormatter.ofPattern("MM-dd");
		String x = d.format(monthDay);
		String sign = "-1";

		if(x.compareTo("03-21")>=0 && x.compareTo("04-19")<=0){
			sign = "Aries ♈";
		}
		if(x.compareTo("04-20")>=0 && x.compareTo("05-20")<=0){
			sign = "Taurus ♉";
		}
		if(x.compareTo("05-21")>=0 && x.compareTo("06-21")<=0){
			sign = "Gemini ♊";
		}
		if(x.compareTo("06-22")>=0 && x.compareTo("07-22")<=0){
			sign = "Cancer ♋";
		}
		if(x.compareTo("07-23")>=0 && x.compareTo("08-22")<=0){
			sign = "Leo ♌";
		}
		if(x.compareTo("08-23")>=0 && x.compareTo("09-22")<=0){
			sign = "Virgo ♍";
		}
		if(x.compareTo("09-23")>=0 && x.compareTo("10-23")<=0){
			sign = "Libra ♎";
		}
		if(x.compareTo("10-24")>=0 && x.compareTo("11-22")<=0){
			sign = "Scorpio ♏";
		}
		if(x.compareTo("11-23")>=0 && x.compareTo("12-21")<=0){
			sign = "Sagittarius ♐";
		}
		if(x.compareTo("12-22")>=0 && x.compareTo("12-31")<=0){
			sign = "Capricorn ♑";
		}
		if(x.compareTo("01-01")>=0 && x.compareTo("01-19")<=0){
			sign = "Capricorn ♑";
		}
		if(x.compareTo("01-20")>=0 && x.compareTo("02-18")<=0){
			sign = "Aquarius ♒";
		}
		if(x.compareTo("02-19")>=0 && x.compareTo("03-20")<=0){
			sign = "Pisces ♓";
		}

		return sign;
	}

	/**
	* 1 full moon revolution is 655.719864 hours
	* to find sign, find difference in hours from target date and % 655.719864
	* dividing 655.719864 by 12 signs, results in the moon being in each sign for 54.643322 hours
	* i used https://www.astrocal.co.uk/moon-sign-calculator/ to check for timestamp correctness
	* @param d (LocalDateTime) 
	* 
	* @return string moon sign
	*
	*/
	public static String getMoonSign(LocalDateTime d){
		Duration dateDiff = Duration.between(moonSignSeed,d);
		double hourDiff = dateDiff.toHours();
		double position = -1;
		String sign = "-1";

		if(hourDiff >=0)
			position = (hourDiff % moonRevolution);
		else
			position = (moonRevolution + (hourDiff % moonRevolution));


		if(position >= 0 && position <= 54.643322)
			sign = "Leo ♌";
		if(position >= 54.643323 && position <= 109.286644)
			sign = "Virgo ♍";
		if(position >= 109.28665 && position <= 163.929966)
			sign = "Libra ♎";
		if(position >= 163.929967 && position <= 218.573288)
			sign = "Scorpio ♏";
		if(position >= 218.573289 && position <= 273.21661)
			sign = "Sagittarius ♐";
		if(position >= 273.21662 && position <= 327.859932)
			sign = "Capricorn ♑";
		if(position >= 327.859933 && position <= 382.503254)
			sign = "Aquarius ♒";
		if(position >= 382.503255 && position <= 437.146576)
			sign = "Pisces ♓";
		if(position >= 437.146577 && position <= 491.789898)
			sign = "Aries ♈";
		if(position >= 491.789899 && position <= 546.43322)
			sign = "Taurus ♉";
		if(position >= 546.43323 && position <= 601.076542)
			sign = "Gemini ♊";
		if(position >= 601.076543 && position <= 655.719864)
			sign = "Cancer ♋";

		return sign;
	}

	//******END METHODS**************

	//start main
	public static void main(String[] args){
		
		Scanner scnr = new Scanner(System.in);
		LocalDateTime currDate = LocalDateTime.now();
		double theMoonAge;
		String theMoonPhase;
		String continueFlag = "y";
		boolean isValidInput = false;
		String [] nextPhaseNames = {"new moon 🌑   ","first quarter 🌓","full moon 🌕   ","third quarter 🌗"};
		double [] nextPhaseDays = {daysUntilNextNewMoon(currDate),daysUntilNextFirstQuarterMoon(currDate),daysUntilNextFullMoon(currDate),daysUntilNextThirdQuarterMoon(currDate)};
		LocalDateTime [] nextPhaseDate = {currDate.plusMinutes(Math.round(nextPhaseDays[0]*24*60)),currDate.plusMinutes(Math.round(nextPhaseDays[1]*24*60)),currDate.plusMinutes(Math.round(nextPhaseDays[2]*24*60)),currDate.plusMinutes(Math.round(nextPhaseDays[3]*24*60))};

		String userInput;
		double userMoonAge;
		String userMoonPhase;

		System.out.println();
		System.out.println();

		//print welcome message
		currDate = LocalDateTime.now();

		printWelcome(currDate);
		System.out.println();
		System.out.println();		

		//print next dates of interest
		System.out.println("*********************************************************");
		for(int j = 0; j < nextPhaseNames.length;j++){
			System.out.printf("* %-5.2f %-15s %16s %2s %-10s %2s%n",nextPhaseDays[j], "days until next",nextPhaseNames[j],"on",formatDay(nextPhaseDate[j]),"*");
		}
		System.out.println("*********************************************************");
		System.out.println();
		System.out.println();

		//find moonphase, sun sign, moon sign from user input... loop until quit
		do{
			isValidInput = false;
			System.out.print("Enter a timestamp to find the moon's phase: ");
			userInput = scnr.nextLine();
			System.out.println();

			while(!isValidInput){

				try{

					LocalDateTime.parse(userInput,dateFormat);
					isValidInput = true;	

				}catch (java.time.format.DateTimeParseException e){ 
					System.out.print("Invalid input, enter a timestamp in the form yyyy-MM-dd HH:mm:ss: ");
					userInput = scnr.nextLine();
				}	
			}		

			LocalDateTime userInputFormatted = LocalDateTime.parse(userInput,dateFormat); 
			userMoonAge = getMoonAge(userInputFormatted);
			userMoonPhase = getMoonPhase(userMoonAge);

			System.out.println("Moon phase at " + userInput + " is " + userMoonPhase);
			System.out.println("The sun is in: " + getSunSign(userInputFormatted));
			System.out.println("The moon is in: " + getMoonSign(userInputFormatted));
			System.out.println();
			System.out.println();
			System.out.println();

			System.out.println("Would you like to see another timestamp? (y/n) ");
			continueFlag = scnr.nextLine();

			while (!continueFlag.equalsIgnoreCase("y") && !continueFlag.equalsIgnoreCase("n")){
				System.out.println("Please enter 'y' to input another timestamp or 'n' to quit (without quotes)");
				continueFlag=scnr.nextLine();
			}
		} while (!continueFlag.equalsIgnoreCase("N"));
	}//end main
}//end class