pipeline {
    agent any

    tools {
        maven 'M3'
    }

    stages {
        stage('Build') {
            steps {
                script {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh 'mvn test'
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
