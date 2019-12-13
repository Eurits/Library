package bookShop;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HomeScreen extends JFrame {
	// Directory where we've stored the local data files, such as BookShop.owl
    public static final String SOURCE = "./src/main/resources/data/";

    // BookShop ontology namespace
    public static final String Books_NS = "http://www.semanticweb.org/eurit/ontologies/2019/9/untitled-ontology-6#";

	private JPanel contentPane;
	protected static Object home_frame;
	private JTextField txtByName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeScreen frame = new HomeScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HomeScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 671, 501);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(144, 238, 144));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 84, 221, 352);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("");
		panel.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\eurit\\Documents\\ARU Computer Science\\Semantic data technologies\\jena-3.13.1-source-release\\jena-3.13.1\\jena-examples\\src\\main\\resources\\images\\book1.jpg"));
		
		JLabel lblNewLabel_1 = new JLabel("Welcome Readers!");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1.setBounds(301, 50, 138, 43);
		contentPane.add(lblNewLabel_1);
		
		JButton btnBook = new JButton("Books");
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Books().setVisible(true);
				//((Window) HomeScreen.home_frame).setVisible(false);
			}
		});
		btnBook.setBounds(323, 177, 89, 23);
		contentPane.add(btnBook);
		
		JButton btnServices = new JButton("Services");
		btnServices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Services().setVisible(true);
				//((Window) HomeScreen.home_frame).setVisible(false);
				
			}
		});
		btnServices.setBounds(323, 245, 89, 23);
		contentPane.add(btnServices);
		
		JButton btnFindUs = new JButton("Find Us");
		btnFindUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FindUs().setVisible(true);
				//((Window) HomeScreen.home_frame).setVisible(false);
			}
		});
		btnFindUs.setBounds(323, 312, 89, 23);
		contentPane.add(btnFindUs);
	}

}
