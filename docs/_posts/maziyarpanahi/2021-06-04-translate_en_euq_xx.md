---
layout: model
title: Translate English to Basque (family) Pipeline
author: John Snow Labs
name: translate_en_euq
date: 2021-06-04
tags: [open_source, pipeline, seq2seq, translation, en, euq, xx, multilingual]
task: Translation
language: xx
edition: Spark NLP 3.1.0
spark_version: 3.0
supported: true
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Marian is an efficient, free Neural Machine Translation framework written in pure C++ with minimal dependencies. It is mainly being developed by the Microsoft Translator team. Many academic (most notably the University of Edinburgh and in the past the Adam Mickiewicz University in Poznań) and commercial contributors help with its development.
It is currently the engine behind the Microsoft Translator Neural Machine Translation services and being deployed by many companies, organizations and research projects (see below for an incomplete list).

source languages: en

target languages: euq

{:.btn-box}
[Live Demo](https://demo.johnsnowlabs.com/public/TRANSLATION_MARIAN/){:.button.button-orange}
[Open in Colab](https://colab.research.google.com/github/JohnSnowLabs/spark-nlp-workshop/blob/master/tutorials/streamlit_notebooks/TRANSLATION_MARIAN.ipynb){:.button.button-orange.button-orange-trans.co.button-icon}
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/translate_en_euq_xx_3.1.0_2.4_1622845820201.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
from sparknlp.pretrained import PretrainedPipeline 
pipeline = PretrainedPipeline("translate_en_euq", lang = "xx") 
pipeline.annotate("Your sentence to translate!")
```
```scala

import com.johnsnowlabs.nlp.pretrained.PretrainedPipeline
val pipeline = new PretrainedPipeline("translate_en_euq", lang = "xx")
pipeline.annotate("Your sentence to translate!")
```

{:.nlu-block}
```python

import nlu
text = ["text to translate"]
translate_df = nlu.load('xx.English.translate_to.Basque (family)').predict(text, output_level='sentence')
translate_df
```
</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|translate_en_euq|
|Type:|pipeline|
|Compatibility:|Spark NLP 3.1.0+|
|License:|Open Source|
|Edition:|Official|
|Language:|xx|

## Data Source

[https://github.com/Helsinki-NLP/OPUS-MT-train/tree/master/models](https://github.com/Helsinki-NLP/OPUS-MT-train/tree/master/models)

## Included Models

- DocumentAssembler
- SentenceDetectorDLModel
- MarianTransformer