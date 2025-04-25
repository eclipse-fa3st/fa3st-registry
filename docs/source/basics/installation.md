# Installation

## Requirements

-   Java 17+

## Usage

### Precompiled JAR

<!--start:download-release-->
<!--end:download-release-->

<!--start:download-snapshot-->
{download}`Latest SNAPSHOT version (1.0.0-SNAPSHOT) <https://oss.sonatype.org/service/local/artifact/maven/redirect?r=snapshots&g=org.eclipse.digitaltwin.fa3st.registry&a=fa3st-registry-starter&v=1.0.0-SNAPSHOT>`<!--end:download-snapshot-->

### Maven

```xml
<dependency>
	<groupId>org.eclipse.digitaltwin.fa3st.registry</groupId>
	<artifactId>fa3st-registry</artifactId>
	<version>1.0.0-SNAPSHOT</version>
</dependency>
```

### As Gradle Dependency

```text
implementation 'org.eclipse.digitaltwin.fa3st.registry:fa3st-registry:1.0.0-SNAPSHOT'
```

## Building from Source

### Prerequisites

-   Maven

```sh
git clone https://github.com/eclipse-fa3st/fa3st-registry
cd fa3st-registry
mvn clean install
```