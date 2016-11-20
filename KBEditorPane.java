/*
Program Name:  Multi-Document Text Editor
Developer:  Kevin Anthony John Blighe
Date:  19th December 2002
*/

import java.awt.event.* ;
import javax.swing.* ;
import javax.swing.event.* ;
import java.io.* ;
import java.util.* ;

/**
	The KBEditorPane, in co-operation with a KBDocumentFrame manages a number
	of open documents for editing. It extends JFrame, and implements
	ListSelectionListener, and ActionListener.
*/

public class KBEditorPane extends JFrame implements ListSelectionListener, ActionListener
{
	//MENU-BAR
	//Create an instance of a JMenuBar
	JMenuBar MenuBar ;

	//MENU-BAR \ FILE
	//Create an instance of a JMenu
	JMenu MenuFile ;
	//Create instances of various JMenuItems for the JMenu we just created
	JMenuItem menuItemNew ;
	JMenuItem menuItemOpen ;
	JMenuItem menuItemSave ;
	JMenuItem menuItemSaveAs ;
	JMenuItem menuItemClose ;
	JMenuItem menuItemCloseAll ;
	JMenuItem menuItemExit ;

	//MENU-BAR \ EDIT
	//Create an instance of a JMenu
	JMenu MenuEdit ;
	//Create instances of various JMenuItems for the JMenu we just created
	JMenuItem menuItemCut ;
	JMenuItem menuItemCopy ;
	JMenuItem menuItemPaste ;
	JMenuItem menuItemDelete ;
	JMenuItem menuItemSelectAll ;

	//MENU-BAR \ SETTINGS
	//Create an instance of a JMenu
	JMenu MenuSettings ;

	//MENU-BAR \ SETTINGS \ DIVIDER
	//Create an instance of a JMenu acting as a sub-menu
	JMenu SubMenuDivider ;
	//Create instances of various JMenuItems for the JMenu we just created
	JMenuItem menuItemHide ;
	JMenuItem menuItemShow ;

	//MENU-BAR \ SETTINGS \ APPEARANCE
	//Create an instance of a JMenu acting as a sub-menu
	JMenu SubMenuAppearance ;
	//Create instances of various JMenuItems for the JMenu we just created
	JRadioButtonMenuItem menuItemJAVA ;
	JRadioButtonMenuItem menuItemMotif ;
	JRadioButtonMenuItem menuItemWindows ;

	//MENU-BAR \ HELP
	JMenu MenuHelp ;
	//Create an instance of a JMenuItem for the JMenu we just created
	JMenuItem menuItemAbout ;



	//NEW DOCUMENTS
	//Global variable holding the next available "Untitled" document number
	//ie those documents as created by the 'New' JMenuItem
	private int NewDocNumber = 0 ;



	//VECTOR
	//Global variable holding the KBDocumentFrames that are currently open
	private Vector DocumentsOpen ;



	//APPLICATION LAYOUT
	//The Split Pane will be global so as to access it from any function in the KBEditorPane	
	private JSplitPane mySplitPane ;

	//The JDesktopPane will be added to the right-component of the JSplitPane
	private JDesktopPane myRightDesktop ;

	//JScrollPane incorporating a JList to hold a list of documents open
	//These will be added to the left-component of the JSplitPane
	private JScrollPane documentScrollPane ;
	private JList DocumentList ;



/**
	This does a number of important things: creates a JFrame object,
	creates a JMenuBar, creates a Vector to hold the documents currently 
	open, and sets the overall design of the application
*/

	public KBEditorPane()
	{
		//Purpose:	Design & initialise the application
		//Parameters:	
		//Returns:	

		//Call the constructor for the parent class (JFrame) to set the title
		super("Multi-Document Text Editor") ;


		//WINDOW ADAPTER
		//Create an instance of the KBWindowAdapter class
		KBWindowAdapter EditorWA = new KBWindowAdapter() ;

		//Calls the public function 'setParentFrame' of KBWindowAdapter to set the parent frame
		EditorWA.setParentFrame( this ) ;

		//set the WindowListener for the application
		this.addWindowListener(EditorWA) ;

		//Set a default close operation to do nothing, so that we can easily...
		//...manipulate the program when it closes
		this.setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE ) ;



		//MENU-BAR
		//Create the menu-bar
		MenuBar = new JMenuBar() ;

		//Set the menu-bar to the application frame
		this.setJMenuBar( MenuBar ) ;
	
