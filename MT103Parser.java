package com.test.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.io.IConversionService;
import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.BIC;
import com.prowidesoftware.swift.model.SwiftBlock;
import com.prowidesoftware.swift.model.SwiftBlock1;
import com.prowidesoftware.swift.model.SwiftBlock2;
import com.prowidesoftware.swift.model.SwiftBlock2Input;
import com.prowidesoftware.swift.model.SwiftBlock2Output;
import com.prowidesoftware.swift.model.SwiftBlock3;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftBlock5;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field70;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

public class MT103Parser {
	private static final Random generator = new Random();
	public static String AppId = "F";
	public static String tag23B ;
	public static String tag20 ;

	public static String field50A ;
	public static String field50F ;
	public static String field50K ;
	public static String firstInChainNcc = null;

	public static String field50A_50F_50K ;

	public static String ServiceId = "01";
	public static String field32A = null;
	public static String field57A = null ;
	public static  String field59 = null ;
	public static  String accno_57A;
	public static  String bic_57A = null;
	public static  String nccType_57A;
	public static  String nccCode_57A;
	public static  String field57B = null ;
	public static  String location_57B;
	public static  String accno_57B;
	public static  String accno_57C;
	public static  String field57C = null  ;
	public static  String nccType_57C;
	public static  String nccCode_57C;
	public static String field57D = null  ;
	public static  String nccType_57D;
	public static  String nccCode_57D;
	public static String accno_57D;
	public static String name57D = null;
	public static String address1_57D= null;
	public static String address2_57D= null;
	public static String address3_57D= null;
	public static String field56A = null;
	public static String accno_56A;
	public static String nccType_56A;
	public static String nccCode_56A;
	public static String bic_56A;
	public static String accno_56C;
	public static String field56C = null  ;
	public static String nccType_56C;
	public static String nccCode_56C;
	public static String field56D = null  ;
	public static String nccType_56D;
	public static String nccCode_56D;
	public static String accno_56D;
	public static String name56D = null;
	public static String address1_56D= null;
	public static String address2_56D= null;
	public static String address3_56D= null;
	public static String accno_59;
	public static String name59 = null;
	public static String address1_59= null;
	public static String address2_59= null;
	public static String address3_59= null;
	public static String field59A = null;
	public static String accno_59A= null;
	public static String bic_59A = null;
	public static  String field59F = null;
	public static  String accno_59F= null;
	public static  String field70= null;
	public static  String field71A= null;
	public static  String field72= null;
	public static  String firstInChain = null;
	public static  String currCorr = null;//Aviroop
	public static  String ctryCorr = null;//Aviroop
	public static String ssiAgent = null;//Aviroop
	public static String firstAgentInChain = null;//Aviroop
	public static boolean isFisrtInCreditChainAccNo = false;//Aviroop
	public static Map<String,String> dataMap;
	public static Map<String,String> RMAMap;
	public static Map<String,String> routeMap;
	public static Map<String,String> correspMap;
	public static Map<String,String> currCorrespMap;
	public static Map<String,String> countryCorrespMap;
	public static String fileContent;




