<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy® -->
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
  <body>
  	 <h2>Fitnesse  Test</h2>
    <table border="1">
      <tr bgcolor="#9acd32">
      	<th>Test Results</th>
      </tr>
      <tr bgcolor="#9acd32">
        <th width='100'>PASS</th>
        <th width='100'>FAILED</th>
        <th width='100'>IGNORES</th>
        <th width='100'>EXCEPTION</th>
        <th width='300'>RUNTIME IN MILLI-SECONDS</th>
      </tr>
      <tr>
        <td align='middle'><xsl:value-of select="testResults/result/counts/right"/></td>
        <td align='middle'><xsl:value-of select="testResults/result/counts/wrong"/></td>
        <td align='middle'><xsl:value-of select="testResults/result/counts/ignores"/></td>
        <td align='middle'><xsl:value-of select="testResults/result/counts/exceptions"/></td>
        <td align='middle'><xsl:value-of select="testResults/result/runTimeInMillis"/></td>
      </tr>
    </table>
    <br/>
    <table border="1">
      <tr bgcolor="#9acd32">
        <th width='100'>Test Methods</th>
        <th width='100'>Arguments</th>
 	<th width='100'>Results</th>
      </tr>
      <xsl:apply-templates select="testResults/result/instructions"/>
      </table>
  </body>
  </html>
</xsl:template>

<xsl:template match="instructionResult">
  <xsl:if test="contains(instruction, 'methodName=')">
  <tr>
     <td align='left'><xsl:value-of select="substring(instruction, 
	string-length(substring-before(instruction, 'methodName='))+string-length('methodName=')+1, 
	string-length(instruction)-string-length(substring-after(instruction, ', args=['))-string-length(', args=[')-string-length(substring-before(instruction, 'methodName='))-string-length('methodName='))"/>
     </td>
     <td align='left'><xsl:value-of select="substring(instruction, 
	string-length(substring-before(instruction, 'args=['))+string-length('args=[')+1, 
	string-length(instruction)-string-length(substring-after(instruction, ']}'))-string-length(']}')-string-length(substring-before(instruction, 'args=['))-string-length('args=['))"/>
     </td>
     <td align='middle'><xsl:value-of select="slimResult"/>
     </td>
  </tr>
  </xsl:if>
</xsl:template>
</xsl:stylesheet>