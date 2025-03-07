---
layout: model
title: Telugu RoBERTa Embeddings (from neuralspace-reverie)
author: John Snow Labs
name: roberta_embeddings_indic_transformers_te_roberta
date: 2022-04-14
tags: [roberta, embeddings, te, open_source]
task: Embeddings
language: te
edition: Spark NLP 3.4.2
spark_version: 3.0
supported: true
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Pretrained RoBERTa Embeddings model, uploaded to Hugging Face, adapted and imported into Spark NLP. `indic-transformers-te-roberta` is a Telugu model orginally trained by `neuralspace-reverie`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/roberta_embeddings_indic_transformers_te_roberta_te_3.4.2_3.0_1649947854411.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

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

embeddings = RoBertaEmbeddings.pretrained("roberta_embeddings_indic_transformers_te_roberta","te") \
.setInputCols(["document", "token"]) \
.setOutputCol("embeddings")

pipeline = Pipeline(stages=[documentAssembler, tokenizer, embeddings])

data = spark.createDataFrame([["నేను స్పార్క్ nlp ను ప్రేమిస్తున్నాను"]]).toDF("text")

result = pipeline.fit(data).transform(data)
```
```scala
val documentAssembler = new DocumentAssembler() 
.setInputCol("text") 
.setOutputCol("document")

val tokenizer = new Tokenizer() 
.setInputCols(Array("document"))
.setOutputCol("token")

val embeddings = RoBertaEmbeddings.pretrained("roberta_embeddings_indic_transformers_te_roberta","te") 
.setInputCols(Array("document", "token")) 
.setOutputCol("embeddings")

val pipeline = new Pipeline().setStages(Array(documentAssembler, tokenizer, embeddings))

val data = Seq("నేను స్పార్క్ nlp ను ప్రేమిస్తున్నాను").toDF("text")

val result = pipeline.fit(data).transform(data)
```


{:.nlu-block}
```python
import nlu
nlu.load("te.embed.indic_transformers_te_roberta").predict("""నేను స్పార్క్ nlp ను ప్రేమిస్తున్నాను""")
```

</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|roberta_embeddings_indic_transformers_te_roberta|
|Compatibility:|Spark NLP 3.4.2+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[sentence, token]|
|Output Labels:|[bert]|
|Language:|te|
|Size:|314.6 MB|
|Case sensitive:|true|

## References

- https://huggingface.co/neuralspace-reverie/indic-transformers-te-roberta
- https://oscar-corpus.com/