	public static void main(String[] args) {
		//	initHashTab();
		fileContent = readPaymentsFile();


		try {
			SwiftMessage m = (new SwiftParser()).parse(fileContent);
			//m.getBlock4()
			
			SwiftBlock4 sb4 = m.getBlock4();//SwiftParser.parseBlock4(fileContent);		

			if(!(sb4.getFieldByName("20") == null)) {
				tag20=sb4.getFieldByName("20").getValue();
			}
			System.out.println("Value Check:20------>" +tag20 );

			if(!(sb4.getFieldByName("23B") == null)) {
				tag23B=sb4.getFieldByName("23B").getValue();
			}
			System.out.println("Value Check:23B------>" +tag23B );

			if(!(sb4.getFieldByName("32A") == null)) {
				field32A = sb4.getFieldByName("32A").getValue();
			}
			System.out.println("Value Check:32A------>" +field32A );

			if(sb4.getFieldByName("50A") != null) {
				field50A = 	sb4.getFieldByName("50A").getValue();
			}
			System.out.println("Value Check:50A------>" +field50A );

			if(sb4.getFieldByName("50F") != null){
				field50F = 	sb4.getFieldByName("50F").getValue();
			}
			System.out.println("Value Check:50F------>" +field50F );

			if(sb4.getFieldByName("50K") != null){
				field50K = 	sb4.getFieldByName("50K").getValue();
			}
			System.out.println("Value Check:50K------>" +field50K );

			if((field50A != null) && (field50F ==null) && (field50K ==null) ) {
				field50A_50F_50K = field50A;
			}
			if((field50A == null) && (field50F !=null) && (field50K ==null) ) {
				field50A_50F_50K = field50F;
			}
			if((field50A == null) && (field50F ==null) && (field50K !=null) ) {
				field50A_50F_50K = field50K;
			}

			System.out.println("Value Check:field50A_50F_50K------>" +field50A_50F_50K );

			if(!(sb4.getFieldByName("57A") == null)) {
				field57A = sb4.getFieldByName("57A").getValue();
			}
			System.out.println("Value Check:57A------>" +field57A );


			if(!(sb4.getFieldByName("57B") == null)) {
				field57B = sb4.getFieldByName("57B").getValue();
			}
			System.out.println("Value Check:57B------>" +field57B );

			if(!(sb4.getFieldByName("57C") == null)) {
				field57C = sb4.getFieldByName("57C").getValue();
			}
			System.out.println("Value Check:57C------>" +field57C );
			if(!(sb4.getFieldByName("57D") == null)) {
				field57D = sb4.getFieldByName("57D").getValue();
			}
			System.out.println("Value Check:57D------>>" +field57D );

			if(!(sb4.getFieldByName("56A") == null)) {
				field56A = sb4.getFieldByName("56A").getValue();
			}

			System.out.println("Value Check:56A------>" +field56A );

			if(!(sb4.getFieldByName("56C") == null)) {
				field56C = sb4.getFieldByName("56C").getValue();
			}

			System.out.println("Value Check:56C------>" +field56C );
			if(!(sb4.getFieldByName("56D") == null)) {
				field56D = sb4.getFieldByName("56D").getValue();
			}

			System.out.println("Value Check:56D------>" +field56D );

			if(!(sb4.getFieldByName("59") == null)) {
				field59 = sb4.getFieldByName("59").getValue();
			}
			System.out.println("Value Check:59------>" +field59 );

			if(!(sb4.getFieldByName("59A") == null)) {
				field59A = sb4.getFieldByName("59A").getValue();
			}

			System.out.println("Value Check:59A------>" +field59A );

			if(!(sb4.getFieldByName("59F") == null)) {
				field59F = sb4.getFieldByName("59F").getValue();
			}

			System.out.println("Value Check:59F------>" +field59F );

			if(!(sb4.getFieldByName("70") == null)) {
				field70 = sb4.getFieldByName("70").getValue();
			}

			System.out.println("Value Check:70------>" +field70 );

			if(!(sb4.getFieldByName("71A") == null)) {
				field71A = sb4.getFieldByName("71A").getValue();
			}

			System.out.println("Value Check:71A------>" +field71A );

			if(!(sb4.getFieldByName("72") == null)) {
				field72 = sb4.getFieldByName("72").getValue();
			}

			System.out.println("Value Check:72------>" +field72 );

			List<String> strList = new ArrayList<String>();

			//FIELD 56 VALIDATION STARTS FROM HERE

			if(!(sb4.getFieldByName("56A") == null)){
				if(!field56A.isEmpty()) {

					if (field56A.startsWith("//")) {
						System.out.println("56A: INSIDE DOUBLE SLASH CHECK");
						Scanner scanner = new Scanner(field56A);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();
						//BIC VALIDATION STARTS

						//NCC VALIDATION STARTS
						if(getATNCC(strList.get(0))|| getAUNCC(strList.get(0))||getBLNCC(strList.get(0))||getCCNCC(strList.get(0))||
								getCNNCC(strList.get(0))|| getESNCC(strList.get(0)) || getFWNCC(strList.get(0))||
								getGRNCC(strList.get(0)) || getHKNCC(strList.get(0)) || getIENCC(strList.get(0))||
								getINNCC(strList.get(0)) || getITNCC(strList.get(0)) || getNZNCC(strList.get(0)) ||
								getPLNCC(strList.get(0)) || getPTNCC(strList.get(0)) || getRTNCC(strList.get(0)) ||
								getSCNCC(strList.get(0)) || getZANCC(strList.get(0))) {
							nccType_56A = strList.get(0).substring(2, 4);
							nccCode_56A	= strList.get(0).substring(4, strList.get(0).length());

							System.out.println("Field 56A:NCC Type="+nccType_56A + ", NCCCode:"+nccCode_56A);
						}
						else {
							System.out.println("56A:Valid NCC not found");
						}

						//eNDS
						if (strList.size() > 1 && strList.get(1) != null ) {

							if(getBicCompare(strList.get(1))) {

								bic_56A = strList.get(1);
								System.out.println("Field 56A:BIC="+bic_56A);
							}
							else {
								System.out.println("Valid BIC not found - F56A is invalid as it should have a BIC");
							}
						}
						else{
							System.out.println("2nd line is empty and doesn't have a BIC - F56A is invalid as it should have a BIC");
						}

					}

					else if(field56A.startsWith("/")) {
						Scanner scanner = new Scanner(field56A);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();


						if(getAccountNoCheck(strList.get(0).substring(1, strList.get(0).length()))) {
							accno_56A = strList.get(0).substring(1, strList.get(0).length() );
							System.out.println("Field 56A:account number="+accno_56A);

						}

						else {
							System.out.println("56A:Valid Account Number not found");
						}


						if (strList.size() > 1 && strList.get(1) != null ) {
							if (getBicCompare(strList.get(1))) {
								bic_56A = strList.get(1);
								System.out.println("Field 56A:BIC="+bic_56A);
							}
							else {
								System.out.println("field 56A has an Account Number but no valid BIC - F56A is invalid as it should have a BIC");
							}
						}
						else{
							System.out.println("2nd line is empty and doesn't have a BIC - F56A is invalid as it should have a BIC");
						}

					}

					else {
						Scanner scanner = new Scanner(field56A);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();
						if(getBicCompare(strList.get(0))) {
							bic_56A = strList.get(0);
							System.out.println("****Field 56A:BIC="+bic_56A);
						}
						else{
							System.out.println("****"+strList.get(0)+" is not a valid BIC - F56A is invalid");

						}
					}
				}
			}

			//56A validation ends here

			//56C validation starts here
			if(!(sb4.getFieldByName("56C") == null)){
				if(!field56C.isEmpty()) {

					if (field56C.startsWith("//")) {
						System.out.println("56C:INSIDE DOUBLE SLASH CHECK");
						Scanner scanner = new Scanner(field56C);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();
						//ncc vALIDATION STARTS
						if(getATNCC(strList.get(0))|| getAUNCC(strList.get(0))||getBLNCC(strList.get(0))||getCCNCC(strList.get(0))||
								getCHNCC(strList.get(0))||getCNNCC(strList.get(0)) || getCPNCC(strList.get(0))||
								getESNCC(strList.get(0)) || getFWNCC(strList.get(0))||
								getGRNCC(strList.get(0)) || getHKNCC(strList.get(0)) || getIENCC(strList.get(0))||
								getINNCC(strList.get(0)) || getITNCC(strList.get(0)) || getNZNCC(strList.get(0)) ||
								getPLNCC(strList.get(0)) || getPTNCC(strList.get(0)) || getRTNCC(strList.get(0)) ||
								getRUNCC(strList.get(0)) || getSCNCC(strList.get(0)) || getSWNCC(strList.get(0)) || 
								getZANCC(strList.get(0))) {
							nccType_56C = strList.get(0).substring(2, 4);
							nccCode_56C	= strList.get(0).substring(4, strList.get(0).length());

							System.out.println("Field 56C:NCC Type="+nccType_56C + ", NCCCode:"+nccCode_56C);
						}
						else {
							System.out.println("56C: Valid NCC not found");
						}
						//eNDS
					}			
					else if(field56C.startsWith("/")) {
						System.out.println("^^^^ inside 56C , starts with /");

						Scanner scanner = new Scanner(field56C);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();


						System.out.println("^^^^ inside 56C , strList:"+strList);
						//	System.out.println("@@@@"+ getAccountNoCheck(strList.get(0).substring(1, strList.get(0).length())));

						if(getAccountNoCheck(strList.get(0).substring(1, strList.get(0).length()))) {
							accno_56C = strList.get(0).substring(1, strList.get(0).length() );
							System.out.println("Field 56C:account number="+accno_56C);

						}
						else {
							System.out.println("56C: Valid Account Number not found");
						}

					}		
					else {		
						System.out.println("****"+strList.get(0)+" not in VALID FORMAT");

					}
				}
			}
			//56C validation ends here


			//56D Validation Starts

			if(!(sb4.getFieldByName("56D") == null)){
				int count =0;
				if(!field56D.isEmpty()) {

					if (field56D.startsWith("//")) {
						System.out.println("56D:INSIDE DOUBLE SLASH CHECK");
						Scanner scanner = new Scanner(field56D);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();
						//NCC VALIDATION STARTS
						if(getATNCC(strList.get(0))|| getAUNCC(strList.get(0))||getBLNCC(strList.get(0))||getCCNCC(strList.get(0))||
								getCHNCC(strList.get(0))||getCNNCC(strList.get(0)) || getCPNCC(strList.get(0))||
								getESNCC(strList.get(0)) || getFWNCC(strList.get(0))||
								getGRNCC(strList.get(0)) || getHKNCC(strList.get(0)) || getIENCC(strList.get(0))||
								getINNCC(strList.get(0)) || getITNCC(strList.get(0)) || getNZNCC(strList.get(0)) ||
								getPLNCC(strList.get(0)) || getPTNCC(strList.get(0)) || getRTNCC(strList.get(0)) ||
								getRUNCC(strList.get(0)) || getSCNCC(strList.get(0)) || getSWNCC(strList.get(0)) || 
								getZANCC(strList.get(0))) {
							nccType_56D = strList.get(0).substring(2, 4);
							nccCode_56D	= strList.get(0).substring(4, strList.get(0).length());

							System.out.println("Field 56D:NCC Type="+nccType_56D+ ", NCCCode:"+nccCode_56D);
						}
						else {
							System.out.println("56D: No NCC Found");
						}
						//eNDS
					}			
					else if(field56D.startsWith("/")) {
						Scanner scanner = new Scanner(field56D);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();
						if(getAccountNoCheck(strList.get(0).substring(1, strList.get(0).length()))) {
							accno_56D = strList.get(0).substring(1, strList.get(0).length() );
							System.out.println("Field 56D:account number="+accno_56D);

						}
						else {
							System.out.println("Field 56D has no valid account number");
						}
					} //end of single / check
					else {		
						Scanner scanner = new Scanner(field56D);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();
						//System.out.println("inside this"+strList.size());
						//Validation for 4 lines
						if (strList.size() >=1 && strList.size() <5  ) {
							if(strList.size()==1) {
								if(getNameAndAddressLinesCheck(strList.get(0))) {
									name56D = strList.get(0);
									System.out.println("Field 56D Name : " + name56D);
								}
							}
							else if(strList.size()==2) {
								System.out.println("#########");
								if(getNameAndAddressLinesCheck(strList.get(0))) {
									name56D = strList.get(0);
									System.out.println("Field 56D Name : " + name56D);
								}
								if(!(strList.get(1) == null)  && getNameAndAddressLinesCheck(strList.get(1))) {
									address1_56D = strList.get(1);

									System.out.println("Field 56D Addr1 : " + address1_56D);
								}
							}
							else if(strList.size()==3) {
								if(getNameAndAddressLinesCheck(strList.get(0))) {
									name56D = strList.get(0);
									System.out.println("Field 56D Name : " + name56D);
								}
								if(!(strList.get(1) == null)  && getNameAndAddressLinesCheck(strList.get(1))) {
									address1_56D = strList.get(1);
									System.out.println("Field 56D Addr1 : " + address1_56D);
								}
								if(!(strList.get(2) == null)  && getNameAndAddressLinesCheck(strList.get(2))) {
									address2_56D = strList.get(2);
									System.out.println("Field 56D Addr2 : " + address2_56D);
								}
							}
							else if(strList.size()==4) {
								if(getNameAndAddressLinesCheck(strList.get(0))) {
									name56D = strList.get(0);
									System.out.println("Field 56D name56D : " + name56D);
								}
								if(!(strList.get(1) == null)  && getNameAndAddressLinesCheck(strList.get(1))) {
									address1_56D = strList.get(1);
									System.out.println("Field 56D Addr1 : " + address1_56D);
								}
								if(!(strList.get(2) == null)  && getNameAndAddressLinesCheck(strList.get(2))) {
									address2_56D = strList.get(2);
									System.out.println("Field 56D Addr2 : " + address2_56D);
								}
								if(!(strList.get(3) == null)  && getNameAndAddressLinesCheck(strList.get(3))) {
									address3_56D = strList.get(3);
									System.out.println("Field 56D Addr3 : " + address3_56D);
								}
							}
						}		
					}//END OF ELSE BLOCK, NO / , NO //
					if (strList.size()==1 && strList.get(0).startsWith("/")) {
						System.out.println("********56D not in VALID FORMAT - Name or Address not present");
					}
					//Validation for 4 lines
					if (strList.size() >=2 && strList.size() <6  && strList.get(0).startsWith("/")) {
						//System.out.println("********57D---inside check1->>" +strList.size()+" ,"+strList);

						if(strList.size()==2) {

							if(getNameAndAddressLinesCheck(strList.get(1))) {
								name56D = strList.get(1);

							}
						}
						else if(strList.size()==3) {
							if(getNameAndAddressLinesCheck(strList.get(1))) {
								name56D = strList.get(1);
							}
							if(!(strList.get(2) == null)  && getNameAndAddressLinesCheck(strList.get(2))) {
								address1_56D = strList.get(2);
							}
						}
						else if(strList.size()==4) {
							if(getNameAndAddressLinesCheck(strList.get(1))) {
								name56D = strList.get(1);
							}
							if(!(strList.get(2) == null)  && getNameAndAddressLinesCheck(strList.get(2))) {
								address1_56D = strList.get(2);
							}
							if(!(strList.get(3) == null)  && getNameAndAddressLinesCheck(strList.get(3))) {
								address2_56D = strList.get(3);
							}

						}
						else if(strList.size()==5) {
							if(getNameAndAddressLinesCheck(strList.get(1))) {
								name56D = strList.get(1);
							}
							if(!(strList.get(2) == null)  && getNameAndAddressLinesCheck(strList.get(2))) {
								address1_56D = strList.get(2);
							}
							if(!(strList.get(3) == null)  && getNameAndAddressLinesCheck(strList.get(3))) {
								address2_56D = strList.get(3);
							}
							if(!(strList.get(4) == null)  && getNameAndAddressLinesCheck(strList.get(4))) {
								address3_56D = strList.get(4);
							}
						}
						System.out.println("------Field 56D Name : " + name56D);
						System.out.println("-----Field 56D Addr1 : " + address1_56D);
						System.out.println("-----Field 56D Addr2 : " + address2_56D);
						System.out.println("------Field 56D Addr3 : " + address3_56D);
					}		

				}
			}


			//FIELD 56 VALIDATION ENDS HERE


			strList = new ArrayList<String>();


			if(!(sb4.getFieldByName("57A") == null)){
				if(!field57A.isEmpty()) {

					if (field57A.startsWith("//")) {
						System.out.println("INSIDE DOUBLE SLASH CHECK");
						Scanner scanner = new Scanner(field57A);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();
						//BIC VALIDATION STARTS

						if (strList.size() == 1 && strList.get(0) != null ) {
							if (getBicCompare(strList.get(0).substring(2, strList.get(0).length()))) {
								bic_57A = strList.get(0).substring(2, strList.get(0).length());
								System.out.println("Field 57A:BIC="+bic_57A);
							}
							else {
								System.out.println("Field 57A:has only 1 line. Not matching with valid BIC Found");
								return;
							}
						}
						//NCC VALIDATION STARTS
						if(getATNCC(strList.get(0))|| getAUNCC(strList.get(0))||getBLNCC(strList.get(0))||getCCNCC(strList.get(0))||
								getCNNCC(strList.get(0))|| getESNCC(strList.get(0)) || getFWNCC(strList.get(0))||
								getGRNCC(strList.get(0)) || getHKNCC(strList.get(0)) || getIENCC(strList.get(0))||
								getINNCC(strList.get(0)) || getITNCC(strList.get(0)) || getNZNCC(strList.get(0)) ||
								getPLNCC(strList.get(0)) || getPTNCC(strList.get(0)) || getRTNCC(strList.get(0)) ||
								getSCNCC(strList.get(0)) || getZANCC(strList.get(0))) {
							nccType_57A = strList.get(0).substring(2, 4);
							nccCode_57A	= strList.get(0).substring(4, strList.get(0).length());

							System.out.println("Field 57A:NCC Type="+nccType_57A + ", NCCCode:"+nccCode_57A);
						}
						else {
							System.out.println("Valid NCC not found");
						}

						//eNDS
						if (strList.size() > 1 && strList.get(1) != null ) {

							if(getBicCompare(strList.get(1))) {

								bic_57A = strList.get(1);
								System.out.println("Field 57A:BIC="+bic_57A);
							}
							else {
								System.out.println("Valid BIC not found - F57A is invalid as it should have a BIC");
							}
						}
						else{
							System.out.println("2nd line is empty and doesn't have a BIC - F57A is invalid as it should have a BIC");
						}

					}

					else if(field57A.startsWith("/")) {
						Scanner scanner = new Scanner(field57A);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();

						if(getAccountNoCheck(strList.get(0).substring(1, strList.get(0).length()))) {
							accno_57A = strList.get(0).substring(1, strList.get(0).length() );
							System.out.println("Field 57A:account number="+accno_57A);

						}
						else {
							System.out.println("Valid Account Number not found");
						}


						if (strList.size() > 1 && strList.get(1) != null ) {
							if (getBicCompare(strList.get(1))) {
								bic_57A = strList.get(1);
								System.out.println("Field 57A:BIC="+bic_57A);
							}
							else {
								System.out.println("field 57A has an Account Number but no valid BIC - F57A is invalid as it should have a BIC");
							}
						}
						else{
							System.out.println("2nd line is empty and doesn't have a BIC - F57A is invalid as it should have a BIC");
						}

					}

					else {
						Scanner scanner = new Scanner(field57A);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();
						if(getBicCompare(strList.get(0))) {
							bic_57A = strList.get(0);
							System.out.println("****Field 57A:BIC="+bic_57A);
						}
						else{
							System.out.println("****"+strList.get(0)+" is not a valid BIC - F57A is invalid");

						}
					}
				}
			}

			if(!(sb4.getFieldByName("57B") == null) && !field57B.isEmpty()) {
				if(field57B.startsWith("/")) {
					Scanner scanner = new Scanner(field57B);
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						strList.add(line);
					}
					scanner.close();				
					if(!getBicCompare(strList.get(0))) {
						if(strList.get(0).length() <=34) {
							accno_57B = strList.get(0).substring(1, strList.get(0).length() );
							System.out.println("Field 57B:account number="+accno_57B);

						}
						else {
							System.out.println("Field 57B has no account number");
						}
					}
					else {
						System.out.println("Field 57B:Not a valid account format, its matching BIC format");

					}

					if (getLocationCheck(strList.get(1))) {
						location_57B = strList.get(1);
						System.out.println("Field 57B:Location="+location_57B);
					}
					else {
						System.out.println("Not a valid Field 57B:Location=");
					}
				}
				else {
					Scanner scanner = new Scanner(field57B);
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						strList.add(line);
					}
					scanner.close();
					if(getLocationCheck(strList.get(0))) {
						location_57B = strList.get(0);
						System.out.println("****Field 57B:Location="+location_57B);
					}
					else{
						System.out.println("****"+strList.get(0)+" is not a valid Location");

					}
				}	

			}

