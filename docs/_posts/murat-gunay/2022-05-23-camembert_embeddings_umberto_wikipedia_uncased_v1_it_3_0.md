---
layout: model
title: Italian CamemBert Embeddings (from Musixmatch)
author: John Snow Labs
name: camembert_embeddings_umberto_wikipedia_uncased_v1
date: 2022-05-23
tags: [it, open_source, camembert, embeddings]
task: Embeddings
language: it
edition: Spark NLP 3.4.4
spark_version: 3.0
supported: true
article_header:
  type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Pretrained CamemBert Embeddings model, adapted from Hugging Face and curated to provide scalability and production-readiness using Spark NLP. `umberto-wikipedia-uncased-v1` is a Italian model orginally trained by `Musixmatch`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/camembert_embeddings_umberto_wikipedia_uncased_v1_it_3.4.4_3.0_1653321429870.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

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
  
embeddings = CamemBertEmbeddings.pretrained("camembert_embeddings_umberto_wikipedia_uncased_v1","it") \
    .setInputCols(["document", "token"]) \
    .setOutputCol("embeddings")
    
pipeline = Pipeline(stages=[documentAssembler, tokenizer, embeddings])

data = spark.createDataFrame([["Adoro Spark NLP"]]).toDF("text")

result = pipeline.fit(data).transform(data)
```
```scala
val documentAssembler = new DocumentAssembler() 
      .setInputCol("text") 
      .setOutputCol("document")
 
val tokenizer = new Tokenizer() 
    .setInputCols(Array("document"))
    .setOutputCol("token")

val embeddings = CamemBertEmbeddings.pretrained("camembert_embeddings_umberto_wikipedia_uncased_v1","it") 
    .setInputCols(Array("document", "token")) 
    .setOutputCol("embeddings")

val pipeline = new Pipeline().setStages(Array(documentAssembler, tokenizer, embeddings))

val data = Seq("Adoro Spark NLP").toDF("text")

val result = pipeline.fit(data).transform(data)
```
</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|camembert_embeddings_umberto_wikipedia_uncased_v1|
|Compatibility:|Spark NLP 3.4.4+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[sentence, token]|
|Output Labels:|[embeddings]|
|Language:|it|
|Size:|265.5 MB|
|Case sensitive:|false|

## References

- https://huggingface.co/Musixmatch/umberto-wikipedia-uncased-v1
- https://github.com/musixmatchresearch/umberto
- https://linguatools.org/tools/corpora/wikipedia-monolingual-corpora/
- http://bit.ly/35wbSj6
- https://github.com/google/sentencepiece
- https://github.com/musixmatchresearch/umberto
- https://github.com/UniversalDependencies/UD_Italian-ISDT
- https://github.com/UniversalDependencies/UD_Italian-ParTUT
- http://www.evalita.it/
- https://figshare.com/articles/Learning_multilingual_named_entity_recognition_from_Wikipedia/5462500
- https://www.sciencedirect.com/science/article/pii/S0004370212000276?via%3Dihub
- https://github.com/loretoparisi
- https://github.com/simonefrancia
- https://github.com/paulthemagno
- https://twitter.com/Musixmatch
- https://twitter.com/musixmatchai
- https://github.com/musixmatchresearch