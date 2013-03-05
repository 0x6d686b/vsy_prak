/**
 * @(#)ExBoxFrame.java
 *
 * JFC ExBox application
 *
 * @author
 * @version	1.00 2012/2/3
 */


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.rmi.Naming;


public class ExBoxFrame	extends	JFrame implements ActionListener, ItemListener {

 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JMenuItem loadExec,connectRMI,open;
  	private final JButton enter;
  	private final JTextField arguments;
  	private final JComboBox history;
  	private final JTextArea output;
  	private CommandInterpreter command;
  	public final boolean debug;
	/**
	 * The constructor
	 */
	public ExBoxFrame()  {
		
		debug = java.lang.management.ManagementFactory.getRuntimeMXBean().
			    getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu();
		JMenuItem menuFileExit = new JMenuItem();
		open = new JMenuItem();
		
		menuFile.setText("File");
		menuFileExit.setText("Exit");
		open.setText("Open");

		menuFileExit.addActionListener
		(
			new	ActionListener() {
				public void	actionPerformed(ActionEvent	e) {
					ExBoxFrame.this.windowClosed();
				}
			}
		);
		
		menuFile.add(menuFileExit);
		menuFile.add(open);
		menuBar.add(menuFile);

		JMenu menuServer = new JMenu();
		menuServer.setText("Server");
		
		loadExec = new JMenuItem();
		loadExec.setText("Load executable");
		loadExec.addActionListener(this);
		
		connectRMI = new JMenuItem();
		connectRMI.setText("Connect to RMI service");
		connectRMI.addActionListener(this);
		
		open.addActionListener(this);
		menuServer.add(loadExec);
		menuServer.add(connectRMI);
		menuBar.add(menuServer);
		

		setTitle("ExBox");
		setJMenuBar(menuBar);
		setSize(new	Dimension(400, 400));

		this.addWindowListener
		(
			new	WindowAdapter() {
				public void	windowClosing(WindowEvent e) {
					ExBoxFrame.this.windowClosed();
				}
			}
		);

	  	try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {System.out.println("L&F not found");}
	  	
	  	setLayout(new BorderLayout());
	  	output = new JTextArea();
	  	arguments = new JTextField(20);
	  	history = new JComboBox();
	  	history.setToolTipText("Verlauf");
	  	history.addItemListener(this);
	  	enter = new JButton("Enter");
	  	enter.addActionListener(this);
	  	
        Panel southPanel = new Panel();
        southPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(output);
        add(BorderLayout.CENTER, scrollPane);

        southPanel.add(BorderLayout.CENTER, arguments);
        southPanel.add(BorderLayout.EAST, enter);
        southPanel.add(BorderLayout.SOUTH, history);
        add(BorderLayout.SOUTH, southPanel);
        
	}

	private	void log(String	s) {
	   System.out.println(s);
	}

	private void error(String s) {
	  output.append("\nERROR:" + s+"\n");
	}

	private void interpret(String args) throws Exception{
	  if (command != null) {
		  String s = command.interpret(args);
		  output.append(s);
	  }
	  else error("no Server	connected");
	}

	public void	itemStateChanged(ItemEvent e) {
	   arguments.setText((String)e.getItem());
	}

	public void	actionPerformed(ActionEvent	e) {
		if (e.getSource() == loadExec) {
			loadExec();
		}
		if (e.getSource() == open) {
			open();
		}
		if (e.getSource() == enter) {
			enter();
		}
		if (e.getSource() == history) {
			history();
		}
		if (e.getSource() == connectRMI) {
			connectRMI();
		}
	 }



	/**
	 * Shutdown	procedure when run as an application.
	 */
	protected void windowClosed() {

		// Exit	application.
		System.exit(0);
	}
	
	private void loadExec() {
		FileDialog fd = new FileDialog(this, "Connect");
		fd.setFile("*Server.class");
		fd.setDirectory("\\bin\\");
		fd.setVisible(true);
		String name = fd.getFile();
		DynamicLoadedClassFactory sf = new DynamicLoadedClassFactory();
		try {
			command = sf.createServer(name);
			log("Connected to" + name);
		} catch (Exception e1) {
			error("Connect throwed an exception!");
			e1.printStackTrace();
		}
		setTitle("ExBox connected to " + name);
	}
	
	private void open() {
		FileInputStream fis = null;
		FileDialog fd = new FileDialog(this, "load");
		fd.setFile("*.xml");
		fd.setVisible(true);
		String name = fd.getFile();
		StringBuilder text = new StringBuilder();
	    String NL = System.getProperty("line.separator");
	    try {
			fis = new FileInputStream(System.getProperty("user.dir") + "/bin/" + name);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Scanner scanner = new Scanner(fis, "UTF-8");
	    try {
	      while (scanner.hasNextLine()){
	        text.append(scanner.nextLine() + NL);
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    arguments.setText(text.toString());
	    //log("Loaded " + name + "\n");
	}
	
	private void enter() {
		try {
			log("Starting the interpreter ...");
			interpret(arguments.getText());
			log("Interpreter returned.");
			history.addItem(arguments.getText());
			arguments.setText("");
		} catch (Exception e1) {
			error("The interpretor throwed an exception!");
			e1.printStackTrace();
		}
	}
	
	private void history() {
		try {
			arguments.setText(history.getSelectedItem().toString());
		} catch (Exception e1) {
			error("There were no items in the combobox.");
			e1.printStackTrace();
		}
	}
	
	private void connectRMI() {
		JTextField uri = new JTextField();
		final JComponent[] inputs = new JComponent[] {
				new JLabel("URI:"),
				uri
		};
		JOptionPane.showMessageDialog(null, inputs, "Enter the URI of the RMI service", JOptionPane.PLAIN_MESSAGE);
		
		System.out.println(uri.getText());
		
		try { Object newborn = Naming.lookup(uri.getText());
			if (newborn instanceof CommandInterpreter) {
				command = (CommandInterpreter) newborn;
			}
		} catch (Exception e1) {
			error("Could not connect to RMI service.");
			e1.printStackTrace();
		}
		
		setTitle("Connected to RMI: " + uri.getName());
	}
}