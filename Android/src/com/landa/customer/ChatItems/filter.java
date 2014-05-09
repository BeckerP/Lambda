package com.landa.customer.ChatItems;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class filter {

	public static String filterwords(String string){
		String[] words = {"fuck", "shit", "cunt", "dick", "faggot",
				"nigger", "asshole", "bitch", "whore", "fag","gay", 
				"dong", "wang", "pussy", "tits", "meekraw"};
		
		for(int i=0; i<words.length; i++){
			if(string.toLowerCase().contains(words[i])){
				string = "Censored";
				return string;
			}
		}
		return string;
	}
}
