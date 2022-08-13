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

medianSerial: $(CLASSES)
	$(JAVA) -cp $(BINDIR) MedianFilterSerial $(input) $(output) $(window)
	
meanSerial: $(CLASSES)
	$(JAVA) -cp $(BINDIR) MeanFilterSerial $(input) $(output) $(window)

medianParallel: $(CLASSES)
	$(JAVA) -cp $(BINDIR) MedianFilterParallel $(input) $(output) $(window)

meanParallel: $(CLASSES)
	$(JAVA) -cp $(BINDIR) MeanFilterParallel $(input) $(output) $(window)


clean:
	rm $(BINDIR)/*.class