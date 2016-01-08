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