import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Wikipedia {
	
	//VARIABLES
		//UI VARIABLES
		private final int WIDTH = 500, HEIGHT = 600, BORDER = 40;
		private final int T_HEIGHT = 80, IN_HEIGHT = 40, OUT_HEIGHT = 440;
		private final int TEXT_HEIGHT = 20;
		private String white = "#EBEDF3", lblue = "#6699CC", gray = "#BDBDBD", blue = "#003B6D";
		
		//METHOD VARIABLES
		private String displayText = "";
	
	//CONSTRUCTOR
	public Wikipedia() {
		
		//UI STUFF
		
			//MAIN PANEL
			JPanel panel = new JPanel();
			BoxLayout b1 = new BoxLayout(panel, BoxLayout.Y_AXIS);
			panel.setLayout(b1);
			panel.setBorder(BorderFactory.createEmptyBorder(20,20, 20, 20));
			panel.setBackground(Color.decode(gray));
			
			//TOP PANEL
			JTextPane topPanel = new JTextPane();
			topPanel.setBackground(Color.decode(blue));
			topPanel.setForeground(Color.decode(white));
			topPanel.setBorder(BorderFactory.createEmptyBorder(20,20, 20, 20));
			topPanel.setPreferredSize(new Dimension(WIDTH - BORDER, T_HEIGHT));
			topPanel.setText("Welcome to Wikipedia Scraper! Please Enter the name of the Article you would like to search.");
			panel.add(topPanel);
			
			//INPUT PANEL
			JPanel inputPanel = new JPanel();
			inputPanel.setBackground(Color.decode(blue));
			inputPanel.setPreferredSize(new Dimension(WIDTH - BORDER, IN_HEIGHT));
			panel.add(inputPanel);
			
				//PROMPT
				JLabel prompt = new JLabel("Article:");
				prompt.setForeground(Color.decode(white));
				prompt.setPreferredSize(new Dimension(50, TEXT_HEIGHT));
				inputPanel.add(prompt);
				
				//INPUT
				JTextPane input = new JTextPane();
				input.setPreferredSize(new Dimension(200, TEXT_HEIGHT));
				input.setBackground(Color.decode(white));
				input.setForeground(Color.decode(blue));
				inputPanel.add(input);
				
				//RUN BUTTON
				JButton run = new JButton("SEARCH");
				run.setPreferredSize(new Dimension(100, TEXT_HEIGHT));
				run.setBackground(Color.decode(lblue));
				run.setForeground(Color.decode(white));
				run.setOpaque(true);
				run.setBorderPainted(false);
				inputPanel.add(run);
			
			//OUTPUT PANEL
			JPanel outputPanel = new JPanel();
			outputPanel.setBackground(Color.decode(white));
			outputPanel.setPreferredSize(new Dimension(WIDTH - BORDER, OUT_HEIGHT));
			panel.add(outputPanel);
			
				//TEXT DISPLAY
				JTextPane outputText = new JTextPane();
				outputText.setBackground(Color.decode(white));
				outputText.setForeground(Color.decode(blue));
				outputText.setPreferredSize(new Dimension(WIDTH - BORDER - 20, OUT_HEIGHT));
				outputText.setText(displayText);
				outputText.setEditable(false);
				
				//SCROLLY
				JScrollPane scrolly = new JScrollPane(outputText);
				scrolly.setBorder(null);
				outputPanel.add(scrolly);
				
			//RUN ACTION
			run.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {  
					
					//get the site link formatted
					String temp = input.getText();
					String site = format(temp);
					
					//get new text
					getInfo(site);
					
					//update text
					outputText.setText(displayText);
					outputText.setCaretPosition(0);
					
				}
			});
				
				
			
			//MAIN FRAME
			JFrame frame = new JFrame();
			frame.setSize(WIDTH, HEIGHT);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(panel);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setVisible(true);
			panel.setFocusable(true);
		
		
	}
	
	public String format(String site) {
		
		return site.replaceAll(" ", "_").toLowerCase();
		
	}
	
	public void getInfo(String site) {
		
		//reset display Text
		displayText = "";
		
		//get info
		try {
			
			Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + site).get();
			Element body = doc.getElementsByClass("mw-parser-output").first();
			Elements pTemp = body.children();
			
			//format the first text
			String formatted = pTemp.first().text().toLowerCase().trim();	
			
			//checks for may refer to page
			if(formatted.equals(site + " may refer to:")) {
				System.out.println("error: page not specific 1");
				displayText = "We are having a hard time finding your page. Please be more specific";
			}
			
			//checks if may also refer to page
			else if(formatted.equals(site+ " may also refer to:")) {
				System.out.println("error: page not specific 2");
				displayText = "We are having a hard time finding your page. Please be more specific";
			}
			
			//set display text if not
			else {
				for(Element p : pTemp) {
					
					//System.out.println("found an element");
					//System.out.println(p.nodeName());
					
					if(p.nodeName().equals("p")) {
						if(p.hasText() == true) {
							displayText += p.text() + "\n\n";
						}
					}
					
				}
				//extra formatting
				displayText += "\n\n";
			}
			
		
		//error catch
		} catch (IOException e) {
			System.out.println("error: page not found");
			displayText = "We are having a hard time finding your page. Please enter a valid input";
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		new Wikipedia();
	}
	

}
