package lab4.client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Set;

import javax.print.attribute.standard.Media;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
/**
 * 
 * @author 
 * Author: Jake Bruemmer
 * Course: CSE 132 Lab B
 * Email: jakebruemmer@wustl.edu
 * Lab: CSE 132 Lab 4
 * File Name: WeasleyClock.java
 * 
 * GUI for the Weasley model. Continuously updates based on the data that comes from the Client.
 *
 */

public class WeasleyClock extends JFrame {

	private JPanel contentPane;
	private Client client;
	private JTable table;
	private JTextField textField_AddLocation, textField_RemoveLocation, textField_AddPerson, textField_RemovePerson, textField_WhosThere;

	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WeasleyClock frame = new WeasleyClock();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * The frame runs the instance Client in  different thread. The lists in the gui are updated 
	 * in real time from the client class. Multiple WeasleyClocks can connect to the same server.
	 * All WeasleyClocks that are connected to the same server will display the information of the
	 * model that is on that server. Any additions or deletions of Persons or Locations that a 
	 * particular WeasleyClock creates will show up on all of the WeasleyClocks, but the WeasleyClock
	 * that actually made a change will be shown what they did in the reporting area. 
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public WeasleyClock() throws UnknownHostException, IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 850, 480);
		contentPane = new JPanel();
		contentPane.setBackground(Color.YELLOW);	
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		client = new Client();

		final JList peopleList = new JList();
		peopleList.setFont(new Font("Tahoma", Font.PLAIN, 11));
		peopleList.setBackground(Color.CYAN);
		peopleList.setBounds(474, 180, 169, 130);
		contentPane.add(peopleList);

		JScrollPane peopleListScroll = new JScrollPane(peopleList);
		peopleListScroll.setBounds(474, 180, 169, 130);
		contentPane.add(peopleListScroll);

		JLabel lblPeople = new JLabel("People");
		lblPeople.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPeople.setBounds(522, 155, 74, 24);
		contentPane.add(lblPeople);

		final JList locationList = new JList();
		locationList.setFont(new Font("Tahoma", Font.PLAIN, 11));
		locationList.setBackground(Color.CYAN);
		locationList.setBounds(653, 180, 169, 130);
		contentPane.add(locationList);

		JScrollPane locationListScroll = new JScrollPane(locationList);
		locationListScroll.setBounds(653, 180, 169, 130);
		contentPane.add(locationListScroll);

		JLabel lblLocations = new JLabel("Locations");
		lblLocations.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblLocations.setBounds(690, 155, 104, 24);
		contentPane.add(lblLocations);

		final JTextArea personLoc = new JTextArea();
		personLoc.setForeground(Color.WHITE);
		personLoc.setFont(new Font("Tahoma", Font.BOLD, 16));
		personLoc.setBackground(Color.BLACK);
		personLoc.setBounds(22, 49, 442, 261);
		personLoc.setEditable(false);
		contentPane.add(personLoc);

		JScrollPane personLocScroll = new JScrollPane(personLoc);
		personLocScroll.setBounds(22, 49, 442, 261);
		contentPane.add(personLocScroll);

		JLabel lblWhereIsEveryone = new JLabel("Where is everyone?");
		lblWhereIsEveryone.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWhereIsEveryone.setBounds(132, 11, 209, 27);
		contentPane.add(lblWhereIsEveryone);

		textField_AddLocation = new JTextField();
		textField_AddLocation.setBounds(653, 312, 169, 20);
		contentPane.add(textField_AddLocation);
		textField_AddLocation.setColumns(10);

		textField_RemoveLocation = new JTextField();
		textField_RemoveLocation.setBounds(653, 373, 170, 20);
		contentPane.add(textField_RemoveLocation);
		textField_RemoveLocation.setColumns(10);

		textField_AddPerson = new JTextField();
		textField_AddPerson.setBounds(474, 312, 169, 20);
		contentPane.add(textField_AddPerson);
		textField_AddPerson.setColumns(10);

		textField_RemovePerson = new JTextField();
		textField_RemovePerson.setBounds(474, 373, 169, 20);
		contentPane.add(textField_RemovePerson);
		textField_RemovePerson.setColumns(10);

		textField_WhosThere = new JTextField();
		textField_WhosThere.setBounds(22, 321, 104, 20);
		contentPane.add(textField_WhosThere);
		textField_WhosThere.setColumns(10);

		final JTextArea reportingArea = new JTextArea();
		reportingArea.setBounds(474, 119, 348, 35);
		reportingArea.setEditable(false);
		contentPane.add(reportingArea);

		JScrollPane reportingAreaScroll = new JScrollPane(reportingArea);
		reportingAreaScroll.setBounds(474, 119, 348, 35);
		contentPane.add(reportingAreaScroll);		

		final JTextArea bottomLeftTextArea = new JTextArea();
		bottomLeftTextArea.setBackground(Color.BLACK);
		bottomLeftTextArea.setForeground(Color.WHITE);
		bottomLeftTextArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		bottomLeftTextArea.setBounds(22, 352, 226, 79);
		contentPane.add(bottomLeftTextArea);

		JScrollPane bottomLeftTextAreaScroll = new JScrollPane(bottomLeftTextArea);
		bottomLeftTextAreaScroll.setBounds(22, 352, 226, 79);
		contentPane.add(bottomLeftTextAreaScroll);

		JTextArea middleText = new JTextArea();
		middleText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		middleText.setText("\rJake's Weasley Clock. \nThis GUI gives the locations of a set of people in real time. "
				+ "You \ncan add and remove both people and locations by using "
				+ "\nthe buttons below.");

		middleText.setBounds(474, 12, 348, 69);
		middleText.setEditable(false);
		contentPane.add(middleText);

		JButton btnAddPerson = new JButton("Add Person!");
		btnAddPerson.setBounds(474, 343, 169, 23);
		contentPane.add(btnAddPerson);

		Thread t = new Thread(new Runnable() {
			public void run() {
				client.run(peopleList, locationList, personLoc, bottomLeftTextArea, textField_WhosThere);		
			}
		});
		t.start();

		JLabel btnWhosThere = new JLabel("Type a location!");
		btnWhosThere.setBounds(132, 321, 116, 23);
		contentPane.add(btnWhosThere);

		JButton btnRemovePerson = new JButton("Remove Person!");
		btnRemovePerson.setBounds(474, 394, 169, 23);
		contentPane.add(btnRemovePerson);
		btnRemovePerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(client.getPeople().contains(textField_RemovePerson.getText())) {
						client.getDOS().writeUTF("dropPerson " + textField_RemovePerson.getText());
						reportingArea.setText("You removed the person " + textField_RemovePerson.getText() + " from the Weasley.");
						textField_RemovePerson.setText("");
					}
					else {
						reportingArea.setText("Can't do that. " + textField_RemovePerson.getText() + " is not in the model!");
					}
				} 
				catch (IOException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		JButton btnRemoveLocation = new JButton("Remove Location!");
		btnRemoveLocation.setBounds(653, 394, 169, 23);
		contentPane.add(btnRemoveLocation);
		btnRemoveLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(textField_RemoveLocation.getText().equals("limbo")) {
						reportingArea.setText("Sorry, you cannot remove limbo from the model.");
					}
					else if(!client.getPlaces().contains(textField_RemoveLocation.getText())) {
						reportingArea.setText("Can't do that. " + textField_RemoveLocation.getText() + " is not in the model");
					}
					else {
						client.getDOS().writeUTF("dropLocation " + textField_RemoveLocation.getText());
						reportingArea.setText("You removed the location " + textField_RemoveLocation.getText() + " from the Weasley.");
					}
					textField_RemoveLocation.setText("");
				} catch (IOException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnAddPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(!client.getPeople().contains(textField_AddPerson.getText())) {
						client.getDOS().writeUTF("addPerson " + textField_AddPerson.getText());
						reportingArea.setText("You added " + textField_AddPerson.getText() + " as a new person to the Weasley.");
					}
					else {
						reportingArea.setText("Can't do that. " + textField_AddPerson.getText() + " is already in the model!");
					}
					textField_AddPerson.setText("");
				} catch (IOException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		JButton btnAddLocation = new JButton("Add Location!");
		btnAddLocation.setBounds(653, 343, 169, 23);
		contentPane.add(btnAddLocation);
		btnAddLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(!client.getPlaces().contains(textField_AddLocation.getText())) {
						client.getDOS().writeUTF("addLocation " + textField_AddLocation.getText());
						reportingArea.setText("You added " + textField_AddLocation.getText() + " as a new location to the Weasley.");
					}
					else {
						reportingArea.setText("Can't do that. " + textField_AddLocation.getText() + " is already in the model!");
					}
					textField_AddLocation.setText("");
				} catch (IOException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		JLabel lblHeresWhatYou = new JLabel("Here's what you did:");
		lblHeresWhatYou.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblHeresWhatYou.setBounds(474, 92, 219, 27);
		contentPane.add(lblHeresWhatYou);

		JLabel lblNewLabel = new JLabel(new ImageIcon(new URL("http://31.media.tumblr.com/f4bb33e3e98a40d86686678c24426f62/tumblr_n1bt9f9Seu1r7mqm8o1_r1_250.gif")));
		lblNewLabel.setBounds(258, 315, 206, 116);
		contentPane.add(lblNewLabel);


	}
}
