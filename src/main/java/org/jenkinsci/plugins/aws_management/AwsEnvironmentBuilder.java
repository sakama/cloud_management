package org.jenkinsci.plugins.aws_management;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import hudson.Launcher;
import hudson.Extension;
import hudson.util.FormValidation;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;

/**
 * 
 * @author Satoshi Akama
 */
public class AwsEnvironmentBuilder extends Builder {

    private final String hostname;
    private final String tool;
    private final Boolean requiresudo;

    @DataBoundConstructor
    public AwsEnvironmentBuilder(String hostname, String tool, Boolean requiresudo) {
        this.hostname = hostname;
        this.tool = tool;
        this.requiresudo = requiresudo;
    }

    public String getHostname() {
        return hostname;
    }
    
    public String getTool() {
        return tool;
    }
    
    public Boolean getRequiresudo() {
        return requiresudo;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {
        return true;
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        public FormValidation doCheckHostname(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error("Please set a name");
            if (value.length() < 4)
                return FormValidation.warning("Isn't the name too short?");
            return FormValidation.ok();
        }
        
        private List<CloudEnv> envs;

        public List<CloudEnv> getEnvs() {
            if (envs == null) {
                envs = new ArrayList<CloudEnv>();
            }
            return envs;
        }

        public void setEnvs(List<CloudEnv> envs) {
            this.envs = envs;
        }
        
        @SuppressWarnings("rawtypes")
        @Override
        public boolean isApplicable(Class jobType) {
            return true;
        }

        public String getDisplayName() {
            return "AWS Management";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            save();
            return super.configure(req, formData);
        }
    }

    public static class CloudEnv {
        private String envName;
        private String amiId;
        private String flavor;
        private String ip;
        private String region;
        private String role;
        private String securityGroup;
        private String zone;

        public String getEnvName() {
            return envName;
        }
        
        public String getAmiId() {
            return amiId;
        }
        
        public String getFlavor() {
            return flavor;
        }
        
        public String getIp() {
            return ip;
        }
        
        public String getRegion() {
            return region;
        }
        
        public String getRole() {
            return role;
        }
        
        public String getSecurityGroup() {
            return securityGroup;
        }
        
        public String getZone() {
            return zone;
        }

        @DataBoundConstructor
        public CloudEnv(String envName, String amiId, String flavor, String ip, String region, String role, String securityGroup, String zone) {
            this.envName = envName;
            this.envName = amiId;
            this.envName = flavor;
            this.envName = ip;
            this.envName = region;
            this.envName = role;
            this.envName = securityGroup;
            this.envName = zone;
        }
    }

}
