# Justified Solutions PDF (justPDF) #
An Open Source library for creating basic PDF documents.

[![Build Status](https://travis-ci.com/justifiedsolutions/justPDF.svg?branch=master)](https://travis-ci.com/justifiedsolutions/justPDF)
[![Maven Central](https://img.shields.io/maven-central/v/com.justifiedsolutions/justPDF?color=green)](https://maven-badges.herokuapp.com/maven-central/com.justifiedsolutions/justPDF)
[![javadoc](https://javadoc.io/badge2/com.justifiedsolutions/justPDF/javadoc.svg)](https://javadoc.io/doc/com.justifiedsolutions/justPDF)
[![License (Apache 2.0)](https://img.shields.io/badge/license-Apache%202.0-blue)](http://www.apache.org/licenses/LICENSE-2.0.txt) 

## License ##
`SPDX-License-Identifier: Apache-2.0`

## Maven Dependency ##
Add this to your pom.xml file to use the latest version of justPDF:

			    <dependency>
			        <groupId>com.justifiedsolutions</groupId>
			        <artifactId>justPDF</artifactId>
			        <version>1.0.0</version>
			    </dependency>

## Goals ##
The primary goal for justPDF is to provide a PDF library to support Justified Solution's other products. This doesn't require a particularly full-featured API. Features that are required:

* Robust Table Layout Support
* Automatic Pagination of Document Content
* Support for Document Headers and Footers
* Standard 14 Font Support
* JPMS Module
* Commercial Friendly License

## Releases ##

### justPDF 1.0.0 (2020-05-18) ###

* Add DeflateFilter
* Completes Initial Project Goals

### justPDF 0.1.0 (2020-05-16) ###

* Initial Release
* Document Layout
  * Sections
  * Paragraph, Phrase, and Chunk Text objects
  * Tables and Cells
  * Headers and Footers
* Document Metadata
* Standard 14 Fonts

## Unimplemented Features ##

These are PDF features that there are not currently plans to implement. If you would like to see any of these features added to the library, we're happy to accept pull requests. Alternatively, we can discuss a sponsorship to complete the feature.

* Document Outline
* Image Support
* TrueType Fonts
* Encryption
* Digital Signatures
* Forms
* PDF Updates