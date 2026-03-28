from pyspark.sql import SparkSession

spark = SparkSession.builder.appName("anas_full_load").getOrCreate()
 
jdbc_url = "jdbc:postgresql://13.42.152.118:5432/testdb"
 
properties = {"user": "admin","password": "admin123","driver": "org.postgresql.Driver"}
 


#creating data frame

df_drivers = spark.read.jdbc(url=jdbc_url,table="anas.drivers_full",properties=properties)
df_constructors = spark.read.jdbc(url=jdbc_url,table="anas.constructors_full",properties=properties)


df_races_initial=spark.read.csv('250226hdfs/anasraj/project/bronze/races_initial.csv',header=True,inferSchema=True)
df_results_initial=spark.read.csv('250226hdfs/anasraj/project/bronze/results_initial.csv',header=True,inferSchema=True)


#writing to bronze


df_drivers.write.mode("overwrite").parquet("/tmp/anas_proj/bronze/drivers")
df_constructors.write.mode("overwrite").parquet("/tmp/anas_proj/bronze/constructors")
 
 
df_races_initial.write.mode("overwrite").parquet("/tmp/anas_proj/bronze/races_initial")
df_results_initial.write.mode("overwrite").parquet("/tmp/anas_proj/bronze/results_initial")