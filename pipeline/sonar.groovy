
def runTest(String targetBranch, String configuration){  
    
    def label = 'jenkins-nodejs'

    podTemplate(label: label) {
        node(label) {
            container('nodejs'){

                // Delete workspace just in case we're on the same node (unstash doesn't overwrite)
                deleteDir()
                unstash 'workspace'

                try {
                    sh 'pipeline/sonar.sh'
                } catch (error) {
                    echo "FAILURE"
                    echo error.message
                    throw error
                }
            }
        }
    }
}

def getName(){
    return "sonar"
}

return this;