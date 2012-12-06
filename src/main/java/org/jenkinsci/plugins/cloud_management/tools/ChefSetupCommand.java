package org.jenkinsci.plugins.cloud_management.tools;


/**
 * 
 * @author Satoshi Akama
 */
public class ChefSetupCommand extends AbstractSetupCommand {
    
    protected String hostname;
    protected String region;
    protected String availability_zone;
    protected String ami;
    protected String instance_type;
    protected Boolean require_sudo;

    public ChefSetupCommand() {
    }

    @Override
    public boolean isCommandEnable(boolean useEc2, boolean useElb) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean launchEc2Instance(String ami) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean stopEc2Instance(String ami_id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean terminateEc2Instance(String ami_id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean associateElasticIp(String ip) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean releaseElasticIp(String ip) {
        // TODO Auto-generated method stub
        return false;
    }

}
