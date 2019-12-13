package bookShop;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

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

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JTree;
import javax.swing.JButton;

public class Services extends JFrame {
	
	// Directory where we've stored the local data files, such as BookShop.owl
    public static final String SOURCE = "./src/main/resources/data/";

    // BookShop ontology namespace
    public static final String Books_NS = "http://www.semanticweb.org/eurit/ontologies/2019/9/untitled-ontology-6#";

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField txtByName;
	private JScrollPane sp=new JScrollPane();

    DefaultListModel listModel = new DefaultListModel();
    private JTextField textField;


	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Services frame = new Services();
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
	public Services() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 497);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Account");
		lblNewLabel.setBounds(529, 23, 84, 26);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(144, 238, 144));
		panel.setBounds(37, 23, 576, 26);
		contentPane.add(panel);
		
		JMenuItem mntmRentABook = new JMenuItem("Rent a Book");
		mntmRentABook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new ChildrenBook().setVisible(true);
				//HomeScreen.home_frame.setVisible(false);
OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
				
				//read ontology model
				FileManager.get().readModel( m, SOURCE + "BookShop.owl" );
				
				String prefix = "prefix BookShop: <" + Books_NS + ">\n" +
		                		"prefix rdfs: <" + RDFS.getURI() + ">\n" +
		                		"prefix owl: <" + OWL.getURI() + ">\n";


				String query_text=  prefix +
							"SELECT ?btitle ?btype ?account ?Days (str(?price) as ?Price) \n" +
							"WHERE { ?x BookShop:ProductType ?btype. "
							+ "  ?x BookShop:BookTitle ?btitle.\n " +
							"?x BookShop:Account ?account. "
							+ "?x BookShop:NumberOfDays ?Days. \n"+
							"?x BookShop:RentPrice ?price \n}";
				
				System.out.println(query_text);
				
				Query query = QueryFactory.create( query_text );
		        QueryExecution qexec = QueryExecutionFactory.create( query, m );
		       
		        /*************************************** Create Arrays for Table Headers and Table Values **********************************/ 
		        List<String> columns = new ArrayList<String>();
		        List<String[]> values = new ArrayList<String[]>();

		        columns.add("Book title");
		        columns.add("Book type");
		        columns.add("Account");
		        columns.add("Days");
		        columns.add("Price");
	            /*******************************************************************************************************************************/

		        try {
		            ResultSet results = qexec.execSelect();
		            int i = 0;
		            while ( results.hasNext() ) {
		                QuerySolution qs = results.next();
		               
		                /****************************  Assign query data to array. That will populate JTable **************************/
		                values.add(new String[] {qs.get("btitle").toString(), qs.get("btype").toString(), qs.get("account").toString(),qs.get("Days").toString(), qs.get("Price").toString()});
		               /**************************************************************************************************************/
		                
		                System.out.println(qs.get("btitle"));
		                i++;
		            }
		            
		         /*************************Create Table and tableModel******************************/
		            TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
		            JTable table = new JTable(tableModel);
		            table.setForeground(Color.DARK_GRAY);
		            table.setBackground(Color.lightGray);
		            table.setRowHeight(35);		
		            sp.setViewportView(table);		           
		            sp.setBounds(130, 150, 500,250);
		            contentPane.add(sp);
		            contentPane.repaint();
		          /*********************************************************************************/
		        }
		        finally {
		            qexec.close();
		        }



			}
		});
		mntmRentABook.setBounds(0, 121, 97, 22);
		contentPane.add(mntmRentABook);
		
		JMenuItem mntmOrderABook = new JMenuItem("Order a Book");
		mntmOrderABook.setBounds(0, 143, 107, 22);
		contentPane.add(mntmOrderABook);
		
		textField = new JTextField();
		textField.setBounds(119, 74, 349, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(465, 74, 89, 23);
		contentPane.add(btnSearch);
		
		JMenuItem mntmReturn = new JMenuItem("Return a Book");
		mntmReturn.setBounds(0, 164, 107, 22);
		contentPane.add(mntmReturn);
		
		JMenuItem mntmRefund = new JMenuItem("Buy and Sale");
		mntmRefund.setBounds(0, 188, 107, 22);
		contentPane.add(mntmRefund);
	}
}