			//57C Validation Starts
			if(!(sb4.getFieldByName("57C") == null)){
				if(!field57C.isEmpty()) {

					if (field57C.startsWith("//")) {
						System.out.println("57C:INSIDE DOUBLE SLASH CHECK");
						Scanner scanner = new Scanner(field57C);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();
						//ncc vALIDATION STARTS
						if(getATNCC(strList.get(0))|| getAUNCC(strList.get(0))||getBLNCC(strList.get(0))||getCCNCC(strList.get(0))||
								getCHNCC(strList.get(0))||getCNNCC(strList.get(0)) || getCPNCC(strList.get(0))||
								getESNCC(strList.get(0)) || getFWNCC(strList.get(0))||
								getGRNCC(strList.get(0)) || getHKNCC(strList.get(0)) || getIENCC(strList.get(0))||
								getINNCC(strList.get(0)) || getITNCC(strList.get(0)) || getNZNCC(strList.get(0)) ||
								getPLNCC(strList.get(0)) || getPTNCC(strList.get(0)) || getRTNCC(strList.get(0)) ||
								getRUNCC(strList.get(0)) || getSCNCC(strList.get(0)) || getSWNCC(strList.get(0)) || 
								getZANCC(strList.get(0))) {
							nccType_57C = strList.get(0).substring(2, 4);
							nccCode_57C	= strList.get(0).substring(4, strList.get(0).length());

							System.out.println("Field 57C:NCC Type="+nccType_57C + ", NCCCode:"+nccCode_57C);
						}
						else {
							System.out.println("57C: No NCC Found");
						}
						//eNDS
					}			
					else if(field57C.startsWith("/")) {
						Scanner scanner = new Scanner(field57C);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();
						if(getAccountNoCheck(strList.get(0).substring(1, strList.get(0).length()))) {
							accno_57C = strList.get(0).substring(1, strList.get(0).length() );
							System.out.println("Field 57C:account number="+accno_57C);

						}
						else {
							System.out.println("Field 57C has no account number");
						}

					}		
					else {		
						System.out.println("****"+strList.get(0)+" not in VALID FORMAT");

					}
				}
			}

