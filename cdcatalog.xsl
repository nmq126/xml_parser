<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"/>
            </head>
            <body>
                <h2>My CD Collection</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th style="text-align:left">Title</th>
                        <th style="text-align:left">Artist</th>
                        <th style="text-align:left">Country</th>
                        <th style="text-align:left">Company</th>
                        <th style="text-align:left">Price</th>
                        <th style="text-align:left">Year</th>
                    </tr>
                    <xsl:for-each select="catalog/cd">
                        <xsl:apply-templates select="."/>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="catalog/cd">
        <tr>
            <td>
                <xsl:value-of select="title"/>
            </td>
            <td>
                <xsl:value-of select="artist"/>
            </td>
            <td>
                <xsl:value-of select="country"/>
            </td>
            <td>
                <xsl:value-of select="company"/>
            </td>
            <td>
                <xsl:apply-templates select="price"/>
            </td>
            <td>
                <xsl:apply-templates select="year"/>
            </td>
        </tr>
    </xsl:template>
    <xsl:template match="price">
        <xsl:value-of select="."/>
        <xsl:if test=". &lt; 9">
            <span style="font-size: 1em; color: blue;">
                <i class="fas fa-arrow-down"></i>
            </span>
        </xsl:if>
    </xsl:template>

    <xsl:template match="year">
        <xsl:value-of select="."/>
        <xsl:if test=". &gt; 1995">
            <span style="font-size: 1em; color: red">
                <i class="fas fa-fire"></i>
            </span>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
