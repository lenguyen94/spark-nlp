---
layout: model
title: Sentence Embeddings - sbert medium (tuned)
author: John Snow Labs
name: sbert_jsl_medium_rxnorm_uncased
date: 2021-12-23
tags: [embeddings, clinical, licensed, en, open_source]
task: Embeddings
language: en
edition: Spark NLP for Healthcare 3.3.4
spark_version: 3.0
supported: true
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

This model maps sentences & documents to a 512-dimensional dense vector space by using average pooling on top of BERT model. It's also fine-tuned on the RxNorm dataset to help generalization over medication-related datasets.

## Predicted Entities



{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/sbert_jsl_medium_rxnorm_uncased_en_3.3.4_3.0_1640270758064.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
sentence_embeddings = BertSentenceEmbeddings.pretrained("sbert_jsl_medium_rxnorm_uncased", "en", "clinical/models")
.setInputCols(Array("sentence"))
.setOutputCol("sbert_embeddings")
```
```scala
val sentence_embeddings = BertSentenceEmbeddings.pretrained("sbert_jsl_medium_rxnorm_uncased", "en", "clinical/models")
.setInputCols("sentence")
.setOutputCol("sbert_embeddings")
```


{:.nlu-block}
```python
import nlu
nlu.load("en.embed_sentence.bert_uncased.rxnorm").predict("""Put your text here.""")
```

</div>

## Results

```bash
Gives a 512-dimensional vector representation of the sentence.
```

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|sbert_jsl_medium_rxnorm_uncased|
|Compatibility:|Spark NLP for Healthcare 3.3.4+|
|License:|Open Source|
|Edition:|Official|
|Language:|en|
|Size:|153.9 MB|
|Case sensitive:|false|
