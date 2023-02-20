pipeline {
    agent none
    options { skipDefaultCheckout(false) }
    stages {
        stage('git pull') {
            agent any
            steps {
                checkout scm
            }
        }
        stage('build gradle') {
            agent any
            steps {
                sh 'chmod +x gradlew'
                sh  './gradlew clean build'
                sh 'ls -al ./build'
            }
            post {
                success {
                    echo 'gradle build success'
                }

                failure {
                    echo 'gradle build failed'
                }
            }
        }
        stage('Docker Build') {
            agent any
            steps {
                sh 'docker build -t user-service:latest /var/jenkins_home/workspace/BE_USER_REPO'
            }
            post {
                success {
                    sh 'echo "Bulid Docker Image Success"'
                }

                failure {
                    sh 'echo "Bulid Docker Image Fail"'
                }
            }
        }
        stage('Deploy') {
            agent any
            steps {
                sh 'docker ps -f name=user-service -q \
                | xargs --no-run-if-empty docker container stop'

                sh 'docker container ls -a -f name=user-service -q \
        | xargs -r docker container rm'

                sh 'docker images -f dangling=true && \
                docker rmi $(docker images -f dangling=true -q)'

                sh 'docker run -d --name user-service \
                -p 8000:8000 \
                -v /etc/localtime:/etc/localtime:ro \
                user-service:latest'
            }

            post {
                success {
                    echo 'success'
                }

                failure {
                    echo 'failed'
                }
            }
        }
    }
}
