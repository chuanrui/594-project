
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.swing.*;

public class StaticGUI implements GUI {

	private JFrame mainFrame;
	private JPanel controlPanel;
	private JTextField input;
	private JList<String> autoComplete;
	private JList<String> resultList;
	private JButton searchButton;
	private DefaultListModel<String> l1;
	private DefaultListModel<String> l2;
	
	private JCheckBox bonus;
	private PrefixTree tree;
	private Reader reader;
	private Parser parseResult;
	HashMap<String,HashSet<String>> tfIdf;
	HashMap<String, Integer> tagFreq;
	HashMap<String, Integer> fileFreq;
	
	public StaticGUI() throws Exception {
		tagFreq = new HashMap<String, Integer>();
		fileFreq = new HashMap<String, Integer>();
		try {
			reader = new Reader("text");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//initialize tag-file map
		parseResult = new Parser();
		HashMap<String,List<String>> fileMap = reader.getFileMap();
		HashSet<String> dictionary= reader.getDictionary(); 
		tfIdf = parseResult.tfIdf(fileMap, dictionary);
		//initialize tree
		tree = new PrefixTree();
		for(String tag : tfIdf.keySet()) {
			System.out.println(tag);
			tree.insert(tag);
		}
		prepareGUI();
	}
	
	@Override
	public void prepareGUI() {
		
		mainFrame = new JFrame("594 project");
		mainFrame.setSize(500, 500);		
		mainFrame.setBackground(Color.WHITE);
		mainFrame.setLayout(new CardLayout(25, 25));
		mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	    });

		controlPanel = new JPanel();
		controlPanel.setBackground(Color.lightGray);
		controlPanel.setLayout(null);

		
		input  = new JTextField(10);
		input.setBounds(80, 10, 150, 30);
		
		searchButton = new JButton("search");
		searchButton.setBounds(230, 10, 80, 30);
		
		//autoComplete list
		l1 = new DefaultListModel<String>();
		autoComplete = new JList<String>(l1);
		autoComplete.setBounds(83, 37, 144, 150);
		autoComplete.setVisible(false);
		
		//result list
		l2 = new DefaultListModel<String>();
		resultList = new JList<String>(l2);
		resultList.setBounds(20, 200, 410, 200);
		resultList.setVisible(false);
		
		//funny thing
		bonus = new JCheckBox("DO NOT click me!!!");
		bonus.setBounds(200, 385, 180, 50);
		
		//add elements
		controlPanel.add(input);
		controlPanel.add(autoComplete);
		controlPanel.add(searchButton);
		controlPanel.add(resultList);
		controlPanel.add(bonus);
		
		mainFrame.add(controlPanel);
		mainFrame.setVisible(true);
	}


	@Override
	public void behavior() {

		TypeBehavior type = new TypeBehavior();
		type.behavior(input, autoComplete, l1, tree, tagFreq);
		ButtonBehavior button = new ButtonBehavior();
		button.behavior(searchButton, resultList, input, l2, tfIdf, autoComplete, tagFreq, fileFreq);
		
		bonus.addItemListener(new ItemListener() {
			@Override
			public synchronized void itemStateChanged(ItemEvent e) {
				if(!bonus.isSelected()) {
					searchButton.setBounds(230, 10, 80, 30);
				}
				int[][] cord = new int[5][2];
				cord[0][0] = 90;
				cord[0][1] = 100;
				cord[1][0] = 300;
				cord[1][1] = 50;
				cord[2][0] = 160;
				cord[2][1] = 300;
				cord[3][0] = 200;
				cord[3][1] = 100;
				cord[4][0] = 10;
				cord[4][1] = 170;
				searchButton.addMouseListener(new MouseListener() {

					int x = 0;
					@Override
					public void mouseClicked(MouseEvent e) {}

					@Override
					public void mousePressed(MouseEvent e) {}

					@Override
					public void mouseReleased(MouseEvent e) {}

					@Override
					public void mouseEntered(MouseEvent e) {
						if(bonus.isSelected()) {
							searchButton.setBounds(cord[x][0], cord[x][1], 80, 30);
							x++;
							if(x==5) x = 0;
						}
					}

					@Override
					public void mouseExited(MouseEvent e) {}
					
				});
			}
		});
	}

	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		StaticGUI g = new StaticGUI();
		g.behavior();
	}
}