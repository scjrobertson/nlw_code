<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>

	<xsl:template match="/">
 		 <xsl:call-template name="t1"/>
	</xsl:template>

	<xsl:template name="t1">
		<xsl:for-each select="/root/document/sentences/sentence">
			<xsl:for-each select="dependencies[1]/dep">
				<xsl:value-of select="governor/@idx"/>
				<xsl:value-of select="'&#160;'"/>
				<xsl:value-of select="dependent/@idx"/>
				<xsl:value-of select="'&#160;'"/>
				<xsl:value-of select="@type"/>
				<xsl:value-of select="'&#160;'"/>
				<xsl:value-of select="'&#10;'"/>
			</xsl:for-each>
			<xsl:value-of select="'&#10;'"/>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>
