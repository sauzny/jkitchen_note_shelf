package com.sauzny.sshj;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.sftp.StatefulSFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.FileSystemFile;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class App {
    public static void main(String[] args) {



        try(SSHClient ssh = new SSHClient()){

            ssh.addHostKeyVerifier(new PromiscuousVerifier());
            ssh.connect("172.16.8.211", 22);
            ssh.authPassword("root", "111111");
            //StatefulSFTPClient
            /*
            try (final SFTPClient sftp = ssh.newSFTPClient()) {
                sftp.put(new FileSystemFile("F:\\temp\\汉字.txt"),"/root/Downloads");
            }
            */
            try (Session session = ssh.startSession()) {
                Session.Command cmd = session.exec("df -h");
                String ret = IOUtils.readFully(cmd.getInputStream()).toString();
                System.out.println(ret);
                cmd.join(1, TimeUnit.SECONDS);
                System.out.println("\n** exit status: " + cmd.getExitStatus());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }catch(IOException e1){
            e1.printStackTrace();
        }
    }
}
