// podTemplate(containers: [
//   containerTemplate(name: 'maven', image: 'maven:3.6.3-adoptopenjdk-11-openj9', ttyEnabled: true, command: 'cat'),
//   containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true)

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

    volumeMounts:
            - name: kubectl-binary
              mountPath: /usr/local/bin/kubectl
              readOnly: true
            - name: kubectl-config
              mountPath: /home/aav/.kube/config
              readOnly: true


  volumes:
      - name: docker-sock
        hostPath:
          path: /var/run/docker.sock
      - name: kubectl-binary
              hostPath:
                path: /usr/local/bin/kubectl
            - name: kubectl-config
              hostPath:
                path: /home/aav/.kube/config
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
//            sh "docker system prune -f"



        stage('Deploy') {
            steps {
                container('kubectl') {
                //sh 'kubectl get clusterrole'
                //sh 'kubectl version'
                //sh 'kubectl delete -f ./demo-cicd-k8s.yml'
               sh 'kubectl apply  -f ./demo-cicd-k8s.yml'
               // script{
//                 withKubeConfig(credentialsId: 'MyKubeConfig', serverUrl: 'https://192.168.49.2:8443') {
//                     echo "========================   ============================="
//                     sh ' du -a '
//                 //    sh 'cat demo-cicd-k8s/demo-cicd-k8s.yml'
//             //        sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl"'
//               //      sh 'chmod u+x ./kubectl'
//                     sh './kubectl get nodes'
//                     sh './kubectl get pods -A'
//                     sh './kubectl apply -f ./demo-cicd-k8s/demo-cicd-k8s.yml'
//                 }
                }
            }
        }
    }
    

}

