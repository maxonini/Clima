package com.climattention.server;

import javax.servlet.ServletContext;

public class ServletContextHold {
	
	private static ServletContextHold instance;
	public static ServletContext CONTEXT;
	
	private ServletContextHold(ServletContext cont){
		
		CONTEXT = cont;
	}
	
	public static ServletContextHold getInstance(ServletContext context){
		if(instance==null){
			instance = new ServletContextHold(context);
		}
		return instance;
	}
	

}
