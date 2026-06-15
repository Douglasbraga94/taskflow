pipeline{
    agent any


    stages{
        stage('Checkout'){
            steps{
                echo 'Código baixando do GIT'
            }
        }

        stage('Build & Test'){
            steps{
                sh './mvnw -B clean test'
            }
        }
    }

    post{
        success{
            echo 'Pipeline passou! testes verdes'
        }
        failure{
            echo 'Pipeline falhou. Confira os testes'
        }
    }
}