pipeline {
    agent any

    parameters {
        choice(
            name: 'LOAD_TYPE',
            choices: ['FULL', 'INCREMENTAL'],
            description: 'Choose which batch pipeline to run'
        )
    }

    environment {
        JAVA_HOME = '/usr/java/jdk1.8.0_232-cloudera'
        SPARK_LOCAL_IP = '127.0.0.1'
        PATH = '/usr/local/bin:/usr/bin:/bin:/usr/java/jdk1.8.0_232-cloudera/bin'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Install Dependencies') {
            steps {
                dir('code/code_final') {
                    sh '''
                        export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                        export PATH=/usr/local/bin:/usr/bin:/bin:/usr/java/jdk1.8.0_232-cloudera/bin
                        python3 -m pip install --user -r requirements.txt
                    '''
                }
            }
        }

        stage('Run Pytest') {
            steps {
                dir('code/code_final') {
                    sh '''
                        export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                        export PATH=/usr/local/bin:/usr/bin:/bin:/usr/java/jdk1.8.0_232-cloudera/bin
                        export SPARK_LOCAL_IP=127.0.0.1
                        export PYSPARK_PYTHON=python3
                        export PYSPARK_DRIVER_PYTHON=python3
                        java -version
                        python3 -c "import pyspark; print(pyspark.__file__)"
                        python3 -m pytest -v
                    '''
                }
            }
        }

        stage('Diagnose Hadoop Tools') {
            steps {
                sh '''
                    echo "USER:"
                    whoami

                    echo "PWD:"
                    pwd

                    echo "PATH:"
                    echo $PATH

                    echo "JAVA:"
                    java -version || true

                    echo "Check hdfs:"
                    ls -l /usr/bin/hdfs || true
                    which hdfs || true
                    command -v hdfs || true

                    echo "Check sqoop:"
                    ls -l /usr/bin/sqoop || true
                    which sqoop || true
                    command -v sqoop || true

                    echo "Check spark-submit:"
                    ls -l /usr/local/bin/spark-submit || true
                    which spark-submit || true
                    command -v spark-submit || true
                '''
            }
        }

        stage('Load Full Bronze Data') {
            when {
                expression { params.LOAD_TYPE == 'FULL' }
            }
            steps {
                sh '''
                    export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                    export PATH=/usr/local/bin:/usr/bin:/bin:/usr/java/jdk1.8.0_232-cloudera/bin

                    /usr/bin/hdfs dfs -put -f csv_files/full_load/races_initial.csv /tmp/anas_proj2/bronze/races/full/
                    /usr/bin/hdfs dfs -put -f csv_files/full_load/results_initial.csv /tmp/anas_proj2/bronze/results/full/

                    /usr/bin/sqoop import --connect jdbc:postgresql://13.42.152.118:5432/testdb --username admin --password admin123 --driver org.postgresql.Driver --table anas.drivers_full_v --target-dir /tmp/anas_proj2/bronze/drivers/full --delete-target-dir -m 1

                    /usr/bin/sqoop import --connect jdbc:postgresql://13.42.152.118:5432/testdb --username admin --password admin123 --driver org.postgresql.Driver --table anas.constructors_full_v --target-dir /tmp/anas_proj2/bronze/constructors/full --delete-target-dir -m 1
                '''
            }
        }

        stage('Run Full Silver Build') {
            when {
                expression { params.LOAD_TYPE == 'FULL' }
            }
            steps {
                dir('code/code_final') {
                    sh '''
                        export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                        export PATH=/usr/local/bin:/usr/bin:/bin:/usr/java/jdk1.8.0_232-cloudera/bin
                        export SPARK_LOCAL_IP=127.0.0.1
                        /usr/local/bin/spark-submit bronze_to_silver_full.py
                    '''
                }
            }
        }

        stage('Load Incremental Bronze Data') {
            when {
                expression { params.LOAD_TYPE == 'INCREMENTAL' }
            }
            steps {
                sh '''
                    export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                    export PATH=/usr/local/bin:/usr/bin:/bin:/usr/java/jdk1.8.0_232-cloudera/bin

                    /usr/bin/hdfs dfs -put -f csv_files/incremental_load/races_incremental.csv /tmp/anas_proj2/bronze/races/incremental/
                    /usr/bin/hdfs dfs -put -f csv_files/incremental_load/results_incremental.csv /tmp/anas_proj2/bronze/results/incremental/
                '''
            }
        }

        stage('Validate Base Silver Exists') {
            when {
                expression { params.LOAD_TYPE == 'INCREMENTAL' }
            }
            steps {
                sh '''
                    export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                    export PATH=/usr/local/bin:/usr/bin:/bin:/usr/java/jdk1.8.0_232-cloudera/bin

                    /usr/bin/hdfs dfs -test -e /tmp/anas_proj2/silver/races || { echo "Base silver races not found. Run FULL load first."; exit 1; }
                    /usr/bin/hdfs dfs -test -e /tmp/anas_proj2/silver/results || { echo "Base silver results not found. Run FULL load first."; exit 1; }
                '''
            }
        }

        stage('Run Incremental Silver Merge') {
            when {
                expression { params.LOAD_TYPE == 'INCREMENTAL' }
            }
            steps {
                dir('code/code_final') {
                    sh '''
                        export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                        export PATH=/usr/local/bin:/usr/bin:/bin:/usr/java/jdk1.8.0_232-cloudera/bin
                        export SPARK_LOCAL_IP=127.0.0.1
                        /usr/local/bin/spark-submit incremental_silver_merge.py
                    '''
                }
            }
        }

        stage('Refresh Gold') {
            steps {
                dir('code/code_final') {
                    sh '''
                        export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                        export PATH=/usr/local/bin:/usr/bin:/bin:/usr/java/jdk1.8.0_232-cloudera/bin
                        export SPARK_LOCAL_IP=127.0.0.1
                        /usr/local/bin/spark-submit silver_to_gold.py
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Jenkins batch pipeline completed successfully.'
        }
        failure {
            echo 'Jenkins batch pipeline failed. Check console logs and pipeline log files.'
        }
    }
}