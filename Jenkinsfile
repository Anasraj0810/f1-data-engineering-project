pipeline {
    agent any

    parameters {
        choice(name: 'LOAD_TYPE', choices: ['FULL', 'INCREMENTAL', 'STREAMING'], description: 'Choose load type')
    }

    environment {
        REMOTE_HOST = '13.41.167.97'
        REMOTE_USER = 'ec2-user'
        PROJECT_PATH = '/home/ec2-user/250226batch/Anasraj/f1_proj_v2'
        CODE_PATH = '/home/ec2-user/250226batch/Anasraj/f1_proj_v2/code/code_final'
        HADOOP_CONF_DIR = '/etc/hadoop/conf'
        YARN_CONF_DIR = '/etc/hadoop/conf'
        HIVE_SERVER = 'jdbc:hive2://ip-172-31-12-74.eu-west-2.compute.internal:10000/default'
        IMPALA_SERVER = 'ip-172-31-3-85.eu-west-2.compute.internal:21000'
    }

    stages {

        stage('Prepare') {
            steps {
                echo 'Using Pipeline script from Jenkins UI'
            }
        }

        stage('Install Dependencies') {
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} 'cd ${CODE_PATH} && python3 -m pip install -r requirements.txt || true'
                """
            }
        }

        stage('Run Pytest') {
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} 'cd ${CODE_PATH} && python3 -m pytest -v'
                """
            }
        }

        stage('Debug Runtime Host') {
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} 'hostname && whoami && pwd && ls -l ${CODE_PATH}'
                """
            }
        }

        stage('Load Full Bronze Data') {
            when {
                expression { params.LOAD_TYPE == 'FULL' }
            }
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} '
                    export HADOOP_CONF_DIR=${HADOOP_CONF_DIR} &&
                    export YARN_CONF_DIR=${YARN_CONF_DIR} &&
                    cd ${PROJECT_PATH} &&

                    hdfs dfs -mkdir -p /tmp/anas_proj2/bronze/races/full &&
                    hdfs dfs -mkdir -p /tmp/anas_proj2/bronze/results/full &&
                    hdfs dfs -mkdir -p /tmp/anas_proj2/bronze/drivers/full &&
                    hdfs dfs -mkdir -p /tmp/anas_proj2/bronze/constructors/full &&

                    hdfs dfs -rm -f /tmp/anas_proj2/bronze/races/full/races_initial.csv || true &&
                    hdfs dfs -rm -f /tmp/anas_proj2/bronze/results/full/results_initial.csv || true &&

                    hdfs dfs -put -f csv_files/full_load/races_initial.csv /tmp/anas_proj2/bronze/races/full/ &&
                    hdfs dfs -put -f csv_files/full_load/results_initial.csv /tmp/anas_proj2/bronze/results/full/ &&

                    sqoop import --connect jdbc:postgresql://13.42.152.118:5432/testdb --username admin --password admin123 --driver org.postgresql.Driver --table anas.drivers_full_v --target-dir /tmp/anas_proj2/bronze/drivers/full --delete-target-dir -m 1 &&

                    sqoop import --connect jdbc:postgresql://13.42.152.118:5432/testdb --username admin --password admin123 --driver org.postgresql.Driver --table anas.constructors_full_v --target-dir /tmp/anas_proj2/bronze/constructors/full --delete-target-dir -m 1
                '
                """
            }
        }

        stage('Run Full Silver Build') {
            when {
                expression { params.LOAD_TYPE == 'FULL' }
            }
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} 'cd ${CODE_PATH} && export PYSPARK_PYTHON=/usr/bin/python3 && export HADOOP_CONF_DIR=${HADOOP_CONF_DIR} && export YARN_CONF_DIR=${YARN_CONF_DIR} && spark-submit --master yarn --deploy-mode client bronze_to_silver_full.py'
                """
            }
        }

        stage('Load Incremental Bronze Data') {
            when {
                expression { params.LOAD_TYPE == 'INCREMENTAL' }
            }
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} '
                    export HADOOP_CONF_DIR=${HADOOP_CONF_DIR} &&
                    export YARN_CONF_DIR=${YARN_CONF_DIR} &&
                    cd ${PROJECT_PATH} &&

                    hdfs dfs -mkdir -p /tmp/anas_proj2/bronze/races/incremental &&
                    hdfs dfs -mkdir -p /tmp/anas_proj2/bronze/results/incremental &&

                    hdfs dfs -rm -f /tmp/anas_proj2/bronze/races/incremental/races_incremental.csv || true &&
                    hdfs dfs -rm -f /tmp/anas_proj2/bronze/results/incremental/results_incremental.csv || true &&

                    hdfs dfs -put -f csv_files/incremental_load/races_incremental.csv /tmp/anas_proj2/bronze/races/incremental/ &&
                    hdfs dfs -put -f csv_files/incremental_load/results_incremental.csv /tmp/anas_proj2/bronze/results/incremental/
                '
                """
            }
        }

        stage('Validate Base Silver Data') {
            when {
                expression { params.LOAD_TYPE == 'INCREMENTAL' }
            }
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} '
                    hdfs dfs -ls /tmp/anas_proj2/silver &&
                    hdfs dfs -ls /tmp/anas_proj2/silver/races &&
                    hdfs dfs -ls /tmp/anas_proj2/silver/results
                '
                """
            }
        }

        stage('Run Incremental Silver Merge') {
            when {
                expression { params.LOAD_TYPE == 'INCREMENTAL' }
            }
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} 'cd ${CODE_PATH} && export PYSPARK_PYTHON=/usr/bin/python3 && export HADOOP_CONF_DIR=${HADOOP_CONF_DIR} && export YARN_CONF_DIR=${YARN_CONF_DIR} && spark-submit --master yarn --deploy-mode client incremental_silver_merge.py'
                """
            }
        }

        stage('Refresh Gold') {
            when {
                expression { params.LOAD_TYPE == 'FULL' || params.LOAD_TYPE == 'INCREMENTAL' }
            }
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} 'cd ${CODE_PATH} && export PYSPARK_PYTHON=/usr/bin/python3 && export HADOOP_CONF_DIR=${HADOOP_CONF_DIR} && export YARN_CONF_DIR=${YARN_CONF_DIR} && spark-submit --master yarn --deploy-mode client silver_to_gold.py'
                """
            }
        }

        stage('Validate Streaming Bronze Data') {
            when {
                expression { params.LOAD_TYPE == 'STREAMING' }
            }
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} '
                    hdfs dfs -ls /tmp/anas_proj2/bronze/live_lap_times
                '
                """
            }
        }

        stage('Run Streaming Silver Build') {
            when {
                expression { params.LOAD_TYPE == 'STREAMING' }
            }
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} 'cd ${CODE_PATH}/streaming && export PYSPARK_PYTHON=/usr/bin/python3 && export HADOOP_CONF_DIR=${HADOOP_CONF_DIR} && export YARN_CONF_DIR=${YARN_CONF_DIR} && spark-submit --master yarn --deploy-mode client bronze_to_silver_stream.py'
                """
            }
        }

        stage('Run Streaming Gold Refresh') {
            when {
                expression { params.LOAD_TYPE == 'STREAMING' }
            }
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} 'cd ${CODE_PATH}/streaming && export PYSPARK_PYTHON=/usr/bin/python3 && export HADOOP_CONF_DIR=${HADOOP_CONF_DIR} && export YARN_CONF_DIR=${YARN_CONF_DIR} && spark-submit --master yarn --deploy-mode client silver_to_gold_stream.py'
                """
            }
        }

        stage('Refresh Hive Batch Tables') {
            when {
                expression { params.LOAD_TYPE == 'FULL' || params.LOAD_TYPE == 'INCREMENTAL' }
            }
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} '
                    cat > /tmp/anas_proj2_refresh_hive.sql <<EOF
