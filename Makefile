# Malika Peters
# PTRMAL007
# Makefile

JAVAC=/usr/bin/javac
JAVA=/usr/bin/java
PACKAGE=barScheduling
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin

$(BINDIR)/$(PACKAGE)/%.class:$(SRCDIR)/$(PACKAGE)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<
	
CLASSES=DrinkOrder.class Barman.class Patron.class \
		SchedulingSimulation.class
	
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/$(PACKAGE)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/$(PACKAGE)/*.class
	
run: $(CLASS_FILES)
	$(JAVA) -cp $(BINDIR) $(PACKAGE).SchedulingSimulation $(ARGS)
