package com.example.biat.InterfacageOGP.Service;

import java.io.InputStream;
import java.io.PrintStream;

public class T24Tools {
	InputStream in;
    PrintStream out;
	public T24Tools(InputStream in, PrintStream out) {
		super();
		this.in = in;
		this.out = out;
	}
	public T24Tools() {
		super();
	}
	
	 public void readUntil(String expectedPattern) {
	     
	            try {
	                //ecrire(expectedPattern);
	                Thread.sleep(250);
	            } catch (Exception exep) {
	                exep.printStackTrace();
	                //tools.Tools.traiterException(tools.Tools.getStackTrace(exep));
	            }
	        }
	      //  pipeThread.streamContent = pipeThread.streamContent.replaceFirst(expectedPattern, "");
	    
	 public void write(String value) {
	        try {
	            out.println(value);
	            out.flush();
	            System.out.println(value);
	            System.gc();
	        } catch (Exception exep) {
	            exep.printStackTrace();
	           // tools.Tools.traiterException(tools.Tools.getStackTrace(exep));
	        }
	    }
	
	 

}
