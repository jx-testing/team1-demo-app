def runTest(String targetBranch, String configuration, String application){  
    
    //def label = 'jenkins-nodejs'
    def label = 'jenkins-meteor'

    podTemplate(label: label) {
        node(label) {
            //container('nodejs'){
            container('meteor'){

                // Delete workspace just in case we're on the same node (unstash doesn't overwrite)
                deleteDir()
                unstash 'workspace'
                
                try {
                    sh 'pipeline/unit.sh'
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
    return "unit"
}

return this;
