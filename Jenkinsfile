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

        stage('Diagnose Hadoop Tools') {
            steps {
                sh '''
                    bash -lc '
                    export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                    export PATH=$JAVA_HOME/bin:$PATH

                    [ -f /etc/profile ] && . /etc/profile || true
                    [ -f ~/.bash_profile ] && . ~/.bash_profile || true
                    [ -f ~/.bashrc ] && . ~/.bashrc || true

                    echo "USER:"
                    whoami

                    echo "PWD:"
                    pwd

                    echo "PATH:"
                    echo $PATH

                    echo "JAVA:"
                    java -version || true

                    echo "Check hdfs:"
                    command -v hdfs || true

                    echo "Check sqoop:"
                    command -v sqoop || true

                    echo "Check spark-submit:"
                    command -v spark-submit || true
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
                    bash -lc '
                    export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                    export PATH=$JAVA_HOME/bin:$PATH

                    [ -f /etc/profile ] && . /etc/profile || true
                    [ -f ~/.bash_profile ] && . ~/.bash_profile || true
                    [ -f ~/.bashrc ] && . ~/.bashrc || true

                    HDFS_CMD=$(command -v hdfs)
                    SQOOP_CMD=$(command -v sqoop)

                    [ -z "$HDFS_CMD" ] && echo "hdfs not found in Jenkins environment" && exit 1
                    [ -z "$SQOOP_CMD" ] && echo "sqoop not found in Jenkins environment" && exit 1

                    "$HDFS_CMD" dfs -put -f csv_files/full_load/races_initial.csv /tmp/anas_proj2/bronze/races/full/
                    "$HDFS_CMD" dfs -put -f csv_files/full_load/results_initial.csv /tmp/anas_proj2/bronze/results/full/

                    "$SQOOP_CMD" import --connect jdbc:postgresql://13.42.152.118:5432/testdb --username admin --password admin123 --driver org.postgresql.Driver --table anas.drivers_full_v --target-dir /tmp/anas_proj2/bronze/drivers/full --delete-target-dir -m 1

                    "$SQOOP_CMD" import --connect jdbc:postgresql://13.42.152.118:5432/testdb --username admin --password admin123 --driver org.postgresql.Driver --table anas.constructors_full_v --target-dir /tmp/anas_proj2/bronze/constructors/full --delete-target-dir -m 1
                    '
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
                        bash -lc '
                        export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                        export PATH=$JAVA_HOME/bin:$PATH
                        export SPARK_LOCAL_IP=127.0.0.1

                        [ -f /etc/profile ] && . /etc/profile || true
                        [ -f ~/.bash_profile ] && . ~/.bash_profile || true
                        [ -f ~/.bashrc ] && . ~/.bashrc || true

                        SPARK_CMD=$(command -v spark-submit)
                        [ -z "$SPARK_CMD" ] && echo "spark-submit not found in Jenkins environment" && exit 1

                        "$SPARK_CMD" bronze_to_silver_full.py
                        '
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
                    bash -lc '
                    export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                    export PATH=$JAVA_HOME/bin:$PATH

                    [ -f /etc/profile ] && . /etc/profile || true
                    [ -f ~/.bash_profile ] && . ~/.bash_profile || true
                    [ -f ~/.bashrc ] && . ~/.bashrc || true

                    HDFS_CMD=$(command -v hdfs)
                    [ -z "$HDFS_CMD" ] && echo "hdfs not found in Jenkins environment" && exit 1

                    "$HDFS_CMD" dfs -put -f csv_files/incremental_load/races_incremental.csv /tmp/anas_proj2/bronze/races/incremental/
                    "$HDFS_CMD" dfs -put -f csv_files/incremental_load/results_incremental.csv /tmp/anas_proj2/bronze/results/incremental/
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
                    bash -lc '
                    export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                    export PATH=$JAVA_HOME/bin:$PATH

                    [ -f /etc/profile ] && . /etc/profile || true
                    [ -f ~/.bash_profile ] && . ~/.bash_profile || true
                    [ -f ~/.bashrc ] && . ~/.bashrc || true

                    HDFS_CMD=$(command -v hdfs)
                    [ -z "$HDFS_CMD" ] && echo "hdfs not found in Jenkins environment" && exit 1

                    "$HDFS_CMD" dfs -test -e /tmp/anas_proj2/silver/races || { echo "Base silver races not found. Run FULL load first."; exit 1; }
                    "$HDFS_CMD" dfs -test -e /tmp/anas_proj2/silver/results || { echo "Base silver results not found. Run FULL load first."; exit 1; }
                    '
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
                        bash -lc '
                        export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                        export PATH=$JAVA_HOME/bin:$PATH
                        export SPARK_LOCAL_IP=127.0.0.1

                        [ -f /etc/profile ] && . /etc/profile || true
                        [ -f ~/.bash_profile ] && . ~/.bash_profile || true
                        [ -f ~/.bashrc ] && . ~/.bashrc || true

                        SPARK_CMD=$(command -v spark-submit)
                        [ -z "$SPARK_CMD" ] && echo "spark-submit not found in Jenkins environment" && exit 1

                        "$SPARK_CMD" incremental_silver_merge.py
                        '
                    '''
                }
            }
        }

        stage('Refresh Gold') {
            steps {
                dir('code/code_final') {
                    sh '''
                        bash -lc '
                        export JAVA_HOME=/usr/java/jdk1.8.0_232-cloudera
                        export PATH=$JAVA_HOME/bin:$PATH
                        export SPARK_LOCAL_IP=127.0.0.1

                        [ -f /etc/profile ] && . /etc/profile || true
                        [ -f ~/.bash_profile ] && . ~/.bash_profile || true
                        [ -f ~/.bashrc ] && . ~/.bashrc || true

                        SPARK_CMD=$(command -v spark-submit)
                        [ -z "$SPARK_CMD" ] && echo "spark-submit not found in Jenkins environment" && exit 1

                        "$SPARK_CMD" silver_to_gold.py
                        '
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