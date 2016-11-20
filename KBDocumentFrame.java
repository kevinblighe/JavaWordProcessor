/*
Program Name:  Multi-Document Text Editor
Developer:  Kevin Anthony John Blighe
Date:  19th December 2002
*/

import javax.swing.* ;
import javax.swing.event.* ;
import java.awt.* ;
import java.lang.* ;
import java.io.* ;
import javax.swing.text.* ;

/**
	The KBDocumentFrame is extened from the JInternalFrame class, and implements
	InternalFrameListener and DocumentListener. Used in co-operation with a
	KBEditorPane class, documents can be saved and opened at will.
*/

public class KBDocumentFrame extends JInternalFrame implements InternalFrameListener, DocumentListener
{
	//The parent frame
	private KBEditorPane parentFrame ;

	//Create an editor for the KBDocumentFrame
	private JEditorPane myEditor ;

	//Create a 'Document' object so as to allow us detect changes in the document
	private Document myDocument ;

	//as default, set the document as being not modified
	private boolean MODIFIED = false ;

	//as default, set the document as being not a default document
	private boolean DEFAULTDOC = false ;

	//Holds the file name of the KBDocumentFrame created
	private String FileName ;

	//Holds the file name of the KBDocumentFrame created
	private File FilePath ;



/**
	Constructor for the KBDocumentFrame, it assigns a parent frame for the document,
	creates a JEditorPane to allow for editing, and retrieves file content based on
	a file path passed down to it.
*/	

