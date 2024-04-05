
pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
        timestamps()
    }
    environment {
        imagename = "192.168.1.39:5000/demo-cicd-k8s-app:1.0"
        registryCredential = 'kevalnagda'
        dockerImage = ''
    }
    agent {
        kubernetes {
            label 'jx-maven-lib'
            yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: maven
    image: maven:3.6.3-adoptopenjdk-11-openj9
    command: ['cat']
    tty: true
  - name: docker
    image: docker
    command: ['cat']
    tty: true
    volumeMounts:
      - mountPath: /var/run/docker.sock
        name: docker-sock
  - name: kubectl
    image: lachlanevenson/k8s-kubectl:v1.14.0 # bitnami/kubectl:1.20.9 # use a version that matches your K8s version #bitnami/kubectl
    command: ['cat']
    tty: true
  volumes:
      - name: docker-sock
        hostPath:
          path: /var/run/docker.sock
"""
        }
    }


    stages {
        stage('Build maven project') {
            steps {
echo "======================= Build maven project ==================================="
                container('maven') {
                   sh "mvn clean package -f /home/jenkins/agent/workspace/my-456_main/pom.xml"
                }
            }
        }
        
        stage('Build & publish image') {
            steps {
echo "======================= Build & publish image ==================================="
                container('docker') {
                    script {
                        dockerImage = docker.build imagename
                        sh " docker login -u admin -p 123 192.168.1.39:5000 "
                        dockerImage.push()
                    }
                }
            }
        }


        stage('Deploy') {
            steps {
echo "======================= Deploy ==================================="
                container('kubectl') {
                    //sh 'kubectl get clusterrole'
                    sh 'kubectl version'
                    sh 'kubectl delete -f ./demo-cicd-k8s.yml'
                    sh 'kubectl apply  -f ./demo-cicd-k8s.yml'
                }
            }
        }
    }
    

}

