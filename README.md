_**Bar Scheduling Simulation**_
This README provides detailed instructions on how to compile and run the bar scheduling simulation with two different scheduling algorithms: 
First-Come, First-Served (FCFS) and Shortest Job First (SJF). 
Each algorithm is managed within its own directory, containing the necessary Java source files and compilation scripts.

**Directory Structure**
1. FCFS: This directory contains all the necessary files for running the simulation using the FCFS scheduling algorithm.
2. SJF: This directory is set up for the SJF scheduling algorithm.
  -  Each of these directories contains:

bin/: Directory for compiled .class files.
src/: Contains all .java source files.
data/: (if present) Contains data generated by the simulation.

**Prerequisites**
1. Ensure Java is installed on your system. You can check by running:
java -version
If Java is not installed, download and install the Java Development Kit (JDK) from Oracle's JDK Downloads.

**Compilation and Execution Instructions**
Using Makefile
A Makefile is provided in both the FCFS and SJF directories to simplify the compilation and execution process. Here’s how to use it:

**FCFS** Algorithm:
1. Change to the FCFS directory:
    cd path/to/Assignment5/FCFS
2. Clean previous compilations:
    make clean
3. Compile the source files:
    make
4. Execute the simulation with the desired number of patrons:
    java -cp bin barScheduling.SchedulingSimulation <number_of_patrons>
5. Example for 50 patrons:
    java -cp bin barScheduling.SchedulingSimulation 50
   
**SJF** Algorithm:
1. Change to the SJF directory:
    cd path/to/Assignment5/SJF

Follow the same steps (2-4) as listed for FCFS to clean, compile, and run the simulation.

**Manual Compilation and Execution:**
If you choose not to use the Makefile, you can manually compile and run the simulations as follows:
Navigate to the appropriate directory (FCFS or SJF).
1. Compile all Java files in the src directory to the bin directory:
    javac -d bin src/*.java
2. Run the compiled program:
    java -cp bin barScheduling.SchedulingSimulation <number_of_patrons>
    
**Additional Information:**
Ensure the terminal's current directory is set to either FCFS or SJF when compiling or executing commands.
Adjust the number of patrons as needed to simulate different loads on the system.
