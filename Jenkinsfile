pipeline {
    agent any

    tools {
        jdk 'Corretto-22'
    }

    stages {

        stage('Test Java') {
            steps {
                sh 'java -version'
            }
        }

        stage('Build') {
            steps {
                script {
                    sh './mvnw clean package'
                }
            }
        }

        stage('Deploy') {
            steps {
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'Laplab',
                            transfers: [
                                sshTransfer(
                                    sourceFiles: 'target/*.jar',
                                    removePrefix: 'target',
                                    remoteDirectory: 'artifacts/movies'
                                ),
                                sshTransfer(
                                    sourceFiles: 'docker/*',
                                    removePrefix: 'docker',
                                    remoteDirectory: 'artifacts/movies',
                                    execCommand: """
                                        cd artifacts/movies/ &&
                                        date >> historial.txt &&
                                        docker build -t movies-app:${env.GIT_COMMIT.substring(0,7)} -t movies-app:latest .
                                    """
                                )
                            ],
                            usePromotionTimestamp: false,
                            verbose: true
                        )
                    ]
                )
            }
        }
    }

    post {
        always {
            script {
                sh 'printenv | grep GIT'
            }
        }
    }
}