		//MENU-BAR \ FILE
		//Create a menu for the menu-bar
		MenuFile = new JMenu("File") ;
		//Add it to the menu-bar
		MenuBar.add(MenuFile) ;
		//Set so that this can be accessed with 'CTRL+F'
		MenuFile.setMnemonic(KeyEvent.VK_F) ;

		//Create an item for this menu
		menuItemNew = new JMenuItem("New") ;
		//Add the item to the menu
		MenuFile.add(menuItemNew) ;
		//Set so that this can be accessed with 'CTRL+N'
		menuItemNew.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_N, ActionEvent.CTRL_MASK )) ;

		//Create an item for this menu
		menuItemOpen = new JMenuItem("Open...") ;
		//Add the item to the menu
		MenuFile.add(menuItemOpen) ;
		//Set so that this can be accessed with 'CTRL+O'
		menuItemOpen.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.CTRL_MASK )) ;

		//Create an item for this menu
		menuItemSave = new JMenuItem("Save") ;
		//Add the item to the menu
		MenuFile.add(menuItemSave) ;
		//Set so that this can be accessed with 'CTRL+S'
		menuItemSave.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.CTRL_MASK )) ;

		//Create an item for this menu
		menuItemSaveAs = new JMenuItem("Save As...") ;
		//Add the item to the menu
		MenuFile.add(menuItemSaveAs) ;

		//Add a separator
		MenuFile.addSeparator() ;

		//Create an item for this menu
		menuItemClose = new JMenuItem("Close") ;
		//Add the item to the menu
		MenuFile.add(menuItemClose) ;

		//Create an item for this menu
		menuItemCloseAll = new JMenuItem("Close All") ;
		//Add the item to the menu
		MenuFile.add(menuItemCloseAll) ;

		//Add a separator
		MenuFile.addSeparator() ;

		//Create an item for this menu
		menuItemExit = new JMenuItem("Exit") ;
		//Add the item to the menu
		MenuFile.add(menuItemExit) ;

		//Add an action listener for the menu items in the 'File' menu
		menuItemNew.addActionListener(this) ;
		menuItemOpen.addActionListener(this) ;
		menuItemSave.addActionListener(this) ;
		menuItemSaveAs.addActionListener(this) ;
		menuItemClose.addActionListener(this) ;
		menuItemCloseAll.addActionListener(this) ;
		menuItemExit.addActionListener(this) ;


		//MENU-BAR \ EDIT
		//Create a menu for the menu-bar
		MenuEdit = new JMenu("Edit") ;
		//Add it to the menu-bar
		MenuBar.add(MenuEdit) ;
		//Set so that this can be accessed with 'CTRL+E'
		MenuEdit.setMnemonic(KeyEvent.VK_E) ;

		//Create an item for this menu
		menuItemCut = new JMenuItem("Cut") ;
		//Add the item to the menu
		MenuEdit.add(menuItemCut) ;
		//Set so that this can be accessed with 'CTRL+X'
		menuItemCut.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_X, ActionEvent.CTRL_MASK )) ;

		//Create an item for this menu
		menuItemCopy = new JMenuItem("Copy") ;
		//Add the item to the menu
		MenuEdit.add(menuItemCopy) ;
		//Set so that this can be accessed with 'CTRL+C'
		menuItemCopy.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_C, ActionEvent.CTRL_MASK )) ;

		//Create an item for this menu
		menuItemPaste = new JMenuItem("Paste") ;
		//Add the item to the menu
		MenuEdit.add(menuItemPaste) ;
		//Set so that this can be accessed with 'CTRL+V'
		menuItemPaste.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_V, ActionEvent.CTRL_MASK )) ;

		//Add a separator
		MenuEdit.addSeparator() ;

		//Create an item for this menu
		menuItemDelete = new JMenuItem("Delete") ;
		//Add the item to the menu
		MenuEdit.add(menuItemDelete) ;

		//Add a separator
		MenuEdit.addSeparator() ;

		//Create an item for this menu
		menuItemSelectAll = new JMenuItem("Select All") ;
		//Add the item to the menu
		MenuEdit.add(menuItemSelectAll) ;
		//Set so that this can be accessed with 'CTRL+A'
		menuItemSelectAll.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_A, ActionEvent.CTRL_MASK )) ;

		//Add an action listener for the menu items in the 'Edit' menu
		menuItemCut.addActionListener(this) ;
		menuItemCopy.addActionListener(this) ;
		menuItemPaste.addActionListener(this) ;
		menuItemDelete.addActionListener(this) ;
		menuItemSelectAll.addActionListener(this) ;

		//MENU-BAR \ SETTINGS
		//Create a menu for the menu-bar
		MenuSettings = new JMenu("Settings") ;
		//Add it to the menu-bar
		MenuBar.add(MenuSettings) ;
		//Set so that this can be accessed with 'CTRL+E'
		MenuSettings.setMnemonic(KeyEvent.VK_E) ;

		//MENU-BAR \ SETTINGS \ DIVIDER
		//Create a menu for the menu-bar
		SubMenuDivider = new JMenu("Divider") ;
		//Add it to the menu-bar
		MenuSettings.add(SubMenuDivider) ;

		//Create an item for this menu
		menuItemHide = new JMenuItem("Hide Divider") ;
		//Add the item to the menu
		SubMenuDivider.add(menuItemHide) ;

		//Create an item for this menu
		menuItemShow = new JMenuItem("Show Divider") ;
		//Add the item to the menu
		SubMenuDivider.add(menuItemShow) ;

		//MENU-BAR \ SETTINGS \ APPEARANCE
		//Create a menu for the menu-bar
		SubMenuAppearance = new JMenu("Appearance") ;
		//Add it to the menu-bar
		MenuSettings.add(SubMenuAppearance) ;

		//Create an item for this menu
		menuItemJAVA = new JRadioButtonMenuItem("JAVA Environment") ;
		//Add the item to the menu
		SubMenuAppearance.add(menuItemJAVA) ;

		//Create an item for this menu
		menuItemMotif = new JRadioButtonMenuItem("Motif Environment") ;
		//Add the item to the menu
		SubMenuAppearance.add(menuItemMotif) ;

		//Create an item for this menu
		menuItemWindows = new JRadioButtonMenuItem("MS Windows Environment") ;
		//Add the item to the menu
		SubMenuAppearance.add(menuItemWindows) ;

		//MENU-BAR \ SETTINGS \ APPEARANCE (BUTTON GROUP)
		//Create a button group
		ButtonGroup GroupAppearance = new ButtonGroup() ;
		//Add 3 JMenuItems to the button group
		GroupAppearance.add(menuItemJAVA) ;
		GroupAppearance.add(menuItemMotif) ;
		GroupAppearance.add(menuItemWindows) ;

		//Set default value for button group
		menuItemJAVA.setSelected( true ) ;

		//Add an action listener for the menu items in the 'Settings' menu
		menuItemHide.addActionListener(this) ;
		menuItemShow.addActionListener(this) ;
		menuItemJAVA.addActionListener(this) ;
		menuItemMotif.addActionListener(this) ;
		menuItemWindows.addActionListener(this) ;

		//MENU-BAR \ HELP
		//Create a menu for the menu-bar
		MenuHelp = new JMenu("Help") ;
		//Add it to the menu-bar
		MenuBar.add(MenuHelp) ;
		//Set so that this can be accessed with 'CTRL+H'
		MenuHelp.setMnemonic(KeyEvent.VK_H) ;

		//Create an item for this menu
		menuItemAbout = new JMenuItem("About The Editor") ;
		//Add the item to the menu
		MenuHelp.add(menuItemAbout) ;

		//Add an action listener for the menu item in the 'HELP' menu
		menuItemAbout.addActionListener(this) ;



		//VECTOR
		//Create the vector
		DocumentsOpen = new Vector() ;



		//APPLICATION LAYOUT
		//Create a JSplitPane
		mySplitPane = new JSplitPane() ;
		//Add the split pane to the content of the application
		this.setContentPane( mySplitPane ) ;

		//Create the JDesktopPane
		myRightDesktop = new JDesktopPane() ;
		//Add the desktop to the right-component of the split pane
		mySplitPane.setRightComponent( myRightDesktop ) ;
		//Set the thickness and the location of the split pane divider
		mySplitPane.setDividerSize( 2 ) ;
		mySplitPane.setDividerLocation( 200 ) ;

		//Calls the function to set & update the JList
		UpdateList() ;
	}


