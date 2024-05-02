pipeline {
  agent any
  stages {
    stage('Checkout Code') {
      steps {
        script {
            git branch: 'master',
                credentialsId: 'jenkins',
                url: 'git@github.com:jucron/pizza-auth-server.git'
        }
      }
    stage('Permissions gradlew') {
      steps {
        sh 'chmod +x gradlew'
      }
    }

    stage('Run gradle builder') {
      steps {
        sh './gradlew clean build --stacktrace --no-cache'
      }
    }

  }
}
