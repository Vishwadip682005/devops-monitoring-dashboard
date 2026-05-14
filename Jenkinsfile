pipeline {
    agent any

    tools {
        maven 'MAVEN_HOME'
        jdk 'JDK17'
    }

    environment {
        IMAGE_NAME = "devops-monitoring-dashboard"
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo 'Pulling code from GitHub...'
                git branch: 'main',
                    url: 'https://github.com/Vishwadip682005/devops-monitoring-dashboard.git'
            }
        }

        stage('Build Project') {
            steps {
                echo 'Building Maven project...'
                sh 'mvn clean package'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Running tests...'
                sh 'mvn test || true'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Run Docker Container') {
            steps {
                echo 'Stopping old container if exists...'
                sh 'docker rm -f monitoring-container || true'

                echo 'Running new container...'
                sh 'docker run -d -p 9090:9090 --name monitoring-container $IMAGE_NAME'
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully 🎉'
        }

        failure {
            echo 'Pipeline failed ❌ Check logs'
        }
    }
}
