# Needleman-Wunsch Global Sequence Alignment

A from-scratch Java implementation of the **Needleman-Wunsch algorithm** for optimal global alignment of protein sequences, written during my first semester of Bioinformatics at LMU & TUM Munich (January 2025).

## Features

- Dynamic programming score matrix with linear gap penalty
- Traceback to reconstruct the optimal alignment
- Substitution scores from **BLOSUM62** (any matrix in CSV format can be used)
- FASTA reader; aligns the first sequence against all others in the file
- Unit tests on a small hand-checkable scoring matrix

## Usage

```bash
javac -d out src/*.java
java -cp out Main
```

The program reads `assets/sequences.fasta`, aligns the first sequence against every other one with BLOSUM62 and a gap penalty of −9, and prints the alignment score and the alignment for each pair.

## Structure

```
src/NeedlemanWunsch.java   score matrix, traceback, alignment
src/IO.java                FASTA and substitution-matrix parsing
src/Main.java              example run
src/Tests.java             tests
assets/                    BLOSUM62, test matrix, example sequences
```

This was my first alignment implementation; later I extended the idea to affine gaps (Gotoh), local and free-shift alignment in [bioinformatics-toolkit](https://github.com/SiljaOh/bioinformatics-toolkit).
