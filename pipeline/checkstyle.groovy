
def runTest(String targetBranch, String configuration, String application){  
    
    def label = 'jenkins-nodejs'

    podTemplate(label: label) {
        node(label) {
            container('nodejs'){
                
                // Delete workspace just in case we're on the same node (unstash doesn't overwrite)
                deleteDir()
                unstash 'workspace'
                
                try {
                    sh 'pipeline/checkstyle.sh'
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
    return "checkstyle"
}

return this;