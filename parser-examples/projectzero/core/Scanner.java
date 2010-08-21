/*
 * ============================================================================
 * Licensed Materials - Property of IBM
 * Project  Zero
 *
 * (C) Copyright IBM Corp. 2007  All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 * ============================================================================
 * Copyright (c) 1999 - 2006 The PHP Group. All rights reserved.
 * ============================================================================
 */
package com.ibm.p8.engine.parser.core;


//////////////////////////////////////////////////////////////////
//                                                              //
//                 IMPORTANT WARNING                            //
//                                                              //
//   This file was auto-generated by the LPG Eclipse Tooling.   //
//       Do not edit this file. It will be overwritten.         //
//                                                              //
//   Copy this file to another location and use it from there.  //
//                                                              //
//////////////////////////////////////////////////////////////////

import java.io.IOException;
import java.util.logging.Logger;

import com.ibm.p8.engine.core.FatalError;
import com.ibm.p8.engine.core.RuntimeInterpreter;
import com.ibm.phpj.logging.SAPIComponent;
import com.ibm.phpj.logging.SAPILevel;
import com.ibm.p8.utilities.log.P8LogManager;
import com.ibm.phpj.xapi.ConfigurationService;

/**
 * Scan an input stream, and turn it into a list of tokens.
 *
 */
public class Scanner implements PHPParsersym {
	private static final Logger LOGGER = P8LogManager._instance
	.getLogger(SAPIComponent.Scanner);
	
	protected char nextChar;
	protected RuntimeInterpreter runtime = null;
	protected boolean short_tags = false;
	protected boolean asp_tags = false;
	public LexStream lexStream;
	public CharStream charStream;
	
	public String fileName;
	
	/**
	 * Create a new, empty scanner.
	 */
	public Scanner() {
	}

	public char getChar() {
		return charStream.getChar();
	}
	
	/**
	 * Look ahead in the underlying char stream.
	 * @param offset How far ahead to look from the current position.
	 * @return the char at the specified offset.
	 */
	public char peekChar(int offset) {
		return charStream.peekChar(offset);
	}
				
//////////////////////////////////////////////////////////////////
//                                                              //
//   Copy this file to another location and use it from there.  //
//                                                              //
//////////////////////////////////////////////////////////////////

	
	/**
	 * Move forwards through the char stream until the next non-whitespace
	 * char is found.
	 * @throws IOException if a problem is encountered.
	 */
	public void skip_spaces() throws java.io.IOException {
		while (nextChar != '\uffff' && Character.isWhitespace(nextChar)) {
			nextChar = getChar();
		}
		return;
	}


	/**
	 * Scan the char stream, identify and return the next token.
	 * @return the next token.
	 * @throws java.io.IOException if there is a problem.
	 */
	public Token getNextToken() throws java.io.IOException {
		if (LOGGER.isLoggable(SAPILevel.SEVERE)) {
			LOGGER.log(SAPILevel.SEVERE , "2526");
		}
		throw new FatalError("This method should never be called - use specialized method instead.");
	}

//////////////////////////////////////////////////////////////////
//                                                              //
//                 IMPORTANT WARNING                            //
//                                                              //
//   This file was auto-generated by the LPG Eclipse Tooling.   //
//       Do not edit this file. It will be overwritten.         //
//                                                              //
//   Copy this file to another location and use it from there.  //
//                                                              //
//////////////////////////////////////////////////////////////////
		
	/**
	 * Search in the charstream to see if we can match any of our keywords,
	 * starting at the current index. 
	 * @return A token representing the keyword if we find a match.
	 */
	public Token matchKeywords() {	
		if (LOGGER.isLoggable(SAPILevel.SEVERE)) {
			LOGGER.log(SAPILevel.SEVERE , "2528");
		}
		throw new FatalError("This method should never be called - use specialized method instead.");
	}

	/**
	 * Get a char array containing the entire contents of the char stream.
	 * @return the contents of the char stream.
	 */
	public char[] getContents() {
		if (LOGGER.isLoggable(SAPILevel.SEVERE)) {
			LOGGER.log(SAPILevel.SEVERE , "2529");
		}
		throw new FatalError("This method should never be called - use specialized method instead.");
	}

