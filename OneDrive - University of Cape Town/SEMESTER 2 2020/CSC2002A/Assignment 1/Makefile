JAVAC=/usr/bin/javac
.SUFFIXES: .java .class

SRCDIR=src/
BINDIR=bin/


.PHONY: docs

all:
	javac -d bin $(SRCDIR)*.java

clean:
	rm ${BINDIR}*.class

run1:
	java -cp bin/ parCell

run2:
	java -cp bin/ parcCell large_in.txt large_out.txt

run3:
	java -cp bin/ parallel

run4:
	java -cp bin/ parallel m


run5:
	java -cp bin/ seqCell large_in.txt large_out.txt

run6:
	java -cp bin/ seqCell m


docs:
	javadoc -d docs/ src/*.java

cleandocs:
	rm -r docs/*