package org.jenkinsci.plugins.aws_management.tools;


/**
 * 
 * @author Satoshi Akama
 */
public abstract class AbstractSetupCommand {
    
    protected String hostname;
    protected String region;
    protected String availability_zone;
    protected String ami;
    protected String instance_type;
    protected Boolean require_sudo;

    public AbstractSetupCommand() {
    }
    
    /**
     * 各ツールでAWSのサービスを使うことが可能かどうか調べる
     */
    public abstract boolean isCommandEnable(boolean useEc2, boolean useElb);

    /**
     * EC2インスタンスを立ち上げる
     */
    public abstract boolean launchEc2Instance(String ami);
    
    /**
     * EC2インスタンスを停止する
     */
    public abstract boolean stopEc2Instance(String ami_id);
    
    /**
     * EC2インスタンスをterminateする
     */
    public abstract boolean terminateEc2Instance(String ami_id);

    /**
     * Elastic IPの割り当てを行う
     */
    public abstract boolean associateElasticIp(String ip);

    /**
     * Elastic IPのReleaseを行う
     */
    public abstract boolean releaseElasticIp(String ip);
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public void setAvailabilityZone(String zone) {
        this.availability_zone = zone;
    }

    /**
     * ビルド終了時の処理を行う
     */
    public boolean disconnect() {
        return true;
    }
}
