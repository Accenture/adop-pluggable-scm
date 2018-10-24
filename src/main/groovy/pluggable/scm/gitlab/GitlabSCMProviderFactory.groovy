package pluggable.scm.gitlab

import pluggable.scm.SCMProvider
import pluggable.scm.SCMProviderFactory
import pluggable.scm.SCMProviderInfo

@SCMProviderInfo(type = "gitlab")
class GitlabSCMProviderFactory implements SCMProviderFactory {

    /**
     * A factory method which return an SCM Provider instantiated with the
     * the provided properties.
     * @param scmProviderProperties - properties for the SCM provider.
     * @return SCMProvider configured from the provided SCM properties.
     */
    public SCMProvider create(Properties scmProviderProperties) {
        List<String> requiredProperties = Arrays.asList("scm.protocol", "scm.host", "scm.port", "gitlab.endpoint", "gitlab.protocol", "gitlab.port")
        requiredProperties.each { it ->
            validateProperties(it, scmProviderProperties.getProperty(it) //verify that all required values are present
            )
        }

        String scmHost = scmProviderProperties.getProperty("scm.host")
        GitlabSCMProtocol scmProtocol = GitlabSCMProtocol.valueOf(scmProviderProperties.getProperty("scm.protocol").toUpperCase())
        int scmPort = Integer.parseInt(scmProviderProperties.getProperty("scm.port"))
        String scmContext = scmProviderProperties.getProperty("scm.context")
        String gitlabContext = scmProviderProperties.getProperty("gitlab.context")
        String gitlabEndpoint = scmProviderProperties.getProperty("gitlab.endpoint")
        GitlabSCMProtocol gitlabProtocol = GitlabSCMProtocol.valueOf(scmProviderProperties.getProperty("gitlab.protocol").toUpperCase())
        int gitlabPort = Integer.parseInt(scmProviderProperties.getProperty("gitlab.port"))

        return new GitlabSCMProvider(scmPort, scmProtocol, scmHost, scmContext, gitlabProtocol, gitlabEndpoint, gitlabPort, gitlabContext)
    }

    /**
     * Verify that a value is neither null or empty
     * @param key
     * @param value
     * @return nothing
     * @throw IllegalArgumentException
     */
    private void validateProperties(String key, String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException("Please make sure that" + key + " is set in the relevant properties file and that it has a valid value.")
        }
    }
}