/**
	Provides functionality for when a value in the list is changed
*/
	public void valueChanged( ListSelectionEvent lse1 )
	{
		//Purpose:	Provide functionality for whenever the JList selected item changes
		//Parameters:	a list selection event object
		//Returns:	

		//Get the index of the item selected in the JList...
		int index = DocumentList.getSelectedIndex() ;

		//From the document vector, create a temporary KBDocumentFrame...
		//...based on the index value selected in the JList
		KBDocumentFrame dummy = (KBDocumentFrame)DocumentsOpen.elementAt( index ) ;

		try
		{
			//Activate the document
			dummy.setSelected( true ) ;

			//If its minimised, then restore it up
			if ( dummy.isIcon() )
				dummy.setIcon( false ) ;
		}
		catch( java.beans.PropertyVetoException PVE1 )
		{
			errorHandler( "Error! Cannot activate the document" ) ;
		}
	}

/**
	Provides functionality for whenever an action event occurs. The most common action event
	in this program is a JMenuItem being clicked on by the mouse pointer.
*/
	public void actionPerformed( java.awt.event.ActionEvent event1 )
	{
		//Purpose:	Provide functionality for whenever a JMenuItem is selected
		//Parameters:	an action event object
		//Returns:	

		//FILE \ NEW
		if ( event1.getSource() == menuItemNew )
		{
			//Create a file name based on the global 'NewDocNumber' variable
			String FileName = ( "Untitled" + (++NewDocNumber) + ".txt" ) ;

			//Create a file object based on the global 'NewDocNumber' variable
			File FilePath = new File("Untitled " + (NewDocNumber) + ".txt" ) ;

			//Pass values to 'CreateDocument'
			//'true' denotes this is a default document
			CreateNewDocument( FileName, FilePath, true ) ;
		}		

		//FILE \ OPEN
		if (event1.getSource() == menuItemOpen)
		{
			JFileChooser chooser = new JFileChooser() ;

			//Create a filter using the ExampleFileFilter.java file
    			ExampleFileFilter filter = new ExampleFileFilter() ;

			//Only allow general script files to be opened
			filter.addExtension("txt") ;
			filter.addExtension("java") ;
			filter.addExtension("ini") ;
			filter.setDescription("text files only") ;

			//Assign the filter as the filter for the file chooser
			chooser.setFileFilter( filter ) ;

			int returnVal = chooser.showOpenDialog( this ) ;

			//if the user doesnt cancel
			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				//Passed values to CreateDocument:
				//the name of the selected file
				//the path of the selected file
				//'false' denotes this is not a default document
				CreateNewDocument( chooser.getSelectedFile().getName(), chooser.getSelectedFile(), false ) ;
			}
		}

		//FILE \ SAVE
		if (event1.getSource() == menuItemSave)
		{
			//If there are documents open
			if ( DocumentsOpen.size() > 0 )
				SaveDocument() ;
			else
				errorHandler( "Error! There is no document open" ) ;
		}

		//FILE \ SAVEAS
		if (event1.getSource() == menuItemSaveAs)
		{
			//If there are documents open
			if ( DocumentsOpen.size() > 0 )
			{
				JFileChooser chooser = new JFileChooser() ;

				//Create a filter using the ExampleFileFilter.java file
				ExampleFileFilter filter = new ExampleFileFilter() ;

				//Only allow general script files to be opened
				filter.addExtension("txt") ;
				filter.addExtension("java") ;
				filter.addExtension("ini") ;
				filter.setDescription("text files only") ;

				//Assign the filter as the filter for the file chooser
				chooser.setFileFilter( filter ) ;

				int returnVal = chooser.showSaveDialog( this ) ;

				//if the user doesnt cancel
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					//Passed values to SaveHandler:
					//the name of the selected file
					//the path of the selected file
					SaveHandler( chooser.getSelectedFile().getName(), chooser.getSelectedFile() ) ;
				}
			}
			else	//If there are no documents open
			{
				errorHandler( "Error! There is no document open" ) ;
			}
		}

		//FILE \ CLOSE
		if (event1.getSource() == menuItemClose)
		{
			//If there are documents open
			if ( DocumentsOpen.size() > 0 )
			{
				//Create a temporary KBDocumentFrame based on the selected one
				KBDocumentFrame dummy = getSelectedDocument() ;

				RemoveDocument( dummy ) ;
			}
			else
			{
				errorHandler( "Error! There is no document open" ) ;
			}
			
		}

		//FILE \ CLOSEALL
		if (event1.getSource() == menuItemCloseAll)
		{
			//If there are documents open
			if ( DocumentsOpen.size() > 0 )
				RemoveAll() ;
			else
				errorHandler( "Error! There are no documents open" ) ;
		}

		//FILE \ EXIT
		if (event1.getSource() == menuItemExit)
		{
			//If there are documents open
			if ( DocumentsOpen.size() > 0 )
				RemoveAll() ;

			System.exit(0) ;
		}

		//EDIT \ CUT
		if (event1.getSource() == menuItemCut)
		{
			//If there are documents open
			if ( DocumentsOpen.size() > 0 )
			{
				//Create a temporary KBDocumentFrame based on the selected one
				KBDocumentFrame dummy = getSelectedDocument() ;

				dummy.cutText() ;
			}
			else
			{
				errorHandler( "Error! There is no document open" ) ;
			}
		}

		//EDIT \ COPY
		if (event1.getSource() == menuItemCopy)
		{
			//If there are documents open
			if ( DocumentsOpen.size() > 0 )
			{
				//Create a temporary KBDocumentFrame based on the selected one
				KBDocumentFrame dummy = getSelectedDocument() ;

				dummy.copyText() ;
			}
			else
			{
				errorHandler( "Error! There is no document open" ) ;
			}
		}

		//EDIT \ PASTE
		if (event1.getSource() == menuItemPaste)
		{
			//If there are documents open
			if ( DocumentsOpen.size() > 0 )
			{
				//Create a temporary KBDocumentFrame based on the selected one
				KBDocumentFrame dummy = getSelectedDocument() ;

				dummy.pasteText() ;
			}
			else
			{
				errorHandler( "Error! There is no document open" ) ;
			}
		}

		//EDIT \ DELETE
		if (event1.getSource() == menuItemDelete)
		{
			//If there are documents open
			if ( DocumentsOpen.size() > 0 )
			{
				//Create a temporary KBDocumentFrame based on the selected one
				KBDocumentFrame dummy = getSelectedDocument() ;

				dummy.deleteText() ;
			}
			else
			{
				errorHandler( "Error! There is no document open" ) ;
			}
		}

		//EDIT \ SELECTALL
		if (event1.getSource() == menuItemSelectAll)
		{
			//If there are documents open
			if ( DocumentsOpen.size() > 0 )
			{
				//Create a temporary KBDocumentFrame based on the selected one
				KBDocumentFrame dummy = getSelectedDocument() ;

				dummy.selectAllText() ;
			}
			else
			{
				errorHandler( "Error! There is no document open" ) ;
			}
		}

		//SETTINGS \ DIVIDER \ HIDE
		if (event1.getSource() == menuItemHide)
		{
			//Set divider location to 0, hiding it
			mySplitPane.setDividerLocation( 0 ) ;
		}

		//SETTINGS \ DIVIDER \ HIDE
		if (event1.getSource() == menuItemShow)
		{
			//Reset the divider location
			mySplitPane.setDividerLocation( 200 ) ;
		}

		//SETTINGS \ APPEARANCE \ JAVA
		if (event1.getSource() == menuItemJAVA)
		{
			//String containing the class path of the PLAF object to be used
			String lnfName = "javax.swing.plaf.metal.MetalLookAndFeel" ;

			setAppearance( lnfName ) ;
		}

		//SETTINGS \ APPEARANCE \ MOTIF
		if (event1.getSource() == menuItemMotif)
		{
			//String containing the class path of the PLAF object to be used
			String lnfName = "com.sun.java.swing.plaf.motif.MotifLookAndFeel" ;

			setAppearance( lnfName ) ;
		}

		//SETTINGS \ APPEARANCE \ WINDOWS
		if (event1.getSource() == menuItemWindows)
		{
			//String containing the class path of the PLAF object to be used
			String lnfName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" ;

			setAppearance( lnfName ) ;
		}

		//HELP \ ABOUT
		if (event1.getSource() == menuItemAbout)
		{
			JOptionPane.showMessageDialog( this,	"Program Name:  Multi-Document Text Editor\n"
								+"Developer:  Kevin Anthony John Blighe\n"
								+"Date:  19th December 2002\n"
								+"Course:  Ft 228\n"
								+"Year:  Three\n"
								+"Subject:  Windows Programming\n"
								+"Description: Open, edit, create, and save text documents") ;
		}
	}