	/**
	 * Scans a Java string as PHP.
	 *
	 * @param runtime the current PHP runtime.
	 //* @param newToken Token representing this script in the parent.
	 * @param evalString  will be scanned as PHP
	 * @return  the resulting LexStream
	 * @throws java.io.IOException if there is a problem.
	 */
	public LexStream scanString(RuntimeInterpreter runtime, String evalString, String fileName) throws java.io.IOException {	
		charStream = new CharStream(runtime, null);
		charStream.setContents(evalString);
		this.fileName = fileName;
		return processScan();
	}


   /**
     * Place holder for the setting of the end offset handled by PHPScanner
     * @param t
     */
	public void setEndOffset(Token t) {
		if (LOGGER.isLoggable(SAPILevel.SEVERE)) {
			LOGGER.log(SAPILevel.SEVERE , "2526");
		}
		throw new FatalError("This method should never be called - use specialized method instead.");
	}
	
	/**
	 * Scan the string contained in the underlying char stream,
	 * and add the resulting tokens to the lexStream.
	 * @return the lexStream representing the scanned string.
	 * @throws java.io.IOException if something goes wrong.
	 */
	private LexStream processScan() throws java.io.IOException {
		lexStream = new LexStream(charStream);
		nextChar = getChar();
		for (; nextChar != '\uffff';) {
			Token nextToken = getNextToken();
			
			if (nextToken != null) {
				//System.out.println("TOKEN" + nextToken.getKind());
				lexStream.addToken(nextToken);
				if (nextToken.getKind() == T_HALT_COMPILER){
					// we have halted - there must be two
					// brackets and a ; for a complete line, otherwise
					// its s syntax error.
					for (int i = 0 ; i < 3; i++ ){
						nextToken = getNextToken();
						if (nextChar == '\uffff') break;
						lexStream.addToken(nextToken);
					}
					setEndOffset(nextToken);
					
					break;
				}
			}
		}
		lexStream.addEofToken($eof);
		return lexStream;

	}
	
	/**
	 * Scan the string represented by the supplied byte array.
	 * @param runtime the PHP Runtime.
	 * @param script the byte array representing the script to be scanned.
	 * @param scriptFileName the name of the file that the script was loaded from or null.
	 * @return the lexStream representing the scanned string.
	 * @throws java.io.IOException if something goes wrong.
	 */
	public LexStream scan(RuntimeInterpreter runtime, byte[] script, String scriptFileName) throws java.io.IOException {
		//
		// Do not use token indexed at location 0.
		//
		this.runtime = runtime;
		if (runtime != null) {
		    ConfigurationService cs = runtime.getConfigurationService();
		    if (cs != null) {
		    	Boolean st = cs.getBooleanOverride("short_open_tag");
		    	if (st == null) {
		    		this.short_tags = false;
		    	} else {
		    		this.short_tags = st.booleanValue();
		    	}
		    	Boolean at = cs.getBooleanOverride("asp_tags");
		    	if (at == null) {
		    		this.asp_tags = false;
		    	} else {
		    		this.asp_tags = at.booleanValue();
		    	}
		    }
		}
		this.fileName = scriptFileName;
		charStream = new CharStream(runtime, scriptFileName);
		charStream.load(script);
		return processScan();
	}
	
//////////////////////////////////////////////////////////////////
//                                                              //
//                 IMPORTANT WARNING                            //
//                                                              //
//   This file was auto-generated by the LPG Eclipse Tooling.   //
//       Do not edit this file. It will be overwritten.         //
//                                                              //
//   Copy this file to another location and use it from there.  //
//                                                              //
//////////////////////////////////////////////////////////////////





