# Justified Solutions PDF (justPDF)

An Open Source library for creating basic PDF documents.

[![justPDF](https://github.com/justifiedsolutions/justPDF/actions/workflows/validate.yml/badge.svg?branch=main)](https://github.com/justifiedsolutions/justPDF/actions/workflows/validate.yml)
[![justPDF](https://github.com/justifiedsolutions/justPDF/actions/workflows/deploy.yml/badge.svg?branch=main)](https://github.com/justifiedsolutions/justPDF/actions/workflows/deploy.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.justifiedsolutions%3AjustPDF&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.justifiedsolutions%3AjustPDF)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.justifiedsolutions%3AjustPDF&metric=coverage)](https://sonarcloud.io/dashboard?id=com.justifiedsolutions%3AjustPDF)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.justifiedsolutions/justPDF/badge.svg?style=flat)](https://central.sonatype.com/artifact/com.justifiedsolutions/justPDF/)
[![javadoc](https://javadoc.io/badge2/com.justifiedsolutions/justPDF/javadoc.svg)](https://javadoc.io/doc/com.justifiedsolutions/justPDF)
[![License (Apache 2.0)](https://img.shields.io/badge/License-Apache%202.0-blue)](http://www.apache.org/licenses/LICENSE-2.0.txt)

## License

`SPDX-License-Identifier: Apache-2.0`

## Maven Dependency

Add this to your pom.xml file to use the latest version of justPDF:

```xml
<dependency>
    <groupId>com.justifiedsolutions</groupId>
    <artifactId>justPDF</artifactId>
    <version>1.2.9</version>
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

### justPDF 1.2.9 (2023-04-26)

* Migrate build to GHA

### justPDF 1.2.8 (2023-04-26)

* Bump dependency versions
* Bump plugin versions
* Migrate build to GHA
* NOT PUBLISHED

### justPDF 1.2.7 (2023-01-06)

* Bump dependency versions
* Change signing key

### justPDF 1.2.6 (2022-06-13)

* Increase unit test coverage
* Bump jacoco from 0.8.7 to 0.8.8
* Bump maven-compiler-plugin from 3.9.0 to 3.10.1
* Bump maven-enforcer-plugin from 3.0.0 to 3.1.0
* Bump maven-javadoc-plugin from 3.3.1 to 3.4.0
* Bump maven-pmd-plugin from 3.14.0 to 3.17.0
* Suppress a couple of PMD warnings
* Bump maven-surefire-plugin from 3.0.0-M5 to 3.0.0-M7
* Bump mockito-core from 4.3.1 to 4.6.1
* Bump mockito-junit-jupiter from 4.3.1 to 4.6.1
* Bump nexus-staging-maven-plugin from 1.6.8 to 1.6.13

### justPDF 1.2.5 (2022-01-29)

* Test auto release process

### justPDF 1.2.4 (2022-01-29)

* Fix release process

### justPDF 1.2.3 (2022-01-29)

* Update Release Process
* Update dependency versions

### justPDF 1.2.2 (2022-01-24)

* Migrate Build to CircleCI
* Enable Dependabot Version Updates
* Update dependency versions

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
