# Justified Solutions PDF (jsPDF) #
An Open Source library for creating basic PDF documents.

[![Build Status](https://travis-ci.org/justifiedsolutions/jspdf.svg?branch=master)](https://travis-ci.org/justifiedsolutions/jspdf)
[![Maven Central](https://img.shields.io/maven-central/v/com.justifiedsolutions/jspdf?color=green)](https://maven-badges.herokuapp.com/maven-central/com.justifiedsolutions/jspdf)
[![javadoc](https://javadoc.io/badge2/com.justifiedsolutions/jspdf/javadoc.svg)](https://javadoc.io/doc/com.justifiedsolutions/jspdf)
[![License (Apache 2.0)](https://img.shields.io/badge/license-Apache%202.0-blue)](http://www.apache.org/licenses/LICENSE-2.0.txt) 

## License ##
`SPDX-License-Identifier: Apache-2.0`

## Goals ##
The primary goal for jsPDF is to provide a PDF library to support Justified Solution's other products. This doesn't require a particularly full-featured API. Features that are required:

* Robust Table Layout Support
* Automatic Pagination of Document Content
* Support for Document Headers and Footers
* Standard 14 Font Support
* JPMS Module
* Commercial Friendly License

## Releases ##

### jspdf 0.1.0 (2020-XX-XX) ###

* Initial Release
* Document Layout
  * Sections
  * Paragraph, Phrase, and Chunk Text objects
  * Tables and Cells
  * Headers and Footers
* Document Metadata
* Standard 14 Fonts

## TODOs ##

* Content Object Filters
	* ASCII85Decode
	* LZWDecode or FlateDecode

## Unimplemented Features ##

These are PDF features that there are not currently plans to implement. If you would like to see any of these features added to the library, we're happy to accept pull requests. Alternatively, we can discuss a sponsorship to complete the feature.

* TrueType Fonts
* Image Support
* Encryption
* Digital Signatures
* Forms
* PDF Updates