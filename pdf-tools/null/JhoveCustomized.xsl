<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="JhoveFindingsSummary">
<html>
<style>
body {font-family: Verdana, Geneva, sans-serif; font-size: 10pt; }
table {font-family: Verdana, Geneva, sans-serif; font-size: 10pt; }
h1 {font-family: Verdana, Geneva, sans-serif; font-weight:bold; font-size: 18pt; color: #000000; }
h2 {font-family: Verdana, Geneva, sans-serif; font-weight:bold; font-size: 14pt; color: #000000; }
tr {background-color: #f0f0f0;}
tr.caption {background-color: #eeafaf; font-weight:bold}
tr.captionm {background-color: #f8dfdf}
tr.captionio {background-color: #afeeaf; font-weight:bold}
</style>
<body>
<h1>PDF files examined by JHOVE</h1>
<xsl:value-of select="FileName"/><br/>
<br/>
<h4>JHOVE Examination per File</h4>
<table border ="1">
<tr>
<th>FileName</th>
<th>Status</th>
<th>JhoveMessages</th>
<th>Message1</th>
<th>Message2</th>
<th>Message3</th>
</tr>
<xsl:for-each select="GifFile">
<tr>
<td><xsl:value-of select="FileName"/></td>
<td><xsl:value-of select="Status"/></td>
<td><xsl:value-of select="JhoveMessages"/></td>
<td><xsl:value-of select="Message1"/></td>
<td><xsl:value-of select="Message2"/></td>
<td><xsl:value-of select="Message3"/></td>
</tr>
</xsl:for-each>
</table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>
