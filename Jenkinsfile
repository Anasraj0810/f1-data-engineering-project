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
                        bash -lc '
                        export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                        export PATH=$JAVA_HOME/bin:$PATH
                        python3 -m pip install --user -r requirements.txt
                        '
                    '''
                }
            }
        }

        stage('Run Pytest') {
            steps {
                dir('code/code_final') {
                    sh '''
                        bash -lc '
                        export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                        export PATH=$JAVA_HOME/bin:$PATH
                        export SPARK_LOCAL_IP=127.0.0.1
                        export PYSPARK_PYTHON=python3
                        export PYSPARK_DRIVER_PYTHON=python3
                        java -version
                        python3 -c "import pyspark; print(pyspark.__file__)"
                        python3 -m pytest -v
                        '
                    '''
                }
            }
        }

        stage('Diagnose Hadoop Tools via ec2-user') {
            steps {
                sh '''
                    sudo -u ec2-user -H bash -lc '
                    echo "USER:"
                    whoami

                    echo "HOSTNAME:"
                    hostname

                    echo "PWD:"
                    pwd

                    echo "JAVA:"
                    java -version || true

                    echo "Check hdfs:"
                    which hdfs || true

                    echo "Check sqoop:"
                    which sqoop || true

                    echo "Check spark-submit:"
                    which spark-submit || true
                    '
                '''
            }
        }

        stage('Load Full Bronze Data') {
            when {
                expression { params.LOAD_TYPE == 'FULL' }
            }
            steps {
                sh '''
                    sudo -u ec2-user -H bash -lc '
                    cd /var/lib/jenkins/workspace/f1-batch-pipeline

                    export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                    export PATH=$JAVA_HOME/bin:$PATH

                    hdfs dfs -put -f csv_files/full_load/races_initial.csv /tmp/anas_proj2/bronze/races/full/
                    hdfs dfs -put -f csv_files/full_load/results_initial.csv /tmp/anas_proj2/bronze/results/full/

                    sqoop import --connect jdbc:postgresql://13.42.152.118:5432/testdb --username admin --password admin123 --driver org.postgresql.Driver --table anas.drivers_full_v --target-dir /tmp/anas_proj2/bronze/drivers/full --delete-target-dir -m 1

                    sqoop import --connect jdbc:postgresql://13.42.152.118:5432/testdb --username admin --password admin123 --driver org.postgresql.Driver --table anas.constructors_full_v --target-dir /tmp/anas_proj2/bronze/constructors/full --delete-target-dir -m 1
                    '
                '''
            }
        }

        stage('Run Full Silver Build') {
            when {
                expression { params.LOAD_TYPE == 'FULL' }
            }
            steps {
                sh '''
                    sudo -u ec2-user -H bash -lc '
                    cd /var/lib/jenkins/workspace/f1-batch-pipeline/code/code_final

                    export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                    export PATH=$JAVA_HOME/bin:$PATH
                    export SPARK_LOCAL_IP=127.0.0.1

                    spark-submit bronze_to_silver_full.py
                    '
                '''
            }
        }

        stage('Load Incremental Bronze Data') {
            when {
                expression { params.LOAD_TYPE == 'INCREMENTAL' }
            }
            steps {
                sh '''
                    sudo -u ec2-user -H bash -lc '
                    cd /var/lib/jenkins/workspace/f1-batch-pipeline

                    export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                    export PATH=$JAVA_HOME/bin:$PATH

                    hdfs dfs -put -f csv_files/incremental_load/races_incremental.csv /tmp/anas_proj2/bronze/races/incremental/
                    hdfs dfs -put -f csv_files/incremental_load/results_incremental.csv /tmp/anas_proj2/bronze/results/incremental/
                    '
                '''
            }
        }

        stage('Validate Base Silver Exists') {
            when {
                expression { params.LOAD_TYPE == 'INCREMENTAL' }
            }
            steps {
                sh '''
                    sudo -u ec2-user -H bash -lc '
                    export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                    export PATH=$JAVA_HOME/bin:$PATH

                    hdfs dfs -test -e /tmp/anas_proj2/silver/races || { echo "Base silver races not found. Run FULL load first."; exit 1; }
                    hdfs dfs -test -e /tmp/anas_proj2/silver/results || { echo "Base silver results not found. Run FULL load first."; exit 1; }
                    '
                '''
            }
        }

        stage('Run Incremental Silver Merge') {
            when {
                expression { params.LOAD_TYPE == 'INCREMENTAL' }
            }
            steps {
                sh '''
                    sudo -u ec2-user -H bash -lc '
                    cd /var/lib/jenkins/workspace/f1-batch-pipeline/code/code_final

                    export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                    export PATH=$JAVA_HOME/bin:$PATH
                    export SPARK_LOCAL_IP=127.0.0.1

                    spark-submit incremental_silver_merge.py
                    '
                '''
            }
        }

        stage('Refresh Gold') {
            steps {
                sh '''
                    sudo -u ec2-user -H bash -lc '
                    cd /var/lib/jenkins/workspace/f1-batch-pipeline/code/code_final

                    export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                    export PATH=$JAVA_HOME/bin:$PATH
                    export SPARK_LOCAL_IP=127.0.0.1

                    spark-submit silver_to_gold.py
                    '
                '''
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