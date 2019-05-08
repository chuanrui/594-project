
import javax.swing.*;

import java.awt.CardLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class ButtonBehavior {

	private JList<String> result;
	private DefaultListModel<String> l;
	HashMap<String, Integer> tagFreq;
	HashMap<String, Integer> fileFreq;
	
	public void behavior(JButton searchButton, JList<String> result, JTextField input, DefaultListModel<String> l, HashMap<String,HashSet<String>> map,JList<String> auto, HashMap<String, Integer> tagFreq, HashMap<String, Integer> fileFreq) {
		this.result = result;
		this.l = l;
		this.tagFreq = tagFreq;
		this.fileFreq = fileFreq;
		//click search
		searchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				l.removeAllElements();
				HashSet<String> set = map.get(input.getText());
				if(set!=null) {
					//tag frequency++
					tagFreq.put(input.getText(), tagFreq.getOrDefault(input.getText(), 0)+1);
					//sort files
					String[] fileArray = set.toArray(new String[0]);
					for(int j = 0; j < fileArray.length; j++) {
						fileArray[j] = fileArray[j].replaceAll("text/", "").replaceAll(".txt", "");
					}
					customSort(fileArray);
					for(int j = fileArray.length-1; j >= 0; j--) {
						l.addElement(fileArray[j]);
					}
				}
				else {
					l.addElement("no results found");
				}
				result.setVisible(true);
				auto.setVisible(false);
			}
		});
		//click filename
		result.addMouseListener(new MouseListener() {
			//open file in new window
			@Override
			public void mouseClicked(MouseEvent e) {
				if(result.getSelectedIndex()!=-1) {
					//file frequency++
					fileFreq.put(result.getSelectedValue(), fileFreq.getOrDefault(result.getSelectedValue(), 0)+1);
					
					JFrame newFrame = new JFrame(result.getSelectedValue());
					newFrame.setSize(800, 800);
					newFrame.setLayout(new CardLayout(30, 30));
					JTextArea area = new JTextArea();
					area.setLineWrap(true);
					JScrollPane scroll = new JScrollPane(area);
					scroll.setSize(300, 300);
					newFrame.add(scroll);
					File file = new File("text/"+result.getSelectedValue()+".txt");
					Scanner sc = null;
					try {
						sc = new Scanner(file);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					while(sc.hasNextLine()) {
						area.append(sc.nextLine());
						area.append("\n");
					}
					sc.close();
					area.setEditable(false);
					newFrame.setVisible(true);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
	}
	private void customSort(String[] array) {
		for(int i = 1; i < array.length; i++) {
			String key = array[i];
			int j = i-1;
			while(j>=0&&fileFreq.getOrDefault(array[j], 0) > fileFreq.getOrDefault(key, 0)) {
				array[j+1] = array[j];
				j--;
			}
			array[j+1] = key;
		}
	}
	
}