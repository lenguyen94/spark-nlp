---
layout: model
title: Sentence Embeddings - sbert tiny (tuned)
author: John Snow Labs
name: sbert_jsl_tiny_umls_uncased
date: 2021-05-14
tags: [embeddings, clinical, licensed, en]
task: Embeddings
language: en
edition: Spark NLP for Healthcare 3.0.3
spark_version: 3.0
supported: true
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---


## Description


This model is trained to generate contextual sentence embeddings of input sentences.


{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/clinical/models/sbert_jsl_tiny_umls_uncased_en_3.0.3_2.4_1621017145970.zip){:.button.button-orange.button-orange-trans.arr.button-icon}


## How to use






<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}

```python
sbiobert_embeddings = BertSentenceEmbeddings\
.pretrained("sbert_jsl_tiny_umls_uncased","en","clinical/models")\
.setInputCols(["sentence"])\
.setOutputCol("sbert_embeddings")
```
```scala
val sbiobert_embeddings = BertSentenceEmbeddings
.pretrained("sbert_jsl_tiny_umls_uncased","en","clinical/models")
.setInputCols(Array("sentence"))
.setOutputCol("sbert_embeddings")
```


{:.nlu-block}
```python
import nlu
nlu.load("en.embed_sentence.bert.jsl_tiny_umls_uncased").predict("""Put your text here.""")
```

</div>


## Results


```bash
Gives a 768 dimensional vector representation of the sentence.
```


{:.model-param}
## Model Information


{:.table-model}
|---|---|
|Model Name:|sbert_jsl_tiny_umls_uncased|
|Compatibility:|Spark NLP for Healthcare 3.0.3+|
|License:|Licensed|
|Edition:|Official|
|Language:|en|
|Case sensitive:|false|


## Data Source


Tuned on MedNLI and UMLS dataset


## Benchmarking


```bash
MedNLI    Score
Acc       0.616
STS(cos)  0.632
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbNzA4MjAyMDA1XX0=
-->