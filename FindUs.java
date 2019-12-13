package bookShop;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;

public class FindUs extends JFrame {
	// Directory where we've stored the local data files, such as BookShop.owl
    public static final String SOURCE = "./src/main/resources/data/";

    // BookShop ontology namespace
    public static final String Books_NS = "http://www.semanticweb.org/eurit/ontologies/2019/9/untitled-ontology-6#";

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField txtByName;
	private JScrollPane sp=new JScrollPane();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FindUs frame = new FindUs();
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
	public FindUs() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 790, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsernm = new JLabel("Username");
		lblUsernm.setBounds(457, 54, 67, 14);
		contentPane.add(lblUsernm);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(534, 54, 39, 14);
		contentPane.add(textArea);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(646, 54, 39, 14);
		contentPane.add(passwordField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(583, 54, 58, 14);
		contentPane.add(lblPassword);
		
		JLabel lblNewAccount = new JLabel("New Account");
		lblNewAccount.setBounds(695, 54, 79, 14);
		contentPane.add(lblNewAccount);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(144, 238, 144));
		panel.setBounds(10, 39, 764, 45);
		contentPane.add(panel);
		
		JLabel lblWeArePresent = new JLabel("You can find us at :");
		lblWeArePresent.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblWeArePresent.setBounds(30, 393, 130, 36);
		contentPane.add(lblWeArePresent);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\eurit\\Documents\\ARU Computer Science\\Semantic data technologies\\jena-3.13.1-source-release\\jena-3.13.1\\jena-examples\\src\\main\\resources\\images\\books-store6.jpg"));
		lblNewLabel.setBounds(20, 79, 744, 303);
		contentPane.add(lblNewLabel);
		
		JButton btnLocation = new JButton("Location");
		btnLocation.setBackground(new Color(175, 238, 238));
		btnLocation.setForeground(new Color(0, 0, 0));
		btnLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
						//new ChildrenBook().setVisible(true);
						//HomeScreen.home_frame.setVisible(false);
		OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
						
						//read ontology model
						FileManager.get().readModel( m, SOURCE + "BookShop.owl" );
						
						String prefix = "prefix BookShop: <" + Books_NS + ">\n" +
				                		"prefix rdfs: <" + RDFS.getURI() + ">\n" +
				                		"prefix owl: <" + OWL.getURI() + ">\n";

						String query_text=  prefix +
								"SELECT ?BookShopName ?name ?address \r\n" + 
								"WHERE {?x  BookShop:hasPlace ?city."
								+ "?x BookShop:Name ?BookShopName."
								+ " ?x BookShop:Address ?address. \r\n"
                                 + "?city BookShop:Name ?name }";
						
						
						
						System.out.println(query_text);
						
						Query query = QueryFactory.create( query_text );
				        QueryExecution qexec = QueryExecutionFactory.create( query, m );
				       
				        /*************************************** Create Arrays for Table Headers and Table Values **********************************/ 
				        List<String> columns = new ArrayList<String>();
				        List<String[]> values = new ArrayList<String[]>();

				      //  columns.add("City");
				        columns.add("Store Name");
				        columns.add("Name");
				        columns.add("Address");
				        
				        
			            /*******************************************************************************************************************************/

				        try {
				            ResultSet results = qexec.execSelect();
				            int i = 0;
				            while ( results.hasNext() ) {
				                QuerySolution qs = results.next();
				               
				                /****************************  Assign query data to array. That will populate JTable **************************/
				                values.add(new String[] { qs.get("?BookShopName").toString(), qs.get("?name").toString(), qs.get("?address").toString()});
				                
				               /**************************************************************************************************************/
				                
				                System.out.println(qs.get("?name"));
				                i++;
				            }
				            
				         /*************************Create Table and tableModel******************************/
				            TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
				            JTable table = new JTable(tableModel);
				            table.setForeground(Color.DARK_GRAY);
				            table.setBackground(Color.lightGray);
				            table.setRowHeight(45);		
				            sp.setViewportView(table);		           
				            sp.setBounds(20,430,650,350);
				            contentPane.add(sp);
				            contentPane.repaint();
				          /*********************************************************************************/
				        }
				        finally {
				            qexec.close();
				        }

					}
				
				
				
			
		});
		btnLocation.setBounds(170, 402, 89, 23);
		contentPane.add(btnLocation);
	}
}
