---
layout: model
title: German ALBERT Embeddings (from abhilash1910)
author: John Snow Labs
name: albert_embeddings_albert_german_ner
date: 2022-04-14
tags: [albert, embeddings, de, open_source]
task: Embeddings
language: de
edition: Spark NLP 3.4.2
spark_version: 3.0
supported: true
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Pretrained ALBERT Embeddings model, uploaded to Hugging Face, adapted and imported into Spark NLP. `albert-german-ner` is a German model orginally trained by `abhilash1910`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/albert_embeddings_albert_german_ner_de_3.4.2_3.0_1649954270550.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
documentAssembler = DocumentAssembler() \
.setInputCol("text") \
.setOutputCol("document")

tokenizer = Tokenizer() \
.setInputCols("document") \
.setOutputCol("token")

embeddings = AlbertEmbeddings.pretrained("albert_embeddings_albert_german_ner","de") \
.setInputCols(["document", "token"]) \
.setOutputCol("embeddings")

pipeline = Pipeline(stages=[documentAssembler, tokenizer, embeddings])

data = spark.createDataFrame([["Ich liebe Funken NLP"]]).toDF("text")

result = pipeline.fit(data).transform(data)
```
```scala
val documentAssembler = new DocumentAssembler() 
.setInputCol("text") 
.setOutputCol("document")

val tokenizer = new Tokenizer() 
.setInputCols(Array("document"))
.setOutputCol("token")

val embeddings = AlbertEmbeddings.pretrained("albert_embeddings_albert_german_ner","de") 
.setInputCols(Array("document", "token")) 
.setOutputCol("embeddings")

val pipeline = new Pipeline().setStages(Array(documentAssembler, tokenizer, embeddings))

val data = Seq("Ich liebe Funken NLP").toDF("text")

val result = pipeline.fit(data).transform(data)
```


{:.nlu-block}
```python
import nlu
nlu.load("de.embed.albert_german_ner").predict("""Ich liebe Funken NLP""")
```

</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|albert_embeddings_albert_german_ner|
|Compatibility:|Spark NLP 3.4.2+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[sentence, token]|
|Output Labels:|[bert]|
|Language:|de|
|Size:|44.8 MB|
|Case sensitive:|false|

## References

- https://huggingface.co/abhilash1910/albert-german-ner