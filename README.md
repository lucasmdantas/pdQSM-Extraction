# pdQSM-Extraction
Uma abordagem paralela e distribuída para assegurar a confidencialidade de dados armazenados em nuvem.

Este repositório representa a implementação da abordagem pdQSM-Extraction. A implementação foi realizada em linguagem scala juntamente com o framework Apache Spark.

Estes algoritmos foram executados no ambiente de nuvem :cloud: da Amazon. Obtivemos ganhos significativos em relação à técnica original QSM-Extraction, fazendo uso do paralelismo e da distribuição através do Spark.

Para executar esses algoritmos, é necessário:

 * Local:
   * Ter o spark na máquina, além do java e scala estarem devidamente configurados. O spark pode ser baixado em: https://spark.apache.org/downloads.html
 * Nuvem:
   * Ter uma estrutura de nuvem.
   * As máquinas devem poder se comunicar entre si, ou seja, IP's e portas devem estar liberadas entre elas.
   * Spark deve estar instalado e o java e scala  devidamente configurados em todas as máquinas do Cluster.
   * Cluster deve estar devidamente configurado:
     * Master e Slaves devem ser conhecidos por todas as máquinas.
   * Utilizar o comando submit do Submit do spark, colocando:
     * Localização do master
     * Devidas configurações de execução do cluster
     * Devidos argumentos do algoritmo.
  

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