/**
	Returns a KBDocumentFrame object based on whatever list item is selected
*/
	KBDocumentFrame getSelectedDocument()
	{
		//Purpose:	Gets the selected document and stores it in a temporary object
		//Parameters:	
		//Returns:	A KBDocumentFrame

		//Get the index value of the item selected in the JList
		int index = DocumentList.getSelectedIndex() ;

		//Based on this index value, create a temporary KBDocumentFrame from the document vector
		KBDocumentFrame dummy = (KBDocumentFrame)DocumentsOpen.elementAt( index ) ;

		return dummy ;
	}

/**
	Sets the PLAF (Pluggable Look And Feel) of the application
*/
	private void setAppearance( String lnfName )
	{
		//Purpose:	Sets the PLAF of the application
		//Parameters:	string representing the class path of the PLAF to be used
		//Returns:	

		try
		{
			//Set the PLAF for the application
			UIManager.setLookAndFeel( lnfName ) ;

			//Update all components in the application
			SwingUtilities.updateComponentTreeUI( this ) ;
		}
		catch ( ClassNotFoundException CNFE )
		{
			errorHandler( "Error! THe selected environment is not installed" ) ;
		}
		catch ( UnsupportedLookAndFeelException ULAFE )
		{
			errorHandler( "Error! The selected environment is not supported" ) ;
		}
		catch ( InstantiationException IE )
		{
			errorHandler( "Error! The selected environment cannot be instantiated" ) ;
		}
		catch ( IllegalAccessException IAE )
		{
			errorHandler( "Error! The selected environment cannot be accessed" ) ;
		}
	}

