# image filter program makefile
# Bina Mukuyamba
# 10/08/2022


.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin
JAVAC=/usr/bin/javac
JAVA=/usr/bin/java

$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES2= MedianFilterSerial.class\
          MeanFilterSerial.class\
          MedianFilterParallel.class\
          MeanFilterParallel.class\

CLASSES=$(CLASSES2:%.class=$(BINDIR)/%.class)
default: $(CLASSES)

run: $(CLASSES)
	$(JAVA) -cp $(BINDIR) MedianFilterSerial
	$(JAVA) -cp $(BINDIR) MeanFilterSerial
	$(JAVA) -cp $(BINDIR) MedianFilterParallel
	$(JAVA) -cp $(BINDIR) MeanFilterParallel
clean:
	rm $(BINDIR)/*.class