# FA³ST Registry
FA³ST Registry implements the registry interfaces of the [Asset Administration Shell (AAS) specification by IDTA](https://industrialdigitaltwin.org/content-hub/downloads) and provides an easy-to-use registry for AAS.

:::{caution}
The FA³ST Registry contains two separate Registry instances: An AAS Registry for Asset Administration Shell (AAS) Descriptors, and a Submodel Registry for Submodel Descriptors. These Registry instances are strictly separated, i.e. when an AAS with Submodels is registered in the AAS Registry, the Submodels of this AAS won't be registered in the Submodel Registry. If you want these Submodels to be registered in the Submodel Registry, you must register them in the Submodel Registry separately.
:::

## Implemented AAS Specification
| Specification                                                                              | Version                                                                                                                                                                                                                                                           |
|:------------------------------------------------------------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Details of the Asset Administration Shell - Part 2<br />Application Programming Interfaces | Version 3.0<br />([specification](https://industrialdigitaltwin.org/wp-content/uploads/2023/06/IDTA-01002-3-0_SpecificationAssetAdministrationShell_Part2_API_.pdf))<br />([swagger](https://app.swaggerhub.com/apis/Plattform_i40/Entire-API-Collection/V3.0.1)) |

## Features

-   supports several persistence implementations: `memory, jpa`
-   supports separated Registries
	-   AAS Registry
    -   Submodel Registry

```{toctree}
:hidden:
:caption: Basics
:maxdepth: 3
basics/gettingstarted.md
basics/installation.md
basics/usage.md
basics/configuration.md
basics/faq.md
```

```{toctree}
:hidden:
:caption: API
:maxdepth: 2
api/rest-api.md
api/persistence.md
```

```{toctree}
:hidden:
:caption: Other
:maxdepth: 2
other/about-the-project.md
other/contributing.md
other/recommended-documents-and-links.md
other/release-notes.md
```