/**
	Creates a new KBDocumentFrame and adds it to the content of the application
*/
	private void CreateNewDocument( String newFileName, File newFilePath, boolean DefaultDoc )
	{
		//Purpose:	Performs a few checks and then creates a new KBDocumentFrame
		//Parameters:	File name, file path of the new document, and a boolean saying if its a default document
		//Returns:	

		KBDocumentFrame dummy ;

		File path = null ;

		boolean fileAlreadyOpen = false ;

		//Loop through already open documents and see if the document is already open
		for ( int i=0 ; i<DocumentsOpen.size() ; i++ )
		{
			dummy = (KBDocumentFrame)DocumentsOpen.elementAt( i ) ;

			//Call public member function of KBDocumentFrame
			path = (File)dummy.getDocPath() ;

			if ( path.equals( newFilePath ) )
			{
				errorHandler( "Error! The selected document is already open" ) ;
				fileAlreadyOpen = true ;
				break ;
			}
		}

		//If the file isnt open already
		if ( !fileAlreadyOpen )
		{
			//Create the document
			KBDocumentFrame myDocumentFrame = new KBDocumentFrame( this, newFileName, newFilePath ) ;

			//Add it to the content of the JDesktopPane
			myRightDesktop.add( myDocumentFrame ) ;
			myDocumentFrame.setVisible( true ) ;

			//Set it as a default document
			if ( DefaultDoc )
			{
				//Calls member function setDefaultDoc of KBDocumentFrame
				myDocumentFrame.setDefaultDoc( true ) ;
			}

			//Add it to the document vector
			DocumentsOpen.addElement( myDocumentFrame ) ;

			//Update the list
			UpdateList() ;

			//Highlight this document just created in the JList
			int index = DocumentsOpen.indexOf( myDocumentFrame ) ;
			DocumentList.setSelectedIndex( index ) ;
		}

		//Keep the divider at a constant position instead of the application changing it
		mySplitPane.setDividerLocation( mySplitPane.getDividerLocation() ) ;
	}

