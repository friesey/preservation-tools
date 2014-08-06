
## Documentation

### PDF files and other files

#### PDF files that are too big

Files that are bigger than 90 MB cannot be turned in a PDDocument (pdfbox). Up to 16 MB this still works, the sizes inbetween have not been tested yet.
An if/else condition has been implemented to avoid that the program tries to create a PDDocument with PDF-files bigger than 90 MB. It gives the message: ("File is bigger than 90 MB and therefore cannot be measured") and the PDF files will be listed as "PDF files too big" in the findings at the end.

The function "PdfUtilities.PdfAChecker" does not work for PDF files that are bigger than 16 MB (up to 10 MB this has been tested to still work). This is due to the PdfReader class (iText) which is not able to handle PDF files this size.
Therefore an if/else condition has been implemented to avoid that this function is performed on PDF files bigger than 16 MB. It gives the message: ("File is bigger than 16 MB and therefore cannot be measured") and the PDF files will be listed as "PDF files too big" in the findings at the end.
