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
                sh 'docker build -t bangsil-user:latest /var/jenkins_home/workspace/bangsil_user'
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
                sh 'docker ps -f name=bangsil-user -q \
                | xargs --no-run-if-empty docker container stop'

                sh 'docker container ls -a -f name=bangsil-user -q \
        | xargs -r docker container rm'

                sh 'docker images -f dangling=true && \
                docker rmi $(docker images -f dangling=true -q)'

                sh 'docker run -d --name bangsil-user \
                -p 8081:8081 \
                -v /etc/localtime:/etc/localtime:ro \
                bangsil-user:latest'
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
