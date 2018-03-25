# pdQSM-Extraction
Uma abordagem paralela e distribuída para assegurar a confidencialidade de dados armazenados em nuvem.

Este repositório representa a implementação da abordagem pdQSM-Extraction. A implementação foi realizada em linguagem scala juntamente com o framework Apache Spark.

Estes algoritmos foram executados no ambiente de nuvem :cloud: da Amazon. Obtivemos ganhos significativos em relação à técnica original QSM-Extraction, fazendo uso do paralelismo e da distribuição através do Spark.

São 3 versões:

**QSM5:**

 Essa versão implementa pdQSM-Extraction com índice global, com compressão na Medida:

* Aqui quebramos diretamente o arquivo em blocos com a posicao do byte global [0,tamanho do arquivo]

* 5 argumentos: *tamanho do bloco*  *arquivo original*  *PathToSaveQ*  *PathToSaveS*  *PathToSaveM*

**QSM10:**

Essa versão implementa pdQSM-Extraction com índice no bloco, com compressão na Medida:

* Aqui quebramos diretamente o arquivo em blocos com a posicao do byte local(no bloco) [0,tamanho do bloco]

* 5 argumentos: *tamanho do bloco*  *arquivo original*  *PathToSaveQ*  *PathToSaveS*  *PathToSaveM*


**QSM11:**

Essa versão implementa pdQSM-Extraction com índice no bloco, com compressão na Qualidade, Quantidade e na Medida:

* Aqui quebramos diretamente o arquivo em blocos com a posicao do byte local(no bloco) [0,tamanho do bloco]

* 5 argumentos: *tamanho do bloco*  *arquivo original*  *PathToSaveQ*  *PathToSaveS*  *PathToSaveM*

:+1:
