package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.swing.GroupLayout.Alignment;

import util.MapUtil;


public class OSSParser {

	private Map <String, Integer> dictionary = new HashMap();
	private Map <String, String> complements = new HashMap();
	private List sources = new ArrayList();
	private List dirs = new ArrayList();
	private List<String> reservedWords = new ArrayList<String>();
	private OutputStream os = null;
	private OutputStreamWriter osw = null; 
    private BufferedWriter bw = null; 
	
	public OSSParser(String[] args) {
		// TODO Auto-generated constructor stub
		
		String dirTrab = System.getProperty("user.dir");
		String format = "java";
		if (args.length==0) {
			dirTrab = System.getProperty("user.dir");
		} else {
		
			dirTrab=args[0];
			format = args[1];
			for (int i=2; i<args.length; i++){
				reservedWords.add(args[i]);
			}
			
		}
		
		//JFileChooser fileChooser = new JFileChooser();
		//fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		//File selectedFile = new File("C:\\\\temp\\\\ALIN-cmt-confOf.rdf");
		/*int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		    selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
		*/
		dirs.add(dirTrab);
		
		try {
			os = new FileOutputStream("/Users/fabiomarcosdeabreusantos/Documents/JabRefImports.csv");
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);
	    	bw.write("File,Word,Complement \n");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Starting----------------");			
		
		for (int i=0; i<dirs.size(); i++) {
			processDir(dirs.get(i));	
		}
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printDictionary(dictionary, "dict");	    
		printDictionary(complements, "complement");
	    
	}


	private void printDictionary(Map dict, String label) {
		// TODO Auto-generated method stub
		//Map<String, Integer> treeMap = new TreeMap<String, Integer>(dictionary);
		Map sortedMap = MapUtil.sortByValue(dict);
		
		sortedMap.forEach((k,v)->System.out.println(label + k + " # : " + v));
	}


	private void processDir(Object obj) {
		// TODO Auto-generated method stub
		String dir = (String) obj;
		System.out.println("Processing ... "+dir);
		File folder = new File(dir);
		String source; 
		//processFile(selectedFile);
		File[] listOfFiles = folder.listFiles();
		ArrayList<File> files = new ArrayList<>();
		
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());
		        files.add(listOfFiles[i]);
		        source = processFile(listOfFiles[i]);
		        sources.add(source);
		        boolean isNew;
		        if (sources!=null) {
		        	/*isNew = alinhamentos.add(alignment);
		        	if (!isNew) {
		        		System.out.println("Alinhamento igual:"+ listOfFiles[i].getName());
		        		alinhamentosDuplicate.add(alignment);
		        	}
		        	
		        	boolean found = false;
		        	for (Iterator i1 = alinhamentos.iterator(); i1.hasNext(); ) {
		        		Alignment alinAux = (Alignment) i1.next();
		        		if (alinAux.getOntologia1().getName() .equals( alignment.getOntologia1().getName()) && alinAux.getOntologia2().getName().equals( alignment.getOntologia2().getName())) {
		        			found = true;
		        		}
		        	}
		        	if (!found) {
		        		alinhamentos.add(alignment);
		        	} else {
		        		alinhamentosDuplicate.add(alignment);
		        	} */
		        }
		    } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		        dirs.add(listOfFiles[i].getAbsolutePath());
		    }
	    }
	}

	private String processFile(File selectedFile) {
		// TODO Auto-generated method stub

	    System.out.println("Arquivo da vez - "+selectedFile.getName());
	    
		if (selectedFile.getName().endsWith("java")){
			InputStream is = null;
			try {
				is = new FileInputStream(selectedFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader br = new BufferedReader(isr);
		    
		    String s = ""; 
		    String token = null;
		    StringTokenizer stk = null;
		    int pos = 0;
			try {

				//Stream st = br.lines(); 
				//st.
				System.out.println("leitura do java "+selectedFile.getName());
				s = br.readLine();// primeira linha do arquivo
				
				//System.out.println(s);
				while (s != null) {
					
					int length = s.length();
					String word = null;
					String complement = null;
					//token = stk.nextToken();
					if (reservedWords.size()==0){
						pos = s.indexOf(" ");
						int i = 0;
						while (i<s.length()&&pos!=-1) {
							
							word = s.substring(i, pos);
							
							insertDictonary(word, dictionary);
							
							i = pos;
							pos = s.indexOf(" ", pos+1);
							
						}
						if (i+1<s.length()) {
							word = s.substring(i+1, s.length()-1);
							insertDictonary(word, dictionary);
						}
					} else {
						pos = s.indexOf(reservedWords.get(0));
						int i = 0;
						if (pos!=-1) {
							
							word = reservedWords.get(0);
							
							//insertComplement(word, complement, complements);
							
							i = pos+word.length();
							
							if (i+1<s.length()-1){
								complement = s.substring(i+1, s.length()-1);
								if (testComplement(complement)){
									insertComplement(word, complement, complements);
									System.out.println(word+" "+complement);
									bw.write(selectedFile.getName()+","+word+","+complement+"\n");
								}
							}
							
						}
						
					}
					s = br.readLine();
					//System.out.println(s);
				}	    
				
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchElementException en) {
				en.printStackTrace();
			
			}
			return s;
		}
		return null;
	}


	private boolean testComplement(String complement) {
		// TODO Auto-generated method stub
		if (complement.indexOf(")")!=-1)
			return false;
		if (complement.indexOf("(")!=-1)
			return false;
		if (complement.indexOf("=")!=-1)
			return false;
		if (complement.indexOf(",")!=-1)
			return false;
		if (complement.indexOf(".")==-1)
			return false;
		return true;
	}


	private void insertDictonary(String word, Map dict) {
		// TODO Auto-generated method stub
		word = word.trim();
		if (word.lastIndexOf(";")!=-1) {
			word = word.substring(0,word.length()-1);
		}
		if (dict.containsKey(word)) {
			Integer value = (Integer) dict.get(word);
			value ++;
			dict.remove(word);
			dict.put(word, value);
			
		} else {
			dict.put(word, 1);
		}
	}
	
	private void insertComplement(String word, String complement, Map dict) {
		// TODO Auto-generated method stub
		word = word.trim();
		if (word.lastIndexOf(";")!=-1) {
			word = word.substring(0,word.length()-1);
		}
		
		dict.put(word, complement);
		
	}

}
