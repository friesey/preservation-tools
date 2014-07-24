PdfEventPrep
--------------

Preparation for the OPF PDF Hackathon which takes place at the 1st and 2nd September in Hamburg. 


These snippets and tools deals with several PDF issues which might come in handy during the Hackathon.


#### iTextRepairPdf 

(formerly known as "Main_Standalone")

Is able to take a PDF-file and copies the content page-per-page to a new, PDFA1-conform PDF-file. The XMP-Metadata is also copied.

This repairs possible issues with the structure of the PDF. JHOVE will consider the so new-built PDF-files as well-formed and valid. PDFTron, however, will still detect problems and issues with the new-built PDF-files, mostly about fonts and images. 

The library iText is used, the AGPL-version, which has to be considered when re-using this tool or snippets from it.

This tools works through a selected folder and possible sub-folders.

#### PdfHeaderChecker

(formerly known as "Test4Bytes")

This is a tool which tests if the file starts with "%PDF". No external libraries are used.

This tools works through a selected folder and possible sub-folders.

#### CreationSoftwareDetective


This tool is able to detect which Software was used to create the PDF and puts out all Creation software in an "outpufile.txt" in the folder which was examined.
It does not yet count how often each software was used, but this is planned to be implemented.

Furthermore, it detects encrypted PDF-files and is able to deal with some really rotten PDF-files. However, some PDF-files still do crash the program, which is planned to be fixed soon.

It would be handy to have some of the functions in this program reused during the Hackathon, as some files (not only PDF-files) can stop the program and during the Hackathon, no time should be wasted to deal with all the exception to be able to get some work done.

The library iText is used, the AGPL-version, which has to be considered when re-using this tool or snippets from it.
The library PDFBox is used, too.
This tools works through a selected folder and possible sub-folders.
