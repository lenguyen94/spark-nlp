---
layout: model
title: Recognize Entities DL pipeline for German - Large
author: John Snow Labs
name: entity_recognizer_lg
date: 2021-03-23
tags: [open_source, german, entity_recognizer_lg, pipeline, de]
supported: true
task: [Named Entity Recognition, Lemmatization]
language: de
edition: Spark NLP 3.0.0
spark_version: 3.0
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

The entity_recognizer_lg is a pretrained pipeline that we can use to process text with a simple pipeline that performs basic processing steps 
and recognizes entities .
It performs most of the common text processing tasks on your dataframe

{:.btn-box}
[Live Demo](https://demo.johnsnowlabs.com/public/NER_EN_18/){:.button.button-orange}
[Open in Colab](https://colab.research.google.com/github/JohnSnowLabs/spark-nlp-workshop/blob/master/tutorials/streamlit_notebooks/NER_EN.ipynb){:.button.button-orange.button-orange-trans.co.button-icon}
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/entity_recognizer_lg_de_3.0.0_3.0_1616491780964.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python

from sparknlp.pretrained import PretrainedPipelinein
pipeline = PretrainedPipeline('entity_recognizer_lg', lang = 'de')
annotations =  pipeline.fullAnnotate(""Hallo aus John Snow Labs! "")[0]
annotations.keys()

```
```scala

val pipeline = new PretrainedPipeline("entity_recognizer_lg", lang = "de")
val result = pipeline.fullAnnotate("Hallo aus John Snow Labs! ")(0)


```

{:.nlu-block}
```python

import nlu
text = [""Hallo aus John Snow Labs! ""]
result_df = nlu.load('de.ner.recognizer.lg').predict(text)
result_df

```
</div>

## Results

```bash
|    | document                       | sentence                      | token                                     | embeddings                   | ner                                   | entities            |
|---:|:-------------------------------|:------------------------------|:------------------------------------------|:-----------------------------|:--------------------------------------|:--------------------|
|  0 | ['Hallo aus John Snow Labs! '] | ['Hallo aus John Snow Labs!'] | ['Hallo', 'aus', 'John', 'Snow', 'Labs!'] | [[-0.245989993214607,.,...]] | ['O', 'O', 'I-PER', 'I-PER', 'I-PER'] | ['John Snow Labs!'] |
```

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|entity_recognizer_lg|
|Type:|pipeline|
|Compatibility:|Spark NLP 3.0.0+|
|License:|Open Source|
|Edition:|Official|
|Language:|de|