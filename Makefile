# Compiler and flags
CXX := javac
CYY := java
SRCDIR := src
OUTDIR := bin
# Run rule
run:
	@echo runing . . .
	@$(CYY) -cp $(OUTDIR)/com/example/$(File)
# Compile rule
compile:
	@echo compiling . . .
	@$(CXX) $(SRCDIR)/com/example/*.java -d $(OUTDIR)