package bookShop;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;

public class SparqlTest {

	 // Directory where we've stored the local data files, such as BookShop.owl
   public static final String SOURCE = "./src/main/resources/data/";

   // bookshop ontology namespace
   public static final String Books_NS = "http://www.semanticweb.org/eurit/ontologies/2019/9/untitled-ontology-6#";
   

		public static void main(String[] args) {
			//create instance of OntModel class
			OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
			
			//read ontology model
			FileManager.get().readModel( m, SOURCE + "BookShop.owl" );
			
			String prefix = "prefix BookShop: <" + Books_NS + ">\n" +
	                		"prefix rdfs: <" + RDFS.getURI() + ">\n" +
	                		"prefix owl: <" + OWL.getURI() + ">\n";

			String query_text=  prefix +
					"select ?BookTitle where {?x a owl:Class ; " +
					"rdfs:subclassOf BookShop:Book.\n"+
					"}";
			
			//org.apache.jena.query.Query query = QueryFactory.create( query_text );
			
			Query query = QueryFactory.create( query_text );
	        QueryExecution qexec = QueryExecutionFactory.create( query, m );
	        try {
	            ResultSet results = qexec.execSelect();
	            ResultSetFormatter.out( results, m );
	        }
	        finally {
	            qexec.close();
	        }

		}

	


	}



