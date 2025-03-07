---
layout: model
title: German Electra Embeddings (from deepset)
author: John Snow Labs
name: electra_embeddings_gelectra_large_generator
date: 2022-05-17
tags: [de, open_source, electra, embeddings]
task: Embeddings
language: de
edition: Spark NLP 3.4.4
spark_version: 3.0
supported: true
article_header:
  type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Pretrained Electra Embeddings model, adapted from Hugging Face and curated to provide scalability and production-readiness using Spark NLP. `gelectra-large-generator` is a German model orginally trained by `deepset`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/electra_embeddings_gelectra_large_generator_de_3.4.4_3.0_1652786854236.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

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
  
embeddings = BertEmbeddings.pretrained("electra_embeddings_gelectra_large_generator","de") \
    .setInputCols(["document", "token"]) \
    .setOutputCol("embeddings")
    
pipeline = Pipeline(stages=[documentAssembler, tokenizer, embeddings])

data = spark.createDataFrame([["Ich liebe Spark NLP"]]).toDF("text")

result = pipeline.fit(data).transform(data)
```
```scala
val documentAssembler = new DocumentAssembler() 
      .setInputCol("text") 
      .setOutputCol("document")
 
val tokenizer = new Tokenizer() 
    .setInputCols(Array("document"))
    .setOutputCol("token")

val embeddings = BertEmbeddings.pretrained("electra_embeddings_gelectra_large_generator","de") 
    .setInputCols(Array("document", "token")) 
    .setOutputCol("embeddings")

val pipeline = new Pipeline().setStages(Array(documentAssembler, tokenizer, embeddings))

val data = Seq("Ich liebe Spark NLP").toDF("text")

val result = pipeline.fit(data).transform(data)
```
</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|electra_embeddings_gelectra_large_generator|
|Compatibility:|Spark NLP 3.4.4+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[sentence, token]|
|Output Labels:|[embeddings]|
|Language:|de|
|Size:|194.8 MB|
|Case sensitive:|true|

## References

- https://huggingface.co/deepset/gelectra-large-generator
- https://arxiv.org/pdf/2010.10906.pdf
- https://arxiv.org/pdf/2010.10906.pdf
- https://deepset.ai/german-bert
- https://deepset.ai/germanquad
- https://github.com/deepset-ai/FARM
- https://github.com/deepset-ai/haystack/
- https://twitter.com/deepset_ai
- https://www.linkedin.com/company/deepset-ai/
- https://haystack.deepset.ai/community/join
- https://github.com/deepset-ai/haystack/discussions
- https://deepset.ai
- http://www.deepset.ai/jobs