			//57C Validation Ends


			//57D Validation Starts
			if(!(sb4.getFieldByName("57D") == null)){
				int count =0;
				if(!field57D.isEmpty()) {

					if (field57D.startsWith("//")) {
						System.out.println("57D:INSIDE DOUBLE SLASH CHECK");
						Scanner scanner = new Scanner(field57D);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();
						//NCC VALIDATION STARTS
						if(getATNCC(strList.get(0))|| getAUNCC(strList.get(0))||getBLNCC(strList.get(0))||getCCNCC(strList.get(0))||
								getCHNCC(strList.get(0))||getCNNCC(strList.get(0)) || getCPNCC(strList.get(0))||
								getESNCC(strList.get(0)) || getFWNCC(strList.get(0))||
								getGRNCC(strList.get(0)) || getHKNCC(strList.get(0)) || getIENCC(strList.get(0))||
								getINNCC(strList.get(0)) || getITNCC(strList.get(0)) || getNZNCC(strList.get(0)) ||
								getPLNCC(strList.get(0)) || getPTNCC(strList.get(0)) || getRTNCC(strList.get(0)) ||
								getRUNCC(strList.get(0)) || getSCNCC(strList.get(0)) || getSWNCC(strList.get(0)) || 
								getZANCC(strList.get(0))) {
							nccType_57D = strList.get(0).substring(2, 4);
							nccCode_57D	= strList.get(0).substring(4, strList.get(0).length());

							System.out.println("Field 57D:NCC Type="+nccType_57D+ ", NCCCode:"+nccCode_57D);
						}
						else {
							System.out.println("57D: No NCC Found");
						}
						//eNDS
					}			
					else if(field57D.startsWith("/")) {
						Scanner scanner = new Scanner(field57D);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();
						if(getAccountNoCheck(strList.get(0).substring(1, strList.get(0).length()))) {
							accno_57D = strList.get(0).substring(1, strList.get(0).length() );
							System.out.println("Field 57D:account number="+accno_57D);

						}
						else {
							System.out.println("Field 57D has no valid account number");
						}
					} //end of single / check
					else {		
						Scanner scanner = new Scanner(field57D);
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							strList.add(line);
						}
						scanner.close();
						//Validation for 4 lines
						if (strList.size() >=1 && strList.size() <5  ) {
							if(strList.size()==1) {
								if(getNameAndAddressLinesCheck(strList.get(0))) {
									name57D = strList.get(0);
									System.out.println("Field 57D Name : " + name57D);
								}
							}
							else if(strList.size()==2) {
								System.out.println("#########");
								if(getNameAndAddressLinesCheck(strList.get(0))) {
									name57D = strList.get(0);
									System.out.println("Field 57D Name : " + name57D);
								}
								if(!(strList.get(1) == null)  && getNameAndAddressLinesCheck(strList.get(1))) {
									address1_57D = strList.get(1);

									System.out.println("Field 57D Addr1 : " + address1_57D);
								}
							}
							else if(strList.size()==3) {
								if(getNameAndAddressLinesCheck(strList.get(0))) {
									name57D = strList.get(0);
									System.out.println("Field 57D Name : " + name57D);
								}
								if(!(strList.get(1) == null)  && getNameAndAddressLinesCheck(strList.get(1))) {
									address1_57D = strList.get(1);
									System.out.println("Field 57D Addr1 : " + address1_57D);
								}
								if(!(strList.get(2) == null)  && getNameAndAddressLinesCheck(strList.get(2))) {
									address2_57D = strList.get(2);
									System.out.println("Field 57D Addr2 : " + address2_57D);
								}
							}
							else if(strList.size()==4) {
								if(getNameAndAddressLinesCheck(strList.get(0))) {
									name57D = strList.get(0);
									System.out.println("Field 57D name57D : " + name57D);
								}
								if(!(strList.get(1) == null)  && getNameAndAddressLinesCheck(strList.get(1))) {
									address1_57D = strList.get(1);
									System.out.println("Field 57D Addr1 : " + address1_57D);
								}
								if(!(strList.get(2) == null)  && getNameAndAddressLinesCheck(strList.get(2))) {
									address2_57D = strList.get(2);
									System.out.println("Field 57D Addr2 : " + address2_57D);
								}
								if(!(strList.get(3) == null)  && getNameAndAddressLinesCheck(strList.get(3))) {
									address3_57D = strList.get(3);
									System.out.println("Field 57D Addr3 : " + address3_57D);
								}
							}
						}		
					}//END OF ELSE BLOCK, NO / , NO //
					if ((strList.size()==1 && strList.get(0).startsWith("/"))){
						System.out.println("********57D not in VALID FORMAT - Name or Address not present");
					}
					//Validation for 4 lines
					if (strList.size() >=2 && strList.size() <6  && strList.get(0).startsWith("/")) {

						if(strList.size()==2) {
							if(getNameAndAddressLinesCheck(strList.get(1))) {
								name57D = strList.get(1);
							}
						}
						else if(strList.size()==3) {
							if(getNameAndAddressLinesCheck(strList.get(1))) {
								name57D = strList.get(1);
							}
							if(!(strList.get(2) == null)  && getNameAndAddressLinesCheck(strList.get(2))) {
								address1_57D = strList.get(2);
							}
						}
						else if(strList.size()==4) {
							if(getNameAndAddressLinesCheck(strList.get(1))) {
								name57D = strList.get(1);
							}
							if(!(strList.get(2) == null)  && getNameAndAddressLinesCheck(strList.get(2))) {
								address1_57D = strList.get(2);
							}
							if(!(strList.get(3) == null)  && getNameAndAddressLinesCheck(strList.get(3))) {
								address2_57D = strList.get(3);
							}

						}
						else if(strList.size()==5) {
							if(getNameAndAddressLinesCheck(strList.get(1))) {
								name57D = strList.get(1);
							}
							if(!(strList.get(2) == null)  && getNameAndAddressLinesCheck(strList.get(2))) {
								address1_57D = strList.get(2);
							}
							if(!(strList.get(3) == null)  && getNameAndAddressLinesCheck(strList.get(3))) {
								address2_57D = strList.get(3);
							}
							if(!(strList.get(4) == null)  && getNameAndAddressLinesCheck(strList.get(4))) {
								address3_57D = strList.get(4);
							}
						}
						System.out.println("------Field 57D Name : " + name57D);
						System.out.println("-----Field 57D Addr1 : " + address1_57D);
						System.out.println("-----Field 57D Addr2 : " + address2_57D);
						System.out.println("------Field 57D Addr3 : " + address3_57D);
					}		

				}
			}
			strList = new ArrayList<String>();



			//57D Validation Ends
			//59 Validation starts here
			if(!(sb4.getFieldByName("59") == null)){

				int count =0;
				if(!field59.isEmpty()) {
					Scanner scanner = new Scanner(field59);
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						strList.add(line);
					}
					scanner.close();
					System.out.println("********SIZE OF LIST:"+ strList.size());
					if (strList.size()==1 && strList.get(0).startsWith("/")) {
						System.out.println("********59 not in VALID FORMAT - Name or Address not present");
						return;
					}
					else if(strList.size() > 1 && field59.startsWith("/")) {
						if(getAccountNoCheck(strList.get(0).substring(1, strList.get(0).length()))) {
							accno_59 = strList.get(0).substring(1, strList.get(0).length() );
							System.out.println("Field 59:account number="+accno_59);

						}
						else {
							System.out.println("Field 59 has no valid account number");
						}
					} //end of single / check


					if (strList.size() >=2 && strList.size() <6  && strList.get(0).startsWith("/")) {

						if(strList.size()==2) {
							if(getNameAndAddressLinesCheck(strList.get(1))) {
								name59 = strList.get(1);
								//		System.out.println("********57D---inside check3:"+name57D);
							}
						}
						else if(strList.size()==3) {
							if(getNameAndAddressLinesCheck(strList.get(1))) {
								name59 = strList.get(1);
							}
							if(!(strList.get(2) == null)  && getNameAndAddressLinesCheck(strList.get(2))) {
								address1_59 = strList.get(2);
							}
						}
						else if(strList.size()==4) {
							if(getNameAndAddressLinesCheck(strList.get(1))) {
								name59 = strList.get(1);
							}
							if(!(strList.get(2) == null)  && getNameAndAddressLinesCheck(strList.get(2))) {
								address1_59 = strList.get(2);
							}
							if(!(strList.get(3) == null)  && getNameAndAddressLinesCheck(strList.get(3))) {
								address2_59 = strList.get(3);
							}

						}
						else if(strList.size()==5) {
							if(getNameAndAddressLinesCheck(strList.get(1))) {
								name59 = strList.get(1);
							}
							if(!(strList.get(2) == null)  && getNameAndAddressLinesCheck(strList.get(2))) {
								address1_59 = strList.get(2);
							}
							if(!(strList.get(3) == null)  && getNameAndAddressLinesCheck(strList.get(3))) {
								address2_59 = strList.get(3);
							}
							if(!(strList.get(4) == null)  && getNameAndAddressLinesCheck(strList.get(4))) {
								address3_59 = strList.get(4);
							}
						}
						System.out.println("------Field 59 Name : " + name59);
						System.out.println("-----Field 59 Addr1 : " + address1_59);
						System.out.println("-----Field 59 Addr2 : " + address2_59);
						System.out.println("------Field 59 Addr3 : " + address3_59);
					}		



				}
			}
			//59 Validation ends here
			//59A Validation starts here

			if(!(sb4.getFieldByName("59A") == null)) {	
				if(field59A.startsWith("/")) {
					Scanner scanner = new Scanner(field59A);
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						strList.add(line);
					}
					scanner.close();
					if(getAccountNoCheck(strList.get(0).substring(1, strList.get(0).length()))) {
						accno_59A = strList.get(0).substring(1, strList.get(0).length() );
						System.out.println("Field 59A:account number="+accno_59A);

					}

					else {
						System.out.println("Valid Account Number not found");
					}


					if (strList.size() > 1 && strList.get(1) != null ) {
						if (getBicCompare(strList.get(1))) {
							bic_59A = strList.get(1);
							System.out.println("Field 59A:BIC="+bic_59A);
						}
						else {
							System.out.println("field 59A has an Account Number but no valid BIC - F59A is invalid as it should have a BIC");
						}
					}
					else{
						System.out.println("2nd line is empty and doesn't have a BIC - F59A is invalid as it should have a BIC");
					}

				}

				else {
					Scanner scanner = new Scanner(field59A);
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						strList.add(line);
					}
					scanner.close();
					if(getBicCompare(strList.get(0))) {
						bic_59A = strList.get(0);
						System.out.println("****Field 59A:BIC="+bic_59A);
					}
					else{
						System.out.println("****"+strList.get(0)+" is not a valid BIC - F59A is invalid");

					}
				}		
			}

			//59A Validation ends here

			//59F Validation starts here
			//	strList = new ArrayList<String>();


			if(!(sb4.getFieldByName("59F") == null)) {
				if(field59F.startsWith("/")) {
					Scanner scanner = new Scanner(field59F);
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						strList.add(line);
					}
					scanner.close();
					//	System.out.println("Field 59F:strList="+strList + ",size of strList: "+strList.size());

					if(getAccountNoCheck(strList.get(0).substring(1, strList.get(0).length()))) {
						accno_59F = strList.get(0).substring(1, strList.get(0).length() );
						System.out.println("Field 59F:account number="+accno_59F);

					}

					else {
						System.out.println("field 59F: Valid Account Number not found");
					}

				}
			}


			if(firstInCreditChain() ==null) {
				System.out.println("****firstInCreditChain() NOT FOUND:*******");
				return;
			}

			//59F Validation ends here


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void initCorrespondent() {
		//System.out.println("***********Inside InitHashTab method************");
		correspMap = new Hashtable<String, String>();
		correspMap.put("BOFAUS3NXXX", "CHASUS33XXX");
		correspMap.put("MIDLGB22XXX", "BARCGB22XXX");//Aviroop
		correspMap.put("DBSBHKHHXXX", "HSBCHKHHXXX");
		correspMap.put("MRMDUS33XXX", "CHASUS33XXX");
	}

	public static boolean checkCorrespondent() {
		initCorrespondent();
		for(Map.Entry<String,String> entry: correspMap.entrySet()) {
			if(entry.getKey().equals(firstInChain)) {//Aviroop
				ssiAgent = entry.getValue();//Aviroop
				return true;			
			}
		}
		return false;
	}


	public static void initCurrCorrespondent() {
		//System.out.println("***********Inside InitHashTab method************");
		currCorrespMap = new Hashtable<String, String>();
		currCorrespMap.put("USD","CHASUS33XXX");
		currCorrespMap.put("GBP","BARCGB22XXX");	
	}

	public static boolean checkCurrCorrespondent() {
		initCurrCorrespondent();
		for(Map.Entry<String,String> entry: currCorrespMap.entrySet()) {
			if(entry.getKey().equals(field32A.substring(6, 9))) {//Aviroop
				currCorr = entry.getValue();//Aviroop
				return true;			
			}
		}
		return false;
	}


	public static void initCountryCorrespondent() {
		//System.out.println("***********Inside InitHashTab method************");
		countryCorrespMap = new Hashtable<String, String>();
		countryCorrespMap.put("US","CHASUS33XXX");
		countryCorrespMap.put("GB","BARCGB22XXX");//Aviroop
	}

	public static boolean checkCountryCorrespondent() {
		initCountryCorrespondent();
		for(Map.Entry<String,String> entry: countryCorrespMap.entrySet()) {
			if(entry.getKey().equals(firstInChain.substring(4,6))) {//Aviroop
				ctryCorr = entry.getValue();//Aviroop
				return true;			
			}
		}
		return false;
	}

	//Below method is meant to put key as NCC and Value as BIC
	public static void initAltRouting() {
		//System.out.println("***********Inside InitHashTab method************");
		routeMap = new Hashtable<String, String>();
		routeMap.put("NORMALALTROUT", "YES");	
	}

	public static boolean checkAltRouting() {
		initAltRouting();
		for(Map.Entry<String,String> entry: routeMap.entrySet()) {
			if(entry.getKey().equals("NORMALALTROUT")) {
				if(entry.getValue() == "YES") { //CORRECTION - Added this condition
				return true;			
				}
			}
		}
		return false;
	}

	//Below method is meant to do RMA Check
	public static void initRMA() {
		//	System.out.println("***********Inside initRMA method************");
		RMAMap = new Hashtable<String, String>();
		RMAMap.put("CHASUS33XXX", "Y");
		//System.out.println("***********size of datamap:************"+ dataMap.size());

	}

	public static boolean checkRMA(String firstInCheckInput) {
		initRMA();
		for(Map.Entry<String,String> entry: RMAMap.entrySet()) {
			if(entry.getKey().equals(firstInCheckInput)) {
				return true;			
			}
		}
		return false;
	}


	//Below method is meant to put key as NCC and Value as BIC
	public static void initHashTab() {
		//System.out.println("***********Inside InitHashTab method************");
		dataMap = new Hashtable<String, String>();
		dataMap.put("AU123456", "CHASAU3MXXX");
		//System.out.println("***********size of datamap:************"+ dataMap.size());



		//return dataMap;

	}	

	public static String firstInCreditChain() {
		initHashTab();
		//	System.out.println("***********size of datamap inside firstInCreditChain:************"+ dataMap.size());
		boolean rmaFound = false;
		//String firstInChainNcc = null;
		if(bic_56A !=null ) {
			firstInChain = bic_56A;
			System.out.println("firstInChain-bic_56A: "+firstInChain);
			//RMA Check
			if(checkRMA(firstInChain)){
				rmaFound = true;
				System.out.println("*******RMA Found for bic56A:" + firstInChain);

			}

		}
		else if(nccCode_56A !=null && nccType_56A !=null) {
			firstInChainNcc = nccType_56A+nccCode_56A;
			for(Map.Entry<String,String> entry: dataMap.entrySet()) {
				if(entry.getKey().equals(firstInChainNcc)) {
					firstInChain = entry.getValue();

				}
			}
			if(checkRMA(firstInChain)){
				rmaFound = true;
				System.out.println("*******RMA Found for ncc56A:" + firstInChain);

			}
			System.out.println("firstInChain-bic based on ncc56A: "+firstInChain);
		}
		else if(nccCode_56C !=null && nccType_56C !=null) {
			firstInChainNcc = nccType_56C+nccCode_56C;
			//	System.out.println("*****firstInChain-bic based on ncc56C: "+firstInChainNcc);
			for(Map.Entry<String,String> entry: dataMap.entrySet()) {
				//		System.out.println("*****firstInChain-bic based on ncc56C entryKey:"+ entry.getKey());
				if(entry.getKey().equals(firstInChainNcc)) {
					firstInChain = entry.getValue();
				}
			}
			if(checkRMA(firstInChain)){
				rmaFound = true;
				System.out.println("*******RMA Found for ncc56C:" + firstInChain);

			}
			System.out.println("firstInChain-bic based on ncc56C: "+firstInChain);
		}
		else if(nccCode_56D !=null && nccType_56D !=null) {
			firstInChainNcc = nccType_56D+nccCode_56D;
			for(Map.Entry<String,String> entry: dataMap.entrySet()) {
				if(entry.getKey().equals(firstInChainNcc)) {
					firstInChain = entry.getValue();
				}
			}
			if(checkRMA(firstInChain)){
				rmaFound = true;
				System.out.println("*******RMA Found for ncc56D:" + firstInChain);

			}
			System.out.println("firstInChain-bic based on ncc56D: "+firstInChain);
		}	
		else if (bic_57A !=null ){
			firstInChain = bic_57A;		
			System.out.println("firstInChain-bic_57A: "+firstInChain);
			if(checkRMA(firstInChain)){
				rmaFound = true;
				System.out.println("*******RMA Found for bic57A:" + firstInChain);

			}
		}
		else if(nccCode_57A !=null && nccType_57A !=null) {
			firstInChainNcc = nccType_57A+nccCode_57A;
			for(Map.Entry<String,String> entry: dataMap.entrySet()) {
				if(entry.getKey().equals(firstInChainNcc)) {
					firstInChain = entry.getValue();
				}
			}
			if(checkRMA(firstInChain)){
				rmaFound = true;
				System.out.println("*******RMA Found for ncc57A:" + firstInChain);

			}
			System.out.println("firstInChain-bic based on ncc57A: "+firstInChain);
		}
		else if(nccCode_57C !=null && nccType_57C !=null) {
			firstInChainNcc = nccType_57C+nccCode_57C;
			for(Map.Entry<String,String> entry: dataMap.entrySet()) {
				if(entry.getKey().equals(firstInChainNcc)) {
					firstInChain = entry.getValue();
				}
			}
			if(checkRMA(firstInChain)){
				rmaFound = true;
				System.out.println("*******RMA Found for ncc57C:" + firstInChain);

			}
			System.out.println("firstInChain-bic based on ncc57C: "+firstInChain);
		}
		else if(nccCode_57D !=null && nccType_57D !=null) {
			firstInChainNcc = nccType_57D+nccCode_57D;
			for(Map.Entry<String,String> entry: dataMap.entrySet()) {
				if(entry.getKey().equals(firstInChainNcc)) {
					firstInChain = entry.getValue();
				}
			}if(checkRMA(firstInChain)){
				rmaFound = true;
				System.out.println("*******RMA Found for ncc57D:" + firstInChain);

			}

			System.out.println("firstInChain-bic based on ncc57D: "+firstInChain);
		}	
		else if(accno_59 !=null   ) {
			firstInChain = accno_59;
			System.out.println("firstInChain-accno_59: "+firstInChain);
			if(checkRMA(firstInChain)){
				rmaFound = true;
				System.out.println("*******RMA Found for accno59:" + firstInChain);

			}
			System.out.println("*******F59 Acc no is the first in chain and hence should be settled in Banks's Book");//Aviroop
			isFisrtInCreditChainAccNo=true;//Aviroop

		}
		else if(accno_59A !=null   ) {
			firstInChain = accno_59A;
			System.out.println("firstInChain-accno_59A: "+firstInChain);
			if(checkRMA(firstInChain)){
				rmaFound = true;
				System.out.println("*******RMA Found for accno59A:" + firstInChain);

			}
			System.out.println("*******F59A Acc no is the first in chain and hence should be settled in Banks's Book");//Aviroop
			isFisrtInCreditChainAccNo=true;//Aviroop
		}
		else if(accno_59F !=null   ) {
			firstInChain = accno_59F;
			System.out.println("firstInChain-accno_59F: "+firstInChain);
			if(checkRMA(firstInChain)){
				rmaFound = true;
				System.out.println("*******RMA Found for accno59F:" + firstInChain);

			}
			System.out.println("*******F59F Acc no is the first in chain and hence should be settled in Banks's Book");//Aviroop
			isFisrtInCreditChainAccNo=true;
		}

		if(rmaFound == false && isFisrtInCreditChainAccNo == false) {
			
			if(checkAltRouting()) {
				System.out.println("******RMA not found for First in Credit Chain, hence going for alternate routing****** ");
				if(checkCorrespondent()) {
					System.out.println("******SSI Agent Found : "+ ssiAgent +" ,Generate Outward MT103****** ");
					firstAgentInChain = ssiAgent;
					System.out.println("******MT103 Outward*******"+getMT103Output());
				}
				else if(checkCurrCorrespondent()) {
					System.out.println("******Currency Correspondent Found : "+ currCorr +" ,Generate Outward MT103****** ");
					firstAgentInChain = currCorr;
					System.out.println("******MT103 Outward*******"+getMT103Output());
				}
				else if(checkCountryCorrespondent()) {
					System.out.println("******Country Correspondent Found : "+ ctryCorr +" ,Generate Outward MT103****** ");
					firstAgentInChain = ctryCorr;
					System.out.println("******MT103 Outward*******"+getMT103Output());

				}
				else {
					System.out.println("******RMA NOT FOUND, SSI Agent not found, currency as well as country correspondent NOT FOUND,Message need to go to REPAIR ****** ");
				}		
			}
			else {
				System.out.println("******RMA NOT FOUND, ALT ROUTING NOT FOUND,Message need to go to REPAIR ****** ");

			}

		}
		return firstInChain;
	}

	public static String getMT103Output() {
		String MT103Msg = convertMT103FromSwiftMsg();
		return MT103Msg;
	}
	
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	public static String convertMT103FromSwiftMsg() {
		final IConversionService srv = new ConversionService() ;
		final SwiftMessage msg = generateOutwardMT103();
		return srv.getFIN(msg);
	
		//return getBase64EncodedString(srv.getFIN(msg)).getBytes();

	}

	//public static String getBase64EncodedString(final String swiftMsg) {
	//	return Base64.encodeBase64String(swiftMsg.getBytes());
	//	}


	public static SwiftMessage generateOutwardMT103() { 
		BIC _bic = new BIC("ANZBAU3MAXXX");
		SwiftMessage swiftMessage = new SwiftMessage();

		//Block1 Population
		SwiftBlock1 sblock1 = new SwiftBlock1();

		sblock1.setApplicationId(AppId);
		sblock1.setServiceId(ServiceId);
		sblock1.setLogicalTerminal(_bic);
		sblock1.setSessionNumber("0001");
		sblock1.setSequenceNumber(String.valueOf(generateSeqNum()));
		swiftMessage.setBlock1(sblock1);

		//Block2 Population
		SwiftBlock2Input sb2i = new SwiftBlock2Input();
		sb2i.setMessageType("103");
		sb2i.setReceiverAddress(firstAgentInChain.substring(0, 8)+"X"+firstAgentInChain.substring(8));
		swiftMessage.setBlock2(sb2i);


		//Block3 Population
		swiftMessage.setBlock3(new SwiftBlock3());
		swiftMessage.getBlock3().append(new Tag("108",tag20));
		swiftMessage.getBlock3().append(new Tag("121",generateUUID()));//Aviroop - UETR logic to be written


		//Block4 Population
		SwiftBlock4 sblock4 = new SwiftBlock4();
		sblock4.addTags(retrieveBlock4Data());
		swiftMessage.setBlock4(sblock4);
		
		//Block5 Population
		

		
		return swiftMessage;

	}


	private static List<Tag> retrieveBlock4Data(){
		final String DELIMITER ="\r\n";
		final StringBuffer sb = new StringBuffer();
		final List<Tag> listTags = new ArrayList<Tag>();
		listTags.add(new Tag("20",tag20));
		listTags.add(new Tag("23B",tag23B));
		listTags.add(new Tag("32A",field32A));		

		if(field50A !=null) {
			listTags.add(new Tag("50A",field50A_50F_50K));
		}
		else if (field50F !=null) {
			listTags.add(new Tag("50F",field50A_50F_50K));
		}
		else if (field50K !=null) {
			listTags.add(new Tag("50K",field50A_50F_50K));
		}
		
		if(bic_57A != null && nccCode_57A != null && nccType_57A != null)
		{
			listTags.add(new Tag("57A",field57A));	
		}
		else if(firstInChainNcc !=null) {
		listTags.add(new Tag("57A","//"+firstInChainNcc +"\n"+firstInChain));
		//listTags.add(new Tag("57A",firstInChain));
		}
		else 
		{
			listTags.add(new Tag("57A",firstInChain));
		}		
		
	

		if(field59 != null ) {
			listTags.add(new Tag("59",field59));
		}		
		else if(field59A != null)
		{
			listTags.add(new Tag("59A",field59A));
		}
		else if(field59F != null)
		{
			listTags.add(new Tag("59F",field59F));
		}
		listTags.add(new Tag("70",field70));
		listTags.add(new Tag("71A",field71A));
		listTags.add(new Tag("72",field72));
		return listTags;


	}


	private static int generateSeqNum() {
		return 100000 + generator.nextInt(900000);
	}

	public static  String readPaymentsFile() {

		String contents = null;
		try {
			contents = new String(Files.readAllBytes(Paths.get("/Users/blockchainpoc/ANZPOC/ANZPE/Payments/sample103_1.txt")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		return contents;
	}

	public static boolean  getBicCompare(String bic)
	{
		Pattern p = Pattern.compile("^([a-zA-Z]){6}([0-9a-zA-Z]{2})([0-9a-zA-Z]{3})?$");
		Matcher m = p.matcher(bic);
		if(m.find())
			return true;
		else 
			return false;
	}

	public static boolean  getATNCC(String strInput)
	{
		Pattern p = Pattern.compile("([AT]){2}([0-9]{5})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getAUNCC(String strInput)
	{
		Pattern p = Pattern.compile("([AU]){2}([0-9]{6})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getBLNCC(String strInput)
	{
		Pattern p = Pattern.compile("([BL]){2}([0-9]{8})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getCCNCC(String strInput)
	{
		Pattern p = Pattern.compile("([CC]){2}([0-9]{9})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getCNNCC(String strInput)
	{
		Pattern p = Pattern.compile("([CN]){2}([0-9]{12,14})?$"); //nEED TO CHECK THE SYNTAX
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getESNCC(String strInput)
	{
		Pattern p = Pattern.compile("([ES]){2}([0-9]{8,9})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getFWNCC(String strInput)
	{
		Pattern p = Pattern.compile("([FW]){2}([0-9]{9})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getGRNCC(String strInput)
	{
		Pattern p = Pattern.compile("([GR]){2}([0-9]{7})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getHKNCC(String strInput)
	{
		Pattern p = Pattern.compile("([HK]){2}([0-9]{3})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getIENCC(String strInput)
	{
		Pattern p = Pattern.compile("([IE]){2}([0-9]{6})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getINNCC(String strInput)
	{
		Pattern p = Pattern.compile("([IN]){2}([0-9A-Za-z]{11})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getITNCC(String strInput)
	{
		Pattern p = Pattern.compile("([IT]){2}([0-9]{10})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getNZNCC(String strInput)
	{
		Pattern p = Pattern.compile("([NZ]){2}([0-9]{6})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getPLNCC(String strInput)
	{
		Pattern p = Pattern.compile("([PL]){2}([0-9]{8})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getPTNCC(String strInput)
	{
		Pattern p = Pattern.compile("([PT]){2}([0-9]{8})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}

	public static boolean  getRTNCC(String strInput)
	{
		Pattern p = Pattern.compile("([RT]){2}([0-9]{7})?$"); //To check with Raja
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getSCNCC(String strInput)
	{
		Pattern p = Pattern.compile("([SC]){2}([0-9]{6})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getZANCC(String strInput)
	{
		Pattern p = Pattern.compile("([ZA]){2}([0-9]{6})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getCHNCC(String strInput)
	{
		Pattern p = Pattern.compile("([CH]){2}([0-9]{6})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getCPNCC(String strInput)
	{
		Pattern p = Pattern.compile("([CP]){2}([0-9]{4})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getRUNCC(String strInput)
	{
		Pattern p = Pattern.compile("([RU]){2}([0-9]{9})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}
	public static boolean  getSWNCC(String strInput)
	{

		Pattern p = Pattern.compile("^([SW]){2}([0-9]{3,6})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}

	public static boolean getLocationCheck(String strInput) {
		Pattern p = Pattern.compile("(^[0-9a-zA-Z]{1,35})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}


	public static boolean getAccountNoCheck(String strInput) {
		Pattern p = Pattern.compile("^([0-9a-zA-Z]{1,34})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}

	public static boolean getNameAndAddressLinesCheck(String strInput) {
		Pattern p = Pattern.compile("^([0-9a-zA-Z]{1,35})?$");
		Matcher m = p.matcher(strInput);
		if(m.find())
			return true;
		else 
			return false;
	}

}
