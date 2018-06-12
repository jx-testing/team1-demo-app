def runTest(String targetBranch, String configuration, String application){  
    
    def label = 'jenkins-nodejs'

    podTemplate(label: label) {
        node(label) {
            container('nodejs'){

                // Delete workspace just in case we're on the same node (unstash doesn't overwrite)

                deleteDir()

                withEnv([
                    "app=${application}",
                ]) {
                    unstash 'workspace'
                                    
                    
                    sh '''
                    export POD_NAMESPACE=$(cat /var/run/secrets/kubernetes.io/serviceaccount/namespace)
                    A=$(env | grep DOCKER_REGISTRY_SERVICE_HOST)
                    export REG_IP=$(echo $A | awk -F'=' '{print $2}')

                    echo "Assuming I've tested I'm now deleting the app"
                    helm init --client-only
                    helm delete ${app} --purge

                    '''
                    

                    stash name: 'workspace'
                }   

                try {
                    sh 'pipeline/component.sh'
                } catch (error) {
                    echo "FAILURE"
                    echo error.message
                    throw error
                }
            }
        }
    }
}
return this;
