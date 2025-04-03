pipeline {
    agent any
    environment {
        // Если Jenkins в Docker на Windows-хосте
        ALLURE_PATH = isUnix() ? "/var/jenkins_scoop/apps/allure/2.32.2/plugins/behaviors-plugin/static/index.js"
                              : "C:\\Users\\user\\scoop\\apps\\allure\\2.32.2\\plugins\\behaviors-plugin\\static\\index.js"
    }
    stages {
        stage('Test') {
            steps {
                script {
                    echo "Allure path: ${env.ALLURE_PATH}"
                    // Если нужно, можно заменить \ на / для кросс-платформенности
                    def normalizedPath = env.ALLURE_PATH.replace('\\', '/')
                    sh "cat ${normalizedPath}"  // для Linux/Docker
                }
            }
        }
    }
}