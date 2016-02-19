@authors Katrina Kerrick, Ian Havens

--
Milestone 1
--

The Design of the Tool
Our tool uses the ASM library to scrape information about classes and aggregate it into dot format, which can be converted using Graphviz into a UML diagram.

In the main method, an ArrayList of classes is used to keep track of all the classes passed in through the program arguments. Each class has its class declaration, list of methods, and list of fields aggregated into its appropriate spot in the ArrayList of classes. The information is collected while the Visitor wrapper is visiting each method/class declaration/field, and it's passed back to the main class via an instance of ClassField. When all of the information is collected and the loop is terminated, a single toString() method can be called to return a string that can be directly printed to the dot file.

Who Did What
We both worked very hard on this assignment. Ian Havens put in around six hours of work, and Katrina Kerrick put in around seven. Originally, Ian wrote the chain of data structures that would hold the class information, but then Katrina revised it. After Katrina committed her revision, Ian refined further and made the code much more elegant. The Visitor classes were given to us by the class, though we did light edits to them. The project was almost entirely paired programming. Katrina wrote the tests and Ian made the hand drawn UML diagrams.

How To Use Our Code
If the code is being run in Eclipse, edit the command line arguments via a GUI to include the necessary classes. If the project is being run via command line, input the arguments after the executable name. After the program is run, run a command to compile a UML diagram using Graphviz using the outputted dot file, named output.dot.

--
Milestone 2
--

The Design of the Tool
We were unsure if there was an ASM library to detect users and associations, so we decided that it would be more prudent to do flow control with if statements and manually detect when uses and associations occurred and to worry about integrating that part of ASM later. Because of this, we only added about fifteen lines of code to the giant method that prints the dot file, and the only "major" change we had to make was to add a parameter to that method. In addition, Ian decided to help simplify the final diagram by preventing interfaces from pointing to uses and associations.

Who Did What
Katrina designed the original way to tell usage and association, and Ian modified it so that it worked properly. Both Ian and Katrina put in about four hours of work. Katrina wrote the tests and Ian made the hand drawn UML diagrams.

How to Use Our Code
IN Eclipse, edit the command line arguments to be the proper classes to be analyzed. Then, run the program. The outputted .dot file should then be run through Graphviz on the command line, and outputs a picture file of the UML diagram.

--
Milestone 3
--

The Design of the Tool
For creating sequence diagrams, we programmed the ClassMethodVisitor to take a second constructor. This constructor would instantiate a special MethodInformation variable, which would hold method information if we turned on sequence diagrams. The method body scraper in MethodInformationVisitor would then call itself recursively and find go five layers deep to find method calls and then print them in order. This is working properly.

Who Did What
Katrina designed and wrote this code for milestone 3, totally 12 hours of work. Ian in the meantime completely refactored milestone 2, spending 11 hours of work. In the end, they merged the two sections of code together, which took about half an hour. Ian wrote the tests this time, and Katrina made the hand drawn sequence diagrams.

How to Use Our Code
In Eclipse, edit hte command line arguments to be the classname and the method to analyze. Then, run the program. The text outputted to the console should then be copied and pasted to sdedit, which outputs a picture of the sequence diagram.

--
Milestone 4
--

The Design of the Tool
For identifying the a Singleton, the ClassMethodVisitor and ClassFieldVisitor now contain checks for static methods and fields unique to Singletons and store respective classes in separate lists based on the visitor. In order to ensure that a Singleton is found, another class has been made to combine the two lists and if there is an intersection, the analyzed class will be added to a third list solely composed of Singletons. 

Who Did What
Katrina designed and wrote the functionality for this milestone, in about an hour. Ian, once functionality was added, designed the design pattern to allow for easy addition of other pattern detection behaviors; this took about an hour and a half. Tests were written by Katrina, where test classes were made to be used by the automated testing. UML is created by code; no Singletons are present in project's source code.

How to Use Our Code
IN Eclipse, edit the command line arguments to be the proper classes to be analyzed. Then, run the program. The outputted .dot file should then be run through Graphviz on the command line, and outputs a picture file of the UML diagram.

--
Milestone 5
--

The Design of the Tool
There was major refactoring for this milestone, with UML patterns abstracted out into seperate classes in order to keep track of them and add modularity to the code. These methods will keep track of static ArrayLists of classes, and then a method can be invoked to obtain these classes for when the UML diagram is being printed out for coloring and printing.

Who Did What
Katrina designed the requirements for this milestone, wrote the tests and README, and Ian implemented the code and algorithm. It took about ten hours of work.

How to Use Our Code
In Eclipse, edit the command line arguments so they point to whatever classes you'd like to generate a diagram for. Then, run the code and paste the contents of the output file into GraphViz.

--
Milestone 6
--

The Design of our Tool
Because of the major refactoring from the last milestone, this milestone was able to be implemented under the same design with little trouble! The Composite pattern follows the same design pattern as the other detectors, and was able to be fit right in next to all of the other classes.

Who Did What
Katrina and Ian worked together to design the needs for the algorithm, and then Ian programmed and explained to Katrina how he implemented the code. Katrina wrote the tests and updated the README. It took about six hours of work.

How to Use Our Code
In Eclipse, edit the command line arguments so they point to whatever classes you'd like to generate a diagram for. Then, run the code and paste the contents of the output file into GraphViz.

--
Milestone 7
--
To make changes to our code:
To add a detector: create new IDetector, which follows the visitor pattern, with all necessary requirements to validly detect what you are looking for. Add the new "phase" to the PhaseMap.
To add a new behavior to display in UML:  create new RecordBehavior with all properties you wish to add, must be valid for plain text read by GraphViz. Add new behavior to RecordBehaviorMap.
