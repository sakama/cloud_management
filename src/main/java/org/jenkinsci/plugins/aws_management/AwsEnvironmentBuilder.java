package org.jenkinsci.plugins.aws_management;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    private final String tool;
    private final String sshCmd;
    private final Boolean requiresudo;
    private final String awsKeyFile;
    private final List<CloudEnv> envs;

    /** Logger. */
    private static final Logger LOGGER = Logger.getLogger(AwsEnvironmentBuilder.class.getName());

    @DataBoundConstructor
    public AwsEnvironmentBuilder(String tool, String sshCmd, Boolean requiresudo, String awsKeyFile, List<CloudEnv> envs) {
        this.tool = tool;
        this.sshCmd = sshCmd;
        this.requiresudo = requiresudo;
        this.awsKeyFile = awsKeyFile;
        this.envs = envs;
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

    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

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
            req.bindJSON(this, formData);
            save();
            return super.configure(req, formData);
        }

        public FormValidation doCheckSshCmd(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0) {
                return FormValidation.error("SSHコマンドを入力してください。");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckAwsKeyfile(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0) {
                return FormValidation.error("AWSの秘密鍵のパスを入力してください。");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckRegion(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0) {
                return FormValidation.error("Regionを入力してください。");
            }
            Pattern pattern = Pattern.compile("^[a-z]+?-[a-z]+?-[0-9]{1,2}$");
            Matcher m = pattern.matcher(value);
            if (!m.find()) {
                return FormValidation.error("Regionのフォーマットが違います。");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckAvailabilityZone(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0) {
                return FormValidation.error("Availability Zoneを入力してください。");
            }
            Pattern pattern = Pattern.compile("^[a-z]+?-[a-z]+?-[0-9]{1,2}[a-z]{1}$");
            Matcher m = pattern.matcher(value);
            if (!m.find()) {
                return FormValidation.error("Availability Zoneのフォーマットが違います。");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckAmiId(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0) {
                return FormValidation.error("AMI IDを入力してください。");
            }
            Pattern pattern = Pattern.compile("^ami-[a-z0-9]{8}$");
            Matcher m = pattern.matcher(value);
            if (!m.find()) {
                return FormValidation.error("AMI IDのフォーマットが違います。");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckFlavor(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0) {
                return FormValidation.error("インスタンスタイプを入力してください。");
            }
            Pattern pattern = Pattern.compile("^[a-z]{1,3}[0-9]{1}\\.[0-9]?(micro|small|medium|large|xlarge){1}$");
            Matcher m = pattern.matcher(value);
            if (!m.find()) {
                return FormValidation.error("インスタンスタイプのフォーマットが違います。");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckElasticIp(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0) {
                return FormValidation.error("Elastic IPを入力してください。");
            }
            Pattern pattern = Pattern
                    .compile("^(\\d|[01]?\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[01]?\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[01]?\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[01]?\\d\\d|2[0-4]\\d|25[0-5])");
            Matcher m = pattern.matcher(value);
            if (!m.find()) {
                return FormValidation.error("Elastic IPのフォーマットが違います。");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckSecurityGroup(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0) {
                return FormValidation.error("Security Groupを入力してください。");
            }
            Pattern pattern = Pattern.compile(".*");
            Matcher m = pattern.matcher(value);
            if (!m.find()) {
                return FormValidation.error("Security Groupのフォーマットが違います。");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckRole(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0) {
                return FormValidation.error("Roleを入力してください。");
            }
            Pattern pattern = Pattern.compile("^((?!,$).)*$");
            Matcher m = pattern.matcher(value);
            if (!m.find()) {
                return FormValidation.error("Roleのフォーマットが違います。");
            }
            return FormValidation.ok();
        }
    }

    public static class CloudEnv {
        private String envName;
        private String amiId;
        private String flavor;
        private String elasticIp;
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

        public String getElasticIp() {
            return elasticIp;
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
        public CloudEnv(String envName, String amiId, String flavor, String elasticIp, String region, String role, String securityGroup, String zone) {
            this.envName = envName;
            this.amiId = amiId;
            this.flavor = flavor;
            this.elasticIp = elasticIp;
            this.region = region;
            this.role = role;
            this.securityGroup = securityGroup;
            this.zone = zone;
        }
    }

    public String getTool() {
        return tool;
    }

    public String getSshCmd() {
        return sshCmd;
    }

    public Boolean getRequiresudo() {
        return requiresudo;
    }

    public String getAwsKeyFile() {
        return awsKeyFile;
    }
    
    public List<CloudEnv> getEnvs() {
        return envs;
    }

}
