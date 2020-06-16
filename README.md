# BIG DATA TECHNOLOGY PROJECT
&copy; Guethie Hane & Thanh Dat Vo
at M.I.U, 6-2020

## Purpose
Research and implement Spark Streaming, in combination with other technologies to create a pipeline to process data and visualize it.
Installation and running Cloudera in cloud environment
## Data
This sample data contains behavior data for a one month (October 2019) from a large multi-category online store.
Each row in the file represents an event. All events are related to products and users. There are different types of events.
## Technologies
### Big data technologies
Cloudera
Kafka
Spark SQl
Spark Streaming
Hive
Hadoop HDFS

### Other technologies
Elasticsearch
Kibana
Google Cloud
Centos 7
Nginx

## Troubleshooting
Installing and running Cloudera in cloud for production is a difficult task. 
Each mistake can lead to reinstalling the whole system which takes about 2 hours & we already have had to reinstalled at least 20 times. 
These are the troubleshootings that we implement for this project

Master Node and Data Nodes should be the same OS and same OS version, so that cloudera can install same package for every node
The number of Data Nodes should be 3 to satisfy the default requirement of Cloudera
Limit exposed ports and turn off the nodes when not in use so that Google Cloud will not suspect crypto mining activities

Configure Kafka Offset Commit Topic Replication Factor to 1 and Advertised Host to the static ip so that there will be one broker running 
Alway run sudo -u hdfs before spark-submit to have right access to write log file in hdfs
Installing new Java will lead to conflict with cloudera java home, so use cloudera Java for other software

Installing elasticsearch with sudo will lead to no java error even we already set $JAVA_HOME value, so install without sudo
After install elasticsearch, go to  /etc/sysconfig/elasticsearch to update JAVA_HOME to cloudera java folder because the elasticsearch will not looking for JAVA_HOME environment variable in the OS

When access to the default port of kibana, there is be a redirect to a sub path, if we only expose this port and access through browser, there will be a 3xx error. So we have to use a proxy like Nginx to manage this redirection in client 
Libraries versions in Maven should be the same as their softwares version in Cloudera packages 

In SparkConf, set the property  spark.driver.allowMultipleContexts to true in order to use multiple SparkContext in the same JVM
When using maven build, config goals to assembly:single in order to wrap all the libraries with the code to one jar file 

## Demo
*Kafka Spark Elasticsearch Kibana*[https://youtu.be/NUWKg9pQlbY]

*Hive SparkSQL*[https://youtu.be/NUWKg9pQlbY]