/**
	Displays the parameter as an error message on screen
*/
	private void errorHandler( String message )
	{
		//Purpose:	display an error message
		//Parameters:	An error message
		//Returns:	

		JOptionPane.showMessageDialog( this, message ) ;
	}

/**
	Save the textual content of a KBDocumentFrame to disk
*/
	private void SaveDocument()
	{
		//Purpose:	Outputs the contents of a KBDocumentFrame to a file
		//Parameters:	
		//Returns:	

		//Store the highlighted document in a temporary document
		KBDocumentFrame dummy = getSelectedDocument() ;

		//If its a default document
		if ( dummy.isDefaultDoc() )
		{
			JFileChooser chooser = new JFileChooser() ;

			//Create a filter using the ExampleFileFilter.java file
			ExampleFileFilter filter = new ExampleFileFilter() ;

			//Only allow txt files to be opened
			filter.addExtension("txt") ;
			filter.addExtension("java") ;
			filter.addExtension("ini") ;
			filter.setDescription("text files only") ;

			//Assign the filter as the filter for the file chooser
			chooser.setFileFilter( filter ) ;

			int returnVal = chooser.showSaveDialog( this ) ;

			//if the user clicks 'Save'
			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				//Call the save handler
				SaveHandler( chooser.getSelectedFile().getName(), chooser.getSelectedFile() ) ;
			}
		}
		//If its not a default dcument
		else
		{
			FileOutputStream WriteFile = null ;

			//call member function getDocPath of KBDocumentFrame
			File FilePath = dummy.getDocPath() ;

			try
			{
				//Create a new output stream for the desired file path
				WriteFile = new FileOutputStream( FilePath ) ;
			}
			catch( FileNotFoundException fnfe )
			{
				errorHandler( "Error! The file cannot be found" ) ;
			}

			//Declare a byte array to hold the contents of the file
			byte[] ByteContents = null ;

			//get a string representation of the content of the open document
			String DocumentContents = dummy.getDocContent() ;

			//convert the string contents to the byte array
			ByteContents = DocumentContents.getBytes() ;
		
			try
			{
				//write the byte contents to a file
				WriteFile.write( ByteContents ) ;

				//close the output stream
				WriteFile.close() ;
			}
			catch( IOException IOE )
			{
				errorHandler( "Error! Cannot write to the file" ) ;
			}

			//Set it to be not modified
			dummy.setModified( false ) ;
		}
	}

