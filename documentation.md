
# Documentation


## Structure of preservation-tools

Generell ist das Repository /die Toolsammlung so aufgebaut, dass alles, was irgendwie für die LZA nützlich sein kann, dort gesammelt wird. Einiges ist sehr rudimentär, wie z. B. dir Prüfung, ob die ersten Bytes einer PDF Datei "%PDF" sind bzw. dies in der ersten Zeile der PDF Datei enthalten ist. Anderes geht über die reine Analyse einer Datei hinaus, z. B. die Bearbeitung von PDF-Dateien mit iText oder die Konvertierung einer PDF-Datei in JPEG-Dateien (eine pro Seite).

Es gibt Methoden - meist unter Utilities zu finden, die fast von allen anderen Klasse benutzt werden wie der File- bzw. Folder Browser Dialog oder die Methode getPaths der Klasse ListsFiles, die aus einem Ordner inkl. aller Unterordner alle Dateien in einer ArrayList aus Files speichert und zurückgibt.

Nicht alle Klassen/ Programme sind bereits produktiv einsetzbar, einiges besteht nur aus einem wieder verworfenen Ansatz (keine Zeit, zu wenig Wissen, zu wenig dringende Aufgabe). Manches wird vielleicht sogar wieder ganz gelöscht.

Für viele der Klassen werden bereits vorhandene Bibliotheken genutzt (oft Apache Tika, iText, JHOVE u. ä.). Oftmals sind Klassen dabei, die Aufgaben erledigen, für die es bereits Tools gibt (HexReader, JPEG-Migration). Ziel ist hier nicht, das Rad neu zu erfinden, sondern möglichst autonom aus einer eigenen Umgebung heraus agieren zu können - wie z. B. das Tool/die Klasse Run Jhove App, das zwar auf die JHOVE Library zurückgreift, aber eine gezielte XML-Ausgabe inkl. eigenem Stylesheet ausgibt, um nur die Informationen anzuzeigen, die ich auch brauche. Oftmals geht es auch nur darum, mehr über das verwendete Tool und die Funktionsweise (Bsp. JHOVE) oder das Dateiformat (PDF, JPEG, TIFF, gif, wave usw.) zu lernen, um die Risiken für die LZA bessser einschätzen oder sogar vermindern zu können.

Das ganze Repositorium ist ein Work in Progress und verändert sich stetig.

### externalToolAnalysis
This package contains several classes which do not work yet to access external programs like JHOVE, FITS and co. It is planned to be able to use these tools from the java program if this is possible in the first place at all.

### filetools
The filetools-package contains classes which should be usable for all types of file. For example, it consists of a Checksum Checker (so far only MD5) and a GeneralFileAnalysis which e. g. checks for the Magical Number at the beginning of the file.

### filetools.audio
As one can easily guess, the containing classes deals with audio files in particular.

### filetools.epub

### filetools.executables

### filetools.pdf

### filetools.tiff

### output
There is an XmlOutput class in this package which helps to get some xml Output. Currently, this class is under construction.

### utilities
Some classes (+ methods) are used very frequently and by almost all classes which contain of a main-program.
* ArchiveFilesToFolder
under construction. Will be able to copy/paste files to a certain folder
* FileBrowserDialog
Navigates to a file
* FolderBrowserDialog
Navigates to a folder
* FolderCreation
under construction
* IsoImageCreator
One day, this class will hopefully contain methods to create an Iso Image File from a CD ROM.
* ListFiles
One of the very important methods, to get all files from a choosen folder incl. subfolders and list them in an ArrayList of files.
* SearchCertainFormat
Very usefull sometimes to gather test data.

(old:)

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
