package org.jenkinsci.plugins.aws_management;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.IOException;
import javax.servlet.ServletException;

import hudson.util.FormValidation;

public class AwsEnvironmentBuilderTest {

    @Test
    public void SSH接続コマンドに対するバリデーションチェック() throws IOException, ServletException {
        String[] corrects = { "ssh root@127.0.0.1" };
        for (int i = 0; i < corrects.length; i++) {
            FormValidation actual = AwsEnvironmentBuilder.DESCRIPTOR.doCheckSshCmd(corrects[i]);
            assertEquals(actual, FormValidation.ok());
        }

        FormValidation actual2 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckSshCmd("");
        assertEquals(actual2.toString(), FormValidation.error("SSHコマンドを入力してください。").toString());

        String[] inCorrects = {};
        for (int i = 0; i < inCorrects.length; i++) {
            FormValidation actual3 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckSshCmd(inCorrects[i]);
            assertEquals(actual3.toString(), FormValidation.error("SSHコマンドの形式が違います。").toString());
        }
    }
    
    @Test
    public void AWSの秘密鍵のパスに対するバリデーションチェック() throws IOException, ServletException {
        String[] corrects = { "~/.ssh/dev_auth.pem" ,"/root/.ssh/dev_auth.pem"};
        for (int i = 0; i < corrects.length; i++) {
            FormValidation actual = AwsEnvironmentBuilder.DESCRIPTOR.doCheckAwsKeyfile(corrects[i]);
            assertEquals(actual, FormValidation.ok());
        }

        FormValidation actual2 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckAwsKeyfile("");
        assertEquals(actual2.toString(), FormValidation.error("AWSの秘密鍵のパスを入力してください。").toString());

        String[] inCorrects = {};
        for (int i = 0; i < inCorrects.length; i++) {
            FormValidation actual3 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckAwsKeyfile(inCorrects[i]);
            assertEquals(actual3.toString(), FormValidation.error("AWSの秘密鍵のパスの形式が違います。").toString());
        }
    }

    @Test
    public void Regionに対するバリデーションチェック() throws IOException, ServletException {
        String[] corrects = { "us-east-1", "ap-northeast-1", "eu-west-1" };
        for (int i = 0; i < corrects.length; i++) {
            FormValidation actual = AwsEnvironmentBuilder.DESCRIPTOR.doCheckRegion(corrects[i]);
            assertEquals(actual, FormValidation.ok());
        }

        FormValidation actual2 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckRegion("");
        assertEquals(actual2.toString(), FormValidation.error("Regionを入力してください。").toString());

        String[] inCorrects = { "日本語テキスト", "us-east-1a" };
        for (int i = 0; i < inCorrects.length; i++) {
            FormValidation actual3 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckRegion(inCorrects[i]);
            assertEquals(actual3.toString(), FormValidation.error("Regionの形式が違います。").toString());
        }
    }

    @Test
    public void AvailabilityZoneに対するバリデーションチェック() throws IOException, ServletException {
        String[] corrects = { "us-east-1a", "ap-northeast-1b", "eu-west-1a" };
        for (int i = 0; i < corrects.length; i++) {
            FormValidation actual = AwsEnvironmentBuilder.DESCRIPTOR.doCheckAvailabilityZone(corrects[i]);
            assertEquals(actual, FormValidation.ok());
        }

        FormValidation actual2 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckAvailabilityZone("");
        assertEquals(actual2.toString(), FormValidation.error("Availability Zoneを入力してください。").toString());

        String[] inCorrects = { "日本語テキスト", "us-east-1" };
        for (int i = 0; i < inCorrects.length; i++) {
            FormValidation actual3 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckAvailabilityZone(inCorrects[i]);
            assertEquals(actual3.toString(), FormValidation.error("Availability Zoneの形式が違います。").toString());
        }
    }

