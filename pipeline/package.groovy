def run(String targetBranch, String configuration){  
    
    def type = 'packaging'
    def label = 'jenkins-nodejs'

    podTemplate(label: label) {
        node(label) {
            container('nodejs'){

                // Delete workspace just in case we're on the same node (unstash doesn't overwrite)
                deleteDir()
                unstash 'workspace'
                
                try {
                    sh 'pipeline/package.sh'
                } catch (error) {
                    echo "FAILURE: ${type} failed"
                    echo error.message
                    throw error
                }
            }
        }
    }
}
return this;
