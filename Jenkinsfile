pipeline {
    agent any
    tools {
        maven 'maven 3.9.9'
        jdk 'java 21'
    }
    environment {
        DOCKER_IMAGE = 'pigeonrace-security-web-1'
        DOCKER_CREDENTIALS = 'totogang'
    }
    stages {
        stage('Checkout') {
            steps {
                echo "Cloning repository..."
                checkout([$class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/mouadHILLALI/pigeonKeyCloakSec']]
                ])
            }
        }
        stage('Build') {
            steps {
                echo "Building the application..."
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                echo "Running tests..."
                sh 'mvn test'
            }
        }
        stage('Verify Docker') {
            steps {
                echo "Checking Docker version..."
                sh 'docker --version'
            }
        }
        stage('Deliver') {
            steps {
                echo "Building Docker image..."
                sh '''
                    docker build -t $DOCKER_IMAGE .
                '''
                echo "Pushing Docker image to Docker Hub..."
                withDockerRegistry([credentialsId: DOCKER_CREDENTIALS, url: "https://index.docker.io/v1/"]) {
                    sh '''
                        docker push $DOCKER_IMAGE
                    '''
                }
            }
        }
    }
    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
