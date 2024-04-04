// podTemplate(containers: [
//   containerTemplate(name: 'maven', image: 'maven:3.6.3-adoptopenjdk-11-openj9', ttyEnabled: true, command: 'cat'),
//   containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true)
//   ],
//   volumes: [
//       hostPathVolume(hostPath: '/var/run/docker.sock',   mountPath: '/var/run/docker.sock'),
//       hostPathVolume(hostPath: '/home/root/repository',  mountPath: '/root/.m2/repository'),
//   ])
// {
//   properties([disableConcurrentBuilds()])
//   node(POD_LABEL) {

pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
        timestamps()
    }
    environment {
        imagename = "kevalnagda/flaskapp"
        registryCredential = 'kevalnagda'
        dockerImage = ''
    }
//     agent {
//         kubernetes {
//             label 'jx-maven-lib'
//             yaml """
// apiVersion: v1
// kind: Pod
// spec:
//   containers:
//   - name: maven
//     image: maven:3.6.3-adoptopenjdk-11-openj9
//     command: ['cat']
//     tty: true
//   - name: docker
//     image: docker
//     command: ['cat']
//     tty: true
// """
//         }
//     }


    stages {
 //To checkout based on the configured credentials in the current Jenkins Job
        stage('Checkout SCM') {
            steps {
                echo " ============ start checkout scm ================"
            	checkout scm
            }
        }

       stage('Package') {
                               agent {
                                   kubernetes {
                                       label 'jxmavenlib-jdk11_build'
                                       containerTemplate {
                                           name 'maven11'
                                           image 'maven:3.6.3-adoptopenjdk-11-openj9'
                                           ttyEnabled true
                                           command 'cat'
                                       }
                                   }
                               }


            steps {

                echo "=========================================================="
                echo "${WORKSPACE}"
                container('maven11') {
                sh "du -a"
                sh "mvn clean package -f /home/jenkins/agent/workspace/my-456/demo-cicd-k8s/pom.xml"

                }
            }
       }
        
//         stage('Build image') {
//             steps {
//                 container('docker') {
// //            sh "docker system prune -f"
//                     sh " docker info "
//                     sh " docker login -u admin -p 123 192.168.1.39:5000 "
// //sh "docker pull 192.168.1.39:5000/demo-cicd-k8s-app:1.0"
// //                    dockerImage = docker.build("192.168.1.39:5000/demo-cicd-k8s-app:1.0","/home/jenkins/agent/workspace/my-345/demo-cicd-k8s")
// //            sh " docker login -u admin -p 123 192.168.1.39:5000 "
// //                    dockerImage.push()
//                 }
//             }
//         }

//         stage('Deploy') {
//         steps {
//             script{
//                 withKubeConfig(credentialsId: 'MyKubeConfig', serverUrl: 'https://192.168.49.2:8443') {
//                     echo "========================   ============================="
//                     sh ' du -a '
//                     sh 'cat demo-cicd-k8s/demo-cicd-k8s.yml'
//                     sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl"'
//                     sh 'chmod u+x ./kubectl'
//                     sh './kubectl get nodes'
//                     sh './kubectl get pods -A'
//                     sh './kubectl apply -f ./demo-cicd-k8s/demo-cicd-k8s.yml'
//                 }
//             }
//         }
//         }
    }
    

 }
//}