CREATE DATABASE IF NOT EXISTS anas_proj2;

DROP TABLE IF EXISTS anas_proj2.race_results;

CREATE EXTERNAL TABLE anas_proj2.race_results (
    resultid INT,
    raceid INT,
    race_name STRING,
    round INT,
    race_date DATE,
    driverid INT,
    driver_name STRING,
    driver_nationality STRING,
    constructorid INT,
    constructor_name STRING,
    constructor_nationality STRING,
    grid INT,
    position INT,
    positionorder INT,
    points DOUBLE,
    laps INT,
    fastestlap INT,
    fastestlapspeed DOUBLE
)
PARTITIONED BY (year INT)
STORED AS PARQUET
LOCATION "/tmp/anas_proj2/gold/race_results";

MSCK REPAIR TABLE anas_proj2.race_results;

DROP TABLE IF EXISTS anas_proj2.driver_points_summary;
CREATE TABLE anas_proj2.driver_points_summary AS
SELECT year, driver_name, SUM(points) AS total_points
FROM anas_proj2.race_results
GROUP BY year, driver_name;

DROP TABLE IF EXISTS anas_proj2.constructor_points_summary;
CREATE TABLE anas_proj2.constructor_points_summary AS
SELECT year, constructor_name, SUM(points) AS total_points
FROM anas_proj2.race_results
GROUP BY year, constructor_name;

