PdfEventPrep
--------------

Preparation for the OPF PDF Hackathon which takes place at the 1st and 2nd September in Hamburg. 
These snippets and tools deals with several PDF issues which might come in handy during the Hackathon.

## jobs

* simple analysis (ss there a PDF Header, which kind of PDF - PDF or PDF/A, which version, which PDF size)
* more detailed analyis (Encryption)
* validation test (for PDF/A, if it is actually a PDF/A)
* repair function (quite simple)
* quality analysis after Migration

### PDF Analysis

#### PdfHeaderChecker

Tests if the file starts with "%PDF". This tools works through a selected folder and possible sub-folders.
To avoid crashes, there are some other tests like e. g. for the extension, if there is an encryption, if the file is a PDF/A etc. For more information see "documentation.md".

#### CreationSoftwareDetective

This tool is able to detect which Software was used to create the PDF and puts out all Creation software in an "outpufile.txt" in the folder which was examined.
It does not yet count how often each software was used, but this is planned to be implemented.

Furthermore, it detects encrypted PDF-files and is able to deal with some really broken PDF-files. However, some PDF-files still do crash the program, which is planned to be fixed soon (already fixed in "PdfHeaderChecker", which should not crash any more at all).

It would be handy to have some of the functions in this program reused during the Hackathon, as some files (not only PDF-files) can stop the program and during the Hackathon, no time should be wasted to deal with all the exception to be able to get some work done.

The library iText is used, the AGPL-version, which has to be considered when re-using this tool or snippets from it.
The library PDFBox is used, too.
This tools works through a selected folder and possible sub-folders.

#### Jhove Statistics
Simple analysis of wordy JHOVE finding files. 

### PDf Validator Tool(s)

#### PdfAValidator

Checks via PDFBox if a PDF/A is valid. Runs through a folder and picks out only PDF/A-files.

### Migration Tool(s)

#### iTextRepairPdf 

Is able to take a PDF-file and copies the content page-per-page to a new, PDFA1-conform PDF-file. The XMP-Metadata is also copied.

This repairs possible issues with the structure of the PDF. JHOVE will consider the so new-built PDF-files as well-formed and valid. PDFTron, however, will still detect problems and issues with the new-built PDF-files, mostly about fonts and images. 

The library iText is used, the AGPL-version, which has to be considered when re-using this tool or snippets from it.

This tools works through a selected folder and possible sub-folders.

#### PdfToImageConverter
Converts PDF Files in a certain folder to JPEGs page-per-page. Is a prerequesite for later Quality Checking /visual comparison via e. g. matchbox or ImageMagick.

### Quality Checking after Migrations

#### PdfTwinTest

First, two files are chosen. The program takes care that two Pdf-files are chosen that can be examined (the too-broken or too-big-issue is avoided). 
The tool compares the two PDF line-by-line and puts out differences. 
This is handy for after-Migration Quality-Checking.
Usually, the PDF-files created with the "iTextRepairPdf"-tool do not show any differences.


#### Helping tools within the programs

#### PdfUtilities.java

Class contains commonly used methods and one commonly used BufferedReader. Will be extended to be more efficient.


#### Reusing external libraries

Third-party libraries and tools used:

    Apache PDFBox
    iText - note that this library is AGPL3 licensed

