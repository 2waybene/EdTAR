package com.fbs.JSwing2.html;

/*
* MiniBrowser1.java
* A test bed for the JEditorPane and a custom editor kit.
* This extremely simple browser has a text field for typing in
* new urls, a JEditorPane to display the HTML page, and a status
* bar to display the contents of hyperlinks the mouse passes over.
*/
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;

import java.awt.*;
import java.io.File;
public class MiniBrowser1 extends JFrame {
	private JEditorPane jep;

	public MiniBrowser1(String startingUrl) {
		// Ok, first just get a screen up and visible, with an appropriate
		// 	handler in place for the kill window command
		super("MiniBrowser");
		setSize(400,300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Now set up our basic screen components, the editor pane, the
		// text field for URLs, and the label for status and link information.
		JPanel urlPanel = new JPanel();
		urlPanel.setLayout(new BorderLayout());
		JTextField urlField = new JTextField(startingUrl);
		urlPanel.add(new JLabel("Site: "), BorderLayout.WEST);
		urlPanel.add(urlField, BorderLayout.CENTER);
		final JLabel statusBar = new JLabel(" ");
		// Here’s the editor pane configuration. It’s important to make
		// the "setEditable(false)" call. Otherwise, our hyperlinks won’t
		// work. (If the text is editable, then clicking on a hyperlink
		// simply means that you want to change the text...not follow the
		// link.)
		jep = new JEditorPane();
		jep.setEditable(false);
		try {
			jep.setPage(startingUrl);
			}
			catch(Exception e) {
				statusBar.setText("Could not open starting page. Using a blank.");
			}
		JScrollPane jsp = new JScrollPane(jep);
		// and get the GUI components onto our content pane
		getContentPane().add(jsp, BorderLayout.CENTER);
		getContentPane().add(urlPanel, BorderLayout.NORTH);
		getContentPane().add(statusBar, BorderLayout.SOUTH);
		// and last but not least, hook up our event handlers
		urlField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					jep.setPage(ae.getActionCommand());
				}
				catch(Exception e) {
					statusBar.setText("Error: " + e.getMessage());
				}
			}
			});
			jep.addHyperlinkListener(new SimpleLinkListener1(jep, urlField,
					statusBar));
		}
	
		public static void main(String args[]) {
			String url = "";
			if (args.length == 1) {
				url = args[0];
				if (!(url.startsWith("http:") || url.startsWith("file:"))) {
					// If it’s not a fully qualified url, assume it’s a file
					if (url.startsWith("/")) {
						// Absolute path, so just prepend "file:"
						url = "file:" + url;
					}
					else {
						try {
							// assume it’s relative to the starting point...
							File f = new File(url);
							url = f.toURI().toURL().toString();
						}
						catch (Exception e) {
							url = "http://www.oreilly.com";
						}
					}
				}
			}
			else {
				url = "http://www.oreilly.com";
			}
			new MiniBrowser1(url).setVisible(true);
		}
}


		
	