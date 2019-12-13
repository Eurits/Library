package bookShop;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JTextPane;
import javax.swing.DropMode;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButton;

public class Books extends JFrame {
	
	// Directory where we've stored the local data files, such as BookShop.owl
    public static final String SOURCE = "./src/main/resources/data/";

    // Places ontology namespace
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
					Books frame = new Books();
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
	public Books() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 801, 502);
		contentPane = new JPanel();
		contentPane.setToolTipText("Semantic Search");
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setBounds(22, 246, 138, -197);
		list.setToolTipText("Book Genre");
		contentPane.add(list);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(463, 25, 46, 14);
		contentPane.add(formattedTextField);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(388, 25, 70, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPassword.setBounds(519, 25, 57, 14);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(571, 25, 46, 14);
		contentPane.add(passwordField);
		
		JLabel lblNewAccount = new JLabel("New Account");
		lblNewAccount.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNewAccount.setBounds(705, 25, 70, 14);
		contentPane.add(lblNewAccount);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(627, 25, 68, 14);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(144, 238, 144));
		panel.setBounds(0, 0, 785, 49);
		contentPane.add(panel);
		
		JLabel lblBooks = new JLabel("Book Genre");
		lblBooks.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBooks.setBounds(37, 146, 97, 23);
		contentPane.add(lblBooks);
		
		JButton btnChildrenBook = new JButton("Children");
		btnChildrenBook.addActionListener(new ActionListener() {
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
						"SELECT distinct ?btitle (concat(?fname ,' ',?lname) AS ?Name) (str(?price) as ?Price) ?wlanguage (str(?pyear) as ?PublisherYear)  \n" +
						"WHERE {?x a BookShop:Children_book. " +
						 "?x BookShop:hasWriter ?AuthorName."+
						 "?AuthorName BookShop:First_Name ?fname." + 
						"?AuthorName BookShop:Last_Name ?lname."+
						"?x BookShop:Price ?price." +
						 "?x BookShop:BookTitle ?btitle. "+
						 "?x BookShop:Language ?wlanguage. "+
						 "?x BookShop:PublishedYear ?pyear. \n }";
			            
						
			
			System.out.println(query_text);
			
			Query query = QueryFactory.create( query_text );
	        QueryExecution qexec = QueryExecutionFactory.create( query, m );
	       
	        /*************************************** Create Arrays for Table Headers and Table Values **********************************/ 
	        List<String> columns = new ArrayList<String>();
	        List<String[]> values = new ArrayList<String[]>();

	        columns.add("Book Title");
	        columns.add("Author Name ");
	        columns.add("Price");
	        columns.add("Language");
	        columns.add("Published Year");
	        
            /*******************************************************************************************************************************/

	        try {
	            ResultSet results = qexec.execSelect();
	            int i = 0;
	            while ( results.hasNext() ) {
	                QuerySolution qs = results.next();
	               
	                /****************************  Assign query data to array. That will populate JTable **************************/
	                values.add(new String[] {qs.get("btitle").toString(), qs.get("Name").toString(), qs.get("Price").toString(),qs.get("wlanguage").toString(), qs.get("PublisherYear").toString()});
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
		            sp.setBounds(150,180,550,300);
		            contentPane.add(sp);
		            contentPane.repaint();
		          /*********************************************************************************/
		        }
		        finally {
		            qexec.close();
		        }

			}
				
			}
		);
		btnChildrenBook.setBounds(22, 185, 104, 23);
		contentPane.add(btnChildrenBook);
		
		JButton btnRomance = new JButton("Bibliography");
		btnRomance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
				
				//read ontology model
				FileManager.get().readModel( m, SOURCE + "BookShop.owl" );
				
				String prefix = "prefix BookShop: <" + Books_NS + ">\n" +
		                		"prefix rdfs: <" + RDFS.getURI() + ">\n" +
		                		"prefix owl: <" + OWL.getURI() + ">\n";

				String query_text=  prefix +
							"SELECT distinct ?btitle (concat(?fname ,' ',?lname) AS ?Name) "+
							"(str(?price) as ?Price) ?wlanguage (str(?pyear) as ?PublisherYear) \n" +
							"WHERE {?x a BookShop:Bibliography. " +
							 "?x BookShop:hasWriter ?AuthorName."+
							 "?AuthorName BookShop:First_Name ?fname." + 
							"?AuthorName BookShop:Last_Name ?lname."+
							"?x BookShop:Price ?price." +
							 "?x BookShop:BookTitle ?btitle. "+
							 "?x BookShop:Language ?wlanguage. "+
							 "?x BookShop:PublishedYear ?pyear. \n }";
				            
							
				
				System.out.println(query_text);
				
				Query query = QueryFactory.create( query_text );
		        QueryExecution qexec = QueryExecutionFactory.create( query, m );
		       
		        /*************************************** Create Arrays for Table Headers and Table Values **********************************/ 
		        List<String> columns = new ArrayList<String>();
		        List<String[]> values = new ArrayList<String[]>();

		        columns.add("Book Title");
		        columns.add("Author Name ");
		        columns.add("Price");
		        columns.add("Language");
		        columns.add("Published Year");
		        
	            /*******************************************************************************************************************************/

		        try {
		            ResultSet results = qexec.execSelect();
		            int i = 0;
		            while ( results.hasNext() ) {
		                QuerySolution qs = results.next();
		               
		                /****************************  Assign query data to array. That will populate JTable **************************/
		                values.add(new String[] {qs.get("btitle").toString(), qs.get("Name").toString(), qs.get("Price").toString(),qs.get("wlanguage").toString(), qs.get("PublisherYear").toString()});
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
		            sp.setBounds(150,180,550,300);
		            contentPane.add(sp);
		            contentPane.repaint();
		          /*********************************************************************************/
		        }
		        finally {
		            qexec.close();
		        }

			}
				
		
	
			
		});
		btnRomance.setBounds(22, 219, 104, 23);
		contentPane.add(btnRomance);
		
		JCheckBox chckbxPublishedYear = new JCheckBox("2019");
		chckbxPublishedYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String Ano = txtByName.getText().toString().toLowerCase();
				//create instance of OntModel class
				OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
				
				//read ontology model
				FileManager.get().readModel( m, SOURCE + "BookShop.owl" );
				
				String prefix = "prefix BookShop: <" + Books_NS + ">\n" +
		                		"prefix rdfs: <" + RDFS.getURI() + ">\n" +
		                		"prefix owl: <" + OWL.getURI() + ">\n";

				String query_text=  prefix +
							"SELECT ?btitle (str(?Pyear) as ?PublisherYear) ?language" + 
							"	WHERE { ?x a BookShop:Book. "
							+ "?x BookShop:BookTitle ?btitle."
				            + "?x BookShop:BookTitle ?btitle."
				            + "?x BookShop:Language ?language."
				           + "?x BookShop:PublishedYear ?Pyear." 
				        + "FILTER (?Pyear = 2019)";
							
				
				
				
				query_text +=	"} ORDER BY ASC(?btitle)" ;  
				
				System.out.println(query_text);
				
				Query query = QueryFactory.create( query_text );
		        QueryExecution qexec = QueryExecutionFactory.create( query, m );
		       
		        /*************************************** Create Arrays for Table Headers and Table Values **********************************/ 
		        List<String> columns = new ArrayList<String>();
		        List<String[]> values = new ArrayList<String[]>();

		        columns.add("Book ");
		       columns.add("Pyear");
		       columns.add("language");
	            /*******************************************************************************************************************************/

		        try {
		            ResultSet results = qexec.execSelect();
		            int i = 0;
		            while ( results.hasNext() ) {
		                QuerySolution qs = results.next();
		               
		                /****************************  Assign query data to array. That will populate JTable **************************/
		                values.add(new String[] {qs.get("btitle").toString(),qs.get("PublisherYear").toString(),qs.get("language").toString()});
		               /**************************************************************************************************************/
		               // , qs.get("lname").toString()
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
		            sp.setBounds(150,180,550,300);
		            contentPane.add(sp);
		            contentPane.repaint();
		          /*********************************************************************************/
		        }
		        finally {
		            qexec.close();
		        }

			
				
			}
		});
		chckbxPublishedYear.setBounds(17, 401, 97, 23);
		contentPane.add(chckbxPublishedYear);
		
		JLabel lblPublishedYear = new JLabel("Published Year");
		lblPublishedYear.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPublishedYear.setBounds(22, 365, 92, 29);
		contentPane.add(lblPublishedYear);
		
		JCheckBox checkBox = new JCheckBox("2012");
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Ano = txtByName.getText().toString().toLowerCase();
				//create instance of OntModel class
				OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
				
				//read ontology model
				FileManager.get().readModel( m, SOURCE + "BookShop.owl" );
				
				String prefix = "prefix BookShop: <" + Books_NS + ">\n" +
		                		"prefix rdfs: <" + RDFS.getURI() + ">\n" +
		                		"prefix owl: <" + OWL.getURI() + ">\n";

				String query_text=  prefix +
							"SELECT ?btitle (str(?Pyear) as ?PublisherYear) ?language" + 
							"	WHERE { ?x a BookShop:Book. "
							+ "?x BookShop:BookTitle ?btitle."
				            + "?x BookShop:BookTitle ?btitle."
				            + "?x BookShop:Language ?language."
				           + "?x BookShop:PublishedYear ?Pyear." 
				        + "FILTER (?Pyear = 2012)";
				
				
				
				query_text +=	"} ORDER BY ASC(?btitle)" ;  
				
				System.out.println(query_text);
				
				Query query = QueryFactory.create( query_text );
		        QueryExecution qexec = QueryExecutionFactory.create( query, m );
		       
		        /*************************************** Create Arrays for Table Headers and Table Values **********************************/ 
		        List<String> columns = new ArrayList<String>();
		        List<String[]> values = new ArrayList<String[]>();

		        columns.add("Book ");
		       columns.add("Pyear");
		       columns.add("language");
	            /*******************************************************************************************************************************/

		        try {
		            ResultSet results = qexec.execSelect();
		            int i = 0;
		            while ( results.hasNext() ) {
		                QuerySolution qs = results.next();
		               
		                /****************************  Assign query data to array. That will populate JTable **************************/
		                values.add(new String[] {qs.get("btitle").toString(),qs.get("PublisherYear").toString(),qs.get("language").toString()});
		               /**************************************************************************************************************/
		               // , qs.get("lname").toString()
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
		            sp.setBounds(150,180,550,300);
		            contentPane.add(sp);
		            contentPane.repaint();
		          /*********************************************************************************/
		        }
		        finally {
		            qexec.close();
		        }

				
			}
		});
		checkBox.setBounds(17, 433, 97, 23);
		contentPane.add(checkBox);
		
		txtByName = new JTextField();
		txtByName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtByName.setToolTipText("Enter Name");
		txtByName.setBounds(199, 122, 310, 28);
		contentPane.add(txtByName);
		txtByName.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String Book_Title = txtByName.getText().toString().toLowerCase();
				//create instance of OntModel class
				OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
				
				//read ontology model
				FileManager.get().readModel( m, SOURCE + "BookShop.owl" );
				
				String prefix = "prefix BookShop: <" + Books_NS + ">\n" +
		                		"prefix rdfs: <" + RDFS.getURI() + ">\n" +
		                		"prefix owl: <" + OWL.getURI() + ">\n";

				String query_text=  prefix +
							"SELECT ?btitle" + 
							"	WHERE { ?x a BookShop:Book. "
							+ "?x BookShop:BookTitle ?btitle. ";
							//+ "?x BookShop:Last_Name ?lname \r\n"  ;
								
				if(Book_Title != null && !Book_Title.isEmpty()) {
						query_text += "FILTER(regex(str(?btitle),\""+Book_Title+"\",\"i\")) ";
				}
				
				query_text +=	"} ORDER BY ASC(?btitle)" ;  
				
				System.out.println(query_text);
				
				Query query = QueryFactory.create( query_text );
		        QueryExecution qexec = QueryExecutionFactory.create( query, m );
		       
		        /*************************************** Create Arrays for Table Headers and Table Values **********************************/ 
		        List<String> columns = new ArrayList<String>();
		        List<String[]> values = new ArrayList<String[]>();

		        columns.add("Books");
		       // columns.add("Last Name");
	            /*******************************************************************************************************************************/

		        try {
		            ResultSet results = qexec.execSelect();
		            int i = 0;
		            while ( results.hasNext() ) {
		                QuerySolution qs = results.next();
		               
		                /****************************  Assign query data to array. That will populate JTable **************************/
		                values.add(new String[] {qs.get("btitle").toString()});
		               /**************************************************************************************************************/
		               // , qs.get("lname").toString()
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
		            sp.setBounds(150,180,550,300);
		            contentPane.add(sp);
		            contentPane.repaint();
		          /*********************************************************************************/
		        }
		        finally {
		            qexec.close();
		        }

			}
		});
			
		
		btnSearch.setBounds(508, 122, 89, 28);
		contentPane.add(btnSearch);
		
		JLabel lblProductType = new JLabel("Language");
		lblProductType.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProductType.setBounds(32, 257, 90, 23);
		contentPane.add(lblProductType);
		
		JRadioButton rdbtnEnglish = new JRadioButton("English");
		rdbtnEnglish.addActionListener(new ActionListener() {
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
						"SELECT distinct ?btitle  ?language  \n" + 
						"	WHERE { ?x a BookShop:Book. \n "+
			             "?x BookShop:BookTitle ?btitle."+
			             "?x BookShop:Language ?language. \n"+
			          " Filter(str(?language)='English') \n}";
							
				System.out.println(query_text);
				
				Query query = QueryFactory.create( query_text );
		        QueryExecution qexec = QueryExecutionFactory.create( query, m );
		       
		        /*************************************** Create Arrays for Table Headers and Table Values **********************************/ 
		        List<String> columns = new ArrayList<String>();
		        List<String[]> values = new ArrayList<String[]>();

		        columns.add("Book");
		        columns.add("Language");
		        
		        
	            /*******************************************************************************************************************************/

		        try {
		            ResultSet results = qexec.execSelect();
		            int i = 0;
		            while ( results.hasNext() ) {
		                QuerySolution qs = results.next();
		               
		                /****************************  Assign query data to array. That will populate JTable **************************/
		                values.add(new String[] {qs.get("?btitle").toString(), qs.get("?language").toString()});
		               /**************************************************************************************************************/
		                
		                System.out.println(qs.get("?btitle"));
		                i++;
		            }
		            
		         /*************************Create Table and tableModel******************************/
		            TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
		            JTable table = new JTable(tableModel);
		            table.setForeground(Color.DARK_GRAY);
		            table.setBackground(Color.lightGray);
		            table.setRowHeight(35);		
		            sp.setViewportView(table);		           
		            sp.setBounds(150,180,550,300);
		            contentPane.add(sp);
		            contentPane.repaint();
		          /*********************************************************************************/
		        }
		        finally {
		            qexec.close();
		        }
			}
		});
		rdbtnEnglish.setBounds(17, 287, 109, 23);
		contentPane.add(rdbtnEnglish);
		
		JRadioButton rdbtnPortugues = new JRadioButton("Portugues");
		rdbtnPortugues.addActionListener(new ActionListener() {
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
						"SELECT distinct ?btitle  ?language  \n" + 
						"	WHERE { ?x a BookShop:Book. \n "+
			             "?x BookShop:BookTitle ?btitle."+
			             "?x BookShop:Language ?language. \n"+
			          " Filter(str(?language)='Portugues') \n}";
							
				System.out.println(query_text);
				
				Query query = QueryFactory.create( query_text );
		        QueryExecution qexec = QueryExecutionFactory.create( query, m );
		       
		        /*************************************** Create Arrays for Table Headers and Table Values **********************************/ 
		        List<String> columns = new ArrayList<String>();
		        List<String[]> values = new ArrayList<String[]>();

		        columns.add("Book");
		        columns.add("Language");
		        
		        
	            /*******************************************************************************************************************************/

		        try {
		            ResultSet results = qexec.execSelect();
		            int i = 0;
		            while ( results.hasNext() ) {
		                QuerySolution qs = results.next();
		               
		                /****************************  Assign query data to array. That will populate JTable **************************/
		                values.add(new String[] {qs.get("?btitle").toString(), qs.get("?language").toString()});
		               /**************************************************************************************************************/
		                
		                System.out.println(qs.get("?btitle"));
		                i++;
		            }
		            
		         /*************************Create Table and tableModel******************************/
		            TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
		            JTable table = new JTable(tableModel);
		            table.setForeground(Color.DARK_GRAY);
		            table.setBackground(Color.lightGray);
		            table.setRowHeight(35);		
		            sp.setViewportView(table);		           
		            sp.setBounds(150,180,550,300);
		            contentPane.add(sp);
		            contentPane.repaint();
		          /*********************************************************************************/
		        }
		        finally {
		            qexec.close();
		        }
			}
			
		});
		rdbtnPortugues.setBounds(17, 311, 109, 23);
		contentPane.add(rdbtnPortugues);
		
		JRadioButton rdbtnSpanish = new JRadioButton("Spanish");
		rdbtnSpanish.addActionListener(new ActionListener() {
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
						"SELECT distinct ?btitle  ?language  \n" + 
						"	WHERE { ?x a BookShop:Book. \n "+
			             "?x BookShop:BookTitle ?btitle."+
			             "?x BookShop:Language ?language. \n"+
			          " Filter(str(?language)='Spanish') \n}";
							
				System.out.println(query_text);
				
				Query query = QueryFactory.create( query_text );
		        QueryExecution qexec = QueryExecutionFactory.create( query, m );
		       
		        /*************************************** Create Arrays for Table Headers and Table Values **********************************/ 
		        List<String> columns = new ArrayList<String>();
		        List<String[]> values = new ArrayList<String[]>();

		        columns.add("Book");
		        columns.add("Language");
		        
		        
	            /*******************************************************************************************************************************/

		        try {
		            ResultSet results = qexec.execSelect();
		            int i = 0;
		            while ( results.hasNext() ) {
		                QuerySolution qs = results.next();
		               
		                /****************************  Assign query data to array. That will populate JTable **************************/
		                values.add(new String[] {qs.get("?btitle").toString(), qs.get("?language").toString()});
		               /**************************************************************************************************************/
		                
		                System.out.println(qs.get("?btitle"));
		                i++;
		            }
		            
		         /*************************Create Table and tableModel******************************/
		            TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
		            JTable table = new JTable(tableModel);
		            table.setForeground(Color.DARK_GRAY);
		            table.setBackground(Color.lightGray);
		            table.setRowHeight(35);		
		            sp.setViewportView(table);		           
		            sp.setBounds(150,180,550,300);
		            contentPane.add(sp);
		            contentPane.repaint();
		          /*********************************************************************************/
		        }
		        finally {
		            qexec.close();
		        }
			
			}
		});
		rdbtnSpanish.setBounds(17, 337, 109, 23);
		contentPane.add(rdbtnSpanish);
		
		
		//btnNewButton_1.setBounds(539, 111, 89, 23);
		//contentPane.add(btnNewButton_1);
	}
	
	

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