	public KBDocumentFrame( KBEditorPane p, String Name, File Path )
	{
		//Purpose:	Constructor for the KBDocumentFrame, retrieves the content and displays it to the screen
		//Parameters:	the parent frame, a file name, and a file path
		//Returns:	

		//calls the constructor of the extended class.in this case: JInternalFrame
		super( Name, true, true, true, true) ;

		//assign the parent frame
		parentFrame = p ;

		//assign the file name
		FileName = Name ;

		//assign the file path
		FilePath = Path ;

		//create a random x-coordinate for the document
		int x = (int)(Math.random() * 400) ;

		//create a random y-coordinate for the document
		int y = (int)(Math.random() * 400) ;

		//set the size of the frame
		this.setSize( 400, 400 ) ;

		//set the location
		this.setLocation( x, y ) ;
		
		//create the JEditorPane
		myEditor = new JEditorPane() ;

		//create a JScrollPane for the JEditorPane
		JScrollPane myScrollPane = new JScrollPane( myEditor ) ;

		//add the JScrollPane (incorporating the JEditorPane) to the content of the internal frame
		this.getContentPane().add( myScrollPane, BorderLayout.CENTER ) ;

		try
		{
			//Opens a specified file for input
			FileInputStream OpenFile = new FileInputStream( FilePath ) ;

			//create a URL object based on tghe file path
			java.net.URL DocumentURL = FilePath.toURL() ;

			//sets the content of the JEditorPane using the contents of the file as specified by the URL object
			myEditor.setPage( DocumentURL ) ;

			//close the input stream
			OpenFile.close() ;
		}
		catch( java.io.FileNotFoundException FNE ){}
		catch ( java.net.MalformedURLException MURLE ){}
		catch( java.io.IOException IOE ){}

		//Assign the 'Document' object to the model associated with the editor.
		myDocument = myEditor.getDocument() ;

		//add an internal frame listener to the KBDocumentFrame
		addInternalFrameListener( this ) ;

		//add the KBDocumentFrame as a document listener for our 'Document' object
		myDocument.addDocumentListener( this ) ;
	}

/**
	Provides functionality for when the JInternalFrame is opened
*/
	public void internalFrameOpened( javax.swing.event.InternalFrameEvent IFE ){}

/**
	Provides functionality for when the JInternalFrame is closing
*/
	public void internalFrameClosing( javax.swing.event.InternalFrameEvent IFE )
	{
		//Purpose:	Provide functionality for when a KBDocumentFrame is closing
		//Parameters:	internal frame listener event
		//Returns:	

		//remove the document specified
		parentFrame.RemoveDocument( this ) ;
	}

/**
	Provides functionality for when the JInternalFrame is closed
*/
	public void internalFrameClosed( javax.swing.event.InternalFrameEvent IFE ){}

/**
	Provides functionality for when the JInternalFrame is iconified
*/
	public void internalFrameIconified( javax.swing.event.InternalFrameEvent IFE ){}

/**
	Provides functionality for when the JInternalFrame is deiconified
*/
	public void internalFrameDeiconified( javax.swing.event.InternalFrameEvent IFE ){}

/**
	Provides functionality for when the JInternalFrame is activated
*/
	public void internalFrameActivated( javax.swing.event.InternalFrameEvent IFE )
	{
		//Purpose:	Provide functionality for when a KBDocumentFrame is activated
		//Parameters:	internal frame listener event
		//Returns:	

		//activate the  document specified
		parentFrame.ActivateDocument( FileName ) ;
	}

/**
	Provides functionality for when the JInternalFrame is deactivated
*/
	public void internalFrameDeactivated( javax.swing.event.InternalFrameEvent IFE ){}

/**
	Returns the textual content of the JEditorPane
*/
	String getDocContent()
	{
		//Purpose:	retrieves the contents of the KBDocumentFrame
		//Parameters:	
		//Returns:	a string representation of the contents

		return this.myEditor.getText() ;
	}

/**
	Cuts the text from the JEditorPane
*/
	public void cutText()
	{
		//Purpose:	provides 'cut' functionality
		//Parameters:	
		//Returns:	

		this.myEditor.cut() ;
	}

/**
	Copies the text from the JEditorPane
*/
	public void copyText()
	{
		//Purpose:	provides 'copy' functionality
		//Parameters:	
		//Returns:	

		this.myEditor.copy() ;
	}

/**
	Pastes the text from the JEditorPane
*/
	public void pasteText()
	{
		//Purpose:	provides 'paste' functionality
		//Parameters:	
		//Returns:	

		this.myEditor.paste() ;
	}

/**
	Deletes the text from the JEditorPane
*/
	public void deleteText()
	{
		//Purpose:	provides 'delete' functionality
		//Parameters:	
		//Returns:	

		//replace the selected text with a null string
		this.myEditor.replaceSelection("") ;
	}

/**
	Selects all the text from the JEditorPane
*/
	public void selectAllText()
	{
		//Purpose:	provides 'Select All' functionality
		//Parameters:	
		//Returns:	

		this.myEditor.selectAll() ;
	}

/**
	Checks whether the document is modified
*/
	public boolean wasDocModified()
	{
		//Purpose:	checks whether the document is modified
		//Parameters:	
		//Returns:	a boolean indicating if modified or not

		return MODIFIED ;
	}

/**
	Sets the document as being modified or not
*/
	public void setModified( boolean isModified )
	{
		//Purpose:	sets the document as being modified or not
		//Parameters:	
		//Returns:	

		MODIFIED = isModified ;
	}

/**
	Sets the document as being a default one or not
*/
	public void setDefaultDoc( boolean isDefault )
	{
		//Purpose:	sets the document as being a default one or not
		//Parameters:	
		//Returns:	

		DEFAULTDOC = isDefault ;
	}

/**
	Checks whether the document is a default document
*/
	public boolean isDefaultDoc()
	{
		//Purpose:	checks whether the document is a default document
		//Parameters:	
		//Returns:	a boolean indicating if default document or not

		return DEFAULTDOC ;
	}

/**
	Provides functionality for when text is inserted into the content of the JEditorPane
*/
	public void insertUpdate( DocumentEvent DE )
	{
		//Purpose:	sets the document as being modified if text is inserted
		//Parameters:	
		//Returns:	

		MODIFIED = true ;
	}

/**
	Provides functionality for when text is removed from the content of the JEditorPane
*/
	public void removeUpdate( DocumentEvent DE )
	{
		//Purpose:	sets the document as being modified if text is removed
		//Parameters:	
		//Returns:	

		MODIFIED = true ;
	}

/**
	Provides functionality for when text is changed in the content of the JEditorPane
*/
	public void changedUpdate( DocumentEvent DE )
	{
		//Purpose:	sets the document as being modified if text is changed
		//Parameters:	
		//Returns:	

		MODIFIED = true ;
	}

/**
	Returns the file name of the document specified
*/
	public String getDocName()
	{
		//Purpose:	retrieves the file name of the document
		//Parameters:	
		//Returns:	a string representing the file name

		return FileName ;
	}

/**
	Sets the file name of the specified document
*/
	public void setDocName( String newName )
	{
		//Purpose:	Sets the file name of the specified document
		//Parameters:	The new file name of the specified document
		//Returns:	

		FileName = newName ;
	}


/**
	Returns the file path of the document specified
*/
	public File getDocPath()
	{
		//Purpose:	retrieves the file path of the document
		//Parameters:	
		//Returns:	a File representing the file path

		return FilePath ;
	}

/**
	Sets the file path of the specified document
*/
	public void setDocPath( File newPath )
	{
		//Purpose:	Sets the file path of the specified document
		//Parameters:	The new file path of the specified document
		//Returns:	

		FilePath = newPath ;
	}
}