/**
	Checks whether the user wishes to overwrite a file that already exists
*/
	private void SaveHandler( String FileName, File FilePath )
	{
		//Purpose:	checks whether the user wishes to overwrite a file that already exists
		//Parameters:	file name, and file path of the document being saved
		//Returns:	

		KBDocumentFrame dummy = getSelectedDocument() ;

		//if the file path is already existing as a file on disk
		if ( FilePath.exists() )
		{
			int overwrite = JOptionPane.showConfirmDialog( this, "Are you sure you want to overwrite the file " + FileName + "?", "Overwrite?", JOptionPane.YES_NO_OPTION ) ;

			//If the user wishes to overwrite
			if ( overwrite == JOptionPane.YES_OPTION )
			{
				SaveAsDocument( dummy, FileName, FilePath ) ;

				//Change the document's title
				dummy.setTitle( FileName ) ;

				//Change its file name
				dummy.setDocName( FileName ) ;

				//Change its file path
				dummy.setDocPath( FilePath ) ;

				//Set the document as being not a default document
				dummy.setDefaultDoc( false ) ;

				//Update the JList
				UpdateList() ;

				//Set the value in the JList as this document
				DocumentList.setSelectedValue( dummy.getTitle(), true ) ;
			}
			//If the user doesn't wish to overwrite
			else
			{
				//Do nothing
			}
		}
		//If the file doesn't already exist
		else
		{
			SaveAsDocument( dummy, FileName, FilePath ) ;
			dummy.setTitle( FileName ) ;
			dummy.setDocName( FileName ) ;
			dummy.setDocPath( FilePath ) ;
			dummy.setDefaultDoc( false ) ;
			UpdateList() ;
			DocumentList.setSelectedValue( dummy.getTitle(), true ) ;
		}
	}

/**
	Save the textual content of a KBDocumentFrame to disk
*/
	private void SaveAsDocument( KBDocumentFrame dummy, String FileName, File FilePath )
	{
		//Purpose:	Outputs the contents of a KBDocumentFrame to a file
		//Parameters:	The document to be saved, file name, and file path of the document being saved
		//Returns:	

		FileOutputStream WriteFile = null ;

		try
		{
			//Create the FileOutputStream from the destination file
			WriteFile = new FileOutputStream( FilePath ) ;
		}
		catch( FileNotFoundException fnfe )
		{
			errorHandler( "Error! The file cannot be found" ) ;
		}

		//Declare a byte array to hold the contents of the file
		byte[] ByteContents = null ;

		//get a string representation of the content of the open document
		String DocumentContents = dummy.getDocContent() ;

		//convert the string contents to the byte array
		ByteContents = DocumentContents.getBytes() ;
		
		try
		{
			//write the byte contents to a file
			WriteFile.write( ByteContents ) ;

			//close the output stream
			WriteFile.close() ;
		}
		catch( IOException IOE )
		{
			errorHandler( "Error! Cannot write to the file" ) ;
		}

		dummy.setModified( false ) ;
	}

