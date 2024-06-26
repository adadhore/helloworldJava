trigger:
- main

pr:
- main

pool:
  vmImage: ubuntu-latest

variables:
  PRISMA_API_URL: https://api0.prismacloud.io

jobs:
- job: Prisma_Cloud_Job
  displayName: Prisma Cloud Job
  steps:
  - task: UsePythonVersion@0
    inputs:
      versionSpec: 3.8
    displayName: 'Use Python 3.8'
  - script: pip install checkov
    displayName: Install Checkov
  - script: checkov -d . --use-enforcement-rules --bc-api-key $(71a8ea9e-bc22-4802-ab77-4aa4b637ba29)::$(Q5c/oh/FSLbbVQ1opBtWDp05b4Y=) --repo-id adadhore/terragoat --branch main -o cli -o junitxml --output-file-path console,CheckovReport.xml
    workingDirectory: $(System.DefaultWorkingDirectory)
    displayName: Run Checkov
  - task: PublishTestResults@2
    inputs:
      testResultsFormat: 'JUnit'
      testResultsFiles: '**/CheckovReport.xml'
      testRunTitle: PrismaCloudTests
    displayName: Publish Test Results
    condition: always()