pipeline {
  agent any
  stages {
    stage('Checkout Code') {
      steps {
        git(url: 'git@github.com:jucron/pizza-auth-server.git', branch: 'master', credentialsId: 'jenkins')
      }
    }

  }
}