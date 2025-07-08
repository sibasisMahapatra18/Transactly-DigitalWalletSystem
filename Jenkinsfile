pipeline {
    agent {
        docker {
            image 'openjdk:23-ea-jdk' // Official JDK 23 early-access image
        }
    }

    environment {
        JAR_NAME = 'digital-wallet.jar' // Change this if your final .jar has a different name
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Install Maven') {
            steps {
                sh '''
                    apt-get update
                    apt-get install -y maven
                    mvn --version
                '''
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run') {
            steps {
                sh '''
                    echo "Starting the app..."
                    java -jar target/*.jar &
                    sleep 10
                    curl -f http://localhost:8080 || echo "App failed to start or is not reachable"
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
    }
}
