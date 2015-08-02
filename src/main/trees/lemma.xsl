<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>

	<xsl:template match="/">
 		 <xsl:call-template name="t1"/>
	</xsl:template>

	<xsl:template name="t1">
		<xsl:for-each select="/root/document/sentences/sentence">
			<xsl:for-each select="tokens/token">
				<xsl:value-of select="@id"/>
				<xsl:value-of select="'&#160;'"/>
				<xsl:value-of select="word"/>
				<xsl:value-of select="'&#160;'"/>
				<xsl:value-of select="lemma"/>
				<xsl:value-of select="'&#160;'"/>
				<xsl:value-of select="POS"/>
				<xsl:value-of select="'&#10;'"/>
			</xsl:for-each>
			<xsl:value-of select="'&#10;'"/>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>
