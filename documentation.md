
# Documentation

## PdfUtilies

Commonly used functions/methods. Will be documented later.

* PdfUtilities.getPaths
This is a folder browser dialog which asks the user to choose a folder. It gives all files from the folder including all subfolders back to the Main program, data type an Array List of files.
* PdfUtilities.PdfAChecker
* PdfUtilities.FileHeaderTest

## PdfHeaderChecker

Some files break the program as the functions used within are not meant for them, e. g. Non-PDF-files, too large PDF-files, encrypted PDF files, PDF files with missing "%PDF"-Headers, too broken PDF-files etc.

Therefore, the **PdfHeaderChecker** has been implemented.

##### Step by Step
* Choose the folder via *PdfUtilities.getPaths*. 
* Extension test "pdf" or not.
* Tests filesize. If the file bigger than 90 MB, end of examination, finding: PdfTooBig 
* Tests encryption (via pdfbox, PDDocument is encrypted or not). If yes, end of examination, finding: PDF Encrypted files.
* Tests if the "%PDF" Header is there via *PdfUtilities.FileHeaderTest*. If not, end of examination. Findings: No PDF Header.
* Tests if PDF is bigger than 16 MB. If yes, end of examination. Findings: PDF too big.
* Checks if PDF is PDF/A via *PdfUtilities.PdfAChecker*. Findings: PDF or PDF/A.
* close *PdfUtilities.PdfHeaderTest* Buffered Reader (in case it has been used at all), as it is not used any more.
* Print out all findings.






### PDF files and other files

#### PDF files that are too big

Files that are bigger than 90 MB cannot be turned in a PDDocument (pdfbox). Up to 16 MB this still works, the sizes inbetween have not been tested yet.
An if/else condition has been implemented to avoid that the program tries to create a PDDocument with PDF-files bigger than 90 MB. It gives the message: ("File is bigger than 90 MB and therefore cannot be measured") and the PDF files will be listed as "PDF files too big" in the findings at the end.

The function "PdfUtilities.PdfAChecker" does not work for PDF files that are bigger than 16 MB (up to 10 MB this has been tested to still work). This is due to the PdfReader class (iText) which is not able to handle PDF files this size.
Therefore an if/else condition has been implemented to avoid that this function is performed on PDF files bigger than 16 MB. It gives the message: ("File is bigger than 16 MB and therefore cannot be measured") and the PDF files will be listed as "PDF files too big" in the findings at the end.
