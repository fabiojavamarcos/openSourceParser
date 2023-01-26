# openSourceParser
Parser to identify libraries in source code. Research project with a huge pipeline that starts with this parser.
Works only with java, C# and C++.

It looks for and reads source code files (Java, C++ or C#) in a directory recursively. It updates the tables project, file, API and file_API. 

/*List of parameters:
		/*List of parameters:

		0: dirTrab ex: /Users/fabiomarcosdeabreusantos/Documents/dev/github/jabref/src

		1: format ex: java

		2: save in db ex: N

		3: save in csv ex: Y

		4: db name to save ex: dev

		5: db user ex: admin

		6: db password ex: 123

		7: project name ex: jabref

		8: outdir (csv) ex: /Users/fabiomarcosdeabreusantos/Documents/dev/github/jabref/

		9: list of reserved words to search (blank separated) ex:     import

		 */





Using the jar:


java -jar OSSParser.jar "/Volumes/GoogleDrive/My Drive/dev/javrefVersions/jabref-5.0-alpha" java N Y teste admin 123 jabref50 "/Users/fd252/OneDrive/Production/ETL1-Pipeline-main/data/outputs/new/jabref/" "import"
		
     
     
