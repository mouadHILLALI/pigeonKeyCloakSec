pipeline {
    agent any 

    tools {
        dockerTool 'docker-latest'
        maven 'maven'
        jdk 'jdk_21'
    }
    environment {
        DOCKER_IMAGE = 'pigeonrace-security-web'
    }
    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "DEBUG: Starting Checkout stage..."
                    deleteDir()
                }
                script {
                    echo "DEBUG: Fetching the repository from Git..."
                    git branch: 'master', url: 'https://github.com/mouadHILLALI/pigeonKeyCloakSec'
                }
                script {
                    echo "DEBUG: Checkout stage completed successfully!"
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    def javaHome = '/opt/java/openjdk'
                    
                    withEnv(["JAVA_HOME=${javaHome}", "PATH+JAVA=${javaHome}/bin"]) {
                        sh '''
                            echo "JAVA_HOME is: $JAVA_HOME"
                            echo "Checking JAVA_HOME contents:"
                            ls -la $JAVA_HOME
                            echo "Java version:"
                            java -version
                            echo "Maven version:"
                            mvn -version
                        '''
                        
                        echo "DEBUG: Starting Build stage..."
                        sh 'mvn clean package -DskipTests'
                        echo "DEBUG: Build stage completed successfully!"
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    def javaHome = '/opt/java/openjdk'
                    withEnv(["JAVA_HOME=${javaHome}", "PATH+JAVA=${javaHome}/bin"]) {
                        echo 'DEBUG: Running tests...'
                        sh 'mvn test'
                    }
                }
            }
        }
            
    }

    post {
        always {
            echo 'DEBUG: Pipeline completed successfully'
        }
        failure {
            echo 'DEBUG: Pipeline failed'
        }
    }
}
