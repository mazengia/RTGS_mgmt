pipeline{
    environment{
    DATE=new Date().format('yy.M')
    TAG = "${DATE}.${BUILD_NUMBER}"
}

tools{
    maven 'maven'
}

agent any
stages{
    stage("Build"){
        steps{
            sh "mvn -version"
            sh "mvn clean install -DskipTests"
        }
    }

     stage("Build Docker"){
        steps{
           script{
               docker.build("10.1.12.73:5000/rtgs-api:${TAG}")
           }
        }
    }
    stage("Push Docker Image to Local Registry"){
        steps{
           script{
               docker.withRegistry("https://10.1.12.73:5000"){
                   docker.image("10.1.12.73:5000/rtgs-api:${TAG}").push()
                   docker.image("10.1.12.73:5000/rtgs-api:${TAG}").push("latest")
               }
           }
        }
    }
    stage("Deliver for development"){
        when{
            branch "develop"
        }
                 steps{
                    sshagent(['ebdev']) {
                    sh 'ssh -o StrictHostKeyChecking=no -l  ebdevuat 10.1.22.72      "docker stop rtgs-api | true;   docker rm rtgs-api | true;    docker run -p 7008:8080 -e "SPRING_PROFILES_ACTIVE=develop" -d --name rtgs-api 10.1.12.73:5000/rtgs-api:${TAG}"'
                }
        }
    }
      stage("Deploy for production"){
        when{
            branch "main"
        }
           steps{
                    sshagent(['enat-remedy-production']) {
                    sh 'ssh -o StrictHostKeyChecking=no -l  administrator 10.1.12.70      "docker stop rtgs-api | true;     docker rm rtgs-api | true;  docker run -p  7008:8080 -e "SPRING_PROFILES_ACTIVE=prod" -d --name rtgs-api 10.1.12.73:5000/rtgs-api:${TAG}"'
                }
            }
    }
}

post{
    always{
        cleanWs()
    }
}
}
