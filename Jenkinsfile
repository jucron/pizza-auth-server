pipeline {
  agent any
  stages {
    stage('Checkout Code') {
      steps {
        git(url: 'git@github.com:jucron/pizza-auth-server.git', branch: 'master', credentialsId: 'jenkins')
      }
    }

    stage('gradle') {
      steps {
        sh './gradlew clean build --stacktrace'
      }
    }

  }
}