    @Test
    public void AMI_IDに対するバリデーションチェック() throws IOException, ServletException {
        String[] corrects = { "ami-03559b6a", "ami-e00df089" };
        for (int i = 0; i < corrects.length; i++) {
            FormValidation actual = AwsEnvironmentBuilder.DESCRIPTOR.doCheckAmiId(corrects[i]);
            assertEquals(actual, FormValidation.ok());
        }

        FormValidation actual2 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckAmiId("");
        assertEquals(actual2.toString(), FormValidation.error("AMI IDを入力してください。").toString());

        String[] inCorrects = { "日本語テキスト", "ami03559b6a", "03559b6a" };
        for (int i = 0; i < inCorrects.length; i++) {
            FormValidation actual3 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckAmiId(inCorrects[i]);
            assertEquals(actual3.toString(), FormValidation.error("AMI IDの形式が違います。").toString());
        }
    }

    @Test
    public void Flavorに対するバリデーションチェック() throws IOException, ServletException {
        String[] corrects = { "m1.micro", "m2.small", "m2.medium", "m1.xlarge", "c1.xlarge", "m2.4xlarge", "hi1.4xlarge" };
        for (int i = 0; i < corrects.length; i++) {
            FormValidation actual = AwsEnvironmentBuilder.DESCRIPTOR.doCheckFlavor(corrects[i]);
            assertEquals(actual, FormValidation.ok());
        }

        FormValidation actual2 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckFlavor("");
        assertEquals(actual2.toString(), FormValidation.error("インスタンスタイプを入力してください。").toString());

        String[] inCorrects = { "m1small", "small", "m1", "hi1.4xlage" };
        for (int i = 0; i < inCorrects.length; i++) {
            FormValidation actual3 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckFlavor(inCorrects[i]);
            assertEquals(actual3.toString(), FormValidation.error("インスタンスタイプの形式が違います。").toString());
        }
    }

    @Test
    public void Elastic_IPに対するバリデーションチェック() throws IOException, ServletException {
        String[] corrects = { "121.2.37.141" };
        for (int i = 0; i < corrects.length; i++) {
            FormValidation actual = AwsEnvironmentBuilder.DESCRIPTOR.doCheckElasticIp(corrects[i]);
            assertEquals(actual, FormValidation.ok());
        }

        FormValidation actual2 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckElasticIp("");
        assertEquals(actual2.toString(), FormValidation.error("Elastic IPを入力してください。").toString());

        String[] inCorrects = { "10.1", "10.1.1" };
        for (int i = 0; i < inCorrects.length; i++) {
            FormValidation actual3 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckElasticIp(inCorrects[i]);
            assertEquals(actual3.toString(), FormValidation.error("Elastic IPの形式が違います。").toString());
        }
    }

    @Test
    public void SecurityGroupに対するバリデーションチェック() throws IOException, ServletException {
        String[] corrects = { "default", "development servers" };
        for (int i = 0; i < corrects.length; i++) {
            FormValidation actual = AwsEnvironmentBuilder.DESCRIPTOR.doCheckSecurityGroup(corrects[i]);
            assertEquals(actual, FormValidation.ok());
        }

        FormValidation actual2 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckSecurityGroup("");
        assertEquals(actual2.toString(), FormValidation.error("Security Groupを入力してください。").toString());

        String[] inCorrects = {};
        for (int i = 0; i < inCorrects.length; i++) {
            FormValidation actual3 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckSecurityGroup(inCorrects[i]);
            assertEquals(actual3.toString(), FormValidation.error("Security Groupの形式が違います。").toString());
        }
    }

    @Test
    public void Roleに対するバリデーションチェック() throws IOException, ServletException {
        String[] corrects = { "web", "web,db" };
        for (int i = 0; i < corrects.length; i++) {
            FormValidation actual = AwsEnvironmentBuilder.DESCRIPTOR.doCheckRole(corrects[i]);
            assertEquals(actual, FormValidation.ok());
        }

        FormValidation actual2 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckRole("");
        assertEquals(actual2.toString(), FormValidation.error("Roleを入力してください。").toString());

        String[] inCorrects = {"web,db," };
        for (int i = 0; i < inCorrects.length; i++) {
            FormValidation actual3 = AwsEnvironmentBuilder.DESCRIPTOR.doCheckRole(inCorrects[i]);
            assertEquals(actual3.toString(), FormValidation.error("Roleの形式が違います。").toString());
        }
    }
}