/**
	Cycles through all open documents and issues a close command
*/
	protected void RemoveAll()
	{
		//Purpose:	Cycles through all open documents and issues a close command
		//Parameters:	
		//Returns:	

		KBDocumentFrame dummy ;

		while ( DocumentsOpen.size() != 0 )
		{
			//Create a temp KBDocumentFrame representing the one at index 0 of the vector
			dummy = (KBDocumentFrame)DocumentsOpen.elementAt( 0 ) ;

			RemoveDocument( dummy ) ;
		}

		mySplitPane.setDividerLocation( mySplitPane.getDividerLocation() ) ;
	}

/**
	Removes the selected document from the application
*/
	protected void RemoveDocument( KBDocumentFrame dummy )
	{
		//Purpose:	Removes a specified document from the application
		//Parameters:	a KBDocumentFrame
		//Returns:	

		try
		{
			//Highlight the document to be closed
			dummy.setSelected( true ) ;

			//If its minimised, then restore it up
			if ( dummy.isIcon() )
				dummy.setIcon( false ) ;
		}
		catch( java.beans.PropertyVetoException PVE1 )
		{
			errorHandler( "Error! Cannot activate the document" ) ;
		}

		//highlight the documents name in the JList
		DocumentList.setSelectedValue( dummy, true ) ;

		//Check for modifications in the document
		checkModifiedDoc( dummy ) ;

		//Get the document's index value
		int index = this.DocumentList.getSelectedIndex() ;

		//ie if the document actually exists
		if ( index>=0 )
		{
			//Remove the document from the vector
			DocumentsOpen.removeElementAt( index ) ;
		}

		//update the list
		UpdateList() ;

		//Resize the vector
		DocumentsOpen.trimToSize() ;

		//totally remove the document (just to make sure!)
		dummy.dispose() ;

		//if there are still documents open
		if ( DocumentsOpen.size() != 0 )
		{
			dummy = (KBDocumentFrame)DocumentsOpen.elementAt( 0 ) ;

			DocumentList.setSelectedIndex(0) ;
		}

		//Keep the divider at a constant position instead of the application changing it
		mySplitPane.setDividerLocation( mySplitPane.getDividerLocation() ) ;
	}

/**
	Provides functionality for when a document frame is activated
*/
	protected void ActivateDocument( String DocToActivate )
	{
		//Purpose:	performs functionality for when a KBDocumentFrame is activated
		//Parameters:	the file name of the document to be activated
		//Returns:	

		//highlight the documents correlating value in the JList
		DocumentList.setSelectedValue( DocToActivate, true ) ;
	}

/**
	Checks for modifications in a document
*/
	private void checkModifiedDoc( KBDocumentFrame dummy )
	{
		//Purpose:	Checks for modifications in a document
		//Parameters:	a KBDocumentFrame
		//Returns:	

		int option ;

		boolean modified = dummy.wasDocModified() ;

		//if its modified
		if ( modified )
		{
			option = JOptionPane.showConfirmDialog( this, "The selected document was modified. Do you want to save changes?", "Save Changes?", JOptionPane.YES_NO_OPTION ) ;

			//if its modified and the user wishes to save changes
			if ( option == JOptionPane.YES_OPTION )
				SaveDocument() ;
		}
	}

/**
	Update the JList based on the document names of each document currently open
*/
	private void UpdateList()
	{
		//Purpose:	Update the JList based on the document names of each document currently open
		//Parameters:	
		//Returns:	

		KBDocumentFrame dummy ;

		//Create a temporary vector to hold the file names
		Vector temp = new Vector() ;

		//loop through the document vector and copy the file names to our temporary vector
		for ( int i=0 ; i<DocumentsOpen.size() ; i++ )
		{
			dummy = (KBDocumentFrame)DocumentsOpen.elementAt( i ) ;
			temp.addElement( dummy.getDocName() ) ;
		}

		//Create the JList with the temporary vector as parameter
		DocumentList = new JList( temp ) ;

		//only allow one selection at a time
		DocumentList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION ) ;

		//add a list selection listener for the JList
		DocumentList.addListSelectionListener( this ) ;

		//create a scroll pane for our JList
		documentScrollPane = new JScrollPane( DocumentList ) ;

		//add the scoll pane to our left-component in our JSplitPane
		mySplitPane.setLeftComponent( documentScrollPane ) ;

		//set the divider location
		mySplitPane.setDividerLocation( mySplitPane.getDividerLocation() ) ;
	}
}
