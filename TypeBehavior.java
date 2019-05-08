
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TypeBehavior {

	private JTextField input;
	private JList<String> result;
	private DefaultListModel<String> l;
	private PrefixTree tree;
	HashMap<String, Integer> tagFreq;
	
	public void behavior(JTextField input, JList<String> result, DefaultListModel<String> l, PrefixTree tree, HashMap<String, Integer> tagFreq) {
		this.input = input;
		this.result = result;
		this.l = l;
		this.tree = tree;
		this.tagFreq = tagFreq;
		input.addKeyListener(new CustomKeyListener());
		result.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(result.getSelectedIndex()!=-1) {
					input.setText(result.getSelectedValue());
					l.removeAllElements();
			        ArrayList<String> res = tree.prefixSearch(input.getText());
			  		for(String tag : res ) {
			  			l.addElement(tag);
				  	}
				    result.setVisible(true); 
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

	class CustomKeyListener implements KeyListener{
	      public void keyTyped(KeyEvent e) {}
	      public void keyPressed(KeyEvent e) {}
	      public void keyReleased(KeyEvent e) {
	    	  l.removeAllElements();
	    	  ArrayList<String> res = null;
	    	  res = tree.prefixSearch(input.getText());
	  		  if(input.getText().length()!=0&&res!=null) {
	  			  res = tree.prefixSearch(input.getText());
	  			  //sort tags
	  			  String[] tagArray = new String[res.size()];
	  			  int i = 0;
	  			  for(String name: res) {
	  				  tagArray[i] = name;
	  				  i++;
	  			  }
	  			  customSort(tagArray);
	  			  for(int j = tagArray.length-1; j >= 0; j--) {
	  				  l.addElement(tagArray[j]);
		  		  }
		  		  result.setVisible(true); 
	  		  }
	  		  else {
	  			  result.setVisible(false);
	  		  }
	      }
	}
	private void customSort(String[] array) {
		for(int i = 1; i < array.length; i++) {
			String key = array[i];
			int j = i-1;
			while(j>=0&&tagFreq.getOrDefault(array[j], 0) > tagFreq.getOrDefault(key, 0)) {
				array[j+1] = array[j];
				j--;
			}
			array[j+1] = key;
		}
	}
}
