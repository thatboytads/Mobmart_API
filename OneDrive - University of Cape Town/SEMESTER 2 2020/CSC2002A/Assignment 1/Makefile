JAVAC=/usr/bin/javac
.SUFFIXES: .java .class

SRCDIR=src/
BINDIR=bin/


.PHONY: docs

all:
	javac -d bin $(SRCDIR)*.java

clean:
	rm ${BINDIR}*.class

runAVL1:
	java -cp bin/ parCell

runAVL2:
	java -cp bin/ parcCell m

runBST1:
	java -cp bin/ parallel

runBST2:
	java -cp bin/ parallel m

docs:
	javadoc -d docs/ src/*.java

cleandocs:
	rm -r docs/*