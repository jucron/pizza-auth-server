pipeline {
  agent any
  stages {
    stage('Checkout Code') {
      steps {
        git(url: 'git@github.com:jucron/pizza-auth-server.git', branch: 'master', credentialsId: 'jenkins')
      }
    }

    stage('Permissions gradlew') {
      steps {
        sh 'chmod +x gradlew'
      }
    }

    stage('Run gradle builder') {
      steps {
        sh './gradlew clean build --stacktrace'
      }
    }

  }
}