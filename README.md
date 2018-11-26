# pdQSM-Extraction
Uma abordagem paralela e distribuída para assegurar a confidencialidade de dados armazenados em nuvem.

Este repositório representa a implementação da abordagem pdQSM-Extraction. A implementação foi realizada em linguagem scala juntamente com o framework Apache Spark.

O propósito dessa pesquisa é melhorar a fase de Decomposição da abordagem QSM-Extracion, além de apropriar a execução para grandes volumos de dados(>1Gb)

Estes algoritmos foram executados no ambiente de nuvem :cloud: da Amazon. Obtivemos ganhos significativos em relação à QSM-Extraction, fazendo uso do paralelismo e da distribuição através do Spark.

São 3 versões:

**pdQSMv1**

 Essa versão implementa pdQSM-Extraction com índice global, com compressão na Medida:

* Aqui quebramos diretamente o arquivo em blocos com a posicao do byte global [0,tamanho do arquivo]

* 5 argumentos: *tamanho do bloco*  *arquivo original*  *PathToSaveQ*  *PathToSaveS*  *PathToSaveM*

**pdQSMv2**

Essa versão implementa pdQSM-Extraction com índice no bloco, com compressão na Medida:

* Aqui quebramos diretamente o arquivo em blocos com a posicao do byte local(no bloco) [0,tamanho do bloco]

* 5 argumentos: *tamanho do bloco*  *arquivo original*  *PathToSaveQ*  *PathToSaveS*  *PathToSaveM*


**pdQSMv3**

Essa versão implementa pdQSM-Extraction com índice no bloco, com compressão na Qualidade, Quantidade e na Medida:

* Aqui quebramos diretamente o arquivo em blocos com a posicao do byte local(no bloco) [0,tamanho do bloco]

* 5 argumentos: *tamanho do bloco*  *arquivo original*  *PathToSaveQ*  *PathToSaveS*  *PathToSaveM*

#### Para executar esses algoritmos, é necessário:

 * Gerar arquivo .jar da aplicação com todas as devidas dependências.
   * Exemplo: https://data-flair.training/blogs/create-spark-scala-project/

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
     
 * Exemplo:
    * Cluster com 3 máquinas da seguinte configuração:
        * 3 máquinas
        * Intel(R) Xeon(R) CPU 2.40GHz, 4 núcleos,
        * 16Gb ram
        * 90gb SSD
        * spark-2.2.1-bin-hadoop2.7/bin/spark-submit --class br.ufc.spark.pdQSMv1 --driver-memory 13g --executor-memory 4g --driver-cores 3 --executor-cores 1 -- num-executors 6 --conf spark.default.parallelism=12  --master spark://172.31.7.9:7077 /home/ubuntu/pdQSM.jar 256 "PathToOriginalFile* *PathToSaveQ* *PathToSaveS*  *PathToSaveM*

Observações importantes:
  * Utilizar outros tipos de serialização para ganho de desempenho, como por exemplo: Kryo.
  * Dica de link para configuração do cluster: http://c2fo.io/c2fo/spark/aws/emr/2016/07/06/apache-spark-config-cheatsheet/

:+1:
