# Justified Solutions PDF (justPDF)
An Open Source library for creating basic PDF documents.

[![justPDF](https://circleci.com/gh/justifiedsolutions/justPDF.svg?style=shield)](https://app.circleci.com/pipelines/github/justifiedsolutions/justPDF?branch=main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.justifiedsolutions%3AjustPDF&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.justifiedsolutions%3AjustPDF)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.justifiedsolutions%3AjustPDF&metric=coverage)](https://sonarcloud.io/dashboard?id=com.justifiedsolutions%3AjustPDF)
[![Maven Central](https://img.shields.io/maven-central/v/com.justifiedsolutions/justPDF?color=green)](https://maven-badges.herokuapp.com/maven-central/com.justifiedsolutions/justPDF)
[![javadoc](https://javadoc.io/badge2/com.justifiedsolutions/justPDF/javadoc.svg)](https://javadoc.io/doc/com.justifiedsolutions/justPDF)
[![License (Apache 2.0)](https://img.shields.io/badge/license-Apache%202.0-blue)](http://www.apache.org/licenses/LICENSE-2.0.txt)

## License
`SPDX-License-Identifier: Apache-2.0`

## Maven Dependency
Add this to your pom.xml file to use the latest version of justPDF:
```
<dependency>
    <groupId>com.justifiedsolutions</groupId>
    <artifactId>justPDF</artifactId>
    <version>1.2.1</version>
</dependency>
```

## Goals
The primary goal for justPDF is to provide a PDF library to support Justified Solution's other products. This doesn't require a particularly full-featured API. Features that are required:

* Robust Table Layout Support
* Automatic Pagination of Document Content
* Support for Document Headers and Footers
* Standard 14 Font Support
* JPMS Module
* Commercial Friendly License

## Releases

### justPDF 1.2.1 (2020-11-04)

* Fix critical bug with hyphenating slash separated words

### justPDF 1.2.0 (2020-11-04)

* New flexible table layout
	* Adjusts column widths automatically if some content cannot fit in specified widths

### justPDF 1.1.1 (2020-10-09)

* Fix issue with extended characters in WinAnsiEncoding

### justPDF 1.1.0 (2020-06-09)

* Improve line break code
	* Utilize BreakIterator to find line break points
	* Add auto-hyphenate property to TextContent. Enabled by default.
* Document Outline
	* Outlines are automatic with section documents
* Add PMD to build
* Add additional javadoc to code

### justPDF 1.0.1 (2020-05-19)

* Add SonarCloud support to build
* Fix issues identified by SonarCloud

### justPDF 1.0.0 (2020-05-18)

* Add DeflateFilter
* Completes Initial Project Goals

### justPDF 0.1.0 (2020-05-16)

* Initial Release
* Document Layout
  * Sections
  * Paragraph, Phrase, and Chunk Text objects
  * Tables and Cells
  * Headers and Footers
* Document Metadata
* Standard 14 Fonts

## Unimplemented Features

These are PDF features that there are not currently plans to implement. If you would like to see any of these features added to the library, we're happy to accept pull requests. Alternatively, we can discuss a sponsorship to complete the feature.

* Image Support
* TrueType Fonts
* Encryption
* Digital Signatures
* Forms
* PDF Updates