	public static char KEYWORDS[][] = {
		/*0 */	new char[]{ '_', '_', 'h', 'a', 'l', 't', '_', 'c', 'o', 'm', 'p', 'i', 'l', 'e', 'r',  } ,
		/*1 */	new char[]{ '_', '_', 'f', 'u', 'n', 'c', 't', 'i', 'o', 'n', '_', '_',  } ,
		/*2 */	new char[]{ 'r', 'e', 'q', 'u', 'i', 'r', 'e', '_', 'o', 'n', 'c', 'e',  } ,
		/*3 */	new char[]{ 'i', 'n', 'c', 'l', 'u', 'd', 'e', '_', 'o', 'n', 'c', 'e',  } ,
		/*4 */	new char[]{ 'i', 'n', 's', 't', 'a', 'n', 'c', 'e', 'o', 'f',  } ,
		/*5 */	new char[]{ 'i', 'm', 'p', 'l', 'e', 'm', 'e', 'n', 't', 's',  } ,
		/*6 */	new char[]{ 'e', 'n', 'd', 'f', 'o', 'r', 'e', 'a', 'c', 'h',  } ,
		/*7 */	new char[]{ 'e', 'n', 'd', 'd', 'e', 'c', 'l', 'a', 'r', 'e',  } ,
		/*8 */	new char[]{ '_', '_', 'm', 'e', 't', 'h', 'o', 'd', '_', '_',  } ,
		/*9 */	new char[]{ '_', '_', 'c', 'l', 'a', 's', 's', '_', '_',  } ,
		/*10 */	new char[]{ 'i', 'n', 't', 'e', 'r', 'f', 'a', 'c', 'e',  } ,
		/*11 */	new char[]{ 'e', 'n', 'd', 's', 'w', 'i', 't', 'c', 'h',  } ,
		/*12 */	new char[]{ 'p', 'r', 'o', 't', 'e', 'c', 't', 'e', 'd',  } ,
		/*13 */	new char[]{ 'a', 'b', 's', 't', 'r', 'a', 'c', 't',  } ,
		/*14 */	new char[]{ '_', '_', 'l', 'i', 'n', 'e', '_', '_',  } ,
		/*15 */	new char[]{ '_', '_', 'f', 'i', 'l', 'e', '_', '_',  } ,
		/*16 */	new char[]{ '(', 'o', 'b', 'j', 'e', 'c', 't', ')',  } ,
		/*17 */	new char[]{ '(', 'd', 'o', 'u', 'b', 'l', 'e', ')',  } ,
		/*18 */	new char[]{ 'e', 'n', 'd', 'w', 'h', 'i', 'l', 'e',  } ,
		/*19 */	new char[]{ 'f', 'u', 'n', 'c', 't', 'i', 'o', 'n',  } ,
		/*20 */	new char[]{ '(', 's', 't', 'r', 'i', 'n', 'g', ')',  } ,
		/*21 */	new char[]{ 'c', 'o', 'n', 't', 'i', 'n', 'u', 'e',  } ,
		/*22 */	new char[]{ '(', 'u', 'n', 's', 'e', 't', ')',  } ,
		/*23 */	new char[]{ 'e', 'x', 't', 'e', 'n', 'd', 's',  } ,
		/*24 */	new char[]{ 'p', 'r', 'i', 'v', 'a', 't', 'e',  } ,
		/*25 */	new char[]{ 'f', 'o', 'r', 'e', 'a', 'c', 'h',  } ,
		/*26 */	new char[]{ '(', 'f', 'l', 'o', 'a', 't', ')',  } ,
		/*27 */	new char[]{ 'd', 'e', 'f', 'a', 'u', 'l', 't',  } ,
		/*28 */	new char[]{ '(', 'a', 'r', 'r', 'a', 'y', ')',  } ,
		/*29 */	new char[]{ 'r', 'e', 'q', 'u', 'i', 'r', 'e',  } ,
		/*30 */	new char[]{ 'd', 'e', 'c', 'l', 'a', 'r', 'e',  } ,
		/*31 */	new char[]{ 'i', 'n', 'c', 'l', 'u', 'd', 'e',  } ,
		/*32 */	new char[]{ 'r', 'e', 't', 'u', 'r', 'n',  } ,
		/*33 */	new char[]{ 'e', 'n', 'd', 'f', 'o', 'r',  } ,
		/*34 */	new char[]{ 'e', 'l', 's', 'e', 'i', 'f',  } ,
		/*35 */	new char[]{ 'g', 'l', 'o', 'b', 'a', 'l',  } ,
		/*36 */	new char[]{ 's', 't', 'a', 't', 'i', 'c',  } ,
		/*37 */	new char[]{ 's', 'w', 'i', 't', 'c', 'h',  } ,
		/*38 */	new char[]{ 'p', 'u', 'b', 'l', 'i', 'c',  } ,
		/*39 */	new char[]{ '(', 'b', 'o', 'o', 'l', ')',  } ,
		/*40 */	new char[]{ 'c', 'l', 'a', 's', 's',  } ,
		/*41 */	new char[]{ 'c', 'o', 'n', 's', 't',  } ,
		/*42 */	new char[]{ 'p', 'r', 'i', 'n', 't',  } ,
		/*43 */	new char[]{ 'u', 'n', 's', 'e', 't',  } ,
		/*44 */	new char[]{ '(', 'i', 'n', 't', ')',  } ,
		/*45 */	new char[]{ 'b', 'r', 'e', 'a', 'k',  } ,
		/*46 */	new char[]{ 'e', 'm', 'p', 't', 'y',  } ,
		/*47 */	new char[]{ 'e', 'n', 'd', 'i', 'f',  } ,
		/*48 */	new char[]{ 'c', 'l', 'o', 'n', 'e',  } ,
		/*49 */	new char[]{ 'a', 'r', 'r', 'a', 'y',  } ,
		/*50 */	new char[]{ 'f', 'i', 'n', 'a', 'l',  } ,
		/*51 */	new char[]{ 'w', 'h', 'i', 'l', 'e',  } ,
		/*52 */	new char[]{ 't', 'h', 'r', 'o', 'w',  } ,
		/*53 */	new char[]{ 'i', 's', 's', 'e', 't',  } ,
		/*54 */	new char[]{ 'c', 'a', 't', 'c', 'h',  } ,
		/*55 */	new char[]{ 'e', 'l', 's', 'e',  } ,
		/*56 */	new char[]{ 'c', 'a', 's', 'e',  } ,
		/*57 */	new char[]{ 'e', 'c', 'h', 'o',  } ,
		/*58 */	new char[]{ 'l', 'i', 's', 't',  } ,
		/*59 */	new char[]{ 'e', 'v', 'a', 'l',  } ,
		/*60 */	new char[]{ 'e', 'x', 'i', 't',  } ,
		/*61 */	new char[]{ 't', 'r', 'y',  } ,
		/*62 */	new char[]{ 'x', 'o', 'r',  } ,
		/*63 */	new char[]{ 'f', 'o', 'r',  } ,
		/*64 */	new char[]{ 'v', 'a', 'r',  } ,
		/*65 */	new char[]{ 'd', 'i', 'e',  } ,
		/*66 */	new char[]{ '<', '<', '=',  } ,
		/*67 */	new char[]{ '=', '=', '=',  } ,
		/*68 */	new char[]{ 'u', 's', 'e',  } ,
		/*69 */	new char[]{ '!', '=', '=',  } ,
		/*70 */	new char[]{ 'a', 'n', 'd',  } ,
		/*71 */	new char[]{ '>', '>', '=',  } ,
		/*72 */	new char[]{ 'n', 'e', 'w',  } ,
		/*73 */	new char[]{ '$', '{',  } ,
		/*74 */	new char[]{ '*', '=',  } ,
		/*75 */	new char[]{ '%', '=',  } ,
		/*76 */	new char[]{ '|', '|',  } ,
		/*77 */	new char[]{ '!', '=',  } ,
		/*78 */	new char[]{ '<', '=',  } ,
		/*79 */	new char[]{ '-', '=',  } ,
		/*80 */	new char[]{ 'a', 's',  } ,
		/*81 */	new char[]{ '|', '=',  } ,
		/*82 */	new char[]{ '^', '=',  } ,
		/*83 */	new char[]{ '\\','"',  } ,
		/*84 */	new char[]{ 'i', 'f',  } ,
		/*85 */	new char[]{ '/', '=',  } ,
		/*86 */	new char[]{ '+', '=',  } ,
		/*87 */	new char[]{ '&', '=',  } ,
		/*88 */	new char[]{ '=', '>',  } ,
		/*89 */	new char[]{ '+', '+',  } ,
		/*90 */	new char[]{ '<', '<',  } ,
		/*91 */	new char[]{ '>', '>',  } ,
		/*92 */	new char[]{ '-', '>',  } ,
		/*93 */	new char[]{ '&', '&',  } ,
		/*94 */	new char[]{ '.', '=',  } ,
		/*95 */	new char[]{ '-', '-',  } ,
		/*96 */	new char[]{ ':', ':',  } ,
		/*97 */	new char[]{ 'o', 'r',  } ,
		/*98 */	new char[]{ '=', '=',  } ,
		/*99 */	new char[]{ '<', '>',  } ,
		/*100 */	new char[]{ 'd', 'o',  } ,
		/*101 */	new char[]{ '>', '=',  } ,
		/*102 */	new char[]{ '\'', } ,
		/*103 */	new char[]{ '>',  } ,
		/*104 */	new char[]{ '{',  } ,
		/*105 */	new char[]{ '.',  } ,
		/*106 */	new char[]{ '%',  } ,
		/*107 */	new char[]{ '^',  } ,
		/*108 */	new char[]{ '$',  } ,
		/*109 */	new char[]{ '*',  } ,
		/*110 */	new char[]{ '@',  } ,
		/*111 */	new char[]{ '~',  } ,
		/*112 */	new char[]{ '?',  } ,
		/*113 */	new char[]{ ';',  } ,
		/*114 */	new char[]{ ')',  } ,
		/*115 */	new char[]{ '/',  } ,
		/*116 */	new char[]{ ',',  } ,
		/*117 */	new char[]{ '-',  } ,
		/*118 */	new char[]{ '|',  } ,
		/*119 */	new char[]{ '`',  } ,
		/*120 */	new char[]{ '&',  } ,
		/*121 */	new char[]{ ']',  } ,
		/*122 */	new char[]{ '!',  } ,
		/*123 */	new char[]{ ':',  } ,
		/*124 */	new char[]{ '(',  } ,
		/*125 */	new char[]{ '+',  } ,
		/*126 */	new char[]{ '=',  } ,
		/*127 */	new char[]{ '}',  } ,
		/*128 */	new char[]{ '[',  } ,
		/*129 */	new char[]{ '<',  } ,
	};
	public static int KEYWORD_VALUES[] = {
		/*0 */	T_HALT_COMPILER,
		/*1 */	T_FUNC_C,
		/*2 */	T_REQUIRE_ONCE,
		/*3 */	T_INCLUDE_ONCE,
		/*4 */	T_INSTANCEOF,
		/*5 */	T_IMPLEMENTS,
		/*6 */	T_ENDFOREACH,
		/*7 */	T_ENDDECLARE,
		/*8 */	T_METHOD_C,
		/*9 */	T_CLASS_C,
		/*10 */	T_INTERFACE,
		/*11 */	T_ENDSWITCH,
		/*12 */	T_PROTECTED,
		/*13 */	T_ABSTRACT,
		/*14 */	T_LINE_C,
		/*15 */	T_FILE_C,
		/*16 */	T_OBJECT_CAST,
		/*17 */	T_DOUBLE_CAST,
		/*18 */	T_ENDWHILE,
		/*19 */	T_FUNCTION,
		/*20 */	T_STRING_CAST,
		/*21 */	T_CONTINUE,
		/*22 */	T_UNSET_CAST,
		/*23 */	T_EXTENDS,
		/*24 */	T_PRIVATE,
		/*25 */	T_FOREACH,
		/*26 */	T_FLOAT_CAST,
		/*27 */	T_DEFAULT,
		/*28 */	T_ARRAY_CAST,
		/*29 */	T_REQUIRE,
		/*30 */	T_DECLARE,
		/*31 */	T_INCLUDE,
		/*32 */	T_RETURN,
		/*33 */	T_ENDFOR,
		/*34 */	T_ELSEIF,
		/*35 */	T_GLOBAL,
		/*36 */	T_STATIC,
		/*37 */	T_SWITCH,
		/*38 */	T_PUBLIC,
		/*39 */	T_BOOL_CAST,
		/*40 */	T_CLASS,
		/*41 */	T_CONST,
		/*42 */	T_PRINT,
		/*43 */	T_UNSET,
		/*44 */	T_INT_CAST,
		/*45 */	T_BREAK,
		/*46 */	T_EMPTY,
		/*47 */	T_ENDIF,
		/*48 */	T_CLONE,
		/*49 */	T_ARRAY,
		/*50 */	T_FINAL,
		/*51 */	T_WHILE,
		/*52 */	T_THROW,
		/*53 */	T_ISSET,
		/*54 */	T_CATCH,
		/*55 */	T_ELSE,
		/*56 */	T_CASE,
		/*57 */	T_ECHO,
		/*58 */	T_LIST,
		/*59 */	T_EVAL,
		/*60 */	T_EXIT,
		/*61 */	T_TRY,
		/*62 */	T_LOGICAL_XOR,
		/*63 */	T_FOR,
		/*64 */	T_VAR,
		/*65 */	T_DIE,
		/*66 */	T_SL_EQUAL,
		/*67 */	T_IS_IDENTICAL,
		/*68 */	T_USE,
		/*69 */	T_IS_NOT_IDENTICAL,
		/*70 */	T_LOGICAL_AND,
		/*71 */	T_SR_EQUAL,
		/*72 */	T_NEW,
		/*73 */	T_DOLLAR_OPEN_CURLY_BRACES,
		/*74 */	T_MUL_EQUAL,
		/*75 */	T_MOD_EQUAL,
		/*76 */	T_BOOLEAN_OR,
		/*77 */	T_SHR_EQUAL,
		/*78 */	T_IS_SMALLER_OR_EQUAL,
		/*79 */	T_MINUS_EQUAL,
		/*80 */	T_AS,
		/*81 */	T_OR_EQUAL,
		/*82 */	T_XOR_EQUAL,
		/*83 */	T_DOUBLE_QUOTE,
		/*84 */	T_IF,
		/*85 */	T_DIV_EQUAL,
		/*86 */	T_PLUS_EQUAL,
		/*87 */	T_AND_EQUAL,
		/*88 */	T_DOUBLE_ARROW,
		/*89 */	T_INC,
		/*90 */	T_SL,
		/*91 */	T_SR,
		/*92 */	T_OBJECT_OPERATOR,
		/*93 */	T_BOOLEAN_AND,
		/*94 */	T_CONCAT_EQUAL,
		/*95 */	T_DEC,
		/*96 */	T_PAAMAYIM_NEKUDOTAYIM,
		/*97 */	T_LOGICAL_OR,
		/*98 */	T_IS_EQUAL,
		/*99 */	T_IS_NOT_EQUAL,
		/*100 */	T_DO,
		/*101 */	T_IS_GREATER_OR_EQUAL,
		/*102 */	T_SINGLE_QUOTE,
		/*103 */	T_GT,
		/*104 */	T_CURLY_OPEN,
		/*105 */	T_PERIOD,
		/*106 */	T_PERCENT,
		/*107 */	T_CARET,
		/*108 */	T_DOLLAR,
		/*109 */	T_ASTERISK,
		/*110 */	T_AT_MARK,
		/*111 */	T_TILDE,
		/*112 */	T_QUESTION,
		/*113 */	T_SEMICOLON,
		/*114 */	T_RPAR,
		/*115 */	T_SLASH,
		/*116 */	T_COMMA,
		/*117 */	T_MINUS,
		/*118 */	T_VERTICAL_LINE,
		/*119 */	T_BACK_QUOTE,
		/*120 */	T_AMPERSAND,
		/*121 */	T_BRACKET_CLOSE,
		/*122 */	T_EXCLAMATION,
		/*123 */	T_COLON,
		/*124 */	T_LPAR,
		/*125 */	T_PLUS,
		/*126 */	T_EQUAL,
		/*127 */	T_CURLY_CLOSE,
		/*128 */	T_BRACKET_OPEN,
		/*129 */	T_LT,
	};
	public static boolean isKeywordStart(char c) {
		switch(c) {
		case '!':
		case '&':
		case '\'':
		case '$':
		case '%':
		case '*':
		case '+':
		case '(':
		case ')':
		case '.':
		case '/':
		case ',':
		case '-':
		case ';':
		case ':':
		case '?':
		case '>':
		case '=':
		case '<':
		case '@':
		case ']':
		case '\\':
		case '_':
		case '^':
		case '[':
		case 'f':
		case 'g':
		case 'd':
		case 'e':
		case 'b':
		case 'c':
		case '`':
		case 'a':
		case 'n':
		case 'o':
		case 'l':
		case 'i':
		case 'w':
		case 'v':
		case 'u':
		case 't':
		case 's':
		case 'r':
		case 'p':
		case '~':
		case '}':
		case '|':
		case '{':
		case 'x':
			return true;
		default:
			return Character.isJavaIdentifierStart(c);
		}
	}
}
