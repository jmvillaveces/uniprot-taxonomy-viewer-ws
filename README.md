uniprot-taxonomy-viewer-ws
==========================

A simple web service to serve Uniprot taxonomy data. The service reads a plain file containing taxonomical information, and creates an in memory database to store the data.

## Methods

Currently, the webservice provides two methods vía REST.

### Get Taxonomy By Id

Returns an array of taxonomies with the defined ids. 

#### Parameters 

1. taxId : a list of taxonomy ids.

#### Examples

```
http://localhost:8080/taxa/9606,2
```

### Get Taxonomy By Name

Returns an array of taxonomies where the taxonomy name contains the given taxName.

#### Parameters 

1. taxName : the taxonomy name.

#### Examples

```
http://localhost:8080/taxa/gorilla
```

### Get Taxonomy Tree

Recursively builds an array of taxonomies by taking as an input the given taxonomy id.

#### Parameters 

1. taxId : the taxonomy id.

#### Examples

```
http://localhost:8080/taxa/9606/tree
http://localhost:8080/taxa/207598/tree
http://localhost:8080/taxa/9604/tree
```

## Response

The service will always answer with a JSON list of taxonomies. A taxonomy in JSON is defined as follows:

```javascript
{
  "taxId":207598,
  "parentTaxId":9604,
  "val":0,
  "name":"Homininae",
  "category":"-",
  "children":[9592,9596,9605]
}
```

## Taxonomy Input File Format

In order for the webservice to work, it needs an input file defining the taxonomies and its attributes. The file is a tabulated text file where columns are defined as follows:

1. Taxonomy identifier for the parent organism
2. Taxonomy identifier for the current taxonomy
3. Taxonomy scientific name
4. Status 
  * cp = complete proteome
  * rp = reference proteome
  * - = no status available
5. Number of proteomes, if status is nota available then it is the number of proteins

Example:

```
1	207598	Homininae
207598	9592	Gorilla
9592	499232	Gorilla beringei	cp	10
9592	9593	Gorilla gorilla	rp	2
207598	9605	Homo	-	2
9605	9606	Human	rp	2
```

## Running the code

This project uses [Spring](https://spring.io) and [Spring Boot](http://projects.spring.io/spring-boot/). In order to run the code follow this steps:

1. Clone the repository
2. Open the console and navigate to the project location
3. Run the Proyect by typing ```mvn spring-boot:run```
