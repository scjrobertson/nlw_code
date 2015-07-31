<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>

	<xsl:template match="/">
 		 <xsl:call-template name="t1"/>
	</xsl:template>

	<xsl:template name="t1">
		<xsl:for-each select="/root/document/sentences/sentence">
			<xsl:value-of select="parse"/>
			<xsl:value-of select="'&#10;'"/>
			<xsl:value-of select="'&#10;'"/>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>
