def run(String targetBranch, String configuration, String application){  
    
    def type = 'deploy'
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
                                    
                    dir('charts'){
                        sh '''
                        export POD_NAMESPACE=$(cat /var/run/secrets/kubernetes.io/serviceaccount/namespace)
                        A=$(env | grep DOCKER_REGISTRY_SERVICE_HOST)
                        export REG_IP=$(echo $A | awk -F'=' '{print $2}')

                        helm init --client-only
                        helm install ${app} --name $app --set image.repository=${REG_IP}/${app} --set image.tag='latest' --namespace ${POD_NAMESPACE}-staging

                        '''
                    }

                    stash name: 'workspace'
                }   

                
                try {
                    //sh 'pipeline/deploy.sh'
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

