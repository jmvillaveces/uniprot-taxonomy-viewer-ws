uniprot-taxonomy-viewer-ws
==========================

A simple web service to serve Uniprot taxonomy data. The service reads a plain file containing taxonomical information, and creates an in memory database to store the data.

## Methods

Currently, the webservice provides two methods vía REST.

### Get Taxonomy By Id

Returns an array of taxonomies with the defined ids. As a parameter,it receives a list of taxonomy ids. Example:

```
http://localhost:8080/taxonomy/getTaxonomyById?taxId=9606,2
```

### Get Taxonomy By Name

Returns an array of taxonomies where the taxonomy name contains the given taxName. Example:

```
http://localhost:8080/taxonomy/getTaxonomyByName?taxName=Homo Sapiens
```

## Response

The service will always answer with a JSON list of taxonomies. A taxonomy in JSON is defined as follows:

```javascript
{
  "taxId":207598,
  "parentTaxId":9604,
  "name":"Homininae",
  "cp":4,
  "rp":13,
  "children":[9592,9606]
}
```

## Taxonomy Input File Format

In order for the webservice to work, it needs an input file defining the taxonomies and its attributes. The file is a tabulated text file where columns are defined as follows:

1. Taxonomy identifier for the parent organism
2. Taxonomy identifier for the current taxonomy
3. Taxonomy scientific name
4. Number of reference proteomes
5. Number of complete proteomes

Example:

```
9604	207598	Homininae
207598	9592	Gorilla
9592  499232  Gorilla beringei
9592  9593  Gorilla gorilla 2 1
207598	9605	Homo  2
9605	9606	Human 3 5
```

## Running the code

This proyect uses [Spring](https://spring.io) and [Spring Boot](http://projects.spring.io/spring-boot/). In order to run the code follow this steps:

1. Clone the repository
2. Open the console and navigate to the proyect location
3. Run the Proyect by typing ```mvn spring-boot:run```
