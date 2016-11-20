/*
Program Name:  Multi-Document Text Editor
Developer:  Kevin Anthony John Blighe
Date:  19th December 2002
Course:  Ft 228
Year:  Three
Subject:  Windows Programming
*/

import java.awt.event.* ;
import javax.swing.* ;

/**
	KBWindowAdapter class can be used with any class in JAVA and allows for the
	manipulation of certain window events, such as closing an application,
	or minimising an application.
*/

public class KBWindowAdapter extends WindowAdapter
{
	private KBEditorPane parentFrame ;

/**
	Set the parent frame for this class
*/
	public void setParentFrame( KBEditorPane p )
	{
		//Purpose:	Set the parent frame for this class
		//Parameters:	KBEditorPane object
		//Returns:	

		parentFrame = p ;
	}

/**
	Provides functionality for when the application is opened
*/
	public void windowOpened(WindowEvent WE){}

/**
	Provides functionality for when the application is closed
*/
	public void windowClosing( WindowEvent WE )
	{
		//Purpose:	Provides functionality for when the application is closing
		//Parameters:	a 'WindowEvent' object
		//Returns:	

		parentFrame.RemoveAll() ;

		System.exit(0) ;
	}

/**
	Provides functionality for when the application is closed
*/
	public void windowClosed(WindowEvent WE){}

/**
	Provides functionality for when the application is iconified
*/
	public void windowIconified(WindowEvent WE){}

/**
	Provides functionality for when the application is deiconified
*/
	public void windowDeiconified(WindowEvent WE){}

/**
	Provides functionality for when the application is activated
*/
	public void windowActivated(WindowEvent WE){}

/**
	Provides functionality for when the application is deavtivated
*/
	public void windowDeactivated(WindowEvent WE){}

/**
	Provides functionality for when the state of the application is changed
*/
	public void windowStateChanged(WindowEvent WE){}

/**
	Provides functionality for when the application gains focus
*/
	public void windowGainedFocus(WindowEvent WE){}

/**
	Provides functionality for when the application loses focus
*/
	public void windowLostFocus(WindowEvent WE){} 
}