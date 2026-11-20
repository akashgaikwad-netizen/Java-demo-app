@Library('my-shared-library') _

pipeline {
    agent any

    parameters {
        choice(
            name: 'action',
            choices: 'create\ndelete',
            description: 'Choose create or delete'
        )

        string(
            name: 'ImageName',
            defaultValue: 'Java-demo-app',
            description: 'Name of Docker image'
        )

        string(
            name: 'ImageTag',
            defaultValue: 'v1',
            description: 'Tag of Docker image'
        )

        string(
            name: 'DockerHubUser',
            defaultValue: 'YOUR_DOCKERHUB_USERNAME',
            description: 'Docker Hub username'
        )
    }

    stages {

        stage('Git Checkout') {
            when {
                expression { params.action == 'create' }
            }
            steps {
                gitCheckout(
                    branch: 'main',
                    url: 'https://github.com/akashgaikwad-netizen/Java-demo-app.git'
                )
            }
        }

        stage('Unit Test Maven') {
            when {
                expression { params.action == 'create' }
            }
            steps {
                script {
                    mvnTest()
                }
            }
        }

        stage('Integration Test Maven') {
            when {
                expression { params.action == 'create' }
            }
            steps {
                script {
                    mvnIntegrationTest()
                }
            }
        }

        stage('Static Code Analysis: SonarQube') {
            when {
                expression { params.action == 'create' }
            }
            steps {
                script {
                    def SonarQubecredentialsId = 'sonarqube-api'
                    statiCodeAnalysis(SonarQubecredentialsId)
                }
            }
        }

        stage('Quality Gate Status Check: SonarQube') {
            when {
                expression { params.action == 'create' }
            }
            steps {
                script {
                    def SonarQubecredentialsId = 'sonarqube-api'
                    QualityGateStatus(SonarQubecredentialsId)
                }
            }
        }

        stage('Maven Build') {
            when {
                expression { params.action == 'create' }
            }
            steps {
                script {
                    mvnBuild()
                }
            }
        }

        stage('Docker Image Build') {
            when {
                expression { params.action == 'create' }
            }
            steps {
                script {
                    dockerBuild(
                        "${params.ImageName}",
                        "${params.ImageTag}",
                        "${params.DockerHubUser}"
                    )
                }
            }
        }

        stage('Docker Image Scan: Trivy') {
            when {
                expression { params.action == 'create' }
            }
            steps {
                script {
                    dockerImageScan(
                        "${params.ImageName}",
                        "${params.ImageTag}",
                        "${params.DockerHubUser}"
                    )
                }
            }
        }

        stage('Docker Image Push: DockerHub') {
            when {
                expression { params.action == 'create' }
            }
            steps {
                script {
                    dockerImagePush(
                        "${params.ImageName}",
                        "${params.ImageTag}",
                        "${params.DockerHubUser}"
                    )
                }
            }
        }

        stage('Docker Image Cleanup') {
            when {
                expression { params.action == 'create' }
            }
            steps {
                script {
                    dockerImageCleanup(
                        "${params.ImageName}",
                        "${params.ImageTag}",
                        "${params.DockerHubUser}"
                    )
                }
            }
        }
    }
}