DROP TABLE IF EXISTS anas_proj2.race_winners_summary;
CREATE TABLE anas_proj2.race_winners_summary AS
SELECT year, race_name, race_date, driver_name, constructor_name, points
FROM anas_proj2.race_results
WHERE position = 1;
EOF

                    beeline -u "${HIVE_SERVER}" -n hive -f /tmp/anas_proj2_refresh_hive.sql
                '
                """
            }
        }

        stage('Refresh Hive Streaming Tables') {
            when {
                expression { params.LOAD_TYPE == 'STREAMING' }
            }
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} '
                    cat > /tmp/anas_proj2_refresh_streaming_hive.sql <<EOF
CREATE DATABASE IF NOT EXISTS anas_proj2;

DROP TABLE IF EXISTS anas_proj2.live_driver_summary;

CREATE EXTERNAL TABLE anas_proj2.live_driver_summary (
    raceid INT,
    driverid INT,
    lap_count BIGINT,
    fastest_lap_ms BIGINT,
    avg_lap_ms DOUBLE,
    latest_lap INT,
    last_event_ts TIMESTAMP
)
PARTITIONED BY (ingest_date DATE)
STORED AS PARQUET
LOCATION "/tmp/anas_proj2/gold/live_driver_summary";

MSCK REPAIR TABLE anas_proj2.live_driver_summary;
EOF

                    beeline -u "${HIVE_SERVER}" -n hive -f /tmp/anas_proj2_refresh_streaming_hive.sql
                '
                """
            }
        }

        stage('Refresh Impala Metadata') {
            when {
                expression { params.LOAD_TYPE == 'FULL' || params.LOAD_TYPE == 'INCREMENTAL' }
            }
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} '
                    impala-shell -i ${IMPALA_SERVER} -q "
                        INVALIDATE METADATA anas_proj2.race_results;
                        INVALIDATE METADATA anas_proj2.driver_points_summary;
                        INVALIDATE METADATA anas_proj2.constructor_points_summary;
                        INVALIDATE METADATA anas_proj2.race_winners_summary;
                    "
                '
                """
            }
        }

        stage('Refresh Impala Streaming Metadata') {
            when {
                expression { params.LOAD_TYPE == 'STREAMING' }
            }
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} '
                    impala-shell -i ${IMPALA_SERVER} -q "
                        INVALIDATE METADATA anas_proj2.live_driver_summary;
                    "
                '
                """
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully.'
        }
        failure {
            echo 'Pipeline failed. Check Jenkins console output.'
        }
        always {
            echo "Build finished with LOAD_TYPE=${params.LOAD_TYPE}"
        }
    }
}