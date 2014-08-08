
# Documentation

## PdfUtilies

Commonly used functions/methods or instances

*PdfHeaderTest: buffered reader to check the (PDF-)file as in an editor, e. g. to look for the "%PDF"-Header.

*functions*
* PdfUtilities.getPaths
This is a folder browser dialog which asks the user to choose a folder. It gives all files from the folder including all subfolders back to the Main program, data type an Array List of files.

* PdfUtilities.FileHeaderTest
Checks if the file contains the "%PDF"-Header using the BufferedReader.

* PdfUtilities.ChooseFolder
A simple folder browse dialog which lets the user choose the folder whose files have to be examined.
If no folder is chosen, the program gives the message "No folder was chosen".

* PdfUtilities.PdfAChecker
Please not that before the extension, PDF Header, encryption and file size has to be checked, otherwise certain files will break this function.
First tests PdfVersion, as everything below 1.4 cannot be PDF/A anyway.
Afterwards the PdfReader extracts the XMP Metadata and tests wether or not th "Pdfaid:conformance"-entry is there.

* PdfUtilities.EncryptionTest
Simple Encryption Test (boolean true/false) without reader, because encryption causes lots of exceptions.
Will put more detailed examination in *PdfEncryptionDetective*.
	  

## PdfHeaderChecker

*Group*: PDF examination

*Short summary*: Handy for Exception handling, omitting files and PDF files which might break the program. Determines encryption (very general) and if PDF is PDF/A (also very general).

Some files break the program as the functions used within are not meant for them, e. g. Non-PDF-files, too large PDF-files, encrypted PDF files, PDF files with missing "%PDF"-Headers, too broken PDF-files etc.

Therefore, the **PdfHeaderChecker** has been implemented.

##### Step by Step
* Choose the folder via *PdfUtilities.getPaths*. 
* Extension test "pdf" or not.
* Tests filesize. If the file bigger than 90 MB, end of examination, finding: PdfTooBig  &&edit> As files with more than 16 MB reallz slowed down the whole computer, I changed this to 16 MB and deleted the other test.
* Tests encryption (via pdfbox, PDDocument is encrypted or not). If yes, end of examination, finding: PDF Encrypted files.
* Tests if the "%PDF" Header is there via *PdfUtilities.FileHeaderTest*. If not, end of examination. Findings: No PDF Header.
* ~~Tests if PDF is bigger than 16 MB. If yes, end of examination. Findings: PDF too big.~~
* Checks if PDF is PDF/A via *PdfUtilities.PdfAChecker*. Findings: PDF or PDF/A.
* close *PdfUtilities.PdfHeaderTest* Buffered Reader (in case it has been used at all), as it is not used any more.
* Print out all findings.

## PdfCreationSoftwareDetection

*Group*: PDF examination

*Short summary*: 

## PdfEncryptionDetective

*Group*: PDF examination

*Short summary*: Not yet implemented. Will determine all kinds of PDF Encryption more closely (via iText, I'd guess).

## PdfAValidator

*Group*: PDF/A examination
*Short summary*: If a PDF is PDF/A (examination possible via *PdfUtilities.PdfAChecker*) checks Validation level. Not yet implemented.

##  iTextRepairPdf

*Group*: Migration Software for PDF Files.

*Short summary*: Turns malformed PDF files in wellformed. Would be great it an examination (valid or not) could be done before. This is not part of the program yet.

## PdfTwinTest

*Group*: QA after migration (PDf to PDF or PDF to PDF/A)

*Short summary*: Compares the original PDF with the migrated one, in terms of characters. Has been implemented in C# before, will be translated into java, has not been done yet.

## Some more information about difficult file cases

### PDF files and other files

#### PDF files that are too big

Files that are bigger than 90 MB cannot be turned in a PDDocument (pdfbox). Up to 16 MB this still works, the sizes inbetween have not been tested yet.
An if/else condition has been implemented to avoid that the program tries to create a PDDocument with PDF-files bigger than 90 MB. It gives the message: ("File is bigger than 90 MB and therefore cannot be measured") and the PDF files will be listed as "PDF files too big" in the findings at the end.

The function "PdfUtilities.PdfAChecker" does not work for PDF files that are bigger than 16 MB (up to 10 MB this has been tested to still work). This is due to the PdfReader class (iText) which is not able to handle PDF files this size.
Therefore an if/else condition has been implemented to avoid that this function is performed on PDF files bigger than 16 MB. It gives the message: ("File is bigger than 16 MB and therefore cannot be measured") and the PDF files will be listed as "PDF files too big" in the findings at the end.

There seems to be another part of the program which cannot really deal with large files. This does not crash the program, but it trhows:
"org.apache.pdfbox.exceptions.WrappedIOException: Could not push back 123575 bytes in order to reparse stream. Try increasing push back buffer using system property org.apache.pdfbox.baseParser.pushBackSize54" + gives file path
