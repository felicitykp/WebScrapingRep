import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiGame {

	//VARIABLES
	private final int WIDTH = 400, HEIGHT = 600, BORDER = 40, TEXT_HEIGHT = 20;
	private String dG = "#006D77", lG = "#83C5BE", white = "#EDF6F9", black = "#222222";
	
	//CONSTRUCTOR
	public WikiGame() {
		
		//UI STUFF
		
			//MAIN PANEL
			JPanel panel = new JPanel();
			BoxLayout b1 = new BoxLayout(panel, BoxLayout.Y_AXIS);
			panel.setLayout(b1);
			panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
			panel.setBorder(BorderFactory.createEmptyBorder(20,20, 20, 20));
			panel.setBackground(Color.decode(lG));
			
			//TOP PANEL
			JPanel topPanel = new JPanel();
			topPanel.setBackground(Color.decode(dG));
			topPanel.setPreferredSize(new Dimension(WIDTH-BORDER, 120));
			panel.add(topPanel);
			
			
				//SPACING
				topPanel.add(Box.createRigidArea(new Dimension(WIDTH-BORDER, TEXT_HEIGHT)));
			
				//START PROMPT
				JLabel startPrompt = new JLabel("Start:");
				startPrompt.setForeground(Color.decode(white));
				startPrompt.setPreferredSize(new Dimension(40, TEXT_HEIGHT));
				topPanel.add(startPrompt);
				
				//START INPUT
				JTextPane startInput = new JTextPane();
				startInput.setBackground(Color.decode(white));
				startInput.setForeground(Color.decode(black));
				startInput.setPreferredSize(new Dimension(100, TEXT_HEIGHT));
				topPanel.add(startInput);
				
				//SPACING
				topPanel.add(Box.createRigidArea(new Dimension(10, TEXT_HEIGHT)));
				
				//END PROMPT
				JLabel endPrompt = new JLabel("End:");
				endPrompt.setForeground(Color.decode(white));
				endPrompt.setPreferredSize(new Dimension(40, TEXT_HEIGHT));
				topPanel.add(endPrompt);
				
				//END INPUT
				JTextPane endInput = new JTextPane();
				endInput.setBackground(Color.decode(white));
				endInput.setForeground(Color.decode(black));
				endInput.setPreferredSize(new Dimension(100, TEXT_HEIGHT));
				topPanel.add(endInput);
				
				//SPACING
				topPanel.add(Box.createRigidArea(new Dimension(WIDTH-BORDER, TEXT_HEIGHT/2)));
				
				//RUN BUTTON
				JButton runButton = new JButton("Run");
				runButton.setPreferredSize(new Dimension(100, TEXT_HEIGHT));
				runButton.setBackground(Color.decode(lG));
				runButton.setForeground(Color.decode(black));
				runButton.setOpaque(true);
				runButton.setBorderPainted(false);
				runButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String start = format(startInput.getText());
						
						try {
							getLinks(start);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					
				});
				topPanel.add(runButton);
				
				//SPACING
				topPanel.add(Box.createRigidArea(new Dimension(10, TEXT_HEIGHT)));
				
			
			//DISPLAY PANEL
			JPanel displayPanel = new JPanel();
			displayPanel.setBackground(Color.decode(white));
			displayPanel.setPreferredSize(new Dimension(WIDTH-BORDER, 440));
			panel.add(displayPanel);
			
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
	
	//METHODS
	public String format(String site) {
		return site.replaceAll(" ", "_").toLowerCase();
	}
	
	
	
	public void getLinks(String site) throws IOException {
		
		Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + site).get();
		Element body = doc.select("div#bodyContent").first();
		Elements links = body.select("a");
		
		//loop through all the hyperlinks
		for(Element l : links) {
			
			if(l.attr(href) == "href") {
				
			}
			
			System.out.println(l);
			
		}
		
		
	}
	
	
	
	//MAIN
	public static void main(String[] args) {
		new WikiGame();
	}
}
