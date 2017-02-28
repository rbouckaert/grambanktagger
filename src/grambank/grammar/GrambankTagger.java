package grambank.grammar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JScrollPane;


public class GrambankTagger extends JPanel {
	private static final long serialVersionUID = 1L;
	
	EditorPanel editorPanel;
	public static String ICONPATH = "grambank/tagger/icons/";
	public static String m_sDir = System.getProperty("user.dir");
	

	public GrambankTagger(String [] args) throws IOException {
		setLayout(new BorderLayout());
		editorPanel = new EditorPanel();
		//JScrollPane plotScrollPane = new JScrollPane(editorPanel);
		add(editorPanel, BorderLayout.CENTER);
		
		String cfgFile = "GramBankTagger.cfg";
		if (args.length != 0) {
			cfgFile = args[0];
		}
		editorPanel.grammarFiles = new ArrayList<>();
        BufferedReader fin = new BufferedReader(new FileReader(cfgFile));
        String str = null;
        while (fin.ready()) {
            str = fin.readLine().trim();
            if (!str.startsWith("#")) {
            	editorPanel.grammarFiles.add(str);
            }
        }
        fin.close();
        
        if (editorPanel.grammarFiles.size() == 0) {
        	throw new IllegalArgumentException("File " + cfgFile + " should contain at least one grammar file name");
        }

        editorPanel.doOpen(new File(editorPanel.grammarFiles.get(0)));
	}
	
	
    static public String load(File file) throws IOException {
        BufferedReader fin = new BufferedReader(new FileReader(file));
        StringBuffer buf = new StringBuffer();
        String str = null;
        while (fin.ready()) {
            str = fin.readLine();
            buf.append(str);
            buf.append('\n');
        }
        fin.close();
        return buf.toString();
    }
    
	class ActionQuit extends MyAction {
		/** for serialization */
		private static final long serialVersionUID = -2038911085935515L;

		public ActionQuit() {
			super("Exit", "Exit Program", "exit", "alt F4");
		} // c'tor

		public void actionPerformed(ActionEvent ae) {
			System.exit(0);
		}
	} // class ActionQuit

	
	Action a_load =  new MyAction("Load", "Open file for editing", "open", KeyEvent.VK_O) {
        private static final long serialVersionUID = 1;

        public void actionPerformed(ActionEvent ae) {
        	editorPanel.doOpen();
        }
    };
    
    MyAction a_quit = new MyAction("Exit", "Exit Program", "exit", KeyEvent.VK_F4) {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
    }; // class ActionQuit
	
	JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		/** status bar at bottom of window */
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);
		fileMenu.add(a_load);
		fileMenu.addSeparator();
		fileMenu.add(a_quit);
		return menuBar;
	}
	
	public static void main(String[] args) {
		try {
			JFrame f = new JFrame("GrambankTagger");
			
			
			GrambankTagger tagger = new GrambankTagger(args);
			
			JMenuBar menuBar = tagger.createMenuBar();
			f.setJMenuBar(menuBar);
			//f.add(drawTest.m_jTbTools, BorderLayout.NORTH);
			f.add(tagger, BorderLayout.CENTER);
			//f.add(drawTest.m_jStatusBar, BorderLayout.SOUTH);
	
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//f.getContentPane().add(drawTest);
			//drawTest.m_doc.loadFile("C:\\Documents and Settings\\rrb\\My Documents\\fruittrees\\XMLDraw1.xdl");
			//drawTest.m_doc.loadFile("G:\\eclipse\\workspace\\var\\test2.xdl");
			f.setSize(1920, 1100);
			f.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Something went wrong: " + e.getMessage());
		}

	}
}
