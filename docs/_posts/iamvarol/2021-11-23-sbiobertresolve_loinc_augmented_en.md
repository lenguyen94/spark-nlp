---
layout: model
title: Sentence Entity Resolver for LOINC (sbiobert_base_cased_mli embeddings)
author: John Snow Labs
name: sbiobertresolve_loinc_augmented
date: 2021-11-23
tags: [loinc, entity_resolution, clinical, en, licensed]
task: Entity Resolution
language: en
edition: Spark NLP for Healthcare 3.3.2
spark_version: 2.4
supported: true
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

This model maps extracted clinical NER entities to LOINC codes using `sbiobert_base_cased_mli` Sentence Bert Embeddings. It trained on the augmented version of the dataset which is used in previous LOINC resolver models.

## Predicted Entities



{:.btn-box}
[Live Demo](https://nlp.johnsnowlabs.com/demo){:.button.button-orange}
[Open in Colab](https://colab.research.google.com/github/JohnSnowLabs/spark-nlp-workshop/blob/master/tutorials/Certification_Trainings/Healthcare/24.Improved_Entity_Resolvers_in_SparkNLP_with_sBert.ipynb){:.button.button-orange.button-orange-trans.co.button-icon}
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/clinical/models/sbiobertresolve_loinc_augmented_en_3.3.2_2.4_1637664939262.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use

```sbiobertresolve_loinc_augmented``` resolver model must be used with ```sbiobert_base_cased_mli``` as embeddings ```ner_jsl``` as NER model. ```Test, BMI, HDL, LDL, Medical_Device, Temperature,
Total_Cholesterol, Triglycerides, Blood_Pressure``` set in ```.setWhiteList()```.


<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
chunk2doc = Chunk2Doc().setInputCols("ner_chunk").setOutputCol("ner_chunk_doc")

sbert_embedder = BertSentenceEmbeddings\
.pretrained("sbiobert_base_cased_mli","en","clinical/models")\
.setInputCols(["ner_chunk_doc"])\
.setOutputCol("sbert_embeddings")


resolver = SentenceEntityResolverModel.pretrained("sbiobertresolve_loinc_augmented","en", "clinical/models") \
.setInputCols(["ner_chunk", "sbert_embeddings"]) \
.setOutputCol("loinc_code")\
.setDistanceFunction("EUCLIDEAN")

pipeline_loinc = Pipeline(stages = [documentAssembler, sentenceDetector, tokenizer, stopwords, word_embeddings, clinical_ner, ner_converter, chunk2doc, sbert_embedder, resolver])

data = spark.createDataFrame([["""The patient is a 22-year-old female with a history of obesity. She has a Body mass index (BMI) of 33.5 kg/m2, aspartate aminotransferase 64, and alanine aminotransferase 126. Her hgba1c is 8.2%."""]]).toDF("text")

results = model.fit(data).transform(data)
```
```scala
val documentAssembler = DocumentAssembler()
.setInputCol("text")
.setOutputCol("ner_chunk")

val sbert_embedder = BertSentenceEmbeddings
.pretrained("sbiobert_base_cased_mli", "en","clinical/models")
.setInputCols(Array("ner_chunk"))
.setOutputCol("sbert_embeddings")

val loinc_resolver = SentenceEntityResolverModel
.pretrained("sbiobertresolve_loinc_augmented", "en", "clinical/models") 
.setInputCols(Array("ner_chunk", "sbert_embeddings")) 
.setOutputCol("loinc_code")
.setDistanceFunction("EUCLIDEAN")

val loinc_pipelineModel = new PipelineModel().setStages(Array(documentAssembler, sbert_embedder, loinc_resolver))

val data = Seq("The patient is a 22-year-old female with a history of obesity. She has a Body mass index (BMI) of 33.5 kg/m2, aspartate aminotransferase 64, and alanine aminotransferase 126. Her hgba1c is 8.2%.").toDF("text")

val result = pipeline.fit(data).transform(data)
```


{:.nlu-block}
```python
import nlu
nlu.load("en.resolve.loinc.augmented").predict("""The patient is a 22-year-old female with a history of obesity. She has a Body mass index (BMI) of 33.5 kg/m2, aspartate aminotransferase 64, and alanine aminotransferase 126. Her hgba1c is 8.2%.""")
```

</div>

## Results

```bash
+--------------------------+-----+---+------+----------+----------+--------------------------------------------------+--------------------------------------------------+
|                     chunk|begin|end|entity|confidence|Loinc_Code|                                         all_codes|                                       resolutions|
+--------------------------+-----+---+------+----------+----------+--------------------------------------------------+--------------------------------------------------+
|           Body mass index|   74| 88|  Test|0.39306664| LP35925-4|LP35925-4:::BDYCRC:::LP172732-2:::39156-5:::LP7...|body mass index:::body circumference:::body mus...|
|aspartate aminotransferase|  111|136|  Test|   0.74925| LP15426-7|LP15426-7:::14409-7:::LP307348-5:::LP15333-5:::...|aspartate aminotransferase::: aspartate transam...|
|  alanine aminotransferase|  146|169|  Test|    0.9579| LP15333-5|LP15333-5:::LP307326-1:::16324-6:::LP307348-5::...|alanine aminotransferase:::alanine aminotransfe...|
|                    hgba1c|  180|185|  Test|    0.1118|   17855-8|17855-8:::4547-6:::55139-0:::72518-4:::45190-6:...| hba1c::: hgb a1::: hb1::: hcds1::: hhc1::: htr...|
+--------------------------+-----+---+------+----------+----------+--------------------------------------------------+--------------------------------------------------+


```

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|sbiobertresolve_loinc_augmented|
|Compatibility:|Spark NLP for Healthcare 3.3.2+|
|License:|Licensed|
|Edition:|Official|
|Input Labels:|[sentence_embeddings]|
|Output Labels:|[loinc_code]|
|Language:|en|
|Case sensitive:|false|

## Data Source

Trained on standard LOINC coding system.
