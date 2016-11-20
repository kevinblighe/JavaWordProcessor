/*
Program Name:  Multi-Document Text Editor
Developer:  Kevin Anthony John Blighe
Date:  19th December 2002
*/

/**
	The Multi-Document Text Editor is a useful application allowing for
	text editing on multiple open documents all under the same program.
	It provides for multiple functionality for editing, opening, and
	saving documents.
*/

public class MultiDocuments
{
	/**
		Creates an application of dimension 1000 * 800
	*/

	public static void main( String [] args )
	{
		//Purpose:	Constructor for the application. Creates an instance of the KBEditorPane
		//Parameters:	String values passed in from the command prompt
		//Returns:	

		//Create an instance of the application
		KBEditorPane myEditor = new KBEditorPane() ;

		//Size the application window to fit the preferred size and layouts of its subcomponents
		myEditor.pack() ;

		//Set the dimension size of the application
		myEditor.setSize( new java.awt.Dimension( 1000,800 )) ;

		//Make the application visible
		myEditor.setVisible(true) ;
	}
}
