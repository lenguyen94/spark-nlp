---
layout: model
title: GPT2 text-to-text model (Medium)
author: John Snow Labs
name: gpt2_medium
date: 2021-12-03
tags: [gpt2, en, open_source]
task: Text Generation
language: en
edition: Spark NLP 3.4.0
spark_version: 3.0
supported: true
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

GPT-2 displays a broad set of capabilities, including the ability to generate conditional synthetic text samples of unprecedented quality, where the model is primed with an input and it generates a lengthy continuation. This is the medium model (smaller than Base). Other models (base, large) are also available in [Models Hub](https://nlp.johnsnowlabs.com/models?task=Text+Generation)

## Predicted Entities



{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/gpt2_medium_en_3.4.0_3.0_1638517188768.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
documentAssembler = DocumentAssembler() \\
.setInputCol("text") \\
.setOutputCol("documents")

gpt2 = GPT2Transformer.pretrained("gpt2_medium") \\
.setInputCols(["documents"]) \\
.setMaxOutputLength(50) \\
.setOutputCol("generation")

pipeline = Pipeline().setStages([documentAssembler, gpt2])
data = spark.createDataFrame([["My name is Leonardo."]]).toDF("text")
result = pipeline.fit(data).transform(data)
result.select("summaries.generation").show(truncate=False)
```
```scala
val documentAssembler = new DocumentAssembler()
.setInputCol("text")
.setOutputCol("documents")

val gpt2 = GPT2Transformer.pretrained("gpt2_medium")
.setInputCols(Array("documents"))
.setMinOutputLength(10)
.setMaxOutputLength(50)
.setDoSample(false)
.setTopK(50)
.setNoRepeatNgramSize(3)
.setOutputCol("generation")

val pipeline = new Pipeline().setStages(Array(documentAssembler, gpt2))
val data = Seq("My name is Leonardo.").toDF("text")
val result = pipeline.fit(data).transform(data)
results.select("generation.result").show(truncate = false)
```


{:.nlu-block}
```python
import nlu
nlu.load("en.gpt2.medium").predict("""My name is Leonardo.""")
```

</div>

## Results

```bash
+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
|result                                                                                                                                                                                                                  |
+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
|[ My name is Leonardo. I am a man of letters. I have been a man for many years. I was born in the year 1776. I came to the   |
| United States in 1776, and I have lived in the United Kingdom since 1776]                                                                                          |
+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
```

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|gpt2_medium|
|Compatibility:|Spark NLP 3.4.0+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[documents]|
|Output Labels:|[generation]|
|Language:|en|

## Data Source

OpenAI WebText  - a corpus created by scraping web pages with emphasis on document quality. It consists of over 8 million documents for a total of 40 GB of text. All Wikipedia documents were removed from WebText.
